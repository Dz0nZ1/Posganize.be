package com.example.posganize.repository;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,Long> {
    Membership findByUser(Users user);

    @Query(value = "SELECT *\n" +
            "FROM membership\n" +
            "WHERE user_id = :userId\n", nativeQuery = true)
    Page<Membership> findAllMembershipByUserId(@Param("userId") Long userId, Pageable pageable);
}
