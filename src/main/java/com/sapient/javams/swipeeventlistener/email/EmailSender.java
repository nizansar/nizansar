package com.sapient.javams.swipeeventlistener.email;

import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmailSender {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${com.sapient.notification.absenteeism.subject}")
    private String subject;

    @Value("${com.sapient.notification.absenteeism.message-content}")
    private String content;

    @Autowired
    private JavaMailSender mailSender;

    public void sendNotificationOfAbsenteeism(List<SwipeDetails> absentees) {
        absentees.forEach(absentee -> sendEmail(absentee, getEmailDetail(absentee)));
    }

    private void sendEmail(SwipeDetails swipeDetails, SimpleMailMessage message) {
        Objects.nonNull(swipeDetails);
        Objects.nonNull(message);
        Objects.nonNull(message.getTo());
        try {
            mailSender.send(message);
            log.info("Mail sent successfully");
        } catch (MailException exception) {
            log.error("Failure occurred while sending email");
        }
    }

    private SimpleMailMessage getEmailDetail(SwipeDetails absentee) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(String.valueOf(absentee.getEmail()));
        message.setSubject(subject);
        message.setText(content);
        return message;
    }

}