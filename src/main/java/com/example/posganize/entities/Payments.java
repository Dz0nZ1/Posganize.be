package com.example.posganize.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payment_id;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users users;

    @ManyToMany
    @JoinTable(
            name = "payments_training",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "training_id"))
    List<Training> likedCourses;

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Double totalPrice;







}
