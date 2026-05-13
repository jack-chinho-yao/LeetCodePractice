package interview2026.mockprep.p5cache;

import java.util.HashMap;
import java.util.Objects;

/*
 * V4 — V3 的改進版，加了 null check (BUG 1 修好了)，但其餘 DLL 操作問題同 V3：
 *
 * ✅ FIXED: get() 加了 if (node == null) return -1 → 不會 NPE
 *
 * 仍存在的 Bug（同 V3）:
 * - get() 移動 node 只改了 prev.next 和 tail.next，漏了 next.prev / node.prev / node.next
 * - get() node 在頭時 prev 是 null → NPE
 * - put() head.next = tail 會斷掉中間所有 node
 * - put() evict 沒做 map.remove(舊head.key)
 * - put() 沒處理 symbol 已存在（update vs insert）
 * - 沒有哨兵 → 空 cache 時 tail 是 null → NPE
 *
 * 根本修法: dummy head/tail + removeNode/addToTail helpers
 * → 修正版見 MarketDataLruCacheV4Claude.java
 */
public class MarketDataLruCacheV4 {
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
    private Node head;
    private Node tail;
    private int capacity;
    public MarketDataLruCacheV4(int capacity){
        this.capacity = capacity;
    }
    public double get(String symbol){
        Objects.requireNonNull(symbol, "symbol must not be null");
        Node node = map.get(symbol);
        if (node == null){ // ✅ V3 的 BUG 1 修好了
            return -1;
        }
        Node prev = node.prev; // 仍有問題: node 在頭時 prev=null → NPE
        Node next = node.next;
        prev.next = next; // 仍有問題: 漏了 next.prev = prev
        this.tail.next = node; // 仍有問題: 漏了 node.prev/node.next 更新
        double value = node.value;
        return value;
    }
    public void put(String symbol, double price){
        Node node = new Node(symbol, price); // 仍有問題: 沒先判斷 symbol 是否已存在
        if (map.isEmpty()){
            this.head = node;
        }
        if (map.size() == capacity){
            this.head = head.next; // 仍有問題: 沒做 map.remove(舊head.key)
        }
        this.head.next = tail; // 仍有問題: 中間 node 全斷
        this.tail.next = node;
        this.tail = this.tail.next;
        map.put(symbol, node);
    }

    public static void main(String[] args) {
        MarketDataLruCacheV4 marketDataLruCache = new MarketDataLruCacheV4(3);
    }
}
