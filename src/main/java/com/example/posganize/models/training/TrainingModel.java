package com.example.posganize.models.training;

import com.example.posganize.enums.CurrencyEnum;
import com.example.posganize.models.schedule.ScheduleModel;
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
    private CurrencyEnum currency;
    private List<ScheduleModel> schedule;
}
