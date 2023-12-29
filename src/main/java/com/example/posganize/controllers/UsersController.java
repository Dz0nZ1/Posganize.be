package com.example.posganize.controllers;

import com.example.posganize.models.user.UpdateUsersModel;
import com.example.posganize.models.user.UserPageableModel;
import com.example.posganize.models.user.UsersModel;
import com.example.posganize.services.users.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.example.posganize.constants.PageableConstants.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<UserPageableModel> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false ) int pageSize,
            @RequestParam(value = "status", defaultValue = DEFAULT_STATUS, required = false) String status
    ){
        return new ResponseEntity<>(usersService.getAllUsers(pageNumber, pageSize, status), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<UsersModel>getUserById(@PathVariable("id") Long userId){
        return new ResponseEntity<>(usersService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<UsersModel> getUserByEmail(@PathVariable("email") String Email){
        return new ResponseEntity<>(usersService.getUserByEmail(Email), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UsersModel> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody UpdateUsersModel updateUsersModel){
        return new ResponseEntity<>(usersService.updateUser(updateUsersModel, userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") Long userId) {
        usersService.deleteUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "User was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
