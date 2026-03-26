package leetcode2026.p206reverselinkedlist;

import leetcode2026.common.ListNode;

public class P206V1ReverseLinkedListNaiveSolution {

    public ListNode reverseList(ListNode head) {
        ListNode temp = new ListNode(0);
        ListNode result = head;
        while(head != null){
            temp = head.next;
            head.next = result;
            head = head.next;
        }
        return null;
    }

    public static void main(String[] args) {
        P206V1ReverseLinkedListNaiveSolution solution = new P206V1ReverseLinkedListNaiveSolution();

        // Example 1: [1,2,3,4,5] -> [5,4,3,2,1]
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        printList(solution.reverseList(head1));

        // Example 2: [1,2] -> [2,1]
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        printList(solution.reverseList(head2));

        // Example 3: [] -> []
        printList(solution.reverseList(null));
    }

    private static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) sb.append(",");
            curr = curr.next;
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
}
