package oop.generics.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 這是「自我測驗的測試卷」：把 Box / Pair / Container / ListContainer / Util 寫完後，
// This is your "self-test paper": once Box / Pair / Container / ListContainer / Util are done,
// 直接跑這支 Main，輸出要跟右邊註解一致 (也可對照 ../example/Main 的輸出)。
// just run this Main; the output should match the comments on the right (compare with ../example/Main).
public class Main {
    public static void main(String[] args) {

        // G1: 泛型類別，取出不用轉型 (generic class; no cast on retrieval)
        Box<String> sb = new Box<>("hello");
        Box<Integer> ib = new Box<>(42);
        System.out.println(sb.get().toUpperCase()); // HELLO
        System.out.println(ib.get() + 1);           // 43

        // G3: 兩個型別參數 (two type parameters)
        Pair<String, Integer> age = new Pair<>("Alice", 30);
        System.out.println(age.getKey() + " is " + age.getValue()); // Alice is 30

        // G8: 泛型介面 + 實作 (generic interface + impl)
        Container<String> names = new ListContainer<>();
        names.add("Tom");
        names.add("Jerry");
        System.out.println(names.get(0) + " & " + names.get(1) + " size=" + names.size());
        // Tom & Jerry size=2

        // G2: 泛型方法 (T 自動推斷 / generic method, T inferred)
        List<String> words = Arrays.asList("a", "b", "c");
        System.out.println(Util.firstOf(words)); // a

        // G4: 有界型別參數 (bounded type parameter)
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
        System.out.println("max = " + Util.max(nums)); // max = 9

        // G6: producer (? extends Number)
        System.out.println("sum ints = " + Util.sum(Arrays.asList(1, 2, 3)));  // 6.0
        System.out.println("sum dbls = " + Util.sum(Arrays.asList(1.5, 2.5))); // 4.0

        // G7: consumer (? super Integer)
        List<Number> sink = new ArrayList<>();
        Util.fillWithOneToThree(sink);
        System.out.println("filled = " + sink); // filled = [1, 2, 3]

        // PECS: copy producer -> consumer
        List<Object> copyOut = new ArrayList<>();
        Util.copy(nums, copyOut);
        System.out.println("copied = " + copyOut); // copied = [3, 1, 4, 1, 5, 9, 2, 6]

        // G10: 不可變性 —— 下面這行「不能」編譯，懂為什麼嗎？
        // List<Number> bad = nums; // compile error

        // G9: 型別擦除 —— 執行期型別參數消失，兩個 Box 是同一個 class
        System.out.println(sb.getClass() == ib.getClass()); // true
    }
}

/*
    ============================================================================
    本練習要練到的泛型概念清單 (Checklist)  —  細節對照 ../example/Main.java 底部
    ============================================================================

    [ ] G1   generic class            Box<T>
    [ ] G2   generic method           Util.firstOf
    [ ] G3   multiple type params     Pair<K, V>
    [ ] G4   bounded type param       Util.max  (<T extends Comparable<T>>)
    [ ] G5   wildcard ?               (出現在 G6/G7)
    [ ] G6   ? extends (producer/讀)  Util.sum
    [ ] G7   ? super  (consumer/寫)   Util.fillWithOneToThree
    [ ] PECS Producer-Extends         Util.copy
             Consumer-Super
    [ ] G8   generic interface        Container<T> / ListContainer<T>
    [ ] G9   type erasure             getClass() == getClass() // true
    [ ] G10  invariance               List<Integer> 不是 List<Number>

    寫完後在 src/ 下編譯執行：
      javac oop/generics/practice/*.java
      java  oop.generics.practice.Main
    輸出要與 example 版一致。
*/
