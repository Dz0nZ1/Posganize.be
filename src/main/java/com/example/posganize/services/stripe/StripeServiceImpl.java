package com.example.posganize.services.stripe;

import com.example.posganize.constants.StripeConstants;
import com.example.posganize.controllers.StripeController;
import com.example.posganize.enums.CurrencyEnum;
import com.example.posganize.models.membership.CreateMembershipModel;
import com.example.posganize.models.stripe.StripeRequestModel;
import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.services.membership.MembershipService;
import com.example.posganize.services.training.TrainingService;
import com.example.posganize.utils.CustomerUtil;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StripeServiceImpl implements StripeService {

    private final TrainingService trainingService;
    private final MembershipService membershipService;
    private final String endpointSecret = "whsec_81f3832b9fe0f8a2bdc39677e4f9703e8ec2a16ed00a325bceee691d6f427f78";
    private static final Logger LOGGER = LoggerFactory.getLogger(StripeController.class);

    public StripeServiceImpl(TrainingService trainingService, MembershipService membershipService) {
        this.trainingService = trainingService;
        this.membershipService = membershipService;
    }

    String STRIPE_API_KEY = StripeConstants.STRIPE_SECRET_KEY;

    @Override
    public String hostedCheckout(StripeRequestModel stripeRequestModel) throws StripeException {
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

    @Override
    public String handleStripeWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            switch (event.getType()) {

                case "payment_intent.succeeded" -> {
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
                                .startDate(LocalDateTime.now().toLocalDate().atStartOfDay())
                                .expireDate(LocalDateTime.now().plusDays(30).toLocalDate().atStartOfDay())
                                .build();

                        membershipService.createMembershipByUserEmail(createMembershipModel, email);
                        System.out.println("Membership successfully purchased");
                    }
                }
                case "payment_intent.payment_failed" -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Received payment failure notification");
                case "checkout.session.completed" -> LOGGER.info("Checkout success");
                default -> LOGGER.info("Unhandled event type: " + event.getType());
            }

            return "Received and processed the event.";
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return "Invalid signature.";
        } catch (Exception e) {
            // Handle other exceptions or log the error
            return "Error processing the event.";
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
