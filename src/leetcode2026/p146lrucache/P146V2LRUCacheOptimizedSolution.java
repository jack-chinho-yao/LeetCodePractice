package leetcode2026.p146lrucache;

import java.util.HashMap;
import java.util.Map;

public class P146V2LRUCacheOptimizedSolution {

    // HashMap + 手刻 Doubly-Linked List (dummy head/tail), Time O(1) get/put, Space O(capacity)
    class LRUCache {

        class Node {
            int key;   // evict 時要靠 node 反查 key 才能從 map 刪掉
            int value;
            Node prev;
            Node next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private final Map<Integer, Node> map = new HashMap<>();
        private final int capacity;
        // dummy head / tail — 不存真資料，只是哨兵，省掉所有 null 判斷
        private final Node head = new Node(0, 0);
        private final Node tail = new Node(0, 0);


        public LRUCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            Node node = map.get(key);
            if (node == null) {
                return -1;
            }
            moveToTail(node);
            return node.value;
        }

        public void put(int key, int value) {
            Node node = map.get(key);
            if (node != null) {
                // key 已存在 → update value + 移到最新
                node.value = value;
                moveToTail(node);
                return;
            }
            // 新 key → 超過 capacity 先 evict
            if (map.size() == capacity) {
                Node lru = head.next; // head 的下一個就是最舊的
                removeNode(lru);
                map.remove(lru.key);
            }
            Node newNode = new Node(key, value);
            addToTail(newNode);
            map.put(key, newNode);
        }

        // 從 linked list 拆掉 node（不動 HashMap）
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        // 把 node 接到 tail 前面（= 最新位置）
        private void addToTail(Node node) {
            node.prev = tail.prev;
            node.next = tail;
            tail.prev.next = node;
            tail.prev = node;
        }

