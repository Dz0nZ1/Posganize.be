package com.example.posganize.services.schedule;

import com.example.posganize.exceptions.ScheduleNotfoundException;
import com.example.posganize.mappers.ScheduleMapper;
import com.example.posganize.models.ScheduleModel;
import com.example.posganize.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
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
    public ScheduleModel updateSchedule(ScheduleModel schedule, Long scheduleID) {
        var entity = ScheduleMapper.mapScheduleModelToSchedule(schedule);
        var newSchedule = scheduleRepository.findById(scheduleID).orElseThrow(() -> new ScheduleNotfoundException("Schedule not found"));
        newSchedule.setName(entity.getName());
        newSchedule.setDay(entity.getDay());
        newSchedule.setTime(entity.getTime());
        scheduleRepository.save(newSchedule);

        return ScheduleMapper.mapScheduleToScheduleModel(newSchedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
            scheduleRepository.deleteById(scheduleId);
    }
}
