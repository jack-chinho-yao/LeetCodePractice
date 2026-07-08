package oop.generics.practice;

import java.util.ArrayList;
import java.util.List;

// ============================================================================
// G8 (impl): 用泛型類別實作泛型介面
//            (a generic class that implements a generic interface)
// ============================================================================
// 目標：用一個內部的 java.util.List<T> 來實作 Container<T>。
// Goal: implement Container<T> by delegating to an internal java.util.List<T>.
//
// 要寫出 / What to write:
//   - class ListContainer<T> implements Container<T>
//   - 一個 private final List<T> items = new ArrayList<>();
//     a private final List<T> items = new ArrayList<>();
//   - @Override 三個方法 (add / get / size)，全部轉交給 items
//     @Override the three methods (add / get / size), all forwarded to items
//   - (可選) toString() 回傳 items.toString()
//     (optional) toString() returns items.toString()
//
// 想一想：為什麼 class 也要帶 <T>？能不能寫 implements Container<String> 就好？
// Think: why must the class also carry <T>? Could we just write implements Container<String>?
public class ListContainer<T> implements Container<T> {
    private final List<T> items = new ArrayList<>();

    @Override
    public void add(T item){
        items.add(item);
    }
    @Override
    public T get(int index){
        return items.get(index);
    }
    @Override
    public int size(){
        return items.size();
    }
    @Override
    public String toString(){
        return items.toString();
    }
}
