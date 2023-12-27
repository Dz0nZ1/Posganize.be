package com.example.posganize.mappers;

import com.example.posganize.entities.Training;
import com.example.posganize.models.training.CreateTrainingModel;
import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.models.training.UpdateTrainingModel;

import java.util.HashSet;
import java.util.Set;

public class TrainingMapper {

    public static TrainingModel mapTrainingToTrainingModel(Training training) {
        var model = TrainingModel.builder()
                .id(training.getTraining_id())
                .name(training.getName())
                .image(training.getImage())
                .description(training.getDescription())
                .price(training.getPrice())
                .currency(training.getCurrency())
                .build();
        if(training.getSchedules() != null) model.setSchedule(ScheduleMapper.mapScheduleListToScheduleModelList(training.getSchedules()));
        else model.setSchedule(null);
        return model;
    }


    public static Training mapTrainingModelToTraining(TrainingModel training) {
        var entity =  Training.builder()
                .training_id(training.getId())
                .name(training.getName())
                .description(training.getDescription())
                .image(training.getImage())
                .price(training.getPrice())
                .currency(training.getCurrency())
                .build();
        if(training.getSchedule() != null) entity.setSchedules((ScheduleMapper.mapScheduleListModelToScheduleList(training.getSchedule())));
        else entity.setSchedules(null);
        return entity;
    }

    public static Training mapCreateTrainingModelToTraining (CreateTrainingModel createTrainingModel) {
        return Training
                .builder()
                .name(createTrainingModel.getName())
                .description(createTrainingModel.getDescription())
                .image(createTrainingModel.getImage())
                .schedules(ScheduleMapper.mapScheduleListModelToScheduleList(createTrainingModel.getSchedule()))
                .price(createTrainingModel.getPrice())
                .build();
    }

    public static Training mapUpdateTrainingModelToTraining (UpdateTrainingModel createTrainingModel) {
        return Training
                .builder()
                .name(createTrainingModel.getName())
                .description(createTrainingModel.getDescription())
                .image(createTrainingModel.getImage())
                .price(createTrainingModel.getPrice())
                .build();
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
