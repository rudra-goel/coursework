import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Quickly written set of tests for DoublyLinkedList that ensures the proper errors are thrown
 * and basic opperations work
 *
 * @author Theo Halpern
 * @version 0.1
 * <p>
 * Collaborators: Matthew David Warren
 */

public class DoublyLinkedListTheoTest {
    private static final int TIMEOUT = 200;
    private DoublyLinkedList<String> list;
    private DoublyLinkedList<String> list2;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
        list.addToBack("added first");
        list.addToBack("added second");
        list.addToBack("added third");
        list.addToBack("added forth");

        list2 = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitializationWithInteger() {
        DoublyLinkedList<Integer> integerList = new DoublyLinkedList<>();
        assertEquals(0, integerList.size());
        assertNull(integerList.getHead());
        assertNull(integerList.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testInitializationWithExampleUserClass() {
        DoublyLinkedList<ExampleUserClass> userList = new DoublyLinkedList<>();
        assertEquals(0, userList.size());
        assertNull(userList.getHead());
        assertNull(userList.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void addAtIndexThrowsOutOfBoundsError() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.addAtIndex(-1, "tooFarFoward"));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.addAtIndex(-5, "wayTooFarFoward"));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.addAtIndex(5, "tooFarBack"));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.addAtIndex(6, "wayTooFarBack"));
    }

    @Test(timeout = TIMEOUT)
    public void addAtIndexThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                list.addAtIndex(0, null));

    }

    @Test(timeout = TIMEOUT)
    public void addToFrontThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                list.addToFront(null));

    }

    @Test(timeout = TIMEOUT)
    public void addToBackThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                list.addToFront(null));

    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexThrowsIllegalArgumentException() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.removeAtIndex(-1));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.removeAtIndex(-5));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.removeAtIndex(4));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.removeAtIndex(5));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.removeAtIndex(6));

    }

    @Test(timeout = TIMEOUT)
    public void removeFromFrontThrowsNoSuchElementException() {
        for (int i = 0; i < 4; i++) {
            list.removeFromFront();
        }
        assertThrows(NoSuchElementException.class, () ->
                list.removeFromFront());

    }

    @Test(timeout = TIMEOUT)
    public void removeFromBackThrowsNoSuchElementException() {
        for (int i = 0; i < 4; i++) {
            list.removeFromBack();
        }
        assertThrows(NoSuchElementException.class, () ->
                list.removeFromBack());

    }

    @Test(timeout = TIMEOUT)
    public void removeLastOccurrenceActuallyRemoves() {
        list.removeLastOccurrence("added first");
        assertEquals(list.get(0), "added second");
        assertEquals(list.size(), 3);
        assertEquals(list.getHead().getData(), "added second");
        list.removeLastOccurrence("added forth");
        assertEquals(list.get(list.size() - 1), "added third");
        assertEquals(list.size(), 2);
        assertEquals(list.getTail().getData(), "added third");
        list.removeLastOccurrence("added second");
        list.removeLastOccurrence("added third");
        assertEquals(list.size(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void removeLastOccurrenceTest2() {
        list.removeFromBack();
        list.removeFromFront();
        list.removeFromBack();


        assertEquals("added second", list.getHead().getData());
        assertEquals("added second", list.getTail().getData());
        
        assertThrows(NoSuchElementException.class, () -> list.removeLastOccurrence("added first"));
        
        list.removeLastOccurrence("added second");
        
        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());

    }

    @Test(timeout = TIMEOUT)
    public void removeLastOccurrenceThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                list.removeLastOccurrence(null));

    }

    @Test(timeout = TIMEOUT)
    public void removeLastOccurrenceNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () ->
                list.removeLastOccurrence("number 5 is not in the list?"));

    }

    @Test(timeout = TIMEOUT)
    public void getThrowsIllegalArgumentException() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.get(-5));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.removeAtIndex(4));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.get(5));
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.get(6));

    }

    @Test(timeout = TIMEOUT)
    public void toArrayWorks() {
        Object[] toArrayRes = list.toArray();
        assertEquals(toArrayRes.length, list.size());
        assertEquals(toArrayRes[1], "added second");
        assertEquals(toArrayRes[3], "added forth");
    }

    @Test(timeout = TIMEOUT)
    public void toArrayOnEmptyList() {
        list.clear();
        Object[] toArrayRes = list.toArray();
        assertEquals(toArrayRes.length, 0);
        assertEquals(toArrayRes.length, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexWith1000Items() {
        for (int i = 0; i < 1000; i++) {
            list.addAtIndex(2, "this is the " + (1001 - i) + "th item");
        }
        assertEquals(1004, list.size());

        for (int i = 2; i < 1002; i++) {
            assertEquals("this is the " + (i) + "th item", list.get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontWith1000Items() {
        for (int i = 0; i < 1000; i++) {
            list.addToFront("this is the " + (1000 - i - 1) + "th item");
        }
        assertEquals(1004, list.size());

        for (int i = 0; i < 1000; i++) {
            assertEquals("this is the " + (i) + "th item", list.get(i));
        }
    }
    @Test(timeout = TIMEOUT)
    public void testAddToBackWith1000Items() {

        for (int i = 0; i < 1000; i++) {
            list.addToBack("this is the " + (i + 4) + "th item");
        }
        assertEquals(1004, list.size());

        for (int i = 4; i < 1000; i++) {
            assertEquals("this is the " + (i) + "th item", list.get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexWith1000ItemsLastIndex() {
        for (int i = 0; i < 1000; i++) {
            list.addAtIndex(list.size(), "this is the " + (i + 4) + "th item");
        }
        assertEquals(1004, list.size());

        for (int i = 4; i < 1004; i++) {
            assertEquals("this is the " + (i) + "th item", list.get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexWith1000ItemsRandomIndex() {
        for (int i = 0; i < 1000; i++) {
            list.addAtIndex((int) (Math.random() * list.size()), "this is the " + (i + 4) + "th item");
        }
        assertEquals(1004, list.size());
    }


    // Every test case from this point on was created Matthew Warren for the ArrayList and updated by Theo for the
    // linked list.

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexWith1000Items() {
        String removedData = list.removeAtIndex(2);
        assertEquals("added third", removedData);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String removedData = list.removeFromFront();
        assertEquals("added first", removedData);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String removedData = list.removeFromBack();
        assertEquals("added forth", removedData);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                list.removeAtIndex(6));
    }

    @Test(timeout = TIMEOUT)
    public void testGetIndex() {
        String retrievedData = list.get(2);
        assertEquals("added third", retrievedData);
    }


    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        assertFalse(list.isEmpty());
        assertTrue(list2.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    private static class ExampleUserClass {
        private int age;
        private String name;
        private UUID id;
        private DoublyLinkedList<ExampleUserClass> friends;

        /**
         * Creates a new user with friends
         *
         * @param age     the user's age
         * @param name    the user's name
         * @param friends an DoublyLinkedList of the user's friends
         */
        public ExampleUserClass(int age, String name, DoublyLinkedList<ExampleUserClass> friends) {
            this.age = age;
            this.name = name;
            this.id = UUID.randomUUID();
            this.friends = friends;
            for (int i = 0; i < friends.size(); i++) {
                friends.get(i).friends.addToBack(this);
            }
        }
    }
}