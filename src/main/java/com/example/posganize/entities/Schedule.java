package com.example.posganize.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedule_id;
    private String scheduleName;
    private String scheduleDay;
    private String scheduleTime;
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

}
