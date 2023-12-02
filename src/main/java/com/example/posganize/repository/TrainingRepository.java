package com.example.posganize.repository;

import com.example.posganize.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long> {

    @Query(value = "SELECT * FROM training", nativeQuery = true)
    Set<Training> findAllSets();


    @Query(value = "SELECT \n" +
            "    t.name AS training,\n" +
            "    COUNT(u.user_id) AS numberOfMembers\n" +
            "FROM \n" +
            "    Training t\n" +
            "JOIN \n" +
            "    membership_training mt ON t.training_id = mt.training_id\n" +
            "JOIN \n" +
            "    Membership m ON mt.membership_id = m.membership_id\n" +
            "JOIN \n" +
            "    users u ON m.user_id = u.user_id\n" +
            "GROUP BY \n" +
            "    t.name", nativeQuery = true)
    List<Map<String, Long>> countUsersByTraining();

}
