package lbrce;

public class RandomLinkedList {
    private RandomNode head;

    private static class RandomNode {
        int val;
        RandomNode next, random;
        RandomNode(int val) { this.val = val; }
    }

    public RandomLinkedList() { this.head = null; }

    public void insert(int val) {
        RandomNode newNode = new RandomNode(val);

        if(this.head == null) {
            this.head = newNode;
            return;
        }
        RandomNode curr = this.head;
        while(curr.next != null)
            curr = curr.next;
        curr.next = newNode;
    }

    public void setRandom(int fromIdx, int toIdx) {
        RandomNode fromNode = getNodeAt(fromIdx);
        RandomNode toNode = getNodeAt(toIdx);

        if(fromNode != null)
            fromNode.random = toNode;
    }

    private RandomNode getNodeAt(int index) {
        RandomNode curr = this.head;
        int count = 0;
        while (curr != null && count < index) {
            curr = curr.next;
            count++;
        }
        return curr;
    }

    public void printList() {
        if(this.head == null) return;

        RandomNode temp = this.head;
        while (temp != null) {
            int randVal = (temp.random != null) ?
                temp.random.val : -1;
            System.out.print("[" + temp.val + ", R: " + randVal + "] -> ");
            temp = temp.next;
        }
        System.out.println("NULL");
    }

    //Q9: Clone a linked list with random pointers
    //leetcode 138
    public RandomLinkedList cloneList() {
        if(this.head == null)
            throw new RuntimeException("List is NULL");

        // Step1: the weave
        RandomNode curr = this.head;
        while (curr != null) {
            RandomNode nextOrignal = curr.next;
            RandomNode cloneNode = new RandomNode(curr.val);

            curr.next = cloneNode;
            cloneNode.next = nextOrignal;

            curr = nextOrignal;
        }

        //Step2: Assign Random Pointers
        curr = this.head;
        while (curr != null) {
            if(curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        //Step3: Seperate Orignal and Clones
        curr = this.head;
        RandomNode dummyClone = new RandomNode(0);
        RandomNode cloneCurr = dummyClone;

        while (curr != null) {
            RandomNode nextOrignal = curr.next.next;

            //extract the clone into the sequence
            cloneCurr.next = curr.next;
            cloneCurr = cloneCurr.next;

            //restore orignal list pointer
            curr.next = nextOrignal;
            curr = nextOrignal;
        }
        RandomLinkedList clonedList = new RandomLinkedList();
        clonedList.head = dummyClone.next;

        return clonedList;
    }

    public static void main(String[] args) {
        RandomLinkedList list = new RandomLinkedList();

        list.insert(1);
        list.insert(2);
        list.insert(3);

        list.setRandom(0, 2);
        list.setRandom(1, 0);

        list.printList();

        RandomLinkedList clonedList = list.cloneList();
        clonedList.printList();
    }
}
