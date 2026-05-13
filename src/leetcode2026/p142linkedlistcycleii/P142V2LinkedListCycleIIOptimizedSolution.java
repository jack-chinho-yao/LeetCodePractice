package leetcode2026.p142linkedlistcycleii;

import leetcode2026.common.ListNode;

public class P142V2LinkedListCycleIIOptimizedSolution {

    // Floyd's Tortoise and Hare | Time: O(n) | Space: O(1)
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null){
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next ;

            if (slow != null && slow ==fast ){

                while (true){
                    if (slow == head){
                        return slow;
                    }
                    slow = slow.next;
                    head = head.next;

                }
            }

        }
        return null;
    }

    public static void main(String[] args) {
        P142V2LinkedListCycleIIOptimizedSolution solution = new P142V2LinkedListCycleIIOptimizedSolution();

        // Example 1: head = [3,2,0,-4], pos = 1 -> return node with val 2
//        ListNode head1 = new ListNode(3);
//        head1.next = new ListNode(2);
//        head1.next.next = new ListNode(0);
//        head1.next.next.next = new ListNode(-4);
//        head1.next.next.next.next = head1.next; // cycle at index 1
//        ListNode result1 = solution.detectCycle(head1);
//        System.out.println("Example 1: " + (result1 != null ? result1.val : "null")); // Expected: 2
//
//        // Example 2: head = [1,2], pos = 0 -> return node with val 1
//        ListNode head2 = new ListNode(1);
//        head2.next = new ListNode(2);
//        head2.next.next = head2; // cycle at index 0
//        ListNode result2 = solution.detectCycle(head2);
//        System.out.println("Example 2: " + (result2 != null ? result2.val : "null")); // Expected: 1
//
//        // Example 3: head = [1], pos = -1 -> return null
//        ListNode head3 = new ListNode(1);
//        ListNode result3 = solution.detectCycle(head3);
//        System.out.println("Example 3: " + (result3 != null ? result3.val : "null")); // Expected: null

        // Example 4: head = [3,5,2,0,-4], pos = 2 -> return node with val 2
        ListNode head4 = new ListNode(3);
        head4.next = new ListNode(5);
        head4.next.next = new ListNode(2);
        head4.next.next.next = new ListNode(0);
        head4.next.next.next.next = new ListNode(-4);
        head4.next.next.next.next.next = head4.next.next; // cycle at index 2
        ListNode result4 = solution.detectCycle(head4);
        System.out.println("Example 4: " + (result4 != null ? result4.val : "null")); // Expected: 2
    }
}

/*
 * Floyd's Tortoise and Hare — Why it works (math proof)
 * Floyd 龜兔演算法 — 數學證明
 *
 * Define / 定義：
 *   F = distance from head to cycle entrance (head 到入口的距離)
 *   C = cycle length (環的長度)
 *   a = distance from cycle entrance to meeting point (入口到相遇點的距離)
 *
 *   head ----F---- entrance ----a---- meeting point
 *                     |                     |
 *                     +------(C - a)--------+
 *
 * Phase 1: Find meeting point / 第一階段：找相遇點
 *   - slow travels: F + a
 *     slow 走了 F + a 步
 *   - fast travels: F + a + nC  (n = number of extra laps fast made in the cycle)
 *     fast 走了 F + a + nC 步（n = fast 在環裡多繞的圈數）
 *   - fast moves 2x speed of slow, so:
 *     fast 速度是 slow 的兩倍：
 *         2(F + a) = F + a + nC
 *         F + a = nC
 *         F = nC - a
 *
 * Phase 2: Find entrance / 第二階段：找入口
 *   - F = nC - a, rewrite as: F = (C - a) + (n-1)C
 *     F = nC - a，改寫成：F = (C - a) + (n-1)C
 *
 *   - From head: walk F steps → arrive at entrance (by definition, F is the distance)
 *     從 head 走 F 步 → 到入口（F 本來就是 head 到入口的距離）
 *
 *   - From meeting point: walk F steps = walk (C-a) + (n-1)C steps, which means:
 *     從相遇點走 F 步 = 走 (C-a) + (n-1)C 步，也就是：
 *       1. walk (C - a) steps → back to entrance (meeting point is distance a past entrance, so C-a to complete the loop)
 *          先走 C-a 步 → 回到入口（相遇點離入口剩 C-a 的距離）
 *       2. walk (n-1)C steps → n-1 full laps, still at entrance
 *          再走 (n-1)C 步 → 繞 n-1 整圈，還是在入口
 *
 *   - Both pointers arrive at entrance after exactly F steps → they meet at entrance
 *     兩個指針走 F 步後都剛好在入口 → 一定在入口碰面
 *
 * Example / 範例：[3, 5, 2, 0, -4], cycle at index 2
 *   F=2, C=3, a=1
 *   Phase 1: slow=F+a=3 steps, fast=F+a+C=6 steps, meet at node 0
 *   Phase 2: head(3)→5→2, meeting(0)→-4→2 → both arrive at entrance node 2 ✓
 */
