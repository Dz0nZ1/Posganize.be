package com.example.posganize.services.auth;

import com.example.posganize.entities.Token;
import com.example.posganize.entities.Users;
import com.example.posganize.enums.RoleEnum;
import com.example.posganize.enums.TokenTypeEnum;
import com.example.posganize.exceptions.UserNotFoundException;
import com.example.posganize.models.auth.AuthenticationRequest;
import com.example.posganize.models.auth.AuthenticationResponse;
import com.example.posganize.models.auth.RegisterRequest;
import com.example.posganize.repository.TokenRepository;
import com.example.posganize.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsersRepository usersRepository;

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
                .phoneNumber(request.getPhoneNumber())
                .trailTraining(true)
                .registrationDate(LocalDateTime.now())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(RoleEnum.USER)
                .build();
        var savedUser = usersRepository.save(user);
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

        var user = usersRepository.findByEmail(request.getEmail()).orElseThrow();
        revokeAllUserTokens(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return  AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstName(user.getFirstName())
                .lastname(user.getLastName())
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
            var user = this.usersRepository.findByEmail(email).orElseThrow();
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
            }
        }
    }

   public Map<String, Object > isAuthenticated() {
       Map<String, Object> response = new HashMap<>();
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (authentication != null && authentication.isAuthenticated()) {
           if(authentication.getName().equals("anonymousUser")) {
               response.put("isAuthenticated", false);
               return response;
           }
       }
       String concatenatedRoleNames = "";
       response.put("isAuthenticated", true);
       assert authentication != null;
       List<String> roleNames = authentication.getAuthorities().stream()
               .map(GrantedAuthority::getAuthority)
               .filter(authority -> authority.startsWith("ROLE_"))
               .toList();
       concatenatedRoleNames = String.join(",", roleNames);
       response.put("role", concatenatedRoleNames);
       var user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));
       response.put("userId", user.getUser_id());
       response.put("email", user.getEmail());
      return response;
   }


}
