package com.example.posganize.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMembershipModel {
    private Long userId;
    private String firstName;
    private String lastName;
}
