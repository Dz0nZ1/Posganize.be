package com.example.posganize.models;

import com.example.posganize.entities.Training;
import com.example.posganize.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentsModel {

    private Users user;
    private List<Training> trainings;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Double totalPrice;

}
