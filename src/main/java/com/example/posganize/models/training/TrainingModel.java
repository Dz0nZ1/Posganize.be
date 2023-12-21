package com.example.posganize.models.training;

import com.example.posganize.models.ScheduleModel;
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

    private Long id;
    private String name;
    private String description;
    private String image;
    private Double price;
    private List<ScheduleModel> schedule;
}
