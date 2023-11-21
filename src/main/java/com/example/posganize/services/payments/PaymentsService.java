package com.example.posganize.services.payments;

import com.example.posganize.models.PaymentsModel;

import java.util.List;

public interface PaymentsService {

    List<PaymentsModel> getAllPayments();

    PaymentsModel getPayments(Long paymentsId);

    PaymentsModel createPayments(PaymentsModel paymentsModel);

    PaymentsModel updatePayments(Long paymentsId, PaymentsModel paymentsModel);

    void deletePayments(Long paymentsId);

}
