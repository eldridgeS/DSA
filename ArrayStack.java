import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayStack.
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
public class ArrayStack<T> {

    /*
     * The initial capacity of the ArrayStack.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        //constructor creates array with size initial capacity and size = 0
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;

    }

    /**
     * Adds the data to the top of the stack.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current length.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    @SuppressWarnings("unchecked")
    public void push(T data) {
        // if array is full, create new array with current size * 2 and copy
        //assign array[size] to data pre size increment
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == backingArray.length) {
            T[] newBackingArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length ; i++) {
                newBackingArray[i] = backingArray[i];
            }
            backingArray = newBackingArray;
        }
        backingArray[size++] = data;

    }

    /**
     * Removes and returns the data from the top of the stack.
     *
     * Do not shrink the backing array.
     *
     * Replace any spots that you pop from with null.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        //store removed data from array[size] post decrement
        //delete array[size] and return removed data
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty");
        }
        T removedData = backingArray[--size];
        backingArray[size] = null;
        return removedData;

    }

    /**
     * Returns the data from the top of the stack without removing it.
     *
     * Must be O(1).
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        // return element that has index size - 1
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty");
        }
        return backingArray[size - 1];
    }

    /**
     * Returns the backing array of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
