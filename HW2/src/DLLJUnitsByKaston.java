import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * JUnits for DoublyLinkedList homework
 *
 * @author Kaston Carr
 * @version 1.0
 */

public class DLLJUnitsByKaston {
    private static final int TIMEOUT = 200;
    private DoublyLinkedList<Integer> list;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void shamelessPlug() {
        System.out.println("If anything is wrong email kcarr36@gatech.edu");
        System.out.println("This is a shameless plug of my LinkedIn");
        System.out.println("consider connecting @ https://www.linkedin.com/in/kastoncarr/");
    }

    @Test(timeout = TIMEOUT)
    public void testMethodsOnListWithNoItems() {
        assertThrows(NoSuchElementException.class, () -> list.removeFromBack());
        assertThrows(NoSuchElementException.class, () -> list.removeFromFront());
        
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(1));

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));

        assertThrows(NoSuchElementException.class, () -> list.removeLastOccurrence(0));

        assertArrayEquals(new Object[0], list.toArray());

        assertTrue(list.isEmpty());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testFrontMethodsOnListWithOneItem() {
        list.addToFront(0);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(0, (int) list.getTail().getData());
        assertNull(list.getHead().getNext());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertNull(list.getTail().getPrevious());
        assertEquals(1, list.size());

        list.removeLastOccurrence(0);
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());

        list.addToFront(0);
        list.removeFromFront();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testBackMethodsOnListWithOneItem() {
        list.addToBack(0);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(0, (int) list.getTail().getData());
        assertNull(list.getHead().getNext());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertNull(list.getTail().getPrevious());
        assertEquals(1, list.size());

        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());

        list.addToBack(0);
        list.removeFromBack();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testIndexMethodsOnListWithOneItem() {
        list.addAtIndex(0, 0);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(0, (int) list.getTail().getData());
        assertNull(list.getHead().getNext());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertNull(list.getTail().getPrevious());
        assertEquals(1, list.size());

        assertEquals(0, (int) list.get(0));
        assertFalse(list.isEmpty());
        assertArrayEquals(new Object[]{0}, list.toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testFrontMethodsOnListWithTwoItems() {
        list.addToFront(1);
        list.addToFront(0);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(1, (int) list.getTail().getData());
        assertEquals(1, (int) list.getHead().getNext().getData());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertEquals(0, (int) list.getTail().getPrevious().getData());
        assertEquals(2, list.size());

        list.removeLastOccurrence(1);
        assertEquals(1, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(0, (int) list.getTail().getData());

        list.addToFront(1);
        list.removeFromFront();
        assertEquals(1, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(0, (int) list.getTail().getData());

        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testBackMethodsOnListWithTwoItems() {
        list.addToBack(0);
        list.addToBack(1);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(1, (int) list.getTail().getData());
        assertEquals(1, (int) list.getHead().getNext().getData());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertEquals(0, (int) list.getTail().getPrevious().getData());
        assertEquals(2, list.size());

        list.removeFromBack();
        assertEquals(1, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(0, (int) list.getTail().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testIndexMethodsOnListWithTwoItems() {
        list.addAtIndex(0, 0);
        list.addAtIndex(1, 1);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(1, (int) list.getTail().getData());
        assertEquals(1, (int) list.getHead().getNext().getData());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertEquals(0, (int) list.getTail().getPrevious().getData());
        assertEquals(2, list.size());

        list.removeAtIndex(1);
        assertEquals(1, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(0, (int) list.getTail().getData());


        list.addToBack(1);
        assertEquals(0, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
        assertArrayEquals(new Object[]{0, 1}, list.toArray());
        assertFalse(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testFrontMethodsOnListWithThreeItems() {
        list.addToFront(2);
        list.addToFront(1);
        list.addToFront(0);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(2, (int) list.getTail().getData());
        assertEquals(1, (int) list.getHead().getNext().getData());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertEquals(1, (int) list.getTail().getPrevious().getData());
        assertEquals(3, list.size());

        list.removeLastOccurrence(1);
        assertEquals(2, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(2, (int) list.getTail().getData());

        list.addToFront(1);
        list.removeFromFront();
        assertEquals(2, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(2, (int) list.getTail().getData());

        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testBackMethodsOnListWithThreeItems() {
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(2);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(2, (int) list.getTail().getData());
        assertEquals(1, (int) list.getHead().getNext().getData());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertEquals(1, (int) list.getTail().getPrevious().getData());
        assertEquals(3, list.size());

        list.removeFromBack();
        assertEquals(2, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(1, (int) list.getTail().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testIndexMethodsOnListWithThreeItems() {
        list.addAtIndex(0, 0);
        list.addAtIndex(1, 1);
        list.addAtIndex(2, 2);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(2, (int) list.getTail().getData());
        assertEquals(1, (int) list.getHead().getNext().getData());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertEquals(1, (int) list.getTail().getPrevious().getData());
        assertEquals(3, list.size());

        list.removeAtIndex(1);
        assertEquals(2, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(2, (int) list.getTail().getData());


        list.addAtIndex(1, 1);
        assertEquals(0, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
        assertEquals(2, (int) list.get(2));
        assertArrayEquals(new Object[]{0, 1, 2}, list.toArray());
        assertFalse(list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testMethodsOnListWithManyItems() {
        list.addToBack(1);
        list.addToBack(3);
        list.addToBack(4);
        list.addToBack(5);
        list.addToFront(0);
        list.addAtIndex(2, 2);
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(5, (int) list.getTail().getData());
        assertEquals(1, (int) list.getHead().getNext().getData());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());
        assertEquals(4, (int) list.getTail().getPrevious().getData());
        assertEquals(6, list.size());

        list.removeLastOccurrence(1);
        assertEquals(5, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(5, (int) list.getTail().getData());

        list.removeFromFront();
        assertEquals(4, list.size());
        assertEquals(2, (int) list.getHead().getData());
        assertEquals(5, (int) list.getTail().getData());

        list.removeFromBack();
        assertEquals(3, list.size());
        assertEquals(2, (int) list.getHead().getData());
        assertEquals(4, (int) list.getTail().getData());

        list.addToFront(0);
        list.removeAtIndex(1);
        assertEquals(3, list.size());
        assertEquals(0, (int) list.getHead().getData());
        assertEquals(4, (int) list.getTail().getData());

        list.addAtIndex(1, 1);
        list.addAtIndex(2, 2);
        list.addToBack(5);

        assertEquals(0, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
        assertEquals(2, (int) list.get(2));
        assertEquals(3, (int) list.get(3));
        assertEquals(4, (int) list.get(4));
        assertEquals(5, (int) list.get(5));
        assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, list.toArray());
        assertFalse(list.isEmpty());

        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
        assertTrue(list.isEmpty());
    }
}