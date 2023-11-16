package com.example.posganize.mappers;

import com.example.posganize.entities.ClubNews;
import com.example.posganize.models.ClubNewsModel;

import java.util.ArrayList;
import java.util.List;

public  class ClubNewsMapper {


    public static ClubNewsModel mapClubNewsToClubNewsModel(ClubNews clubNews) {
       return ClubNewsModel.builder()
                .name(clubNews.getName())
                .description(clubNews.getDescription())
                .build();
    }


    public static ClubNews mapClubNewsModelToClubNews(ClubNewsModel clubNews) {
        return ClubNews.builder()
                .name(clubNews.getName())
                .description(clubNews.getDescription())
                .build();
    }


    public static List<ClubNewsModel> mapClubNewsListToClubNewsModelList(List<ClubNews> clubNews) {
        List<ClubNewsModel> modelList = new ArrayList<>();
        for (ClubNews entity : clubNews) {
        modelList.add(mapClubNewsToClubNewsModel(entity));
    }
        return modelList;

    }

}
