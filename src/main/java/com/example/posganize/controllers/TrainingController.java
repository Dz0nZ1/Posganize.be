package com.example.posganize.controllers;

import com.example.posganize.models.training.CreateTrainingModel;
import com.example.posganize.models.training.TrainingModel;
import com.example.posganize.models.training.UpdateTrainingModel;
import com.example.posganize.services.training.TrainingService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/training")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<Set<TrainingModel>> getAllTrainings(){
        return new ResponseEntity<>(trainingService.getAllTraining(), HttpStatus.OK);
    }

    @GetMapping("/users-per-training")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Map<String, Long>>> getUsersCountPerTraining(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        return new ResponseEntity<>(trainingService.getUserCountPerTraining(fromDate, toDate), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<TrainingModel> getTrainingById(@PathVariable("id") Long trainingId){
        return new ResponseEntity<>(trainingService.getTrainingById(trainingId), HttpStatus.OK);
    }

    @GetMapping("/duplicate/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateTraining(@PathVariable("id") Long trainingId) {
        return new ResponseEntity<>(trainingService.checkDuplicateTraining(trainingId), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<TrainingModel> createTraining(@Valid @RequestBody CreateTrainingModel training){
        return new ResponseEntity<>(trainingService.createTraining(training), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<TrainingModel> updateTraining(@PathVariable("id") Long trainingId, @Valid @RequestBody UpdateTrainingModel training){
        return new ResponseEntity<>(trainingService.updateTraining(training, trainingId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteTraining(@PathVariable("id") Long trainingId) {
        trainingService.deleteTraining(trainingId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "Training was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
