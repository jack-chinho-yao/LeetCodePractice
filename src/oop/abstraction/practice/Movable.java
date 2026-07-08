package oop.abstraction.practice;

// PRACTICE: fill in every TODO. Compare against ../example when stuck.
interface Movable {
    // TODO (I5): declare an int constant MAX_SPEED = 120
    //            (remember: interface fields are implicitly public static final).
    int MAX_SPEED = 120;

    // TODO (I1): declare the abstract method  void move();
    void move();
    /*
  interface Movable {
      void move();   // ← 你寫的這行，就是「定義契約」
  }

  「abstract method」為什麼不用寫 abstract

  interface 裡的方法天生就是 public abstract，編譯器自動補。所以這三種寫法完全等價：

  void move();                    // ✅ 一般這樣寫
  public void move();             // 一樣，public 多餘
  public abstract void move();    // 一樣，abstract 也多餘

*/

    // TODO (I4): declare a STATIC method  String describeSpeedLimit()
    //            that returns "Top speed capped at " + MAX_SPEED + " km/h".
    static String describeSpeedLimit(){
        return "Top speed capped at " + MAX_SPEED + " km/h";
    }


    /*介面也能直接定義方法了？

⏺ 對！這是 Java 8 之後的大改變 —— interface 不再只能放「光宣告、沒實作」的抽象方法了。現在 interface 裡可以直接寫出有 { } 內容的方法，主要有三種：

  1. default 方法 —— 給實作者一個「預設版本」

  interface Movable {
      void move();                    // 抽象，必須實作

      default void stop() {           // 有 body！implements 的人「不寫也有得用」
          System.out.println("停止移動");
      }
  }

  implements 的 class 可以不寫 stop()，直接繼承這個預設版本；想改的話也可以 override 它。

  2. static 方法 —— 屬於 interface 本身的工具方法

  interface Movable {
      static Movable createDefault() {
          return () -> System.out.println("預設移動");
      }
  }

  // 用法：直接用介面名呼叫
  Movable m = Movable.createDefault();

  3. private 方法（Java 9+）—— 給 default 方法當內部 helper

  interface Movable {
      default void moveLeft()  { log("左"); }
      default void moveRight() { log("右"); }

      private void log(String dir) {     // 只給介面內部用，外面看不到
          System.out.println("移動: " + dir);
      }
  }

  （這就是我前面提過的「interface 的 private 方法」—— 它存在的目的就是給 default 方法共用程式碼，不對外開放。）

  為什麼要加這個功能？

  主要是為了向後相容。假設 Movable 已經被一百個 class implements 了，今天你想加一個新方法。如果是傳統抽象方法，那一百個 class 全部會編譯失敗（因為它們沒實作新方法）。但如果加成 default
  方法，它們全部不受影響，因為有預設實作可以用。

  對比整理

  ┌───────────────────────┬─────────────┬────────────────────────────────┐
  │       方法種類        │ 有沒有 body │  implements 的人一定要實作嗎   │
  ├───────────────────────┼─────────────┼────────────────────────────────┤
  │ 抽象方法 void move(); │ ❌ 沒有     │ ✅ 必須         ─┘               │
  ├───────────────────────┼─────────────┼────────────────────────────────┤
  │ default 方法          │ ✅  有       │ ❌  不用，可選擇 override       │
  ├───────────────────────┼─────────────┼────────────────────────────────┤
  │ static 方法           │ ✅  有       │ ❌  不能 override，用介面名呼叫 │
  ├───────────────────────┼─────────────┼────────────────────────────────┤
  │ private 方法          │ ✅  有       │ ❌  介面內部專用                │
  └───────────────────────┴─────────────┴────────────────────────────────┘

  ---
  所以現在 interface 和 abstract class 的界線變模糊了，但還是有關鍵差別：

  - interface 仍然不能有 instance 欄位（只能 public static final 常數）、不能有 constructor。
  - abstract class 可以有一般的 instance 狀態（像你 Animal 裡的 name、energy）。

  一句話：現在 interface 可以提供「行為的預設實作」，但不能持有「物件的狀態」—— 要存狀態還是得用 abstract class。

*/


    /*
    * 讓每個實作類都有他 那就用static不就好了

⏺ 這就是最關鍵的誤會 —— interface 的 static 方法，實作類「並不會擁有」它。它不會被繼承下去。

  實際試試看就懂了

  interface Movable {
      static void stop() { System.out.println("停"); }
  }

  class Fish implements Movable { }

  Fish f = new Fish();

  f.stop();           // ❌ 編譯錯誤！物件呼叫不到
  Fish.stop();        // ❌ 編譯錯誤！實作類名也呼叫不到
  Movable.stop();     // ✅ 只有這樣可以 —— 一定要用「介面名」

  看到沒？就算 Fish implements Movable，Fish 完全拿不到 stop()。static 方法只認 Movable.stop() 這一條路。所以它根本達不到你要的「每個實作類都有他」。

  對比：default 才是「每個實作類都有」

  interface Movable {
      default void stop() { System.out.println("停"); }
  }

  class Fish implements Movable { }

  Fish f = new Fish();
  f.stop();           // ✅ Fish 真的「擁有」這個方法

  default 才會讓方法繼承到每個實作類身上，可以用物件呼叫、有多型。

  核心區別

  ┌────────────────────────────────────────────────┬────────────┐
  │                    你的需求                    │    該用    │
  ├────────────────────────────────────────────────┼────────────┤
  │ 想讓每個實作類 / 每個物件都有這個方法          │ default ✅  │
  ├────────────────────────────────────────────────┼────────────┤
  │ 想要一個跟物件無關、只透過介面名呼叫的工具方法 │ static     │
  └────────────────────────────────────────────────┴────────────┘

  static 不是「大家共用所以大家都有」—— 它是「只屬於介面這個型別，誰都繼承不到」。

  ---
  所以結論剛好相反：你要「每個實作類都有他」，正是要用 default，不能用 static。 static 反而是「沒有任何實作類擁有它」。

* */

