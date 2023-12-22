package com.example.posganize.controllers;

import com.example.posganize.config.TestSecurityConfig;
import com.example.posganize.models.schedule.CreateScheduleModel;
import com.example.posganize.models.schedule.ScheduleModel;
import com.example.posganize.models.schedule.UpdateScheduleModel;
import com.example.posganize.repository.TokenRepository;
import com.example.posganize.services.auth.JwtService;
import com.example.posganize.services.schedule.ScheduleService;
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


import java.util.List;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateScheduleModel createScheduleModel;

    private UpdateScheduleModel updateScheduleModel;

    private ScheduleModel scheduleModel;


    @BeforeEach
    void init() {

        scheduleModel = ScheduleModel
                .builder()
                .id(1L)
                .scheduleName("Advanced group")
                .scheduleDay("Monday")
                .scheduleTime("22:00")
                .build();

        createScheduleModel = CreateScheduleModel
                .builder()
                .scheduleName("Advanced group")
                .scheduleDay("Monday")
                .scheduleTime("22:00")
                .build();

        updateScheduleModel = UpdateScheduleModel
                .builder()
                .scheduleName("Updated group")
                .scheduleDay("Monday")
                .scheduleTime("22:00")
                .build();

    }

    @Test
    void getAllSchedules() throws Exception {
        when(scheduleService.getAllSchedules()).thenReturn(List.of(scheduleModel));
        ResultActions response = mockMvc.perform(get("/api/v1/schedule/all" )
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(List.of(scheduleModel).size()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getSchedule() throws Exception {
        Long scheduleId = 1L;
        when(scheduleService.getScheduleById(scheduleId)).thenReturn(scheduleModel);

        ResultActions response = mockMvc.perform(get("/api/v1/schedule/get/{id}", scheduleId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(scheduleModel.getId()))
                .andExpect(jsonPath("$.scheduleName").value(scheduleModel.getScheduleName()))
                .andExpect(jsonPath("$.scheduleDay").value(scheduleModel.getScheduleDay()))
                .andExpect(jsonPath("$.scheduleTime").value(scheduleModel.getScheduleTime()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void createSchedule() throws Exception {

        when(scheduleService.createSchedule(any(CreateScheduleModel.class)))
                .thenReturn(scheduleModel);

        ResultActions response = mockMvc.perform(post("/api/v1/schedule/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createScheduleModel)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(scheduleModel.getId()))
                .andExpect(jsonPath("$.scheduleName").value(scheduleModel.getScheduleName()))
                .andExpect(jsonPath("$.scheduleDay").value(scheduleModel.getScheduleDay()))
                .andExpect(jsonPath("$.scheduleTime").value(scheduleModel.getScheduleTime()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void testCreateScheduleByTrainingId() {

    }

    @Test
    void updateSchedule() throws Exception {
        Long scheduleId = 1L;
        when(scheduleService.updateSchedule(any(UpdateScheduleModel.class), any(Long.class)))
                .thenReturn(scheduleModel);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/schedule/update/{id}", scheduleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateScheduleModel)));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(scheduleModel.getId()))
                .andExpect(jsonPath("$.scheduleName").value(scheduleModel.getScheduleName()))
                .andExpect(jsonPath("$.scheduleDay").value(scheduleModel.getScheduleDay()))
                .andExpect(jsonPath("$.scheduleTime").value(scheduleModel.getScheduleTime()))
                .andDo(MockMvcResultHandlers.print());
    }



    @Test
    void deleteSchedule() throws Exception {
        Long scheduleId = 1L;
        doNothing().when(scheduleService).deleteSchedule(1L);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/schedule/delete/{id}", scheduleId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk());
    }
}