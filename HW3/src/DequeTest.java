import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;



import static org.junit.Assert.assertThrows;

public class DequeTest {
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    @Test
    public void testArrayDequeConstructor() {
        assertEquals(0, array.size());
    }

    @Test
    public void testArrayDequeAddFirstNull() {
        assertThrows(IllegalArgumentException.class, () -> array.addFirst(null));
    }

    @Test
    public void testArrayDequeAddFirst() {
        // Test add to front where index wraps around to back of array
        array.addFirst("first");

        String[] backingArrayExpected = new String[11];
        backingArrayExpected[10] = "first";

        assertArrayEquals(backingArrayExpected, array.getBackingArray());

        // Test add to front multiple times
        array.addFirst("second");
        array.addFirst("third");

        backingArrayExpected[9] = "second";
        backingArrayExpected[8] = "third";

        assertArrayEquals(backingArrayExpected, array.getBackingArray());

        // Test add to front where array resizes
        array.addFirst("fourth");
        array.addFirst("fifth");
        array.addFirst("sixth");
        array.addFirst("seventh");
        array.addFirst("eighth");
        array.addFirst("ninth");
        array.addFirst("tenth");
        array.addFirst("eleventh");
        array.addFirst("twelfth"); // resizes this time
        array.addFirst("thirteenth");

        backingArrayExpected = new String[22];
        backingArrayExpected[21] = "thirteenth";
        backingArrayExpected[0] = "twelfth";
        backingArrayExpected[1] = "eleventh";
        backingArrayExpected[2] = "tenth";
        backingArrayExpected[3] = "ninth";
        backingArrayExpected[4] = "eighth";
        backingArrayExpected[5] = "seventh";
        backingArrayExpected[6] = "sixth";
        backingArrayExpected[7] = "fifth";
        backingArrayExpected[8] = "fourth";
        backingArrayExpected[9] = "third";
        backingArrayExpected[10] = "second";
        backingArrayExpected[11] = "first";

        assertArrayEquals(backingArrayExpected, array.getBackingArray());
    }

    @Test
    public void testArrayDequeAddLastNull() {
        assertThrows(IllegalArgumentException.class, () -> array.addLast(null));
    }

    @Test
    public void testArrayDequeAddLast() {
        // Test add to back
        array.addLast("last1");
        array.addLast("last2");

        String[] backingArrayExpected = new String[11];
        backingArrayExpected[0] = "last1";
        backingArrayExpected[1] = "last2";

        assertArrayEquals(backingArrayExpected, array.getBackingArray());

        // Test add to back multiple times
        array.addLast("last3");
        array.addLast("last4");
        array.addLast("last5");
        array.addLast("last6");

        backingArrayExpected[2] = "last3";
        backingArrayExpected[3] = "last4";
        backingArrayExpected[4] = "last5";
        backingArrayExpected[5] = "last6";

        assertArrayEquals(backingArrayExpected, array.getBackingArray());

        // Test add to back where array resizes
        array.addLast("last7");
        array.addLast("last8");
        array.addLast("last9");
        array.addLast("last10");
        array.addLast("last11");
        array.addLast("last12"); // resizes here
        array.addLast("last13");

        backingArrayExpected = new String[22];

        backingArrayExpected[0] = "last1";
        backingArrayExpected[1] = "last2";
        backingArrayExpected[2] = "last3";
        backingArrayExpected[3] = "last4";
        backingArrayExpected[4] = "last5";
        backingArrayExpected[5] = "last6";
        backingArrayExpected[6] = "last7";
        backingArrayExpected[7] = "last8";
        backingArrayExpected[8] = "last9";
        backingArrayExpected[9] = "last10";
        backingArrayExpected[10] = "last11";
        backingArrayExpected[11] = "last12";
        backingArrayExpected[12] = "last13";

        assertArrayEquals(backingArrayExpected, array.getBackingArray());
    }

