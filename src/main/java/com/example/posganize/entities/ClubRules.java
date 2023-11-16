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
@Table(name = "club_rules")
public class ClubRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rules_id;
    private String name;
    private String description;

}
