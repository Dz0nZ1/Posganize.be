package com.example.posganize.mappers;


import com.example.posganize.entities.Schedule;
import com.example.posganize.models.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleMapper {

    public static ScheduleModel mapScheduleToScheduleModel(Schedule schedule) {
        return ScheduleModel.builder()
                .name(schedule.getName())
                .day(schedule.getDay())
                .time(schedule.getTime())
                .build();
    }


    public static Schedule mapScheduleModelToSchedule(ScheduleModel schedule){
        return Schedule.builder().name(schedule.getName()).time(schedule.getTime()).day(schedule.getDay()).build();
    }

    public static List<ScheduleModel> mapScheduleListToScheduleModelList(List<Schedule> schedules){
        List<ScheduleModel> modelList = new ArrayList<>();
        for(Schedule entity : schedules){
            modelList.add(mapScheduleToScheduleModel(entity));
        }
        return modelList;
    }

}
