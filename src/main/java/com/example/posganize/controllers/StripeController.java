package com.example.posganize.controllers;

import com.example.posganize.constants.StripeConstants;
import com.example.posganize.enums.CurrencyEnum;
import com.example.posganize.models.membership.CreateMembershipModel;
import com.example.posganize.models.stripe.*;
import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.services.membership.MembershipService;
import com.example.posganize.services.training.TrainingService;
import com.example.posganize.utils.CustomerUtil;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/stripe")
@Slf4j
public class StripeController {

    String STRIPE_API_KEY = StripeConstants.STRIPE_SECRET_KEY;

    private final TrainingService trainingService;
    private final MembershipService membershipService;
    private final String endpointSecret = "whsec_81f3832b9fe0f8a2bdc39677e4f9703e8ec2a16ed00a325bceee691d6f427f78";
    private static final Logger LOGGER = LoggerFactory.getLogger(StripeController.class);

    public StripeController(TrainingService trainingService, MembershipService membershipService) {
        this.trainingService = trainingService;
        this.membershipService = membershipService;
    }


    @PostMapping("/checkout")
    @PreAuthorize("hasAuthority('user:create')")
    String hostedCheckout(@RequestBody StripeRequestModel stripeRequestModel) throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;
        String clientBaseURL = StripeConstants.CLIENT_BASE_URL;

        // Start by finding an existing customer record from Stripe or creating a new one if needed
        Customer customer = CustomerUtil.findOrCreateCustomer(stripeRequestModel.getCustomerEmail(), stripeRequestModel.getCustomerName());


        List<Long> trainingModelIds = stripeRequestModel.getItems().stream().map(TrainingModel::getId)
                .toList();


        // Next, create a checkout session by adding the details of the checkout
        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(customer.getId())
                        .setSuccessUrl(clientBaseURL + "/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl(clientBaseURL + "/failure");

        for (TrainingModel trainingModel : stripeRequestModel.getItems()) {
            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .putMetadata("app_id", trainingModel.getId().toString())
                                                            .setName(trainingModel.getName())
                                                            .build()
                                            )
                                            .setCurrency(CurrencyEnum.USD.toString())
                                            .setUnitAmountDecimal(BigDecimal.valueOf(trainingModel.getPrice() * 100))
                                            .build())
                            .build());
            paramsBuilder.setPaymentIntentData(
                    SessionCreateParams.PaymentIntentData.builder()
                            .putMetadata("trainingModelIds", trainingModelIds.toString())
                            .putMetadata("email", stripeRequestModel.getCustomerEmail())
                            .build()
            );
        }

        Session session = Session.create(paramsBuilder.build());

        return session.getUrl();
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent createPaymentIntent = null;

                    var stripeObjectOptional1 = event.getDataObjectDeserializer().getObject();

                    if (stripeObjectOptional1.isPresent() && stripeObjectOptional1.get() instanceof PaymentIntent) {
                        createPaymentIntent = (PaymentIntent) stripeObjectOptional1.get();
                    } else {
                        System.out.println("Unexpected object type or missing PaymentIntent");
                    }
                    if (createPaymentIntent != null) {
                        LOGGER.info("Payment intent was successfully created");
                    }

                    break;
                case "invoice.payment_succeeded":

                    PaymentIntent paymentIntent = null;

                    var stripeObjectOptional = event.getDataObjectDeserializer().getObject();

                    if (stripeObjectOptional.isPresent() && stripeObjectOptional.get() instanceof PaymentIntent) {
                        paymentIntent = (PaymentIntent) stripeObjectOptional.get();
                    } else {
                        System.out.println("Unexpected object type or missing PaymentIntent");
                    }

                    if (paymentIntent != null) {
                        Map<String, String> metadata = paymentIntent.getMetadata();

                        String trainingModelIdsString = metadata.get("trainingModelIds");

                        String email = (String) metadata.get("email");
                        List<Long> listData = parseStringToList(trainingModelIdsString);

                        Set<TrainingModel> trainingModels = new HashSet<>();
                        for (Long trainingId : listData) {
                            var training = trainingService.getTrainingById(trainingId);
                            trainingModels.add(training);
                        }

                        CreateMembershipModel createMembershipModel = CreateMembershipModel
                                .builder()
                                .trainings(trainingModels)
                                .startDate(LocalDateTime.now())
                                .expireDate(LocalDateTime.now().plusDays(30))
                                .build();

                        membershipService.createMembershipByUserEmail(createMembershipModel, email);
                        System.out.println("Membership successfully purchased");


                    }
                    break;
                case "checkout.session.completed":
                    LOGGER.info("Checkout success");
                    break;
                default:
                    LOGGER.info("Unhandled event type: " + event.getType());
                    break;
            }

            return ResponseEntity.status(HttpStatus.OK).body("Received and processed the event.");
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature.");
        } catch (Exception e) {
            // Handle other exceptions or log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the event.");
        }
    }

    public static List<Long> parseStringToList(String input) {
        input = input.substring(1, input.length() - 1);
        String[] elements = input.split(", ");

        List<Long> result = new ArrayList<>();
        for (String element : elements) {
            result.add(Long.parseLong(element));
        }

        return result;
    }
}