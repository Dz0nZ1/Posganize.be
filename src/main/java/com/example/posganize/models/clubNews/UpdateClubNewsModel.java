package com.example.posganize.models.clubNews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateClubNewsModel {

    private String title;
    private String description;
    private String image;
}
