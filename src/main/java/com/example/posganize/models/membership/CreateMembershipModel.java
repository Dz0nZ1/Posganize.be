package com.example.posganize.models.membership;
import com.example.posganize.models.training.TrainingModel;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Start date name is mandatory")
    private LocalDateTime startDate;
    @NotNull(message = "Expire day name name is mandatory")
    private LocalDateTime expireDate;
    private Long userId;
    private Set<TrainingModel> trainings;
}
