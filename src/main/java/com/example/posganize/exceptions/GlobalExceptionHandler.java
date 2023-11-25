package com.example.posganize.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorModel> handleRegisterException(DataIntegrityViolationException ex) {
        var error = ErrorModel.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage() + ": User with that name already exists")
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handleUserNotFoundException(UserNotFoundException ex) {
        var error =  ErrorModel.builder()
                .type("User")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MembershipNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handleMembershipNotFoundException(MembershipNotFoundException ex) {
        var error =  ErrorModel.builder()
                .type("Membership")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        var error =  ErrorModel.builder()
                .type("Payment")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(TrainingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handleTrainingNotFoundException(TrainingNotFoundException ex) {
        var error =  ErrorModel.builder()
                .type("Training")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ScheduleNotfoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handleScheduleNotFoundException(ScheduleNotfoundException ex) {
        var error =  ErrorModel.builder()
                .type("Schedule")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