        private void moveToTail(Node node) {
            removeNode(node);
            addToTail(node);
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new P146V2LRUCacheOptimizedSolution().new LRUCache(2);
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
 * Optimized approach — HashMap + 手刻 Doubly-Linked List:
 *   把 V1 裡那條慢吞吞的 ArrayList 換成 doubly-linked list。
 *   為什麼是「doubly」而不是 singly？因為要 O(1) 拆掉一個 node，必須知道它的前一個是誰。
 *   Singly list 只能從頭走到底找 prev → 又變回 O(n)。
 *
 * 結構:
 *   [dummy head] ⇄ [A] ⇄ [B] ⇄ [C] ⇄ [dummy tail]
 *                   LRU              MRU
 *   最舊 = head.next, 最新 = tail.prev
 *
 * 1. Dummy head/tail（哨兵節點）— 不存真資料，永遠不被刪除
 *    好處：removeNode/addToTail 裡永遠不用判斷 null，因為每個真實 node 的 prev/next
 *    最差也是指向 dummy，不會是 null。
 *    沒有哨兵的話，「node 在頭」「node 在尾」「list 只剩一個」「list 全空」
 *    每個都要一組 if — 就是手刻 LRU 最容易寫爆的地方。
 *
 * 2. 三個 helper methods 讓邏輯乾淨:
 *    - removeNode(node): 從 DLL 拆掉 node（改 2 個 pointer）
 *    - addToTail(node) : 插到 tail 前面（改 4 個 pointer）
 *    - moveToTail(node): removeNode + addToTail
 *    get 和 put 就只是在組合這三個，不用自己碰 pointer → 大幅降低出錯機會。
 *
 * 3. Node 裡為什麼要存 key？
 *    evict 時我們是從 DLL 拿到 head.next（最舊的 node），
 *    但還得從 HashMap 裡把它刪掉 → 需要 key。
 *    如果 Node 只存 value，就只能反向掃整個 map 找 → O(n)。這是最常被忽略的一點。
 *
 * 4. put() 一定要先判斷 key 是否已存在:
 *    - 已存在 → update value + moveToTail（不 evict、size 不變）
 *    - 不存在 → 檢查 capacity，需要時 evict head.next，再 addToTail 新 node
 *    漏掉這個判斷 → 同一個 key 會在 list 裡出現兩次，size 也算錯。
 *
 * 過程示意 (capacity = 2)，LeetCode 官方範例:
 *   put(1,1) → H ⇄ 1 ⇄ T
 *   put(2,2) → H ⇄ 1 ⇄ 2 ⇄ T                      (1 最舊, 2 最新)
 *   get(1)   → 1，moveToTail(1)  → H ⇄ 2 ⇄ 1 ⇄ T   (2 變最舊)
 *   put(3,3) → 滿了，evict head.next = 2
 *              → H ⇄ 1 ⇄ 3 ⇄ T
 *   get(2)   → -1（map 裡沒了）
 *   put(4,4) → 滿了，evict head.next = 1
 *              → H ⇄ 3 ⇄ 4 ⇄ T
 *   get(1)   → -1
 *   get(3)   → 3，moveToTail(3)  → H ⇄ 4 ⇄ 3 ⇄ T
 *   get(4)   → 4，moveToTail(4)  → H ⇄ 3 ⇄ 4 ⇄ T
 *
 * addToTail 的 4 個 pointer 順序（很容易寫反）:
 *   node.prev = tail.prev;   // ① 新 node 指向原本的最後一個
 *   node.next = tail;        // ② 新 node 指向 dummy tail
 *   tail.prev.next = node;   // ③ 原本最後一個 → 指向新 node（必須在 ④ 之前！）
 *   tail.prev = node;        // ④ dummy tail 回指新 node
 *   ⚠️ ③ 和 ④ 順序不能對調：先做 ④ 的話 tail.prev 就被覆蓋成 node 了，
 *      ③ 會變成 node.next = node（自己指自己），list 直接斷掉。
 *
 * V1 vs V2:
 *   V1 (HashMap + ArrayList) — get/put O(n)，不符題目的 O(1) 要求。
 *                              慢在 ArrayList remove 要線性搜尋 + 整批搬移。
 *                              （實測 V1 其實過得了 LeetCode，只是 runtime 很難看 —
 *                                ArrayList 常數小、cache 友善。細節見 V1 檔案上方註解。）
 *   V2 (HashMap + DLL, 本檔) — get/put O(1)。HashMap 負責「查得快」，
 *                              DLL 負責「排序調整得快」，兩個資料結構各司其職。
 *
 * 關鍵觀念 — 「兩個資料結構互補」:
 *   單用 HashMap → 查值 O(1) 但不知道誰最舊
 *   單用 DLL     → 順序好維護但查 key 要 O(n)
 *   合起來，HashMap 的 value 直接存「DLL 的 node 參考」，
 *   就能 O(1) 從 key 跳到 node，再 O(1) 調整順序。
 *   同樣的組合手法也出現在: LFU Cache (146 的進階版 460)、Insert Delete GetRandom O(1) (380)。
 *
 * Complexity:
 *   Time:  O(1) get / O(1) put
 *   Space: O(capacity) — map 和 DLL 各存最多 capacity 個 node
 *
 * 延伸 — 面試如果允許用內建資料結構:
 *   Java 的 LinkedHashMap 本身就是 HashMap + DLL，覆寫 removeEldestEntry 三行就解決:
 *     new LinkedHashMap<Integer,Integer>(capacity, 0.75f, true) {  // true = access order
 *         protected boolean removeEldestEntry(Map.Entry<Integer,Integer> eldest) {
 *             return size() > capacity;
 *         }
 *     };
 *   但面試官問 146 通常就是要看你手刻 DLL，先寫本檔這版比較保險，
 *   再補一句「知道 LinkedHashMap 可以一行解」加分。
 *   （這個寫法已經練過 → interview2026/mockprep/p5cache/
 *     MarketDataLruCacheV2LinkedHashMapRemoveEldestEntry.java）
 *
 * 相關練習:
 *   interview2026/mockprep/p5cache/MarketDataLruCacheV4Claude.java
 *   — 同樣的 dummy head/tail 手刻版，只是 key 是 String、value 是 double。
 *     146 就是它的 int 版本，結構一模一樣。
 */
