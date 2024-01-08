package com.example.posganize.controllers;

import com.example.posganize.config.TestSecurityConfig;
import com.example.posganize.models.user.UpdateUsersModel;
import com.example.posganize.models.user.UserPageableModel;
import com.example.posganize.models.user.UsersModel;
import com.example.posganize.repository.TokenRepository;
import com.example.posganize.services.auth.JwtService;
import com.example.posganize.services.users.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UsersModel usersModel;

    private UpdateUsersModel updateUsersModel;


    @BeforeEach
    void setUp() {

         usersModel = UsersModel
                .builder()
                .id(1L)
                .firstName("Nikola")
                .lastName("Nikolic")
                .email("nikola@email.com")
                .phoneNumber("30434355")
                .registrationDate(LocalDateTime.now())
                .active(true)
                .build();

        updateUsersModel = UpdateUsersModel
                .builder()
                .firstName("Goran")
                .lastName("Goranovic")
                .build();


    }

    @Test
    void getAllUsers() throws Exception {

        int pageNumber = 0;
        int pageSize = 10;
        String status = "active";
        UserPageableModel userPageableModel = UserPageableModel
                .builder()
                .users(List.of(usersModel))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        when(usersService.getAllUsers(pageNumber, pageSize, status)).thenReturn(userPageableModel);
        ResultActions response = mockMvc.perform(get("/api/v1/users/all")
                .contentType(MediaType.APPLICATION_JSON));
        assertNotNull(userPageableModel);
        response.andExpect(status().isOk());
        System.out.println(userPageableModel);

    }

    @Test
    void getUserById() throws Exception {

        Long userId = 1L;
        when(usersService.getUserById(userId)).thenReturn(usersModel);

        ResultActions response = mockMvc.perform(get("/api/v1/users/get/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usersModel.getId()))
                .andExpect(jsonPath("$.firstName").value(usersModel.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(usersModel.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(usersModel.getPhoneNumber()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getUserByEmail() throws Exception {
        String userEmail = "nikola@email.com";
        when(usersService.getUserByEmail(userEmail)).thenReturn(usersModel);

        ResultActions response = mockMvc.perform(get("/api/v1/users/email/{email}", userEmail)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usersModel.getId()))
                .andExpect(jsonPath("$.firstName").value(usersModel.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(usersModel.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(usersModel.getPhoneNumber()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateUser() throws Exception {

        Long userId = 1L;
        when(usersService.updateUser(any(UpdateUsersModel.class), any(Long.class)))
                .thenReturn(usersModel);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/update/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUsersModel)));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usersModel.getId()))
                .andExpect(jsonPath("$.firstName").value(usersModel.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(usersModel.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(usersModel.getPhoneNumber()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteUser() throws Exception {
        Long userId = 1L;
        doNothing().when(usersService).deleteUser(1L);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/delete/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk());
    }
}