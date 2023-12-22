package com.example.posganize.services.schedule;

import com.example.posganize.entities.Schedule;
import com.example.posganize.mappers.ScheduleMapper;
import com.example.posganize.models.schedule.CreateScheduleModel;
import com.example.posganize.models.schedule.ScheduleModel;
import com.example.posganize.models.schedule.UpdateScheduleModel;
import com.example.posganize.repository.ScheduleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        Schedule schedule1 = Schedule
                .builder()
                .schedule_id(1L)
                .scheduleName("Advanced group")
                .scheduleDay("Monday")
                .scheduleTime("22:00")
                .build();

        Schedule schedule2 = Schedule
                .builder()
                .schedule_id(2L)
                .scheduleName("Beginner group")
                .scheduleDay("Friday")
                .scheduleTime("21:00")
                .build();

        //findAll
        Mockito.when(scheduleRepository.findAll()).thenReturn(List.of(schedule1 , schedule2));

        //findById
        Mockito.when(scheduleRepository.findById(1L))
                .thenReturn(Optional.of(schedule1));

        //Create
        Mockito.when(scheduleRepository.save(Mockito.any(Schedule.class)))
                .thenAnswer(invocation -> {
                    Schedule createdSchedule = invocation.getArgument(0);
                    createdSchedule.setSchedule_id(1L);
                    return createdSchedule;
                });


    }

    @Test
    void getAllSchedules() {
        Schedule schedule1 = Schedule
                .builder()
                .schedule_id(1L)
                .scheduleName("Advanced group")
                .scheduleDay("Monday")
                .scheduleTime("22:00")
                .build();

        Schedule schedule2 = Schedule
                .builder()
                .schedule_id(2L)
                .scheduleName("Beginner group")
                .scheduleDay("Friday")
                .scheduleTime("21:00")
                .build();

        List<ScheduleModel> expected = ScheduleMapper.mapScheduleListToScheduleModelList(List.of(schedule1, schedule2));
        List<ScheduleModel> actual = scheduleService.getAllSchedules();
        assertEquals(expected, actual);
    }

    @Test
    void getScheduleById() {
        Long scheduleId = 1L;
        ScheduleModel found = scheduleService.getScheduleById(scheduleId);
        assertEquals(scheduleId, found.getId());
    }

    @Test
    void createSchedule() {
        CreateScheduleModel schedule = CreateScheduleModel
                .builder()
                .scheduleName("Advanced group")
                .scheduleDay("Monday")
                .scheduleTime("22:00")
                .build();

        ScheduleModel createdSchedule = scheduleService.createSchedule(schedule);

        assertNotNull(createdSchedule.getId());
        assertEquals("Advanced group", createdSchedule.getScheduleName());
        assertEquals("Monday", createdSchedule.getScheduleDay());
        assertEquals("22:00", createdSchedule.getScheduleTime());


    }

    @Test
    void createScheduleByTrainingId() {
    }

    @Test
    void updateSchedule() {
        Long scheduleId = 1L;
        UpdateScheduleModel updateSchedule = UpdateScheduleModel
                .builder()
                .scheduleName("Updated Group")
                .scheduleDay("Wednesday")
                .scheduleTime("15:00")
                .build();

        ScheduleModel updatedSchedule = scheduleService.updateSchedule(updateSchedule, scheduleId);

        assertEquals(scheduleId, updatedSchedule.getId());
        assertEquals("Updated Group", updatedSchedule.getScheduleName());
        assertEquals("Wednesday", updatedSchedule.getScheduleDay());
        assertEquals("15:00", updatedSchedule.getScheduleTime());

    }

    @Test
    void deleteSchedule() {
        Long scheduleId = 1L;
        scheduleService.deleteSchedule(scheduleId);
        Mockito.verify(scheduleRepository, Mockito.times(1)).deleteById(scheduleId);
    }
}