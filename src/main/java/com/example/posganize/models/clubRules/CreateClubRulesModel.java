package com.example.posganize.models.clubRules;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClubRulesModel {

    @NotBlank(message = "Image is mandatory")
    private String image;
    @NotBlank(message = "Club news name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;


}
