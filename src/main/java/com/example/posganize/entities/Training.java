package com.example.posganize.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long training_id;

    private String name;
    private Double price;
    private String image;

    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "training")
    private List<Schedule> schedules;


}
