package oop.abstraction.example;

// Contrast class: extends an abstract class AND implements a SINGLE interface.
class Cat extends Animal implements Swimmable {

    public Cat(String name) {
        super(name);   // A5
    }

    @Override
    public String speak() {
        return "Meow~";
    }

    @Override
    public void swim() {
        System.out.println(getName() + " paddles reluctantly");
    }

    // Note: move() and dive() are inherited for free from Swimmable's defaults,
    //       so Cat does not need to implement them. No conflict here because Cat
    //       implements only one interface that supplies move().
}
