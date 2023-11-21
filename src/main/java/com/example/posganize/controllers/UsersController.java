package com.example.posganize.controllers;

import com.example.posganize.models.UpdateUsersModel;
import com.example.posganize.models.UsersModel;
import com.example.posganize.services.users.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<UsersModel>> getAllUsers(){
        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/get/{email}")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<UsersModel> getUserByEmail(@PathVariable("email") String Email){
        return new ResponseEntity<>(usersService.getUserByEmail(Email), HttpStatus.OK);
    }

//    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('admin:create')")
//    public ResponseEntity<UsersModel> createUser(@RequestBody UsersModel usersModel){
//        return new ResponseEntity<>(usersService.createUser(usersModel), HttpStatus.OK);
//    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUsersModel> updateUser(@PathVariable("id") Long userId, @RequestBody UpdateUsersModel updateUsersModel){
        return new ResponseEntity<>(usersService.updateUser(updateUsersModel, userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") Long userId) {
        usersService.deleteUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("defaultMessage", "User was deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
