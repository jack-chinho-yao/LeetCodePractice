package oop.collectiontest;

import java.util.Arrays;
import java.util.HashSet;

public class SetPractice {

    public static void main(String[] args) {
        HashSet<Object> set = new HashSet<>();
        set.add("Jack");
        set.add("Cath");
        set.add("Apple");
        set.add("Banana");
        System.out.println(set);
        set.remove("Banana");
        System.out.println(set);
    }
}
