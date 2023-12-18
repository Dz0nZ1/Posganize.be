package com.example.posganize.models;


import lombok.Data;

@Data
public class StripeTokenModel {

    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String cvc;
    private String token;
    private String username;
    private boolean success;
}
