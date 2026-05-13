package oop.referencetest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String name;
    private Integer id;

    public static void changeName(User user){
        user.setName("Catherine");
    }

    public static void changeUser(User user){
        user = User.builder().name("Cath").id(2).build();
        System.out.println(user);
    }

    public static void main(String[] args) {
        User jack = User.builder().name("Jack").id(1).build();
        changeUser(jack);
        System.out.println(jack.toString());
        changeName(jack);
        System.out.println(jack.toString());
    }
}


