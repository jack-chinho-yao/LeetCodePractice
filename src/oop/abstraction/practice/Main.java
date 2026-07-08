package oop.abstraction.practice;

public class Main {
    public static void main(String[] args) {
        // Once you've filled in the other files, uncomment and complete this driver.
        // Goal: reproduce the same behavior as ../example/Main.java.
//        Animal animal = new Animal(); // Animal is abstract; cannot be instantiated.
        // TODO (P3 upcasting): Animal dog = new Dog();  etc. for Cat, Duck.
        Cat cat = new Cat("Catherine");
        System.out.println(cat.getName());
        System.out.println(cat.speak());
        cat.move(); //#202606121404 Cat cat = new Cat("Catherine"); 才能用 Animal cat = new Cat("Catherine"); 不行 因為是Cat才有實作Movable介面
        System.out.println(cat.describe());

        Dog dog = new Dog("Jack");
        System.out.println();
        System.out.println(dog.speak());
        System.out.println(dog.performTrick());

        // TODO (P1 dynamic dispatch): print dog.describe() / cat.describe() / duck.describe().
        System.out.println(cat.describe());
        System.out.println(dog.describe());

        Duck duck = new Duck("Jacky");
        System.out.println(duck.speak());
        duck.swim();
        duck.move();

        System.out.println("=============");
        // TODO (P2 polymorphic loop): loop over an Animal[] and print speak().
        Animal[] animals = {
                new Cat("CatherineInArrays"),
                new Dog("JackInArrays"),
                new Duck("JackyInArrays"),
        };
        for(int i = 0; i < animals.length; i++){
            System.out.println(animals[i].speak());
        }
        System.out.println("=============P4");

        // TODO (P4 instanceof + downcast): for each animal, if Swimmable -> swim(),
        //       else if Flyable -> fly(), else if neither -> "stays on the ground".
        for(int i = 0; i < animals.length; i++){
            Animal animal = animals[i];
            System.out.println(animal.getName());
            if (animal instanceof Swimmable){
                System.out.println("Swimmable");
                ((Swimmable) animal).swim();
            }else if (animal instanceof Flyable){
                System.out.println("Flyable");
                ((Flyable) animal).fly();
            }else {
                System.out.println("stays on the ground");
            }
        }

        // TODO (I4): print Movable.describeSpeedLimit().
        System.out.println("<<<<<<<<<<I4");
            System.out.println(Movable.describeSpeedLimit());
//            cat.describeSpeedLimit();Static method may only be called on its containing interface

        System.out.println(">>>>>>>>>>I4");
        // TODO (I7): cast duck to Duck and call move().
        System.out.println("<<<<<<<<<<I7");
        for(int i = 0; i < animals.length; i++){
            Animal animal = animals[i];
            System.out.println(animal.getName());
            if (animal instanceof Duck){
                System.out.println("Duck");
                ((Duck) animal).move();
            }
        }

        System.out.println(">>>>>>>>>>I7");
        // TODO (A9): Pet pet = new Dog("Buddy"); print pet.performTrick().
        System.out.println("<<<<<<<<<<A9");
        Pet pet = new Dog("Buddy");
        System.out.println(pet.performTrick());
        System.out.println(">>>>>>>>>>A9");
    }
}

/*
    ============================================================================
    本練習要練到的抽象概念清單 (Checklist)  —  細節對照 ../example/Main.java 底部
    ============================================================================

    Abstract class 側:
      [ ] A1  abstract class 不能被 new
      [ ] A2  abstract method（只有簽名）
      [ ] A3  abstract class 內的共用 concrete method
      [ ] A4  private vs protected 欄位
      [ ] A5  super(...) 呼叫父類別建構子   ← 面試卡過的重點
      [ ] A6  this(...) 同類別建構子鏈
      [ ] A7  Template Method（describe 呼叫抽象 speak）
      [ ] A8  final method 不能被覆寫
      [ ] A9  re-abstraction（Pet 仍抽象並新增抽象方法）

    Interface 側:
      [ ] I1  interface + implements
      [ ] I2  一個類別實作多個介面（Duck）
      [ ] I3  default method
      [ ] I4  interface 的 static method
      [ ] I5  interface 常數（public static final）
      [ ] I6  interface extends interface
      [ ] I7  default 衝突用 Interface.super.method() 解決

    Polymorphism 側:
      [ ] P1  dynamic dispatch
      [ ] P2  多型陣列/迴圈
      [ ] P3  upcasting
      [ ] P4  downcasting + instanceof
      [ ] P5  @Override

    寫完後到 src/oop/abstraction 編譯執行，輸出應與 example 版一致。
*/

