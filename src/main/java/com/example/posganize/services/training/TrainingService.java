package com.example.posganize.services.training;

import com.example.posganize.models.training.CreateTrainingModel;
import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.models.training.UpdateTrainingModel;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TrainingService {


    Set<TrainingModel> getAllTraining();

    TrainingModel getTrainingById(Long trainingId);

    TrainingModel createTraining(CreateTrainingModel training);

    TrainingModel updateTraining(UpdateTrainingModel training, Long trainingId);
    void deleteTraining(Long trainingId);

    List<Map<String, Long>> getUserCountPerTraining(LocalDate fromDate, LocalDate toDate);
}
