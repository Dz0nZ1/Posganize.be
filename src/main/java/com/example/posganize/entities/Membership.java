package com.example.posganize.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membership_id;
    @Column(name = "start_date")
    @NotNull(message = "Start day name is mandatory")
    private LocalDateTime startDate;
    @Column(name = "expire_date")
    @NotNull(message = "Expire day name name is mandatory")
    private LocalDateTime expireDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "membership_training",
            joinColumns = @JoinColumn(name = "membership_id"),
            inverseJoinColumns = @JoinColumn(name = "training_id"))
    private Set<Training> trainings;
    @Column(name = "price")
    @NotNull
    private Double price;
    @Column(name = "active")
    private Boolean active;

}
