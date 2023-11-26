package com.example.posganize.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorModel {
    private String errorType;
    private Integer statusCode;
    private String message;
    private LocalDateTime time;
}
