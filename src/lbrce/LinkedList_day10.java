package lbrce;

public class LinkedList_day10 {
    private ListNode head;

    LinkedList_day10() {
        this.head = null;
    }

    //Singly Linked List
    private static class ListNode {
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

    // only use for circular Linked List
    public void printCircularList() {
        if(this.head == null) {
            System.out.println("Empty List");
            return;
        }
        ListNode temp = this.head;
        do {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        } while(temp != this.head);
        System.out.println("Head: " + this.head.data);
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

    //only use for circular Linked List
    public void insertCircular(int data) {
        ListNode newNode = new ListNode(data);
        if(this.head == null) {
            this.head = newNode;
            this.head.next = this.head;
            return;
        }
        ListNode tail = this.head;
        while(tail.next != this.head) {
            tail = tail.next;
        }
        tail.next = newNode;
        newNode.next = this.head;
    }

    //Q1:  Add two numbers represented as linked lists
    //leetcode 2, 445
    public LinkedList_day10 add(LinkedList_day10 otherList) {
        LinkedList_day10 result = new LinkedList_day10();

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;

        ListNode l1 = this.head;
        ListNode l2 = otherList.head;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if(l1 != null) {
                sum += l1.data;
                l1 = l1.next;
            }
            if(l2 != null) {
                sum += l2.data;
                l2 = l2.next;
            }
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
        }
        result.head = dummy.next;
        return result;
    }

    //Q2:  Remove all nodes with a given value
    //leetcode 203
    public void removeElement(int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = this.head;
        ListNode curr = dummy;

        while (curr.next != null) {
            if(curr.next.data == val)
                curr.next = curr.next.next;
            else curr = curr.next;
        }
        this.head = dummy.next;
    }

    //Q3: Swap nodes in pairs in a linked list
    //leetcode 24
    public void swapPairs() {
        if(this.head == null || this.head.next == null)
            return;

        ListNode dummy = new ListNode(0);
        dummy.next = this.head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;

            first.next = second.next;
            second.next = first;
            prev.next = second;
            prev = first;
        }
        this.head = dummy.next;
    }

    //Q4: Rotate a linked list to the right by K places.
    //leetcode 61
    public void rotateRight(int k) {
        //Edge Cases
        if(this.head == null || this.head.next == null || k <= 0)
            return;

        //Step1: Find tail and calculate length
        ListNode tail = this.head;
        int len = 1;
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }
        //Step2: Optimize k to avoid useless full rotations
        k = k % len;
        if(k == 0) return;

        //Step3: Form a circular Linked List
        // Tail.next -> head
        tail.next = this.head;

        //Step4: Find the new tail element
        int stepsToNewTail = len - k;
        ListNode newTail = this.head;

        //Step5: Lop runs for stepsToNewtail -1 times
        for(int i = 1; i < stepsToNewTail; i++)
            newTail = newTail.next;

        this.head = newTail.next;
        newTail.next = null;
    }

    //Q5: Reorder list L0→Ln→L1→Ln-1→L2→Ln-2
    //leetcode 143
    public void reorder() {
        //Edge case
        if(this.head == null || this.head.next == null)
            return;

        //Step1: find middle
        ListNode slow = this.head;
        ListNode fast = this.head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        } //Your slow reference is you middle

        //Step2: disconnect the first half
        // reverse the second half
        ListNode prev = null;
        ListNode curr = slow.next;
        slow.next = null;

        while(curr != null) {
            ListNode nextNode=curr.next;
            curr.next=prev;
            prev=curr;
            curr=nextNode;
        }
        //Step3: L1 & L2
        // the Zipper Merge
        ListNode first = this.head; //head of 1st half
        ListNode second = prev; //head of 2nd half

        while (second != null) {
            //Save the next node before overwriting
            ListNode t1 = first.next;
            ListNode t2 = second.next;

            //zip the pointers together
            first.next = second;
            second.next = t1;

            //advance the heads for next iteration
            first = t1;
            second = t2;
        }
    }

    //Q6: Split a circular linked list into two equal halves.
    //GFG
    public LinkedList_day10[] split() {
        //Edge case
        if(this.head == null)
            return new LinkedList_day10[]{new LinkedList_day10(), new LinkedList_day10()};

        // Step1: find Middle
        ListNode slow = this.head;
        ListNode fast = this.head;
        while (fast.next != this.head && fast.next.next != this.head) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if(fast.next.next == this.head)
            fast = fast.next;

        //Step2
        ListNode head1= this.head;
        ListNode head2 = slow.next;

        fast.next = slow.next;
        slow.next = this.head;

        LinkedList_day10 firstHalf = new LinkedList_day10();
        LinkedList_day10 secondhalf = new LinkedList_day10();

        firstHalf.head = head1;
        secondhalf.head = head2;

        return new LinkedList_day10[]{firstHalf, secondhalf};
    }
    public static void main(String[] args) {
        LinkedList_day10 list1 = new LinkedList_day10();
        list1.insert(1);
        list1.insert(2);
        list1.insert(3);
        list1.insert(4);
        list1.insert(5);
        list1.insert(6);

        LinkedList_day10 list2 = new LinkedList_day10();
        list2.insert(5);
        list2.insert(6);
        list2.insert(6);
        list2.insert(6);
        list2.insert(6);
        list2.insert(4);
        list2.insert(6);

        LinkedList_day10 circularList = new LinkedList_day10();
        circularList.insertCircular(1);
        circularList.insertCircular(2);
        circularList.insertCircular(3);
        circularList.insertCircular(4);

        //Q1
        // LinkedList_day10 ss);

        //Q2
        // list2.removeElement(6);
        // list2.printList();

        //Q3
        // list1.printList();
        // list1.swapPairs();
        // list1.printList();

        //Q4
        // list1.rotateRight(2);
        // list1.printList();

        //Q5
        // list1.reorder();
        // list1.printList();

        //Q6
        LinkedList_day10[] halves = circularList.split();
        halves[0].printCircularList();
        halves[1].printCircularList();
    }
}
