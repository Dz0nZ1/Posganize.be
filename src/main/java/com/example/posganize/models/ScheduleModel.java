package com.example.posganize.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleModel {
    private Long id;
    private String scheduleName;
    private String scheduleDay;
    private String scheduleTime;
}
