package oop.abstraction.example;

// The showcase class: a Duck IS-A Animal, and it can BOTH fly and swim.
// I2: one class implements MULTIPLE interfaces.
class Duck extends Animal implements Flyable, Swimmable {

    public Duck(String name) {
        super(name);   // A5
    }

    @Override
    public String speak() {
        return "Quack~";
    }

    @Override
    public void fly() {
        System.out.println(getName() + " flaps into the air");
    }

    @Override
    public void swim() {
        System.out.println(getName() + " glides on the pond");
    }

    // I7: default-method conflict. Flyable AND Swimmable each give a default move().
    //     Java refuses to guess — the class MUST override move() and pick one
    //     explicitly with the Interface.super.method() syntax.
    @Override
    public void move() {
        Flyable.super.move();   // on purpose: a duck prefers to fly to get around
    }
}
