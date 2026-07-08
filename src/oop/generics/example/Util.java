package oop.generics.example;

import java.util.List;

// Static generic helpers — this is where generic METHODS, bounds and wildcards live.
// 靜態泛型工具方法 —— 泛型「方法」、邊界、萬用字元都住在這裡。
public class Util {

    // G2: generic METHOD — declares its OWN type parameter <T> before the return
    //     type. Independent of any class type parameter; T is inferred from the
    //     argument at the call site (no need to write Util.<String>firstOf(...)).
    // G2: 泛型「方法」—— 在回傳型別前自己宣告 <T>，與任何類別的型別參數無關；
    //     呼叫時 T 會從引數自動推斷 (不必寫 Util.<String>firstOf(...))。
    public static <T> T firstOf(List<T> list) {
        return list.get(0);
    }

    // G4: BOUNDED type parameter — "T extends Comparable<T>" means T must be
    //     comparable, so we are allowed to call compareTo. Without the bound, T
    //     only has Object's methods and item.compareTo(...) would NOT compile.
    // G4: 「有界」型別參數 —— "T extends Comparable<T>" 表示 T 必須可比較，
    //     才能呼叫 compareTo。沒有界限時 T 只看得到 Object 的方法，
    //     item.compareTo(...) 會無法編譯。
    public static <T extends Comparable<T>> T max(List<T> list) {
        T best = list.get(0);
        for (T item : list) {
            if (item.compareTo(best) > 0) {
                best = item;
            }
        }
        return best;
    }

    // G5 + G6: PRODUCER — we only READ out of src. "? extends Number" makes the
    //     parameter COVARIANT, so it accepts List<Integer>, List<Double>,
    //     List<Number>... We can read each element AS a Number, but we may NOT
    //     add into src (the compiler doesn't know the exact element type).
    // G5 + G6: 生產者 (PRODUCER) —— 我們只「讀取」src。"? extends Number" 讓參數
    //     變成「協變(covariant)」，因此可接受 List<Integer>、List<Double>、
    //     List<Number>...。能把每個元素「讀成」Number，但「不能」往 src 寫入
    //     (編譯器不知道確切的元素型別)。
    public static double sum(List<? extends Number> src) {
        double total = 0;
        for (Number n : src) {      // safe: every element IS-A Number / 安全：每個元素都是 Number
            total += n.doubleValue();
        }
        // src.add(1);  // would NOT compile — see G7 for why writing needs ? super
        //              // 無法編譯 —— 為何寫入需要 ? super 見 G7
        return total;
    }

    // G7: CONSUMER — we only WRITE Integers into dst. "? super Integer" makes the
    //     parameter CONTRAVARIANT, so it accepts List<Integer>, List<Number>,
    //     List<Object>. We may add Integers, but reading back only yields Object.
    // G7: 消費者 (CONSUMER) —— 我們只「寫入」Integer 到 dst。"? super Integer" 讓
    //     參數變成「逆變(contravariant)」，因此可接受 List<Integer>、List<Number>、
    //     List<Object>。能加入 Integer，但讀回來只看得到 Object。
    public static void fillWithOneToThree(List<? super Integer> dst) {
        dst.add(1);
        dst.add(2);
        dst.add(3);
        // Integer x = dst.get(0);  // would NOT compile — element seen only as Object
        //                          // 無法編譯 —— 元素只被視為 Object
    }

    // G5 + PECS together — copy: read from a producer (? extends T), write to a
    //     consumer (? super T). "Producer Extends, Consumer Super".
    // G5 + PECS 合體 —— copy：從生產者讀 (? extends T)、往消費者寫 (? super T)。
    //     口訣「Producer Extends, Consumer Super」(生產者用 extends，消費者用 super)。
    public static <T> void copy(List<? extends T> src, List<? super T> dst) {
        for (T item : src) {
            dst.add(item);
        }
    }
}
