import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Homework4TestCases {

    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<>();
    }

    //////////////// Constructor tests
    @Test
    public void testDefaultConstructor() {
        ExternalChainingHashMap<Integer, String> defaultMap = new ExternalChainingHashMap<>();
        assertEquals(0, defaultMap.size());
        assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY, defaultMap.getTable().length);
    }

    @Test
    public void testInitialCapacityConstructor() {
        ExternalChainingHashMap<Integer, String> mapWithInitialCapacity = new ExternalChainingHashMap<>(20);
        assertEquals(0, mapWithInitialCapacity.size());
        assertEquals(20, mapWithInitialCapacity.getTable().length);
    }

    //////////////// Put method tests
    @Test(expected = IllegalArgumentException.class)
    public void testPut_NullKey() {
        map.put(null, "Null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPut_NullValue() {
        map.put(0, null);
    }

    @Test
    public void testPut_SingleKeyValuePair() {
        assertNull(map.put(5, "Value5"));

        assertEquals(1, map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expected[5] = new ExternalChainingMapEntry<>(5, "Value5");

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPut_MultipleKeyValuePairs() {
        assertNull(map.put(1, "Value1"));
        assertNull(map.put(3, "Value3"));
        assertNull(map.put(8, "Value8"));
        assertNull(map.put(15, "Value15"));
        assertNull(map.put(19, "Value19"));
        assertNull(map.put(25, "Value25"));

        assertEquals(6, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expected[1] = new ExternalChainingMapEntry<>(1, "Value1");
        expected[3] = new ExternalChainingMapEntry<>(3, "Value3");
        expected[8] = new ExternalChainingMapEntry<>(8, "Value8");
        expected[2] = new ExternalChainingMapEntry<>(15, "Value15");
        expected[6] = new ExternalChainingMapEntry<>(19, "Value19");
        expected[12] = new ExternalChainingMapEntry<>(25, "Value25");

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPut_Collisions() {
        assertNull(map.put(1, "Value1"));
        assertNull(map.put(14, "Value14"));
        assertNull(map.put(25, "Value25"));

        assertEquals(3, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];

        int index1 = Math.abs(1 % ExternalChainingHashMap.INITIAL_CAPACITY);
        int index14 = Math.abs(14 % ExternalChainingHashMap.INITIAL_CAPACITY);
        int index25 = Math.abs(25 % ExternalChainingHashMap.INITIAL_CAPACITY);

        ExternalChainingMapEntry<Integer, String> entry1 = new ExternalChainingMapEntry<>(1, "Value1");
        ExternalChainingMapEntry<Integer, String> entry14 = new ExternalChainingMapEntry<>(14, "Value14");
        ExternalChainingMapEntry<Integer, String> entry25 = new ExternalChainingMapEntry<>(25, "Value25");

        entry25.setNext(entry14);
        entry14.setNext(entry1);

        expected[index1] = entry1;
        expected[index14] = entry14;
        expected[index25] = entry25;

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPut_DuplicateKey() {
        assertNull(map.put(1, "Value1"));
        assertNull(map.put(2, "Value2"));
        assertNull(map.put(3, "Value3"));

        assertEquals("Value3", map.put(3, "D"));

        assertEquals(3, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expected[1] = new ExternalChainingMapEntry<>(1, "Value1");
        expected[2] = new ExternalChainingMapEntry<>(2, "Value2");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPut_DuplicateKeyWithAdditionalKeys() {
        assertNull(map.put(1, "Value1"));
        assertNull(map.put(2, "Value2"));
        assertNull(map.put(3, "Value3"));
        assertNull(map.put(16, "Value16"));
        assertNull(map.put(29, "Value29"));
        assertEquals("Value3", map.put(3, "D"));

        assertEquals(5, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap.INITIAL_CAPACITY];
        expected[1] = new ExternalChainingMapEntry<>(1, "Value1");
        expected[2] = new ExternalChainingMapEntry<>(2, "Value2");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[3] = new ExternalChainingMapEntry<>(16, "Value16", expected[3]);
        expected[3] = new ExternalChainingMapEntry<>(29, "Value29", expected[3]);

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testResize() {
        assertNull(map.put(1, "Value1"));
        assertNull(map.put(2, "Value2"));
        assertNull(map.put(3, "Value3"));
        assertNull(map.put(4, "Value4"));
        assertNull(map.put(5, "Value5"));
        assertNull(map.put(6, "Value6"));
        assertNull(map.put(7, "Value7"));
        assertNull(map.put(8, "Value8"));

        assertEquals(8, map.size());
        assertEquals(13, map.getTable().length);

        assertNull(map.put(9, "Value9"));

        assertEquals(9, map.size());
        assertEquals(27, map.getTable().length);

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[27];
        expected[1] = new ExternalChainingMapEntry<>(1, "Value1");
        expected[2] = new ExternalChainingMapEntry<>(2, "Value2");
        expected[3] = new ExternalChainingMapEntry<>(3, "Value3");
        expected[4] = new ExternalChainingMapEntry<>(4, "Value4");
        expected[5] = new ExternalChainingMapEntry<>(5, "Value5");
        expected[6] = new ExternalChainingMapEntry<>(6, "Value6");
        expected[7] = new ExternalChainingMapEntry<>(7, "Value7");
        expected[8] = new ExternalChainingMapEntry<>(8, "Value8");
        expected[9] = new ExternalChainingMapEntry<>(9, "Value9");

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testResize2() {
        assertNull(map.put(14, "Value14"));
        assertNull(map.put(15, "Value15"));
        assertNull(map.put(16, "Value16"));
        assertNull(map.put(17, "Value17"));
        assertNull(map.put(18, "Value18"));
        assertNull(map.put(19, "Value19"));
        assertNull(map.put(20, "Value20"));
        assertNull(map.put(21, "Value21"));
        assertNull(map.put(22, "Value22"));

        assertEquals(9, map.size());
        assertEquals(27, map.getTable().length);

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[27];
        expected[14] = new ExternalChainingMapEntry<>(14, "Value14");
        expected[15] = new ExternalChainingMapEntry<>(15, "Value15");
        expected[16] = new ExternalChainingMapEntry<>(16, "Value16");
        expected[17] = new ExternalChainingMapEntry<>(17, "Value17");
        expected[18] = new ExternalChainingMapEntry<>(18, "Value18");
        expected[19] = new ExternalChainingMapEntry<>(19, "Value19");
        expected[20] = new ExternalChainingMapEntry<>(20, "Value20");
        expected[21] = new ExternalChainingMapEntry<>(21, "Value21");
        expected[22] = new ExternalChainingMapEntry<>(22, "Value22");

        assertArrayEquals(expected, map.getTable());
    }

    ////////////// Remove tests
    @Test(expected = IllegalArgumentException.class)
    public void testRemove_NullKey() {
        map.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemove_KeyNotPresent() {
        map.put(1, "Value1");
        map.remove(2);
    }

    @Test
    public void testRemove_SingleEntry() {
        map.put(1, "Value1");
        assertEquals("Value1", map.remove(1));
        assertEquals(0, map.size());
        assertFalse(map.containsKey(1));
    }

    @Test
    public void testRemove_MultipleEntries() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");

        assertEquals("Value2", map.remove(2));
        assertEquals(2, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[13];
        expected[1] = new ExternalChainingMapEntry<>(1, "Value1");
        expected[3] = new ExternalChainingMapEntry<>(3, "Value3");

        assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testRemove_MultipleEntries_WithCollision() {
        map.put(1, "Value1");
        map.put(14, "Value14");
        map.put(25, "Value25");

        assertEquals("Value14", map.remove(14));
        assertEquals(2, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[13];
        expected[1] = new ExternalChainingMapEntry<>(1, "Value1");
        expected[12] = new ExternalChainingMapEntry<>(25, "Value25");

        assertArrayEquals(expected, map.getTable());
    }

    ///////////// Get tests
    @Test(expected = IllegalArgumentException.class)
    public void testGet_NullKey() {
        map.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGet_KeyNotPresent() {
        map.put(1, "Value1");
        map.get(2);
    }

    @Test
    public void testGet_SingleEntry() {
        map.put(1, "Value1");
        assertEquals("Value1", map.get(1));
    }

    @Test
    public void testGet_MultipleEntries_NoCollision() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");

        assertEquals("Value2", map.get(2));
    }

    @Test
    public void testGet_MultipleEntries_WithCollision() {
        map.put(1, "Value1");
        map.put(14, "Value14");
        map.put(27, "Value27");
        map.put(40, "Value40");

        assertEquals("Value14", map.get(14));
    }

    //////////// Contains tests
    @Test(expected = IllegalArgumentException.class)
    public void testContainsKey_NullKey() {
        map.containsKey(null);
    }

    @Test
    public void testContainsKey_EmptyMap() {
        assertFalse(map.containsKey(1));
    }

    @Test
    public void testContainsKey_KeyNotPresent() {
        map.put(1, "Value1");
        assertFalse(map.containsKey(2));
    }

    @Test
    public void testContainsKey_SingleEntry() {
        map.put(1, "Value1");
        assertTrue(map.containsKey(1));
    }

    @Test
    public void testContainsKey_MultipleEntries_NoCollision() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");

        assertTrue(map.containsKey(2));
    }

    @Test
    public void testContainsKey_MultipleEntries_WithCollision() {
        map.put(1, "Value1");
        map.put(14, "Value14");
        map.put(27, "Value27");
        map.put(40, "Value40");

        assertTrue(map.containsKey(27));
    }

    ///////// Keyset methods
    @Test
    public void testKeySet_EmptyMap() {
        Set<Integer> expected = new HashSet<>();
        assertEquals(expected, map.keySet());
    }

    @Test
    public void testKeySet_SingleEntry() {
        map.put(1, "Value1");
        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        assertEquals(expected, map.keySet());
    }

    @Test
    public void testKeySet_MultipleEntries_NoCollision() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);

        assertEquals(expected, map.keySet());
    }

    @Test
    public void testKeySet_MultipleEntries_WithCollision() {
        map.put(1, "Value1");
        map.put(14, "Value14");
        map.put(27, "Value27");
        map.put(40, "Value40");

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(14);
        expected.add(27);
        expected.add(40);

        assertEquals(expected, map.keySet());
    }

    ////////// Values tests
    @Test
    public void testValues_EmptyMap() {
        List<String> expected = new ArrayList<>();
        assertEquals(expected, map.values());
    }

    @Test
    public void testValues_SingleEntry() {
        map.put(1, "Value1");
        List<String> expected = new ArrayList<>();
        expected.add("Value1");
        assertEquals(expected, map.values());
    }

    @Test
    public void testValues_MultipleEntries_NoCollision() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");

        List<String> expected = new ArrayList<>();
        expected.add("Value1");
        expected.add("Value2");
        expected.add("Value3");

        assertEquals(expected, map.values());
    }

    @Test
    public void testValues_MultipleEntries_WithCollision() {
        map.put(1, "Value1");
        map.put(14, "Value14");
        map.put(27, "Value27");
        map.put(40, "Value40");

        List<String> expected = new ArrayList<>();
        expected.add("Value40");
        expected.add("Value27");
        expected.add("Value14");
        expected.add("Value1");

        assertEquals(expected, map.values());
    }

    //////////// Resize tests
    @Test(expected = IllegalArgumentException.class)
    public void testResize_LengthLessThanCurrentSize() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");
        map.put(4, "Value4");

        map.resizeBackingTable(3);
    }

    @Test
    public void testResize_NoEntries() {
        map.resizeBackingTable(20);
        assertEquals(20, map.getTable().length);
    }

    @Test
    public void testResize_LengthEqualsCurrentSize() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");
        map.put(4, "Value4");

        map.resizeBackingTable(4);
        assertEquals(4, map.getTable().length);
    }

    @Test
    public void testResize_LengthGreaterThanCurrentSize() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");
        map.put(4, "Value4");

        map.resizeBackingTable(10);
        assertEquals(10, map.getTable().length);
    }

    @Test
    public void testResize_KeysLargerThanInitialCapacity() {
        map.put(11, "Value11");
        map.put(12, "Value12");
        map.put(13, "Value13");
        map.put(14, "Value14");
        map.put(15, "Value15");
        map.put(16, "Value16");

        map.resizeBackingTable(20);
        assertEquals(20, map.getTable().length);

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[]) new ExternalChainingMapEntry[20];

        expected[11] = new ExternalChainingMapEntry<>(11, "Value11");
        expected[12] = new ExternalChainingMapEntry<>(12, "Value12");
        expected[13] = new ExternalChainingMapEntry<>(13, "Value13");
        expected[14] = new ExternalChainingMapEntry<>(14, "Value14");
        expected[15] = new ExternalChainingMapEntry<>(15, "Value15");
        expected[16] = new ExternalChainingMapEntry<>(16, "Value16");
        assertArrayEquals(expected, map.getTable());
    }

    /////////////// Clear tests
    @Test
    public void testClear_EmptyMap() {
        map.clear();
        assertEquals(0, map.size());
        assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY, map.getTable().length);
    }

    @Test
    public void testClear_NonEmptyMap() {
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(3, "Value3");

        map.clear();
        assertEquals(0, map.size());
        assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY, map.getTable().length);
    }

    @Test
    public void testClear_AfterResize() {
        for (int i = 1; i <= 20; i++) {
            map.put(i, "Value" + i);
        }

        map.clear();
        assertEquals(0, map.size());
        assertEquals(ExternalChainingHashMap.INITIAL_CAPACITY, map.getTable().length);
    }
}