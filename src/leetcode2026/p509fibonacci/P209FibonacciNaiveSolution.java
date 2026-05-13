package leetcode2026.p509fibonacci;

import java.util.ArrayList;
import java.util.List;

public class P209FibonacciNaiveSolution {

    public static List<Integer> createFibonacci(int n){
        List<Integer> result = new ArrayList<>();
        int i = 0;
        if (i < n){
            result.add(0);
            i++;
        }
        if (i < n){
            result.add(1);
            i++;
        }
        for(i = 2; i < n; i++){
            result.add(result.get(i-2) + result.get(i-1));
        }
        return result;
    }
    public static void main(String[] args) {
        // [0, 1, 2, 3, 5, 8, 13]
        System.out.println(createFibonacci(0));
        System.out.println(createFibonacci(1));
        System.out.println(createFibonacci(2));
        System.out.println(createFibonacci(3));
        System.out.println(createFibonacci(8));
        System.out.println(createFibonacci(13));


    }
}

