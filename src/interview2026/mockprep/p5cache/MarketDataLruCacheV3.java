package interview2026.mockprep.p5cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/*
 * V3 — 手刻 HashMap + DLL 第一版，概念方向對但有多個 Bug：
 *
 * BUG 1: get() 沒判斷 node == null（symbol 不存在時 NPE）
 *
 * BUG 2: get() 移動 node 不完整 — doubly-linked list 要改 4 個 pointer：
 *        - 拆掉: prev.next = next, next.prev = prev
 *        - 接尾: tail.prev 的 next → node, node.prev → tail.prev, node.next → tail, tail.prev → node
 *        目前只改了 prev.next 和 tail.next，漏了 next.prev 和 node 自己的 prev/next
 *
 * BUG 3: get() 沒處理 node 在頭/尾的邊界情況
 *        - node 在頭: node.prev 是 null → prev.next = next 會 NPE
 *        - node 在尾: 已經是最新，不應該移動
 *
 * BUG 4: put() 第 46 行 this.head.next = tail → 把 head 直接接 tail，中間所有 node 斷掉
 *
 * BUG 5: put() evict 時沒從 HashMap 刪舊 head 的 key → map.remove(oldHead.key)
 *
 * BUG 6: put() 沒處理 symbol 已存在的情況（update vs insert）
 *
 * BUG 7: 空 cache 時 tail 是 null → this.tail.next 會 NPE
 *
 * 根本修法: 用 dummy head/tail 哨兵 + removeNode/addToTail helper methods
 * → 修正版見 MarketDataLruCacheV4Claude.java
 */
public class MarketDataLruCacheV3 {
    class Node{
        String key;
        private double value;
        private Node prev ;
        private Node next ;

        public Node(String key, double value) {
            this.key = key;
            this.value = value;
        }
    }
    private final HashMap<String, Node> map = new HashMap<>();
    private Node head; // BUG 7: 初始 null，沒有哨兵 → 每個操作都要判斷 null
    private Node tail; // BUG 7: 同上
    private int capacity;
    public MarketDataLruCacheV3(int capacity){
        this.capacity = capacity;
    }
    public double get(String symbol){
        Objects.requireNonNull(symbol, "symbol must not be null");
        Node node = map.get(symbol); // BUG 1: 沒判斷 node == null
        Node prev = node.prev; // BUG 3: node 在頭時 prev 是 null → NPE
        Node next = node.next;
        prev.next = next; // BUG 2: 只改了一半，漏了 next.prev = prev
        this.tail.next = node; // BUG 2: 漏了 node.prev = tail, node.next = null
        double value = node.value;
        return value;
    }
    public void put(String symbol, double price){
        Node node = new Node(symbol, price); // BUG 6: 沒先檢查 symbol 是否已存在
        if (map.isEmpty()){
            this.head = node;
        }
        if (map.size() == capacity){
            this.head = head.next; // BUG 5: 沒做 map.remove(舊head.key)
        }
        this.head.next = tail; // BUG 4: 把 head 直接接 tail，中間 node 全斷
        this.tail.next = node; // BUG 7: tail 可能是 null → NPE
        this.tail = this.tail.next;
        map.put(symbol, node);
    }

    public static void main(String[] args) {
        MarketDataLruCacheV3 marketDataLruCache = new MarketDataLruCacheV3(3);
    }
}
