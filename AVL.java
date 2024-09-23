import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author ELDRIDGE SURIANTO
 * @userid esurianto3
 * @GTID 903440765
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else {
            size = 0;
            for (T element: data) {
                if (element == null) {
                    throw new IllegalArgumentException("Null element in data");
                } else {
                    add(element);
                }
            }
        }

    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        root = addHelper(data, root);

    }

    /**
     * Helper method to recursively add data
     * @param data to be added
     * @param node first root, then current node
     * @return root for root, rebalanced for other nodes
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        int comparison = data.compareTo(node.getData());
        if (comparison < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (comparison > 0) {
            node.setRight(addHelper(data, node.getRight()));
        } else {
            return node;
        }
        recalculate(node);
        return balance(node);
    }

    /**
     * Balance Function to call the necessary notations on the way back
     * to return the root node
     * @param node to be rebalanced
     * @return balanced node
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) { //Right heavy
            
            if (node.getRight().getBalanceFactor() > 0) { //RightLeft heavy
                node.setRight(rotateRight(node.getRight())); //Right rotation
            }
            node = rotateLeft(node); //Left Rotation
        } else if (node.getBalanceFactor() > 1) { //Left Heavy
            
            if (node.getLeft().getBalanceFactor() < 0) { //LeftRight heavy
                node.setLeft(rotateLeft(node.getLeft())); //Left Rotation
            }
            node = rotateRight(node); //Right Rotation
        }
        return node;
    }

    /**
     * Helper method to perform left rotation if right heavy
     * @param node to be rotated
     * @return right child (new parent)
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> right = node.getRight();
        node.setRight(right.getLeft());
        right.setLeft(node);
        recalculate(node);
        recalculate(right);
        return right;
    }

    /**
     * Helper method to perform right rotation if left heavy
     * @param node to be rotated
     * @return left child (new parent)
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        node.setLeft(left.getRight());
        left.setRight(node);
        recalculate(node);
        recalculate(left);
        return left;
    }


    /**
     * set height and balance factor
     * @param node to be calculated
     */
    private void recalculate(AVLNode<T> node) {
        int lHeight = height(node.getLeft());
        int rHeight = height(node.getRight());
        node.setHeight(Math.max(lHeight, rHeight) + 1);
        node.setBalanceFactor(lHeight - rHeight);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeR(data, root, removed);
        return removed.getData();

    }

    /**
     * Helper method to recursively remove and balance node
     * @param data to be removed
     * @param node current pointer
     * @param removed dummy node to store removed data
     * @return parent node of removed node
     */
    private AVLNode<T> removeR(T data, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("Data Not Found");
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeR(data, node.getLeft(), removed));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeR(data, node.getRight(), removed));
        } else {
            removed.setData(node.getData());
            size--;
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> replaced = new AVLNode<>(null);
                node.setLeft(predecessorR(node.getLeft(), replaced));
                node.setData(replaced.getData());

            }

        }
        recalculate(node);
        return balance(node);
    }

    /**
     * Recursive helper method to find and skip the predecessor
     * @param node current node
     * @param removed dummy node to store removed node
     * @return pointer reinforcement node or removed node child
     */
    private AVLNode<T> predecessorR(AVLNode<T> node, AVLNode<T> removed) {
        if (node.getRight() == null) {
            removed.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(predecessorR(node.getRight(), removed));
            return node;
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        //similar to add method, handle null and empty case, then create node
        //to store and return found node data
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        } else {
            AVLNode<T> foundNode = getR(root, data);
            return foundNode.getData();
        }
    }

    /**
     * Recursive function for add
     * Handles Base case of data not found, then traverse recursively
     * if curr data == data, then curr will be returned
     * @param curr recursive pointer
     * @param data data to be searched
     * @return found node
     */
    private AVLNode<T> getR(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data not found");
        }
        if (data.compareTo(curr.getData()) < 0) {
            return getR(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return getR(curr.getRight(), data);
        } else {
            return curr;
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Finds and retrieves the median data of the passed in AVL. 
     * 
     * This can be done in O(n) time. That being said, this method will not need
     * to traverse the entire tree to function properly, so you should only
     * traverse enough branches of the tree necessary to find the median data
     * and only do so once. Failure to do so will result in an efficiency
     * penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     * 
     * findMedian() should return 40
     *
     * @throws java.util.NoSuchElementException if the tree is empty or contains
     * an even number of data
     * @return the median data of the AVL
     */
    public T findMedian() {
        if (size == 0 || size % 2 == 0) {
            throw new NoSuchElementException("Tree is empty or even size");
        }
        AVLNode<T> medianNode = new AVLNode<>(null);
        AVLNode<Integer> count = new AVLNode<>(0);
        findMedianHelper(root, count, medianNode);
        return medianNode.getData();
    }

    /**
     * Helper method to find median in inOrder traversal
     * @param node traverse left -> center -> right
     * @param count counter of node type AVLNode so its pass by address
     * @param medianNode dummy node to store the median node to be returned
     */
    private void findMedianHelper(AVLNode<T> node, AVLNode<Integer> count,
                                  AVLNode<T> medianNode) {
        if (node == null) {
            return;
        }

        findMedianHelper(node.getLeft(), count, medianNode);

        count.setData(count.getData() + 1);
        if (count.getData() == (size + 1) / 2) {
            medianNode.setData(node.getData());
            return;
        }

        findMedianHelper(node.getRight(), count, medianNode);
    }



    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Helper method with a parameter to find height
     * @param node height to be found
     * @return height, else -1 if null
     */
    private int height(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}