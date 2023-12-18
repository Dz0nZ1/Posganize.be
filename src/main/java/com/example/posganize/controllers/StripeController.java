package com.example.posganize.controllers;

import com.example.posganize.models.StripeChargeModel;
import com.example.posganize.models.StripeTokenModel;
import com.example.posganize.services.stripe.StripeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/card/token")
    @ResponseBody
    public StripeTokenModel createCardToken(@RequestBody StripeTokenModel model) {
        return stripeService.createCardToken(model);
    }

    @PostMapping("/charge")
    @ResponseBody
    public StripeChargeModel charge(@RequestBody StripeChargeModel model) {
        return stripeService.charge(model);
    }

}
