package oop.abstraction.practice;

// TODO (A1): make this class ABSTRACT.
abstract class Animal {

    // A4: state is given to you — keep name private, energy protected.
    private final String name;
    protected int energy;

    // TODO (A6): convenience constructor Animal(String name) that chains to the
    //            full constructor below using this(name, 100).
    protected Animal(String name) {
        this(name, 100);   // <-- remove this once you use this(...) chaining
    }

    // A5 target: subclasses will reach this via super(...).
    protected Animal(String name, int energy) {
        this.name = name;
        this.energy = energy;
    }

    // TODO (A2): declare an ABSTRACT method  String speak();  (no body).
    public abstract String speak(); // 沒有方法本體{}內容易 就一定要加上abstract

    // TODO (A3): implement getName() to return name.
    public String getName() {
        return this.name; // TODO
    }

    // TODO (A7 + A8): implement a FINAL method describe() that returns
    //                 getName() + " says " + speak().  Mark it final.
    public final String describe() {
        return getName() + " says " + speak(); // TODO  (and add `final`)
    }
}
