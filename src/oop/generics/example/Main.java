package oop.generics.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // G1: one generic class, two concrete types — and NO casts on the way out.
        //     一份泛型類別，兩種實際型別 —— 取出時完全不用轉型。
        Box<String> sb = new Box<>("hello");
        Box<Integer> ib = new Box<>(42);
        System.out.println(sb.get().toUpperCase()); // HELLO   (compiler knows it's String)
        System.out.println(ib.get() + 1);           // 43      (compiler knows it's Integer)

        // G3: two independent type parameters.
        //     兩個彼此獨立的型別參數。
        Pair<String, Integer> age = new Pair<>("Alice", 30);
        System.out.println(age.getKey() + " is " + age.getValue()); // Alice is 30

        // G8: generic interface + its generic implementation.
        //     泛型介面 + 它的泛型實作。
        Container<String> names = new ListContainer<>();
        names.add("Tom");
        names.add("Jerry");
        System.out.println(names.get(0) + " & " + names.get(1) + " size=" + names.size());
        // Tom & Jerry size=2

        // G2: generic method — T inferred from the argument, no explicit type needed.
        //     泛型方法 —— T 由引數自動推斷，不必明寫型別。
        List<String> words = Arrays.asList("a", "b", "c");
        System.out.println(Util.firstOf(words)); // a

        // G4: bounded type parameter lets us call compareTo inside max().
        //     有界型別參數讓我們能在 max() 裡呼叫 compareTo。
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
        System.out.println("max = " + Util.max(nums)); // max = 9

        // G6: producer (? extends Number) accepts BOTH List<Integer> and List<Double>.
        //     生產者 (? extends Number) 同時接受 List<Integer> 與 List<Double>。
        System.out.println("sum ints = " + Util.sum(Arrays.asList(1, 2, 3)));     // 6.0
        System.out.println("sum dbls = " + Util.sum(Arrays.asList(1.5, 2.5)));    // 4.0

        // G7: consumer (? super Integer) — fill a List<Number> with Integers.
        //     消費者 (? super Integer) —— 往 List<Number> 寫入 Integer。
        List<Number> sink = new ArrayList<>();
        Util.fillWithOneToThree(sink);
        System.out.println("filled = " + sink); // filled = [1, 2, 3]

        // PECS: copy from a producer into a consumer.
        //       從生產者複製到消費者。
        List<Object> copyOut = new ArrayList<>();
        Util.copy(nums, copyOut);               // List<Integer> -> List<Object>
        System.out.println("copied = " + copyOut); // copied = [3, 1, 4, 1, 5, 9, 2, 6]

        // G10: INVARIANCE — List<Integer> is NOT a List<Number>. The next line would
        //      NOT compile, which is exactly WHY the wildcards above were necessary.
        // G10: 不可變性 —— List<Integer> 不是 List<Number>。下一行會無法編譯，
        //      這正是為什麼上面那些萬用字元是必要的。
        // List<Number> bad = nums; // compile error: incompatible types

        // G9: TYPE ERASURE — at runtime the type argument is gone. Box<String> and
        //     Box<Integer> share ONE runtime class, so getClass() returns the same
        //     Class object. (This is also why you can't do `new T()` or
        //     `if (x instanceof T)` — T simply doesn't exist at runtime.)
        // G9: 型別擦除 —— 執行期型別參數已不存在。Box<String> 與 Box<Integer> 共用
        //     「同一個」執行期 class，所以 getClass() 回傳相同的 Class 物件。
        //     (這也是為什麼不能 `new T()` 或 `if (x instanceof T)` —— 執行期根本沒有 T。)
        System.out.println(sb.getClass() == ib.getClass()); // true
    }
}

