package lbrce;

public class Linkedlist_day9 {
    private ListNode head;

    Linkedlist_day9() {
        this.head = null;
    }

    //Singly Linked List
    static class ListNode {
        int data;
        ListNode next;
        ListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public void printList() {
        ListNode curr = head;
        while(curr != null) {
            System.out.print(curr.data + " -> ");
            curr = curr.next;
        }
        System.out.println("NULL");
    }

    public void insert(int data) {
        ListNode newNode = new ListNode(data);

        if(this.head == null) {
            this.head = newNode;
            return;
        }
        ListNode curr = this.head;
        while(curr.next != null)
            curr = curr.next;
        curr.next = newNode;
    }

    public void insertWithCycle(int data, int pos) {
        ListNode newNode = new ListNode(data);

        if(this.head == null) {
            this.head = newNode;
            if(pos == 0)
                newNode.next = newNode;
            else System.out.println("List was empty");
        }

        ListNode curr = this.head;
        ListNode cycleTarget = null;
        int currentIndex = 0;

        while(curr.next != null) {
            if(currentIndex == pos) {
                cycleTarget = curr;
            }
            curr = curr.next;
            currentIndex++;
        }

        if(currentIndex == pos)
            cycleTarget = curr;
        curr.next = newNode;

        if(cycleTarget != null)
            newNode.next = cycleTarget;
        else System.out.println("Index is out of Bounds");
    }

    //Q1: reverse a Linked List
    //leetcode 206
    public void reverseList() {
        ListNode prev = null;
        ListNode curr = this.head;

        while(curr != null) {
            ListNode nextNode=curr.next;
            curr.next=prev;
            prev=curr;
            curr=nextNode;
        }
        this.head = prev;
    }

    //Q2: Find Middle
    //leetcode 876
    public Integer getMiddleValue() {
        if(this.head == null)
            return null;
        ListNode slow = this.head;
        ListNode fast = this.head;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    //Q3:  Floyd's Cycle Detection
    //leetcode 141
    public boolean hasCycle() {
        if(this.head == null) return false;

        ListNode slow = this.head;
        ListNode fast = this.head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast)
                return true;
        }
        return false;
    }

    //Q4: Node where cycle begins in linked List
    //leetcode 142
    public Integer getCycleBeginValue() {
        if(this.head == null) return null;

        ListNode slow = this.head;
        ListNode fast = this.head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) {
                //Find entry point for cycle
                ListNode entry = this.head;
                while(entry != slow) {
                    entry = entry.next;
                    slow = slow.next;
                }
                return entry.data;
            }
        }
        return null; // if no cycle is there, return null
    }

    //Q5: Merge two sorted linked list
    //leetcode 21
    public void merge(Linkedlist_day9 otherList) {
        if(otherList == null) return;
        if(this == otherList) return;
        
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;

        ListNode l1 = this.head;
        ListNode l2 = otherList.head;

        while(l1 != null && l2 != null) {
            if(l1.data <= l2.data) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        current.next = (l1 != null) ? l1 : l2;
        this.head = dummy.next;
        otherList.head = null;
    }

    //Q6: Remove Nth Node from end
    //leetcode 19
    public void removeNthfromEnd(int n) {
        if(this.head == null || n <= 0) return;

        ListNode dummy = new ListNode(0);
        dummy.next = this.head;
        ListNode slow = dummy;
        ListNode fast = dummy;

        //Move fast pointer to create a gap between slow and fast
        //for n nodes
        for(int i = 0; i <= n; i++) {
            //If n is larger than the list itself
            if(fast == null)
                return;
            fast = fast.next;
        }
        while(fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        if(slow != null)
            slow.next = slow.next.next;
        this.head = dummy.next;
    }

    //Q7: Reverse nodes in groups of K
    //leetcode 25
    public void reverseKgroup(int k) {
        if(this.head == null || k <= 1) return;

        ListNode dummy = new ListNode(0);
        dummy.next = this.head;
        ListNode prevGroupTail = dummy;
        ListNode curr = this.head;

        while (curr != null) {
            ListNode groupEnd = curr;
            //Verify if there are atleast K nodes left
            for(int i = 1; i < k && groupEnd != null; i++) {
                groupEnd = groupEnd.next;
            }
            //if remaining is lower than K
            //leave it unchanged
            if(groupEnd == null) break;
            ListNode nextGroupStart = groupEnd.next;
            groupEnd.next = null;

            prevGroupTail.next = reverseSegment(curr);
            curr.next = nextGroupStart;

            prevGroupTail = curr;
            curr = nextGroupStart;
        }
        this.head = dummy.next;
    }

    private ListNode reverseSegment(ListNode node) {
        ListNode prev = null;
        ListNode curr = node;
        while(curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }
    public static void main(String[] args) {
        Linkedlist_day9 list = new Linkedlist_day9();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.insert(4);
        list.insert(5);
        // list.insertWithCycle(4, 2);
        //never print a list with cycle

        Linkedlist_day9 list2 = new Linkedlist_day9();
        list2.insert(2);
        list2.insert(4);
        list2.insert(6);
        list2.insert(8);
        list2.insert(10);

        //Q1
        // list.reverseList();
        // list.printList();

        //Q2
        // System.out.println(list.getMiddleValue());

        //Q3
        // System.out.println(list.hasCycle());

        //Q4
        // System.out.println(list.getCycleBeginValue());

        //Q5
        // list.merge(list2);
        // list.printList();

        //Q6
        // list2.printList();
        // list2.removeNthfromEnd(4);
        // list2.printList();

        //Q7
        list.reverseKgroup(2);
        list.printList();
    }
}
