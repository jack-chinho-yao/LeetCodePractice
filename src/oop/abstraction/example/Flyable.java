package oop.abstraction.example;

// I6: interface extends interface — Flyable IS-A Movable, plus it can fly.
interface Flyable extends Movable {

    void fly();

    // I3: default method — concrete behavior living inside an interface.
    //     A flyer satisfies the inherited Movable.move() by flying.
    @Override
    default void move() {
        fly();
    }
}
