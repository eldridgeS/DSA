import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author ELDRIDGE SURIANTO
 * @version 1.0
 * @userid esurianto3
 * @GTID 903440765
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        //System.out.println("Adding " + data + " at index " + index); (test)
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        //settle head and tail cases, then the normal addAtIndex
        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
        } else if (index == size) {
            tail.setNext(newNode);
            tail = newNode;
        } else {
            SinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }

        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        //normal addToFront, then settle empty list case
        newNode.setNext(head);
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size++;

    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        //settle empty list case, then normal addToBack
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        SinglyLinkedListNode<T> removedNode = null;
        //settle head case, then normal removeAtIndex
        if (index == 0) {
            removedNode = head;
            head = head.getNext();
            if (size == 1) {
                tail = null;
            }
        } else {
            SinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            removedNode = current.getNext();
            current.setNext(current.getNext().getNext());
            if (index == size - 1) {
                tail = current;
            }
        }
        size--;
        return removedNode.getData();
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
        SinglyLinkedListNode<T> removedNode = head;
        //normal removeFromFront, then settle size 1 case
        head = head.getNext();
        if (size == 1) {
            tail = null;
        }
        size--;
        return removedNode.getData();
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }

        SinglyLinkedListNode<T> removedNode = tail;

        if (size == 1) {
            head = null;
            tail = null;
        } else {
            SinglyLinkedListNode<T> current = head;
            while (current.getNext().getNext() != null) {
                current = current.getNext();
            }
            current.setNext(null);
            tail = current;
        }
        size--;
        return removedNode.getData();
    }


    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        //O(1) for head and tail, O(n) for the rest
        if (index == 0) {
            return head.getData();
        }

        if (index == size - 1) {
            return tail.getData();
        }

        SinglyLinkedListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        // if head is empty, whole list is empty
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        /** Create pointers removedNode and its previous node = null
         * everytime a node with target data is found, update pointers
         *once traversed, if removedNode still null, data not found
         * if previousNode is null, means last occurrence is at head
         * perform the remove accordingly
         */
        SinglyLinkedListNode<T> current = head;
        SinglyLinkedListNode<T> removedNode = null;
        SinglyLinkedListNode<T> previousNode = null;
        SinglyLinkedListNode<T> previous = null;

        while (current != null) {
            if (current.getData().equals(data)) {
                removedNode = current;
                previousNode = previous;
            }
            previous = current;
            current = current.getNext();
        }

        if (removedNode == null) {
            throw new NoSuchElementException("Data not found");
        }

        if (previousNode == null) {
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
        } else {
            previousNode.setNext(previousNode.getNext().getNext());
            if (removedNode.equals(tail)) {
                tail = previousNode;
            }
        }
        size--;
        return removedNode.getData();

    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */

    public T[] toArray() {


        //if (size == 0) {
        //   return (T[]) new Object[0];
        //}

        //create array, traverse and add to respective index using counter
        int index = 0;
        SinglyLinkedListNode<T> current = head;
        T[] array = (T[]) new Object[size];
        while (current != null) {
            array[index] = current.getData();
            current = current.getNext();
            index++;
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}

