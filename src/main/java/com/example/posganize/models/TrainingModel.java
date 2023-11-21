package com.example.posganize.models;

import com.example.posganize.entities.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingModel {

    private Schedule schedule;

    private String name;
    private Double price;
}
