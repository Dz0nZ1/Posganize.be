package com.example.posganize.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipModel {

    private LocalDateTime startDate;
    private LocalDateTime expireDate;
    private Double price;
    private Boolean active;
    private UserMembershipModel user;
    private List<TrainingModel> trainings;

}
