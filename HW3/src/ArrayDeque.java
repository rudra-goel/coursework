import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayDeque.
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
public class ArrayDeque<T> {

    /**
     * The initial capacity of the ArrayDeque.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 11;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayDeque.
     */
    public ArrayDeque() {
        backingArray = (T[]) (new Object[INITIAL_CAPACITY]);
        front = 0; // index of where the front is in array backed dequeue
    }

    /**
     * Adds the element to the front of the deque.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0. After the resize and add
     * operation, the new data should be at index 0 of the array. Consider how 
     * to do this efficiently.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        //resize conditions
        if (size == backingArray.length) {
            //create new array of double size
            T[] newArr = (T[]) (new Object[backingArray.length * 2]);
            //adding to front by assigning new data to index zero
            newArr[0] = data;

            //counter var to help keep track and traverse indices 
            //when copying elements of the backingArray over to the newArr
            int backingArrayCounter = front;

            //simple counter from 1 to backingArray.length to assign indices in newArr values oin backingArray
            int newArrCounter = 1;

            //calculate the back index of the original backing Array so that we know when to stop the loop in copying
            int backIndex = mod((front + size), backingArray.length) - 1;

            if (backIndex < 0) {
                backIndex = backingArray.length - 1;
            }

            // System.out.print("[" + backingArray[0] + ", ");
            // for(int i = 1; i < backingArray.length - 1; i++) {
            //     System.out.print(backingArray[i] + ", ");
            // }
            // System.out.println(backingArray[backingArray.length - 1] + "]");

            // System.out.println("Front Index is: "  + backingArrayCounter);
            // System.out.println("Back Index is: " + backIndex);

            //traverse starting from the front and walk all the way in a circle till you get to the back index
            while (backingArrayCounter != backIndex) {
                //copying statement
                newArr[newArrCounter] = backingArray[backingArrayCounter];

                //increment counter
                backingArrayCounter++;
                newArrCounter++;

                //if the counter for the backing array hits the end of the array, wrap around to index zero
                if (backingArrayCounter >= backingArray.length) {
                    backingArrayCounter = 0;
                }
            }

            //add the last element
            newArr[newArrCounter] = backingArray[backingArrayCounter];

            //reassign pointers
            backingArray = newArr;
            //increment size
            size++;
            //reassign front pointer to be index zero
            front = 0;


        } else {
            //in this condition, the space before the front will always be empty because if it were not, 
            //then the size would be at the capacity

            //calculating the index one before the front
            int indexToAdd;
            if (front - 1 < 0) {
                indexToAdd = backingArray.length - 1;
            } else {
                indexToAdd = front - 1;
            }

            //add data and reassign front pointer
            backingArray[indexToAdd] = data;
            front = indexToAdd;
            //increment size
            size++;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == backingArray.length) {
            T[] newArr = (T[]) (new Object[backingArray.length * 2]);
            int backingArrayCounter = front;
            int backingArrayBackIndex = mod((front + size), backingArray.length) - 1;
            if (backingArrayBackIndex < 0) {
                backingArrayBackIndex = backingArray.length - 1; 
            }


            int newArrCounter = 0;


            while (backingArrayCounter != backingArrayBackIndex) {
                newArr[newArrCounter] = backingArray[backingArrayCounter];

                backingArrayCounter++;
                newArrCounter++;
                
                if (backingArrayCounter >= backingArray.length) {
                    backingArrayCounter = 0;
                }
            }
            newArr[newArrCounter] = backingArray[backingArrayCounter];
            
            //adding data to the back
            backingArrayCounter++;
            newArrCounter++;
            newArr[newArrCounter] = data;
            size++;

            //reassign front to the zeroth index
            front = 0;

            backingArray = newArr;
        } else {
            int addToLastIndex = mod((front + size), backingArray.length);


            backingArray[addToLastIndex] = data;
            
            size++;
        }


    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Do not grow or shrink the backing array.
     *
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0. Rather, modify the front index as if the deque did not become
     * empty as a result of this call.
     *
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The dequeue is empty");
        }
        T retData = backingArray[front];
        size--;

        backingArray[front] = null;

        front++;
        if (front == backingArray.length) {
            front = 0;
        }

        return retData;
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Do not grow or shrink the backing array.
     *
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0. 
     *
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The dequeue is empty");
        }
        //minus one as the equation covered in class is meant to be the position of where to add to back 
        //a potential element
        int lastIndex = mod((front + size), backingArray.length) - 1;

        if (lastIndex == -1) {
            lastIndex = backingArray.length - 1; 
        }
        T retData = backingArray[lastIndex];

        backingArray[lastIndex] = null;
        size--;

        return retData;
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the first data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The dequeue is empty");
        }
        return backingArray[front];
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the last data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("The dequeue is empty");
        }
        //System.out.println(front);
        int lastIndex = mod((front + size), backingArray.length) - 1;
        if (lastIndex == -1) {
            lastIndex = backingArray.length - 1; 
        }
        return backingArray[lastIndex];
    }

    /**
     * Returns the backing array of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the deque
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    // public void printBackingArray() {
    //     System.out.print("[" + backingArray[0] + ", ");
    //     for(int i = 1; i < backingArray.length - 1; i++) {
    //         System.out.print(backingArray[i] + ", ");
    //     }
    //     System.out.println(backingArray[backingArray.length - 1] + "]");
    // }

    /**
     * Returns the smallest non-negative remainder when dividing index by
     * modulo. So, for example, if modulo is 5, then this method will return
     * either 0, 1, 2, 3, or 4, depending on what the remainder is.
     *
     * This differs from using the % operator in that the % operator returns
     * the smallest answer with the same sign as the dividend. So, for example,
     * (-5) % 6 => -5, but with this method, mod(-5, 6) = 1.
     *
     * Examples:
     * mod(-3, 5) => 2
     * mod(11, 6) => 5
     * mod(-7, 7) => 0
     *
     * This helper method is here to make the math part of the circular
     * behavior easier to work with.
     *
     * @param index  the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        // DO NOT MODIFY THIS METHOD!
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive");
        }
        int newIndex = index % modulo;
        return newIndex >= 0 ? newIndex : newIndex + modulo;
    }
}
