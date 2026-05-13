package leetcode2026.p509fibonacci;

import java.util.ArrayList;
import java.util.List;

public class P209FibonacciNaiveSolutionV2ForLeetCode {

    public static int createFibonacci(int n){
        List<Integer> result = new ArrayList<>();

        if(n >= 0) {
            result.add(0);
        }
        if(n >= 1) {
            result.add(1);
        }

        for(int i= 2; i <= n; i++){
            result.add(result.get(i-2) + result.get(i-1));
        }
        return result.get(n);
    }
    public static void main(String[] args) {
        // [0, 1, 2, 3, 5, 8, 13]
        System.out.println(createFibonacci(0));
        System.out.println(createFibonacci(1));
        System.out.println(createFibonacci(2));
        System.out.println(createFibonacci(3));
        System.out.println(createFibonacci(4));
        System.out.println(createFibonacci(5));
        System.out.println(createFibonacci(6));
        System.out.println(createFibonacci(7));
        System.out.println(createFibonacci(8));
        System.out.println(createFibonacci(13));


    }
}

