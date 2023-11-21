package com.example.posganize.services.users;


import com.example.posganize.mappers.UsersMapper;
import com.example.posganize.models.UpdateUsersModel;
import com.example.posganize.models.UsersModel;
import com.example.posganize.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<UsersModel> getAllUsers() {
        return UsersMapper.mapUsersListToUsersModelList(usersRepository.findAll());
    }

    @Override
    public UsersModel getUserByEmail(String Email) {
        return UsersMapper.mapUsersToUsersModel(usersRepository.findByEmail(Email).orElseThrow(() -> new NullPointerException("User not found")));

    }




    @Override
    public UpdateUsersModel updateUser(UpdateUsersModel updateUsersModel, Long userId) {
        var entity = UsersMapper.mapUpdateUsersModelToUsers(updateUsersModel);
        var newUser = usersRepository.findById(userId).orElseThrow(() -> new NullPointerException("User not found"));
        if (entity.getFirstName() !=null )
            newUser.setFirstName(entity.getFirstName());
        if (entity.getLastName() !=null )
            newUser.setLastName(entity.getLastName());
        if (entity.getPhoneNumber() !=null )
            newUser.setPhoneNumber(entity.getPhoneNumber());
        if (entity.getPassword() !=null )
            newUser.setPassword(entity.getPassword());
        usersRepository.save(newUser);
        return UsersMapper.mapUsersToUpdateUsersModel(newUser);
    }

    @Override
    public void deleteUser(Long userId) {
        usersRepository.deleteById(userId);
    }
}
