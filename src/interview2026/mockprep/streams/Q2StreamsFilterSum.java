package interview2026.mockprep.streams;

import java.util.*;

/**
 * Q2 - Streams API：過濾 COMPLETED + amount > 1000 的交易，算出總金額
 */
public class Q2StreamsFilterSum {

    public static void main(String[] args) {
        List<Trade> trades = Arrays.asList(
            new Trade("T001", "COMPLETED", 1500.0),
            new Trade("T002", "PENDING",   2000.0),
            new Trade("T003", "COMPLETED",  800.0),
            new Trade("T004", "COMPLETED", 1200.0),
            new Trade("T005", "FAILED",    1600.0)
        );

        // TODO: 用 Streams 完成以下三步
        // 1. filter status == "COMPLETED"
        // 2. filter amount > 1000
        // 3. 算出總金額 (mapToDouble + sum)
        // 期望輸出：Total = 2700.0 (T001 + T004)

    }
}
