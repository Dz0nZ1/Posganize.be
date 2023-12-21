package com.example.posganize.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUsersModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
}
