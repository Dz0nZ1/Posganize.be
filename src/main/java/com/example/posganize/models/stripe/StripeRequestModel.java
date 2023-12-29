package com.example.posganize.models.stripe;



import com.example.posganize.models.training.TrainingModel;
import lombok.Data;

import java.util.List;

@Data
public class StripeRequestModel {
    List<TrainingModel> items;
    String customerName;
    String customerEmail;

}
