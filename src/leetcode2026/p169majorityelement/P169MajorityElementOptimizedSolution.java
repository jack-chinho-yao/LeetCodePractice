package leetcode2026.p169majorityelement;

public class P169MajorityElementOptimizedSolution {

    public int majorityElement(int[] nums) {
        int length = nums.length;
        int major = nums[0];
        int majorCount = 1;
        for(int i = 1; i < length; i++){
            int num1 = nums[i];
            if (num1 != major){
                majorCount --;
                if (majorCount == 0){
                    i++;
                    major = nums[i];
                    majorCount = 1;
                }
            }else {
                majorCount++;
            }
        }
        return major;
    }

    public static void main(String[] args) {
        P169MajorityElementOptimizedSolution solution = new P169MajorityElementOptimizedSolution();
        solution.majorityElement(new int[]{3,2,3});

    }

    /*這就是 Boyer-Moore 的精髓：不確定沒關係，先假設第一個就是 majority。

  規則很簡單：
  - count 歸零時 → 換人當 candidate
  - 遇到跟 candidate 一樣的 → count++
  - 遇到不一樣的 → count--

  因為真正的 majority 出現超過一半，被抵消掉之後一定還會剩下來。就算一開始猜錯，count 會降到 0，然後自動換成對的。

  試著把這個邏輯寫進你的 for 迴圈裡看看？

❯ P169MajorityElementOptimizedSolution
  這樣寫對嗎 把你的寫法寫在
  P169MajorityElementOptimizedSolutionClaude
  ⎿  ⧉ Selected 1 lines from src/leetcode2026/p169majorityelement/P169MajorityElementOptimizedSolution.java in IntelliJ IDEA

⏺ Read 1 file (ctrl+o to expand)

⏺ 你的思路對了，但有一個問題：count 歸零時你做了 i++ 跳到下一個當新 candidate，但這會跳過一個元素。其實 count 歸零的那個元素自己就可以當新 candidate，不需要額外 i++。

⏺ Write(src/leetcode2026/p169majorityelement/P169MajorityElementOptimizedSolutionClaude.java)
  ⎿  Wrote 27 lines to src/leetcode2026/p169majorityelement/P169MajorityElementOptimizedSolutionClaude.java
     package leetcode2026.p169majorityelement;

     public class P169MajorityElementOptimizedSolutionClaude {

         // Boyer-Moore Voting Algorithm, Time: O(n), Space: O(1)
         public int majorityElement(int[] nums) {
             int candidate = nums[0];
             int count = 1;

             for (int i = 1; i < nums.length; i++) {
     … +17 lines (ctrl+o to expand)

⏺ 關鍵差異在這：

  你的版本：count 歸零 → i++ 跳到下一個當 candidate（會跳過一個元素）
  Claude 版：count 歸零 → 當前這個 nums[i] 直接當新 candidate

  你的版本裡，先 count-- 到 0，再 i++ 跳一格，等於把讓 count 歸零的那個元素和下一個元素之間跳過了一個。正確的做法是先檢查 count == 0，再決定要 ++ 還是 --。


⏺ Boyer-Moore Voting 最直接的用途是在資料流中找出多數元素，只需要掃一遍、不用額外空間。

  實際場景：
  - 網路流量分析 — 即時找出佔流量超過一半的 IP（偵測 DDoS）
  - 分散式系統 — 多個節點投票決定 leader，找出多數共識
  - 資料庫查詢優化 — 快速判斷某個值是否佔資料的多數，決定要不要用 index

  核心價值是：在記憶體有限、資料量大到無法存下來的情況下，O(1) 空間就能找出答案。


*/
}