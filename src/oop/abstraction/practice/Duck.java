package oop.abstraction.practice;

// TODO (I2): make Duck EXTEND Animal and IMPLEMENT BOTH Flyable and Swimmable.
class Duck extends Animal implements Flyable, Swimmable{

    // TODO (A5): constructor Duck(String name) that calls super(name).
    public Duck(String name){
        super(name);
    }

    // TODO (P5): @Override speak() to return "Quack~".
    @Override
    public String speak(){
        return "Quack~";
    }

    // TODO: @Override fly() to print getName() + " flaps into the air".
    @Override
    public void fly(){
        System.out.println(getName() + " flaps into the air");
    }

    // TODO: @Override swim() to print getName() + " glides on the pond".
    @Override
    public void swim(){
        System.out.println(getName() + " glides on the pond ");
    }

    @Override
    public void move(){
        Swimmable.super.move();
    }
/*
*  所以 Flyable          Swimmable
   這兩個選一個 而不是用Movable.super.move()
  ⎿  ⧉ Selected 1 lines from src/oop/abstraction/practice/Duck.java in IntelliJ IDEA

⏺ 對，只能選 Flyable 或 Swimmable，不能寫 Movable.super.move()。原因很關鍵：

  Movable.super.move() 是非法的，因為 Movable 的 move() 沒有 body

  回想一下三個介面的 move()：

  interface Movable {
      void move();              // ← 抽象，沒 body
  }
  interface Flyable extends Movable {
      default void move() {...}  // ← 有 body（default）
  }
  interface Swimmable extends Movable {
      default void move() {...}  // ← 有 body（default）
  }

  介面名.super.move() 的意思是「去呼叫那個介面裡『現成的實作』」。但 Movable.move() 根本沒有實作（它是抽象的、只有宣告）—— 沒有東西可以呼叫，所以：

  Movable.super.move();   // ❌ 編譯錯誤：Movable.move() 沒有 body，無從呼叫

  規則：X.super.方法() 的 X 必須「有提供 default 實作」

  ┌───────────┬────────────────────┬───────────────────────┐
  │   介面    │    它的 move()     │ 能不能 X.super.move() │
  ├───────────┼────────────────────┼───────────────────────┤
  │ Movable   │ 抽象（沒 body）    │ ❌ 沒東西可呼叫        │
  ├───────────┼────────────────────┼───────────────────────┤
  │ Flyable   │ default（有 body） │ ✅                  │  │
  ├───────────┼────────────────────┼───────────────────────┤
  │ Swimmable │ default（有 body） │ ✅                     │
  └───────────┴────────────────────┴───────────────────────┘

  而且還有個限制：X 必須是 Duck 直接 implements 的介面。Duck 寫的是 implements Flyable, Swimmable，所以這兩個可以；Movable 是「阿公層」（透過 Flyable/Swimmable 間接來的），就算它有 default
  你也不能跳過直接父介面去呼叫它。

  為什麼衝突只在 Flyable vs Swimmable 之間

          Movable          move() 抽象 ← 沒人會跟它「衝突」，因為它沒實作
          /      \
     Flyable    Swimmable  各自有一版 default move() ← 衝突發生在這兩個之間
          \      /
           Duck            收到兩個「現成的」move()，二選一（或自己處理）

  衝突的本質是「有兩個現成的實作」打架。Movable 那層沒有實作，不參與打架 —— 它只是當初定義「要有 move()」的那個契約源頭。Duck 要解決的是「Flyable 給的實作 vs Swimmable 給的實作，用哪個」。

  一句話總結

  - X.super.move() 的 X 必須是「有提供 default 實作」且「你直接 implements」的介面 → 只有 Flyable、Swimmable 符合。
  - Movable.super.move() ❌  —— 它的 move() 是抽象的，沒有 body 可以呼叫，而且它也不是 Duck 直接 implements 的。
  - 所以你的選項就是 Flyable.super.move() / Swimmable.super.move() 二選一（或兩個都呼叫、或自己另寫）。

* */
}
