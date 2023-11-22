package com.example.posganize.mappers;

import com.example.posganize.entities.Training;
import com.example.posganize.models.TrainingModel;

import java.util.ArrayList;
import java.util.List;

public class TrainingMapper {

    public static TrainingModel mapTrainingToTrainingModel(Training training) {
        return TrainingModel.builder()
                .schedule(ScheduleMapper.mapScheduleListToScheduleModelList(training.getSchedule()))
                .name(training.getName())
                .price(training.getPrice())
                .build();
    }


    public static Training mapTrainingModelToTraining(TrainingModel training) {
        return Training.builder()
                .schedule(ScheduleMapper.mapScheduleListModelToScheduleList(training.getSchedule()))
                .name(training.getName())
                .price(training.getPrice())
                .build();
    }


    public static List<TrainingModel> mapTrainingListToTrainingModelList(List<Training> training) {
        List<TrainingModel> modelList = new ArrayList<>();
        for (Training entity : training) {
            modelList.add(mapTrainingToTrainingModel(entity));
        }
        return modelList;

    }

    public static List<Training> mapTrainingModelListToTrainingList(List<TrainingModel> training) {
        List<Training> modelList = new ArrayList<>();
        for (TrainingModel model : training) {
            modelList.add(mapTrainingModelToTraining(model));
        }
        return modelList;

    }

}
