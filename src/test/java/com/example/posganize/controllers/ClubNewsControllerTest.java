package com.example.posganize.controllers;

import com.example.posganize.config.TestSecurityConfig;
import com.example.posganize.models.clubNews.ClubNewsModel;
import com.example.posganize.models.clubNews.ClubNewsPageableModel;
import com.example.posganize.models.clubNews.CreateClubNewsModel;
import com.example.posganize.models.clubNews.UpdateClubNewsModel;
import com.example.posganize.models.user.UserPageableModel;
import com.example.posganize.repository.TokenRepository;
import com.example.posganize.services.auth.JwtService;
import com.example.posganize.services.clubNews.ClubNewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ClubNewsController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class ClubNewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClubNewsService clubNewsService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ClubNewsModel clubNewsModel;

    private CreateClubNewsModel createClubNewsModel;

    private UpdateClubNewsModel updateClubNewsModel;

    @BeforeEach
    void setUp() {
        clubNewsModel = ClubNewsModel
                .builder()
                .id(1L)
                .image("random image")
                .description("random description")
                .title("News title")
                .createdAt(LocalDateTime.now())
                .author("nikola@nikolic.com")
                .build();

        createClubNewsModel = CreateClubNewsModel
                .builder()
                .image("Random image")
                .description("Random description")
                .title("News title")
                .email("nikola@nikolic.com")
                .build();

        updateClubNewsModel = UpdateClubNewsModel
                .builder()
                .image("Updated random image")
                .description("Updated random description")
                .title("Updated news title")
                .build();
    }

    @Test
    void getAllClubNews() throws Exception {
        when(clubNewsService.getAllClubNews()).thenReturn(List.of(clubNewsModel));
        ResultActions response = mockMvc.perform(get("/api/v1/clubnews/all" )
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(List.of(clubNewsModel).size()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void getAllClubNewsPageable() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        ClubNewsPageableModel clubNewsPageableModel = ClubNewsPageableModel
                .builder()
                .clubNews(List.of(clubNewsModel))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        when(clubNewsService.getAllClubNewsPageable(pageNumber, pageSize)).thenReturn(clubNewsPageableModel);
        ResultActions response = mockMvc.perform(get("/api/v1/clubnews/pageable")
                .contentType(MediaType.APPLICATION_JSON));
        assertNotNull(clubNewsPageableModel);
        response.andExpect(status().isOk());

    }

    @Test
    void getClubNewsById() throws Exception {
        Long clubNewsId = 1L;
        when(clubNewsService.getClubNewsById(clubNewsId)).thenReturn(clubNewsModel);

        ResultActions response = mockMvc.perform(get("/api/v1/clubnews/get/{id}", clubNewsId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clubNewsModel.getId()))
                .andExpect(jsonPath("$.image").value(clubNewsModel.getImage()))
                .andExpect(jsonPath("$.description").value(clubNewsModel.getDescription()))
                .andExpect(jsonPath("$.author").value(clubNewsModel.getAuthor()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void createClubNews() throws Exception {
        when(clubNewsService.createClubNews(any(CreateClubNewsModel.class)))
                .thenReturn(clubNewsModel);

        ResultActions response = mockMvc.perform(post("/api/v1/clubnews/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createClubNewsModel)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clubNewsModel.getId()))
                .andExpect(jsonPath("$.image").value(clubNewsModel.getImage()))
                .andExpect(jsonPath("$.description").value(clubNewsModel.getDescription()))
                .andExpect(jsonPath("$.author").value(clubNewsModel.getAuthor()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateClubNews() throws Exception {
        Long clubNewsId = 1L;
        when(clubNewsService.updateClubNews(any(UpdateClubNewsModel.class), any(Long.class)))
                .thenReturn(clubNewsModel);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/clubnews/update/{id}", clubNewsId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateClubNewsModel)));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clubNewsModel.getId()))
                .andExpect(jsonPath("$.image").value(clubNewsModel.getImage()))
                .andExpect(jsonPath("$.description").value(clubNewsModel.getDescription()))
                .andExpect(jsonPath("$.author").value(clubNewsModel.getAuthor()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteClubNews() throws Exception {
        Long clubNewsId = 1L;
        doNothing().when(clubNewsService).deleteClubNews(1L);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clubnews/delete/{id}", clubNewsId )
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk());

    }
}