import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author ELDRIDGE SURIANTO
 * @version 1.0
 * @userid esurianto3
 * @GTID 903440765
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        //iterate twice and find next smallest on second iteration, then swap
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        //iterate, and move everything larger up and update current
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        for (int i = 0; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        //keep comparing with next value and bubble largest element up
        //stop if nothing swapped last iteration or last swapped index reached
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }

        int n = arr.length;
        boolean swapped = true;

        while (swapped) {
            swapped = false;
            int lastSwappedIndex = 0;
            for (int i = 0; i < n - 1; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    // Swap elements
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;

                    swapped = true;
                    lastSwappedIndex = i + 1;
                }
            }
            n = lastSwappedIndex; // Reduce the range for the next pass
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        //recursively split array to subarrays of half size, then call merge
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        if (arr.length > 1) {
            int mid = arr.length / 2;
            T[] left = (T[]) new Object[mid];
            T[] right = (T[]) new Object[arr.length - mid];
            for (int i = 0; i < mid; i++) {
                left[i] = arr[i];
            }
            for (int i = mid; i < arr.length; i++) {
                right[i - mid] = arr[i];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(arr, comparator, left, right);
        }
    }

    /**
     *Helper Merge function to merge the two subarrays in order
     * @param arr main array where two subarrays are merged
     * @param comparator comparator used to compare arrays
     * @param left left array to be merged
     * @param right right array to be merged
     * @param <T> data type to sort
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator,
                                  T[] left, T[] right) {
        //compare the sorted subarrays and merge accordingly
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < left.length) {
            arr[k++] = left[i++];
        }
        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        //iterate once to find element with most digits, then find digit number
        //sort elements based on their digits, putting them into buckets then
        //adding to buckets, then removing and adding to an array then readding
        //to the a fresh set of buckets, finally an arranged array
        if (arr == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;

        int biggest = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i]) > Math.abs(biggest)) {
                biggest = arr[i];
            }
            if (arr[i] == Integer.MIN_VALUE) {
                biggest = Integer.MAX_VALUE;
            }
        }
        int k = 0;
        while (biggest != 0) {
            biggest /= 10;
            k++;
        }

        while (k > 0) {
            for (int num : arr) {
                int bucket = num / div;
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrIdx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int num : bucket) {
                        arr[arrIdx++] = num;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
            k--;
        }

    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        //priority queue will poll smallest value by default
        //create heap, then keep polling and putting into result array
        if (data == null) {
            throw new IllegalArgumentException("The input data cannot be null.");
        }

        // Initialize the PriorityQueue using its build heap constructor
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);

        // Create an array to hold the sorted elements
        int[] sortedArray = new int[data.size()];

        // Extract elements from the heap and add them to the sortedArray
        int index = 0;
        while (!heap.isEmpty()) {
            sortedArray[index++] = heap.poll();
        }

        return sortedArray;
    }
}