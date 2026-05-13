package interview2026.mockprep.p5cache;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * V1 — 第一版，有以下 Bug（練習用，保留原始錯誤）：
 *
 * BUG 1: LinkedHashMap 沒開 accessOrder=true → 這是 insertion order (FIFO)，不是 LRU
 *        修法: new LinkedHashMap<>(capacity, 0.75f, true)
 *
 * BUG 2: get() 裡 map.get(symbol) 找不到時回傳 null，auto-unbox 成 double → NPE
 *        修法: map.getOrDefault(symbol, -1.0)
 *
 * BUG 3: get() 在 LRU 裡其實是寫操作（要移動 entry），用 readLock 不夠安全
 *        但更根本的問題是：先把鎖拿掉，專注單線程正確性，thread safety 用口述
 *
 * BUG 4: put() 的 eviction 條件是 size() > capacity，但應該是 size() >= capacity
 *        因為 size == capacity 時放新的就超過了，要先 evict
 *        而且沒判斷 symbol 是否已存在（update 不需要 evict）
 *
 * BUG 5: removeEldestEntry() 不該是 public method — LinkedHashMap 有內建的
 *        protected removeEldestEntry(Map.Entry) 可以 override，
 *        每次 put 後自動被呼叫，回傳 true 就自動刪最舊的
 *
 * BUG 6: capacity 應該是 final（只在 constructor 賦值一次）
 */
public class MarketDataLruCacheV1 {
    private final LinkedHashMap<String, Double> map = new LinkedHashMap<>(); // BUG 1: 沒開 accessOrder=true
    private int capacity = 0; // BUG 6: 應該是 final
    private final ReadWriteLock lock = new ReentrantReadWriteLock(); // BUG 3: 先別加鎖，專注單線程正確性

    public MarketDataLruCacheV1(int capacity){
        this.capacity = capacity;
    }
    public double get(String symbol){
        lock.readLock().lock(); // BUG 3: get 在 LRU 是寫操作（移動 entry），readLock 不夠
        double price = 0.0;
        try {
            price = map.get(symbol); // BUG 2: 找不到回 null → auto-unbox NPE，應用 getOrDefault
        }finally {
            lock.readLock().unlock();
        }
        return price;
    }
    public void put(String symbol, double price){
        lock.writeLock().lock();
        try {
            if (symbol == null){
                throw new IllegalArgumentException("symbol must not be null.");
            }
            if (map.size() > capacity){ // BUG 4: 應該是 >= capacity，且沒判斷 symbol 是否已存在
                String firstKey = map.keySet().iterator().next();
                map.remove(firstKey);
            }
            map.put(symbol, price);
        }finally {
            lock.writeLock().unlock();
        }
    }

    public void removeEldestEntry(){ // BUG 5: 不該手動寫，LinkedHashMap 內建 override 就好
        lock.writeLock().lock();
        try {
            String firstKey = map.keySet().iterator().next();
            map.remove(firstKey);
        }finally {
            lock.writeLock().unlock();
        }

    }

    public static void main(String[] args) {

    }
}
