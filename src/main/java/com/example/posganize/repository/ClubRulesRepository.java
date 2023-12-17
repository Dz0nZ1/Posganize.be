package com.example.posganize.repository;

import com.example.posganize.entities.ClubRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRulesRepository extends JpaRepository<ClubRules, Long> {
}
