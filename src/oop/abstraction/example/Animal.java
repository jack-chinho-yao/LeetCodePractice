package oop.abstraction.example;

// A1: abstract class — cannot be instantiated directly (`new Animal(...)` won't compile).
//     It captures what EVERY animal shares (a name, the ability to "describe" itself)
//     while leaving the variable part (speak) for subclasses.
abstract class Animal {

    // A4: private field — hidden state, reachable only through getName().
    private final String name;

    // A4: protected field — visible to subclasses, but not to outside code.
    protected int energy;

    // A6: convenience constructor that chains to the full one via this(...).
    protected Animal(String name) {
        this(name, 100);
    }

    // A5 target: subclasses reach this through super(...).
    protected Animal(String name, int energy) {
        this.name = name;
        this.energy = energy;
    }

    // A2: abstract method — no body; every concrete subclass MUST implement it.
    public abstract String speak();

    // A3: concrete shared method — inherited as-is by all subclasses.
    public String getName() {
        return name;
    }

    // A7 + A8: Template Method. describe() fixes the algorithm (name + " says " + sound)
    //          and delegates only the changing step to abstract speak().
    //          It is `final` (A8) so no subclass can alter the algorithm's shape.
    public final String describe() {
        return getName() + " says " + speak();
    }
}
