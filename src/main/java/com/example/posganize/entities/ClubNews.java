package com.example.posganize.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "club_news")
public class ClubNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long news_id;
    private String name;
    private String description;

}
