package com.example.posganize.controllers;
import com.example.posganize.models.stripe.*;
import com.example.posganize.services.stripe.StripeService;

import com.stripe.exception.StripeException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe")
@Slf4j
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasAuthority('user:create')")
    ResponseEntity<String> hostedCheckout(@RequestBody StripeRequestModel stripeRequestModel) throws StripeException {
        return new ResponseEntity<>(stripeService.hostedCheckout(stripeRequestModel), HttpStatus.OK);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        return new ResponseEntity<>(stripeService.handleStripeWebhook(payload, sigHeader), HttpStatus.OK);
    }
}