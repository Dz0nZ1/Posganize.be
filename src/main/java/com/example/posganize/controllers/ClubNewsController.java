package com.example.posganize.controllers;

import com.example.posganize.models.ClubNewsModel;
import com.example.posganize.services.ClubNews.ClubNewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@PreAuthorize("hasRole('Admin')")
@RequestMapping("/api/v1/clubnews")
public class ClubNewsController {

    private final ClubNewsService clubNewsService;

    public ClubNewsController(ClubNewsService clubNewsService) {
        this.clubNewsService = clubNewsService;
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<ClubNewsModel>> getAllClubNews(){
        return new ResponseEntity<>(clubNewsService.getAllClubNews(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<ClubNewsModel> getClubNewsById(@PathVariable("id") Long newsId){
        return new ResponseEntity<>(clubNewsService.getClubNewsById(newsId), HttpStatus.OK);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ClubNewsModel> createClubNews(@RequestBody ClubNewsModel clubNews){
        return new ResponseEntity<>(clubNewsService.createClubNews(clubNews), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ClubNewsModel> updateClubNews(@PathVariable("id") Long newsId, @RequestBody ClubNewsModel clubNews){
        return new ResponseEntity<>(clubNewsService.updateClubNews(clubNews, newsId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteClubNews(@PathVariable("id") Long newsId) {
        clubNewsService.deleteClubNews(newsId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "ClubNews was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
