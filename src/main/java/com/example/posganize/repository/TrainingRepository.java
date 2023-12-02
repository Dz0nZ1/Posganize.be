package com.example.posganize.repository;

import com.example.posganize.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long> {

    @Query(value = "SELECT * FROM training", nativeQuery = true)
    Set<Training> findAllSets();


    @Query(value = "SELECT t.name AS training, COUNT(u.user_id) AS numberOfMembers " +
            "FROM training t " +
            "JOIN membership_training mt ON t.training_id = mt.training_id " +
            "JOIN membership m ON mt.membership_id = m.membership_id " +
            "JOIN users u ON m.user_id = u.user_id " +
            "WHERE m.start_date BETWEEN :fromDate AND :toDate " +
            "GROUP BY t.name", nativeQuery = true)
    List<Map<String, Long>> countUsersByTraining( @Param("fromDate") LocalDate fromDate,
                                                  @Param("toDate") LocalDate toDate);



    @Query(value = "SELECT COUNT(DISTINCT mt.training_id) FROM membership_training mt " +
            "JOIN membership m ON mt.membership_id = m.membership_id " +
            "WHERE m.start_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Long countTrainingsBetweenDates(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    @Query(value = "SELECT t.name FROM training t " +
            "JOIN membership_training mt ON t.training_id = mt.training_id " +
            "JOIN membership m ON mt.membership_id = m.membership_id " +
            "WHERE m.start_date BETWEEN :fromDate AND :toDate " +
            "GROUP BY t.training_id, t.name " +
            "ORDER BY SUM(t.price) DESC " +
            "LIMIT 1", nativeQuery = true)
    String findTrainingNameWithMaxRevenueBetweenDates(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );


}
