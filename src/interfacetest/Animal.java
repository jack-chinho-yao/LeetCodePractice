package interfacetest;

public interface Animal {
    default void makeNoise(){
        System.out.println("default make noise");
    }
}
