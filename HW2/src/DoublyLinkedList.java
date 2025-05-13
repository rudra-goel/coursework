import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is not valid.\nExpected int from [0, "
             + (size - 1) + "]");
        }

        if (index == 0) {
            addToFront(data);
            return;
        } else if (index == size) {
            //System.out.println("Add " + data  + " at index: " 
            //+ index + " with list size " + size + " - add to back invoked");
            addToBack(data);
            return;
        }

        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
        int counter;
        DoublyLinkedListNode<T> current;

        //must traverse from back to front to be better
        if (index > size / 2) {
            counter = size - 1;
            current = tail;

            while (counter > index) {
                current = current.getPrevious();
                counter--;
            }

        } else { // traverse forward to back to be better
            counter = 1;
            current = head.getNext();
    
    
            while (counter < index) {
                current = current.getNext();
                counter++;
            }
        }


        current.getPrevious().setNext(newNode);
        newNode.setPrevious(current.getPrevious());
        newNode.setNext(current);
        current.setPrevious(newNode);
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

        DoublyLinkedListNode<T> newData = new DoublyLinkedListNode<>(data);
        
        if (size == 0) {
            head = newData;
            tail = newData;
            size++;
            return;

        }
        
        newData.setNext(head);
        head.setPrevious(newData);
        head = newData;
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
        DoublyLinkedListNode<T> newData = new DoublyLinkedListNode<>(data);
        
        if (size == 0) {
            head = newData;
            tail = newData;
            size++;
            return;
        }
        
        tail.setNext(newData);
        newData.setPrevious(tail);
        tail = newData;
        size++;

    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is not valid.\nExpected int from [0, "
             + (size) + "]");
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        }
        int counter;
        DoublyLinkedListNode<T> current;

        //more efficient to traverse from backward to forward when the removal index is closer to the end
        if (index > size / 2) {
            counter = size - 2; // since == size & == size - 1 are already checked above
            current = tail.getPrevious();

            while (counter > index) {
                current = current.getPrevious();
                counter--;
            }
        } else {
            counter = 1;
            current = head.getNext();
    
            while (counter < index) {
                current = current.getNext();
                counter++;
            }
        }

        //actual removal
        current.getPrevious().setNext(current.getNext());
        current.getNext().setPrevious(current.getPrevious());

        current.setNext(null);
        current.setPrevious(null);

        size--;

        return current.getData();
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty => cannot remove from front");
        }

        DoublyLinkedListNode<T> temp = head;

        if (size == 1) {
            head = null;
            tail = null;
            size--; 
            return temp.getData();
        } 

        head = head.getNext();
        head.setPrevious(null);
        
        size--;

        return temp.getData();
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
            throw new NoSuchElementException("List is empty => cannot remove from front");
        }
        DoublyLinkedListNode<T> temp = tail;

        if (size == 1) {
            tail = null;
            head = null;
            size--;
            return temp.getData();
        }
        
        
        tail = tail.getPrevious();
        tail.setNext(null);
        
        size--;

        return temp.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is not valid.\nExpected int from [0, "
             + (size - 1) + "]");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        }
        int count;
        DoublyLinkedListNode<T> current;

        if (index > size / 2) {
            count = size - 2;
            current = tail.getPrevious();

            while (count > index) {
                current = current.getPrevious();
                count--;
            }
        } else {
            //counter variable
            count = 1;
    
            //reference to current node
            current = head.getNext();
            
            //actually traversing the linked list
            while (current.getNext() != null && count < index) {
                current = current.getNext();
                count++;
            }
        }


        //returning the data
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
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {

        //reset size to zero
        size = 0;

        //dereference all variables to the linked list
        //java garbage collector will see that all nodes don't have any references pointing to it from our source code 
        //and will reallocate that space in memory
        //this effectively clears the linked list structure
        head = null;
        tail = null;

    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
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

        if (size == 0) {
            throw new NoSuchElementException("List is empty thus cannot find data: " + data);
        }

        if (tail.getData().equals(data)) {
            return removeFromBack(); // guarantee O(1) operation for this case
        }

        int indexOfLastOccurrence = -1; //assume data is not in index for later check
        int index = 0;

        DoublyLinkedListNode<T> current = head;

        while (current.getNext() != null) {
            if (current.getData().equals(data)) {
                indexOfLastOccurrence = index;
            }
            current = current.getNext();
            index++;
        }

        if (indexOfLastOccurrence == -1) {
            throw new NoSuchElementException("The specified data does not exist within the linked list");
        }

        return removeAtIndex(indexOfLastOccurrence);
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] arr = new Object[size];

        if (size == 0) {
            return arr;
        }

        //guaranteed to have the head point to non null data since size >= 1

        int count = 0;
        DoublyLinkedListNode<T> current = head;
        while (current.getNext() != null) {
            arr[count] = current.getData();
            count++;
            current = current.getNext();
        }
        arr[count] = current.getData();

        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
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
    public DoublyLinkedListNode<T> getTail() {
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
