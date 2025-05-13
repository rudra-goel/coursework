import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * @author Christopher Tava
 * @version 1.0
 * @Colloborator 1332 TAs (I used some of the provided tests as I thought the getFirst/getLast ones were good enough)
 * @linkedinShamelessPlug (if you found the following helpful) www.linkedin.com/in/christopher-tava-04a93830b
 */

public class TavaDequeStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }
  
    @Test(timeout = TIMEOUT)
    public void testAddLast1000() {
        for (int i = 0; i < 1000; i++) {
            array.addLast("this is the " + i + " index");
        }
        assertEquals(1000, array.size());
        assertNotNull(array.getLast());
        assertNotNull(array.getFirst());
    }

    @Test(timeout = TIMEOUT)
    public void testAddFirst1000() {
        for (int i = 0; i < 1000; i++) {
            array.addFirst("this is the " + i + " index");
        }
        assertEquals(1000, array.size());
        assertNotNull(array.getLast());
        assertNotNull(array.getFirst());

    }
    @Test(timeout = TIMEOUT)
    public void testLinkedAddFirst1000() {
        for (int i = 0; i < 1000; i++) {
            linked.addFirst("this is the " + i + " index");
        }
        assertEquals(1000, linked.size());
        assertNotNull(linked.getLast());
        assertNotNull(linked.getFirst());
    }


    @Test(timeout = TIMEOUT)
    public void testLinkedAddLast1000() {
        for (int i = 0; i < 1000; i++) {
            linked.addLast("this is the " + i + " index");
        }
        assertEquals(1000, linked.size());
        assertNotNull(linked.getLast());
        assertNotNull(linked.getFirst());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFirst1000() {
        for (int i = 0; i < 1000; i++) {
            array.addFirst("this is the " + i + " index");
        }
        for (int i = array.size() - 1; i >= 0; i--) {
            array.removeFirst();
        }
        assertEquals(0, array.size());
    }
    @Test(timeout = TIMEOUT)
    public void testRemoveLast1000() {
        for (int i = 0; i < 1000; i++) {
            array.addFirst("this is the " + i + " index");
        }
        for (int i = array.size() - 1; i >= 0; i--) {
            array.removeLast();
        }
        assertEquals(0, array.size());
    }

    @Test(timeout = TIMEOUT)
    public void testNulls() {
        assertThrows(IllegalArgumentException.class, () ->
                array.addFirst(null));
        assertThrows(IllegalArgumentException.class, () ->
                array.addLast(null));
        assertThrows(NoSuchElementException.class, () ->
                array.removeFirst());
        assertThrows(NoSuchElementException.class, () ->
                array.removeLast());
        assertThrows(NoSuchElementException.class, () ->
                array.getFirst());
        assertThrows(NoSuchElementException.class, () ->
                array.getLast());

        assertThrows(IllegalArgumentException.class, () ->
                linked.addFirst(null));
        assertThrows(IllegalArgumentException.class, () ->
                linked.addLast(null));
        assertThrows(NoSuchElementException.class, () ->
                linked.removeFirst());
        assertThrows(NoSuchElementException.class, () ->
                linked.removeLast());
        assertThrows(NoSuchElementException.class, () ->
                linked.getFirst());
        assertThrows(NoSuchElementException.class, () ->
                linked.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testNullAfterRemoveFront() {
        for (int i = 0; i < 4; i++) {
            array.addFirst("this is the " + i + " index");
        }
        for (int i = 0; i < 4; i++) {
            array.removeFirst();
        }
        assertThrows(NoSuchElementException.class, () ->
                array.removeFirst());
    }
    @Test(timeout = TIMEOUT)
    public void testNullAfterRemoveLast() {
        for (int i = 0; i < 4; i++) {
            array.addFirst("this is the " + i + " index");
        }
        for (int i = 0; i < 4; i++) {
            array.removeLast();
        }
        assertThrows(NoSuchElementException.class, () ->
                array.removeLast());
    }

    @Test(timeout = TIMEOUT)
    public void testNullAfterLinkedRemoveLast() {
        for (int i = 0; i < 4; i++) {
            linked.addFirst("this is the " + i + " index");
        }
        for (int i = 0; i < 4; i++) {
            linked.removeLast();
        }
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
        assertThrows(NoSuchElementException.class, () ->
                linked.removeLast());
    }

    @Test(timeout = TIMEOUT)
    public void testNullAfterLinkedRemoveFirst() {
        for (int i = 0; i < 4; i++) {
            linked.addFirst("this is the " + i + " index");
        }
        assertEquals(4, linked.size());
        for (int i = 0; i < 4; i++) {
            linked.removeFirst();
        }
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
        assertThrows(NoSuchElementException.class, () ->
                linked.removeLast());
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
    public void testArrayDequeNoWrapAround() {
        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.addLast("2a"); // 0a, 1a, 2a,  _, _, _, _, _, _, _, _
        array.addLast("3a"); // 0a, 1a, 2a, 3a, _, _, _, _, _, _, _
        array.addLast("4a"); // 0a, 1a, 2a, 3a, 4a, _, _, _, _, _, _

        assertEquals(5, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("4a", array.getLast());

        // _, 1a, 2a, 3a, 4a, _, _, _, _, _, _
        assertEquals("0a", array.removeFirst());
        // _, _, 2a, 3a, 4a, _, _, _, _, _, _
        assertEquals("1a", array.removeFirst());
        // _, _, 2a, 3a, _, _, _, _, _, _, _
        assertEquals("4a", array.removeLast());
        // _, _, 2a, _, _, _, _, _, _, _, _
        assertEquals("3a", array.removeLast());

        assertEquals(1, array.size());
        expected[0] = null;
        expected[1] = null;
        expected[3] = null;
        expected[4] = null;
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("2a", array.getFirst());
        assertEquals("2a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeWrapAround() {
        array.addFirst("4a"); // _, _, _, _, _, _, _, _, _, _, 4a
        array.addFirst("3a"); // _, _, _, _, _, _, _, _, _, 3a, 4a
        array.addFirst("2a"); // _, _, _, _, _, _, _, _, 2a, 3a, 4a
        array.addFirst("1a"); // _, _, _, _, _, _, _, 1a, 2a, 3a, 4a
        array.addFirst("0a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[10] = "4a";
        expected[9] = "3a";
        expected[8] = "2a";
        expected[7] = "1a";
        expected[6] = "0a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("4a", array.getLast());

        // _, _, _, _, _, _, 0a, 1a, 2a, 3a, _
        assertEquals("4a", array.removeLast());

        assertEquals(4, array.size());
        expected[10] = null;
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("3a", array.getLast());

        array.addLast("5a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 5a
        array.addLast("6a"); // 6a, _, _, _, _, _, 0a, 1a, 2a, 3a, 5a

        assertEquals(6, array.size());
        expected[10] = "5a";
        expected[0] = "6a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("6a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeAdd() {
        linked.addFirst("1a"); // 1a
        linked.addFirst("0a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals(4, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("0a", cur.getData());

        LinkedNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("1a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotEquals(null, cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("3a", cur.getData());
        assertEquals(linked.getTail(), cur);
        assertNull(cur.getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeRemove() {
        linked.addFirst("1a"); // 1a
        linked.addFirst("0a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals(4, linked.size());

        assertEquals("0a", linked.removeFirst()); // 1a, 2a, 3a
        assertEquals("3a", linked.removeLast()); // 1a, 2a

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("1a", cur.getData());

        LinkedNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());
        assertEquals(linked.getTail(), cur);

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeGet() {
        linked.addLast("0a"); // 0a
        linked.addLast("1a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals("0a", linked.getFirst());
        assertEquals("3a", linked.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeAddFirst1000() {
        for (int i = 0; i < 1000; i++) {
            linked.addFirst(i + "a");
        }
        assertEquals(1000, linked.size());
        assertEquals("999a", linked.getFirst());
        linked.removeFirst();
        assertEquals("998a", linked.getFirst());
        assertEquals("0a", linked.getLast());
    }
}