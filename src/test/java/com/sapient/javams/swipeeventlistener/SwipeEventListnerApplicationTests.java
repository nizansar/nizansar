package com.sapient.javams.swipeeventlistener;

import com.sapient.javams.swipeeventlistener.controller.AttendanceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SwipeEventListenerApplicationTests {
    @Autowired
    AttendanceController controller;


    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
