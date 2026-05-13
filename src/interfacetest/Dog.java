package interfacetest;

public class Dog implements Animal{

    @Override
    public void makeNoise(){
        System.out.println("make noise by dog, bark!");
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.makeNoise();
    }
}
