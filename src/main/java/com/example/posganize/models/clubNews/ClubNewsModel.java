package com.example.posganize.models.clubNews;

import com.example.posganize.entities.Users;
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
    private Users user;
    private LocalDateTime createdAt;

}
