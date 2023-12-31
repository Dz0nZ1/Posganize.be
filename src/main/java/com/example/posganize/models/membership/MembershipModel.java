package com.example.posganize.models.membership;

import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.models.user.UserMembershipModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipModel {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;
    private Double price;
    private Boolean active;
    private UserMembershipModel user;
    private Set<TrainingModel> trainings;

}
