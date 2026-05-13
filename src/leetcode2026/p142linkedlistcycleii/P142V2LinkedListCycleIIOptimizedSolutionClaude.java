package leetcode2026.p142linkedlistcycleii;

import leetcode2026.common.ListNode;

public class P142V2LinkedListCycleIIOptimizedSolutionClaude {

    // Floyd's Tortoise and Hare | Time: O(n) | Space: O(1)
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        P142V2LinkedListCycleIIOptimizedSolutionClaude solution = new P142V2LinkedListCycleIIOptimizedSolutionClaude();

        // Example 1: head = [3,2,0,-4], pos = 1 -> return node with val 2
        ListNode head1 = new ListNode(3);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(0);
        head1.next.next.next = new ListNode(-4);
        head1.next.next.next.next = head1.next;
        ListNode result1 = solution.detectCycle(head1);
        System.out.println("Example 1: " + (result1 != null ? result1.val : "null")); // Expected: 2

        // Example 2: head = [1,2], pos = 0 -> return node with val 1
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = head2;
        ListNode result2 = solution.detectCycle(head2);
        System.out.println("Example 2: " + (result2 != null ? result2.val : "null")); // Expected: 1

        // Example 3: head = [1], pos = -1 -> return null
        ListNode head3 = new ListNode(1);
        ListNode result3 = solution.detectCycle(head3);
        System.out.println("Example 3: " + (result3 != null ? result3.val : "null")); // Expected: null
    }
}
