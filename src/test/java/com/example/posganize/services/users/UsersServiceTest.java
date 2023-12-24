package com.example.posganize.services.users;

import com.example.posganize.entities.Users;
import com.example.posganize.enums.RoleEnum;
import com.example.posganize.models.user.UpdateUsersModel;
import com.example.posganize.models.user.UserPageableModel;
import com.example.posganize.models.user.UsersModel;
import com.example.posganize.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersServiceTest {


    @Autowired
    public UsersService usersService;

    @MockBean
    public UsersRepository usersRepository;


    @BeforeEach
    void setUp() {

        Users user1 = Users
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

        Users user2 = Users
                .builder()
                .user_id(2L)
                .firstName("Marko")
                .lastName("Markovic")
                .email("marko@email.com")
                .password("123456789")
                .role(RoleEnum.USER)
                .trailTraining(false)
                .phoneNumber("30434355")
                .memberships(List.of())
                .build();

        List<Users> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        Page<Users> usersPage = new PageImpl<>(userList);


        Mockito.when(usersRepository.findAll(Mockito.any(Pageable.class))).thenReturn(usersPage);
        Mockito.when(usersRepository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
        Mockito.when(usersRepository.findById(user2.getUser_id())).thenReturn(Optional.of(user1));
        Mockito.when(usersRepository.findUserWithMembership(user2.getUser_id())).thenReturn(user2);
        Mockito.when(usersRepository.findAllUsersWithActiveMembership(Mockito.any(Pageable.class))).thenReturn(usersPage);
        Mockito.when(usersRepository.findAllUsersWithoutActiveMembership(Mockito.any(Pageable.class))).thenReturn(usersPage);
        Mockito.when(usersRepository.save(Mockito.any(Users.class))) .thenAnswer(invocation -> {
            Users createdUser = invocation.getArgument(0);
            createdUser.setUser_id(user2.getUser_id());
            return createdUser;
        });

    }

    @Test
    void getAllUsers() {
        int pageNumber = 0;
        int pageSize = 10;
        String status = "active";
        UserPageableModel result = usersService.getAllUsers(pageNumber, pageSize, status);
        assertNotNull(result);
        assertEquals(pageNumber, result.getPageNumber());
        assertEquals(pageSize, result.getPageSize());
    }

    @Test
    void getUserById() {
        Long userId = 2L;
        UsersModel foundUser = usersService.getUserById(userId);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void getUserByEmail() {
        String userEmail = "nikola@email.com";
        UsersModel foundUser = usersService.getUserByEmail(userEmail);
        assertEquals(userEmail, foundUser.getEmail());
    }

    @Test
    void updateUser() {
        Long userId = 2L;
        UpdateUsersModel user = UpdateUsersModel
                .builder()
                .firstName("Goran")
                .lastName("Goranovic")
                .build();
        UsersModel updatedUser = usersService.updateUser(user, userId);
        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getId());
        System.out.println(updatedUser);
        assertEquals("Goran", updatedUser.getFirstName());
        assertEquals("Goranovic", updatedUser.getLastName());
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        usersService.deleteUser(userId);
        Mockito.verify(usersRepository, Mockito.times(1)).deleteById(userId);
    }
}