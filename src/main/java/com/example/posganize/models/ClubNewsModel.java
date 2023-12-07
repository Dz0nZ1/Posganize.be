package com.example.posganize.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubNewsModel {

    @NotBlank(message = "Club news name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;

}
