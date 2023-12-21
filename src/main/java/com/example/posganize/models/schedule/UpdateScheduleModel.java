package com.example.posganize.models.schedule;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateScheduleModel {
    private String scheduleName;
    private String scheduleDay;
    private String scheduleTime;
}
