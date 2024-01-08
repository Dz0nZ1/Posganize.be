package com.example.posganize.services.stripe;

import com.example.posganize.models.stripe.StripeRequestModel;
import com.stripe.exception.StripeException;


public interface StripeService {
    String hostedCheckout(StripeRequestModel stripeRequestModel) throws StripeException;

    String handleStripeWebhook(String payload, String sigHeader);

}
