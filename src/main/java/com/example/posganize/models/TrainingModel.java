package com.example.posganize.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingModel {

    private String name;
    private Double price;
    private List<ScheduleModel> schedule;
}
