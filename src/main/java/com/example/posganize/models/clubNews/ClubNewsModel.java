package com.example.posganize.models.clubNews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubNewsModel {

    private Long id;
    private String title;
    private String description;
    private String image;
    private String author;
    private LocalDateTime createdAt;

}
