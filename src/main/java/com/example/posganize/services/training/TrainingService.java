package com.example.posganize.services.training;

import com.example.posganize.models.TrainingModel;


import java.util.Set;

public interface TrainingService {


    Set<TrainingModel> getAllTraining();

    TrainingModel getTrainingById(Long trainingId);

    TrainingModel createTraining(TrainingModel training);

    TrainingModel updateTraining(TrainingModel training, Long trainingId);
    void deleteTraining(Long trainingId);
}
