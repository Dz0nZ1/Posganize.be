package com.example.posganize.services.schedule;

import com.example.posganize.models.schedule.CreateScheduleModel;
import com.example.posganize.models.schedule.ScheduleModel;
import com.example.posganize.models.schedule.UpdateScheduleModel;

import java.util.List;

public interface ScheduleService {

    List<ScheduleModel> getAllSchedules();

    ScheduleModel getScheduleById(Long scheduleId);

    ScheduleModel createSchedule(CreateScheduleModel schedule);

    ScheduleModel createScheduleByTrainingId(CreateScheduleModel scheduleModel, Long trainingId);

    ScheduleModel updateSchedule(UpdateScheduleModel schedule , Long scheduleID);

    void deleteSchedule(Long scheduleId);
}
