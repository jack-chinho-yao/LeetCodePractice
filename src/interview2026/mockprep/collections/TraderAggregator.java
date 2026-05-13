package interview2026.mockprep.collections;

import java.util.*;

/**
 * Q1 核心邏輯 — 抽成可測試的方法
 */
public class TraderAggregator {

    /** 計算每個 trader 的總交易金額 */
    public Map<String, Integer> sumByTrader(List<String[]> transactions) {
        Map<String, Integer> traderTotals = new HashMap<>();
        for (String[] txn : transactions) {
            String traderId = txn[0];
            int amount = Integer.parseInt(txn[1]);
            traderTotals.put(traderId, amount + traderTotals.getOrDefault(traderId, 0));
        }
        return traderTotals;
    }

    /** 找出金額最高的 trader ID */
    public String findMaxTrader(Map<String, Integer> traderTotals) {
        String maxTrader = null;
        int maxAmount = 0;
        for (Map.Entry<String, Integer> entry : traderTotals.entrySet()) {
            if (entry.getValue() >= maxAmount) {
                maxTrader = entry.getKey();
                maxAmount = entry.getValue();
            }
        }
        return maxTrader;
    }
}