    @Test
    public void testArrayDequeAddMixed() {
        array.addFirst("first");
        array.addFirst("second");
        array.addLast("third");

        String[] backingArrayExpected = new String[11];
        backingArrayExpected[10] = "first";
        backingArrayExpected[9] = "second";
        backingArrayExpected[0] = "third";

        assertArrayEquals(backingArrayExpected, array.getBackingArray());

        array.addFirst("fourth");
        array.addLast("fifth");
        array.addLast("sixth");
        array.addLast("seventh");
        array.addFirst("eighth");
        array.addLast("ninth");
        array.addFirst("tenth");
        array.addFirst("eleventh");

        backingArrayExpected[8] = "fourth";
        backingArrayExpected[1] = "fifth";
        backingArrayExpected[2] = "sixth";
        backingArrayExpected[3] = "seventh";
        backingArrayExpected[7] = "eighth";
        backingArrayExpected[4] = "ninth";
        backingArrayExpected[6] = "tenth";
        backingArrayExpected[5] = "eleventh"; // first

        assertArrayEquals(backingArrayExpected, array.getBackingArray());

        array.addFirst("twelfth");
        array.addLast("thirteenth");
        array.addLast("fourteenth");

        backingArrayExpected = new String[22];
        backingArrayExpected[0] = "twelfth";
        backingArrayExpected[1] = "eleventh";
        backingArrayExpected[2] = "tenth";
        backingArrayExpected[3] = "eighth";
        backingArrayExpected[4] = "fourth";
        backingArrayExpected[5] = "second";
        backingArrayExpected[6] = "first";
        backingArrayExpected[7] = "third";
        backingArrayExpected[8] = "fifth";
        backingArrayExpected[9] = "sixth";
        backingArrayExpected[10] = "seventh";
        backingArrayExpected[11] = "ninth";
        backingArrayExpected[12] = "thirteenth";
        backingArrayExpected[13] = "fourteenth";

        assertArrayEquals(backingArrayExpected, array.getBackingArray());
    }

    @Test
    public void testArrayDequeRemoveFirstNone() {
        assertThrows(NoSuchElementException.class, () -> array.removeFirst());
    }

    @Test
    public void testArrayDequeRemoveFirst() {
        array.addFirst("first");
        String removed = array.removeFirst();

        assertEquals("first", removed);

        array.addFirst("first");
        array.addFirst("second");
        array.addFirst("third");

        removed = array.removeFirst();
        assertEquals("third", removed);

        removed = array.removeFirst();
        assertEquals("second", removed);

        array.addFirst("fourth");
        removed = array.removeFirst();

        assertEquals("fourth", removed);

        array.addLast("fifth");
        removed = array.removeFirst();
        assertEquals("first", removed);
    }

    @Test
    public void testArrayDequeRemoveLastNone() {
        assertThrows(NoSuchElementException.class, () -> array.removeLast());
    }

    @Test
    public void testArrayDequeRemoveLast() {
        array.addLast("first");

        String removed = array.removeLast();
        assertEquals("first", removed);

        array.addLast("second");
        array.addFirst("third");
        removed = array.removeLast();
        assertEquals("second", removed);

        array.addLast("fourth");
        removed = array.removeLast();
        assertEquals("fourth", removed);
    }

    @Test
    public void testArrayDequeGetFirstNone() {
        assertThrows(NoSuchElementException.class, () -> array.getFirst());
    }

    @Test
    public void testArrayDequeGetFirst() {
        array.addFirst("first");
        String first = array.getFirst();
        assertEquals("first", first);

        array.addFirst("second");
        String second = array.getFirst();
        assertEquals("second", second);

        array.addFirst("third");
        array.addFirst("fourth");
        array.addFirst("fifth");
        String third = array.getFirst();
        assertEquals("fifth", third);
    }

    @Test
    public void testArrayDequeGetLastNone() {
        assertThrows(NoSuchElementException.class, () -> array.getLast());
    }

    @Test
    public void testArrayDequeGetLast() {
        array.addLast("first");
        String removed = array.getLast();
        assertEquals("first", removed);

        array.addLast("second");
        array.addLast("third");
        removed = array.getLast();
        assertEquals("third", removed);

        array.addLast("fourth");
        array.addFirst("fifth");
        removed = array.getLast();
        assertEquals("fourth", removed);
    }

    @Test
    public void testLinkedDequeAddFirstNull() {
        assertThrows(IllegalArgumentException.class, () -> linked.addFirst(null));
    }

