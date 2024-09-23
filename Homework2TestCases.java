import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class Homework2TestCases {
    private ArrayStack<String> arrayStack;
    private LinkedStack<String> linkedStack;
    private ArrayQueue<String> arrayQueue;
    private LinkedQueue<String> linkedQueue;

    @Before
    public void setup() {
        arrayStack = new ArrayStack<>();
        linkedStack = new LinkedStack<>();
        arrayQueue = new ArrayQueue<>();
        linkedQueue = new LinkedQueue<>();
    }

    ////////// Array Stack Test Cases
    @Test(expected = IllegalArgumentException.class)
    public void testArrayStackPushNull() {
        arrayStack.push(null);
    }

    @Test
    public void testArrayStackPushOneElement() {
        arrayStack.push("A");
        assertEquals(1, arrayStack.size());
        assertEquals("A", arrayStack.peek());
        assertArrayEquals(new Object[]{"A", null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPushFourElements() {
        arrayStack.push("A");
        arrayStack.push("B");
        arrayStack.push("C");
        arrayStack.push("D");
        assertEquals(4, arrayStack.size());
        assertEquals("D", arrayStack.peek());
        assertArrayEquals(new Object[]{"A", "B", "C", "D", null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPushBeyondInitialCapacity() {
        for (int i = 0; i < 10; i++) {
            arrayStack.push("A" + i);
        }
        assertEquals(10, arrayStack.size());
        Object[] backingArr = arrayStack.getBackingArray();
        assertEquals(18, backingArr.length);
        assertEquals("A9", arrayStack.peek());
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPushResizingTwice() {
        for (int i = 0; i < 20; i++) {
            arrayStack.push("A" + i);
        }
        assertEquals(20, arrayStack.size());
        Object[] backingArr = arrayStack.getBackingArray();
        assertEquals(36, backingArr.length);
        assertEquals("A19", arrayStack.peek());
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "A13", "A14", "A15", "A16", "A17", "A18", "A19", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testArrayStackPopEmpty() {
        arrayStack.pop();
    }

    @Test
    public void testArrayStackPopOneElement() {
        arrayStack.push("A");
        String popped = arrayStack.pop();
        assertEquals("A", popped);
        assertEquals(0, arrayStack.size());
        assertArrayEquals(new Object[]{null, null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPopSizeSix() {
        for (int i = 0; i < 6; i++) {
            arrayStack.push("A" + i);
        }
        String popped = arrayStack.pop();
        assertEquals("A5", popped);
        assertEquals(5, arrayStack.size());
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPopTwoElementsWhenSizeNine() {
        for (int i = 0; i < 9; i++) {
            arrayStack.push("A" + i);
        }
        String popped1 = arrayStack.pop();
        String popped2 = arrayStack.pop();
        assertEquals("A8", popped1);
        assertEquals("A7", popped2);
        assertEquals(7, arrayStack.size());
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", "A5", "A6", null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPopAfterResize() {
        for (int i = 0; i < 10; i++) {
            arrayStack.push("A" + i);
        }
        String popped = arrayStack.pop();
        assertEquals("A9", popped);
        assertEquals(9, arrayStack.size());
        Object[] backingArr = arrayStack.getBackingArray();
        assertEquals(18, backingArr.length);
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", null, null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPopMultipleAfterResize() {
        for (int i = 0; i < 20; i++) {
            arrayStack.push("A" + i);
        }
        for (int i = 0; i < 10; i++) {
            arrayStack.pop();
        }
        assertEquals(10, arrayStack.size());
        Object[] backingArr = arrayStack.getBackingArray();
        assertEquals(36, backingArr.length);
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testArrayStackPeekEmpty() {
        arrayStack.peek();
    }

    @Test
    public void testArrayStackPeekOneElement() {
        arrayStack.push("A");
        String top = arrayStack.peek();
        assertEquals("A", top);
        assertEquals(1, arrayStack.size());
        assertArrayEquals(new Object[]{"A", null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPeekMultipleElements() {
        arrayStack.push("A");
        arrayStack.push("B");
        arrayStack.push("C");
        String top = arrayStack.peek();
        assertEquals("C", top);
        assertEquals(3, arrayStack.size());
        assertArrayEquals(new Object[]{"A", "B", "C", null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPeekAfterResizing() {
        for (int i = 0; i < 10; i++) {
            arrayStack.push("A" + i);
        }
        String top = arrayStack.peek();
        assertEquals("A9", top);
        assertEquals(10, arrayStack.size());
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    @Test
    public void testArrayStackPeekDoesNotChangeStack() {
        for (int i = 0; i < 10; i++) {
            arrayStack.push("A" + i);
        }
        for (int i = 0; i < 5; i++) {
            arrayStack.peek();
        }
        assertEquals(10, arrayStack.size());
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", null, null, null, null, null, null, null, null}, arrayStack.getBackingArray());
    }

    //////////////////// Linked Stack Tests
    @Test(expected = IllegalArgumentException.class)
    public void testLinkedStackPushNull() {
        linkedStack.push(null);
    }

    @Test
    public void testLinkedStackPushOneElement() {
        linkedStack.push("A");
        assertEquals(1, linkedStack.size());
        assertEquals("A", linkedStack.peek());
        assertNotNull(linkedStack.getHead());
        assertEquals("A", linkedStack.getHead().getData());
    }

    @Test
    public void testLinkedStackPushMultipleElements() {
        linkedStack.push("A");
        linkedStack.push("B");
        linkedStack.push("C");
        linkedStack.push("D");

        assertEquals("D", linkedStack.peek());
        assertNotNull(linkedStack.getHead());
        assertEquals("D", linkedStack.getHead().getData());

        LinkedNode<String> current = linkedStack.getHead();
        assertEquals("D", current.getData());

        current = current.getNext();
        assertEquals("C", current.getData());

        current = current.getNext();
        assertEquals("B", current.getData());

        current = current.getNext();
        assertEquals("A", current.getData());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testLinkedStackPopEmpty() {
        linkedStack.pop();
    }

    @Test
    public void testLinkedStackPopOneElement() {
        linkedStack.push("A");
        assertEquals("A", linkedStack.pop());
        assertEquals(0, linkedStack.size());
        assertNull(linkedStack.getHead());
    }

    @Test
    public void testLinkedStackPopMultipleElements() {
        linkedStack.push("A");
        linkedStack.push("B");
        linkedStack.push("C");
        linkedStack.push("D");
        assertEquals("D", linkedStack.pop());
        assertEquals("C", linkedStack.pop());
        assertEquals(2, linkedStack.size());
        assertEquals("B", linkedStack.peek());
        assertNotNull(linkedStack.getHead());
        assertEquals("B", linkedStack.getHead().getData());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testLinkedStackPeekEmpty() {
        linkedStack.peek();
    }

    @Test
    public void testLinkedStackPeekOneElement() {
        linkedStack.push("A");
        assertEquals("A", linkedStack.peek());
        assertEquals(1, linkedStack.size());
        assertNotNull(linkedStack.getHead());
        assertEquals("A", linkedStack.getHead().getData());
    }

    @Test
    public void testLinkedStackPeekMultipleElements() {
        linkedStack.push("A");
        linkedStack.push("B");
        linkedStack.push("C");
        linkedStack.push("D");
        assertEquals("D", linkedStack.peek());
        assertEquals(4, linkedStack.size());
        assertNotNull(linkedStack.getHead());
        assertEquals("D", linkedStack.getHead().getData());
    }

    @Test
    public void testLinkedStackPopAndPeek() {
        linkedStack.push("A");
        linkedStack.push("B");
        assertEquals("B", linkedStack.peek());
        assertEquals("B", linkedStack.pop());
        assertEquals("A", linkedStack.peek());
        assertEquals(1, linkedStack.size());
        assertNotNull(linkedStack.getHead());
        assertEquals("A", linkedStack.getHead().getData());
    }

    ///////////////////////// Array Queue Tests
    @Test(expected = IllegalArgumentException.class)
    public void testArrayQueueEnqueueNull() {
        arrayQueue.enqueue(null);
    }

    @Test
    public void testArrayQueueEnqueueOneElement() {
        arrayQueue.enqueue("A");
        assertEquals(1, arrayQueue.size());
        assertEquals("A", arrayQueue.peek());
        assertEquals(0, arrayQueue.getFront());
        assertEquals(1, arrayQueue.size());
        assertArrayEquals(new Object[]{"A", null, null, null, null, null, null, null, null}, arrayQueue.getBackingArray());
    }

    @Test
    public void testArrayQueueEnqueueMultipleElements() {
        arrayQueue.enqueue("A");
        arrayQueue.enqueue("B");
        arrayQueue.enqueue("C");
        assertEquals(3, arrayQueue.size());
        assertEquals("A", arrayQueue.peek());
        assertEquals(0, arrayQueue.getFront());
        assertEquals(3, arrayQueue.size());
        assertArrayEquals(new Object[]{"A", "B", "C", null, null, null, null, null, null}, arrayQueue.getBackingArray());
    }

    @Test
    public void testArrayQueueEnqueueResize() {
        for (int i = 0; i < 11; i++) {
            arrayQueue.enqueue("A" + i);
        }
        assertEquals(11, arrayQueue.size());
        assertEquals("A0", arrayQueue.peek());
        assertEquals(0, arrayQueue.getFront());
        assertEquals(11, arrayQueue.size());
        assertArrayEquals(new Object[]{"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", null, null, null, null, null, null, null}, arrayQueue.getBackingArray());
    }

    @Test
    public void testArrayQueueEnqueueResizeAndDequeue() {
        for (int i = 0; i < 11; i++) {
            arrayQueue.enqueue("A" + i);
        }
        for (int i = 0; i < 3; i++) {
            arrayQueue.dequeue();
        }
        assertEquals(8, arrayQueue.size());
        assertEquals("A3", arrayQueue.peek());
        assertEquals(3, arrayQueue.getFront());
        assertEquals(8, arrayQueue.size());
        assertArrayEquals(new Object[]{null, null, null, "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", null, null, null, null, null, null, null}, arrayQueue.getBackingArray());
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayQueueDequeueEmpty() {
        arrayQueue.dequeue();
    }

    @Test
    public void testArrayQueueDequeueOneElement() {
        arrayQueue.enqueue("A");
        assertEquals("A", arrayQueue.dequeue());
        assertEquals(0, arrayQueue.size());
        assertArrayEquals(new Object[]{null, null, null, null, null, null, null, null, null}, arrayQueue.getBackingArray());
    }

    @Test
    public void testArrayQueueDequeueMultipleElements() {
        arrayQueue.enqueue("A");
        arrayQueue.enqueue("B");
        arrayQueue.enqueue("C");
        assertEquals("A", arrayQueue.dequeue());
        assertEquals(2, arrayQueue.size());
        assertEquals("B", arrayQueue.peek());
        assertEquals(2, arrayQueue.size());
        assertArrayEquals(new Object[]{null, "B", "C", null, null, null, null, null, null}, arrayQueue.getBackingArray());
    }

    @Test
    public void testArrayQueueDequeueAfterResize() {
        for (int i = 0; i < 11; i++) {
            arrayQueue.enqueue("A" + i);
        }
        assertEquals(11, arrayQueue.size());
        for (int i = 0; i < 5; i++) {
            arrayQueue.dequeue();
        }
        assertEquals(6, arrayQueue.size());
        assertEquals("A5", arrayQueue.peek());
        assertEquals(5, arrayQueue.getFront());
        assertEquals(6, arrayQueue.size());
        assertArrayEquals(new Object[]{null, null, null, null, null, "A5", "A6", "A7", "A8", "A9", "A10", null, null, null, null, null, null, null}, arrayQueue.getBackingArray());
    }

    @Test
    public void testArrayQueueEnqueueAndDequeue() {
        arrayQueue.enqueue("A");
        arrayQueue.enqueue("B");
        arrayQueue.enqueue("C");
        assertEquals(3, arrayQueue.size());
        assertEquals("A", arrayQueue.peek());

        assertEquals("A", arrayQueue.dequeue());
        assertEquals(2, arrayQueue.size());

        for (int i = 0; i < 9; i++) {
            arrayQueue.enqueue("D" + i);
        }
        assertEquals(11, arrayQueue.size());
        assertEquals("B", arrayQueue.peek());
        assertArrayEquals(new Object[]{"B", "C", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", null, null, null, null, null, null, null}, arrayQueue.getBackingArray());
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayQueuePeekEmpty() {
        arrayQueue.peek();
    }

    @Test
    public void testArrayQueuePeekOneElement() {
        arrayQueue.enqueue("A");
        assertEquals("A", arrayQueue.peek());
    }

    @Test
    public void testArrayQueuePeekMultipleElements() {
        arrayQueue.enqueue("A");
        arrayQueue.enqueue("B");
        arrayQueue.enqueue("C");
        assertEquals("A", arrayQueue.peek());
    }

    @Test
    public void testArrayQueuePeekAfterDequeue() {
        arrayQueue.enqueue("A");
        arrayQueue.enqueue("B");
        arrayQueue.enqueue("C");
        arrayQueue.dequeue();
        assertEquals("B", arrayQueue.peek());
    }

    ///////////// Linked Queue Methods
    @Test(expected = IllegalArgumentException.class)
    public void testLinkedQueueEnqueueNull() {
        linkedQueue.enqueue(null);
    }

    @Test
    public void testLinkedQueueEnqueueOneElement() {
        linkedQueue.enqueue("A");
        assertEquals(1, linkedQueue.size());
        assertEquals("A", linkedQueue.peek());
        assertEquals("A", linkedQueue.getHead().getData());
    }

    @Test
    public void testLinkedQueueEnqueueMultipleElements() {
        linkedQueue.enqueue("A");
        linkedQueue.enqueue("B");
        linkedQueue.enqueue("C");
        assertEquals(3, linkedQueue.size());
        assertEquals("A", linkedQueue.peek());
        assertEquals("A", linkedQueue.getHead().getData());
        assertEquals("B", linkedQueue.getHead().getNext().getData());
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedQueueDequeueEmpty() {
        linkedQueue.dequeue();
    }

    @Test
    public void testLinkedQueueDequeueOneElement() {
        linkedQueue.enqueue("A");
        assertEquals("A", linkedQueue.dequeue());
        assertEquals(0, linkedQueue.size());
        assertNull(linkedQueue.getHead());
    }

    @Test
    public void testLinkedQueueDequeueMultipleElements() {
        linkedQueue.enqueue("A");
        linkedQueue.enqueue("B");
        linkedQueue.enqueue("C");
        assertEquals("A", linkedQueue.dequeue());
        assertEquals(2, linkedQueue.size());
        assertEquals("B", linkedQueue.getHead().getData());
        assertEquals("B", linkedQueue.dequeue());
        assertEquals(1, linkedQueue.size());
        assertEquals("C", linkedQueue.getHead().getData());
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedQueuePeekEmpty() {
        linkedQueue.peek();
    }

    @Test
    public void testLinkedQueuePeekOneElement() {
        linkedQueue.enqueue("A");
        assertEquals("A", linkedQueue.peek());
        assertEquals(1, linkedQueue.size());
        assertEquals("A", linkedQueue.getHead().getData());
    }

    @Test
    public void testLinkedQueuePeekMultipleElements() {
        linkedQueue.enqueue("A");
        linkedQueue.enqueue("B");
        linkedQueue.enqueue("C");
        assertEquals("A", linkedQueue.peek());
        assertEquals(3, linkedQueue.size());
    }
}