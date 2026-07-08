package oop.abstraction.practice;

// TODO: make Cat EXTEND Animal and IMPLEMENT a single interface, Swimmable.
class Cat extends Animal implements Swimmable{

    // TODO (A5): constructor Cat(String name) that calls super(name).
    public Cat(String name){
        super(name);
    }

    // TODO (P5): @Override speak() to return "Meow~".
    @Override
    public String speak(){
        return "Cat : Meow~";
    }

    // TODO: @Override swim() to print getName() + " paddles reluctantly".
    @Override
    public void swim(){
        System.out.println("Cat: paddles reluctantly ");
    }

    @Override
    public void move(){
        System.out.println("Cat: move() ");
    };




    // Note: move()/dive() come free from Swimmable's default methods — no conflict
    //       because Cat implements only one interface that supplies move().
}
