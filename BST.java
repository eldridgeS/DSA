import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Eldridge Surianto
 * @version 1.0
 * @userid esurianto3
 * @GTID 903440765
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        //use for loop without index to iterate data and add each element
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else {
            size = 0;
            for (T element : data) {

                if (element == null) {
                    throw new IllegalArgumentException("Null element in data");
                } else {
                    add(element);
                }
            }
        }


    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        //handle null and empty case, then call recursive function
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else {
            addR(root, data);
        }
    }

    /**
     * Recursive function for add
     * Starting with root, compare current to data and recurse left or right
     * if next node is empty, then add to that spot
     * @param curr recursive pointer
     * @param data the data to add
     */
    private void addR(BSTNode<T> curr, T data) {
        if (data.compareTo(curr.getData()) < 0) {
            if (curr.getLeft() == null) {
                curr.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addR(curr.getLeft(), data);
            }
        } else if (data.compareTo(curr.getData()) > 0) {
            if (curr.getRight() == null) {
                curr.setRight(new BSTNode<>(data));
                size++;
            } else {
                addR(curr.getRight(), data);
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        }
        //create storage node, run recursive method on root, decrement size, return data
        BSTNode<T> removedNode = new BSTNode<>(null);
        root = removeR(root, data, removedNode);
        size--;
        return removedNode.getData();
    }

    private BSTNode<T> removeR(BSTNode<T> curr, T data, BSTNode<T> removed) {
        if (curr == null) {
            throw new NoSuchElementException("Data not found");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeR(curr.getLeft(), data, removed));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeR(curr.getRight(), data, removed));
        } else {
            removed.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> replaced = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), replaced));
                curr.setData(replaced.getData());
            }
        }
        return curr;
    }

    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> replaced) {
        if (curr.getLeft() == null) {
            replaced.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), replaced));
            return curr;
        }

    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        //similar to add method, handle null and empty case, then create node
        //to store and return found node data
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        } else {
            BSTNode<T> foundNode = getR(root, data);
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
    private BSTNode<T> getR(BSTNode<T> curr, T data) {
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
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        /*
        handle null case, then try to run get() function. If successful, then
        BST contains data and return false. Else, NoSuchElementException will be
        caught but not called and instead return false.
         */
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        try {
            get(data);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        //create storage list, call recursive function, then return result
        List<T> result = new ArrayList<>();
        preorderR(root, result);
        return result;
    }

    /**
     * Recursive function for preorder traversal
     * if a null node is reached, return nothing and stop traversing
     * Add parent node, traverse left, then traverse right
     * @param curr recursive pointer
     * @param result list to be returned in main function
     */
    private void preorderR(BSTNode<T> curr, List<T> result) {
        if (curr == null) {
            return;
        }
        result.add(curr.getData());
        preorderR(curr.getLeft(), result);
        preorderR(curr.getRight(), result);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        //create storage list, call recursive function, then return result
        List<T> result = new ArrayList<>();
        inorderR(root, result);
        return result;
    }

    /**
     * Recursive function for inorder traversal
     * if a null node is reached, return nothing and stop traversing
     * Traverse left, add current node, then traverse right
     * @param curr recursive pointer
     * @param result list to be returned in main function
     */
    private void inorderR(BSTNode<T> curr, List<T> result) {
        if (curr == null) {
            return;
        }
        inorderR(curr.getLeft(), result);
        result.add(curr.getData());
        inorderR(curr.getRight(), result);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        //create storage list, call recursive function, then return result
        List<T> result = new ArrayList<>();
        postorderR(root, result);
        return result;
    }

    /**
     * Recursive function for postorder traversal
     * if a null node is reached, return nothing and stop traversing
     * Traverse left, traverse right, then add current node
     * @param curr recursive pointer
     * @param result list to be returned in main function
     */
    private void postorderR(BSTNode<T> curr, List<T> result) {
        if (curr == null) {
            return;
        }
        postorderR(curr.getLeft(), result);
        postorderR(curr.getRight(), result);
        result.add(curr.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        /*
        Create a list to store and return. Handle empty case, then create a
        queue using linked list framework with queue methods.
        Add root then iterate through queue and dequeue element, adding to
        result and adding its children to the queue. Finally, return result
         */
        List<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BSTNode<T> curr = queue.poll();
            result.add(curr.getData());

            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.add(curr.getRight());
            }
        }
        return result;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        //handle empty case, then call recursive height function
        if (root == null) {
            return -1;
        }
        return heightR(root);
    }

    /**
     * Recursive method to find height
     * Base case of no child node return -1
     * recursively calls function for left and right child
     * return node's height using max function +1
     * having one node will return -1 + 1 = 0
     * @param curr recursive pointer
     * @return height of current recurse node
     */
    private int heightR(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int leftHeight = heightR(curr.getLeft());
        int rightHeight = heightR(curr.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        // set root to null and reset size
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        /*
        Handle the illegal argument cases
        Create a list to store the result, reverse the result and return
         */
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("Invalid k!");
        }
        List<T> result = new LinkedList<>();
        kLargestR(root, result, k);
        return result;
    }

    /**
     * Recursive function to return the kLargest list in reverse order
     * traverses the node in reverse order and adds the nodes to the front of
     * the arraylist since we are traversing backwards
     * @param curr recursive pointer
     * @param result list to be returned
     * @param length parameter for k
     */
    private void kLargestR(BSTNode<T> curr, List<T> result, int length) {
        if (curr == null ) {
            return;
        }
        kLargestR(curr.getRight(), result, length);
        if (result.size() < length) {
            result.addFirst( curr.getData());
        }
        kLargestR(curr.getLeft(), result, length);
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
