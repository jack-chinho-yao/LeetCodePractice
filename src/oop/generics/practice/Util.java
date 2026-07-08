package oop.generics.practice;

import java.util.List;

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
    public static <T> T firstOf(List<T> list){
        return list.get(0);
    }

    public static <T extends Comparable> T max(List<T> list){
        T max = list.get(0);
        for(T e: list){
            if (max.compareTo(e) < 0){
                max = e;
            }
        }
        return max;
    }

    public static double sum(List<? extends Number> src){
        double total = 0;
        for(Number e: src){
            total += e.doubleValue();
        }
        return total;
    }

    /*
    *  一句口訣：PECS — Producer Extends, Consumer Super。

  判斷方法：問自己這個集合在方法裡是被讀還是被寫。

  ┌────────────────────────────────┬────────────────────────┬───────────┐
  │         你對集合做什麼         │      它扮演的角色      │  用哪個   │
  ├────────────────────────────────┼────────────────────────┼───────────┤
  │ 從裡面讀出元素（拿資料出來用） │ Producer 生產者        │ ? extends │
  ├────────────────────────────────┼────────────────────────┼───────────┤
  │ 往裡面寫入元素（把資料放進去） │ Consumer 消費者        │ ? super   │
  ├────────────────────────────────┼────────────────────────┼───────────┤
  │ 兩者都做                       │ 用具名 T，不用萬用字元 │ —         │
  └────────────────────────────────┴────────────────────────┴───────────┘

  為什麼是這樣？用你的三題對照

  G6 sum —— 只讀 → extends
  double sum(List<? extends Number> src) {
      for (Number n : src) total += n.doubleValue();  // 只讀出來
  }
  你只是把元素「讀成 Number」。? extends Number 保證「裡面每個元素至少是 Number」，所以讀出來當 Number 用一定安全。
  反過來你不能寫入：編譯器不知道 src 到底是 List<Integer> 還是 List<Double>，你 src.add(某個Number) 可能塞錯型別，所以禁止。

  G7 fillWithOneToThree —— 只寫 → super
  void fillWithOneToThree(List<? super Integer> dst) {
      dst.add(1); dst.add(2); dst.add(3);  // 只寫進去
  }
  你要把 Integer 塞進去。? super Integer 保證「這個 list 容得下 Integer」（它可能是 List<Integer>、List<Number>、List<Object>——全都能裝 Integer），所以寫入安全。
  反過來你讀不回來：讀出的元素只能當 Object，因為編譯器只知道「型別是 Integer 的某個父類」，不知道確切是誰。

  PECS copy —— 一讀一寫 → 兩個都用上
  <T> void copy(List<? extends T> src,   // 從 src 讀 → producer → extends
                List<? super T> dst) {    // 往 dst 寫 → consumer → super
      for (T item : src) dst.add(item);
  }

  一個記憶畫面

  把資料流想成水管：
  - 水流出來的那端（你接水喝）= producer = extends
  - 水灌進去的那端（你倒水進去）= consumer = super

  所以回到註解那行的判斷：先問「這個 ? 的 list，在我方法裡是被讀還是被寫？」—— 讀就 extends，寫就 super。

  要不要我出一兩個小題讓你練習判斷？

✻ Sautéed for 19s

❯   - 水流出來的那端（你接水喝）= producer = extends
    - 水灌進去的那端（你倒水進去）= consumer = super

  怎麼覺得producer是生產者 應該生產水
  consumer消費者 應該消耗水 語意怪怪的

⏺ 你的語感其實是對的 —— 問題出在「誰是生產者/消費者」搞混了。主詞是那個集合 (List)，不是你。

  把主詞固定成「集合」再讀一次，就完全順了：

  Producer 生產者 = 那個 List。它「生產/吐出」元素給你。
  - List 生產水 → 你去接水來喝 → 你是讀取
  - 對應 ? extends
  - 例：sum(List<? extends Number> src) —— src 不斷吐出 Number 給你加總

  Consumer 消費者 = 那個 List。它「消耗/吃掉」你給它的元素。
  - List 消費水 → 你倒水進去給它喝 → 你是寫入
  - 對應 ? super
  - 例：fillWithOneToThree(List<? super Integer> dst) —— 你把 1,2,3 倒進 dst，dst 把它們吃下去

  所以你說「producer 應該生產水、consumer 應該消耗水」—— 完全正確！只是：

  ┌─────────────────┬────────────────────┬──────────────────┐
  │      角色       │ 主詞（集合）做什麼 │ 你（方法）做什麼 │
  ├─────────────────┼────────────────────┼──────────────────┤
  │ Producer 生產者 │ 生產/給出元素      │ 你讀             │
  ├─────────────────┼────────────────────┼──────────────────┤
  │ Consumer 消費者 │ 消耗/收下元素      │ 你寫             │
  └─────────────────┴────────────────────┴──────────────────┘

  之前我寫「你接水喝 / 你倒水進去」是從你的視角講，難怪覺得擰 —— 因為「生產者/消費者」這名字是站在集合的視角取的。一旦把主詞換成集合，語意就跟你的直覺一致了：

  ▎ Producer 生產者 → 生產東西給你 → 你只能拿（讀）→ extends
  ▎ Consumer 消費者 → 收下你給的東西 → 你只能給（寫）→ super

  記憶錨點修正版：主詞永遠是 List。 List 是「供應商(producer)」你就只能跟它拿貨（讀，extends）；List 是「客戶(consumer)」你就只能出貨給它（寫，super）。

    * */
    public static void fillWithOneToThree(List<? super Integer> dst){
        dst.add(1);
        dst.add(2);
        dst.add(3);
    }

    public static <T> void copy(List<? extends Number> src, List<? super Integer> dst){
        for(Number e: src){
            dst.add(e.intValue());
        }
    }
}
