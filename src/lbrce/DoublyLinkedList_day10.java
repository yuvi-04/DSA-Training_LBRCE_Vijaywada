package lbrce;

public class DoublyLinkedList_day10 {
    private DLLNode head;

    private static class DLLNode {
        int val;
        DLLNode prev;
        DLLNode next;
        DLLNode(int val) {this.val = val; }
    }

    public DoublyLinkedList_day10() {
        this.head = null;
    }

    public void insert(int data) {
        DLLNode newNode = new DLLNode(data);
        if(this.head == null) {
            this.head = newNode;
            return;
        }
        DLLNode curr = this.head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = newNode;
        newNode.prev = curr;
    }

    public void printList() {
        if(this.head == null) return;

        System.out.print("Forward: ");
        DLLNode curr = this.head;
        DLLNode tailTracker = null;
        while (curr != null) {
            System.out.print(curr.val + " <--> ");
            tailTracker = curr;
            curr = curr.next;
        }
        System.out.println("NULL");

        System.out.print("Backward: ");
        curr = tailTracker;
        while (curr != null) {
            System.out.print(curr.val + " <--> ");
            curr = curr.prev;
        }
        System.out.println("NULL");
    }

    //Q7: Revove Duplicates for DLL
    public void removeDuplicates() {
        if(this.head == null) return;
        DLLNode curr = this.head;

        while (curr.next != null) {
            if(curr.val == curr.next.val) {
                DLLNode duplicate = curr.next;
                curr.next = duplicate.next;
                if(duplicate.next != null)
                    duplicate.next.prev = curr;
            }
            else curr = curr.next;
        }
    }
    public static void main(String[] args) {
        DoublyLinkedList_day10 list = new DoublyLinkedList_day10();
        list.insert(1);
        list.insert(1);
        list.insert(3);
        list.insert(4);
        list.insert(5);
        list.insert(5);
        list.insert(5);

        //Q7
        list.removeDuplicates();
        list.printList();
    }
}
