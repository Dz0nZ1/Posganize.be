package com.example.posganize.mappers;
import com.example.posganize.entities.Users;
import com.example.posganize.models.UpdateUsersModel;
import com.example.posganize.models.UsersModel;

import java.util.ArrayList;
import java.util.List;

public class UsersMapper {


    public static UsersModel mapUsersToUsersModel(Users users) {
        return UsersModel.builder()
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .phoneNumber(users.getPhoneNumber())
                .registrationDate(users.getRegistrationDate())
                .email(users.getEmail())
                .build();
    }


    public static Users mapUsersModelToUsers(UsersModel usersModel) {
        return Users.builder()
                .firstName(usersModel.getFirstName())
                .lastName(usersModel.getLastName())
                .phoneNumber(usersModel.getPhoneNumber())
                .registrationDate(usersModel.getRegistrationDate())
                .email(usersModel.getEmail())
                .build();
    }



    public static UpdateUsersModel mapUsersToUpdateUsersModel(Users users) {
        return UpdateUsersModel.builder()
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .phoneNumber(users.getPhoneNumber())
                .password(users.getPassword())
                .build();

    }


    public static Users mapUpdateUsersModelToUsers(UpdateUsersModel usersModel) {
        return Users.builder()
                .firstName(usersModel.getFirstName())
                .lastName(usersModel.getLastName())
                .phoneNumber(usersModel.getPhoneNumber())
                .password(usersModel.getPassword())
                .build();
    }


    public static List<UsersModel> mapUsersListToUsersModelList(List<Users> users) {
        List<UsersModel> modelList = new ArrayList<>();
        for (Users entity : users) {
            modelList.add(mapUsersToUsersModel(entity));
        }
        return modelList;

    }



}
