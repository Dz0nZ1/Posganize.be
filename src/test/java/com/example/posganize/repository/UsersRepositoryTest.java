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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MembershipRepository membershipRepository;


    private Users user1;
    private Users user2;
    private Users user3;
    private Users user4;

    private Membership membership1;
    private Membership membership2;
    private Membership membership3;
    private Membership membership4;

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

        user2 = Users
                .builder()
                .user_id(2L)
                .firstName("Marko")
                .lastName("Markovic")
                .email("marko@email.com")
                .password("123456789")
                .role(RoleEnum.USER)
                .trailTraining(false)
                .phoneNumber("30434355")
                .build();

        user3 = Users
                .builder()
                .user_id(1L)
                .firstName("Milos")
                .lastName("Milosevic")
                .email("milos@email.com")
                .password("123456789")
                .role(RoleEnum.USER)
                .trailTraining(true)
                .phoneNumber("30434355")
                .build();

        user4 = Users
                .builder()
                .user_id(2L)
                .firstName("Stefan")
                .lastName("Stefanovic")
                .email("stefan@email.com")
                .password("123456789")
                .role(RoleEnum.USER)
                .trailTraining(true)
                .phoneNumber("30434355")
                .build();


        membership1 = Membership
                .builder()
                .membership_id(1L)
                .startDate(LocalDateTime.of(2023, 12, 5, 15, 30 ))
                .expireDate(LocalDateTime.of(2024, 2, 4, 13, 30 ))
                .active(true)
                .price(3000.00)
                .user(user1)
                .build();

        membership2 = Membership
                .builder()
                .membership_id(2L)
                .startDate(LocalDateTime.of(2023, 12, 9, 14, 35 ))
                .expireDate(LocalDateTime.of(2024, 1, 5, 21, 30 ))
                .active(true)
                .price(4000.00)
                .user(user2)
                .build();

        membership3 = Membership
                .builder()
                .membership_id(3L)
                .startDate(LocalDateTime.of(2023, 12, 9, 14, 35 ))
                .expireDate(LocalDateTime.of(2024, 1, 5, 21, 30 ))
                .active(false)
                .price(4000.00)
                .user(user3)
                .build();

        membership4 = Membership
                .builder()
                .membership_id(4L)
                .startDate(LocalDateTime.of(2023, 12, 9, 14, 35 ))
                .expireDate(LocalDateTime.of(2024, 1, 5, 21, 30 ))
                .active(false)
                .price(4000.00)
                .user(user4)
                .build();
    }

    @Test
    void findByEmail() {
        Users savedUser = usersRepository.save(user1);

        String userEmail = savedUser.getEmail();
        assertNotNull(userEmail);

        Optional<Users> optionalUser = usersRepository.findByEmail(userEmail);

        assertTrue(optionalUser.isPresent());
        Users foundUser = optionalUser.get();
        assertEquals("Nikola", foundUser.getFirstName());
        assertEquals("Nikolic", foundUser.getLastName());
        assertEquals("nikola@email.com", foundUser.getEmail());
        assertEquals("123456789", foundUser.getPassword());
        assertEquals("30434355", foundUser.getPhoneNumber());
    }

    @Test
    void findAllUsersWithMembership() {
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Users savedUser3 = usersRepository.save(user3);
        Users savedUser4 = usersRepository.save(user4);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);
        Membership savedMembership3 = membershipRepository.save(membership3);
        Membership savedMembership4 = membershipRepository.save(membership4);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Users> usersWithActiveMembership = usersRepository.findAllUsersWithMembership(pageRequest);
        System.out.println(usersWithActiveMembership);
        assertNotNull(usersWithActiveMembership);
        assertEquals(4, usersWithActiveMembership.getTotalElements());



    }

    @Test
    void findAllUsersWithActiveMembership() {

        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);


        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Users> usersWithActiveMembership = usersRepository.findAllUsersWithActiveMembership(pageRequest);
        System.out.println(usersWithActiveMembership);
        assertNotNull(usersWithActiveMembership);
        assertEquals(2, usersWithActiveMembership.getTotalElements());
    }

    @Test
    void findAllUsersWithoutActiveMembership() {

        Users savedUser3 = usersRepository.save(user3);
        Users savedUser4 = usersRepository.save(user4);
        Membership savedMembership3 = membershipRepository.save(membership3);
        Membership savedMembership4 = membershipRepository.save(membership4);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Users> usersWithActiveMembership = usersRepository.findAllUsersWithoutActiveMembership(pageRequest);
        System.out.println(usersWithActiveMembership);
        assertNotNull(usersWithActiveMembership);
        assertEquals(2, usersWithActiveMembership.getTotalElements());


    }

    @Test
    void findUserWithMembership() {
        Long userId = 1L;
        Users savedUser1 = usersRepository.save(user1);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Users foundUser = usersRepository.findUserWithMembership(userId);
        assertEquals(userId, foundUser.getUser_id());
    }
}