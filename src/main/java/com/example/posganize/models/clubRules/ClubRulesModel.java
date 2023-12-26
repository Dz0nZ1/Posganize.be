package com.example.posganize.models.clubRules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubRulesModel {

    private Long id;
    private String image;
    private String name;
    private String description;

}
