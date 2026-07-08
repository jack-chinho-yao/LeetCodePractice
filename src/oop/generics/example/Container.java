package oop.generics.example;

// G8: generic INTERFACE — a contract parameterized by its element type T.
//     The whole Collections framework is built this way: List<E>, Set<E>,
//     Iterator<E>, Comparable<T> are all generic interfaces.
// G8: 泛型「介面」—— 一份以元素型別 T 為參數的合約。
//     整個 Collections 框架都是這樣建構的：List<E>、Set<E>、
//     Iterator<E>、Comparable<T> 全都是泛型介面。
public interface Container<T> {

    void add(T item);

    T get(int index);

    int size();
}
