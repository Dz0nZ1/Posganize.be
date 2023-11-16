package com.example.posganize.services.clubNews;
import com.example.posganize.mappers.ClubNewsMapper;
import com.example.posganize.models.ClubNewsModel;
import com.example.posganize.repository.ClubNewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubNewsServiceImpl implements ClubNewsService {

    private final ClubNewsRepository clubNewsRepository;

    public ClubNewsServiceImpl(ClubNewsRepository clubNewsRepository) {
        this.clubNewsRepository = clubNewsRepository;
    }

    @Override
    public List<ClubNewsModel> getAllClubNews() {
        return ClubNewsMapper.mapClubNewsListToClubNewsModelList(clubNewsRepository.findAll());
    }

    @Override
    public ClubNewsModel getClubNewsById(Long newsId) {
        return ClubNewsMapper.mapClubNewsToClubNewsModel(clubNewsRepository.findById(newsId).orElseThrow(() -> new NullPointerException("News not found")));
    }

    @Override
    public ClubNewsModel createClubNews(ClubNewsModel clubNews) {
        var entity = ClubNewsMapper.mapClubNewsModelToClubNews(clubNews);
        clubNewsRepository.save(entity);
        return ClubNewsMapper.mapClubNewsToClubNewsModel(entity);
    }

    @Override
    public ClubNewsModel updateClubNews(ClubNewsModel clubNews, Long newsId) {
        var entity = ClubNewsMapper.mapClubNewsModelToClubNews(clubNews);
        var newClubNews = clubNewsRepository.findById(newsId).orElseThrow(() -> new NullPointerException("News not found"));
        if (entity.getName()!=null)
            newClubNews.setName(entity.getName());
        if (entity.getDescription()!=null)
            newClubNews.setDescription(entity.getDescription());

        clubNewsRepository.save(newClubNews);
        return ClubNewsMapper.mapClubNewsToClubNewsModel(newClubNews);
    }


    @Override
    public void deleteClubNews(Long newsId) {
         clubNewsRepository.deleteById(newsId);
    }
}
