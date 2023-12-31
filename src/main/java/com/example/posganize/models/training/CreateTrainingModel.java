package com.example.posganize.models.training;

import com.example.posganize.models.schedule.ScheduleModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTrainingModel {

    @NotBlank(message = "Training name is mandatory")
    private String name;
    @NotBlank(message = "Description name is mandatory")
    private String description;
    @NotBlank(message = "Image name is mandatory")
    private String image;
    @NotNull(message = "Price is mandatory")
    private Double price;
    private List<ScheduleModel> schedule;
}
