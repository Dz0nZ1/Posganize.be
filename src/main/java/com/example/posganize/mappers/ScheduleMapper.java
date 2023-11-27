package com.example.posganize.mappers;


import com.example.posganize.entities.Schedule;
import com.example.posganize.models.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleMapper {

    public static ScheduleModel mapScheduleToScheduleModel(Schedule schedule) {
        return ScheduleModel.builder()
                .id(schedule.getSchedule_id())
                .scheduleName(schedule.getScheduleName())
                .scheduleDay(schedule.getScheduleDay())
                .scheduleTime(schedule.getScheduleTime())
                .build();
    }


    public static Schedule mapScheduleModelToSchedule(ScheduleModel schedule){
        return Schedule.builder().schedule_id(schedule.getId()).scheduleName(schedule.getScheduleName()).scheduleTime(schedule.getScheduleTime()).scheduleDay(schedule.getScheduleDay()).build();
    }

    public static List<ScheduleModel> mapScheduleListToScheduleModelList(List<Schedule> schedules){
        List<ScheduleModel> modelList = new ArrayList<>();
        for(Schedule entity : schedules){
            modelList.add(mapScheduleToScheduleModel(entity));
        }
        return modelList;
    }


    public static List<Schedule> mapScheduleListModelToScheduleList(List<ScheduleModel> schedules){
        List<Schedule> modelList = new ArrayList<>();
        for(ScheduleModel model : schedules){
            modelList.add(mapScheduleModelToSchedule(model));
        }
        return modelList;
    }

}
