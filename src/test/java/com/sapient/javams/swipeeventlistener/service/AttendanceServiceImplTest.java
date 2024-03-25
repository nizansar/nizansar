package com.sapient.javams.swipeeventlistener.service;

import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import com.sapient.javams.swipeeventlistener.reposistory.SwipeEventRepository;
import com.sapient.javams.swipeeventlistener.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.sapient.javams.swipeeventlistener.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceImplTest {

    @Mock
    private SwipeEventRepository swipeEventRepository;

    @InjectMocks
    private AttendanceServiceImpl service;

    @Test
    void getTotalOfficeHours() {
        Integer id = 2;

        SwipeDetails swipeDetails = TestUtil.getSwipeDetails();
        when(swipeEventRepository.findByEmployeeIdAndSwipeDate(id, LocalDate.now())).thenReturn(swipeDetails);
        SwipeDetails foundSwipeDetails = service.getTotalOfficeHours(id);
        assertEquals(EMPLOYEE_ID, foundSwipeDetails.getEmployeeId());
        assertEquals(SWIPE_IN_TIME, foundSwipeDetails.getSwipeIns());
        assertEquals(SWIPE_OUT_TIME, foundSwipeDetails.getSwipeOuts());
        assertEquals(TOTAL_SWIPE_HOURS, foundSwipeDetails.getTotalHours());
        assertEquals(EMAIL, foundSwipeDetails.getEmail());
    }
}