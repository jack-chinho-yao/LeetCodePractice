package interview2026.mockprep.streams;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TradeStreamProcessorTest {

    private final TradeStreamProcessor processor = new TradeStreamProcessor();

    @Test
    void testSumCompletedOver1000_normalCase() {
        List<Trade> trades = Arrays.asList(
            new Trade("T001", "COMPLETED", 1500.0),
            new Trade("T002", "PENDING",   2000.0),
            new Trade("T003", "COMPLETED",  800.0),
            new Trade("T004", "COMPLETED", 1200.0),
            new Trade("T005", "FAILED",    1600.0)
        );

        // Only T001 (1500) + T004 (1200) = 2700
        assertEquals(2700.0, processor.sumCompletedOver1000(trades), 0.01);
    }

    @Test
    void testSumCompletedOver1000_noMatch() {
        List<Trade> trades = Arrays.asList(
            new Trade("T001", "PENDING", 5000.0),
            new Trade("T002", "COMPLETED", 500.0)  // COMPLETED but <= 1000
        );

        assertEquals(0.0, processor.sumCompletedOver1000(trades), 0.01);
    }

    @Test
    void testSumCompletedOver1000_emptyList() {
        assertEquals(0.0, processor.sumCompletedOver1000(Collections.emptyList()), 0.01);
    }

    @Test
    void testSumCompletedOver1000_allMatch() {
        List<Trade> trades = Arrays.asList(
            new Trade("T001", "COMPLETED", 2000.0),
            new Trade("T002", "COMPLETED", 3000.0)
        );

        assertEquals(5000.0, processor.sumCompletedOver1000(trades), 0.01);
    }
}
