package com.sapient.javams.swipeeventlistener.helpere;

import com.sapient.javams.attendance.core.SwipeEvent;
import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import com.sapient.javams.swipeeventlistener.reposistory.SwipeEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@Transactional
public class PersistenceHelper {
    private static Logger logger = LoggerFactory.getLogger(PersistenceHelper.class);
    SwipeEventRepository repository;

    public PersistenceHelper(SwipeEventRepository repository) {
        this.repository = repository;
    }

    public void saveSwipeEvent(SwipeEvent swipeEvent, String messageId, String messageKey) {

        SwipeDetails savedDetails = repository.findByMessageId(messageId);
        if (savedDetails == null) {
            SwipeDetails swipeDetails = getSwipeDetails(swipeEvent);
            swipeDetails.setMessageId(messageId);
            repository.save(swipeDetails);
        } else {
            updateSwipes(swipeEvent, savedDetails);
        }
    }

    private void updateSwipes(SwipeEvent swipeEvent, SwipeDetails savedDetails) {
        String IN = "in";
        String SEPARATOR = ",";
        if (IN.equals(swipeEvent.getSwipeType())) {
            String newSwipeIn = savedDetails.getSwipeIns().concat(SEPARATOR).concat(swipeEvent.getSwipeInsAsStr());
            repository.updateSwipeIns(newSwipeIn, String.valueOf(swipeEvent.getEmployeeId()),LocalDate.now());
        } else {
            String newSwipeOut = savedDetails.getSwipeOuts().concat(SEPARATOR).concat(swipeEvent.getSwipeOutsAsStr());
            repository.updateSwipeOuts(newSwipeOut, String.valueOf(swipeEvent.getEmployeeId()), LocalDate.now());
        }

    }

    private SwipeDetails getSwipeDetails(SwipeEvent swipeEvent) {
        SwipeDetails details = new SwipeDetails();
        details.setSwipeId(swipeEvent.getSwipeId());
        details.setEmployeeId(swipeEvent.getEmployeeId());
        details.setSwipeIns(swipeEvent.getSwipeInsAsStr());
        details.setSwipeOuts(swipeEvent.getSwipeInsAsStr());
        details.setSwipeDate(swipeEvent.getSwipeDate());
        details.setSwipeType(swipeEvent.getSwipeType());
        return details;
    }
}
