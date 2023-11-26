package com.example.posganize.repository;

import com.example.posganize.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long> {

    @Query(value = "SELECT * FROM training", nativeQuery = true)
    Set<Training> findAllSets();
}
