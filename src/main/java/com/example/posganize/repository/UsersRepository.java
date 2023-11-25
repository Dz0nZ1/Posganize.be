package com.example.posganize.repository;

import com.example.posganize.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    @Query(value = "SELECT u.*, m.active\n" +
            "FROM Users u\n" +
            "INNER JOIN membership m ON u.user_id = m.user_id;", nativeQuery = true)
    Page<Users> findAllUsersWithMembership(Pageable pageable);

    @Query(value = "SELECT u.*, m.active\n" +
            "FROM Users u\n" +
            "INNER JOIN membership m ON u.user_id = m.user_id WHERE m.active = 1;", nativeQuery = true)
    Page<Users> findAllUsersWithActiveMembership(Pageable pageable);

    @Query(value = "SELECT u.*, m.active\n" +
            "FROM Users u\n" +
            "INNER JOIN membership m ON u.user_id = m.user_id WHERE m.active = 0;", nativeQuery = true)
    Page<Users> findAllUsersWithNotActiveMembership(Pageable pageable);


    @Query(value = "    SELECT u.*, m.active\n" +
            "    FROM Users u\n" +
            "    INNER JOIN membership m ON u.user_id = m.user_id WHERE u.user_id = :userId", nativeQuery = true)
    Users findUserWithMembership(@Param("userId") Long userId);



}
