package com.example.posganize.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "schedule_name")
    @NotBlank(message = "Schedule name name is mandatory")
    private String scheduleName;
    @Column(name = "schedule_day")
    @NotBlank(message = "Schedule day is mandatory")
    private String scheduleDay;
    @Column(name = "schedule_time")
    @NotBlank(message = "Schedule time is mandatory")
    private String scheduleTime;
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

}