    /*static與 default 方法記憶體沒啥差別
    不是啊用static感覺比較省啊 不用每個做類都要給他記憶替位置 用法指示差一點而已不是嗎 能省記憶體位置不是更好？

⏺ 你的前提其實是錯的 —— 這裡有個很常見的誤會：方法（不管 default、static 還是一般方法）都不會「每個物件存一份」。所以 default 並沒有比較浪費記憶體。

  關鍵：方法的程式碼只存一份

  物件在記憶體裡只存欄位（資料），不存方法。方法的程式碼是放在 class 的「類別資訊區」(metaspace)，整個程式從頭到尾就一份，所有物件共用。

  class Fish implements Movable {
      int x, y;     // ← 這個才會「每個 Fish 物件各存一份」
  }
  // move()、stop() 的「程式碼」只有一份，10000 隻 Fish 也是共用同一份

  所以你想成「每多一隻 Fish 就要多存一份 stop() 的程式碼」—— 不會的。default 方法的 body 只編譯一次、存一次。new 一萬個物件，方法程式碼還是那一份。

  default 跟 static 在記憶體上幾乎沒差

  兩者的方法程式碼都是「整個 class 共用一份」。差別只在呼叫機制：

  - default（實例方法）：呼叫時要透過物件，背後有個「方法查表」找到正確版本（為了支援多型/override）。這部分成本極小，而且查的是共用的表，不是每個物件存一份。
  - static：直接綁死介面型別，呼叫時不用查表，理論上快一咪咪。

  但這個差距小到你永遠不需要為它煩惱，現代 JVM（JIT）還會把這些都優化掉。

  真正該在意的不是「省那一點」，而是「能不能用」

  前面已經證明過：static 根本達不到你要的效果 —— 實作類繼承不到、物件呼叫不到、不能 override、用不了 this。

  // 你要的是「每隻動物能停下來」，這需要綁在物件上
  fish.stop();   // static 做不到這件事，default 才行

  所以這不是「省記憶體 vs 多花一點」的取捨，而是 static 根本不是同一種工具，硬要用它你那個功能會直接壞掉。

  一句話總結

  ┌────────────────────────────────────────┬───────────────────────────────────────────────┐
  │                  誤會                  │                     真相                      │
  ├────────────────────────────────────────┼───────────────────────────────────────────────┤
  │ default 每個物件存一份方法，浪費記憶體 │ ❌  方法程式碼全程只存一份，物件只存欄位       │
  ├────────────────────────────────────────┼───────────────────────────────────────────────┤
  │ static 比較省                          │ ❌  兩者記憶體幾乎一樣，差異小到不用管         │
  ├────────────────────────────────────────┼───────────────────────────────────────────────┤
  │ 用法只差一點                           │ ❌  差很多：static 繼承不到、沒多型、不能 this │
  └────────────────────────────────────────┴───────────────────────────────────────────────┘

  只有「欄位」才會每個物件各存一份；方法不會。 所以選 default 還是 static，請看「功能上需不需要綁在物件上」，不要從省記憶體的角度想 —— 那裡根本沒有你以為的成本。

    *
    * */

    /*所以你是說fish.stop(); 還比較直觀囉？

⏺ 對，但「直觀」只是表象，底層原因是 語意上 stop() 本來就是「某隻動物做的動作」，綁在物件上才合理。

  為什麼 fish.stop() 才對

  fish.stop();        // 「這隻魚停下來」 ← 主詞是 fish，符合現實
  Movable.stop();     // 「Movable 這個型別停下來」 ← 誰停？很怪

  stop（停止）、move（移動）這種動作，一定是某個具體物件在做。所以它應該是「物件.動作()」的形式，這就是物件導向的核心 —— 物件對自己的行為負責。

  而且 default 還能配合多型，static 不行

  interface Movable {
      void move();
      default void stop() {
          System.out.println("停止");
          // 可以呼叫 this 的其他方法、用物件的狀態
      }
  }

  class Fish implements Movable {
      public void move() { System.out.println("游"); }
  }

  class Car implements Movable {
      public void move() { System.out.println("開"); }
      public void stop() { System.out.println("煞車");  // 改寫成自己的版本
      }
  }
      }
  }

  Movable m = getSomething();   // 可能是 Fish 也可能是 Car
  m.stop();                     // 自動跑對應的版本 ← 這就是多型

  static 完全做不到這件事 —— 它一個固定版本、不能依物件不同而變化。

  什麼時候 Movable.xxx() 才直觀

  當那個動作跟任何具體物件都無關時，static 才合理：

  Movable.createDefault();   // 「製造一個 Movable」← 製造的當下還沒有物件，用型別呼叫才對
  Math.max(3, 5);            // 「算最大值」← 不屬於任何物件，純工具

  判斷口訣

  問自己一句：「這個動作有沒有一個『誰』在做？」

  - 有（某隻動物停、某台車開）→ 綁物件 → default（或一般實例方法）→ fish.stop()
  - 沒有（製造東西、純計算）→ 綁型別 → static → Movable.createDefault()

  所以結論：fish.stop() 不只是比較直觀，而是語意正確 + 能配合多型。static 在這裡是「用錯工具」，不是「比較省的另一種寫法」。

*/
}
