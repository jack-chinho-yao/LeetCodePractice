package leetcode2026.p344reversestring;

import java.util.Arrays;

public class P344V1ReverseStringNaiveSolution {

    public void reverseString(char[] s) {
        // TODO: implement naive approach
        int length = s.length;
        char[] reversed = new char[length];
        for(int i = 0; i < length; i++){
            reversed[i] = s[length - 1 - i];
        }
        for(int i = 0; i < length; i++){
            s[i] = reversed[i];
        }
    }

    public static void main(String[] args) {
        P344V1ReverseStringNaiveSolution solution = new P344V1ReverseStringNaiveSolution();

        char[] s1 = {'h', 'e', 'l', 'l', 'o'};
        solution.reverseString(s1);
        System.out.println(Arrays.toString(s1)); // expected: [o, l, l, e, h]

        char[] s2 = {'H', 'a', 'n', 'n', 'a', 'h'};
        solution.reverseString(s2);
        System.out.println(Arrays.toString(s2)); // expected: [h, a, n, n, a, H]
    }

    /*
    Problem 344. Reverse String

    Write a function that reverses a string. The input string is given as an
    array of characters `s`.

    You must do this by modifying the input array in-place with O(1) extra memory.

    Example 1:
        Input : s = ['h', 'e', 'l', 'l', 'o']
        Output:     ['o', 'l', 'l', 'e', 'h']

    Example 2:
        Input : s = ['H', 'a', 'n', 'n', 'a', 'h']
        Output:     ['h', 'a', 'n', 'n', 'a', 'H']

    Constraints:
    - 1 <= s.length <= 10^5
    - s[i] is a printable ASCII character.
    - Must be done in-place with O(1) extra memory.
    */
}
