import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class AdditionalHashTests {
    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<>();
    }


    @Test
    public void testHighVolumeSequentialInsertsAndDeletes() {
        int entries = 1000;
        for (int i = 0; i < entries; i++) {
            map.put(i, "Value" + i);
        }
        assertEquals(entries, map.size());
        for (int i = 0; i < entries; i++) {
            assertEquals("Value" + i, map.remove(i));
        }
        assertEquals(0, map.size());
    }


    @Test
    public void testDifferentKeysSameValue() {
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        assertEquals("1", map.get(1));
        assertEquals("2", map.get(2));
        assertEquals("3", map.get(3));
        map.remove(2);
        assertTrue(map.containsKey(1) && map.containsKey(3));
        assertFalse(map.containsKey(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnError() {
        assertNull(map.get(999));
        assertNull(map.remove(999));


    }

    @Test
    public void testResizeBehavior() {
        int initialCapacity = 13;
        double loadFactor = 0.6;
        int threshold = (int) (initialCapacity * loadFactor);


        for (int i = 0; i < threshold; i++) {
            map.put(i, "Value" + i);
        }

        for (int i = 0; i < threshold; i++) {
            assertEquals("Value" + i, map.get(i));
        }

        map.put(threshold, "Value" + threshold);

        assertEquals("Value" + threshold, map.get(threshold));

        int additionalElements = 100;
        for (int i = threshold + 1; i <= threshold + additionalElements; i++) {
            map.put(i, "Value" + i);
        }

        for (int i = 0; i <= threshold + additionalElements; i++) {
            assertEquals("Value" + i, map.get(i));
        }

        assertEquals(threshold + 1 + additionalElements, map.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testMapStaysIntactAfterFailedRemovals() {
        map.put(1, "One");
        map.put(2, "Two");
        assertNull(map.remove(3));  // Non-existent key
        assertEquals(2, map.size());
        assertTrue(map.containsKey(1) && map.containsKey(2));
    }

    @Test
    public void testNegativeKeys() {

        map.put(-1, "Negative One");
        map.put(-2, "Negative Two");
        map.put(-100, "Negative Hundred");


        assertEquals("Negative One", map.get(-1));
        assertEquals("Negative Two", map.get(-2));
        assertEquals("Negative Hundred", map.get(-100));


        assertEquals("Negative One", map.remove(-1));
        try {
            map.get(-1);
            fail("Expected NoSuchElementException.");
        } catch (NoSuchElementException e) {
        }

        assertTrue(map.containsKey(-100));

        assertEquals(2, map.size());

        for (int i = -3; i >= -10; i--) {
            map.put(i, "Value " + i);
        }
        for (int i = -3; i >= -10; i--) {
            assertEquals("Value " + i, map.remove(i));
        }

        assertEquals(2, map.size());
    }


}
