package com.example.posganize.controllers;


import com.example.posganize.models.ScheduleModel;
import com.example.posganize.services.schedule.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @GetMapping("/all")
    //    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<ScheduleModel>> getAllSchedules(){
        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<ScheduleModel> getClubNewsById(@PathVariable("id") Long scheduleId){
        return new ResponseEntity<>(scheduleService.getScheduleById(scheduleId), HttpStatus.OK);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ScheduleModel> createSchedule(@RequestBody ScheduleModel schedule){
        return new ResponseEntity<>(scheduleService.createSchedule(schedule), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ScheduleModel> updateSchedule(@PathVariable("id") Long scheduleId, @RequestBody ScheduleModel schedule){
        return new ResponseEntity<>(scheduleService.updateSchedule(schedule, scheduleId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteSchedule(@PathVariable("id") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "Schedule was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
