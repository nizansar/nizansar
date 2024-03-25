package com.sapient.javams.swipeeventlistener.service;

import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import com.sapient.javams.swipeeventlistener.reposistory.SwipeEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private SwipeEventRepository repository;

    private final String ZONE_IST = "Asia/Kolkata";

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

    public AttendanceServiceImpl(SwipeEventRepository repository) {
        this.repository = repository;
    }


    @Override
    public SwipeDetails getTotalOfficeHours(int employeeId) {
        SwipeDetails swipeDetails = repository.findByEmployeeIdAndSwipeDate(employeeId, getCurrentDate());
        if (swipeDetails != null &&
                swipeDetails.getTotalHours() == 0 && swipeDetails.getSwipeOuts() != null) {
            String ins = swipeDetails.getSwipeIns();
            LocalTime firstIn = getFirstIn(ins);
            String outs = swipeDetails.getSwipeOuts();
            LocalTime lastOut = getLastOut(outs);

            long hrs = Duration.between(firstIn, lastOut).toHours();
            swipeDetails.setTotalHours(hrs);
            LOGGER.info("******** TOTAL SWIPE HOURS  {} ********", hrs);
            return swipeDetails;
        } else return new SwipeDetails();

    }

    private LocalTime getFirstIn(String ins) {
        Objects.isNull(ins);

        String[] swipeIns = ins.split(",");
        //LocalTime dateTime = LocalDateTime.parse(swipeIns[0]).toLocalTime();
        LocalDateTime dateTime = LocalDateTime.parse(swipeIns[0].substring(0, 19), formatter);
        LOGGER.info("First swipe in is {}", dateTime);
        return dateTime.toLocalTime();
    }

    private LocalTime getLastOut(String outs) {
        Objects.isNull(outs);
        String[] swipeOuts = outs.split(",");

        LocalTime dateTime = LocalDateTime.parse(swipeOuts[swipeOuts.length - 1].substring(0, 19), formatter).toLocalTime();
        LOGGER.info("last swipe out is {}", dateTime);
        return dateTime;
    }

    private LocalDate getCurrentDate() {
        ZoneId zonedId = ZoneId.of(ZONE_IST);
        return LocalDate.now(zonedId);
    }

}
