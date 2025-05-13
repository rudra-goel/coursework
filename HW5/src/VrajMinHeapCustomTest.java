import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class VrajMinHeapCustomTest {

    private MinHeap<Item> testHeap;
    private PriorityQueue<Item> referenceHeap;


    @Before
    public void init() {
        testHeap = new MinHeap<>();
        referenceHeap = new PriorityQueue<>();
    }

    @Test
    public void testBuildEmptyHeap() {
        testHeap = new MinHeap<Item>(new ArrayList<>());

        assertTrue(testHeap.isEmpty());
    }

    @Test
    public void testBuildNullHeap() {
        assertThrows(IllegalArgumentException.class, () -> testHeap = new MinHeap<>(null));
    }

    @Test
    public void testBuildHeapWithNull() {
        ArrayList<Item> a = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            a.add(new Item(i));
        }
        a.add(null);
        for (int i = 6; i < 10; i++) {
            a.add(new Item(i));
        }

        assertThrows(IllegalArgumentException.class, () -> testHeap = new MinHeap<>(a));
    }

    @Test
    public void testBuildHeapInOrder() {
        ArrayList<Item> a = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            a.add(new Item(i, true));
            referenceHeap.add(a.get(i));
        }

        testHeap = new MinHeap<>(a);

        assertValidHeap();
    }

    @Test
    public void testBuildHeapReverseOrder() {
        ArrayList<Item> a = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            a.add(new Item(14 - i, true));
            referenceHeap.add(a.get(i));
        }

        testHeap = new MinHeap<>(a);

        assertValidHeap();
    }

    // Goes through 100 random permutations of a build heap (but with the same seed for consistency)
    @Test
    public void testBuildHeapRandomOrder() {
        Random random = new Random(2148951495104L); // Setting the seed in advance to keep this consistent

        ArrayList<Item> a = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            a.add(new Item(14 - i, true));
            referenceHeap.add(a.get(i));
        }

        for (int i = 0; i < 100; i++) {
            Collections.shuffle(a, random);
            testHeap = new MinHeap<>(a);
            assertValidHeap();
        }
    }

    @Test
    public void testAddNull() {
        assertThrows(IllegalArgumentException.class, () -> testHeap.add(null));

        assertValidHeap();
    }

    @Test
    public void testAddNullToNonEmpty() {
        for (int i = 0; i < 50; i++) {
            add(i);
        }

        assertThrows(IllegalArgumentException.class, () -> testHeap.add(null));

        assertValidHeap();
    }

    @Test
    public void testAddInOrder() {
        for (int i = 0; i < 50; i++) {
            add(i);
        }

        assertEquals(52, ((Comparable[])testHeap.getBackingArray()).length);
    }

    @Test
    public void testAddReverseOrder() {
        for (int i = 49; i >= 0; i--) {
            add(i);
        }

        assertEquals(52, ((Comparable[])testHeap.getBackingArray()).length);
    }

    @Test
    public void testAddRandomOrder() {
        Random random = new Random(2148951495104L); // Setting the seed in advance to keep this consistent

        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            order.add(i);
        }


        for (int i = 0; i < 100; i++) {
            Collections.shuffle(order, random);
            for (Integer item : order) {
                add(item);
            }

            assertEquals(52, ((Comparable[])testHeap.getBackingArray()).length);

            referenceHeap.clear();
            testHeap.clear();

            assertEquals(13, ((Comparable[])testHeap.getBackingArray()).length);
        }
    }

    @Test
    public void testRemoveOne() {
        Item a = add(5);

        remove();

        assertValidHeap();

        assertThrows(NoSuchElementException.class, testHeap::remove);
    }

    @Test
    public void testRemoveAll() {
        for (int i = 0; i < 50; i++) {
            add(i);
        }

        int current = remove();
        for (int i = 0; i < 49; i++) {
            int x = remove();

            assertTrue(x > current);
            current = x;
        }

        assertThrows(NoSuchElementException.class, testHeap::remove);
    }

    @Test
    public void testRemoveFromEmpty() {
        assertThrows(NoSuchElementException.class, testHeap::remove);
    }

    private int remove() {
        int t = referenceHeap.peek().value;
        assertSame(referenceHeap.remove(), testHeap.remove());

        assertValidHeap();

        return t;
    }

    private Item add(int value) {
        Item item = new Item(value, true);
        referenceHeap.add(item);
        testHeap.add(item);

        assertValidHeap();

        return item;
    }

    private void assertValidHeap() {
        assertEquals("Heaps had different sizes", referenceHeap.size(), testHeap.size());
        assertEquals("Heap isEmpty and reference isEmpty did not match", referenceHeap.isEmpty(), testHeap.isEmpty());

        Comparable[] inner = testHeap.getBackingArray();

        TreeSet<Item> values = new TreeSet<>(); // Make sure that there are no duplicates

        assertNull("index 0 not null", inner[0]);
        for (int i = 1; i <= testHeap.size(); i++) {
            assertTrue("Heap contained an item that it shouldn't: " + inner[i], referenceHeap.contains((Item) inner[i]));
            assertTrue("Heap contained copy instead of original item", ((Item) inner[i]).original);
            if (i != 1) {
                assertTrue("order property not satisfied by " + i, inner[i].compareTo(inner[i >> 1]) > 0);
            }

            assertFalse("Heap contained a duplicate value!", values.contains(inner[i]));
            values.add((Item) inner[i]);
        }

        for (int i = testHeap.size() + 1; i < inner.length; i++) {
            assertNull("space after final object not null", inner[i]);
        }

        assertEquals("Size changed unexpectedly", referenceHeap.size(), values.size());
    }

    private static final class Item implements Comparable<Item> {
        final int value;
        final boolean original;

        public Item(int value) {
            this(value, false);
        }

        public Item(int value, boolean original) {
            this.value = value;
            this.original = original;
        }

        @Override
        public int compareTo(Item o) {
            return this.value - o.value;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Item)) {
                return false;
            }
            return this.compareTo((Item)o) == 0;
        }

        @Override
        public int hashCode() {
            return 42;
        }

        @Override
        public String toString() {
            return "Item{" + value + "}";
        }
    }

    private void assertThrows(Class<? extends Exception> exceptionClass, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (!exceptionClass.isInstance(e)) {
                throw new AssertionFailedError("Runnable did not throw correct exception\n\tException expected: " +
                        exceptionClass.getName() + "\n\tException received: " + e.getClass().getName() + ": " + e.getMessage());
            }
            return;
        }
        throw new AssertionFailedError("Runnable did not throw exception\n\tExpected exception: " + exceptionClass.getName());
    }
}