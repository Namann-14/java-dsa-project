package datastructures;

import java.util.Comparator;
import java.util.function.Consumer;

public class BinarySearchTree<T> {
    private Node<T> root;
    private Comparator<T> comparator;

    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public BinarySearchTree(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node<T> insertRec(Node<T> root, T data) {
        if (root == null) {
            return new Node<>(data);
        }

        int compareResult = comparator.compare(data, root.data);
        if (compareResult < 0) {
            root.left = insertRec(root.left, data);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, data);
        }

        return root;
    }

    public boolean search(T data) {
        return searchRec(root, data);
    }

    private boolean searchRec(Node<T> root, T data) {
        if (root == null) {
            return false;
        }

        int compareResult = comparator.compare(data, root.data);
        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            return searchRec(root.left, data);
        } else {
            return searchRec(root.right, data);
        }
    }

    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private Node<T> deleteRec(Node<T> root, T data) {
        if (root == null) {
            return null;
        }

        int compareResult = comparator.compare(data, root.data);
        if (compareResult < 0) {
            root.left = deleteRec(root.left, data);
        } else if (compareResult > 0) {
            root.right = deleteRec(root.right, data);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            root.data = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.data);
        }

        return root;
    }

    private T minValue(Node<T> root) {
        T minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    public void inorderTraversal(Consumer<T> consumer) {
        inorderTraversalRec(root, consumer);
    }

    private void inorderTraversalRec(Node<T> root, Consumer<T> consumer) {
        if (root != null) {
            inorderTraversalRec(root.left, consumer);
            consumer.accept(root.data);
            inorderTraversalRec(root.right, consumer);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
} 