package leetcode2026.p21mergetwosortedlists;

import leetcode2026.common.ListNode;

public class P21MergeTwoSortedListsNaiveSolution {

    // Iterative 解法 — 用 dummy node (sentinel node) + 雙指針
    // Time: O(n + m), Space: O(1)
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode result = dummy;

        while(list1 != null && list2 != null){
            if (list1.val <= list2.val){
                dummy.next = list1;
                list1 = list1.next;
            }else {
                dummy.next = list2;
                list2 = list2.next;
            }
            dummy = dummy.next;
        }
        if (list1 == null){
            dummy.next = list2;
        }else {
            dummy.next = list1;
        }
        return result.next;
    }

    public static void main(String[] args) {
        P21MergeTwoSortedListsNaiveSolution solution = new P21MergeTwoSortedListsNaiveSolution();

        // list1 = [1,2,4]
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(4);

        // list2 = [1,3,4]
        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);

        // Expected: 1 -> 1 -> 2 -> 3 -> 4 -> 4
        ListNode result = solution.mergeTwoLists(list1, list2);
        while (result != null) {
            System.out.print(result.val + " -> ");
            result = result.next;
        }
        System.out.println("null");
    }

    /*
    思路：
    - 不是建一條全新的 list，而是把 list1 和 list2 現有的 node 重新串起來
    - 每次比較兩邊的頭，小的接到 current.next，然後那邊往前走一步
    - 某一邊走完後，把另一邊剩下的直接接上去

    為什麼需要 dummy node (sentinel node)？
    - 一開始合併 list 是空的，沒有東西可以 .next =，需要一個假的起點避免特判第一個 node
    - dummy 的 val (0) 沒有意義，純粹佔位

    為什麼需要 result = dummy？
    - dummy 會一直往前走 (dummy = dummy.next)，走到最後它指向尾巴，頭就丟了
    - result 記住 dummy 的初始位置，最後 return result.next 就能拿到完整結果

    過程示意：
    list1: 1 → 2 → 4
    list2: 1 → 3 → 4

    比較 1 vs 1 → 拿 list1(1)，list1 往前走
    比較 2 vs 1 → 拿 list2(1)，list2 往前走
    比較 2 vs 3 → 拿 list1(2)，list1 往前走
    比較 4 vs 3 → 拿 list2(3)，list2 往前走
    比較 4 vs 4 → 拿 list1(4)，list1 走完 → 把 list2 剩下的 (4) 接上
    結果: 1 → 1 → 2 → 3 → 4 → 4
    */

    //  第 14 行 < 應改為 <=
    //
    //  if (list1.val < list2.val)  // 現在：值相等時走 else (list2)
    //  if (list1.val <= list2.val) // 建議：值相等時走 list1，保持穩定性
    //
    //  功能上都能 AC，但用 <= 是比較好的習慣 — 相等時固定從 list1 拿，行為更可預期（stable merge）。
}