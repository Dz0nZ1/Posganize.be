package com.example.posganize.repository;

import com.example.posganize.entities.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ScheduleRepositoryTest {

    @BeforeEach
    void setUp() {
    }

    @Autowired
    private ScheduleRepository scheduleRepository;


    @Test
    public void ScheduleRepository_Save_ReturnsSavedSchedule() {

        //Arrange
        Schedule schedule = Schedule
                .builder()
                .scheduleName("Advanced group")
                .scheduleDay("Monday")
                .scheduleTime("22:00")
                .build();

        //Act
        Schedule savedSchedule = scheduleRepository.save(schedule);


        //Assert
        assertNotNull(savedSchedule);
        assertNotNull(savedSchedule.getSchedule_id());
        assertEquals("Advanced group", savedSchedule.getScheduleName());
        assertEquals("Monday", savedSchedule.getScheduleDay());
        assertEquals("22:00", savedSchedule.getScheduleTime());


    }

    @Test
    public void ScheduleRepository_Update_ReturnsUpdatedSchedule(){
        Schedule schedule = Schedule
                .builder()
                .scheduleName("Advanced group")
                .scheduleDay("Monday")
                .scheduleTime("22:00")
                .build();

        //Act
        Schedule savedSchedule = scheduleRepository.save(schedule);


        Long scheduleId = savedSchedule.getSchedule_id();
        Schedule retrievedSchedule = scheduleRepository.findById(scheduleId).orElse(null);
        assert retrievedSchedule != null;
        assertNotNull(retrievedSchedule);

        retrievedSchedule.setScheduleName("Beginner Group");
        retrievedSchedule.setScheduleDay("Friday");
        retrievedSchedule.setScheduleTime("21:00");

        Schedule updatedSchedule = scheduleRepository.save(retrievedSchedule);

        Schedule retrievedUpdatedSchedule = scheduleRepository.findById(scheduleId).orElse(null);
        assert retrievedUpdatedSchedule != null;
        assertNotNull(retrievedUpdatedSchedule);

        assertEquals("Beginner Group", retrievedSchedule.getScheduleName());
        assertEquals("Friday", retrievedSchedule.getScheduleDay());
        assertEquals("21:00", retrievedSchedule.getScheduleTime());

    }

    @Test
    public void ScheduleRepository_Delete_ReturnsNull(){
        Schedule schedule = new Schedule();
        schedule.setScheduleName("Advanced Group");
        schedule.setScheduleDay("Monday");
        schedule.setScheduleTime("22:00");


        Schedule savedSchedule = scheduleRepository.save(schedule);


        Long scheduleId = savedSchedule.getSchedule_id();
        Schedule retrievedSchedule = scheduleRepository.findById(scheduleId).orElse(null);
        assert retrievedSchedule != null;
        assertNotNull(retrievedSchedule);


        scheduleRepository.delete(retrievedSchedule);


        Schedule deletedSchedule = scheduleRepository.findById(scheduleId).orElse(null);


        assertNull(deletedSchedule);
    }

    @Test
    public void ScheduleRepository_findAll_ReturnsScheduleList() {

        Schedule schedule1 = new Schedule();
        schedule1.setScheduleName("Advanced Group");
        schedule1.setScheduleDay("Monday");
        schedule1.setScheduleTime("22:00");


        Schedule schedule2 = new Schedule();
        schedule2.setScheduleName("Beginner Group");
        schedule2.setScheduleDay("Friday");
        schedule2.setScheduleTime("21:00");


        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);

        List<Schedule> allSchedules = scheduleRepository.findAll();

        assertNotNull(allSchedules);
        assertEquals(2, allSchedules.size());

    }

    @Test
    public void ScheduleRepository_findById_ReturnsSchedule() {

        Schedule schedule = new Schedule();
        schedule.setScheduleName("Advanced Group");
        schedule.setScheduleDay("Monday");
        schedule.setScheduleTime("22:00");

        Schedule savedSchedule = scheduleRepository.save(schedule);


        Long scheduleId = savedSchedule.getSchedule_id();
        assertNotNull(scheduleId);


        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);

        assertTrue(optionalSchedule.isPresent());
        Schedule foundSchedule = optionalSchedule.get();
        assertEquals("Advanced Group", foundSchedule.getScheduleName());
        assertEquals("Monday", foundSchedule.getScheduleDay());
        assertEquals("22:00", foundSchedule.getScheduleTime());

    }
}