/*
    ============================================================================
    泛型概念總覽 (Generics Concept Map)
    ============================================================================

    --- 宣告與使用 (declaring & using) ---

    G1  Generic class — class Box<T> { ... }
        泛型類別：用型別參數 T 當佔位符，使用時填入實際型別（Box<String>）。
        Generic class: use type parameter T as a placeholder, filled with a real
        type at the use site (Box<String>).
        好處：一份程式碼適用所有型別，且取出時不用轉型、編譯期就保證型別安全。
        Benefit: one piece of code fits every type; no cast on retrieval, and full
        type safety is guaranteed at compile time.
        See: Box<T>.

    G2  Generic method — public static <T> T firstOf(List<T> list)
        泛型方法：在回傳型別前自己宣告 <T>，與類別的型別參數無關；
        呼叫時編譯器會自動推斷 T（type inference），不必寫 Util.<String>firstOf。
        Generic method: declares its own <T> before the return type, independent of
        any class type parameter; the compiler infers T at the call site (type
        inference), so you needn't write Util.<String>firstOf.
        See: Util.firstOf, Util.copy.

    G3  Multiple type parameters — class Pair<K, V>
        多個型別參數：彼此獨立，Map.Entry<K,V> 就是這樣。
        Multiple type parameters: independent of each other — that's exactly Map.Entry<K,V>.
        See: Pair<K,V>.

    G8  Generic interface — interface Container<T> + class impl<T>
        泛型介面：把元素型別參數化的合約；List<E>、Comparable<T> 都是。
        實作類別把自己的 T 直接轉交給介面。
        Generic interface: a contract parameterized by its element type; List<E> and
        Comparable<T> are examples. The implementing class forwards its own T to the
        interface.
        See: Container<T>, ListContainer<T>.

    --- 邊界與萬用字元 (bounds & wildcards) ---

    G4  Bounded type parameter — <T extends Comparable<T>>
        有界型別參數：限制 T 的上界，才能呼叫該型別／介面的方法（如 compareTo）。
        沒有界限時 T 只看得到 Object 的方法。
        Bounded type parameter: constrains T's upper bound so you can call that
        type/interface's methods (e.g. compareTo). Without a bound, T only exposes
        Object's methods.
        See: Util.max.

    G5  Wildcard ? — 未知型別的佔位（搭配 extends / super 使用）。
        Wildcard ?: a placeholder for an unknown type (used with extends / super).

    G6  Upper-bounded wildcard — List<? extends Number>（PRODUCER，只讀）
        上界萬用字元：協變(covariant)，可接受 List<Integer>/List<Double>；
        能把元素「讀成」Number，但「不能寫入」。
        Upper-bounded wildcard: covariant; accepts List<Integer>/List<Double>; can
        read each element AS a Number, but CANNOT write.
        See: Util.sum.

    G7  Lower-bounded wildcard — List<? super Integer>（CONSUMER，只寫）
        下界萬用字元：逆變(contravariant)，可接受 List<Integer>/List<Number>/List<Object>；
        能「寫入」Integer，但讀出來只看得到 Object。
        Lower-bounded wildcard: contravariant; accepts List<Integer>/List<Number>/
        List<Object>; can WRITE Integers, but reading back only yields Object.
        See: Util.fillWithOneToThree.

        ▲ PECS 口訣：Producer-Extends, Consumer-Super
          —— 你「拿資料出來(讀)」就用 ? extends；「把資料放進去(寫)」就用 ? super。
          PECS mnemonic: Producer-Extends, Consumer-Super — when you take data OUT
          (read) use ? extends; when you put data IN (write) use ? super.
          See: Util.copy(List<? extends T> src, List<? super T> dst)。

    --- 執行期真相 (runtime reality) ---

    G9  Type erasure — 型別擦除：泛型資訊只存在於編譯期，執行期會被「抹掉」。
        後果：Box<String> 與 Box<Integer> 在執行期是同一個 class；
        而且不能 new T()、不能 new T[]、不能 x instanceof T。
        Type erasure: generic info exists only at compile time and is "erased" at
        runtime. Consequences: Box<String> and Box<Integer> are the same class at
        runtime; and you cannot do new T(), new T[], or x instanceof T.
        See: sb.getClass() == ib.getClass() // true。

    G10 Invariance — 不可變性：List<Integer> 不是 List<Number> 的子型別！
        即使 Integer 是 Number 的子型別也一樣。這正是需要萬用字元的原因。
        (陣列相反，是協變的：Integer[] 可當 Number[]，但那會帶來執行期風險。)
        Invariance: List<Integer> is NOT a subtype of List<Number> — even though
        Integer is a subtype of Number. This is exactly why wildcards are needed.
        (Arrays are the opposite — covariant: Integer[] can act as Number[], but that
        brings runtime risk.)
        See: 註解掉的 List<Number> bad = nums; / the commented-out List<Number> bad = nums;.

    ============================================================================
    實務上哪裡會用到 (real-world usage)
    ============================================================================

    - Collections 框架：List<E>、Map<K,V>、Set<E>、Optional<T> 全是泛型，天天都用。
      Collections framework: List<E>, Map<K,V>, Set<E>, Optional<T> are all generic — used daily.
    - Comparable<T> / Comparator<T>：排序時指定比較的型別。
      Comparable<T> / Comparator<T>: specify the type being compared when sorting.
    - 泛型方法工具：Collections.sort、Collections.max、Arrays.asList 都是泛型方法。
      Generic method utilities: Collections.sort, Collections.max, Arrays.asList are all generic methods.
    - ? extends / ? super：Collections.copy(dest, src)、addAll 的簽名就是 PECS 的範本。
      ? extends / ? super: the signatures of Collections.copy(dest, src) and addAll are textbook PECS.
    - 自訂泛型容器 / DAO / Repository<T, ID>：Spring Data 的 JpaRepository<T, ID>
      就是讓你一個介面套用到所有 Entity，少寫大量重複程式碼。
      Custom generic containers / DAO / Repository<T, ID>: Spring Data's JpaRepository<T, ID>
      lets one interface apply to every Entity, saving tons of duplicated code.
    - 型別擦除的坑：為什麼不能 new T[]、為什麼要傳 Class<T> clazz 進來（如 Gson
      的 fromJson(json, MyType.class)）——都是 erasure 造成的。
      Type-erasure pitfalls: why you can't do new T[], and why you pass a Class<T> clazz
      in (e.g. Gson's fromJson(json, MyType.class)) — all caused by erasure.
*/
