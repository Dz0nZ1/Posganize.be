package com.example.posganize.repository;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,Long> {
    Membership findByUser(Users user);
}
