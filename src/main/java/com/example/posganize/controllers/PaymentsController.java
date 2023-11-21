package com.example.posganize.controllers;

import com.example.posganize.models.PaymentsModel;
import com.example.posganize.services.payments.PaymentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<PaymentsModel>> getAllPayments(){
        return new ResponseEntity<>(paymentsService.getAllPayments(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<PaymentsModel> getPaymentsById(@PathVariable("id") Long paymentsId){
        return new ResponseEntity<>(paymentsService.getPayments(paymentsId), HttpStatus.OK);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<PaymentsModel> createPayments(@RequestBody PaymentsModel payments){
        return new ResponseEntity<>(paymentsService.createPayments(payments), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<PaymentsModel> updatePayments(@PathVariable("id") Long paymentsId, @RequestBody PaymentsModel payments){
        return new ResponseEntity<>(paymentsService.updatePayments(paymentsId, payments), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deletePayments(@PathVariable("id") Long paymentsId) {
        paymentsService.deletePayments(paymentsId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "Payment was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
