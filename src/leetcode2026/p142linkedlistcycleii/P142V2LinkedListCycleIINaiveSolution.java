package leetcode2026.p142linkedlistcycleii;

import leetcode2026.common.ListNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class P142V2LinkedListCycleIINaiveSolution {

    // HashSet approach | Time: O(n) | Space: O(n)
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while(head != null){
            if (set.contains(head)){
                return head;
            }
            set.add(head);
            head = head.next;
        }
        return null;
    }

    public static void main(String[] args) {
        P142V2LinkedListCycleIINaiveSolution solution = new P142V2LinkedListCycleIINaiveSolution();

        // Example 1: head = [3,2,0,-4], pos = 1 -> return node with val 2
        ListNode head1 = new ListNode(3);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(0);
        head1.next.next.next = new ListNode(-4);
        head1.next.next.next.next = head1.next; // cycle at index 1
        ListNode result1 = solution.detectCycle(head1);
        System.out.println("Example 1: " + (result1 != null ? result1.val : "null")); // Expected: 2

        // Example 2: head = [1,2], pos = 0 -> return node with val 1
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = head2; // cycle at index 0
        ListNode result2 = solution.detectCycle(head2);
        System.out.println("Example 2: " + (result2 != null ? result2.val : "null")); // Expected: 1

        // Example 3: head = [1], pos = -1 -> return null
        ListNode head3 = new ListNode(1);
        ListNode result3 = solution.detectCycle(head3);
        System.out.println("Example 3: " + (result3 != null ? result3.val : "null")); // Expected: null
    }
}
