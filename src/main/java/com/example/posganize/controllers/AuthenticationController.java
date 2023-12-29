package com.example.posganize.controllers;

import com.example.posganize.models.auth.AuthenticationRequest;
import com.example.posganize.models.auth.AuthenticationResponse;
import com.example.posganize.models.auth.RegisterRequest;
import com.example.posganize.services.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);

    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);

    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/check-auth")
    public ResponseEntity<Map<String, Object>> checkAuth() {
        return new ResponseEntity<>(authenticationService.isAuthenticated(), HttpStatus.OK);
    }

}
