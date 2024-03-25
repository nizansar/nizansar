package com.sapient.javams.swipeeventlistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwipeEventListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwipeEventListenerApplication.class, args);
    }

}
