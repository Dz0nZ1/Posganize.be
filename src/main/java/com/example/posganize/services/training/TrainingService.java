package com.example.posganize.services.training;

import com.example.posganize.models.TrainingModel;

import java.util.List;

public interface TrainingService {


    List<TrainingModel> getAllTraining();

    TrainingModel getTrainingById(Long trainingId);

    TrainingModel createTraining(TrainingModel training);

    TrainingModel updateTraining(TrainingModel training, Long trainingId);
    void deleteTraining(Long trainingId);
}
