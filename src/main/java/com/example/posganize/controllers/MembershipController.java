package com.example.posganize.controllers;

import com.example.posganize.models.membership.CreateMembershipModel;
import com.example.posganize.models.membership.MembershipModel;
import com.example.posganize.models.membership.MembershipPageableModel;
import com.example.posganize.services.membership.MembershipService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.posganize.constants.PageableConstants.*;

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
    public ResponseEntity<MembershipPageableModel> getMembershipByUserId(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false ) int pageSize,
            @RequestParam(value = "sortOrder",defaultValue = DEFAULT_SORT, required = false) String sortOrder,
            @PathVariable("id") Long userId
    ){
        boolean ascending = sortOrder.equalsIgnoreCase(DEFAULT_SORT);
        return new ResponseEntity<>(membershipService.getAllMembershipsByUserId(userId, pageNumber, pageSize, ascending), HttpStatus.OK);
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<MembershipModel> createMembership(@Valid @RequestBody CreateMembershipModel membership){
        return new ResponseEntity<>(membershipService.createMembership(membership), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<MembershipModel> updateMembership(@PathVariable("id") Long membershipId, @Valid @RequestBody MembershipModel membershipModel){
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

    @GetMapping("/revenue-and-members")
    public ResponseEntity<Map<String, Object>> getRevenueAndMembers(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        return new ResponseEntity<>(membershipService.getRevenueAndMembers(fromDate, toDate), HttpStatus.OK);
    }

}
