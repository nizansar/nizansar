package com.sapient.javams.swipeeventlistener.scheduler;

import com.sapient.javams.swipeeventlistener.email.EmailSender;
import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import com.sapient.javams.swipeeventlistener.reposistory.SwipeEventRepository;
import com.sapient.javams.swipeeventlistener.service.AttendanceService;
import com.sapient.javams.swipeeventlistener.util.TestUtil;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.sapient.javams.swipeeventlistener.util.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DailyEmployeeStatusGeneratorTest {
    @Autowired
    private TaskScheduler taskScheduler;
    @Mock
    private SwipeEventRepository swipeEventRepository;
    @Mock
    private AttendanceService service;
    @Mock
    private EmailSender emailSender;

    @Inject
    private DailyEmployeeStatusGenerator midnightJob;

    @Autowired
    private ScheduledTaskHolder scheduledTaskHolder;

    @Test
    void testUpdateStatus() {
        Set<ScheduledTask> scheduledTasks = scheduledTaskHolder.getScheduledTasks();
        scheduledTasks.forEach(scheduledTask -> scheduledTask.getTask().getRunnable().getClass().getDeclaredMethods());
        long count = scheduledTasks.stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals("@midnight") && cronTask.toString().equals("com.sapient.javams.swipeeventlistener.scheduler.updateStatus"))
                .count();
        assertThat(count).isEqualTo(0);
    }

    @Test
    void testSchedulerJob() {
        List<SwipeDetails> swipeDetails = Arrays.asList(TestUtil.getSwipeDetails());
        when(swipeEventRepository.findAll()).thenReturn(swipeDetails);

        doNothing().when(emailSender).sendNotificationOfAbsenteeism(anyList());
        when(service.getTotalOfficeHours(anyInt())).thenReturn(swipeDetails.get(0));
        List<SwipeDetails> savedSwipeDetails = swipeEventRepository.findAll();
        midnightJob.updateStatus();
        emailSender.sendNotificationOfAbsenteeism(swipeDetails);
        SwipeDetails saved = savedSwipeDetails.get(0);
        assertThat(saved.getSwipeDate().isEqual(LocalDate.now()));
        assertThat(saved.getSwipeOuts().equals(SWIPE_OUT_TIME));
        assertThat(saved.getSwipeIns().equals(SWIPE_IN_TIME));
        assertThat(saved.getEmail().equals(EMAIL));
    }

}