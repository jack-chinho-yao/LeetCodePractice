package oop;

public class Cat extends Animal{
    Cat(String name) {
        super(name);
    }

    @Override
    public String speak(){
        return "Meow~";
    }
}
