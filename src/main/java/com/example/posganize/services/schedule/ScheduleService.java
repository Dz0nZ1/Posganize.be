package com.example.posganize.services.schedule;

import com.example.posganize.models.ScheduleModel;

import java.util.List;

public interface ScheduleService {

    List<ScheduleModel> getAllSchedules();

    ScheduleModel getScheduleById(Long scheduleId);

    ScheduleModel createSchedule(ScheduleModel schedule);

    ScheduleModel updateSchedule(ScheduleModel schedule , Long scheduleID);

    void deleteSchedule(Long scheduleId);
}
