package com.example.posganize.services.schedule;

import com.example.posganize.exceptions.ScheduleNotfoundException;
import com.example.posganize.exceptions.TrainingNotFoundException;
import com.example.posganize.mappers.ScheduleMapper;
import com.example.posganize.models.schedule.CreateScheduleModel;
import com.example.posganize.models.schedule.ScheduleModel;
import com.example.posganize.models.schedule.UpdateScheduleModel;
import com.example.posganize.repository.ScheduleRepository;
import com.example.posganize.repository.TrainingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final TrainingRepository trainingRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, TrainingRepository trainingRepository) {
        this.scheduleRepository = scheduleRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<ScheduleModel> getAllSchedules() {
        return ScheduleMapper.mapScheduleListToScheduleModelList(scheduleRepository.findAll());
    }

    @Override
    public ScheduleModel getScheduleById(Long scheduleId) {
        return ScheduleMapper.mapScheduleToScheduleModel(scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotfoundException("Schedule not found")));
    }

    @Override
    public ScheduleModel createSchedule(CreateScheduleModel schedule) {
        var entity = ScheduleMapper.mapCreateScheduleModelToSchedule(schedule);
        scheduleRepository.save(entity);
        return ScheduleMapper.mapScheduleToScheduleModel(entity);
    }

    @Override
    public ScheduleModel createScheduleByTrainingId(CreateScheduleModel schedule, Long trainingId) {
        var entity = ScheduleMapper.mapCreateScheduleModelToSchedule(schedule);
        entity.setTraining(trainingRepository.findById(trainingId).orElseThrow(() -> new TrainingNotFoundException("Training not found")));
        scheduleRepository.save(entity);
        return ScheduleMapper.mapScheduleToScheduleModel(entity);
    }

    @Override
    public ScheduleModel updateSchedule(UpdateScheduleModel scheduleModel, Long scheduleID) {
        var entity = ScheduleMapper.mapUpdateScheduleModelToSchedule(scheduleModel);
        var schedule = scheduleRepository.findById(scheduleID).orElseThrow(() -> new ScheduleNotfoundException("Schedule not found"));
        if(scheduleModel.getScheduleName() != null) {
            schedule.setScheduleName(entity.getScheduleName());
        }
        if(scheduleModel.getScheduleDay() != null) {
            schedule.setScheduleDay(entity.getScheduleDay());
        }
        if(scheduleModel.getScheduleTime() != null) {
            schedule.setScheduleTime(entity.getScheduleTime());
        }
        scheduleRepository.save(schedule);

        return ScheduleMapper.mapScheduleToScheduleModel(schedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
            scheduleRepository.deleteById(scheduleId);
    }
}
