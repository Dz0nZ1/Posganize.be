package com.example.posganize.repository;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Training;
import com.example.posganize.entities.Users;
import com.example.posganize.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TrainingRepositoryTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    public MembershipRepository membershipRepository;

    @Autowired
    public UsersRepository usersRepository;

    private Training training1;
    private Training training2;

    private Users user1;
    private Users user2;
    private Users user3;
    private Membership membership1;
    private Membership membership2;
    private Membership membership3;


    @BeforeEach
    void setUp() {

       training1 = Training
                .builder()
                .schedules(List.of())
                .name("Boxing")
                .price(3000.00)
                .image("random image")
                .description("random description")
                .build();

        training2 = Training
                .builder()
                .schedules(List.of())
                .name("MMA")
                .price(5000.00)
                .image("random image")
                .description("random description")
                .build();

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

        user3 = Users
                .builder()
                .firstName("Stefan")
                .lastName("Stefanovic")
                .email("stefan@email.com")
                .password("123456789")
                .role(RoleEnum.USER)
                .trailTraining(false)
                .phoneNumber("30434355")
                .build();

        membership1 = Membership
                .builder()
                .startDate(LocalDate.of(2023, 8, 1  ).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1  ).atStartOfDay())
                .active(true)
                .price(3000.00)
                .trainings(Set.of(training1))
                .user(user1)
                .build();

        membership2 = Membership
                .builder()
                .startDate(LocalDate.of(2023, 8, 1  ).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1  ).atStartOfDay())
                .active(true)
                .price(3000.00)
                .user(user2)
                .trainings(Set.of(training1))
                .build();

        membership3 = Membership
                .builder()
                .startDate(LocalDate.of(2023, 8, 1  ).atStartOfDay())
                .expireDate(LocalDate.of(2023, 9, 1  ).atStartOfDay())
                .active(true)
                .price(3000.00)
                .user(user3)
                .trainings(Set.of(training2))
                .build();

    }

    @Test
    void findAllSets() {
        Training savedTraining1 = trainingRepository.save(training1);
        Training savedTraining2 = trainingRepository.save(training2);

        Set<Training> allTrainings = trainingRepository.findAllSets();
        assertNotNull(allTrainings);
        assertEquals(2, allTrainings.size());

    }

    @Test
    void countUsersByTraining() {
        Training savedTraining1 = trainingRepository.save(training1);
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);

        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 1, 1);

        List<Map<String, Long>> result = trainingRepository.countUsersByTraining(fromDate, toDate);
        Map<String, Long> firstResult = result.get(0);

        System.out.println(result);
        assertEquals(2, firstResult.get("numberOfMembers"));
        assertEquals("Boxing", firstResult.get("training"));


    }

    @Test
    void countTrainingsBetweenDates() {
        Training savedTraining1 = trainingRepository.save(training1);
        Training savedTraining2 = trainingRepository.save(training2);
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user3);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership3);

        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 1, 1);

        Long numberOfTrainingsByDate = trainingRepository.countTrainingsBetweenDates(fromDate, toDate);
        assertEquals(2, numberOfTrainingsByDate);


    }

    @Test
    void findTrainingNameWithMaxRevenueBetweenDates() {
        Training savedTraining1 = trainingRepository.save(training1);
        Training savedTraining2 = trainingRepository.save(training2);
        Users savedUser1 = usersRepository.save(user1);
        Users savedUser2 = usersRepository.save(user2);
        Users savedUser3 = usersRepository.save(user3);
        Membership savedMembership1 = membershipRepository.save(membership1);
        Membership savedMembership2 = membershipRepository.save(membership2);
        Membership savedMembership3 = membershipRepository.save(membership3);
        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 1, 1);

        String training = trainingRepository.findTrainingNameWithMaxRevenueBetweenDates(fromDate, toDate);
        assertEquals("Boxing", training);

    }
}