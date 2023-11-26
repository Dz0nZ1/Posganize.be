package com.example.posganize.services.training;

import com.example.posganize.exceptions.TrainingNotFoundException;
import com.example.posganize.mappers.TrainingMapper;
import com.example.posganize.models.TrainingModel;
import com.example.posganize.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TrainingServiceImpl implements TrainingService{

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
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
        var entity = TrainingMapper.mapTrainingModelToTraining(training);
        trainingRepository.save(entity);
        return TrainingMapper.mapTrainingToTrainingModel(entity);
    }

    @Override
    public TrainingModel updateTraining(TrainingModel training, Long trainingId) {
        var entity = TrainingMapper.mapTrainingModelToTraining(training);
        var newTraining = trainingRepository.findById(trainingId).orElseThrow(() -> new TrainingNotFoundException("Training not found"));
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
}
