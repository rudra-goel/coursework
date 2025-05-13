import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data at is null. Cannot build null type into Heap");
        }

        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        size = 0;

        //copy data before BuildHeap algo
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data at index " + i + " is null. Cannot add null type to Heap");
            }
            backingArray[i + 1] = data.get(i);
        }

        size = data.size();

        //implement BuildHead algorithm by down heap enforcing sibling pairs
        //we have to call down heap on all non leaf nodes
        //non leaf nodes are nodes such that they have children
        //this means that a node at index i will have a non null child at 2*i and/or 2*i+1
        //the last non leaf node will be at index size/2
        for (int i = size / 2; i > 0; i--) {
            //call down heap on each internal node
            downHeap(i);
        }
        size = data.size();

        // printBackingArray();
    }

    /**
     * Helper method that recursively implements down heap
     * @param index the current index to perform down heap on
     */
    private void downHeap(int index) {
        // printBackingArray();
        //due to shape property of heaps, internal nodes are certain to have one left child
        //since the first internal node of this build heap call is 
        //size / 2 --> i * 2 will produce a valid non-null index
        int leftIndex = index * 2;

        //this index may be out of bounds or may be null
        int rightIndex = index * 2 + 1;

        // System.out.println("Current Index: " + index);
        // System.out.println("Left Index: " + leftIndex);
        // System.out.println("Right Index: " + rightIndex);


        //need to check if the index of the internal node is really an internal node
        //base case of down heap as we cannot heapify a node that is a leaf (no kids)
        if (leftIndex > size) {
            return;
        } else if (rightIndex >= size + 1 || backingArray[rightIndex] == null) {
            //the index passed is an internal node
            //need to check if it has a right child
            //set it equal to -1 as a flag to indicate that it does not have a right child
            rightIndex = -1;
        }

        T left = backingArray[leftIndex];

        //compare current node to left child. if it is smaller, check the right child (if it exists)
        if (backingArray[index].compareTo(left) < 0) {
            //check if the current node is smaller than the right child
            //if it is, then the order property is maintained, and we can return
            if (rightIndex == -1) {
                //no right child
                //meaning that current index is already smaller than left child and order property is maintained
                return;
            } else if (backingArray[rightIndex] != null
                        && backingArray[index].compareTo(backingArray[rightIndex]) < 0) {
                //current index is smaller than left child and right child --> order property maintained
                return;
            } else if (backingArray[index].compareTo(backingArray[rightIndex]) > 0) {
                //meaning the current node is smaller than the left
                //and bigger than the right so we have to swap it with the right
                T right = backingArray[rightIndex];
                backingArray[rightIndex] = backingArray[index];
                backingArray[index] = right;

                downHeap(rightIndex);
            }
        } else {
            //meaning current node is greater than left child.
            //need to compare the left and right child (if exists) and swap with the smaller of the two.
            //we then need to perform down heap on the position of the swapped child.

            if (rightIndex != -1) {
                
                if (left.compareTo(backingArray[rightIndex]) < 0) {
                    //left child is smaller than right child, so swap current index with left child
                    //perform down heap on the left child position
                    backingArray[leftIndex] = backingArray[index];
                    backingArray[index] = left;

                    downHeap(leftIndex);
                } else {
                    //right child is smaller than the left child, 
                    //swap current index with right child and perform down heap on the right index
                    T right = backingArray[rightIndex];
                    backingArray[rightIndex] = backingArray[index];
                    backingArray[index] = right;

                    downHeap(rightIndex);
                }

            } else {
                //only left child exists and current node is bigger than the left child
                //swap the left child with the current node
                //perform down heap on the left child position
                backingArray[leftIndex] = backingArray[index];
                backingArray[index] = left;

                downHeap(leftIndex);
            }
        }

    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        
        if (data == null) {
            throw new IllegalArgumentException("cannot add null data to heap");
        }
        //since index 0 must be null
        if (size == backingArray.length - 1) {
            //must resize backingArray
            T[] tempArr = (T[]) new Comparable[size * 2];
            
            for (int i = 1; i < backingArray.length; i++) {
                tempArr[i] = backingArray[i];
            }
            
            backingArray = tempArr;
        }
        backingArray[size + 1] = data;
        size++;

        // System.out.print("Backing Array Before DownHeap " + data + ": ");
        // printBackingArray();
        
        //perform down heap on all the internal nodes to re order the heap
        //as adding the data could disrupt the whole graph
        
        for (int i = size / 2; i > 0; i--) {
            downHeap(i);
        }
        
        // System.out.print("Backing Array After DownHeap " + data + ": ");
        // printBackingArray();
        // System.out.println();
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        // System.out.print("Backing Array Before Removing : ");
        // printBackingArray();
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove anything if heap is empty");
        }
        
        
        T dataToRemove = backingArray[1];
        
        //grab the right most data from the set of leaves
        //this is just the last element in the array
        backingArray[1] = backingArray[size];
        
        backingArray[size] = null;
        size--;
        
        //perform down heap on the root to re order the data
        downHeap(1);

        // System.out.print("Backing Array Before Removing : ");
        // printBackingArray();
        // System.out.println();
        
        return dataToRemove;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
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
     * Returns the size of the heap.
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

    /**
     * Helper method to print backing array for debugging purposes
     */
    // private void printBackingArray() {
    //     System.out.print("[" + backingArray[0] + ", ");
        
    //     for (int i = 1; i < backingArray.length - 1; i++) {
    //         System.out.print(backingArray[i] + ", ");
    //     }
    //     System.out.println(backingArray[backingArray.length - 1] + "]");
    // }
}
