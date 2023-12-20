package com.example.posganize.mappers;

import com.example.posganize.entities.ClubNews;
import com.example.posganize.models.clubNews.ClubNewsModel;
import com.example.posganize.models.clubNews.CreateClubNewsModel;
import com.example.posganize.models.clubNews.UpdateClubNewsModel;

import java.util.ArrayList;
import java.util.List;

public  class ClubNewsMapper {


    public static ClubNewsModel mapClubNewsToClubNewsModel(ClubNews clubNews) {
       return ClubNewsModel.builder()
                .id(clubNews.getNews_id())
                .title(clubNews.getTitle())
                .description(clubNews.getDescription())
                .image(clubNews.getImage())
                .createdAt(clubNews.getCreatedAt())
                .build();
    }


    public static ClubNews mapClubNewsModelToClubNews(ClubNewsModel clubNews) {
        return ClubNews.builder()
                .news_id(clubNews.getId())
                .title(clubNews.getTitle())
                .description(clubNews.getDescription())
                .image(clubNews.getImage())
                .createdAt(clubNews.getCreatedAt())
                .build();
    }

    public static ClubNews mapCreateClubNewsTOClubNews(CreateClubNewsModel clubNewsModel) {
        return ClubNews
                .builder()
                .title(clubNewsModel.getTitle())
                .description(clubNewsModel.getDescription())
                .image(clubNewsModel.getImage())
                .build();
    }

    public static ClubNews mapUpdateClubNewsTOClubNews(UpdateClubNewsModel clubNewsModel) {
        return ClubNews
                .builder()
                .title(clubNewsModel.getTitle())
                .description(clubNewsModel.getDescription())
                .image(clubNewsModel.getImage())
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
