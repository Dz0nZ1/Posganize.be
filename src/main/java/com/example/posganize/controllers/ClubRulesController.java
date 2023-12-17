package com.example.posganize.controllers;

import com.example.posganize.models.ClubRulesModel;
import com.example.posganize.services.clubRules.ClubRulesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clubrules")
public class ClubRulesController {

    private final ClubRulesService clubRulesService;

    public ClubRulesController(ClubRulesService clubRulesService) {
        this.clubRulesService = clubRulesService;
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<ClubRulesModel>> getAllClubRules(){
        return new ResponseEntity<>(clubRulesService.getAllClubRules(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<ClubRulesModel> getClubRuleById(@PathVariable("id") Long clubRuleId){
        return new ResponseEntity<>(clubRulesService.getClubRule(clubRuleId), HttpStatus.OK);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ClubRulesModel> createClubRule(@Valid @RequestBody ClubRulesModel clubRulesModel){
        return new ResponseEntity<>(clubRulesService.createClubRule(clubRulesModel), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ClubRulesModel> updateClubRule(@PathVariable("id") Long clubRuleId, @Valid @RequestBody ClubRulesModel clubRulesModel){
        return new ResponseEntity<>(clubRulesService.updateClubRule(clubRulesModel, clubRuleId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteClubRule(@PathVariable("id") Long clubRuleId) {
        clubRulesService.deleteClubRule(clubRuleId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "ClubRule was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
