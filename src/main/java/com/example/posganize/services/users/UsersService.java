package com.example.posganize.services.users;

import com.example.posganize.models.UpdateUsersModel;
import com.example.posganize.models.UsersModel;

import java.util.List;

public interface UsersService {

    List<UsersModel> getAllUsers();

    UsersModel getUserByEmail(String Email);

    UpdateUsersModel updateUser (UpdateUsersModel updateUsersModel, Long userId);

    void deleteUser(Long userId);

}
