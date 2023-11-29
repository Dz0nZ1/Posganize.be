package com.example.posganize.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMembershipModel {
    private LocalDateTime startDate;
    private LocalDateTime expireDate;
    private Long userId;
    private Set<TrainingModel> trainings;
    private Double price;
    private Boolean active;
}
