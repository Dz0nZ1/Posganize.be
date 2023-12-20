package com.example.posganize.models;


import lombok.Data;

@Data
public class StripeSubscriptionModel {

    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String cvc;
    private String email;
    private String username;
    private String priceId;
    private long numberOfLicense;
    private boolean success;
}
