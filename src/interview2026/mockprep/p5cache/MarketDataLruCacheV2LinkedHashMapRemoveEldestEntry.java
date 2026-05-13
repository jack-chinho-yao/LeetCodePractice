package interview2026.mockprep.p5cache;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * V2 — 修正了 V1 大部分問題，核心邏輯正確。剩餘小問題：
 *
 * ISSUE 1: map 應該加 final（只在 constructor 賦值一次）
 * ISSUE 2: get() 的 price 變數多餘，可以直接 return map.getOrDefault(...)
 * ISSUE 3: 未刪除未使用的 import（ReadWriteLock 等已在修正時移除）
 *
 * → 修正版見 MarketDataLruCacheV2Claude.java
 */
public class MarketDataLruCacheV2LinkedHashMapRemoveEldestEntry {
    private LinkedHashMap<String, Double> map; // ISSUE 1: 應加 final

    public MarketDataLruCacheV2LinkedHashMapRemoveEldestEntry(int capacity){
        map = new LinkedHashMap<>(capacity, 0.75f, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Double> eldest) {
                return size() > capacity;
            }
        };
    }
    public double get(String symbol){
        if (symbol == null){
            throw new IllegalArgumentException("symbol must not be null.");
        }
        double price = 0.0; // ISSUE 2: 多餘，直接 return map.getOrDefault(symbol, -1.0)
        price = map.getOrDefault(symbol,-1.0);
        return price;
    }
    public void put(String symbol, double price){
        if (symbol == null){
            throw new IllegalArgumentException("symbol must not be null.");
        }
        map.put(symbol, price); // ✅ 正確 — removeEldestEntry 自動處理 eviction，不用手動 remove
    }

    public static void main(String[] args) {
        MarketDataLruCacheV2LinkedHashMapRemoveEldestEntry marketDataLruCache = new MarketDataLruCacheV2LinkedHashMapRemoveEldestEntry(3);
    }
}
