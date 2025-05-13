import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


public class TestCases {
    private static final int TIMEOUT = 1000;
    private DoublyLinkedList<String> list;
    private static String[] words = {
                                        "a", "b", "c", "d", "e", "f", "g",
                                        "h", "i", "j", "k", "l", "m", "n",
                                        "n", "o", "p", "q", "r", "s", "t",
                                        "u", "v", "w", "x", "y", "z"
                                    };

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testAdding() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAtIndex(-1, "0a"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAtIndex(10, "0a"));
        int addedCounter = 0;

        for (int i = 0; i < 10000; i++) {
            int addIndex = (int)(Math.random() * list.size() * 1.5);
            int grabFromWordIndex = (int)(Math.random() * 26);

            if (addIndex > list.size()) {
                assertThrows(IndexOutOfBoundsException.class, () -> list.addAtIndex(addIndex, words[grabFromWordIndex]));
                continue;
            }

            list.addAtIndex(addIndex, words[grabFromWordIndex]);

            addedCounter++;
            if (addIndex == list.size()) {
                assertEquals(words[grabFromWordIndex], list.getTail());
            } else if (addIndex == 0){
                assertEquals(words[grabFromWordIndex], list.getHead().getData());
            }
            assertEquals(words[grabFromWordIndex], list.get(addIndex));
            
            assertEquals(addedCounter, list.size());

        }
        list.clear();

        assertEquals(0, list.size());

        for(int i = 0; i < 10; i++) {
            list.addToFront("CS");
            list.addToBack("1332");
            assertEquals(2 + 2*i, list.size());
        }

        assertThrows(IllegalArgumentException.class, () -> list.addToFront(null));
        assertThrows(IllegalArgumentException.class, () -> list.addToBack(null));
    }

    @Test(timeout = TIMEOUT)
    public void testAddingV2() {
        //add 100 elements to the list
        for(int i = 0; i < 100; i++) {
            String toBeAdded = new String("New String " + i);
            list.addToBack(toBeAdded);
        }

        //Then add strings to random indices and verify a node some x elements down or forward is where they are supposed to be

        for(int i = 0; i < 10; i++) {
            int randomIndex = (int)(Math.random() * list.size());

            int otherRandomIndex = (int)(Math.random() * list.size());
            String dataToVerify = list.get(otherRandomIndex);

            list.addAtIndex(randomIndex, "Data: " + randomIndex);

            //this means that the data to verify sits beyond the added index
            //thus it should move down one
            //we are making sure the data moves down one
            if (otherRandomIndex >= randomIndex) {
                assertEquals(dataToVerify, list.get(otherRandomIndex+1));
            } else { // this is when the data to verify index is before the added index => data should stay the same
                assertEquals(dataToVerify, list.get(otherRandomIndex));
            }

            assertEquals(list.get(randomIndex), "Data: " + randomIndex);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoving() {


        list.addToFront("This is Head");
        for(int i = 0; i < 10; i++) {
            list.addToBack("data");
        }
        list.addToBack("This is tail");

        assertEquals(12, list.size());

        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(list.size()));

        assertEquals("data", list.removeAtIndex(1));
        assertEquals("This is Head", list.removeAtIndex(0));
        assertEquals("This is tail", list.removeAtIndex(list.size()-1));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurrence() {
        list.addToBack("1");
        list.addToBack("2");
        list.addToBack("3");
        list.addToBack("3");
        list.addToBack("4");
        list.addToBack("5");
        list.addToBack("6");
        list.addToBack("6");
        list.addToBack("7");
        list.addToBack("8");
        list.addToBack("9");
        list.addToBack("3");
        list.addToBack("6");
        list.addToBack("1");
        list.addToBack("2");
        list.addToBack("1");

        DoublyLinkedListNode current;

        current = list.getHead();
        while(current.getNext() != null) {
            System.out.print(current.getData() + ", ");
            current = current.getNext();
        }
        System.out.println(current.getData());

        list.removeLastOccurrence("1");
        
        current = list.getHead();
        while(current.getNext() != null) {
            System.out.print(current.getData() + ", ");
            current = current.getNext();
        }
        System.out.println(current.getData());
        
        list.removeLastOccurrence("2");
        
        current = list.getHead();
        while(current.getNext() != null) {
            System.out.print(current.getData() + ", ");
            current = current.getNext();
        }
        System.out.println(current.getData());
        
        list.removeLastOccurrence("5");
        
        current = list.getHead();
        while(current.getNext() != null) {
            System.out.print(current.getData() + ", ");
            current = current.getNext();
        }
        System.out.println(current.getData());
        
    }

}
