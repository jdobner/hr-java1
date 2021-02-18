package net.jerrydobner.ed.binarytree;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BTraversal {
    static class TreeNode<T> {
        TreeNode<T> left;
        TreeNode<T> right;
        final T value;

        public TreeNode(T value) {
            this.value = value;
        }
    }

    <T> List<List<T>> treeNodeToArrays(TreeNode<T> root) {
        final List<List<T>> listOfArrays = new ArrayList<>();
        var upperList = Collections.singletonList(root);
        int size = 1;
        do {
            final List<T> list = new ArrayList<>(upperList.size());
            final List<TreeNode<T>> nodes = new ArrayList<>(size * 2);

            for (var node : upperList) {
                list.add(node.value);
                if (node.left != null) {
                    nodes.add(node.left);
                    nodes.add(node.right);
                }
            }
            listOfArrays.add(list);
            upperList = nodes;
        } while (!upperList.isEmpty());
        return listOfArrays;

    }

    public <T> TreeNode<T> arrayToTreeNode(List<List<T>> data) {
        var root = new TreeNode<T>(data.get(0).get(0));
        var row = Collections.singletonList(root);
        for (int i = 1; i < data.size(); i++) {
            var nextRow = new ArrayList<TreeNode<T>>(row.size() * 2);
            var dataRow = data.get(i);
            for (int j = 0; j < dataRow.size(); j += 2) {
                var parent = row.get(j / 2);
                parent.left = new TreeNode<>(dataRow.get(j));
                nextRow.add(parent.left);
                parent.right = new TreeNode<>(dataRow.get(j + 1));
                nextRow.add(parent.right);
            }
            row = nextRow;
        }
        return root;
    }

}
