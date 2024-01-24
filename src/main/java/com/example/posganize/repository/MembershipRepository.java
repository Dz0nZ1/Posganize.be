package com.example.posganize.repository;

import com.example.posganize.entities.Membership;
import com.example.posganize.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,Long> {
    Membership findByUser(Users user);

    List<Membership> findAllByUser(Users users);

    List<Membership> findByExpireDateBeforeAndActiveTrue(LocalDateTime localDate);


    @Query(value = "SELECT *\n" +
            "FROM membership\n" +
            "WHERE user_id = :userId\n", nativeQuery = true)
    Page<Membership> findAllMembershipByUserId(@Param("userId") Long userId, Pageable pageable);


    @Query(value = "SELECT MONTHNAME(start_date) AS month, "
            + "SUM(price) AS price, COUNT(membership_id) AS members, YEAR(start_date) As year "
            + "FROM membership "
            + "WHERE start_date BETWEEN :fromDate AND :toDate "
            + "GROUP BY YEAR(start_date), MONTHNAME(start_date), MONTH(start_date) "
            + "ORDER BY YEAR(start_date), MONTH(start_date)", nativeQuery = true)
    List<Map<String, Object>> getRevenueAndMembersByMonth(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

    @Query(value = "SELECT SUM(price) AS totalRevenue, COUNT(membership_id) AS totalMembers "
            + "FROM membership "
            + "WHERE MONTH(start_date) IN (SELECT MONTH(start_date) FROM membership "
            + "WHERE start_date BETWEEN :fromDate AND :toDate) "
            + "AND YEAR(start_date) IN (SELECT YEAR(start_date) FROM membership "
            + "WHERE start_date BETWEEN :fromDate AND :toDate)", nativeQuery = true)
    Map<String, Object> getTotalRevenueAndMembers(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);




    @Query(value = "SELECT COUNT(membership_id) FROM membership", nativeQuery = true)
    Long countMembers();

    @Query(value = "SELECT SUM(price) FROM membership", nativeQuery = true)
    Double calculateTotalRevenue();

}
