package com.example.posganize.exceptions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.example.posganize.constants.ExceptionConstants.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorModel> handleRegisterException(DataIntegrityViolationException ex) {
        var error = ErrorModel.builder()
                .errorType(DATA_INTEGRITY_ERROR_TYPE)
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorModel> handleAccessDeniedException(AccessDeniedException ex) {
        var error = ErrorModel.builder()
                .errorType(ACCESS_DENIED_ERROR_TYPE)
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handleUserStatusException(UserStatusException ex) {
        var error = ErrorModel.builder()
                .errorType(USER_STATUS_ERROR_TYPE)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handleUserNotFoundException(UserNotFoundException ex) {
        var error =  ErrorModel.builder()
                .errorType(USER_ERROR_TYPE)
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
                .errorType(MEMBERSHIP_ERROR_TYPE)
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
                .errorType(TRAINING_ERROR_TYPE)
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
                .errorType(SCHEDULE_ERROR_TYPE)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorModel> handleBadCredentialsException(BadCredentialsException ex) {
        var error =  ErrorModel.builder()
                .errorType(CREDENTIALS_TYPE)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorModel> handleUsernameNotFoundExceptionException(UsernameNotFoundException ex) {
        var error =  ErrorModel.builder()
                .errorType(USER_ERROR_TYPE)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex)
    {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("name", ex.getName());
        response.put("statusCode", Integer.toString(HttpStatus.BAD_REQUEST.value()));
        response.put("value", " request parameter  " + ex.getValue());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        errors.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> resolveException() {
        Map<String, String> message = new HashMap<>();
        message.put("message", "Please provide Request Body in valid JSON format");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
