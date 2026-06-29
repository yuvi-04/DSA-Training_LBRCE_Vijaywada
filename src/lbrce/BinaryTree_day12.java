package lbrce;

import java.util.ArrayDeque;
import java.util.Deque;

public class BinaryTree_day12 {
    private TreeNode root;

    static class TreeNode {
        int data;
        TreeNode left, right;
        TreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public BinaryTree_day12() {
        this.root = null;
    }
    public void insert(int data) {
        root  = insertRec(root, data);
    }
    private TreeNode insertRec(TreeNode root, int data) {
        if(root == null)
            return new TreeNode(data);
        if(data < root.data)
            root.left = insertRec(root.left, data);
        else if(data > root.data)
            root.right = insertRec(root.right, data);
        return root;
    }

    //Q1:  Level-order traversal (BFS)
    //leetcode 102
    public void Bfs() {
        if(root == null)
            return;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for(int i = 0; i < levelSize; i++) {
                TreeNode curr = queue.poll();
                System.out.print(curr.data + " ");
                if(curr.left != null) queue.offer(curr.left);
                if(curr.right != null) queue.offer(curr.right);
            }
            System.out.println();
        }
    }

    //Q2: (maximum depth) of a binary tree.
    //leetcode 104
    public int getHeight() {
        return calculateHeight(this.root);
    }
    private int calculateHeight(TreeNode root) {
        if(root == null) return 0;
        return 1 + Math.max(calculateHeight(root.left), calculateHeight(root.right));
    }

    //Q3: Check if a binary tree is a valid BST
    //leetcode 98
    public boolean isValidBst() {
        return validateBst(this.root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private boolean validateBst(TreeNode root, long minValue, long maxValue) {
        if(root == null) return true;
        if(root.data <= minValue || root.data > maxValue) return false;
        return validateBst(root.left, minValue, root.data) &&
            validateBst(root.right, root.data, maxValue);
    }

    //Q5: mirror images (symmetric)
    //leetcode 101
    public static boolean isMirror(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) return true;
        if(t1 == null || t2 == null) return false;

        return (t1.data == t2.data) &&
            isMirror(t1.left, t2.right) &&
            isMirror(t1.right, t2.left);
    }
    public static void main(String[] args) {
        BinaryTree_day12 tree = new BinaryTree_day12();
        tree.insert(15);
        tree.insert(8);
        tree.insert(25);
        tree.insert(4);
        tree.insert(12);
        tree.insert(20);
        tree.insert(30);
        tree.insert(22);
        tree.insert(14);
        tree.insert(2);
        tree.insert(1);

        //Q1
        // tree.Bfs();

        // Q2
    //    System.out.println(tree.getHeight());

       //Q3
    //    System.out.println(tree.isValidBst());

       //Q5
       TreeNode tree1 = new TreeNode(5);
       tree1.left = new TreeNode(25);
       tree1.right = new TreeNode(30);

       TreeNode tree2 = new TreeNode(5);
       tree2.left = new TreeNode(30);
       tree2.right = new TreeNode(25);

       System.out.println(isMirror(tree1, tree2));
    }
}
