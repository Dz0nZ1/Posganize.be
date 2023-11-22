package com.example.posganize.models;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentsModel {

    private Users user;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Double totalPrice;
    private Membership membership;

}
