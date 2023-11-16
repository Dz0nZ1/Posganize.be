package com.example.posganize.services.ClubNews;


import com.example.posganize.models.ClubNewsModel;

import java.util.List;

public interface ClubNewsService {

    List<ClubNewsModel> getAllClubNews();

    ClubNewsModel getClubNewsById(Long newsId);

    ClubNewsModel createClubNews(ClubNewsModel clubNews);

    ClubNewsModel updateClubNews(ClubNewsModel clubNews, Long newsId);
    void deleteClubNews(Long newsId);

}
