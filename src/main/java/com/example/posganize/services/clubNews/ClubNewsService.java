package com.example.posganize.services.clubNews;


import com.example.posganize.models.clubNews.ClubNewsModel;
import com.example.posganize.models.clubNews.ClubNewsPageableModel;
import com.example.posganize.models.clubNews.CreateClubNewsModel;
import com.example.posganize.models.clubNews.UpdateClubNewsModel;

import java.util.List;

public interface ClubNewsService {

    List<ClubNewsModel> getAllClubNews();

    ClubNewsPageableModel getAllClubNewsPageable(int pageNumber, int pageSize);

    ClubNewsModel getClubNewsById(Long newsId);

    ClubNewsModel createClubNews(CreateClubNewsModel clubNews);

    ClubNewsModel updateClubNews(UpdateClubNewsModel clubNews, Long newsId);
    void deleteClubNews(Long newsId);

}
