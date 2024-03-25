package com.sapient.javams.swipeeventlistener.helpere;

import com.sapient.javams.attendance.core.SwipeEvent;
import com.sapient.javams.swipeeventlistener.model.SwipeDetails;
import com.sapient.javams.swipeeventlistener.reposistory.SwipeEventRepository;
import com.sapient.javams.swipeeventlistener.util.TestUtil;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.sapient.javams.swipeeventlistener.util.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PersistenceHelperTest {

    @Mock
    private SwipeEventRepository swipeEventRepository;
    @Inject
    private PersistenceHelper helper;
    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testSaveSwipeInEvent() {
        String swipeType = "in";
        SwipeEvent swipeEvent = TestUtil.getSwipeEvent(swipeType);
        SwipeDetails details = TestUtil.getSwipeDetails();
        when(swipeEventRepository.findByEmployeeIdAndSwipeDate(1, LocalDate.now())).thenReturn(details);
        helper.saveSwipeEvent(swipeEvent,"messageID","messageKEy");
        assertEventDetails();
    }

    @Test
    void testSaveSwipeOutEvent() {
        String swipeType = "out";
        SwipeEvent swipeEvent = TestUtil.getSwipeEvent(swipeType);
        SwipeDetails details = TestUtil.getSwipeDetails();
        when(swipeEventRepository.findByEmployeeIdAndSwipeDate(1, LocalDate.now())).thenReturn(details);
        helper.saveSwipeEvent(swipeEvent,"messageID","messageKEy"       );
        assertEventDetails();
    }

    private void assertEventDetails() {
        SwipeDetails saved = swipeEventRepository.findByEmployeeIdAndSwipeDate(1, LocalDate.now());
        assertThat(saved.getSwipeDate().isEqual(LocalDate.now()));
        assertThat(saved.getSwipeOuts().equals(SWIPE_OUT_TIME));
        assertThat(saved.getSwipeIns().equals(SWIPE_IN_TIME));
        assertThat(saved.getEmail().equals(EMAIL));
    }
}