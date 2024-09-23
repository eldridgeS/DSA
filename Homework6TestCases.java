import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Homework6TestCases {
    private Integer[] intArray;
    private CountingComparator<Integer> comp;

    @Before
    public void setUp() {
        intArray = new Integer[]{5, 3, 8, 2, 1, 7, 4};
        comp = new CountingComparator<Integer>(Comparator.naturalOrder());
    }

    //////////////// Selection Sort tests
    @Test(expected = IllegalArgumentException.class)
    public void testNullArray() {
        Integer[] nullArray = null;
        Sorting.selectionSort(nullArray, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullComparator() {
        Integer[] someArray = {1, 2, 3};
        Sorting.selectionSort(someArray, null);
    }

    @Test
    public void testSelectionSort() {
        Integer[] expected = {1, 2, 3, 4, 5, 7, 8};
        Sorting.selectionSort(intArray, comp);
        assertArrayEquals(expected, intArray);
        assertEquals(21, comp.getCount());
    }

    @Test
    public void testSelectionSingleElementArray() {
        Integer[] singleElementArray = new Integer[]{42};
        Sorting.selectionSort(singleElementArray, comp);
        assertEquals(42, (int) singleElementArray[0]);
        assertEquals(0, comp.getCount());
    }

    @Test
    public void testSelectionAlreadySortedArray() {
        Integer[] alreadySorted = {1, 2, 3, 4, 5};
        Sorting.selectionSort(alreadySorted, comp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, alreadySorted);
        assertEquals(10, comp.getCount());
    }

    ////////// Insertion Sort tests
    @Test
    public void testInsertionSort() {
        Integer[] expected = {1, 2, 3, 4, 5, 7, 8};
        Sorting.insertionSort(intArray, comp);
        assertArrayEquals(expected, intArray);
        assertEquals(15, comp.getCount());
    }

    @Test
    public void testInsertionAlreadySortedArray() {
        Integer[] alreadySorted = {1, 2, 3, 4, 5};
        Sorting.insertionSort(alreadySorted, comp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, alreadySorted);
        assertEquals(4, comp.getCount());
    }

    @Test
    public void testReverseSortedArray() {
        Integer[] reverseSorted = {5, 4, 3, 2, 1};
        Sorting.insertionSort(reverseSorted, comp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, reverseSorted);
        assertEquals(10, comp.getCount());
    }

    @Test
    public void testInsertionArrayWithDuplicates() {
        Integer[] arrayWithDuplicates = {3, 2, 1, 2, 3, 1};
        Sorting.insertionSort(arrayWithDuplicates, comp);
        assertArrayEquals(new Integer[]{1, 1, 2, 2, 3, 3}, arrayWithDuplicates);
        assertEquals(11, comp.getCount());
    }

    /////////// Bubble Sort tests
    @Test
    public void testBubbleSort() {
        Integer[] expected = {1, 2, 3, 4, 5, 7, 8};
        Sorting.bubbleSort(intArray, comp);
        assertArrayEquals(expected, intArray);
        assertEquals(18, comp.getCount());
    }

    @Test
    public void testBubbleAlreadySortedArray() {
        Integer[] alreadySorted = {1, 2, 3, 4, 5};
        Sorting.bubbleSort(alreadySorted, comp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, alreadySorted);
        assertEquals(4, comp.getCount());
    }

    @Test
    public void testBubbleReverseSortedArray() {
        Integer[] reverseSorted = {5, 4, 3, 2, 1};
        Sorting.bubbleSort(reverseSorted, comp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, reverseSorted);
        assertEquals(10, comp.getCount());
    }

    ////////// Merge Sort tests
    @Test
    public void testMergeSort() {
        Integer[] expected = {1, 2, 3, 4, 5, 7, 8};
        Sorting.mergeSort(intArray, comp);
        assertArrayEquals(expected, intArray);
        assertEquals(13, comp.getCount());
    }

    @Test
    public void testMergeAlreadySortedArray() {
        Integer[] alreadySorted = {1, 2, 3, 4, 5};
        Sorting.mergeSort(alreadySorted, comp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, alreadySorted);
        assertEquals(5, comp.getCount());
    }

    @Test
    public void testMergeReverseSortedArray() {
        Integer[] reverseSorted = {5, 4, 3, 2, 1};
        Sorting.mergeSort(reverseSorted, comp);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, reverseSorted);
        assertEquals(7, comp.getCount());
    }

    ////////////// Radix Sort tests
    @Test
    public void testLSDRadixSort() {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        int[] expected = {2, 24, 45, 66, 75, 90, 170, 802};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testLSDRadixSortAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testLSDRadixSortReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testLSDRadixSortArrayWithNegativeNumbers() {
        int[] arr = {170, -45, 75, -90, 802, -24, 2, -66};
        int[] expected = {-90, -66, -45, -24, 2, 75, 170, 802};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(expected, arr);
    }

    /////////////////// Heap Sort tests
    @Test
    public void testHeapSort() {
        List<Integer> list = Arrays.asList(4, 10, 3, 5, 1);
        int[] expected = {1, 3, 4, 5, 10};
        int[] result = Sorting.heapSort(list);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testHeapSortAlreadySorted() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        int[] expected = {1, 2, 3, 4, 5};
        int[] result = Sorting.heapSort(list);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testHeapSortReverseSorted() {
        List<Integer> list = Arrays.asList(5, 4, 3, 2, 1);
        int[] expected = {1, 2, 3, 4, 5};
        int[] result = Sorting.heapSort(list);
        assertArrayEquals(expected, result);
    }







    /////////// Inner class for Counting Comparator
    private static class CountingComparator<T> implements Comparator<T> {
        private final Comparator<T> comparator;
        private int count;

        public CountingComparator(Comparator<T> comparator) {
            this.comparator = comparator;
            this.count = 0;
        }

        @Override
        public int compare(T o1, T o2) {
            count++;
            return comparator.compare(o1, o2);
        }

        public int getCount() {
            return count;
        }
    }
}