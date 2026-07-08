package oop.generics.example;

import java.util.ArrayList;
import java.util.List;

// G8 (impl): a concrete generic class that fulfils a generic interface.
//     It stays parameterized by T and forwards T straight into the contract,
//     just like ArrayList<E> implements List<E>.
// G8 (實作): 一個實作了泛型介面的具體泛型類別。
//     它自己仍然帶著 T，並把 T 直接轉交給介面合約，
//     就像 ArrayList<E> 實作 List<E> 一樣。
public class ListContainer<T> implements Container<T> {

    private final List<T> items = new ArrayList<>();

    @Override
    public void add(T item) {
        items.add(item);
    }

    @Override
    public T get(int index) {
        return items.get(index);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
