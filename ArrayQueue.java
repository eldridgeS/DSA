import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayQueue.
 *
 * @author Eldridge Surianto
 * @version 1.0
 * @userid esurianto3
 * @GTID 903440765
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayQueue<T> {

    /*
     * The initial capacity of the ArrayQueue.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        //create array of size initial capacity, create front and size pointer
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        front = 0;
        size = 0;

    }

    /**
     * Adds the data to the back of the queue.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current length. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    @SuppressWarnings("unchecked")
    public void enqueue(T data) {
        //if array is full, copy a new array with 2x size and reset front
        //assign data to next open space modulus for overflow, increment size
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == backingArray.length) {
            T[] newBackingArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                newBackingArray[i] = backingArray[(front + i) % size];
            }
            backingArray = newBackingArray;
            front = 0;
        }
        backingArray[(front + size) % backingArray.length] = data;
        size++;
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * Do not shrink the backing array.
     *
     * Replace any spots that you dequeue from with null.
     *
     * If the queue becomes empty as a result of this call, do not reset
     * front to 0.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        //store front data, delete front, move front pointer and decrement size
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        T removedData = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size--;
        return removedData;

    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        //return front data
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return backingArray[front];
    }

    /**
     * Returns the front index of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the front index of the queue
     */
    public int getFront() {
        // DO NOT MODIFY THIS METHOD!
        return front;
    }

    /**
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
