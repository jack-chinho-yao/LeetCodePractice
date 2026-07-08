package oop.abstraction.example;

public class Main {
    public static void main(String[] args) {
        // A1: Animal is abstract — `new Animal("x")` would NOT compile.

        // P3: upcasting — a child object held in a parent-typed reference.
        Animal dog = new Dog();            // this("Rex") -> super("Rex") chain
        Animal cat = new Cat("Kitty");
        Animal duck = new Duck("Donald");

        // P1: dynamic dispatch — the runtime type decides which speak() runs,
        //     even though the reference type is Animal.
        System.out.println(dog.describe());   // Rex says Woof~
        System.out.println(cat.describe());   // Kitty says Meow~
        System.out.println(duck.describe());  // Donald says Quack~

        // P2: polymorphic loop — one body handles every subtype, no instanceof.
        Animal[] zoo = { dog, cat, duck };
        for (Animal animal : zoo) {
            System.out.println(animal.speak());
        }

        // P4: capability check via instanceof + downcast. Explicit conditions on
        //     every branch (no bare else).
        for (Animal animal : zoo) {
            if (animal instanceof Swimmable) {
                ((Swimmable) animal).swim();
            } else if (animal instanceof Flyable) {
                ((Flyable) animal).fly();
            } else if (!(animal instanceof Swimmable) && !(animal instanceof Flyable)) {
                System.out.println(animal.getName() + " stays on the ground");
            }
        }

        // I4: call a static method on the interface itself (not on an instance).
        System.out.println(Movable.describeSpeedLimit());

        // I7: Duck resolved the move() conflict in favor of flying.
        ((Duck) duck).move();   // -> Flyable.super.move() -> fly()

        // A9: a Dog is also a Pet, so it carries the extra performTrick() duty.
        Pet pet = new Dog("Buddy");
        System.out.println(pet.performTrick());   // Buddy rolls over
    }
}

/*
    ============================================================================
    抽象相關概念總覽 (Abstraction Concept Map)
    ============================================================================

    --- Abstract class side (抽象類別) ---

    A1  Abstract class cannot be instantiated.
        抽象類別不能被 new；它只描述「共通的樣子」，由子類別補完。
        See: Animal (abstract), Pet (abstract).

    A2  Abstract method = signature only, no body; subclasses MUST implement.
        抽象方法只有簽名沒有實作，具體子類別一定要 override，否則自己也得是抽象。
        See: Animal.speak(), Pet.performTrick().

    A3  Concrete (shared) method inside an abstract class is inherited as-is.
        抽象類別裡也能放「已寫好」的共用方法，子類別直接繼承使用。
        See: Animal.getName().

    A4  Access modifiers: private hides state, protected exposes it to subclasses.
        private 把狀態藏起來只能透過方法取用；protected 讓子類別看得到、外人看不到。
        See: Animal.name (private), Animal.energy (protected).

    A5  super(...) — a subclass constructor calls its parent constructor FIRST.
        super(...) 是子類別建構子呼叫父類別建構子，必須是第一行。
        這就是你面試卡住的點：父類別沒有無參數建構子時，子類別「一定」要用 super(...) 把參數往上傳。
        See: Cat/Duck/Pet use super(name); Dog -> Pet -> Animal (two levels up).

    A6  this(...) — constructor chaining to ANOTHER constructor in the same class.
        this(...) 是同一個類別內，一個建構子呼叫另一個建構子（常用來塞預設值）。
        See: Animal(name) -> this(name, 100); Dog() -> this("Rex").

    A7  Template Method pattern — fixed algorithm + replaceable steps.
        範本方法：父類別定好流程骨架，把會變的步驟交給抽象方法。
        See: Animal.describe() orchestrates the fixed shape, speak() is the variable step.

    A8  final method — cannot be overridden; locks the algorithm.
        final 方法不能被子類別覆寫，用來保護不該被改的流程。
        See: Animal.describe() is final (Dog shows the forbidden override as a comment).

    A9  Re-abstraction — an abstract subclass that stays abstract and adds new
        abstract methods.
        再抽象：抽象子類別可以繼續保持抽象，並再宣告新的抽象方法。
        See: Pet extends Animal, still abstract, adds performTrick().

    --- Interface side (介面) ---

    I1  Interface + implements — a contract of capabilities.
        介面是一份「能力合約」，類別用 implements 履行。
        See: Movable.move(); Cat implements Swimmable.

    I2  A class can implement MANY interfaces (Java's answer to multiple inheritance).
        一個類別可以同時實作多個介面（Java 用這個取代多重繼承）。
        See: Duck implements Flyable, Swimmable.

    I3  default method — concrete behavior inside an interface.
        default 方法讓介面也能帶實作，實作類別不寫就用預設的。
        See: Flyable.move(), Swimmable.move(), Swimmable.dive().

    I4  static method in an interface — utility called on the interface itself.
        介面的 static 方法掛在介面上，用 Movable.xxx() 呼叫，不需要物件。
        See: Movable.describeSpeedLimit().

    I5  Constant — interface fields are implicitly public static final.
        介面欄位自動是 public static final 常數。
        See: Movable.MAX_SPEED, Swimmable.MAX_DEPTH.

    I6  Interface extends interface.
        介面可以繼承介面（而且能多重繼承介面）。
        See: Flyable extends Movable; Swimmable extends Movable.

    I7  Diamond / default-method conflict resolution with Interface.super.method().
        兩個介面給了同名 default 方法時會衝突，類別必須覆寫並用 介面.super.方法() 指定。
        See: Duck.move() picks Flyable.super.move().

    --- Polymorphism side (多型) ---

    P1  Dynamic dispatch — runtime type picks the method, not the reference type.
        動態分派：實際物件型別決定呼叫哪個方法，不是宣告的參考型別。

    P2  Polymorphic collection/loop — one body, many subtypes, no if/instanceof.
        多型陣列/迴圈：同一段程式碼處理所有子型別，新增子型別不用改迴圈。

    P3  Upcasting — child reference assigned to a parent-typed variable (implicit).
        向上轉型：子類別物件放進父型別變數，自動發生、永遠安全。

    P4  Downcasting + instanceof — narrow back to a subtype to use extra abilities.
        向下轉型搭配 instanceof：先檢查型別再轉回子型別，才能用子型別獨有的能力。

    P5  @Override — compiler-checked declaration that you are replacing a method.
        @Override 讓編譯器幫你確認真的有覆寫到（簽名打錯會報錯）。

    ============================================================================
    Real-world usage (實務上哪裡會用到)
    ============================================================================

    - Abstract class + Template Method:
      框架的生命週期鉤子。Android Activity / Servlet HttpServlet 定好流程，
      你只覆寫 onCreate() / doGet() 這些「會變的步驟」。
      JUnit 的 setUp()/tearDown()、Spring 的 AbstractController 同理。

    - Interface + multiple implements:
      Java 標準庫到處都是：List、Comparable、Runnable、Iterable。
      一個類別常常同時是 Comparable + Serializable + Cloneable。

    - default method:
      JDK 8 在 List/Collection 加上 stream()、forEach() 而不破壞舊有實作類別，
      靠的就是 default 方法（向後相容地擴充介面）。

    - Polymorphism + downcasting:
      事件處理、外掛系統、支付通道（PaymentMethod 介面，CreditCard / ApplePay
      各自實作），用同一段流程跑不同實作；需要特殊能力時才 instanceof 偵測。

    - super(...) constructor:
      任何繼承框架基底類別的場景：extends Exception 自訂例外要 super(message)；
      extends Thread / AbstractList 都得把參數往上傳給父類別建構子。
*/
