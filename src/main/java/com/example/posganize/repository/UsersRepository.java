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
            "FROM users u\n" +
            "INNER JOIN membership m ON u.user_id = m.user_id\n" +
            "WHERE m.expire_date = (\n" +
            "    SELECT MAX(expire_date)\n" +
            "    FROM membership\n" +
            "    WHERE user_id = u.user_id\n" +
            ") AND m.active = 1", nativeQuery = true)
    Page<Users> findAllUsersWithActiveMembership(Pageable pageable);

    @Query(value = "SELECT u.*, m.active\n" +
            "FROM users u\n" +
            "INNER JOIN membership m ON u.user_id = m.user_id\n" +
            "WHERE m.expire_date = (\n" +
            "    SELECT MAX(expire_date)\n" +
            "    FROM membership\n" +
            "    WHERE user_id = u.user_id\n" +
            ") AND m.active = 0", nativeQuery = true)
    Page<Users> findAllUsersWithoutActiveMembership(Pageable pageable);


    @Query(value = "SELECT u.*, m.active\n" +
            "FROM users u\n" +
            "INNER JOIN membership m ON u.user_id = m.user_id\n" +
            "WHERE m.expire_date = (\n" +
            "    SELECT MAX(expire_date)\n" +
            "    FROM membership\n" +
            "    WHERE user_id = :userId\n" +
            ") AND m.active = 1", nativeQuery = true)
    Users findUserWithMembership(@Param("userId") Long userId);



}
