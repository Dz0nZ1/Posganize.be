package com.example.posganize.repository;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,Long> {
    Membership findByUser(Users user);

    @Query(value = "SELECT *\n" +
            "FROM membership\n" +
            "WHERE user_id = :userId\n" +
            "ORDER BY expire_date DESC", nativeQuery = true)
    List<Membership> findAllMembershipByUserId(@Param("userId") Long userId);
}
