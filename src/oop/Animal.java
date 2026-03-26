package oop;

public abstract class Animal {
    private String name;
    public abstract String speak();
    Animal(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
