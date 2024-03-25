package com.sapient.javams.swipeeventlistener.email;

import com.sapient.javams.swipeeventlistener.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmailSenderTest {
    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    EmailSender emailSender;

    @Test
    void sendNotificationOfAbsenteeism() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("someone@email.com");
        doNothing().when(javaMailSender).send(message);
        emailSender.sendNotificationOfAbsenteeism(Arrays.asList(TestUtil.getSwipeDetails()));
        verify(javaMailSender, times(1)).send(message);
    }
}