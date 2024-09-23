import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * These tests (except one) are based on the provided tests and test higher capacity (20) queues.
 * One new test tests reallocation.
 *
 * @author Joseph Yoo
 * @version 1.0
 */
public class QueueExtraStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayQueue<String> array;
    private LinkedQueue<String> linked;

    @Before
    public void setup() {
        array = new ArrayQueue<>();
        linked = new LinkedQueue<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayQueue.INITIAL_CAPACITY],
            array.getBackingArray());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayEnqueue() {
        array.enqueue("0a");    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        array.enqueue("5a");
        array.enqueue("6a");
        array.enqueue("7a");
        array.enqueue("8a");
        array.enqueue("9a");
        array.enqueue("10a");
        array.enqueue("11a");
        array.enqueue("12a");
        array.enqueue("13a");
        array.enqueue("14a");
        array.enqueue("15a");
        array.enqueue("16a");
        array.enqueue("17a");
        array.enqueue("18a");
        array.enqueue("19a");

        assertEquals(20, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY*4];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";
        expected[9] = "9a";
        expected[10] = "10a";
        expected[11] = "11a";
        expected[12] = "12a";
        expected[13] = "13a";
        expected[14] = "14a";
        expected[15] = "15a";
        expected[16] = "16a";
        expected[17] = "17a";
        expected[18] = "18a";
        expected[19] = "19a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPop() {
        String temp = "0a";

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        array.enqueue("5a");
        array.enqueue("6a");
        array.enqueue("7a");
        array.enqueue("8a");
        array.enqueue("9a");
        array.enqueue("10a");
        array.enqueue("11a");
        array.enqueue("12a");
        array.enqueue("13a");
        array.enqueue("14a");
        array.enqueue("15a");
        array.enqueue("16a");
        array.enqueue("17a");
        array.enqueue("18a");
        array.enqueue("19a");

        assertEquals(20, array.size());

        assertSame(temp, array.dequeue());

        assertEquals(19, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY*4];
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";
        expected[9] = "9a";
        expected[10] = "10a";
        expected[11] = "11a";
        expected[12] = "12a";
        expected[13] = "13a";
        expected[14] = "14a";
        expected[15] = "15a";
        expected[16] = "16a";
        expected[17] = "17a";
        expected[18] = "18a";
        expected[19] = "19a";
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPeek() {
        String temp = "0a";

        array.enqueue(temp);    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        array.enqueue("5a");
        array.enqueue("6a");
        array.enqueue("7a");
        array.enqueue("8a");
        array.enqueue("9a");
        array.enqueue("10a");
        array.enqueue("11a");
        array.enqueue("12a");
        array.enqueue("13a");
        array.enqueue("14a");
        array.enqueue("15a");
        array.enqueue("16a");
        array.enqueue("17a");
        array.enqueue("18a");
        array.enqueue("19a");

        assertEquals(20, array.size());

        assertSame(temp, array.peek());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedEnqueue() {
        linked.enqueue("0a");   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");   // 0a, 1a, 2a, 3a
        linked.enqueue("4a");   // 0a, 1a, 2a, 3a, 4a
        linked.enqueue("5a");
        linked.enqueue("6a");
        linked.enqueue("7a");
        linked.enqueue("8a");
        linked.enqueue("9a");
        linked.enqueue("10a");
        linked.enqueue("11a");
        linked.enqueue("12a");
        linked.enqueue("13a");
        linked.enqueue("14a");
        linked.enqueue("15a");
        linked.enqueue("16a");
        linked.enqueue("17a");
        linked.enqueue("18a");
        linked.enqueue("19a");

        assertEquals(20, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("0a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("1a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("4a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("5a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("6a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("7a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("8a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("9a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("10a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("11a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("12a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("13a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("14a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("15a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("16a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("17a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("18a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("19a", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeue() {
        String temp = "0a";

        linked.enqueue(temp);   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        linked.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        linked.enqueue("6a");
        linked.enqueue("7a");
        linked.enqueue("8a");
        linked.enqueue("9a");
        linked.enqueue("10a");
        linked.enqueue("11a");
        linked.enqueue("12a");
        linked.enqueue("13a");
        linked.enqueue("14a");
        linked.enqueue("15a");
        linked.enqueue("16a");
        linked.enqueue("17a");
        linked.enqueue("18a");
        linked.enqueue("19a");

        assertEquals(20, linked.size());

        assertSame(temp, linked.dequeue()); // 1a, 2a, 3a, 4a, 5a

        assertEquals(19, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertEquals("1a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("2a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("3a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("4a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("5a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("6a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("7a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("8a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("9a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("10a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("11a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("12a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("13a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("14a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("15a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("16a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("17a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("18a", cur.getData());

        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals("19a", cur.getData());

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPeek() {
        String temp = "0a";

        linked.enqueue(temp);   // 0a
        linked.enqueue("1a");   // 0a, 1a
        linked.enqueue("2a");   // 0a, 1a, 2a
        linked.enqueue("3a");    // 0a, 1a, 2a, 3a
        linked.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        linked.enqueue("5a");    // 0a, 1a, 2a, 3a, 4a, 5a
        linked.enqueue("6a");
        linked.enqueue("7a");
        linked.enqueue("8a");
        linked.enqueue("9a");
        linked.enqueue("10a");
        linked.enqueue("11a");
        linked.enqueue("12a");
        linked.enqueue("13a");
        linked.enqueue("14a");
        linked.enqueue("15a");
        linked.enqueue("16a");
        linked.enqueue("17a");
        linked.enqueue("18a");
        linked.enqueue("19a");

        assertEquals(20, linked.size());

        assertSame(temp, linked.peek());
    }

    @Test(timeout = TIMEOUT)
    public void testReallocation() {
        array.enqueue("1e");
        array.enqueue("1d");
        array.enqueue("1c");
        array.enqueue("1b");
        array.enqueue("1a");
        array.enqueue("2a");
        array.enqueue("3a");
        array.enqueue("4a");
        array.enqueue("5a");
        assertEquals(9, array.size());

        assertEquals("1e", array.dequeue());
        assertEquals("1d", array.dequeue());
        assertEquals("1c", array.dequeue());
        assertEquals("1b", array.dequeue());
        assertEquals(5, array.size());

        array.enqueue("6a");
        array.enqueue("7a");
        array.enqueue("8a");
        array.enqueue("9a");
        assertEquals(9, array.size());

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY];
        expected[0] = "6a";
        expected[1] = "7a";
        expected[2] = "8a";
        expected[3] = "9a";
        expected[4] = "1a";
        expected[5] = "2a";
        expected[6] = "3a";
        expected[7] = "4a";
        expected[8] = "5a";

        assertArrayEquals(expected, array.getBackingArray());

        // trigger reallocation
        array.enqueue("10a");
        assertEquals(10, array.size());

        expected = new Object[ArrayQueue.INITIAL_CAPACITY*2];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        expected[5] = "6a";
        expected[6] = "7a";
        expected[7] = "8a";
        expected[8] = "9a";
        expected[9] = "10a";

        assertArrayEquals(expected, array.getBackingArray());
    }
}
