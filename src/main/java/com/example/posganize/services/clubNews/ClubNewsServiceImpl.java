package com.example.posganize.services.clubNews;
import com.example.posganize.mappers.ClubNewsMapper;
import com.example.posganize.models.clubNews.ClubNewsModel;
import com.example.posganize.models.clubNews.CreateClubNewsModel;
import com.example.posganize.models.clubNews.UpdateClubNewsModel;
import com.example.posganize.repository.ClubNewsRepository;
import com.example.posganize.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClubNewsServiceImpl implements ClubNewsService {

    private final ClubNewsRepository clubNewsRepository;

    private final UsersRepository usersRepository;

    public ClubNewsServiceImpl(ClubNewsRepository clubNewsRepository, UsersRepository usersRepository) {
        this.clubNewsRepository = clubNewsRepository;
        this.usersRepository = usersRepository;
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
    public ClubNewsModel createClubNews(CreateClubNewsModel clubNews) {
        var entity = ClubNewsMapper.mapCreateClubNewsTOClubNews(clubNews);
        var user = usersRepository.findByEmail(clubNews.getEmail()).orElseThrow(() -> new NullPointerException("News not found"));
        entity.setUser(user);
        entity.setCreatedAt(LocalDateTime.now());
        clubNewsRepository.save(entity);
        return ClubNewsMapper.mapClubNewsToClubNewsModel(entity);
    }

    @Override
    public ClubNewsModel updateClubNews(UpdateClubNewsModel clubNewsModel, Long newsId) {
        var clubNews = clubNewsRepository.findById(newsId).orElseThrow(() -> new NullPointerException("News not found"));
        if(clubNewsModel.getTitle() != null) {
            clubNews.setTitle(clubNewsModel.getTitle());
        }
        if(clubNewsModel.getDescription() != null) {
            clubNews.setDescription(clubNewsModel.getDescription());
        }
        if(clubNewsModel.getImage() != null) {
            clubNews.setImage(clubNewsModel.getImage());
        }
        clubNewsRepository.save(clubNews);
        return ClubNewsMapper.mapClubNewsToClubNewsModel(clubNews);
    }


    @Override
    public void deleteClubNews(Long newsId) {
         clubNewsRepository.deleteById(newsId);
    }
}
