package com.sapient.javams.swipeeventlistener.reposistory;

import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface SwipeEventRepository extends JpaRepository<SwipeDetails, Integer> {

    SwipeDetails findByEmployeeIdAndSwipeDate(Integer id, LocalDate date);
    SwipeDetails findByMessageId( String messageId);

    @Transactional
    @Modifying
    @Query("update SwipeDetails c set c.swipeIns = ?1 where c.employeeId = ?2 and c.swipeDate=?3")
    void updateSwipeIns(String swipeIns, String employeeId, LocalDate date);

    @Transactional
    @Modifying
    @Query("update SwipeDetails c set c.swipeOuts = ?1 where c.employeeId = ?2 and c.swipeDate=?3")
    void updateSwipeOuts(String swipeOuts, String employeeId, LocalDate date);

    @Transactional
    @Modifying
    @Query("update SwipeDetails c set c.attendanceStatus = ?1 where c.employeeId = ?2 and c.swipeDate=?3")
    void updateStatus(String attendanceStatus, String employeeId, LocalDate date);



    //List<SwipeDetails> findAllByStatusAndDate(String status,LocalDate date);
}
