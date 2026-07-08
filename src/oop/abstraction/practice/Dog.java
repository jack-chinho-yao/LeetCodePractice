package oop.abstraction.practice;

// TODO: make Dog EXTEND Pet (two levels of inheritance: Dog -> Pet -> Animal).
class Dog extends Pet{

    // TODO (A6): no-arg constructor Dog() that chains to Dog("Rex") via this(...).
    public Dog(){
       this("Rex");
    }

    // TODO (A5): constructor Dog(String name) that calls super(name).
    public Dog(String name){
        super(name);
    }

    // TODO (P5): @Override speak() to return "Woof~".
    @Override
    public String speak(){
        return " Woof ~";
    }

    // TODO (P5): @Override performTrick() to return getName() + " rolls over".
    public String performTrick(){
        return getName() + " rolls over.";
    }



    // A8 note: you must NOT override describe() — it is final in Animal.
}
