package com.example.posganize.models.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTrainingModel {
    @NotBlank(message = "Training name is mandatory")
    private String name;
    @NotBlank(message = "Description name is mandatory")
    private String description;
    @NotNull(message = "Price is mandatory")
    private Double price;
    @NotBlank(message = "Image name is mandatory")
    private String image;

}
