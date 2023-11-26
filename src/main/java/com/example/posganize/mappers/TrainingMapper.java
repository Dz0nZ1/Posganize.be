package com.example.posganize.mappers;

import com.example.posganize.entities.Training;
import com.example.posganize.models.TrainingModel;

import java.util.HashSet;
import java.util.Set;

public class TrainingMapper {

    public static TrainingModel mapTrainingToTrainingModel(Training training) {
        var model = TrainingModel.builder()
                .name(training.getName())
                .price(training.getPrice())
                .build();
        if(training.getSchedules() != null) model.setSchedule(ScheduleMapper.mapScheduleListToScheduleModelList(training.getSchedules()));
        else model.setSchedule(null);
        return model;
    }


    public static Training mapTrainingModelToTraining(TrainingModel training) {
        var entity =  Training.builder()
                .name(training.getName())
                .price(training.getPrice())
                .build();
        if(training.getSchedule() != null) entity.setSchedules((ScheduleMapper.mapScheduleListModelToScheduleList(training.getSchedule())));
        else entity.setSchedules(null);
        return entity;
    }


    public static Set<TrainingModel> mapTrainingSetToTrainingModelSet(Set<Training> training) {
        Set<TrainingModel> modelSet = new HashSet<>();
        for (Training entity : training) {
            modelSet.add(mapTrainingToTrainingModel(entity));
        }
        return modelSet;

    }

    public static Set<Training> mapTrainingModelSetToTrainingSet(Set<TrainingModel> training) {
        Set<Training> modelSet = new HashSet<>();
        for (TrainingModel model : training) {
            modelSet.add(mapTrainingModelToTraining(model));
        }
        return modelSet;

    }

}
