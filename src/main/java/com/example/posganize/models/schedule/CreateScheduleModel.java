package com.example.posganize.models.schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateScheduleModel {
    @NotBlank(message = "Schedule name name is mandatory")
    private String scheduleName;
    @NotBlank(message = "Schedule day name is mandatory")
    private String scheduleDay;
    @NotBlank(message = "Schedule time name is mandatory")
    private String scheduleTime;
}
