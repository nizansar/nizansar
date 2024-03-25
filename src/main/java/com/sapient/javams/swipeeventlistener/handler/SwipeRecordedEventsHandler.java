package com.sapient.javams.swipeeventlistener.handler;


import com.sapient.javams.attendance.core.SwipeEvent;
import com.sapient.javams.swipeeventlistener.helpere.PersistenceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "swipe-event-store-topic")
public class SwipeRecordedEventsHandler {

    PersistenceHelper persistenceHelper;

    public SwipeRecordedEventsHandler(PersistenceHelper persistenceHelper) {
        this.persistenceHelper = persistenceHelper;

    }

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(@Payload SwipeEvent swipeEvent, @Header("messageId") String messageId,
                       @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        LOGGER.info("Received a new swipe event for the employee with id {}", swipeEvent.getEmployeeId());
        persistenceHelper.saveSwipeEvent(swipeEvent,messageId,messageKey);
    }
}
