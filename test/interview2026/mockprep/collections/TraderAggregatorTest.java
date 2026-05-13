package interview2026.mockprep.collections;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TraderAggregatorTest {

    private final TraderAggregator aggregator = new TraderAggregator();

    @Test
    void testSumByTrader_normalCase() {
        List<String[]> transactions = Arrays.asList(
            new String[]{"T001", "500"},
            new String[]{"T002", "300"},
            new String[]{"T001", "200"},
            new String[]{"T003", "800"},
            new String[]{"T002", "150"}
        );

        Map<String, Integer> result = aggregator.sumByTrader(transactions);

        assertEquals(700, result.get("T001"));
        assertEquals(450, result.get("T002"));
        assertEquals(800, result.get("T003"));
        assertEquals(3, result.size());
    }

    @Test
    void testSumByTrader_singleTrader() {
        List<String[]> transactions = Arrays.asList(
            new String[]{"T001", "100"},
            new String[]{"T001", "200"}
        );

        Map<String, Integer> result = aggregator.sumByTrader(transactions);

        assertEquals(300, result.get("T001"));
        assertEquals(1, result.size());
    }

    @Test
    void testSumByTrader_emptyList() {
        Map<String, Integer> result = aggregator.sumByTrader(Collections.emptyList());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindMaxTrader() {
        Map<String, Integer> totals = new HashMap<>();
        totals.put("T001", 700);
        totals.put("T002", 450);
        totals.put("T003", 800);

        assertEquals("T003", aggregator.findMaxTrader(totals));
    }

    @Test
    void testFindMaxTrader_emptyMap() {
        assertNull(aggregator.findMaxTrader(Collections.emptyMap()));
    }
}
