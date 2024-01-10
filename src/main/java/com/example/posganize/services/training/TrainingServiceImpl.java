package com.example.posganize.services.training;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Schedule;
import com.example.posganize.entities.Training;
import com.example.posganize.enums.CurrencyEnum;
import com.example.posganize.exceptions.TrainingNotFoundException;
import com.example.posganize.exceptions.UserNotFoundException;
import com.example.posganize.mappers.ScheduleMapper;
import com.example.posganize.mappers.TrainingMapper;
import com.example.posganize.models.schedule.ScheduleModel;
import com.example.posganize.models.training.CreateTrainingModel;
import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.models.training.UpdateTrainingModel;
import com.example.posganize.repository.ScheduleRepository;
import com.example.posganize.repository.TrainingRepository;
import com.example.posganize.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService{

    private final TrainingRepository trainingRepository;

    private final UsersRepository usersRepository;

    private final ScheduleRepository scheduleRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository, UsersRepository usersRepository, ScheduleRepository scheduleRepository) {
        this.trainingRepository = trainingRepository;
        this.usersRepository = usersRepository;
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
    public TrainingModel createTraining(CreateTrainingModel training) {
        var trainingEntity = TrainingMapper.mapCreateTrainingModelToTraining(training);
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
        trainingEntity.setCurrency(CurrencyEnum.USD);
        trainingRepository.save(trainingEntity);
        return TrainingMapper.mapTrainingToTrainingModel(trainingEntity);
    }

    @Override
    public TrainingModel updateTraining(UpdateTrainingModel training, Long trainingId) {
        var entity = TrainingMapper.mapUpdateTrainingModelToTraining(training);
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
    public Map<String, Boolean> checkDuplicateTraining(Long trainingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<Long> userTrainingIds = user.getMemberships().stream()
                .filter(Membership::getActive)
                .flatMap(membership -> membership.getTrainings().stream())
                .map(Training::getTraining_id)
                .collect(Collectors.toSet());

        boolean isDuplicate = userTrainingIds.contains(trainingId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }



    @Override
    public List<Map<String, Long>> getUserCountPerTraining(LocalDate fromDate, LocalDate toDate) {
        return trainingRepository.countUsersByTraining(fromDate, toDate);
    }
}
