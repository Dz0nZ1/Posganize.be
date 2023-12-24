package com.example.posganize.services.training;


import com.example.posganize.entities.Training;
import com.example.posganize.mappers.TrainingMapper;
import com.example.posganize.models.training.CreateTrainingModel;
import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.models.training.UpdateTrainingModel;
import com.example.posganize.repository.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrainingServiceTest {

    @Autowired
    private TrainingService trainingService;

    @MockBean
    private TrainingRepository trainingRepository;

    private Training training1;
    private Training training2;

    LocalDate fromDate;
    LocalDate toDate;

    List<Map<String, Long>> result;

    @BeforeEach
    void setUp() {

        fromDate = LocalDate.now();
        toDate = LocalDate.now().plusDays(30);

         training1 = Training
                .builder()
                .training_id(1L)
                .schedules(List.of())
                .name("Boxing")
                .price(3000.00)
                .image("random image")
                .description("random description")
                .build();

        training2 = Training
                .builder()
                .training_id(2L)
                .schedules(List.of())
                .name("MMA")
                .price(5000.00)
                .image("random image")
                .description("random description")
                .build();

        Mockito.when(trainingRepository.findAllSets()).thenReturn(Set.of(training1, training2));
        Mockito.when(trainingRepository.findById(training1.getTraining_id())).thenReturn(Optional.of(training1));
        Mockito.when(trainingRepository.save(Mockito.any(Training.class))) .thenAnswer(invocation -> {
            Training createdTraining = invocation.getArgument(0);
            createdTraining.setTraining_id(1L);
            return createdTraining;
        });
        Mockito.when(trainingRepository.countUsersByTraining(fromDate, toDate)).thenReturn(result);

    }

    @Test
    void getAllTraining() {
        Set<TrainingModel> expected = TrainingMapper.mapTrainingSetToTrainingModelSet(Set.of(training1,training2));
        Set<TrainingModel> actual = trainingService.getAllTraining();
        assertEquals(expected, actual);
    }

    @Test
    void getTrainingById() {
        Long trainingId = 1L;
        TrainingModel found = trainingService.getTrainingById(trainingId);
        assertEquals(trainingId, found.getId());
    }

    @Test
    void createTraining() {

        CreateTrainingModel trainingModel = CreateTrainingModel
                .builder()
                .name("MMA")
                .price(5000.00)
                .image("random image")
                .description("random description")
                .schedule(List.of())
                .build();

        TrainingModel createdTraining = trainingService.createTraining(trainingModel);

        assertNotNull(createdTraining.getId());
        assertEquals("MMA", createdTraining.getName());
        assertEquals(5000, createdTraining.getPrice());
        assertEquals("random image", createdTraining.getImage());
        assertEquals("random description", createdTraining.getDescription());
    }

    @Test
    void updateTraining() {
        Long trainingId = 1L;
        UpdateTrainingModel updateTraining = UpdateTrainingModel
                .builder()
                .name("Basketball")
                .price(3000.00)
                .build();

        TrainingModel updatedTraining = trainingService.updateTraining(updateTraining, trainingId);

        assertEquals(trainingId, updatedTraining.getId());
        assertEquals("Basketball", updatedTraining.getName());
        assertEquals(3000, updatedTraining.getPrice());
    }

    @Test
    void deleteTraining() {
        Long trainingId = 1L;
        trainingService.deleteTraining(trainingId);
        Mockito.verify(trainingRepository, Mockito.times(1)).deleteById(trainingId);
    }

    @Test
    void getUserCountPerTraining() {
        List<Map<String, Long>> expected = trainingService.getUserCountPerTraining(fromDate, toDate);
        assertEquals(expected, result);
    }
}