/* #202606121404
* 所以游泳跟移動是Cat的事情 不是Animal的事情？

⏺ 對 —— 就你現在的程式碼設計而言，沒錯。但這是一個「設計選擇」，不是 Java 規定，我幫你把它講清楚。

  你現在的結構長這樣

  Animal (abstract)          ← 定義「動物共通的事」：name、speak()、describe()
    ↑ extends
  Cat ──implements──> Swimmable ──extends──> Movable
                         ↑ swim()              ↑ move()

  - Animal 管的事：每隻動物都有的 —— 名字 (getName)、叫聲 (speak)、自我介紹 (describe)。
  - move() / swim()：來自 Swimmable，而只有 Cat 去 implements 它。所以這兩個能力是「會游泳的東西」才有的，剛好 Cat 是其中之一。

  所以從 Animal 的視角，它根本不知道有 move/swim 這回事 —— 這就是為什麼 Animal cat 呼叫不到 move()。

  這個設計在表達什麼

  ▎ 「不是所有動物都會移動/游泳，所以我不把它放進 Animal，而是做成一個獨立能力 Swimmable，誰會就誰去 implements。」

  這其實是一個蠻常見且合理的設計觀點 —— 用 interface 來表達「附加能力」，而不是硬塞進繼承體系。比如：

  class Fish  extends Animal implements Swimmable { }   // 魚會游
  class Bird  extends Animal implements Flyable    { }   // 鳥會飛
  class Snake extends Animal { }                          // 蛇兩個都不 implements

  但你也可以選另一種觀點

  如果你認為「所有動物本來就都會移動」，那 move() 應該屬於 Animal：

  class Snake extends Animal { }                          // 蛇兩個都不 implements

  但你也可以選另一種觀點

  如果你認為「所有動物本來就都會移動」，那 move() 應該屬於 Animal：

  abstract class Animal implements Movable { }   // 移動是動物的本質

  這樣就變成「移動是 Animal 的事，游泳才是 Cat（Swimmable）的事」。

  怎麼判斷該放哪

  問一句：「這個能力是『所有』這類東西都有的嗎？」

  ┌──────────────────────┬───────────────────────┬───────────────────────────────────┐
  │         能力         │    所有動物都有嗎     │               放哪                │
  ├──────────────────────┼───────────────────────┼───────────────────────────────────┤
  │ 有名字、會叫 (speak) │ ✅  是                 │ Animal（共通本質）                │
  ├──────────────────────┼───────────────────────┼───────────────────────────────────┤
  │ 游泳 (swim)          │ ❌  不是（只有部分會） │ interface Swimmable（附加能力）   │
  ├──────────────────────┼───────────────────────┼───────────────────────────────────┤
  │ 移動 (move)          │ 🤔 看你怎麼定義       │ 都會 → Animal；不一定 → interface │
  └──────────────────────┴───────────────────────┴───────────────────────────────────┘

  ---
  所以總結：

  - 「是不是 Cat 的事」取決於你把能力放在哪一層。 現在你把 move/swim 放在 Swimmable 介面、只有 Cat implements，所以它們現在是「會游泳的東西的事」，Animal 管不到。
  - 共通本質 → 放 Animal；只有部分才有的附加能力 → 放 interface。 這就是 abstract class 和 interface 在設計上的分工。

*/