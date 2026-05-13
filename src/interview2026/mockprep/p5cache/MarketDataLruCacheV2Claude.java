package interview2026.mockprep.p5cache;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * V2Claude — LinkedHashMap 正解版。Time O(1) get/put, Space O(capacity)
 *
 * 關鍵設計：
 * 1. LinkedHashMap(capacity, 0.75f, true) — 第三個參數 accessOrder=true 開啟 LRU 順序
 *    - true: 每次 get/put 都把 entry 移到尾巴（access order = LRU）
 *    - false (預設): insertion order = FIFO，不是 LRU
 *
 * 2. Override removeEldestEntry — LinkedHashMap 每次 put 後自動呼叫此方法
 *    回傳 true 就自動刪掉 head（最久沒被存取的 entry），不需手動 remove
 *
 * 3. 0.75f 是 load factor 預設值（bucket 填到 75% 就 resize），不需要改
 *    這裡寫 0.75f 只是因為 constructor 沒有 (capacity, accessOrder) 的版本
 *
 * 4. map 宣告成 final — final 鎖的是「reference 不能指向別的物件」，
 *    map 內容（put/get/remove）照樣能改。final field 可以在 constructor 裡第一次賦值。
 *
 * 5. getOrDefault(symbol, -1.0) — 避免 map.get() 回 null 時 auto-unbox NPE
 */
public class MarketDataLruCacheV2Claude {
    private final LinkedHashMap<String, Double> map;

    public MarketDataLruCacheV2Claude(int capacity) {
        map = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Double> eldest) {
                return size() > capacity;
            }
        };
    }

    public double get(String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("symbol must not be null.");
        }
        return map.getOrDefault(symbol, -1.0);
    }

    public void put(String symbol, double price) {
        if (symbol == null) {
            throw new IllegalArgumentException("symbol must not be null.");
        }
        map.put(symbol, price);
    }

    public static void main(String[] args) {
        MarketDataLruCacheV2Claude cache = new MarketDataLruCacheV2Claude(3);

        cache.put("NGX-WCS-JUL26", 95.5);
        cache.put("NGX-AECO-AUG26", 2.10);
        cache.put("NGX-MSW-SEP26", 88.0);
        System.out.println(cache.get("NGX-WCS-JUL26")); // 95.5 — 碰過，變最新

        cache.put("NGX-C5-OCT26", 12.0); // capacity=3，evict 最久沒碰的 AECO
        System.out.println(cache.get("NGX-AECO-AUG26")); // -1.0 — 被 evict 了
        System.out.println(cache.get("NGX-WCS-JUL26"));  // 95.5 — 還在，因為剛被 get 過
    }
}
