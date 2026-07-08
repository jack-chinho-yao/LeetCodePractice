package oop.abstraction.practice;

import java.util.Map;

// TODO (I6): make this interface EXTEND Movable.
interface Swimmable extends Movable {

    // TODO (I5): declare an int constant MAX_DEPTH = 100.
    int MAX_DEPTH = 100;

    // TODO: declare the abstract method  void swim();
    void swim();

    // TODO (I3): provide a DEFAULT method move() that calls swim() (@Override Movable.move()).
    @Override
    default void move(){
         swim();
    };

    // TODO (I3): provide a DEFAULT method dive() that prints the depth then calls swim().
    default void dive(){
        System.out.println("Depth : " + MAX_DEPTH);
        swim();
    }

    /*
    先分清楚兩件事：interface 裡能放的「變數」不是一般的欄位，而是常數（constant）。

  interface 裡的「欄位」一律是 public static final

  interface Swimmable {
      int MAX_DEPTH = 100;   // 編譯器自動補成 public static final int MAX_DEPTH = 100;
  }

  你在 interface 裡寫一個欄位，Java 強制它是 public static final —— 也就是一個「公開的常數」。所以：

  - 它不是物件的屬性，而是整個 interface 共用的常數（static）。
  - 一定要馬上給初始值（因為 final），不能像 class 那樣留著之後在 constructor 賦值。
  - interface 沒有 constructor、不能 new，所以也不可能有 instance 欄位。

  這跟你「以為只有抽象方法才行」不衝突：interface 裡可以有 (1) 抽象方法 ＋ (2) 常數，兩種都行。

  為什麼 private 不行

  因為 interface 的常數天生就是要給 implements 它的人用的，藏成 private 就自相矛盾了 —— 沒人看得到，那放在 interface 裡幹嘛？所以 Java 直接禁止：

  private final int MAX_DEPTH = 100;   // ❌ 編譯錯誤：interface 欄位不能 private

  你這個 TODO 正確寫法

  int MAX_DEPTH = 100;

  就這樣。public static final 全部省略，編譯器自動幫你補。寫出來反而囉嗦（很多 IDE 還會灰字提示 redundant）。

  ---
  補充一個容易混淆的點：Java 9 之後，interface 裡的 private 方法是允許的 —— 但那是用來給 default 方法當內部 helper 用的，跟「欄位不能 private」是兩回事。欄位（常數）永遠不能 private。


    * */

}
