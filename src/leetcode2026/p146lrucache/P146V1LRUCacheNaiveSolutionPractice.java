package leetcode2026.p146lrucache;

import java.util.ArrayList;
import java.util.HashMap;

public class P146V1LRUCacheNaiveSolutionPractice {

    // HashMap + ArrayList 記錄使用順序, Time O(n) get/put, Space O(capacity)
    class LRUCache {

        // TODO Step 1: 你需要哪些欄位？
        private final int capacity;
        private HashMap<Integer, Integer> map = new HashMap<>();
        private ArrayList<Integer> order = new ArrayList<>();

        public LRUCache(int capacity) {
            // TODO Step 2
            this.capacity = capacity;
        }

        public int get(int key) {
            // TODO Step 3
            Integer value = map.get(key);
            if (value == null){
                return -1;
            }else {
                order.remove(order.indexOf(key));
                order.add(key);
                return value;
            }
        }

        public void put(int key, int value) {
            // TODO Step 4
            if (map.get(key) != null){
                order.remove(order.indexOf(key));


            }else {
                if (map.size() == capacity){
                    map.remove(order.get(0));
                    order.remove(0);
                }
            }
            order.add(key);
            map.put(key,value);
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new P146V1LRUCacheNaiveSolutionPractice().new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));  // 預期 1
        cache.put(3, 3);                   // evict key 2
        System.out.println(cache.get(2));  // 預期 -1
        cache.put(4, 4);                   // evict key 1
        System.out.println(cache.get(1));  // 預期 -1
        System.out.println(cache.get(3));  // 預期 3
        System.out.println(cache.get(4));  // 預期 4
    }
}
