package leetcode2026.p14longestcommonprefix;


public class P14V1LongestCommonPrefixNaiveSolution {

    public String longestCommonPrefix(String[] strs) {
        String result = "";
        int count = 0;
        String str1 = strs[0];
        int minLength = Integer.MAX_VALUE;
        while(count < str1.length()){
            count++;
            result = str1.substring(0, count);
            for (int i = 0; i < strs.length; i++) {
                minLength = Math.min(strs[i].length(),minLength);
                if (result.length() > minLength || !result.equalsIgnoreCase(strs[i].substring(0, count))){
                    return result.substring(0, count-1);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        P14V1LongestCommonPrefixNaiveSolution solution = new P14V1LongestCommonPrefixNaiveSolution();
//        System.out.println(solution.longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        System.out.println(solution.longestCommonPrefix(new String[]{"ab", "a"}));
//        System.out.println(solution.longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
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

    */
}
