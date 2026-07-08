package oop.abstraction.example;

// Second capability interface. Also extends Movable, so it ALSO supplies a
// default move() — this is what creates the conflict that Duck must resolve.
interface Swimmable extends Movable {

    // I5: another interface constant.
    int MAX_DEPTH = 100;

    void swim();

    // I3: default move() — a swimmer moves by swimming.
    @Override
    default void move() {
        swim();
    }

    // I3: a second default method built on top of the abstract swim().
    default void dive() {
        System.out.println("Diving down to " + MAX_DEPTH + "m");
        swim();
    }
}
