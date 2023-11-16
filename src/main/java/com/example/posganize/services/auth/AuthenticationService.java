package com.example.posganize.services.auth;

import com.example.posganize.entities.Token;
import com.example.posganize.entities.Users;
import com.example.posganize.enums.RoleEnum;
import com.example.posganize.enums.TokenTypeEnum;
import com.example.posganize.models.AuthenticationRequest;
import com.example.posganize.models.AuthenticationResponse;
import com.example.posganize.models.RegisterRequest;
import com.example.posganize.repository.TokenRepository;
import com.example.posganize.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Users
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleEnum.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return  AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        revokeAllUserTokens(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return  AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(Users users) {
        var validUserToken = tokenRepository.findAllValidTokensByUser(users.getUser_id());
        if(validUserToken.isEmpty())
            return;
        validUserToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserToken);
    }


    private void saveUserToken(Users users, String jwtToken) {
        var token = Token.builder()
                .users(users)
                .token(jwtToken)
                .tokenTypeEnum(TokenTypeEnum.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        email = jwtService.extractUsername(refreshToken);
        if(email != null) {
            var user = this.userRepository.findByEmail(email).orElseThrow();
//            boolean isTokenValid = tokenRepository.findByToken(refreshToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
            if(jwtService.isTokenValid(refreshToken, user)){
               var accessToken = jwtService.generateToken(user);
               revokeAllUserTokens(user);
               saveUserToken(user, accessToken);
               var authResponse = AuthenticationResponse
                       .builder()
                       .accessToken(accessToken)
                       .refreshToken(refreshToken)
                       .build();
               new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            };
        }
    }
}
