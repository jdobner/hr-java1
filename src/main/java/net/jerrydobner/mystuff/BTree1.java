package net.jerrydobner.mystuff;

public class BTree1 {

    /*
     * Java Program to traverse a binary tree using PreOrder traversal.
     * In PreOrder the node value is printed first, followed by visit
     * to left and right subtree.
     * input:
     *     A
     *    / \
     *   B   E
     *  / \   \
     * C   D   F
     *    /   /
     *   G   H
     *
     * output: A B C D E F
     */


    public static void main(String[] args) throws Exception {

        // construct the binary tree given in question
        BinaryTree bt = new BinaryTree();
        BinaryTree.TreeNode root = new BinaryTree.TreeNode("A");
        bt.root = root;
        bt.root.left = new BinaryTree.TreeNode("B");
        bt.root.left.left = new BinaryTree.TreeNode("C");

        bt.root.left.right = new BinaryTree.TreeNode("D");
        bt.root.right = new BinaryTree.TreeNode("E");
        bt.root.right.right = new BinaryTree.TreeNode("F");
        bt.root.right.right.left = new BinaryTree.TreeNode("H");
        bt.root.left.right.left = new BinaryTree.TreeNode("G");

        // visiting nodes in preOrder traversal
        System.out.println("printing nodes of a binary tree in preOrder using recursion");
        bt.printHeader();
        bt.preOrder();
        bt.inOrder();
        bt.postOrder();

    }

}

class BinaryTree {
    static class TreeNode {
        String data;
        TreeNode left, right;

        TreeNode(String value) {
            this.data = value;
            left = right = null;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }

    }

    // root of binary tree
    TreeNode root;

    public void preOrder() {
        System.out.print("\npreOrder:    ");
        preOrder(root);
    }

    private void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.printf("%s ", node.data);
        preOrder(node.left);
        preOrder(node.right);
    }


    public void inOrder() {
        System.out.print("\ninOrder:     ");
        inOrder(root);
    }


    private void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.printf("%s ", node.data);
        inOrder(node.right);
    }


    public void postOrder() {
        System.out.print("\npostOrder:   ");
        postOrder(root);
    }


    private void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.printf("%s ", node.data);

    }

    void printHeader() {
        System.out.println(
                "                 A\n" +
                "                / \\\n" +
                "               B   E\n" +
                "              / \\   \\\n" +
                "             C   D   F\n" +
                "                /    /\n" +
                "               G    H\n");
    }

}

