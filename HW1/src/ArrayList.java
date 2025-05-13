/**
 * Your implementation of an ArrayList.
 *
 * @author Rudra Goel
 * @version 1.0
 * @userid rgoel68
 * @GTID 903897740
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */

import java.util.NoSuchElementException;

public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        backingArray = (T[]) (new Object[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    @SuppressWarnings("unchecked")
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid. Expected index: [0, " + size + "]");
        }


        /**
         * Case 1: array is already full and the index they want to add to is the very end --> 
         * this should already be implemented by the addToBack()
         */
        if (index == size) {
            addToBack(data);
            return;
        }

        if (size == backingArray.length) {
            // System.out.println("Backing Array Capacity reached. Expanding on data input: " + data);
            T[] expandedArr = (T[]) (new Object[(backingArray.length * 2)]);


            for (int i = 0; i < index; i++) {
                expandedArr[i] = backingArray[i];
            }

            expandedArr[index] = data;
            size++;

            for (int i = index + 1; i < backingArray.length + 1; i++) {
                expandedArr[i] = backingArray[i - 1];

            }

            // System.out.print("[");
            // for (int i = 0; i < expandedArr.length; i++) {
            //     System.out.print(expandedArr[i] + ", ");
            // }
            // System.out.println("]");


            //Re-point the backing array
            backingArray = expandedArr;

            return;
        }
        
        /**
         * Final case: size < capacity
         */

        for (int i = size - 1; i >= index; i--) {
            backingArray[i + 1] = backingArray[i]; // ensured to have a valid index
        }

        backingArray[index] = data;
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    @SuppressWarnings("unchecked")
    public void addToBack(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (size == backingArray.length) {
            T[] expandedArr = (T[]) (new Object[(int) (backingArray.length * 1.5)]);

            for (int i = 0; i < backingArray.length; i++) {
                expandedArr[i] = backingArray[i];
            }

            backingArray = expandedArr;
        }

        backingArray[size] = data;
        size++;

    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is not valid. Must be between (0, size]");
        }
        T temp = backingArray[index];
        backingArray[index] = null;
        
        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
            backingArray[i + 1] = null;
        }
        size--;
        return temp;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("ArrayList is empty");
        }

        T temp = backingArray[0];

        backingArray[0] = null;

        for (int i = 0; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
            backingArray[i + 1] = null;
        }

        size--;
        return temp;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("ArrayList is empty");
        }

        T temp = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return temp;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid. Expected index: [0, " + size + ")");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        backingArray = (T[]) (new Object[INITIAL_CAPACITY]);
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
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
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
