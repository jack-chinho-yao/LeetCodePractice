package oop.generics.template;

// ============================================================================
// 泛型方法 / 邊界 / 萬用字元 (generic methods, bounds, wildcards)
// ============================================================================
// 把下列 static 方法寫出來。重點不只是 body，更是「簽名要怎麼寫」。
// Write the static methods below. The point is not just the body, but how to
// write the SIGNATURE.
//
// G2  generic method:
//     static <T> T firstOf(List<T> list)            // 回傳第一個元素 / return the first element
//
// G4  bounded type parameter:
//     static <?> ? max(List<?> list)
//     -> T 要能呼叫 compareTo，所以 T 必須有上界。該怎麼寫 <T ...>？
//        T must be able to call compareTo, so T needs an upper bound. How do you write <T ...>?
//        回傳清單中最大的元素。
//        Return the largest element in the list.
//
// G6  upper-bounded wildcard (PRODUCER / 只讀 read-only):
//     static double sum(List<?> src)
//     -> 要能同時接受 List<Integer> 與 List<Double>，把每個元素讀成 Number 加總。
//        Must accept both List<Integer> and List<Double>; read each element as a Number and sum.
//        ? 該配 extends 還是 super？
//        Should ? go with extends or super?
//
// G7  lower-bounded wildcard (CONSUMER / 只寫 write-only):
//     static void fillWithOneToThree(List<?> dst)
//     -> 要能往 List<Integer>/List<Number>/List<Object> 寫入 1,2,3。
//        Must be able to write 1,2,3 into List<Integer>/List<Number>/List<Object>.
//        ? 該配 extends 還是 super？
//        Should ? go with extends or super?
//
// PECS copy (Producer-Extends, Consumer-Super):
//     static <T> void copy(List<?> src, List<?> dst)
//     -> 從 src 讀、往 dst 寫。兩個 ? 分別該配 extends 還是 super？
//        Read from src, write to dst. For each ?, should it be extends or super?
public class Util {

    // TODO: 實作上述五個方法 (記得 import java.util.List)

}
