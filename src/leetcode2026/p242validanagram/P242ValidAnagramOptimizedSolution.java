package leetcode2026.p242validanagram;

public class P242ValidAnagramOptimizedSolution {

    // int[26] counting, Time O(n), Space O(1)
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] intArray = new int[26];
        for(int i = 0; i < s.length(); i++){
            char sChar = s.charAt(i);
            char tChar = t.charAt(i);
            intArray[sChar - 'a']++;
            intArray[tChar - 'a']--;
        }

        for (int component: intArray){
            if (component != 0) return false;
        }


        return true;
    }

    public static void main(String[] args) {
        P242ValidAnagramOptimizedSolution solution = new P242ValidAnagramOptimizedSolution();
        solution.isAnagram("rat","cat");

    }
}

/*
 * Approach:
 *   Use int[26] array to count character frequency (only lowercase a-z).
 *   char - 'a' maps each character to index 0-25.
 *   +1 for chars in s, -1 for chars in t. If all values are 0, it's an anagram.
 *
 * Key learnings:
 *   - int[26] avoids HashMap overhead (no boxing/unboxing of Character and Integer).
 *   - char arithmetic: 'a' - 'a' = 0, 'b' - 'a' = 1, ..., 'z' - 'a' = 25.
 *   - new int[26] automatically initializes all elements to 0.
 *   - Space is O(1) since array size is fixed at 26 regardless of input.
 *
 * P242 is the foundation for P49 Group Anagrams:
 *   - P242 teaches frequency counting to compare two strings.
 *   - P49 extends this by using the frequency as a HashMap key to group strings.
 */
