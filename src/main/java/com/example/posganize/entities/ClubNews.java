package com.example.posganize.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "name")
    @NotBlank(message = "Club news name is mandatory")
    private String name;
    @Column(name = "description")
    @NotBlank(message = "Description is mandatory")
    private String description;

}
