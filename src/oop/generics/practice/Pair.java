package oop.generics.practice;

// ============================================================================
// G3: Multiple type parameters  (多個型別參數)
// ============================================================================
// 目標：一個同時持有 key 與 value 的不可變配對 (像 Map.Entry<K,V>)。
// Goal: an immutable pair holding a key and a value at once (like Map.Entry<K,V>).
//
// 要寫出 / What to write:
//   - 兩個型別參數 K, V (class Pair<K, V>)
//     two type parameters K, V  (class Pair<K, V>)
//   - final 欄位 key (型別 K)、value (型別 V)
//     final fields key (type K) and value (type V)
//   - 建構子 Pair(K key, V value)
//     a constructor Pair(K key, V value)
//   - K getKey()
//   - V getValue()
//   - toString() 回傳 "(<key>, <value>)"
//     toString() returns "(<key>, <value>)"
public class Pair<K, V> {
    private final K key;
    private final V value;
    // TODO: 實作
    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public K getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }

    @Override
    public String toString(){
        return "(" + key + ", " + value + ")";
    }
}
