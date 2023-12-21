package com.example.posganize.services.users;

import com.example.posganize.models.user.UpdateUsersModel;
import com.example.posganize.models.user.UserPageableModel;
import com.example.posganize.models.user.UsersModel;


public interface UsersService {

    UserPageableModel getAllUsers(int pageNumber, int pageSize, String status);

    UsersModel getUserById(Long userId);

    UsersModel getUserByEmail(String Email);

    UpdateUsersModel updateUser (UpdateUsersModel updateUsersModel, Long userId);

    void deleteUser(Long userId);

}
