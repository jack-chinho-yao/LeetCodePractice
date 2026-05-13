package leetcode2026.p14longestcommonprefix;

public class P14V2LongestCommonPrefixSolutionPractice {

    // Vertical scanning: compare char-by-char across all strings. Time O(S), Space O(1), S = total chars.
    public String longestCommonPrefix(String[] strs) {
        for(int i = 0; i < strs[0].length(); i++){
            char c = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++){
                if(strs[j].length() <= i || !(c - strs[j].charAt(i)  == 0)){
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }

    public static void main(String[] args) {
        P14V2LongestCommonPrefixSolutionPractice solution = new P14V2LongestCommonPrefixSolutionPractice();
//        System.out.println(solution.longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
//        System.out.println(solution.longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        System.out.println(solution.longestCommonPrefix(new String[]{"ab", "a"}));
//        System.out.println(solution.longestCommonPrefix(new String[]{"a"}));
    }




    /*
    Example 1:

    Input: strs = ["flower","flow","flight"]
    Output: "fl"

    Example 2:

    Input: strs = ["dog","racecar","car"]
    Output: ""
    Explanation: There is no common prefix among the input strings.

    Constraints:
    - 1 <= strs.length <= 200
    - 0 <= strs[i].length <= 200
    - strs[i] consists of only lowercase English letters if it is non-empty.


    Approach: Vertical scanning
    ---------------------------
    Fix a column index i, take strs[0].charAt(i) as the reference character,
    then compare it against strs[j].charAt(i) for every other string j. Return
    strs[0].substring(0, i) as soon as we hit either:
      - a length mismatch: i >= strs[j].length()  (strs[j] ran out of chars)
      - a character mismatch: c != strs[j].charAt(i)
    If the outer loop completes, strs[0] itself is the common prefix.

    Why this beats V1:
      - No redundant state (no running minLength, no rebuilt substring)
      - Substring is only built once, on the return path
      - Compares chars directly instead of constructing and comparing substrings
      - The "everything matched" edge case is expressed directly by
        `return strs[0]`, not relied on via an overwritten variable

    Complexity:
      - Time:  O(S) worst case, where S = sum of lengths of all strings.
               Early exit on first mismatch makes it much faster in practice.
      - Space: O(1) extra (excluding the returned substring).

    Real-world usage of LCP:
      - Autocomplete / type-ahead: display the shared prefix of suggestions
        as the user types.
      - Trie-based prefix search: LCP is the core operation for dictionaries,
        spellcheck, and word-completion systems.
      - Longest-prefix match in networking: IP routing and CIDR subnet lookup
        pick the route with the longest matching address prefix.
      - File system / URL paths: find the common parent directory or route
        prefix of a set of paths.
      - Bioinformatics: compare DNA or protein sequences for shared leading
        regions.
      - Diff tools / version control: skip the unchanged prefix of two
        strings before computing a diff.
    */
}
