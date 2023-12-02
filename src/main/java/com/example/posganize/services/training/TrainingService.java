package com.example.posganize.services.training;

import com.example.posganize.models.TrainingModel;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TrainingService {


    Set<TrainingModel> getAllTraining();

    TrainingModel getTrainingById(Long trainingId);

    TrainingModel createTraining(TrainingModel training);

    TrainingModel updateTraining(TrainingModel training, Long trainingId);
    void deleteTraining(Long trainingId);

    List<Map<String, Long>> getUserCountPerTraining(LocalDate fromDate, LocalDate toDate);
}
