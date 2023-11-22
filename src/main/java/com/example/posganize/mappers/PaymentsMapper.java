package com.example.posganize.mappers;
import com.example.posganize.entities.Payments;
import com.example.posganize.models.PaymentsModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentsMapper {


    public static PaymentsModel mapPaymentsToPaymentsModel(Payments payments) {
        return PaymentsModel.builder()
                .user(payments.getUser())
                .membership(payments.getMembership())
                .fromDate(payments.getFromDate())
                .toDate(payments.getToDate())
                .totalPrice(payments.getTotalPrice())
                .build();
    }


    public static Payments mapPaymentsModelToPayments(PaymentsModel payments) {
        return Payments.builder()
                .user(payments.getUser())
                .membership(payments.getMembership())
                .fromDate(payments.getFromDate())
                .toDate(payments.getToDate())
                .totalPrice(payments.getTotalPrice())
                .build();
    }


    public static List<PaymentsModel> mapPaymentsListToPaymentsModelList(List<Payments> payments) {
        List<PaymentsModel> modelList = new ArrayList<>();
        for (Payments entity : payments) {
            modelList.add(mapPaymentsToPaymentsModel(entity));
        }
        return modelList;

    }


}
