package com.example.posganize.controllers;


import com.example.posganize.models.schedule.CreateScheduleModel;
import com.example.posganize.models.schedule.ScheduleModel;
import com.example.posganize.models.schedule.UpdateScheduleModel;
import com.example.posganize.services.schedule.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/api/v1/schedule")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<ScheduleModel>> getAllSchedules(){
        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<ScheduleModel> getSchedule(@PathVariable("id") Long scheduleId){
        return new ResponseEntity<>(scheduleService.getScheduleById(scheduleId), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ScheduleModel> createSchedule(@Valid @RequestBody CreateScheduleModel schedule){
        return new ResponseEntity<>(scheduleService.createSchedule(schedule), HttpStatus.OK);
    }

    @PostMapping("/create/{id}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ScheduleModel> createScheduleByTrainingId(@PathVariable("id") Long trainingId, @Valid @RequestBody CreateScheduleModel schedule){
        return new ResponseEntity<>(scheduleService.createScheduleByTrainingId(schedule, trainingId), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ScheduleModel> updateSchedule(@PathVariable("id") Long scheduleId, @Valid @RequestBody UpdateScheduleModel schedule){
        return new ResponseEntity<>(scheduleService.updateSchedule(schedule, scheduleId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteSchedule(@PathVariable("id") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "Schedule was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
