package interview2026.mockprep.streams;

import java.util.List;

/**
 * Q2 核心邏輯 — 用 Streams 過濾並加總交易金額
 */
public class TradeStreamProcessor {

    /** 過濾 COMPLETED + amount > 1000，回傳總金額 */
    public double sumCompletedOver1000(List<Trade> trades) {
        return trades.stream()
                .filter(t -> "COMPLETED".equals(t.getStatus()))
                .filter(t -> t.getAmount() > 1000)
                .mapToDouble(Trade::getAmount)
                .sum();
    }
}
