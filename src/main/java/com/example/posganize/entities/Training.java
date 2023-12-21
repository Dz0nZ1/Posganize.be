package com.example.posganize.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "name")
    @NotBlank(message = "Training name is mandatory")
    private String name;
    @Column(name = "price")
    @NotNull
    private Double price;
    @Column(name = "image" )
    @NotBlank(message = "Image is mandatory")
    private String image;
    @Column(name = "description", length = 100000)
    @NotBlank(message = "Description is mandatory")
    private String description;

    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Schedule> schedules;


}
