package oop.abstraction.example;

// Base capability interface. Anything that can change position is Movable.
interface Movable {

    // I5: interface constant — implicitly `public static final`, no instance field.
    int MAX_SPEED = 120;

    // I1: abstract method — implicitly `public abstract`, no body.
    void move();

    // I4: static method — belongs to the interface itself, not to instances.
    //     Called as Movable.describeSpeedLimit(), never on an object.
    static String describeSpeedLimit() {
        return "Top speed capped at " + MAX_SPEED + " km/h";
    }
}
