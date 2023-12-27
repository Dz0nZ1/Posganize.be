package com.example.posganize.services.clubNews;

import com.example.posganize.entities.ClubNews;
import com.example.posganize.entities.Users;
import com.example.posganize.enums.RoleEnum;
import com.example.posganize.mappers.ClubNewsMapper;
import com.example.posganize.models.clubNews.ClubNewsModel;
import com.example.posganize.models.clubNews.ClubNewsPageableModel;
import com.example.posganize.models.clubNews.CreateClubNewsModel;
import com.example.posganize.models.clubNews.UpdateClubNewsModel;
import com.example.posganize.repository.ClubNewsRepository;
import com.example.posganize.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClubNewsServiceTest {


    @Autowired
    private ClubNewsService clubNewsService;

    @MockBean
    private ClubNewsRepository clubNewsRepository;

    @MockBean
    private UsersRepository usersRepository;

    private ClubNews clubNews;

    private Users user;

    @BeforeEach
    void setUp() {

        user = Users
                .builder()
                .user_id(1L)
                .firstName("Nikola")
                .lastName("Nikolic")
                .email("nikola@email.com")
                .password("123456789")
                .role(RoleEnum.USER)
                .trailTraining(false)
                .phoneNumber("30434355")
                .build();

        clubNews = ClubNews
                .builder()
                .news_id(1L)
                .image("random image")
                .description("random description")
                .title("News title")
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Page<ClubNews> mockPage = new PageImpl<>(List.of(clubNews));

        Mockito.when(clubNewsRepository.findAll(Mockito.any(Pageable.class))).thenReturn(mockPage);
        Mockito.when(clubNewsRepository.findAll()).thenReturn(List.of(clubNews));
        Mockito.when(clubNewsRepository.findById(clubNews.getNews_id())).thenReturn(Optional.of(clubNews));
        Mockito.when(clubNewsRepository.save(Mockito.any(ClubNews.class))).thenAnswer(invocation -> {
            ClubNews createdClubNews = invocation.getArgument(0);
            createdClubNews.setNews_id(1L);
            return createdClubNews;
        });

        Mockito.when(usersRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));


    }

    @Test
    void getAllClubNews() {
        List<ClubNewsModel> clubNewsModel = clubNewsService.getAllClubNews();
        List<ClubNewsModel> dummyList = ClubNewsMapper.mapClubNewsListToClubNewsModelList(List.of(clubNews));
        assertNotNull(clubNewsModel);
        assertEquals(dummyList, clubNewsModel);

    }

    @Test
    void getAllClubNewsPageable() {
        int pageNumber = 0;
        int pageSize = 10;
        ClubNewsPageableModel result = clubNewsService.getAllClubNewsPageable(pageNumber, pageSize);
        assertNotNull(result);
        assertEquals(pageNumber, result.getPageNumber());
        assertEquals(pageSize, result.getPageSize());
    }

    @Test
    void getClubNewsById() {
        Long clubNewsId = 1L;
        ClubNewsModel found = clubNewsService.getClubNewsById(clubNewsId);
        assertEquals(clubNewsId, found.getId());
    }

    @Test
    void createClubNews() {
        CreateClubNewsModel clubNewsModel = CreateClubNewsModel
                .builder()
                .image("Random image")
                .description("Random description")
                .title("News title")
                .email(user.getEmail())
                .build();

        ClubNewsModel createdClubNews = clubNewsService.createClubNews(clubNewsModel);

        assertNotNull(createdClubNews.getId());
        assertEquals("Random image", createdClubNews.getImage());
        assertEquals("Random description", createdClubNews.getDescription());
        assertEquals("News title", createdClubNews.getTitle());
    }

    @Test
    void updateClubNews() {
        Long clubNewsId = 1L;
       UpdateClubNewsModel clubNewsModel = UpdateClubNewsModel
                .builder()
                .image("Updated random image")
                .description("Updated random description")
                .title("Updated news title")
                .build();

        ClubNewsModel createdClubNews = clubNewsService.updateClubNews(clubNewsModel, clubNewsId);

        assertNotNull(createdClubNews.getId());
        assertEquals("Updated random image", createdClubNews.getImage());
        assertEquals("Updated random description", createdClubNews.getDescription());
        assertEquals("Updated news title", createdClubNews.getTitle());

    }

    @Test
    void deleteClubNews() {
        Long clubNewsId = 1L;
        clubNewsService.deleteClubNews(clubNewsId);
        Mockito.verify(clubNewsRepository, Mockito.times(1)).deleteById(clubNewsId);
    }
}