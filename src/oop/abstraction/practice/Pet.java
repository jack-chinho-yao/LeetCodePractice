package oop.abstraction.practice;

// TODO (A9): make this class ABSTRACT and have it EXTEND Animal (re-abstraction).
abstract class Pet extends Animal{

    // TODO (A5): constructor Pet(String name) that calls super(name).
    public Pet(String name){
        super(name);
    }

     @Override
     public String speak(){
        return " Pet ";
     }

    // TODO: declare a NEW abstract method  String performTrick();
    public abstract String performTrick();
}
