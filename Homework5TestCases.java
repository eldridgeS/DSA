import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class Homework5TestCases {
    private AVL<Integer> avlTree;

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    ///////////// Constructor tests
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullCollection() {
        avlTree = new AVL<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorCollectionWithNullElement() {
        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(1);
        toAdd.add(null);
        avlTree = new AVL<>(toAdd);
    }

    @Test
    public void testConstructorSingleElement() {
        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(1);
        avlTree = new AVL<>(toAdd);

        assertEquals(1, avlTree.size());
        assertEquals((Integer) 1, avlTree.getRoot().getData());
        assertEquals(0, avlTree.getRoot().getHeight());
        assertEquals(0, avlTree.getRoot().getBalanceFactor());
    }

    @Test
    public void testConstructorMultipleElementsNoDuplicates() {
        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(3);
        toAdd.add(1);
        toAdd.add(5);
        toAdd.add(2);
        toAdd.add(4);
        avlTree = new AVL<>(toAdd);

        assertEquals(5, avlTree.size());

        // Expected tree structure:
        //       3
        //     /   \
        //    1     5
        //     \   /
        //      2 4

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 1, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(-1, left.getBalanceFactor());
        assertEquals((Integer) 2, left.getRight().getData());

        assertEquals((Integer) 5, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(1, right.getBalanceFactor());
        assertEquals((Integer) 4, right.getLeft().getData());
    }

    @Test
    public void testConstructorMultipleElementsWithDuplicates() {
        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(3);
        toAdd.add(1);
        toAdd.add(5);
        toAdd.add(2);
        toAdd.add(4);
        toAdd.add(3);
        avlTree = new AVL<>(toAdd);

        assertEquals(5, avlTree.size());

        // Expected tree structure:
        //       3
        //     /   \
        //    1     5
        //     \   /
        //      2 4

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        AVLNode<Integer> left = root.getLeft();
        AVLNode<Integer> right = root.getRight();
        assertEquals((Integer) 1, left.getData());
        assertEquals(1, left.getHeight());
        assertEquals(-1, left.getBalanceFactor());
        assertEquals((Integer) 2, left.getRight().getData());

        assertEquals((Integer) 5, right.getData());
        assertEquals(1, right.getHeight());
        assertEquals(1, right.getBalanceFactor());
        assertEquals((Integer) 4, right.getLeft().getData());
    }

    /////////////// Add methods
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullData() {
        avlTree.add(null);
    }

    @Test
    public void testAddSingleElement() {
        avlTree.add(5);

        assertEquals(1, avlTree.size());
        assertEquals((Integer) 5, avlTree.getRoot().getData());
        assertEquals(0, avlTree.getRoot().getHeight());
        assertEquals(0, avlTree.getRoot().getBalanceFactor());
    }

    @Test
    public void testAddDuplicateElement() {
        avlTree.add(5);
        avlTree.add(5);

        assertEquals(1, avlTree.size());
        assertEquals((Integer) 5, avlTree.getRoot().getData());
    }

    @Test
    public void testAddRightRotationAfterAdditions() {
        /*
            5
            /
            3
            /
        1
        */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(1);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        /*
        After adding (right rotation at root):
                3
            / \
            1   5
        */
    }

    @Test
    public void testAddLeftRotationAfterAdditions() {
        /*
               1
                \
                 3
                  \
                   5
         */
        avlTree.add(1);
        avlTree.add(3);
        avlTree.add(5);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    
        /*
           After adding (left rotation at root):
                3
               / \
              1   5
         */
    }

    @Test
    public void testAddLeftRightRotationAfterAdditions() {
        /*
               5
              /
             1
              \
               3
                \
                 4
         */
        avlTree.add(5);
        avlTree.add(1);
        avlTree.add(3);
        avlTree.add(4);

        assertEquals(4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 4, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
    
        /*
           After adding (left-right rotation at root):
                3
               / \
              1   5
                 /
                4
         */
    }

    @Test
    public void testAddRightLeftRotationAfterAdditions() {
        /*
               1
                \
                 5
                /
               3
              /
             4
         */
        avlTree.add(1);
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(4);

        assertEquals(4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 4, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
    
        /*
           After adding (right-left rotation at root):
                3
               / \
              1   5
                 /
                4
         */
    }

    @Test
    public void testAddSequenceWithRotations() {
        /*
               5
              / \
             3   7
            /      \
           2        9
          /
         1
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(9);
        avlTree.add(1);

        assertEquals(6, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 5, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 2, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 7, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 9, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
    
        /*
           After adding (right rotation at 3)
                5
               / \
              2   7
             / \   \
            1   3   9
                     
                      
         */
    }

    ////////////// Remove tests
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullData() {
        avlTree.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNonExistentData() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);

        avlTree.remove(10);
    }

    @Test
    public void testRemoveLeafNode() {
        /*
         *    5            5
         *   / \     ->   / \
         *  3   7        3   7
         * /
         * 2
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 2, avlTree.remove(2));
        assertEquals(3, avlTree.size());
        assertNull(root.getLeft().getLeft());
        assertEquals((Integer) 5, root.getData());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals((Integer) 7, root.getRight().getData());
    }

    @Test
    public void testRemoveNodeWithOneChild() {
        /*
         *    5            5
         *   / \     ->   / \
         *  3   7        4   7
         *   \
         *    4
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(4);

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 3, avlTree.remove(3));
        assertEquals(3, avlTree.size());
        assertEquals((Integer) 5, root.getData());
        assertEquals((Integer) 4, root.getLeft().getData());
        assertEquals((Integer) 7, root.getRight().getData());
    }

    @Test
    public void testRemoveNodeWithTwoChildren() {
        /*
         *    5            4
         *   / \     ->   / \
         *  3   7        3   7
         * / \         /
         *2   4       2
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(4);

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 5, avlTree.remove(5));
        assertEquals(4, avlTree.size());
        assertEquals((Integer) 4, root.getData());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals((Integer) 7, root.getRight().getData());
        assertEquals((Integer) 2, root.getLeft().getLeft().getData());
    }

    @Test
    public void testRemoveNodeWithRotation() {
        /*
         *    5            3
         *   / \     ->   / \
         *  3   7        2   5
         * /
         * 2
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);

        assertEquals((Integer) 7, avlTree.remove(7));
        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 3, root.getData());
        assertEquals((Integer) 2, root.getLeft().getData());
        assertEquals((Integer) 5, root.getRight().getData());
    }

    ////////////////// Get tests
    @Test(expected = IllegalArgumentException.class)
    public void testGetNullData() {
        avlTree.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNonExistentData() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.get(10);
    }

    @Test
    public void testGetSingleNode() {
        avlTree.add(5);
        assertEquals((Integer) 5, avlTree.get(5));
    }

    @Test
    public void testGetRootNode() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        assertEquals((Integer) 5, avlTree.get(5));
    }

    @Test
    public void testGetLeafNode() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        assertEquals((Integer) 3, avlTree.get(3));
    }

    @Test
    public void testGetNodeWithMultipleLevels() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(4);
        assertEquals((Integer) 2, avlTree.get(2));
    }

    //////////////// Contains method
    @Test(expected = IllegalArgumentException.class)
    public void testContainsNullData() {
        avlTree.contains(null);
    }

    @Test
    public void testDoesNotContainNode() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        assertFalse(avlTree.contains(10));
    }

    @Test
    public void testContainsSingleNode() {
        avlTree.add(5);
        assertTrue(avlTree.contains(5));
    }

    @Test
    public void testContainsNodeWithMultipleLevels() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(4);
        assertTrue(avlTree.contains(2));
    }

    //////////////// Clear tests
    @Test
    public void testClearEmptyTree() {
        avlTree.clear();
        assertEquals(null, avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }

    @Test
    public void testClearSingleNodeTree() {
        avlTree.add(5);
        avlTree.clear();
        assertEquals(null, avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }

    @Test
    public void testClearMultiNodeTree() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(4);

        avlTree.clear();
        assertEquals(null, avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }

    /////////////////// Height tests
    @Test
    public void testHeightEmptyTree() {
        assertEquals(-1, avlTree.height());
    }

    @Test
    public void testHeightSingleNodeTree() {
        avlTree.add(5);
        assertEquals(0, avlTree.height());
    }

    @Test
    public void testHeightBalancedTree() {
        /*
         *    5
         *   / \
         *  3   7
         * / \
         *2   4
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(4);

        assertEquals(2, avlTree.height());
    }

    @Test
    public void testHeightUnbalancedTree() {
        /*
         *       5
         *      /
         *     4
         *    /
         *   3
         *  /
         * 2
         */
        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);
        avlTree.add(2);

        assertEquals(2, avlTree.height());
    }

    ///////// Find Median tests
    @Test(expected = NoSuchElementException.class)
    public void testFindMedianEmptyTree() {
        avlTree.findMedian();
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindMedianEvenElements() {
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(4);
        avlTree.add(6);

        assertEquals((Integer) 5, avlTree.findMedian());
    }

    @Test
    public void testFindMedianSingleElementTree() {
        avlTree.add(5);

        assertEquals((Integer) 5, avlTree.findMedian());
    }

    @Test
    public void testFindMedianRoot() {
        /*
         *     5
         *   /   \
         *  3     7
         * / \   / \
         *2  4  6   8
         *
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(4);
        avlTree.add(6);
        avlTree.add(8);

        assertEquals((Integer) 5, avlTree.findMedian());
    }

    @Test
    public void testFindMedianNonRoot() {
        /*
         *     5
         *    / \
         *   3   7
         *  / \
         * 2   4
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(7);
        avlTree.add(2);
        avlTree.add(4);

        assertEquals((Integer) 4, avlTree.findMedian());
    }
}