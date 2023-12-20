package com.example.posganize.models.membership;
import com.example.posganize.models.TrainingModel;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Start date name is mandatory")
    private LocalDateTime startDate;
    @NotBlank(message = "Expire day name name is mandatory")
    private LocalDateTime expireDate;
    private Long userId;
    private Set<TrainingModel> trainings;
}
