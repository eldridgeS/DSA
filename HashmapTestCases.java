import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class HashmapTestCases {

    private static final int TIMEOUT = 200;
    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testDuplicatePut() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertEquals(map.put(0, "A1"), "A");
    }

    @Test(timeout = TIMEOUT)
    public void testSameIndexPut() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(13, "A1"));
        assertNull(map.put(26, "A2"));
        assertEquals(map.size(), 3);
        assertEquals(map.getTable()[0].getValue(), "A2");
    }

    @Test(timeout = TIMEOUT)
    public void testPutDuplicate() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(14, "A1"));
        assertNull(map.put(27, "A2"));
        assertEquals(map.size(), 3);

        assertEquals(map.put(1, "B"), "A");
        assertEquals(map.getTable()[1].getValue(), "A2");
        assertEquals(map.getTable()[1].getNext().getNext().getValue(), "B");
    }

    @Test(timeout = TIMEOUT)
    public void testPutResize() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));
        assertNull(map.put(6, "F"));
        assertNull(map.put(7, "G"));
        assertNull(map.put(8, "H"));
        assertEquals(map.size(), 8);
        assertEquals(map.getTable().length, 13);
        //add element triggering resize
        assertNull(map.put(9, "I"));
        assertEquals(map.size(), 9);
        assertEquals(map.getTable().length, 27);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveChain() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(13, "B"));
        assertNull(map.put(26, "C"));
        assertEquals(map.size(), 3);

        assertEquals(map.remove(13), "B");
        assertEquals(map.size(), 2);
        assertEquals(map.getTable()[0].getValue(), "C");
        assertEquals(map.getTable()[0].getNext().getValue(), "A");

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFrontOfChain() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(13, "B"));
        assertNull(map.put(26, "C"));
        assertEquals(map.size(), 3);
        assertEquals(map.remove(26), "C");
        assertEquals(map.size(), 2);
        assertEquals(map.getTable()[0].getValue(), "B");
        assertEquals(map.getTable()[0].getNext().getValue(), "A");
    }

    @Test(timeout = TIMEOUT)
    public void testGetTableNull() {
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        assertEquals(map.size(), 0);
        assertArrayEquals(map.getTable(), expected);

    }

    @Test(timeout = TIMEOUT)
    public void testDuplicateGet() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        map.put(0, "A1");
        assertEquals("A1", map.get(0));


    }

    @Test(timeout = TIMEOUT)
    public void testSameIndexGet() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(13, "A1"));
        assertNull(map.put(26, "A2"));
        assertEquals(map.size(), 3);

        assertEquals("A", map.get(0));
        assertEquals("A1", map.get(13));
        assertEquals("A2", map.get(26));
    }

    @Test(timeout = TIMEOUT)
    public void testNullSet() {
        Set<Object> expected = new HashSet<>();
        assertEquals(expected, map.keySet());
    }
}