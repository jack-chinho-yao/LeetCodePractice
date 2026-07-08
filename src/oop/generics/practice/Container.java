package oop.generics.practice;

// ============================================================================
// G8: Generic interface  (泛型介面)
// ============================================================================
// 目標：一份「以元素型別 T 為參數」的容器合約 (像 List<E>)。
// Goal: a container contract parameterized by its element type T (like List<E>).
//
// 要宣告 (介面方法不用寫 body)：
// To declare (interface methods need no body):
//   - void add(T item)
//   - T get(int index)
//   - int size()
public interface Container<T> {

    void add(T item);
    T get(int index);
    int size();

}
