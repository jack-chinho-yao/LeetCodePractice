package oop.abstraction.example;

// Concrete class. extends Pet (which extends Animal) — two levels of inheritance.
class Dog extends Pet {

    // A6: no-arg constructor chains to the name-taking one with a default name.
    public Dog() {
        this("Rex");
    }

    // A5: super(name) -> Pet(name) -> Animal(name) -> Animal(name, energy).
    public Dog(String name) {
        super(name);
    }

    @Override                 // P5: override the abstract method from Animal.
    public String speak() {
        return "Woof~";
    }

    @Override                 // P5: override the abstract method from Pet.
    public String performTrick() {
        return getName() + " rolls over";
    }

    // A8 reminder: you CANNOT override describe() here — it is `final` in Animal.
    //     Uncommenting the lines below would be a compile error:
    // @Override
    // public String describe() { return "nope"; }
}
