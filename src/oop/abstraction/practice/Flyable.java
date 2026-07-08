package oop.abstraction.practice;

// TODO (I6): make this interface EXTEND Movable.
interface Flyable extends Movable {

    // TODO: declare the abstract method  void fly();
    void fly();

    // TODO (I3): provide a DEFAULT method move() that satisfies Movable by calling fly().
    //            (annotate it with @Override since it implements Movable.move())
    @Override
    default void move(){
        System.out.println("Flyable is a kind of Movable");
    }

}

/*你抓到一個很準的點 —— 用「關鍵字」來判斷比用「class 還是 interface」更可靠。你的直覺對。

  看關鍵字，規則很乾淨

  ┌────────────┬────────────────┐
  │   關鍵字   │      關係      │
  ├────────────┼────────────────┤
  │ extends    │ IS-A（是一種） │
  ├────────────┼────────────────┤
  │ implements │ CAN-DO（會做） │
  └────────────┴────────────────┘

  驗證一下三種情況：

  class Cat extends Animal              // extends → IS-A：Cat 是一種 Animal ✅
  class Cat implements Swimmable        // implements → CAN-DO：Cat 會游泳 ✅
  interface Flyable extends Movable     // extends → IS-A：Flyable 是一種 Movable ✅

  特別看第三行：Flyable 是 interface，Movable 也是 interface，但因為用的是 extends，所以是 IS-A（會飛的是一種會移動的）。

  這正好證明你的問題答案 —— 不能只看「是不是 interface」。如果照「interface = can-do」去硬套，你會誤以為 Flyable extends Movable 是 can-do，但它其實是 IS-A。真正決定的是 extends 這個關鍵字，不是 Movable 是 interface
  這件事。

  但有個更底層的真相：關鍵字不是你自己選的

  你不能隨便挑關鍵字 —— 關鍵字是被「左右兩邊的型別」強制決定的：

  ┌───────────┬───────────────────┬────────────┬────────┐
  │   左邊    │       右邊        │   只能用   │  關係  │
  ├───────────┼───────────────────┼────────────┼────────┤
  │ class     │ class             │ extends    │ IS-A   │
  ├───────────┼───────────────────┼────────────┼────────┤
  │ class     │ interface         │ implements │ CAN-DO │
  ├───────────┼───────────────────┼────────────┼────────┤
  │ interface │ interface         │ extends    │ IS-A   │
  ├───────────┼───────────────────┼────────────┼────────┤
  │ class     │ interface（多個） │ implements │ CAN-DO │
  └───────────┴───────────────────┴────────────┴────────┘

  class Cat implements Animal { }    // ❌ 編譯錯誤！Animal 是 class，不能用 implements
  class Cat extends Swimmable { }     // ❌ 編譯錯誤！Swimmable 是 interface，不能用 extends

  所以「看關鍵字」和「看 class/interface」其實不是兩件獨立的事 —— 型別組合決定了你只能用哪個關鍵字，關鍵字再對應到 IS-A 或 CAN-DO。

  那為什麼說「看關鍵字」更好用

  因為有 interface extends interface 這個情況：

  - 如果你只看「右邊是 interface」→ 會猜 can-do → 錯
  - 如果你看「用的是 extends」→ IS-A → 對

  所以關鍵字是更穩的判斷依據，它把那個容易搞混的情況也涵蓋對了。

  一句話總結

  - extends = IS-A，implements = CAN-DO ← 記這個就好，最準。
  - 你沒得選關鍵字 —— class↔class 和 interface↔interface 強制 extends、class→interface 強制 implements。
  - 所以「看關鍵字」其實就等於「看型別組合」，只是用關鍵字念起來更直接、也不會被 interface extends interface 騙到。

*/