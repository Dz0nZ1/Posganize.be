package com.example.posganize.services.schedule;

import com.example.posganize.exceptions.ScheduleNotfoundException;
import com.example.posganize.exceptions.TrainingNotFoundException;
import com.example.posganize.mappers.ScheduleMapper;
import com.example.posganize.models.ScheduleModel;
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
    public ScheduleModel createSchedule(ScheduleModel schedule) {
        var entity = ScheduleMapper.mapScheduleModelToSchedule(schedule);
        scheduleRepository.save(entity);
        return ScheduleMapper.mapScheduleToScheduleModel(entity);
    }

    @Override
    public ScheduleModel createScheduleByTrainingId(ScheduleModel schedule, Long trainingId) {
        var entity = ScheduleMapper.mapScheduleModelToSchedule(schedule);
        entity.setTraining(trainingRepository.findById(trainingId).orElseThrow(() -> new TrainingNotFoundException("Training not found")));
        scheduleRepository.save(entity);
        return ScheduleMapper.mapScheduleToScheduleModel(entity);
    }

    @Override
    public ScheduleModel updateSchedule(ScheduleModel schedule, Long scheduleID) {
        var entity = ScheduleMapper.mapScheduleModelToSchedule(schedule);
        var newSchedule = scheduleRepository.findById(scheduleID).orElseThrow(() -> new ScheduleNotfoundException("Schedule not found"));
        newSchedule.setScheduleName(entity.getScheduleName());
        newSchedule.setScheduleDay(entity.getScheduleDay());
        newSchedule.setScheduleTime(entity.getScheduleTime());
        scheduleRepository.save(newSchedule);

        return ScheduleMapper.mapScheduleToScheduleModel(newSchedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
            scheduleRepository.deleteById(scheduleId);
    }
}
