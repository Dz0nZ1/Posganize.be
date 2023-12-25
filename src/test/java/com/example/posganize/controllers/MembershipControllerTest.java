package com.example.posganize.controllers;

import com.example.posganize.config.TestSecurityConfig;
import com.example.posganize.models.membership.CreateMembershipModel;
import com.example.posganize.models.membership.MembershipModel;
import com.example.posganize.models.membership.MembershipPageableModel;
import com.example.posganize.models.user.UserMembershipModel;
import com.example.posganize.repository.TokenRepository;
import com.example.posganize.services.auth.JwtService;
import com.example.posganize.services.membership.MembershipService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MembershipController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class MembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MembershipService membershipService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private  UserMembershipModel user;

    private MembershipModel membership;


    private CreateMembershipModel createMembershipModel;



    @BeforeEach
    void setUp() {
        user = UserMembershipModel
                .builder()
                .userId(1L)
                .firstName("Nikola")
                .lastName("Nikolic")
                .build();

        membership = MembershipModel
                .builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 8, 1  ).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1  ).atStartOfDay())
                .active(true)
                .price(3000.0)
                .user(user)
                .build();

        createMembershipModel = CreateMembershipModel
                .builder()
                .startDate(LocalDateTime.of(2023, 8, 1, 15, 30  ))
                .expireDate(LocalDateTime.of(2023, 9, 1, 14, 20  ))
                .build();

    }

    @Test
    void getAllMemberships() throws Exception {
        when(membershipService.getAllMemberships()).thenReturn(List.of(membership));
        ResultActions response = mockMvc.perform(get("/api/v1/membership/all" )
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(List.of(membership).size()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getMembership() throws Exception {

        Long membershipId = 1L;
        when(membershipService.getMembership(membershipId)).thenReturn(membership);

        ResultActions response = mockMvc.perform(get("/api/v1/membership/get/{id}", membershipId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(membership.getId()))
                .andExpect(jsonPath("$.startDate").value(membership.getStartDate()+ ":00"))
                .andExpect(jsonPath("$.expireDate").value(membership.getExpireDate() + ":00"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void getMembershipByUserId() throws Exception {
        Long userId = 123L;
        int pageNumber = 0;
        int pageSize = 10;
        String sortOrder = "asc";

        MembershipPageableModel membershipPageableModel = MembershipPageableModel
                .builder()
                .pageSize(pageSize)
                .pageNumber(pageNumber)
                .build();
        when(membershipService.getAllMembershipsByUserId(userId, pageNumber, pageSize, true))
                .thenReturn(membershipPageableModel);

        mockMvc.perform(get("/api/v1/membership/user/{id}", userId)
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("sortOrder", sortOrder)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Add more assertions for the response body or structure if needed
                .andExpect(jsonPath("$.pageNumber").value(pageNumber))
                .andExpect(jsonPath("$.pageSize").value(pageSize));


    }

    @Test
    void createMembership() throws Exception {
        when(membershipService.createMembership(any(CreateMembershipModel.class)))
                .thenReturn(membership);

        ResultActions response = mockMvc.perform(post("/api/v1/membership/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMembershipModel)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(membership.getId()))
                .andExpect(jsonPath("$.startDate").value(membership.getStartDate()+ ":00"))
                .andExpect(jsonPath("$.expireDate").value(membership.getExpireDate() + ":00"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateMembership() throws Exception {
        Long membershipId = 1L;
        when(membershipService.updateMembership(any(MembershipModel.class), any(Long.class)))
                .thenReturn(membership);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/membership/update/{id}", membershipId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(membership)));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(membership.getId()))
                .andExpect(jsonPath("$.startDate").value(membership.getStartDate()+ ":00"))
                .andExpect(jsonPath("$.expireDate").value(membership.getExpireDate() + ":00"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteMembership() throws Exception {
        Long membershipId = 1L;
        doNothing().when(membershipService).deleteMembership(1L);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/membership/delete/{id}", membershipId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk());
    }

    @Test
    void isActiveMembershipByUserId() throws Exception {
        Long userId = 1L;

        Map<String, Boolean> membershipMap = new HashMap<>();
        membershipMap.put("active", true);
        when(membershipService.isActiveMembershipByUserId(userId)).thenReturn(membershipMap);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/membership/active/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON));
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(true));

    }

    @Test
    void getRevenueAndMembers() throws Exception {

        LocalDate fromDate = LocalDate.of(2022, 1, 1);
        LocalDate toDate = LocalDate.of(2022, 12, 31);

        Map<String, Object> revenueAndMembersMap = new HashMap<>();
        revenueAndMembersMap.put("MostPopularTraining", "Boxing");

        when(membershipService.getRevenueAndMembers(fromDate, toDate)).thenReturn(revenueAndMembersMap);

        // Performing the GET request
        mockMvc.perform(get("/api/v1/membership/revenue-and-members")
                        .param("fromDate", "2022-01-01")
                        .param("toDate", "2022-12-31"))
                .andExpect(status().isOk())
                // Add assertions based on your expected response structure
                .andExpect(jsonPath("$.MostPopularTraining").value("Boxing"));

    }
}