    @Test
    public void testLinkedDequeAddFirst() {
        linked.addFirst("first");
        assertEquals("first", linked.getFirst());

        linked.addFirst("second");
        assertEquals("second", linked.getFirst());

        linked.addFirst("third");
        linked.addFirst("fourth");
        linked.addFirst("fifth");
        assertEquals("fifth", linked.getFirst());

        linked.addLast("sixth");
        linked.addLast("seventh");
        assertEquals("fifth", linked.getFirst());
    }

    @Test
    public void testLinkedDequeAddLastNull() {
        assertThrows(IllegalArgumentException.class, () -> linked.addLast(null));
    }

    @Test
    public void testLinkedDequeAddLast() {
        linked.addLast("first");
        assertEquals("first", linked.getLast());
        assertEquals("first", linked.getFirst());

        linked.addLast("second");
        assertEquals("second", linked.getLast());
        assertEquals("first", linked.getFirst());

        linked.addFirst("third");
        linked.addFirst("fourth");
        linked.addLast("fifth");
        assertEquals("fifth", linked.getLast());

        linked.addLast("sixth");
        linked.addLast("seventh");
        assertEquals("seventh", linked.getLast());
    }

    @Test
    public void testLinkedDequeAddMixed() {
        linked.addFirst("first");
        assertEquals("first", linked.getFirst());
        assertEquals("first", linked.getLast());

        linked.addFirst("second");
        linked.addLast("third");
        assertEquals("second", linked.getFirst());
        assertEquals("third", linked.getLast());

        linked.addFirst("fourth");
        linked.addLast("fifth");
        linked.removeLast();
        assertEquals("fourth", linked.getFirst());
        assertEquals("third", linked.getLast());
    }

    @Test
    public void testLinkedDequeRemoveFirstNone() {
        assertThrows(NoSuchElementException.class, () -> linked.removeFirst());
    }

    @Test
    public void testLinkedDequeRemoveFirst() {
        linked.addFirst("first");
        String removed = linked.removeFirst();

        assertEquals("first", removed);

        linked.addFirst("first");
        linked.addFirst("second");
        linked.addFirst("third");

        removed = linked.removeFirst();
        assertEquals("third", removed);

        removed = linked.removeFirst();
        assertEquals("second", removed);

        linked.addFirst("fourth");
        removed = linked.removeFirst();

        assertEquals("fourth", removed);

        linked.addLast("fifth");
        removed = linked.removeFirst();
        assertEquals("first", removed);
    }

    @Test
    public void testLinkedDequeRemoveLastNone() {
        assertThrows(NoSuchElementException.class, () -> linked.removeLast());
    }

    @Test
    public void testLinkedDequeRemoveLast() {
        linked.addLast("first");

        String removed = linked.removeLast();
        assertEquals("first", removed);

        linked.addLast("second");
        linked.addFirst("third");
        removed = linked.removeLast();
        assertEquals("second", removed);

        linked.addLast("fourth");
        removed = linked.removeLast();
        assertEquals("fourth", removed);
    }

    @Test
    public void testLinkedDequeGetFirstNone() {
        assertThrows(NoSuchElementException.class, () -> linked.getFirst());
    }

    @Test
    public void testLinkedDequeGetFirst() {
        linked.addFirst("first");
        String first = linked.getFirst();
        assertEquals("first", first);

        linked.addFirst("second");
        String second = linked.getFirst();
        assertEquals("second", second);

        linked.addFirst("third");
        linked.addFirst("fourth");
        linked.addFirst("fifth");
        String third = linked.getFirst();
        assertEquals("fifth", third);
    }

    @Test
    public void testLinkedDequeGetLastNone() {
        assertThrows(NoSuchElementException.class, () -> linked.getLast());
    }

    @Test
    public void testLinkedDequeGetLast() {
        linked.addLast("first");
        String removed = linked.getLast();
        assertEquals("first", removed);

        linked.addLast("second");
        linked.addLast("third");
        removed = linked.getLast();
        assertEquals("third", removed);

        linked.addLast("fourth");
        linked.addFirst("fifth");
        removed = linked.getLast();
        assertEquals("fourth", removed);
    }
}