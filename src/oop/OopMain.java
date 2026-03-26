package oop;

public class OopMain {
    public static void main(String[] args) {
        Animal dog = new Dog("dog1");
        Animal cat = new Cat("cat1");

        System.out.println(dog.speak());
        System.out.println(dog.getName());
        System.out.println(cat.speak());

        Animal[] animals = {new Dog("dog2"), new Cat("cat2")};
        for(Animal animal : animals){
            System.out.println(animal.speak());
        }
    }
}

/*
    === OOP Concepts Demonstrated ===

    1. Abstraction
       - Animal is an abstract class that defines speak() without implementation.
       - It exposes "what" an animal does (speak), but hides "how".
       - Dog and Cat each provide their own implementation.

    2. Polymorphism
       - Variables are declared as the parent type Animal, but instantiated with child types (Dog, Cat).
       - Java picks the correct speak() at runtime based on the actual object type.
       - Looping over Animal[] demonstrates this: one piece of code handles all subtypes
         without if/instanceof checks. Adding new animals requires no changes to the loop.

    3. Abstract Class vs Interface
       - Both define methods that concrete subclasses must implement.
       - Both allow "re-abstracting": an abstract class can extend another abstract class,
         and an interface can extend another interface, without implementing methods.
       - Key differences:
         * Abstract class: extends one only, can have fields/constructors/concrete methods.
         * Interface: implements many, only static final constants, no constructors.
       - Use abstract class when subtypes share common state/fields.
       - Use interface when unrelated classes share capabilities.
*/
