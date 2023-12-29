package com.example.posganize.controllers;

import com.example.posganize.models.clubNews.ClubNewsModel;
import com.example.posganize.models.clubNews.ClubNewsPageableModel;
import com.example.posganize.models.clubNews.CreateClubNewsModel;
import com.example.posganize.models.clubNews.UpdateClubNewsModel;
import com.example.posganize.services.clubNews.ClubNewsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.posganize.constants.PageableConstants.*;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequestMapping("/api/v1/clubnews")
public class ClubNewsController {

    private final ClubNewsService clubNewsService;

    public ClubNewsController(ClubNewsService clubNewsService) {
        this.clubNewsService = clubNewsService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<ClubNewsModel>> getAllClubNews(){
        return new ResponseEntity<>(clubNewsService.getAllClubNews(), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<ClubNewsPageableModel> getClubNewsPageable(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false ) int pageSize
    ) {
    return new ResponseEntity<>(clubNewsService.getAllClubNewsPageable(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<ClubNewsModel> getClubNewsById(@PathVariable("id") Long newsId){
        return new ResponseEntity<>(clubNewsService.getClubNewsById(newsId), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ClubNewsModel> createClubNews(@Valid  @RequestBody CreateClubNewsModel clubNews){
        return new ResponseEntity<>(clubNewsService.createClubNews(clubNews), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ClubNewsModel> updateClubNews(@PathVariable("id") Long newsId, @Valid @RequestBody UpdateClubNewsModel clubNews){
        return new ResponseEntity<>(clubNewsService.updateClubNews(clubNews, newsId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteClubNews(@PathVariable("id") Long newsId) {
        clubNewsService.deleteClubNews(newsId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "ClubNews was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
