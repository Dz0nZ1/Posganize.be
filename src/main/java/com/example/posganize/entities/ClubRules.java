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
@Table(name = "club_rules")
public class ClubRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rules_id;
    @Column(name = "name")
    @NotBlank(message = "Club rules name is mandatory")
    private String name;
    @Column(name="image")
    @NotBlank(message = "Image Url for the icon is mandatory")
    private String image;
    @Column(name = "description", length = 10000)
    @NotBlank(message = "Club rules description is mandatory")
    private String description;

}
