package com.example.posganize.services.membership;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Training;
import com.example.posganize.entities.Users;
import com.example.posganize.enums.RoleEnum;
import com.example.posganize.mappers.MembershipMapper;
import com.example.posganize.mappers.TrainingMapper;
import com.example.posganize.models.membership.CreateMembershipModel;
import com.example.posganize.models.membership.MembershipModel;
import com.example.posganize.models.membership.MembershipPageableModel;
import com.example.posganize.repository.MembershipRepository;
import com.example.posganize.repository.TrainingRepository;
import com.example.posganize.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MembershipServiceTest {


    @Autowired
    private MembershipService membershipService;

    @MockBean
    private MembershipRepository membershipRepository;

    @MockBean
    private TrainingRepository trainingRepository;

    @MockBean
    private UsersRepository usersRepository;

    private Users user1;

    private Membership membership1;

    private Training training1;
    private List<Membership> membershipList;




    @BeforeEach
    void setUp() {

        user1 = Users
                .builder()
                .user_id(1L)
                .firstName("Nikola")
                .lastName("Nikolic")
                .email("nikola@email.com")
                .password("123456789")
                .role(RoleEnum.USER)
                .trailTraining(false)
                .phoneNumber("30434355")
                .build();

        membership1 = Membership
                .builder()
                .membership_id(1L)
                .startDate(LocalDate.of(2023, 8, 1  ).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1  ).atStartOfDay())
                .active(true)
                .price(3000.0)
                .user(user1)
                .build();

         training1 = Training
                .builder()
                .training_id(1L)
                .schedules(List.of())
                .name("Boxing")
                .price(3000.00)
                .image("random image")
                .description("random description")
                .build();

        Map<String, Object> data1 = new HashMap<>();
        data1.put("totalRevenue", 50000);
        List<Map<String, Object>> result1 = new ArrayList<>();
        result1.add(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("totalRevenueByMont", 3000);
        List<Map<String, Object>> result2 = new ArrayList<>();
        result2.add(data2);

        Long trainingsBetweenDates = 5L;
        String trainingWithMaximumRevenue = "Boxing";

        Long userId = 1L;
        int pageNumber = 0;
        int pageSize = 10;
        membershipList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "expire_date"));
        Page<Membership> pagedMemberships = new PageImpl<>(membershipList, pageable, membershipList.size());

        when(membershipRepository.findAll()).thenReturn(List.of(membership1));
        when(membershipRepository.findById(membership1.getMembership_id())).thenReturn(Optional.of(membership1));
        when(membershipRepository.getTotalRevenueAndMembers(any(LocalDate.class), any(LocalDate.class))).thenReturn(data1);
        when(membershipRepository.getRevenueAndMembersByMonth(any(LocalDate.class), any(LocalDate.class))).thenReturn(result2);
        when(trainingRepository.countTrainingsBetweenDates(any(LocalDate.class), any(LocalDate.class))).thenReturn(trainingsBetweenDates);
        when(trainingRepository.findTrainingNameWithMaxRevenueBetweenDates(any(LocalDate.class), any(LocalDate.class))).thenReturn(trainingWithMaximumRevenue);
        when(membershipRepository.findAllMembershipByUserId(userId, pageable)).thenReturn(pagedMemberships);
        when(membershipRepository.save(any(Membership.class))) .thenAnswer(invocation -> {
            Membership createdMembership = invocation.getArgument(0);
            createdMembership.setMembership_id(1L);
            return createdMembership;
        });
        when(usersRepository.findById(membership1.getUser().getUser_id())).thenReturn(Optional.of(user1));
        when(membershipRepository.findByUser(any(Users.class))).thenReturn(membership1);

    }


    @Test
    void getAllMemberships() {
        List<MembershipModel> membershipModelList = membershipService.getAllMemberships();
        List<MembershipModel> dummyList = MembershipMapper.mapMembershipListToMembershipModelList(List.of(membership1));
        assertNotNull(membershipModelList);
        assertEquals(dummyList, membershipModelList);
    }

    @Test
    void getMembership() {
        Long membershipId = 1L;
        MembershipModel foundMembership = membershipService.getMembership(membershipId);
        assertNotNull(foundMembership);
        assertEquals(membershipId, foundMembership.getId());
    }

    @Test
    void getAllMembershipsByUserId() {
        Long userId = 1L;
        int pageNumber = 0;
        int pageSize = 10;
        boolean ascending = true;
        MembershipPageableModel result = membershipService.getAllMembershipsByUserId(userId, pageNumber, pageSize, ascending);
        assertEquals(pageNumber, result.getPageNumber());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(membershipList.size(), result.getNumberOfMemberships());

    }

    @Test
    void getRevenueAndMembers() {
        Long trainingsBetweenDates = 5L;
        String trainingWithMaximumRevenue = "Boxing";
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now().plusDays(30);
        Map<String, Object> response = membershipService.getRevenueAndMembers(fromDate, toDate);
        assertNotNull(response);
        assertEquals(trainingsBetweenDates, response.get("NumberOfActiveTrainings"));
        assertEquals(trainingWithMaximumRevenue, response.get("MostPopularTraining"));
    }

    @Test
    void createMembership() {

        CreateMembershipModel membership = CreateMembershipModel.builder()
                .userId(user1.getUser_id())
                .startDate(LocalDate.of(2023, 8, 1).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1).atStartOfDay())
                .trainings(TrainingMapper.mapTrainingSetToTrainingModelSet(Set.of(training1)))
                .build();

        assertNotNull(membership);
        MembershipModel createdMembership = membershipService.createMembership(membership);

        assertEquals(LocalDate.of(2023, 8, 1).atStartOfDay(), createdMembership.getStartDate());
        assertEquals(LocalDate.of(2023, 9, 1).atStartOfDay(), createdMembership.getExpireDate());
        assertEquals(TrainingMapper.mapTrainingSetToTrainingModelSet(Set.of(training1)), createdMembership.getTrainings());

        verify(usersRepository).findById(any());
        verify(membershipRepository).save(any(Membership.class));

    }

    @Test
    void updateMembership() {

        Long membershipId = 1L;
        MembershipModel membershipModel = MembershipModel
                .builder()
                .startDate(LocalDate.of(2023, 8, 1).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1).atStartOfDay())
                .build();

        MembershipModel updatedMembership = membershipService.updateMembership(membershipModel, membershipId);

        assertNotNull(updatedMembership.getId());
        assertEquals(LocalDate.of(2023, 8, 1).atStartOfDay(), updatedMembership.getStartDate());
        assertEquals(LocalDate.of(2023, 9, 1).atStartOfDay(), updatedMembership.getExpireDate());
    }

    @Test
    void deleteMembership() {
        Long membershipId = 1L;
        membershipService.deleteMembership(membershipId);
        Mockito.verify(membershipRepository, Mockito.times(1)).deleteById(membershipId);
    }


    @Test
    void isActiveMembershipByUserId() {
        Map<String, Boolean> result = membershipService.isActiveMembershipByUserId(user1.getUser_id());

        assertNotNull(result);
        assertTrue(result.containsKey("active"));
        if (membership1 != null) {
            assertTrue(result.get("active"));
        } else {
            assertFalse(result.get("active"));
        }
        verify(usersRepository).findById(user1.getUser_id());
        verify(membershipRepository).findByUser(user1);
    }
}