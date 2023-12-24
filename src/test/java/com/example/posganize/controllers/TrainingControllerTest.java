package com.example.posganize.controllers;

import com.example.posganize.config.TestSecurityConfig;
import com.example.posganize.models.training.CreateTrainingModel;
import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.models.training.UpdateTrainingModel;
import com.example.posganize.repository.TokenRepository;
import com.example.posganize.services.auth.JwtService;
import com.example.posganize.services.training.TrainingService;
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

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TrainingController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingService trainingService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private TrainingModel trainingModel;

    private CreateTrainingModel createTrainingModel;

    private UpdateTrainingModel updateTrainingModel;

    private  List<Map<String, Long>> expectedResponse;
    @BeforeEach
    void setUp() {

        expectedResponse = new ArrayList<>();


        trainingModel = TrainingModel
                .builder()
                .id(1L)
                .name("MMA")
                .price(5000.00)
                .image("random image")
                .description("random description")
                .build();

        updateTrainingModel = UpdateTrainingModel
                .builder()
                .name("Basketball")
                .price(3000.00)
                .image("random image")
                .description("random description")
                .build();

        createTrainingModel = CreateTrainingModel
                .builder()
                .name("MMA")
                .price(5000.00)
                .image("random image")
                .description("random description")
                .schedule(List.of())
                .build();
    }



    @Test
    void getAllTrainings() throws Exception {
        when(trainingService.getAllTraining()).thenReturn(Set.of(trainingModel));
        ResultActions response = mockMvc.perform(get("/api/v1/training/all" )
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(List.of(trainingModel).size()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getUsersCountPerTraining() throws Exception {

        Map<String, Long> trainingData = new HashMap<>();
        trainingData.put("training",  1L);
        trainingData.put("numberOfMembers", 10L);
        expectedResponse.add(trainingData);

        when(trainingService.getUserCountPerTraining(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(get("/api/v1/training/users-per-training")
                        .param("fromDate", "2023-01-01")
                        .param("toDate", "2023-12-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].training").value(1L))
                .andExpect(jsonPath("$[0].numberOfMembers").value(10));

    }

    @Test
    void getTrainingById() throws Exception {
        Long trainingId = 1L;
        when(trainingService.getTrainingById(trainingId)).thenReturn(trainingModel);

        ResultActions response = mockMvc.perform(get("/api/v1/training/get/{id}", trainingId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trainingModel.getId()))
                .andExpect(jsonPath("$.name").value(trainingModel.getName()))
                .andExpect(jsonPath("$.price").value(trainingModel.getPrice()))
                .andExpect(jsonPath("$.image").value(trainingModel.getImage()))
                .andExpect(jsonPath("$.description").value(trainingModel.getDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void createTraining() throws Exception {
        when(trainingService.createTraining(any(CreateTrainingModel.class)))
                .thenReturn(trainingModel);

        ResultActions response = mockMvc.perform(post("/api/v1/training/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTrainingModel)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trainingModel.getId()))
                .andExpect(jsonPath("$.name").value(trainingModel.getName()))
                .andExpect(jsonPath("$.price").value(trainingModel.getPrice()))
                .andExpect(jsonPath("$.image").value(trainingModel.getImage()))
                .andExpect(jsonPath("$.description").value(trainingModel.getDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateTraining() throws Exception {
        Long trainingId = 1L;
        when(trainingService.updateTraining(any(UpdateTrainingModel.class), any(Long.class)))
                .thenReturn(trainingModel);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/training/update/{id}", trainingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateTrainingModel)));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trainingModel.getId()))
                .andExpect(jsonPath("$.name").value(trainingModel.getName()))
                .andExpect(jsonPath("$.price").value(trainingModel.getPrice()))
                .andExpect(jsonPath("$.image").value(trainingModel.getImage()))
                .andExpect(jsonPath("$.description").value(trainingModel.getDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteTraining() throws Exception {
        Long trainingId = 1L;
        doNothing().when(trainingService).deleteTraining(1L);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/training/delete/{id}", trainingId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk());
    }
}