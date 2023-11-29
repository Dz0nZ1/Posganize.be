package com.example.posganize.controllers;

import com.example.posganize.models.CreateMembershipModel;
import com.example.posganize.models.MembershipModel;
import com.example.posganize.services.membership.MembershipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/membership")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }


    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<MembershipModel>> getAllMemberships(){
        return new ResponseEntity<>(membershipService.getAllMemberships(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<MembershipModel> getMembership(@PathVariable("id") Long membershipId){
        return new ResponseEntity<>(membershipService.getMembership(membershipId), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<MembershipModel>> getMembershipByUserId(@PathVariable("id") Long userId){
        return new ResponseEntity<>(membershipService.getAllMembershipByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<MembershipModel> createMembership(@RequestBody CreateMembershipModel membership){
        return new ResponseEntity<>(membershipService.createMembership(membership), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<MembershipModel> updateMembership(@PathVariable("id") Long membershipId, @RequestBody MembershipModel membershipModel){
        return new ResponseEntity<>(membershipService.updateMembership(membershipModel, membershipId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteMembership(@PathVariable("id") Long membershipId) {
        membershipService.deleteMembership(membershipId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "Membership was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/active/{id}")
    public ResponseEntity<Map<String, Boolean>> isActiveMembershipByUserId(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(membershipService.isActiveMembershipByUserId(userId), HttpStatus.OK);
    }


}
