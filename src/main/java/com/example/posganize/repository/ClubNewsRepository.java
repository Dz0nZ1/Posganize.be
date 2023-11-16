package com.example.posganize.repository;

import com.example.posganize.entities.ClubNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubNewsRepository extends JpaRepository<ClubNews, Long> {
}
