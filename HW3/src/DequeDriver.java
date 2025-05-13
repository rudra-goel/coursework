import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * The tests in this file for different methods are sometimes dependent on other methods
 * so, try to debug failed test cases from the start to the end.
 * 
 * Also, these are just my test cases, there could be ones I've missed!
 */
public class DequeDriver {

    private static final int TIMEOUT = 200;
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayDeque.INITIAL_CAPACITY],
                array.getBackingArray());

        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeAddFirst() {

        //check whether add first throws exception for null data
        assertThrows(IllegalArgumentException.class, () -> linked.addFirst(null));

        linked.addFirst("a"); //a
        assertEquals("Node containing: a", linked.getHead().toString());
        assertEquals("Node containing: a", linked.getTail().toString());

        //make sure pointers point to null for singular obj
        LinkedNode<String> currNode = linked.getHead();
        assertEquals("a", currNode.getData());
        assertNull(currNode.getNext());
        assertNull(currNode.getPrevious());

        assertEquals(linked.size(), 1); //check if sizing working

        linked.addFirst("b"); //b, a
        assertEquals("Node containing: b", linked.getHead().toString());
        assertEquals("Node containing: a", linked.getTail().toString());

        linked.addFirst("c"); //c, b, a

        //make sure all the pointers are working
        currNode = linked.getHead();
        assertNull(currNode.getPrevious());
        assertEquals("c", currNode.getData());
        assertEquals("b", currNode.getNext().getData());
        assertEquals("a", currNode.getNext().getNext().getData());
        assertNull(currNode.getNext().getNext().getNext());

        assertEquals(linked.size(), 3); //check if sizing working
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeAddLast() {

        //check whether add first throws exception for null data
        assertThrows(IllegalArgumentException.class, () -> linked.addLast(null));

        linked.addLast("a"); //a
        assertEquals("Node containing: a", linked.getHead().toString()) ;
        assertEquals("Node containing: a", linked.getTail().toString());

        //make sure pointers point to null for singular obj
        LinkedNode<String> currNode = linked.getHead();
        assertEquals("a", currNode.getData());
        assertNull(currNode.getNext());
        assertNull(currNode.getPrevious());

        assertEquals(linked.size(), 1); //check if sizing working

        linked.addLast("b"); // a, b
        assertEquals("Node containing: a", linked.getHead().toString());
        assertEquals("Node containing: b", linked.getTail().toString());

        linked.addLast("c"); //a, b, c

        //make sure all the pointers are working
        currNode = linked.getHead();
        assertNull(currNode.getPrevious());
        assertEquals("a", currNode.getData());
        assertEquals("b", currNode.getNext().getData());
        assertEquals("c", currNode.getNext().getNext().getData());
        assertNull(currNode.getNext().getNext().getNext());

        assertEquals(linked.size(), 3);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeRemoveFirst() {

        //throw exception if you try to remove something from empty list
        assertThrows(NoSuchElementException.class, () -> linked.removeFirst());

        //check if everything works as expected when removing last element in list
        linked.addFirst("z");
        assertEquals("z", linked.removeFirst());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
        assertEquals(linked.size(), 0);

        linked.addFirst("a");
        linked.addLast("b");
        linked.addLast("c");
        linked.addLast("d"); //a, b, c, d
        assertEquals("a", linked.removeFirst());
        assertEquals("b", linked.getHead().getData());
        assertEquals("d", linked.getTail().getData());
        assertNull(linked.getTail().getNext());
        assertNull(linked.getHead().getPrevious());
        assertEquals(linked.size(), 3);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeRemoveLast() {

        //throw exception if you try to remove something from empty list
        assertThrows(NoSuchElementException.class, () -> linked.removeLast());

        //check if everything works as expected when removing last element in list
        linked.addFirst("z");
        assertEquals("z", linked.removeLast());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
        assertEquals(linked.size(), 0);

        linked.addFirst("a");
        linked.addLast("b");
        linked.addLast("c");
        linked.addLast("d"); //a, b, c, d
        assertEquals("d", linked.removeLast()); //a, b, c
        assertEquals("a", linked.getHead().getData());
        assertEquals("c", linked.getTail().getData());
        assertNull(linked.getTail().getNext());
        assertNull(linked.getHead().getPrevious());
        assertEquals(linked.size(), 3);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeGet() {

        //throw exception if you try to get something from empty list
        assertThrows(NoSuchElementException.class, () -> linked.getFirst());
        assertThrows(NoSuchElementException.class, () -> linked.getLast());

        linked.addLast("0a"); // 0a
        linked.addLast("1a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals("0a", linked.getFirst());
        assertEquals("3a", linked.getLast());
    }

    /*
     Array Deque tests
     */

    @Test(timeout = TIMEOUT)
    public void testArrayDequeAddFirst() {

        //throws exception if you try to pass in null data
        assertThrows(IllegalArgumentException.class, () -> array.addFirst(null));

        /*
        case where array just adds elements from front = 0 till it has to resize
         */
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        array.addFirst("4a"); // _, _, _, _, _, _, _, _, _, _, 4a
        expected[10] = "4a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(1, array.size());

        array.addFirst("3a"); // _, _, _, _, _, _, _, _, _, 3a, 4a
        expected[9] = "3a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(2, array.size());

        array.addFirst("2a"); // _, _, _, _, _, _, _, _, 2a, 3a, 4a
        array.addFirst("1a"); // _, _, _, _, _, _, _, 1a, 2a, 3a, 4a
        array.addFirst("0a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 4a
        array.addFirst("0"); // _, _, _, _, _, 0, 0a, 1a, 2a, 3a, 4a
        array.addFirst("1"); // _, _, _, _, 1, 0, 0a, 1a, 2a, 3a, 4a
        array.addFirst("2"); // _, _, _, 2, 1, 0, 0a, 1a, 2a, 3a, 4a
        array.addFirst("3"); // _, _, 3, 2, 1, 0, 0a, 1a, 2a, 3a, 4a
        array.addFirst("4"); // _, 4, 3, 2, 1, 0, 0a, 1a, 2a, 3a, 4a
        array.addFirst("5"); // 5, 4, 3, 2, 1, 0, 0a, 1a, 2a, 3a, 4a
        expected[8] = "2a";
        expected[7] = "1a";
        expected[6] = "0a";
        expected[5] = "0";
        expected[4] = "1";
        expected[3] = "2";
        expected[2] = "3";
        expected[1] = "4";
        expected[0] = "5";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(11, array.size());

        array.addFirst("oop"); //oop, 5, 4, 3, 2, 1, 0, 0a, 1a, 2a, 3a, 4a, _, _, _, _, _, _, _, _, _, _
        String[] newExpected = new String[ArrayDeque.INITIAL_CAPACITY*2];
        newExpected[0] = "oop";
        for (int i = 1; i <= expected.length; i++) {
            newExpected[i] = expected[i - 1];
        }

        Object[] backingArray = array.getBackingArray();

        System.out.print("[" + backingArray[0] + ", ");
        for(int i = 1; i < backingArray.length - 1; i++) {
            System.out.print(backingArray[i] + ", ");
        }
        System.out.println(backingArray[backingArray.length - 1] + "]");



        assertArrayEquals(newExpected, array.getBackingArray());
        assertEquals(12, array.size());

        //adding 1000 elements
        array = new ArrayDeque<>();
        expected = new String[1408];
        String temp;
        for (int i = 704; i >= 0; i--) {
            temp = "" + i;
            expected[i] = temp;
            array.addFirst(temp);
        }
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(705, array.size());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeAddLast() {

        //throws exception if you try to pass in null data
        assertThrows(IllegalArgumentException.class, () -> array.addLast(null));

        /*
        case where array just adds elements from back till it has to resize
         */
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        array.addLast("4a"); // 4a, _, _, _, _, _, _, _, _, _, _
        expected[0] = "4a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(1, array.size());

        array.addLast("3a"); // 4a, 3a, _, _, _, _, _, _, _, _, _
        expected[1] = "3a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(2, array.size());

        array.addLast("2a");
        array.addLast("1a"); //4a, 3a, 2a, 1a, _, _, _, _, _, _, _
        array.addLast("0a"); //4a, 3a, 2a, 1a, 0a, _, _, _, _, _, _
        array.addLast("0");
        array.addLast("1");
        array.addLast("2"); //4a, 3a, 2a, 1a, 0a, 0, 1, 2, _, _, _
        array.addLast("3");
        array.addLast("4");
        array.addLast("5"); //4a, 3a, 2a, 1a, 0a, 0, 1, 2, 3, 4, 5
        expected[2] = "2a";
        expected[3] = "1a";
        expected[4] = "0a";
        expected[5] = "0";
        expected[6] = "1";
        expected[7] = "2";
        expected[8] = "3";
        expected[9] = "4";
        expected[10] = "5";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(11, array.size());

        array.addLast("oop"); //4a, 3a, 2a, 1a, 0a, 0, 1, 2, 3, 4, 5, oop, _, _, _, _, _, _, _, _, _, _
        String[] newExpected = new String[ArrayDeque.INITIAL_CAPACITY*2];
        newExpected[11] = "oop";
        for (int i = 0; i < expected.length; i++) {
            newExpected[i] = expected[i];
        }

        Object[] backingArray = array.getBackingArray();

        System.out.print("[" + backingArray[0] + ", ");
        for(int i = 1; i < backingArray.length - 1; i++) {
            System.out.print(backingArray[i] + ", ");
        }
        System.out.println(backingArray[backingArray.length - 1] + "]");


        assertArrayEquals(newExpected, array.getBackingArray());
        assertEquals(12, array.size());

        //adding 1000 elements
        array = new ArrayDeque<>();
        expected = new String[1408];
        String temp;
        for (int i = 0; i < 1000; i++) {
            temp = "" + i;
            expected[i] = temp;
            array.addLast(temp);
        }
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(1000, array.size());
    }


    @Test(timeout = TIMEOUT)
    public void testArrayDequeGetMethods() {

        assertThrows(NoSuchElementException.class, () -> array.getFirst());
        assertThrows(NoSuchElementException.class, () -> array.getLast());

        array.addFirst("4a"); // _, _, _, _, _, _, _, _, _, _, 4a
        assertEquals("4a", array.getFirst());
        assertEquals("4a", array.getLast());

        array.addFirst("3a"); // _, _, _, _, _, _, _, _, _, 3a, 4a
        assertEquals("3a", array.getFirst());
        assertEquals("4a", array.getLast());

        array.addLast("1"); // 1, _, _, _, _, _, _, _, _, 3a, 4a
        assertEquals("3a", array.getFirst());
        assertEquals("1", array.getLast());


    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeRemoveFirst() {

        //throws exception if there's nothing in the list
        assertThrows(NoSuchElementException.class, () -> array.removeFirst());

        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _

        array.removeFirst(); // _, 1a, _, _, _, _, _, _, _, _, _
        assertEquals("1a", array.getFirst());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[1] = "1a";
        assertArrayEquals(expected, array.getBackingArray());

        //if this isn't working you're front isn't being updated properly when removing last element
        array.removeFirst();
        array.addLast("0a"); // _, _, 0a, _, _, _, _, _, _, _, _
        assertEquals("0a", array.getFirst());
        assertEquals(1, array.size());
        expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[2] = "0a";
        assertArrayEquals(expected, array.getBackingArray());

        //wraparound scenario
        array = new ArrayDeque<>(); //reset array
        array.addLast("1a");
        array.addLast("0a");
        array.addFirst("0"); // 1a, 0a, _, _, _, _, _, _, _, _, 0
        assertEquals("0", array.getFirst()); //check if front points to 0
        expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "0a";
        expected[10] = "0";
        assertArrayEquals(expected, array.getBackingArray());

        array.removeFirst();
        assertEquals("1a", array.getFirst()); //front should point to index 0
        expected[10] = null;
        assertArrayEquals(expected, array.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeRemoveLast() {

        assertThrows(NoSuchElementException.class, () -> array.removeLast());

        array.addLast("0a");
        array.addLast("1a");
        array.addFirst("2a"); // 0a, 1a, _, _, _, _, _, _, _, _, 2a
        assertEquals("1a", array.getLast());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        array.removeLast();
        expected[10] = "2a";
        expected[0] = "0a";
        assertEquals("0a", array.getLast()); //normal case
        assertArrayEquals(expected, array.getBackingArray());

        //wraparound case
        array.removeLast();
        assertEquals("2a", array.getLast());
        expected[0] = null;
        assertArrayEquals(expected, array.getBackingArray());

        //extra case
        array.removeLast();
        expected = new String[ArrayDeque.INITIAL_CAPACITY];
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(0, array.size());
    }

}