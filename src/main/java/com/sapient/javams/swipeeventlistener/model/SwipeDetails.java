package com.sapient.javams.swipeeventlistener.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SwipeDetails implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String messageId;

    @Column(name = "SWIPE_ID")
    private String swipeId;

    @Column(name = "EMPLOYEE_ID")
    private Integer employeeId;

    @Column(name = "SWIPE_INS")
    private String swipeIns;

    @Column(name = "SWIPE_OUT")
    private String swipeOuts;

    @Column(name = "DATE")
    private LocalDate swipeDate;

    @Column(name = "TYPE")
    private String swipeType;

    @Column(name = "STATUS")
    private String attendanceStatus;
    
    @Column(columnDefinition = "integer default 0")
    private long totalHours;

    @Column(name = "'EMAIL'")
    private String email;


}
