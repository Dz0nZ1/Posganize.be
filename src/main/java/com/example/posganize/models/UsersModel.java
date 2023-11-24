package com.example.posganize.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersModel {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDateTime registrationDate;
    private Boolean active;

}
