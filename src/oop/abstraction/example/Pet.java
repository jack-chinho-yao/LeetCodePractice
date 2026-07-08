package oop.abstraction.example;

// A9: re-abstraction. An abstract class can extend another abstract class and
//     STAY abstract — it may implement some duties and add brand-new abstract
//     ones. Pet is still an Animal, but now every Pet also owes a trick.
abstract class Pet extends Animal {

    protected Pet(String name) {
        super(name);   // A5: super(...) — hand the name up to Animal's constructor.
    }

    // A new abstract method introduced at this level; concrete pets must define it.
    public abstract String performTrick();
}
