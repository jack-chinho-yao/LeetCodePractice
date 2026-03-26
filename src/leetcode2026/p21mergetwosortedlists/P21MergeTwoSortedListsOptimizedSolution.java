package leetcode2026.p21mergetwosortedlists;

import leetcode2026.common.ListNode;

public class P21MergeTwoSortedListsOptimizedSolution {

    // Recursive 解法 — 每次挑小的那個，讓它的 next 去遞迴處理剩下的
    // Time: O(n + m), Space: O(n + m) call stack
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;

        if (list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }

    public static void main(String[] args) {
        P21MergeTwoSortedListsOptimizedSolution solution = new P21MergeTwoSortedListsOptimizedSolution();

        // list1 = [1,2,4]
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(4);

        // list2 = [1,3,4]
        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);

        // Expected: 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> null
        ListNode result = solution.mergeTwoLists(list1, list2);
        while (result != null) {
            System.out.print(result.val + " -> ");
            result = result.next;
        }
        System.out.println("null");
    }

    /*
    思路：
    - 比較 list1 和 list2 的頭，小的那個就是當前結果的頭
    - 讓它的 .next 指向「剩下部分的合併結果」（遞迴）
    - base case：某一邊是 null，直接回傳另一邊

    為什麼不會丟失尾巴？
    - Iterative 的 dummy 會往前走所以頭會丟，需要 result 記住
    - Recursive 不同：每一層都 return 自己，call stack 自動從底部往回彈，把整條串回來

    遞迴展開示意：
    mergeTwoLists(1→2→4, 1→3→4)
      1 <= 1 → list1(1).next = mergeTwoLists(2→4, 1→3→4)
        1 < 2 → list2(1).next = mergeTwoLists(2→4, 3→4)
          2 < 3 → list1(2).next = mergeTwoLists(4, 3→4)
            3 < 4 → list2(3).next = mergeTwoLists(4, 4)
              4 <= 4 → list1(4).next = mergeTwoLists(null, 4)
                list1 == null → return list2(4)

    回彈：4→4 → 3→4→4 → 2→3→4→4 → 1→2→3→4→4 → 1→1→2→3→4→4

    缺點：
    - 每次遞迴呼叫都會在 call stack 上疊一層，佔記憶體 O(n + m)
    - list 超長時可能會 StackOverflowError
    - Iterative 只用固定幾個變數 O(1)，不會有這個問題

    兩種解法比較：
    | Iterative | O(1) space，不怕 list 超長，但程式碼較多 |
    | Recursive | 程式碼簡潔，但吃 call stack 空間          |
    */
}