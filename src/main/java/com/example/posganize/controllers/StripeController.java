package com.example.posganize.controllers;

import com.example.posganize.constants.StripeConstants;
import com.example.posganize.enums.CurrencyEnum;
import com.example.posganize.models.stripe.*;
import com.example.posganize.models.training.TrainingModel;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {

    String STRIPE_API_KEY = StripeConstants.STRIPE_SECRET_KEY;

//    @PostMapping("/checkout/hosted")
//    String hostedCheckout(@RequestBody RequestDTO requestDTO) throws StripeException {
//        return "Hello World!";
//    }

    @PostMapping("/checkout")
    String hostedCheckout(@RequestBody RequestDTO requestDTO) throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;
        String clientBaseURL = StripeConstants.CLIENT_BASE_URL;

        // Start by finding an existing customer record from Stripe or creating a new one if needed
        Customer customer = CustomerUtil.findOrCreateCustomer(requestDTO.getCustomerEmail(), requestDTO.getCustomerName());

        // Next, create a checkout session by adding the details of the checkout
        SessionCreateParams.Builder paramsBuilder =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(customer.getId())
                        .setSuccessUrl(clientBaseURL + "/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl(clientBaseURL + "/failure");

        for (TrainingModel trainingModel : requestDTO.getItems()) {
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
                                            .setUnitAmountDecimal(BigDecimal.valueOf(trainingModel.getPrice()*100))
                                            .build())
                            .build());
        }

        Session session = Session.create(paramsBuilder.build());

        return session.getUrl();
    }




//    private final StripeService stripeService;
//
//    public StripeController(StripeService stripeService) {
//        this.stripeService = stripeService;
//    }
//
//    @PostMapping("/card/token")
//    @ResponseBody
//    public StripeTokenModel createCardToken(@RequestBody StripeTokenModel model) {
//        return stripeService.createCardToken(model);
//    }
//
//    @PostMapping("/charge")
//    @ResponseBody
//    public StripeChargeModel charge(@RequestBody StripeChargeModel model) {
//        return stripeService.charge(model);
//    }
//
//
//    @PostMapping("/customer/subscription")
//    @ResponseBody
//    public StripeSubscriptionResponse subscription(@RequestBody StripeSubscriptionModel model) {
//        return stripeService.createSubscription(model);
//    }


}
