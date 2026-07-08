package oop.generics.example;

// G3: multiple type parameters — K and V are two INDEPENDENT placeholders.
//     This is exactly how java.util.Map.Entry<K,V> models a key/value pair.
// G3: 多個型別參數 —— K 與 V 是兩個「彼此獨立」的佔位符。
//     java.util.Map.Entry<K,V> 就是用這種方式表示一組 key/value。
public class Pair<K, V> {

    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
