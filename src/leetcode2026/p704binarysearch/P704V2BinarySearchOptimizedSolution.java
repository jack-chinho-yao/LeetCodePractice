package leetcode2026.p704binarysearch;

// Binary search. Time O(log n), Space O(1).
public class P704V2BinarySearchOptimizedSolution {

    public int search(int[] nums, int target) {
        // TODO
        int right =  nums.length;
        int mid = nums.length / 2;
        int left = 0;
        while(right != left){
            if (nums[mid] == target){
                return mid;
            }else if (nums[mid] > target){
                right = mid;
            }else if (nums[mid] < target){
                left = mid+1;
            }else{
                System.out.println("unpredicted error");
            }
            mid = (left + right) / 2;
        }
        return -1;
    }

    public static void main(String[] args) {
        P704V2BinarySearchOptimizedSolution solution = new P704V2BinarySearchOptimizedSolution();
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));  // 4
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));  // -1
        System.out.println(solution.search(new int[]{5}, 5));                   // 0
    }

    /*
    === Guiding questions (先別看 V1，自己想過再寫) ===

    這是所有二分的「模板題」，把它寫到肌肉記憶，後面 5 題都是它的變形。

    1. 你要維護哪兩個邊界變數？初始值要設 [0, n-1] 還是 [0, n]？
       這個選擇會連動到下面第 3 點的 while 條件，兩者要一致。

    2. mid 為什麼建議寫 lo + (hi - lo) / 2，而不是 (lo + hi) / 2？
       （提示：想想 lo, hi 都很大時會發生什麼事。）

    3. while 迴圈條件用 lo < hi 還是 lo <= hi？
       這決定了「迴圈結束時 lo 和 hi 停在哪」。先決定你要哪種結束狀態，再回頭挑條件。

    4. 每一輪比較 nums[mid] 跟 target 之後，你把 lo 或 hi 移到 mid、mid+1、還是 mid-1？
       關鍵問自己：「mid 這一格，這一輪確定可以排除了嗎？」——排除了才能跳過它。

    5. 邊界自測：用 nums=[5], target=5（只有一個元素）和 target=6（找不到）
       手動跑一次你的迴圈，會不會無限迴圈？會不會 index 越界？

    寫完後跟 V1 比對：同樣的輸入，兩者輸出要一模一樣，但你的迴圈次數應該遠少於 V1。
    */
}
