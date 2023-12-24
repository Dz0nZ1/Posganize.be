package com.example.posganize.services.users;


import com.example.posganize.entities.Users;
import com.example.posganize.exceptions.UserNotFoundException;
import com.example.posganize.exceptions.UserStatusException;
import com.example.posganize.mappers.UsersMapper;
import com.example.posganize.models.user.UpdateUsersModel;
import com.example.posganize.models.user.UserPageableModel;
import com.example.posganize.models.user.UsersModel;
import com.example.posganize.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;


    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserPageableModel getAllUsers(int pageNumber, int pageSize, String status) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Users> pagedUsers;
        switch (status) {
            case "all" -> pagedUsers = usersRepository.findAll(pageable);
            case "active" -> pagedUsers = usersRepository.findAllUsersWithActiveMembership(pageable);
            case "not-active" -> pagedUsers = usersRepository.findAllUsersWithoutActiveMembership(pageable);
            default -> throw new UserStatusException("Status not found: " + status);
        }
        return UserPageableModel.builder()
                .users(UsersMapper.mapUsersPageableToUsersModel(pagedUsers))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .numberOfUsers(pagedUsers.getTotalElements())
                .totalPages(pagedUsers.getTotalPages())
                .isLast(pagedUsers.isLast())
                .isFirst(pagedUsers.isFirst())
                .hasPrevious(pagedUsers.hasPrevious())
                .hasNext(pagedUsers.hasNext())
                .build();
    }

    @Override
    public UsersModel getUserById(Long userId) {
        var user = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        if(user.getMemberships() == null) {
            user = usersRepository.findUserWithMembership(userId);
        }
        if(user != null) return  UsersMapper.mapUsersToUsersModel(user);
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public UsersModel getUserByEmail(String Email) {
        return UsersMapper.mapUsersToUsersModel(usersRepository.findByEmail(Email).orElseThrow(() -> new UserNotFoundException("User not found")));

    }







    @Override
    public UsersModel updateUser(UpdateUsersModel updateUsersModel, Long userId) {
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
        return UsersMapper.mapUsersToUsersModel(newUser);
    }

    @Override
    public void deleteUser(Long userId) {
        usersRepository.deleteById(userId);
    }
}
