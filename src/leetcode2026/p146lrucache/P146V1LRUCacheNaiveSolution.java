package leetcode2026.p146lrucache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P146V1LRUCacheNaiveSolution {

    // HashMap + ArrayList 記錄使用順序, Time O(n) get/put, Space O(capacity)
    class LRUCache {

        private final Map<Integer, Integer> map = new HashMap<>();
        private final List<Integer> recency = new ArrayList<>(); // index 0 = LRU, 最後 = MRU
        private final int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            }
            touch(key);
            return map.get(key);
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                map.put(key, value);
                touch(key);
                return;
            }
            if (map.size() == capacity) {
                int lru = recency.remove(0); // 最舊的
                map.remove(lru);
            }
            map.put(key, value);
            recency.add(key);
        }

        // 把 key 移到 recency 尾端（= 最新用過）
        private void touch(int key) {
            recency.remove(Integer.valueOf(key)); // 注意: 不能寫 remove(key)，見底部說明
            recency.add(key);
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new P146V1LRUCacheNaiveSolution().new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));  // 1
        cache.put(3, 3);                   // evict key 2
        System.out.println(cache.get(2));  // -1
        cache.put(4, 4);                   // evict key 1
        System.out.println(cache.get(1));  // -1
        System.out.println(cache.get(3));  // 3
        System.out.println(cache.get(4));  // 4
    }
}

/*
 * === Discussion Notes ===
 *
 * Problem: 設計一個 LRU (Least Recently Used) Cache，支援 get / put，
 *          超過 capacity 時淘汰「最久沒被使用」的 key。
 *          題目要求 get 和 put 都要 O(1) — 這個 V1 naive 版不符合該要求（是 O(n)），
 *          但先寫出來能看清楚「LRU 到底在維護什麼」。
 *
 *          ⚠️ 實測補充：V1 這版「不符 O(1) 要求」但**實際上是可以 AC 的**，只是 runtime 很難看。
 *          原因是 ArrayList 的常數極小 —— 底層是連續記憶體，indexOf 的線性掃描和 remove 的
 *          搬移都走 System.arraycopy，cache 命中率極高。所以就算最差 2×10^5 × 3000 ≈ 6 億次，
 *          在 Java 裡也只是跑個一兩秒，剛好被時限收下。
 *          教訓：大 O 決定成長曲線，常數決定你今天過不過得了 —— 兩個都要看，
 *          不能因為「O(n) 看起來很糟」就直接斷定 TLE。
 *
 * Naive approach（本檔）:
 *   - HashMap<key, value> → 查值 O(1)
 *   - ArrayList<Integer> recency → 額外維護一條「使用順序」，index 0 最舊、最後最新
 *   - get(key)  : 有的話把 key 從 list 裡拔掉再 append 到尾端 → O(n)（拔掉要線性搜尋 + 搬移）
 *   - put(key,v): 已存在 → 更新值 + touch
 *                 不存在 → 滿了就 remove(0) 淘汰最舊，再 append → O(n)
 *
 * 為什麼是 O(n)：
 *   ArrayList 的 remove(Object) 要線性掃描找到位置，找到後還要把後面元素整批往前搬。
 *   remove(0) 也是整批搬移。所以每次 get/put 最差都是 O(n) — 這就是 V2 要解掉的痛點。
 *
 * ⚠️ Java 陷阱: recency.remove(key) vs recency.remove(Integer.valueOf(key))
 *   List 有兩個 remove overload：
 *     - remove(int index)    → 「刪除第 index 個位置」
 *     - remove(Object o)     → 「刪除值等於 o 的元素」
 *   key 宣告成 int，直接寫 recency.remove(key) 會挑中 remove(int index)，
 *   變成「刪掉第 key 個位置」→ 邏輯完全錯，key 大於 size 時還會噴
 *   IndexOutOfBoundsException。
 *   必須寫 recency.remove(Integer.valueOf(key)) 強制走 remove(Object)。
 *   （如果 list 宣告成 List<Integer> 且變數本身是 Integer 就不會有這問題，
 *     但這裡 key 是 int，autoboxing 不會發生 — Java 優先選不需要 boxing 的 overload。）
 *
 * 過程示意 (capacity = 2)，LeetCode 官方範例:
 *   put(1,1) → map{1=1}      recency[1]
 *   put(2,2) → map{1=1,2=2}  recency[1,2]
 *   get(1)   → 1，touch(1)   recency[2,1]       ← 1 變最新，2 變最舊
 *   put(3,3) → 滿了，淘汰 recency[0]=2
 *              map{1=1,3=3}  recency[1,3]
 *   get(2)   → -1（已被 evict）
 *   put(4,4) → 滿了，淘汰 recency[0]=1
 *              map{3=3,4=4}  recency[3,4]
 *   get(1)   → -1
 *   get(3)   → 3，touch(3)   recency[4,3]
 *   get(4)   → 4，touch(4)   recency[3,4]
 *
 * 關鍵觀念:
 *   「LRU」需要同時做到兩件事 —
 *     (a) 用 key 快速查值             → HashMap 就夠了
 *     (b) 知道誰最舊、並能快速調整順序 → ArrayList 做得到但很慢
 *   V2 的重點就是把 (b) 換成 doubly-linked list，讓「拔掉 + 插到尾端」變成改幾個
 *   pointer 的 O(1) 操作。
 *
 * Complexity:
 *   Time:  O(n) get / O(n) put   ← 不符題目 O(1) 要求（但實測可 AC，見上方補充）
 *   Space: O(capacity)
 *
 * 下一步 → P146V2LRUCacheOptimizedSolution（HashMap + 手刻 DLL，真正的 O(1)）
 */
