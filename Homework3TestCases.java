import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Collection;

import static org.junit.Assert.*;

public class Homework3TestCases {
    private BST<Integer> tree;

    @Before
    public void setup() {
        tree = new BST<>();
    }

    /////////////// Contructor tests
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullCollection() {
        new BST<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithCollectionContainingNullElements() {
        Collection<Integer> data = Arrays.asList(50, 25, null, 75);

        new BST<>(data);
    }

    @Test
    public void testConstructorWithValidCollection() {
        Collection<Integer> data = Arrays.asList(50, 25, 75, 10, 30);

        tree = new BST<>(data);

        assertTrue(tree.contains(50));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(75));
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(30));

        assertEquals(5, tree.size());
    }

    ///////////////// Add method tests
    @Test(expected = IllegalArgumentException.class)
    public void testAddNull() {
        tree.add(null);
    }

    @Test
    public void testAddElements() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);

        /*
              50
             /  \
           25    75
          /  \
         10  30
        */


        assertEquals(5, tree.size());
        assertEquals(Integer.valueOf(50), tree.getRoot().getData());

        BSTNode<Integer> root = tree.getRoot();
        assertEquals(Integer.valueOf(25), root.getLeft().getData());
        assertEquals(Integer.valueOf(75), root.getRight().getData());
        assertEquals(Integer.valueOf(10), root.getLeft().getLeft().getData());
        assertEquals(Integer.valueOf(30), root.getLeft().getRight().getData());
    }

    @Test
    public void testAddSingleElement() {
        tree.add(50);

        assertEquals(1, tree.size());
        assertEquals(Integer.valueOf(50), tree.getRoot().getData());
    }

    @Test
    public void testAddDuplicateElements() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(25);
        tree.add(75);

        assertEquals(3, tree.size());
        assertEquals(Integer.valueOf(50), tree.getRoot().getData());

        BSTNode<Integer> root = tree.getRoot();
        assertEquals(Integer.valueOf(25), root.getLeft().getData());
        assertEquals(Integer.valueOf(75), root.getRight().getData());
        assertNull(root.getRight().getRight());
        assertNull(root.getLeft().getLeft());
    }

    @Test
    public void testAddMultipleElements() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(60);
        tree.add(80);

        /*
              50
             /  \
           25    75
          /  \   / \
         10  30 60 80
        */

        assertEquals(7, tree.size());
        assertEquals(Integer.valueOf(50), tree.getRoot().getData());

        BSTNode<Integer> root = tree.getRoot();
        assertEquals(Integer.valueOf(25), root.getLeft().getData());
        assertEquals(Integer.valueOf(75), root.getRight().getData());
        assertEquals(Integer.valueOf(10), root.getLeft().getLeft().getData());
        assertEquals(Integer.valueOf(30), root.getLeft().getRight().getData());
        assertEquals(Integer.valueOf(60), root.getRight().getLeft().getData());
        assertEquals(Integer.valueOf(80), root.getRight().getRight().getData());
    }

    @Test
    public void testAddElementsInAscendingOrder() {
        tree.add(10);
        tree.add(20);
        tree.add(30);
        tree.add(40);
        tree.add(50);

        assertEquals(5, tree.size());
        assertEquals(Integer.valueOf(10), tree.getRoot().getData());

        BSTNode<Integer> root = tree.getRoot();
        assertEquals(Integer.valueOf(20), root.getRight().getData());
        assertEquals(Integer.valueOf(30), root.getRight().getRight().getData());
        assertEquals(Integer.valueOf(40), root.getRight().getRight().getRight().getData());
        assertEquals(Integer.valueOf(50), root.getRight().getRight().getRight().getRight().getData());
    }

    @Test
    public void testAddComplexTree() {
        tree.add(50);
        assertEquals(50, (int) tree.getRoot().getData());

        tree.add(25);
        assertEquals(25, (int) tree.getRoot().getLeft().getData());

        tree.add(75);
        assertEquals(75, (int) tree.getRoot().getRight().getData());

        tree.add(10);
        assertEquals(10, (int) tree.getRoot().getLeft().getLeft().getData());

        tree.add(30);
        assertEquals(30, (int) tree.getRoot().getLeft().getRight().getData());

        tree.add(27);
        assertEquals(27, (int) tree.getRoot().getLeft().getRight().getLeft().getData());

        tree.add(35);
        assertEquals(35, (int) tree.getRoot().getLeft().getRight().getRight().getData());

        tree.add(5);
        assertEquals(5, (int) tree.getRoot().getLeft().getLeft().getLeft().getData());

        tree.add(20);
        assertEquals(20, (int) tree.getRoot().getLeft().getLeft().getRight().getData());

        tree.add(28);
        assertEquals(28, (int) tree.getRoot().getLeft().getRight().getLeft().getRight().getData());
    }

    ///////////// Remove method tests
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        tree.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFromEmptyTree() {
        tree.remove(50);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNonExistentElement() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.remove(100);
    }

    @Test
    public void testRemoveLeafNode() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);

        /*
              50
             /  \
           25    75
          /  \
         10  30
        */

        assertEquals(Integer.valueOf(10), tree.remove(10));
        assertEquals(4, tree.size());

        /*
              50
             /  \
           25    75
             \
              30
        */

        assertEquals(Integer.valueOf(50), tree.getRoot().getData());
        BSTNode<Integer> root = tree.getRoot();
        assertEquals(Integer.valueOf(25), root.getLeft().getData());
        assertNull(root.getLeft().getLeft());
        assertEquals(Integer.valueOf(30), root.getLeft().getRight().getData());
        assertEquals(Integer.valueOf(75), root.getRight().getData());
    }

    @Test
    public void testRemoveNodeWithOneChild() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);

        /*
              50
             /  \
           25    75
          /
         10
        */

        assertEquals(Integer.valueOf(25), tree.remove(25));
        assertEquals(3, tree.size());

        /*
              50
             /  \
           10    75
        */

        assertEquals(Integer.valueOf(50), tree.getRoot().getData());
        BSTNode<Integer> root = tree.getRoot();
        assertEquals(Integer.valueOf(10), root.getLeft().getData());
        assertNull(root.getLeft().getLeft());
        assertNull(root.getLeft().getRight());
        assertEquals(Integer.valueOf(75), root.getRight().getData());
    }

    @Test
    public void testRemoveNodeWithTwoChildren() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);

        /*
              50
             /  \
           25    75
          /  \
         10  30
            /  \
          27   35
        */

        assertEquals(Integer.valueOf(25), tree.remove(25));
        assertEquals(6, tree.size());

        /*
              50
             /  \
           27    75
          /  \
         10  30
              \
              35
        */

        assertEquals(Integer.valueOf(50), tree.getRoot().getData());
        BSTNode<Integer> root = tree.getRoot();
        assertEquals(Integer.valueOf(27), root.getLeft().getData());
        assertEquals(Integer.valueOf(10), root.getLeft().getLeft().getData());
        assertEquals(Integer.valueOf(30), root.getLeft().getRight().getData());
        assertEquals(Integer.valueOf(35), root.getLeft().getRight().getRight().getData());
        assertEquals(Integer.valueOf(75), root.getRight().getData());
    }

    @Test
    public void testRemoveRootNodeWithTwoChildren() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);

        /*
              50
             /  \
           25    75
          /  \
         10  30
        */

        assertEquals(Integer.valueOf(50), tree.remove(50));
        assertEquals(4, tree.size());

        /*
              75
             /  
           25    
          /  \
         10  30
        */

        assertEquals(Integer.valueOf(75), tree.getRoot().getData());
        BSTNode<Integer> root = tree.getRoot();
        assertEquals(Integer.valueOf(25), root.getLeft().getData());
        assertEquals(Integer.valueOf(10), root.getLeft().getLeft().getData());
        assertEquals(Integer.valueOf(30), root.getLeft().getRight().getData());
        assertNull(root.getRight());
    }

    ///////////// Get method tests
    @Test(expected = IllegalArgumentException.class)
    public void testGetNull() {
        tree.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetFromEmptyTree() {
        tree.get(50);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNonExistentElement() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.get(100);
    }

    @Test
    public void testGetRoot() {
        tree.add(50);

        /*
              50
        */

        assertEquals(Integer.valueOf(50), tree.get(50));
    }

    @Test
    public void testGetLeafNode() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);

        /*
              50
             /  \
           25    75
          /  \
         10  30
        */

        assertEquals(Integer.valueOf(10), tree.get(10));
        assertEquals(Integer.valueOf(30), tree.get(30));
    }

    @Test
    public void testGetNodeWithChildren() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);

        /*
              50
             /  \
           25    75
          /  \
         10  30
            /  \
          27   35
        */

        assertEquals(Integer.valueOf(25), tree.get(25));
        assertEquals(Integer.valueOf(30), tree.get(30));
        assertEquals(Integer.valueOf(27), tree.get(27));
        assertEquals(Integer.valueOf(35), tree.get(35));
    }

    /////////////////// Contains method tests
    @Test(expected = IllegalArgumentException.class)
    public void testContainsNull() {
        tree.contains(null);
    }

    @Test
    public void testContainsInEmptyTree() {
        assertFalse(tree.contains(50));
    }

    @Test
    public void testContainsNonExistentElement() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        assertFalse(tree.contains(100));
    }

    @Test
    public void testContainsMultipleElements() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);
        tree.add(5);
        tree.add(20);
        tree.add(28);

        assertTrue(tree.contains(50));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(75));
        assertTrue(tree.contains(10));
        assertTrue(tree.contains(30));
        assertTrue(tree.contains(27));
        assertTrue(tree.contains(35));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(20));
        assertTrue(tree.contains(28));
    }

    @Test
    public void testDoesNotContainElements() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);

        assertFalse(tree.contains(40));
        assertFalse(tree.contains(5));
        assertFalse(tree.contains(100));
    }

    /////////// Preorder method tests
    @Test
    public void testPreorderEmptyTree() {
        List<Integer> expected = Arrays.asList();
        assertEquals(expected, tree.preorder());
    }

    @Test
    public void testPreorderSingleElement() {
        tree.add(50);
        /*
              50
        */
        List<Integer> expected = Arrays.asList(50);
        assertEquals(expected, tree.preorder());
    }

    @Test
    public void testPreorderLeftOnlyTree() {
        tree.add(50);
        tree.add(25);
        tree.add(10);
        tree.add(5);
        tree.add(1);
        /*
              50
             /
           25
          /
         10
        /
       5
      /
     1
        */
        List<Integer> expected = Arrays.asList(50, 25, 10, 5, 1);
        assertEquals(expected, tree.preorder());
    }

    @Test
    public void testPreorderFullTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(60);
        tree.add(80);
        /*
              50
             /  \
           25    75
          /  \   /  \
         10  30 60  80
        */
        List<Integer> expected = Arrays.asList(50, 25, 10, 30, 75, 60, 80);
        assertEquals(expected, tree.preorder());
    }

    @Test
    public void testPreorderComplexTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);
        tree.add(5);
        tree.add(20);
        /*
              50
             /  \
           25    75
          /  \
         10  30
        / \  /  \
       5  20 27  35
        */
        List<Integer> expected = Arrays.asList(50, 25, 10, 5, 20, 30, 27, 35, 75);
        assertEquals(expected, tree.preorder());
    }

    @Test
    public void testPreorderWithOnlyRightSubtree() {
        tree.add(50);
        tree.add(75);
        tree.add(60);
        tree.add(80);
        tree.add(55);
        tree.add(65);
        /*
        50
          \
          75
         /  \
        60  80
       /  \
      55  65
        */
        List<Integer> expected = Arrays.asList(50, 75, 60, 55, 65, 80);
        assertEquals(expected, tree.preorder());
    }

    ///////// Inorder method tests
    @Test
    public void testInorderEmptyTree() {
        List<Integer> expected = Arrays.asList();
        assertEquals(expected, tree.inorder());
    }

    @Test
    public void testInorderSingleElement() {
        tree.add(50);
        List<Integer> expected = Arrays.asList(50);
        assertEquals(expected, tree.inorder());
    }

    @Test
    public void testInorderLeftOnlyTree() {
        tree.add(50);
        tree.add(25);
        tree.add(10);
        tree.add(5);
        tree.add(1);
        /*
              50
             /
           25
          /
         10
        /
       5
      /
     1
        */
        List<Integer> expected = Arrays.asList(1, 5, 10, 25, 50);
        assertEquals(expected, tree.inorder());
    }

    @Test
    public void testInorderFullTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(60);
        tree.add(80);
        /*
              50
             /  \
           25    75
          /  \   /  \
         10  30 60  80
        */
        List<Integer> expected = Arrays.asList(10, 25, 30, 50, 60, 75, 80);
        assertEquals(expected, tree.inorder());
    }

    @Test
    public void testInorderComplexTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);
        tree.add(5);
        tree.add(20);
        /*
              50
             /  \
           25    75
          /  \
         10  30
        / \  /  \
       5  20 27  35
        */
        List<Integer> expected = Arrays.asList(5, 10, 20, 25, 27, 30, 35, 50, 75);
        assertEquals(expected, tree.inorder());
    }

    @Test
    public void testInorderWithOnlyRightSubtree() {
        tree.add(50);
        tree.add(75);
        tree.add(60);
        tree.add(80);
        tree.add(55);
        tree.add(65);
        /*
        50
          \
          75
         /  \
        60  80
       /  \
      55  65
        */
        List<Integer> expected = Arrays.asList(50, 55, 60, 65, 75, 80);
        assertEquals(expected, tree.inorder());
    }

    /////////// Postorder method tests
    @Test
    public void testPostorderEmptyTree() {
        List<Integer> expected = Arrays.asList();
        assertEquals(expected, tree.postorder());
    }

    @Test
    public void testPostorderSingleElement() {
        tree.add(50);
        List<Integer> expected = Arrays.asList(50);
        assertEquals(expected, tree.postorder());
    }

    @Test
    public void testPostorderLeftOnlyTree() {
        tree.add(50);
        tree.add(25);
        tree.add(10);
        tree.add(5);
        tree.add(1);
        /*
              50
             /
           25
          /
         10
        /
       5
      /
     1
        */
        List<Integer> expected = Arrays.asList(1, 5, 10, 25, 50);
        assertEquals(expected, tree.postorder());
    }

    @Test
    public void testPostorderFullTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(60);
        tree.add(80);
        /*
              50
             /  \
           25    75
          /  \   /  \
         10  30 60  80
        */
        List<Integer> expected = Arrays.asList(10, 30, 25, 60, 80, 75, 50);
        assertEquals(expected, tree.postorder());
    }

    @Test
    public void testPostorderComplexTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);
        tree.add(5);
        tree.add(20);
        /*
              50
             /  \
           25    75
          /  \
         10  30
        / \  /  \
       5  20 27  35
        */
        List<Integer> expected = Arrays.asList(5, 20, 10, 27, 35, 30, 25, 75, 50);
        assertEquals(expected, tree.postorder());
    }

    @Test
    public void testPostorderWithOnlyRightSubtree() {
        tree.add(50);
        tree.add(75);
        tree.add(60);
        tree.add(80);
        tree.add(55);
        tree.add(65);
        /*
        50
          \
          75
         /  \
        60  80
       /  \
      55  65
        */
        List<Integer> expected = Arrays.asList(55, 65, 60, 80, 75, 50);
        assertEquals(expected, tree.postorder());
    }

    ////////////// Levelorder method tests
    @Test
    public void testLevelorderEmptyTree() {
        List<Integer> expected = Arrays.asList();
        assertEquals(expected, tree.levelorder());
    }

    @Test
    public void testLevelorderSingleElement() {
        tree.add(50);

        List<Integer> expected = Arrays.asList(50);
        assertEquals(expected, tree.levelorder());
    }

    @Test
    public void testLevelorderLeftOnlyTree() {
        tree.add(50);
        tree.add(25);
        tree.add(10);
        tree.add(5);
        /*
            Tree structure:
                 50
                /
               25
              /
             10
            /
           5
        */
        List<Integer> expected = Arrays.asList(50, 25, 10, 5);
        assertEquals(expected, tree.levelorder());
    }

    @Test
    public void testLevelorderFullTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(60);
        tree.add(80);
        /*
            Tree structure:
                 50
                /  \
               25   75
              / \   / \
             10 30 60 80
        */
        List<Integer> expected = Arrays.asList(50, 25, 75, 10, 30, 60, 80);
        assertEquals(expected, tree.levelorder());
    }

    @Test
    public void testLevelorderComplexTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(80);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);
        tree.add(5);
        tree.add(20);
        tree.add(28);

        List<Integer> expected = Arrays.asList(50, 25, 75, 10, 30, 80, 5, 20, 27, 35, 28);
        assertEquals(expected, tree.levelorder());
    }

    @Test
    public void testLevelorderWithOnlyRightSubtree() {
        tree.add(50);
        tree.add(75);
        tree.add(60);
        tree.add(80);
        tree.add(55);
        tree.add(65);
        /*
            Tree structure:
                 50
                   \
                   75
                  /  \
                 60   80
                / \
               55  65
        */
        List<Integer> expected = Arrays.asList(50, 75, 60, 80, 55, 65);
        assertEquals(expected, tree.levelorder());
    }

    ///////////// Height method tests
    @Test
    public void testHeightEmptyTree() {
        assertEquals(-1, tree.height());
    }

    @Test
    public void testHeightSingleElementTree() {
        tree.add(50);
        assertEquals(0, tree.height());
    }

    @Test
    public void testHeightRightHeavyTree() {
        /*
            Tree structure:
                 50
                  \
                  75
                   \
                   100
                     \
                     125
        */
        tree.add(50);
        tree.add(75);
        tree.add(100);
        tree.add(125);
        assertEquals(3, tree.height());
    }

    @Test
    public void testHeightBalancedTree() {
        /*
            Tree structure:
                 50
                /  \
               25   75
              / \   / \
             10 30 60 80
        */
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(60);
        tree.add(80);
        assertEquals(2, tree.height());
    }

    /////////// Clear method tests
    @Test
    public void testClearEmptyTree() {
        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test
    public void testClearTreeWithSingleElement() {
        tree.add(50);
        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test
    public void testClearComplexTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(27);
        tree.add(35);
        tree.add(5);
        tree.add(20);
        tree.add(28);
        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    //////////// kLargest method tests
    @Test(expected = IllegalArgumentException.class)
    public void testKLargestWithNegativeK() {
        tree.kLargest(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKLargestWithKGreaterThanSizeOfEmptyTree() {
        tree.kLargest(4);
    }

    @Test
    public void testKLargestWithSingleElementTree() {
        tree.add(50);
        assertEquals(Arrays.asList(50), tree.kLargest(1));
    }

    @Test
    public void testKLargestWithMultipleElementsTree() {
        /*
            Tree structure:
                 50
                /  \
               25   75
              / \   / \
             10 30 60 80
        */
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(60);
        tree.add(80);
        assertEquals(Arrays.asList(75, 80), tree.kLargest(2));
    }

    @Test
    public void testKLargestWithAllElementsTree() {
        /*
            Tree structure:
                 50
                /  \
               25   75
              / \   / \
             10 30 60 80
        */
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(10);
        tree.add(30);
        tree.add(60);
        tree.add(80);
        assertEquals(Arrays.asList(10, 25, 30, 50, 60, 75, 80), tree.kLargest(7));
    }
}