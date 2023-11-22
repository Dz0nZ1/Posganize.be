package com.example.posganize.models;

import com.example.posganize.entities.Training;
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
public class CreateMembershipModel {
    private LocalDateTime startDate;
    private LocalDateTime expireDate;
    private Long userId;
    private List<Training> trainings;
    private Double price;
    private Boolean active;
}
