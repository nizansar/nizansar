package com.sapient.javams.swipeeventlistener.util;

import com.sapient.javams.attendance.core.SwipeEvent;
import com.sapient.javams.swipeeventlistener.model.SwipeDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TestUtil {
    public static final String SWIPE_IN_TIME = String.valueOf(LocalDateTime.now());
    public static final String SWIPE_OUT_TIME = String.valueOf(LocalDateTime.now());
    public static final String EMAIL = "someone@email.com";
    public static final Long TOTAL_SWIPE_HOURS = 0L;
    public static final Integer EMPLOYEE_ID = 2;
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

    public static SwipeDetails getSwipeDetails() {
        SwipeDetails swipeDetails = new SwipeDetails();
        swipeDetails.setEmail(EMAIL);
        swipeDetails.setTotalHours(TOTAL_SWIPE_HOURS);
        swipeDetails.setSwipeDate(LocalDate.now());
        swipeDetails.setEmployeeId(EMPLOYEE_ID);
        swipeDetails.setSwipeIns(SWIPE_IN_TIME);
        swipeDetails.setSwipeOuts(SWIPE_OUT_TIME);
        return swipeDetails;
    }

    public static SwipeEvent getSwipeEvent(String swipeType) {
        SwipeEvent swipeEvent = new SwipeEvent();
        swipeEvent.setSwipeId(UUID.randomUUID().toString());
        swipeEvent.setSwipeInsAsStr(SWIPE_IN_TIME);
        swipeEvent.setSwipeOutsAsStr(SWIPE_OUT_TIME);
        swipeEvent.setEmployeeId(EMPLOYEE_ID);
        swipeEvent.setSwipeDate(LocalDate.now());
        swipeEvent.setSwipeType(swipeType);
        return swipeEvent;
    }

}
