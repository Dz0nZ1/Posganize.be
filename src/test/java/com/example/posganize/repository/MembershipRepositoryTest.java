package com.example.posganize.repository;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Users;
import com.example.posganize.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MembershipRepositoryTest {


    @Autowired
    public MembershipRepository membershipRepository;

    @Autowired
    public UsersRepository usersRepository;

    private Users user1;

    private Users user2;

    private Membership membership1;

    private Membership membership2;
    private Membership membership3;


    @BeforeEach
    void setUp() {

        user1 = Users
                .builder()
                .firstName("Nikola")
                .lastName("Nikolic")
                .email("nikola@email.com")
                .password("123456789")
                .role(RoleEnum.USER)
                .trailTraining(false)
                .phoneNumber("30434355")
                .build();

        user2 = Users
                .builder()
                .firstName("Milos")
                .lastName("Milosevic")
                .email("milos@email.com")
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

        membership2 = Membership
                .builder()
                .membership_id(2L)
                .startDate(LocalDate.of(2023, 8, 1  ).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1  ).atStartOfDay())
                .active(true)
                .price(3000.0)
                .user(user2)
                .build();

        membership3 = Membership
                .builder()
                .membership_id(3L)
                .startDate(LocalDate.of(2023, 8, 1  ).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1  ).atStartOfDay())
                .active(true)
                .price(3000.0)
                .user(user2)
                .build();

    }

    @Test
    void findByUser() {
        Users savedUser1 = usersRepository.save(user1);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Users foundUser = usersRepository.findById(savedUser1.getUser_id()).orElseThrow(null);
        Membership foundMembership = membershipRepository.findByUser(foundUser);
        assertNotNull(foundMembership);
        assertEquals(membership1.getMembership_id(), foundMembership.getMembership_id());
        assertEquals(membership1.getUser(), foundMembership.getUser());

    }

    @Test
    void findByExpireDateBeforeAndActiveTrue() {
        LocalDateTime localDate = LocalDateTime.now();
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);
        List<Membership> foundMemberships = membershipRepository.findByExpireDateBeforeAndActiveTrue(localDate);
        assertNotNull(foundMemberships);
        assertEquals(2, foundMemberships.size());

    }

    @Test
    void findAllMembershipByUserId() {
        int pageNumber = 0;
        int pageSize = 10;
        Users savedUser2 = usersRepository.save(user2);
        Membership savedMembership2 = membershipRepository.save(membership2);
        Membership savedMembership3 = membershipRepository.save(membership3);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Membership> foundMemberships = membershipRepository.findAllMembershipByUserId(savedUser2.getUser_id(), pageRequest);
        assertNotNull(foundMemberships);
        assertEquals(2, foundMemberships.getTotalElements());

    }


    /*

    --- H2 database doesn't support MONTHNAME reserved word from MySQL syntax so the test will always fail ---
    @Test
    void getRevenueAndMembersByMonth() {
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);
        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 28);
        List<Map<String, Object>> result = membershipRepository.getRevenueAndMembersByMonth(fromDate, toDate);
        assertNotNull(result);
    }
    */

    @Test
    void getTotalRevenueAndMembers() {
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);
        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 31);
        Map<String, Object> result = membershipRepository.getTotalRevenueAndMembers(fromDate, toDate);
        assertNotNull(result);
        assertEquals(2L, result.get("totalMembers"));
    }

    @Test
    void countMembers() {
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);
        Long membersCount = membershipRepository.countMembers();
        Long expectedMembersCount = 2L;
        assertEquals(expectedMembersCount, membersCount);

    }

    @Test
    void calculateTotalRevenue() {
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);
        Double totalRevenue = membershipRepository.calculateTotalRevenue();
        Double expectedTotalRevenue = 6000.0;
        assertEquals(expectedTotalRevenue, totalRevenue);
    }
}