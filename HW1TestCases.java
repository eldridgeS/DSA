import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class HW1TestCases {

    private static final int TIMEOUT = 200;
    private SinglyLinkedList<String> list;

    @Before
    public void setUp() {
        list = new SinglyLinkedList<>();
    }
    @Test(timeout = TIMEOUT)
    public void testRemove2() {
        list.addAtIndex(0, "0a");
        list.removeAtIndex(0);
        assertNull(list.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove3() {
        list.addAtIndex(0, "0a");
        list.removeFromBack();
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove4() {
        list.addAtIndex(0, "0a");
        list.removeFromFront();
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLastOccurrence2() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1b");
        list.addAtIndex(2, "2c");
        list.removeLastOccurrence("0a");
        assertEquals(list.getHead().getData(), "1b");
    }

    @Test(timeout = TIMEOUT)
    public void testLastOccurrence3() {
        list.addAtIndex(0, "0a");
        list.addAtIndex(1, "1b");
        list.addAtIndex(2, "2c");
        list.addAtIndex(3, "0a");
        list.removeLastOccurrence("0a");
        assertEquals(list.getTail().getData(), "2c");
    }

    @Test(timeout = TIMEOUT)
    public void testLastOccurrence4() {
        list.addAtIndex(0, "0a");
        list.removeLastOccurrence("0a");
        assertNull(list.getHead());
        assertNull(list.getTail());
    }
}