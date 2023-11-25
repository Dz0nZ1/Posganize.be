package com.example.posganize.services.users;

import com.example.posganize.models.UpdateUsersModel;
import com.example.posganize.models.UserPageableModel;
import com.example.posganize.models.UsersModel;


public interface UsersService {

    UserPageableModel getAllUsers(int pageNumber, int pageSize, String status);

    UsersModel getUserWithMembershipById(Long userId);

    UsersModel getUserByEmail(String Email);

    UpdateUsersModel updateUser (UpdateUsersModel updateUsersModel, Long userId);

    void deleteUser(Long userId);

}
