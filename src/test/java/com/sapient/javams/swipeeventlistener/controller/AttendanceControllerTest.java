package com.sapient.javams.swipeeventlistener.controller;

import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import com.sapient.javams.swipeeventlistener.service.AttendanceService;
import com.sapient.javams.swipeeventlistener.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

import static com.sapient.javams.swipeeventlistener.util.TestUtil.EMAIL;
import static com.sapient.javams.swipeeventlistener.util.TestUtil.TOTAL_SWIPE_HOURS;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AttendanceControllerTest {

    MockMvc mockMvc;

    @MockBean
    AttendanceService service;

    @InjectMocks
    AttendanceController controller;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getTotalHoursForEmployee() throws Exception {
        assertTrue(Objects.nonNull(controller));
        SwipeDetails mockedDetails = TestUtil.getSwipeDetails();
        when(service.getTotalOfficeHours(anyInt())).thenReturn(mockedDetails);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/totalHours/{employeeId}", 2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalHours").value(TOTAL_SWIPE_HOURS));

    }

}