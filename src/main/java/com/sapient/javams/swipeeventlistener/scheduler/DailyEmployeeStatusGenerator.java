package com.sapient.javams.swipeeventlistener.scheduler;

import com.sapient.javams.swipeeventlistener.email.EmailSender;
import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import com.sapient.javams.swipeeventlistener.reposistory.SwipeEventRepository;
import com.sapient.javams.swipeeventlistener.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DailyEmployeeStatusGenerator {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private SwipeEventRepository repository;
    private AttendanceService service;

    private EmailSender emailSender;
    List<SwipeDetails> ABSENTEES = new ArrayList<>();

    public DailyEmployeeStatusGenerator(SwipeEventRepository repository, AttendanceService service, EmailSender emailSender) {
        this.repository = repository;
        this.service = service;
        this.emailSender = emailSender;
    }

    @Scheduled(cron = "@midnight", zone = "Asia/Kolkata")
    public void updateStatus() {
        LOGGER.info("Updating attendance status for all employees");
        List<SwipeDetails> swipeDetails = repository.findAll();
        swipeDetails.forEach(this::updateAttendanceStatus);
        emailSender.sendNotificationOfAbsenteeism(ABSENTEES);
        LOGGER.info(" Attendance status updated for all employees");
    }

    private void updateAttendanceStatus(SwipeDetails details) {
        String id = String.valueOf(details.getEmployeeId());
        SwipeDetails updated = service.getTotalOfficeHours(details.getEmployeeId());
        final long totalHours = updated.getTotalHours();
        details.setTotalHours(totalHours);
        String status = getStatus(totalHours);
        if ("absent".equals(status))
            ABSENTEES.add(details);
        details.setAttendanceStatus(status);
        repository.updateStatus(status, id, LocalDate.now());
    }

    private void sendNotificationOfAbsenteeism() {
    }

    private String getStatus(long hours) {
        return hours < 4 ? "absent" : (hours > 4 && hours < 8 ? "half-day" : "present");
    }
}
