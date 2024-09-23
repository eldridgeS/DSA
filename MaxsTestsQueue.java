import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

/**
 * 1. Test the enqueue operation when the ArrayQueue is filled to its initial capacity and resized.
 * 2. Test the dequeue operation and verify the state of the backing array after dequeuing two elements.
 * 3. Verify correct behavior of the enqueue operation after dequeueing elements, ensuring new elements are placed correctly.
 * 4. Test the enqueue operation in the presence of empty spots created by previous dequeues.
 * 5. Test resizing when the front pointer is not at zero due to prior dequeues.
 * 6. Verify that the front pointer is reset to zero when the queue becomes empty.
 *
 * @author Max Budnick
 * @version 1.0
 */
public class MaxsTestsQueue {

    private static final int TIMEOUT = 20000;
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
    public void testArrayEnqueueFull() {
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

        assertEquals(12, array.size());

        Object[] expected = new Object[18];
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

        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void dequeueTwoElements() {
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

        assertEquals(12, array.size());
        assertEquals(0, array.getFront());

        array.dequeue();
        array.dequeue();

        assertEquals(10, array.size());

        Object[] dequeueExpected = new Object[18];
        dequeueExpected[0] = null;
        dequeueExpected[1] = null;
        dequeueExpected[2] = "2a";
        dequeueExpected[3] = "3a";
        dequeueExpected[4] = "4a";
        dequeueExpected[5] = "5a";
        dequeueExpected[6] = "6a";
        dequeueExpected[7] = "7a";
        dequeueExpected[8] = "8a";
        dequeueExpected[9] = "9a";
        dequeueExpected[10] = "10a";
        dequeueExpected[11] = "11a";

        assertEquals(dequeueExpected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void enqueueCorrectly() {
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

        assertEquals(12, array.size());
        assertEquals(0, array.getFront());

        array.dequeue();
        array.dequeue();

        array.enqueue("12a");
        assertEquals(11, array.size());
        assertEquals(2, array.getFront());

        Object[] dequeueExpected2 = new Object[18];
        dequeueExpected2[0] = null;
        dequeueExpected2[1] = null;
        dequeueExpected2[2] = "2a";
        dequeueExpected2[3] = "3a";
        dequeueExpected2[4] = "4a";
        dequeueExpected2[5] = "5a";
        dequeueExpected2[6] = "6a";
        dequeueExpected2[7] = "7a";
        dequeueExpected2[8] = "8a";
        dequeueExpected2[9] = "9a";
        dequeueExpected2[10] = "10a";
        dequeueExpected2[11] = "11a";
        dequeueExpected2[12] = "12a";

        assertEquals(dequeueExpected2, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void enqueueInEmptySpots() {
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

        assertEquals(12, array.size());
        assertEquals(0, array.getFront());

        array.dequeue();
        array.dequeue();

        array.enqueue("12a");
        assertEquals(11, array.size());
        assertEquals(2, array.getFront());

        array.enqueue("13a");
        array.enqueue("14a");
        array.enqueue("15a");
        array.enqueue("16a");
        array.enqueue("17a");
        array.enqueue("18a");
        array.enqueue("19a");

        assertEquals(18, array.size());
        assertEquals(2, array.getFront());

        //System.out.println("The length of the array: " + array.getBackingArray().length);

        Object[] dequeueExpected3 = new Object[18];
        dequeueExpected3[0] = "18a";
        dequeueExpected3[1] = "19a";
        dequeueExpected3[2] = "2a";
        dequeueExpected3[3] = "3a";
        dequeueExpected3[4] = "4a";
        dequeueExpected3[5] = "5a";
        dequeueExpected3[6] = "6a";
        dequeueExpected3[7] = "7a";
        dequeueExpected3[8] = "8a";
        dequeueExpected3[9] = "9a";
        dequeueExpected3[10] = "10a";
        dequeueExpected3[11] = "11a";
        dequeueExpected3[12] = "12a";
        dequeueExpected3[13] = "13a";
        dequeueExpected3[14] = "14a";
        dequeueExpected3[15] = "15a";
        dequeueExpected3[16] = "16a";
        dequeueExpected3[17] = "17a";

        assertEquals(dequeueExpected3, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void enqueueResizeArrayWithNonZeroFront() {
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

        assertEquals(12, array.size());
        assertEquals(0, array.getFront());

        array.dequeue();
        array.dequeue();

        array.enqueue("12a");

        assertEquals(11, array.size());
        assertEquals(2, array.getFront());

        array.enqueue("13a");
        array.enqueue("14a");
        array.enqueue("15a");
        array.enqueue("16a");
        array.enqueue("17a");
        array.enqueue("18a");
        array.enqueue("19a");

        assertEquals(18, array.size());
        assertEquals(2, array.getFront());

        array.enqueue("20a");

        assertEquals(19, array.size());

        Object[] dequeueExpected4 = new Object[36];
        dequeueExpected4[0] = "2a";
        dequeueExpected4[1] = "3a";
        dequeueExpected4[2] = "4a";
        dequeueExpected4[3] = "5a";
        dequeueExpected4[4] = "6a";
        dequeueExpected4[5] = "7a";
        dequeueExpected4[6] = "8a";
        dequeueExpected4[7] = "9a";
        dequeueExpected4[8] = "10a";
        dequeueExpected4[9] = "11a";
        dequeueExpected4[10] = "12a";
        dequeueExpected4[11] = "13a";
        dequeueExpected4[12] = "14a";
        dequeueExpected4[13] = "15a";
        dequeueExpected4[14] = "16a";
        dequeueExpected4[15] = "17a";
        dequeueExpected4[16] = "18a";
        dequeueExpected4[17] = "19a";
        dequeueExpected4[18] = "20a";

        assertEquals(dequeueExpected4, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void resetFrontToZeroWhenEmpty() {
        array.enqueue("0a");    // 0a
        array.enqueue("1a");    // 0a, 1a
        array.enqueue("2a");    // 0a, 1a, 2a
        array.enqueue("3a");    // 0a, 1a, 2a, 3a
        array.enqueue("4a");    // 0a, 1a, 2a, 3a, 4a
        array.enqueue("5a");
        array.enqueue("6a");
        array.enqueue("7a");
        array.enqueue("8a");

        assertEquals(9, array.size());
        Object[] expected = new Object[9];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";
        assertArrayEquals(expected, array.getBackingArray());

        array.dequeue();
        array.dequeue();
        array.dequeue();
        array.dequeue();

        Object[] expected2 = new Object[9];
        expected2[0] = null;
        expected2[1] = null;
        expected2[2] = null;
        expected2[3] = null;
        expected2[4] = "4a";
        expected2[5] = "5a";
        expected2[6] = "6a";
        expected2[7] = "7a";
        expected2[8] = "8a";
        assertArrayEquals(expected2, array.getBackingArray());
        assertEquals(4, array.getFront());
        assertEquals(5, array.size());

        array.dequeue();
        array.dequeue();
        array.dequeue();
        array.dequeue();
        array.dequeue();

        Object[] expected3 = new Object[9];
        expected3[0] = null;
        expected3[1] = null;
        expected3[2] = null;
        expected3[3] = null;
        expected3[4] = null;
        expected3[5] = null;
        expected3[6] = null;
        expected3[7] = null;
        expected3[8] = null;
        assertArrayEquals(expected3, array.getBackingArray());
        assertEquals(0, array.getFront());








    }
}
