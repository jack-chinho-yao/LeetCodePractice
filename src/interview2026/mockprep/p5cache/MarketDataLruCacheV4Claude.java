package interview2026.mockprep.p5cache;

import java.util.HashMap;
import java.util.Objects;

/*
 * V4Claude — 手刻 HashMap + Doubly-Linked List 正解版。Time O(1) get/put, Space O(capacity)
 *
 * 關鍵設計：
 * 1. Dummy head/tail（哨兵節點）— 不存真資料，永遠不被刪除
 *    好處：removeNode/addToTail 裡永遠不用判斷 null，因為每個真實 node 的 prev/next
 *    最差也是指向 dummy，不會是 null
 *    結構: [dummy head] ⇄ [A] ⇄ [B] ⇄ [C] ⇄ [dummy tail]
 *                         LRU              MRU
 *    最舊 = head.next, 最新 = tail.prev
 *
 * 2. 三個 helper methods 讓邏輯乾淨：
 *    - removeNode(node): 從 DLL 拆掉 node（改 2 個 pointer）
 *    - addToTail(node): 插到 tail 前面（改 4 個 pointer）
 *    - moveToTail(node): removeNode + addToTail
 *
 * 3. HashMap<String, Node> — O(1) 用 key 找到 node
 *    Node 裡存 key 是因為 evict 時拿到 head.next node，需要 key 才能從 HashMap 刪
 *
 * 4. put() 要先判斷 symbol 是否已存在：
 *    - 已存在 → update value + moveToTail（不需要 evict）
 *    - 不存在 → 檢查 capacity，需要時 evict head.next，再 addToTail 新 node
 */
public class MarketDataLruCacheV4Claude {

    class Node {
        String key;
        double value;
        Node prev;
        Node next;

        Node(String key, double value) {
            this.key = key;
            this.value = value;
        }
    }

    private final HashMap<String, Node> map = new HashMap<>();
    private final int capacity;
    // dummy head / tail — 不存真資料，只是哨兵，省掉所有 null 判斷
    private final Node head = new Node("", 0);
    private final Node tail = new Node("", 0);

    public MarketDataLruCacheV4Claude(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public double get(String symbol) {
        Objects.requireNonNull(symbol, "symbol");
        Node node = map.get(symbol);
        if (node == null) return -1.0;
        moveToTail(node);
        return node.value;
    }

    public void put(String symbol, double price) {
        Objects.requireNonNull(symbol, "symbol");
        Node node = map.get(symbol);
        if (node != null) {
            // symbol 已存在 → update value + 移到最新
            node.value = price;
            moveToTail(node);
        } else {
            // 新 symbol → 超過 capacity 先 evict
            if (map.size() == capacity) {
                Node lru = head.next; // head 的下一個就是最舊的
                removeNode(lru);
                map.remove(lru.key);
            }
            Node newNode = new Node(symbol, price);
            addToTail(newNode);
            map.put(symbol, newNode);
        }
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

    public static void main(String[] args) {
        MarketDataLruCacheV4Claude cache = new MarketDataLruCacheV4Claude(3);

        cache.put("NGX-WCS-JUL26", 95.5);
        cache.put("NGX-AECO-AUG26", 2.10);
        cache.put("NGX-MSW-SEP26", 88.0);
        System.out.println(cache.get("NGX-WCS-JUL26")); // 95.5 — WCS 移到最新

        cache.put("NGX-C5-OCT26", 12.0); // evict AECO（最久沒碰）
        System.out.println(cache.get("NGX-AECO-AUG26")); // -1.0 — 被 evict
        System.out.println(cache.get("NGX-WCS-JUL26"));  // 95.5 — 還在

        cache.put("NGX-WCS-JUL26", 96.0); // update existing
        System.out.println(cache.get("NGX-WCS-JUL26"));  // 96.0
    }
}
