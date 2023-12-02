package com.example.posganize.services.training;

import com.example.posganize.entities.Schedule;
import com.example.posganize.exceptions.TrainingNotFoundException;
import com.example.posganize.mappers.ScheduleMapper;
import com.example.posganize.mappers.TrainingMapper;
import com.example.posganize.models.ScheduleModel;
import com.example.posganize.models.TrainingModel;
import com.example.posganize.repository.ScheduleRepository;
import com.example.posganize.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService{

    private final TrainingRepository trainingRepository;

    private final ScheduleRepository scheduleRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository, ScheduleRepository scheduleRepository) {
        this.trainingRepository = trainingRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Set<TrainingModel> getAllTraining() {
        return TrainingMapper.mapTrainingSetToTrainingModelSet(trainingRepository.findAllSets());
    }

    @Override
    public TrainingModel getTrainingById(Long trainingId) {
        return TrainingMapper.mapTrainingToTrainingModel(trainingRepository.findById(trainingId).orElseThrow(() -> new TrainingNotFoundException("Training not found")));
    }

    @Override
    public TrainingModel createTraining(TrainingModel training) {
        var trainingEntity = TrainingMapper.mapTrainingModelToTraining(training);
        trainingRepository.save(trainingEntity);
        List<ScheduleModel> scheduleModels = training.getSchedule();
        List<Schedule> schedules = scheduleModels.stream()
                .map(scheduleModel -> {
                    Schedule schedule = ScheduleMapper.mapScheduleModelToSchedule(scheduleModel);
                    schedule.setTraining(trainingEntity);
                    scheduleRepository.save(schedule);
                    return schedule;
                })
                .collect(Collectors.toList());
        trainingEntity.setSchedules(schedules);
        trainingRepository.save(trainingEntity);
        return TrainingMapper.mapTrainingToTrainingModel(trainingEntity);
    }

    @Override
    public TrainingModel updateTraining(TrainingModel training, Long trainingId) {
        var entity = TrainingMapper.mapTrainingModelToTraining(training);
        var newTraining = trainingRepository.findById(trainingId).orElseThrow(() -> new TrainingNotFoundException("Training not found"));

        if(entity.getDescription() != null) {
            newTraining.setDescription(entity.getDescription());
        }
        if(entity.getImage() != null) {
            newTraining.setImage(entity.getImage());
        }
        if (entity.getSchedules()!=null)
            newTraining.setSchedules(entity.getSchedules());
        if (entity.getName()!=null)
            newTraining.setName(entity.getName());
        if (entity.getPrice()!=null)
            newTraining.setPrice(entity.getPrice());
        trainingRepository.save(newTraining);
        return TrainingMapper.mapTrainingToTrainingModel(newTraining);
    }

    @Override
    public void deleteTraining(Long trainingId) {
        trainingRepository.deleteById(trainingId);

    }

    @Override
    public List<Map<String, Long>> getUserCountPerTraining(LocalDate fromDate, LocalDate toDate) {
        return trainingRepository.countUsersByTraining(fromDate, toDate);
    }
}
