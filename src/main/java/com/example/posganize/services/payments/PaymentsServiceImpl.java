package com.example.posganize.services.payments;
import com.example.posganize.exceptions.PaymentNotFoundException;
import com.example.posganize.mappers.PaymentsMapper;
import com.example.posganize.models.PaymentsModel;
import com.example.posganize.repository.PaymentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    private final PaymentsRepository paymentsRepository;

    public PaymentsServiceImpl(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public List<PaymentsModel> getAllPayments() {
        return PaymentsMapper.mapPaymentsListToPaymentsModelList(paymentsRepository.findAll());
    }

    @Override
    public PaymentsModel getPayments(Long paymentsId) {
        return PaymentsMapper.mapPaymentsToPaymentsModel(paymentsRepository.findById(paymentsId).orElseThrow(() -> new PaymentNotFoundException("Payments not found")));
    }

    @Override
    public PaymentsModel createPayments(PaymentsModel paymentsModel) {
        var entity = PaymentsMapper.mapPaymentsModelToPayments(paymentsModel);
        paymentsRepository.save(entity);
        return PaymentsMapper.mapPaymentsToPaymentsModel(entity);
    }

    @Override
    public PaymentsModel updatePayments(Long paymentsId, PaymentsModel paymentsModel) {
        var entity = PaymentsMapper.mapPaymentsModelToPayments(paymentsModel);
        var newPayments = paymentsRepository.findById(paymentsId).orElseThrow(() -> new PaymentNotFoundException("Payments not found"));
        if(entity.getUser() != null) {
            newPayments.setUser(entity.getUser());
        }
        if(entity.getMembership() != null) {
            newPayments.setMembership(entity.getMembership());
        }
        if(entity.getFromDate() != null) {
            newPayments.setFromDate(entity.getFromDate());
        }
        if(entity.getToDate() != null) {
            newPayments.setToDate(entity.getToDate());
        }

        return PaymentsMapper.mapPaymentsToPaymentsModel(newPayments);

    }

    @Override
    public void deletePayments(Long paymentsId) {
        paymentsRepository.deleteById(paymentsId);
    }
}
