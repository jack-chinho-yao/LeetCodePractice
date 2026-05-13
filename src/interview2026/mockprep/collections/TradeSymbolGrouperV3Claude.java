package interview2026.mockprep.collections;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * ============================================================================
 * Thread-Safety 方案比較
 * ============================================================================
 *
 * 方案 A：各容器自保（V3）
 *   - ConcurrentHashMap + Collections.synchronizedList
 *   - 每個容器各自 thread-safe，但兩個 map 之間的操作不是原子的
 *   - 可能出現「tradeId 加了但 sum 還沒更新」的中間狀態
 *
 * 方案 B：ReadWriteLock 統一管（本檔案）
 *   - 用一把 ReadWriteLock 保護所有讀寫
 *   - 底層用普通 HashMap + ArrayList 就夠（鎖已經保護了，不需要 concurrent 容器）
 *   - 寫入兩個 map 是原子的，不會有中間狀態
 *
 * ============================================================================
 * ReadWriteLock 規則
 * ============================================================================
 *
 *   讀 vs 讀 → 可以同時（不互斥）
 *   讀 vs 寫 → 互斥（讀要等寫完）
 *   寫 vs 寫 → 互斥
 *
 *   重點：讀方法也要加 readLock！
 *   如果讀不加鎖，寫加鎖也白加，根本擋不住讀 thread 看到不一致的資料。
 *
 * ============================================================================
 * merge() 筆記
 * ============================================================================
 *
 *   map.merge(key, value, remappingFunction)
 *     - key 不存在 → 直接放 value
 *     - key 已存在 → 新值 = remappingFunction.apply(舊值, value)
 *
 *   第三個參數是 BiFunction<V, V, V>，接收 (oldValue, newValue)，不是 key！
 *     正確：merge(symbol, amount, (old, new) -> old + new)  或  Double::sum
 *     錯誤：merge(symbol, amount, k -> map.get(k) + amount) ← k 是 oldValue 不是 key，會 NPE
 *
 *   ConcurrentHashMap.merge() 的 remappingFunction 裡面不能再去讀寫同一張 map
 *   （Javadoc: "must not attempt to update any other mappings of this Map"）
 *
 * ============================================================================
 * List.copyOf() / Set.copyOf()
 * ============================================================================
 *
 *   回傳不可變副本，防止外部拿到內部引用後直接修改資料。
 *   面試加分小細節：defensive copy。
 *
 * ============================================================================
 */
public class TradeSymbolGrouperV3Claude {
    // 用普通 HashMap，因為 ReadWriteLock 已經保護所有存取，不需要 ConcurrentHashMap
    private final Map<String, List<String>> symbolToTradeIdMap = new HashMap<>();
    private final Map<String, Double> symbolToSum = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    // writeLock：同時只有一個 thread 能寫，且寫的時候 readLock 也會被擋住
    public void recordTrade(String tradeId, String symbol, double amount) {
        lock.writeLock().lock();
        try {
            // 兩步都在 writeLock 裡面 → 原子性，不會出現中間狀態
            symbolToTradeIdMap.computeIfAbsent(symbol, k -> new ArrayList<>()).add(tradeId);
            symbolToSum.merge(symbol, amount, Double::sum);
        } finally {
            lock.writeLock().unlock(); // finally 確保例外時也會放鎖，不會 deadlock
        }
    }

    // readLock：多個 thread 可以同時讀，但如果有人在寫就要等
    public List<String> getTradeIdsBySymbol(String symbol) {
        lock.readLock().lock();
        try {
            return List.copyOf(symbolToTradeIdMap.getOrDefault(symbol, List.of()));
        } finally {
            lock.readLock().unlock();
        }
    }

    public double getTotalVolume(String symbol) {
        lock.readLock().lock();
        try {
            return symbolToSum.getOrDefault(symbol, 0.0);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Set<String> getAllSymbols() {
        lock.readLock().lock();
        try {
            return Set.copyOf(symbolToSum.keySet());
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        TradeSymbolGrouperV3Claude grouper = new TradeSymbolGrouperV3Claude();
        grouper.recordTrade("TR001", "NGX-AB-E", 150000.0);
        grouper.recordTrade("TR002", "NGX-AB-G", 230000.0);
        grouper.recordTrade("TR003", "NGX-AB-E", 175000.0);
        grouper.recordTrade("TR004", "WTI",      500000.0);
        grouper.recordTrade("TR005", "NGX-AB-E", 120000.0);

        System.out.println(grouper.getTradeIdsBySymbol("NGX-AB-E"));  // -> [TR001, TR003, TR005]
        System.out.println(grouper.getTradeIdsBySymbol("WTI"));       // -> [TR004]
        System.out.println(grouper.getTradeIdsBySymbol("UNKNOWN"));   // -> []

        System.out.println(grouper.getTotalVolume("NGX-AB-E"));       // -> 445000.0
        System.out.println(grouper.getTotalVolume("UNKNOWN"));        // -> 0.0

        System.out.println(grouper.getAllSymbols());                   // -> [NGX-AB-E, NGX-AB-G, WTI]
    }
}
