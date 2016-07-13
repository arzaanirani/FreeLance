import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/** Binary Search Tree implementation for Dictionary ADT */
class BST<Key extends Comparable<? super Key>, E> implements Dictionary<Key, E> {
    private BSTNode<Key, E> root; // Root of the BST
    private int nodecount; // Number of nodes in the BST

    /**
     * Constructor
     */
    BST() {
        root = null;
        nodecount = 0;
    }

    /**
     * Reinitialize tree
     */
    public void clear() {
        root = null;
        nodecount = 0;
    }

    /**
     * Insert a record into the tree.
     *
     * @param k Key value of the record.
     * @param e The record to insert.
     */
    public void insert(Key k, E e) {
        root = inserthelp(root, k, e);
        nodecount++;
    }

    /**
     * Recursively find the given node
     * @param rt    The node
     * @param k     The key
     * @return      The node found
     */
    private E findhelp(BSTNode<Key, E> rt, Key k) {
        if (rt == null) return null;
        if (rt.getKey().compareTo(k) > 0)
            return findhelp(rt.getLeft(), k);
        else if (rt.getKey().compareTo(k) == 0) return rt.getElement();
        else return findhelp(rt.getRight(), k);
    }

    /**
     * Remove a record from the tree.
     *
     * @param k Key value of record to remove.
     * @return The record removed, null if there is none.
     */
    public E remove(Key k) {
        E temp = findhelp(root, k); // First find it
        if (temp != null) {
            root = removehelp(root, k); // Now remove it
            nodecount--;
        }
        return temp;
    }

    /**
     * Remove and return the root node from the dictionary.
     *
     * @return The record removed, null if tree is empty.
     */
    public E removeAny() {
        if (root == null) return null;
        E temp = root.getElement();
        root = removehelp(root, root.getKey());
        nodecount--;
        return temp;
    }

    /**
     * @param k The key value to find.
     * @return Record with key value k, null if none exist.
     */
    public E find(Key k) {
        return findhelp(root, k);
    }

    /**
     * @return The number of records in the dictionary.
     */
    public int size() {
        return nodecount;
    }

    /**
     * @return The current subtree, modified to contain
     * the new item
     */
    private BSTNode<Key, E> inserthelp(BSTNode<Key, E> rt, Key k, E e) {
        //if (rt == null) return new BSTNode<Key,E>(k, e);
        if (rt == null) return new BSTNode<Key, E>(k, e, null, null);
        if (rt.getKey().compareTo(k) > 0)
            rt.setLeft(inserthelp(rt.getLeft(), k, e));
        else
            rt.setRight(inserthelp(rt.getRight(), k, e));
        return rt;
    }

    /**
     * Delete the minimum node in the subtree given by rt
     * @param rt    Current node
     * @return  Node that was deleted
     */
    private BSTNode<Key, E> deletemin(BSTNode<Key, E> rt) {
        if (rt.getLeft() == null) return rt.getRight();
        rt.setLeft(deletemin(rt.getLeft()));
        return rt;
    }

    /**
     * Get the minimum node in this subtree
     * @param rt    Current node
     * @return  minimum node
     */
    private BSTNode<Key, E> getmin(BSTNode<Key, E> rt) {
        if (rt.getLeft() == null) return rt;
        return getmin(rt.getLeft());
    }

    /**
     * Remove a node with key value k
     *
     * @return The tree with the node removed
     */
    private BSTNode<Key, E> removehelp(BSTNode<Key, E> rt, Key k) {
        if (rt == null) return null;
        if (rt.getKey().compareTo(k) > 0)
            rt.setLeft(removehelp(rt.getLeft(), k));
        else if (rt.getKey().compareTo(k) < 0)
            rt.setRight(removehelp(rt.getRight(), k));
        else { // Found it
            if (rt.getLeft() == null) return rt.getRight();
            else if (rt.getRight() == null) return rt.getLeft();
            else { // Two children
                BSTNode<Key, E> temp = getmin(rt.getRight());
                rt.setElement(temp.getElement());
                rt.setKey(temp.getKey());
                rt.setRight(deletemin(rt.getRight()));
            }
        }
        return rt;
    }

    /**
     * Traverses the tree in preorder
     */
    public void nonRecursivePreorderTraverse() {
        Deque<BSTNode> stack = new ArrayDeque<BSTNode>();
        BSTNode node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                System.out.println(node.getElement().toString());
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                }
                node = node.getLeft();
            } else {
                node = stack.pop();
            }
        }
    }

    /**
     * Traverses the tree in order
     */
    public void nonRecursiveInorderTraverse() {
        Deque<BSTNode> stack = new ArrayDeque<BSTNode>();
        BSTNode node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.getLeft();
            } else {
                node = stack.pop();
                System.out.println(node.getElement().toString());
                node = node.getRight();
            }
        }
    }

    /**
     * Traverses the tree in postorder
     */
    public void nonRecursivePostorderTraverse() {
        Deque<BSTNode> stack = new ArrayDeque<BSTNode>();
        BSTNode node = root;
        BSTNode lastNodeVisited = null;
        BSTNode peekNode;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.getLeft();
            } else {
                peekNode = stack.peek();
                if (peekNode.getRight() != null && lastNodeVisited != peekNode.getRight()) {
                    node = peekNode.getRight();
                } else {
                    System.out.println(node.getElement().toString());
                    lastNodeVisited = stack.pop();
                }
            }
        }
    }

    /**
     * Traverse the tree level by level
     */
    public void levelOrderTraverse() {
        Queue<BSTNode> queue = new LinkedList<BSTNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode n = queue.poll();
            System.out.println(n.getElement().toString());
            if (n.getLeft() != null) {
                queue.add(n.getLeft());
            }
            if (n.getRight() != null) {
                queue.add(n.getRight());
            }
        }
    }

    /**
     * Call the recursive DrawHelp function, starting at the root
     */
    public void draw() {
        drawHelp(root, 0);
    }

    /**
     * Recursively draws the tree
     * @param rt        Current node
     * @param level     Level this node is at
     */
    public void drawHelp(BSTNode<Key, E> rt, int level) {
        if (rt == null) return;
        drawHelp(rt.getRight(), level+1);
        for (int i = 0; i < level; i++) {
            System.out.print("   |");
        }
        System.out.println(rt.getElement().toString());
        drawHelp(rt.getLeft(),level+1);
    }
}