package leetcode2026.p153findminimuminrotatedsortedarray;

// Binary search on the rotation point. Time O(log n), Space O(1).
public class P153V2FindMinimumInRotatedSortedArrayOptimizedSolution {

    public int findMin(int[] nums) {
        // TODO
        return -1;
    }

    public static void main(String[] args) {
        P153V2FindMinimumInRotatedSortedArrayOptimizedSolution solution =
                new P153V2FindMinimumInRotatedSortedArrayOptimizedSolution();
        System.out.println(solution.findMin(new int[]{3, 4, 5, 1, 2}));        // 1
        System.out.println(solution.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));  // 0
        System.out.println(solution.findMin(new int[]{11, 13, 15, 17}));       // 11
    }

    /*
    === Guiding questions ===

    這題的難點：沒有 target，你到底在二分「什麼」？先突破這個心魔。

    1. 畫一下旋轉後的形狀：一條上升線，中間斷一次掉到最低點，再上升。
       最小值就是那個「斷點」。二分的目標，是逼近這個斷點的位置。

    2. 每一輪你手上有 nums[mid]。要決定往左半還是右半收縮，你需要一個「參照點」
       來判斷 mid 落在斷點的左段還是右段。試試拿 nums[mid] 跟 nums[hi]（右端）比：
         - nums[mid] > nums[hi] 代表 mid 還在「高的那段」，斷點（最小值）在 mid 的哪一側？
         - nums[mid] < nums[hi] 代表 mid 已經在「低的那段」，最小值在哪一側？（mid 本身可能就是答案，別急著跳過它）

    3. 為什麼建議跟 nums[hi] 比，而不是 nums[lo]？
       拿 [1,2,3,4,5]（沒真的旋轉）跟 [3,4,5,1,2] 各跑一次，你會發現跟 lo 比會有討厭的歧義。

    4. 收斂條件：這題適合 while (lo < hi)，結束時 lo == hi 剛好停在答案上。
       想清楚為什麼「移動 hi 時要 hi = mid（不是 mid-1），移動 lo 時要 lo = mid+1」。

    自測：[11,13,15,17] 完全沒斷點的情況，你的邏輯會不會正確回傳 11？
    */
}
