import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HashMapCustomTest {

    private HashMap<Integer, String> referenceInt;
    private QuadraticProbingHashMap<Integer, String> testInt;

    @Before
    public void setUp() {
        referenceInt = new HashMap<>();
        testInt = new QuadraticProbingHashMap<>();
    }

    @After
    public void tearDown() {
        Set<Integer> items = new HashSet<>(testInt.keySet());

        for (Integer i : items) {
            assertTrue(testInt.containsKey(i));
            String value = testInt.get(i);
            assertSame(value, testInt.remove(i));

            assertFalse(testInt.containsKey(i));
            assertThrows(NoSuchElementException.class, () -> testInt.remove(i));
        }

        assertEquals(0, testInt.size());
    }
    @Test
    public void testConstruction() {
        assertEquals(testInt.getTable().length, 13);

        testInt = new QuadraticProbingHashMap<>(43);
        assertEquals(testInt.getTable().length, 43);

        testInt = new QuadraticProbingHashMap<>(86);
        assertEquals(testInt.getTable().length, 86);
    }

    @Test
    public void testInsertSimple() {
        insert(referenceInt, testInt, 5, "a");

        insert(referenceInt, testInt, 434, "fewfw");
        insert(referenceInt, testInt, 41256, "wa");
        insert(referenceInt, testInt, 16467, "dfeaf");
        insert(referenceInt, testInt, 47264, "bw");

    }

    @Test
    public void testInsertSame() {
        insert(referenceInt, testInt, 5, "a");
        insert(referenceInt, testInt, 5, "b");
        insert(referenceInt, testInt, 5, "c");
    }

    @Test
    public void testInsertDummyHash() {
        HashMap<Dummy, Integer> ref = new HashMap<>();
        QuadraticProbingHashMap<Dummy, Integer> test = new QuadraticProbingHashMap<>(9887);

        for (int i = 0; i < 250; i++) { // Should not need to resize
            insert(ref, test, new Dummy(i), i);
        }
        assertEquals(test.getTable().length, 9887);
    }
    
    @Test
    public void testInsertDummyHashWithResize() {
        HashMap<Dummy, Integer> ref = new HashMap<>();
        QuadraticProbingHashMap<Dummy, Integer> test = new QuadraticProbingHashMap<>(13);

        for (int i = 0; i < 250; i++) {
            System.out.println("Inserting Dummy with Key: " + i);
            insert(ref, test, new Dummy(i), i);
        }
    }

    @Test
    public void testForceIntegerOverflow() {
        // This test is so nitpicky that it probably won't even be graded
        // Basically, if you aren't careful, you can force an integer overflow with this specific sequence of inputs

        testInt = new QuadraticProbingHashMap<>(100109);

        for (int i = 0; i < 47000; i++) {
            // Set up hash map so that next insertion at an index that collides with 0 overflows
            int index = (int) ( (1 + (((long)i) * i)) % 100109);
            testInt.put(index, "");
        }

        assertEquals(testInt.getTable().length, 100109);
        testInt.put(1001091, "joker");

        QuadraticProbingMapEntry[] t = testInt.getTable();
        for (int i = 0; i < 47000; i++) {
            int index = (int) ( (1 + (((long)i)) * i) % 100109);
            assertEquals(t[index].getValue(), "");
        }
        assertEquals(t[(int) ( (1 + (((long)47000) * 47000)) % 100109)].getValue(), "joker");
    }

    @Test
    public void testPutNull() {
        assertThrows(IllegalArgumentException.class, () -> testInt.put(null, "not null"));
        assertThrows(IllegalArgumentException.class, () -> testInt.put(0, null));

        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void testSimpleRemove() {
        insert(referenceInt, testInt, 5, "a");
        remove(referenceInt, testInt, 5);

        insert(referenceInt, testInt, 7, "b");
        remove(referenceInt, testInt, 7);
    }

    @Test
    public void testPutRemovePut() {
        for (int i = 0; i < 4; i++) {
            System.out.println("inserting <5,v{" + i + "}> into map");

            insert(referenceInt, testInt, 5, "v{" + i + "}");
            
            remove(referenceInt, testInt, 5);
        }
    }

    @Test
    public void testRemoveMissingAndNull() {
        assertThrows(IllegalArgumentException.class, () -> testInt.remove(null));
        assertValidHashMap(referenceInt, testInt);

        assertThrows(NoSuchElementException.class, () -> remove(referenceInt, testInt, 5));
        assertValidHashMap(referenceInt, testInt);

        insert(referenceInt, testInt, 5, "a");
        remove(referenceInt, testInt, 5);

        assertThrows(NoSuchElementException.class, () -> remove(referenceInt, testInt, 5));
        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void testCollisionResize() {
        QuadraticProbingHashMap<Dummy, String> test = new QuadraticProbingHashMap<>();
        HashMap<Dummy, String> reference = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            insert(reference, test, new Dummy(i), "v{"+i+"}");
        }
        assertEquals(13, test.getTable().length);

        insert(reference, test, new Dummy(7), "v{"+7+"}");
        assertEquals(27, test.getTable().length);
        verifyHashIndexes(test);
    }

    @Test
    public void testResizeOnCapacity() {
        testInt = new QuadraticProbingHashMap<>(100);
        for (int i = 0; i < 67; i++) {
            insert(referenceInt, testInt, i, "v{"+i+"}");
        }
        assertEquals(100, testInt.getTable().length);
        insert(referenceInt, testInt, 67, "v{67}");
        assertEquals(201, testInt.getTable().length);
        verifyHashIndexes(testInt);
    }

    @Test
    public void testCustomResize() {
        int[] items = new int[]{1, 2, 32, 4, 124, 84, 12, 7, 9, 30};
        for (int i : items) {
            insert(referenceInt, testInt, i, "v{"+i+"}");
        }

        for (int newSize = items.length; newSize < 80; newSize++) {
            testInt.resizeBackingTable(newSize);
            for (int i : items) {
                assertTrue("On resize, key " + i + " was missing", testInt.containsKey(i));
                assertEquals(testInt.get(i), "v{"+i+"}");
            }
            verifyHashIndexes(testInt);
        }
    }

    @Test
    public void testCustomResizeWithCollisions() {
        testInt.resizeBackingTable(35);
        for (int i = 0; i < 6; i++) {
            insert(referenceInt, testInt, 2 + 13*i, "v{"+i+"}");
        }
        insert(referenceInt, testInt, 18, "v{18}");


        testInt.resizeBackingTable(13);
        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void testCollisionThenResize() {
        for (int i = 0; i < 7; i++) {
            insert(referenceInt, testInt, 2 + 13*i, "v{"+i+"}");
        }
        assertEquals(13, testInt.getTable().length);
        insert(referenceInt, testInt, 20, "v{20}");
        insert(referenceInt, testInt, 33, "v{33}");

        assertEquals(27, testInt.getTable().length);
        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void testResizeThenCollision() {
        for (int i = 0; i < 8; i++) {
            insert(referenceInt, testInt, 2 + 27*i, "v{"+i+"}");
        }
        assertEquals(13, testInt.getTable().length);
        insert(referenceInt, testInt, 20, "v{20}");

        assertEquals(27, testInt.getTable().length);
        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void testCollisionBeforeAfterResize() {
        testInt.resizeBackingTable(43);
        for (int i = 0; i < 8; i++) { // Collide after
            insert(referenceInt, testInt, 2 + 87*i, "v{"+i+"}");
            insert(referenceInt, testInt, 23 + 87*i, "v{"+i+"}");
        }
        assertEquals(43, testInt.getTable().length);

        for (int i = 0; i < 8; i++) {
            insert(referenceInt, testInt, 9 + 43*i, "v{"+i+"}");
        }

        assertEquals(43, testInt.getTable().length);

        for (int i = 24; i < 43*.67; i++) {
            insert(referenceInt, testInt, -i*7, "v{-"+i+"}");

        }

        assertEquals(87, testInt.getTable().length);

        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void testInvalidResize() {
        assertThrows(IllegalArgumentException.class, () -> testInt.resizeBackingTable(-1));
        assertThrows(IllegalArgumentException.class, () -> testInt.resizeBackingTable(-124));

        for (int i = 0; i < 15; i++) {
            insert(referenceInt, testInt, i, "v{"+i+"}");
        }

        assertThrows(IllegalArgumentException.class, () -> testInt.resizeBackingTable(0));
        assertThrows(IllegalArgumentException.class, () -> testInt.resizeBackingTable(-124));
        assertThrows(IllegalArgumentException.class, () -> testInt.resizeBackingTable(14));

        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void testClear() {
        for (int i = 0; i < 150; i++) {
            insert(referenceInt, testInt, i, "v{"+i+"}");
        }

        testInt.clear();

        assertEquals(13, testInt.getTable().length);
        assertEquals(0, testInt.size());

        for (QuadraticProbingMapEntry i : testInt.getTable()) {
            assertNull(i);
        }
    }

    @Test
    public void test1000Entries() {
        for (int i = 0; i < 1000; i++) {
            insert(referenceInt, testInt, 5*i+7, "v{"+(5*i + 7)+"}");
        }
    }

    @Test
    public void testGetMissingAndNull() {
        assertThrows(IllegalArgumentException.class, () -> testInt.get(null));
        assertValidHashMap(referenceInt, testInt);

        assertThrows(NoSuchElementException.class, () -> testInt.get(5));
        assertValidHashMap(referenceInt, testInt);

        insert(referenceInt, testInt, 5, "a");
        remove(referenceInt, testInt, 5);

        assertThrows(NoSuchElementException.class, () -> testInt.get(5));
        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void testContainsNull() {
        assertThrows(IllegalArgumentException.class, () -> testInt.containsKey(null));

        insert(referenceInt, testInt, 5, "a");
        insert(referenceInt, testInt, 6, "b");

        assertThrows(IllegalArgumentException.class, () -> testInt.containsKey(null));
        
        assertValidHashMap(referenceInt, testInt);
    }

    @Test
    public void getValueList() {
        testInt.resizeBackingTable(107);

        List<Integer> toInsert = new ArrayList<>();
        for (int i = 0; i < 35; i++){
            toInsert.add(i*2+3);
        }
        Collections.shuffle(toInsert);

        for (int i : toInsert) {
            insert(referenceInt, testInt, i, "v{"+i+"}");
        }

        List<String> v = testInt.values();
        assertEquals(35, v.size());
        for (int i = 0; i < 35; i++) {
            assertEquals("v{"+(i*2+3)+"}", v.get(i));
        }

    }

    private <K, V> void insert(HashMap<K, V> reference, QuadraticProbingHashMap<K, V> test, K key, V value) {
        V r = reference.put(key, value);
        V t = test.put(key, value);

        System.out.println("Referenced Put: " + r);
        System.out.println("Test Put: " + t);
        System.out.println(referenceInt.toString());
        testInt.printTable();
        System.out.println();
        assertSame("returned object did not match old value on put", r, t);
        assertValidHashMap(reference, test);
    }

    private <K, V> void remove(HashMap<K, V> reference, QuadraticProbingHashMap<K, V> test, K key) {
        V r = reference.remove(key);
        V t = test.remove(key);
        assertSame("returned object did not match old value on remove", r, t);
        assertValidHashMap(reference, test);
    }

    private <K, V> void assertValidHashMap(HashMap<K, V> reference, QuadraticProbingHashMap<K, V> test) {
        assertEquals("Hash map size incorrect", reference.size(), test.size());
        assertEquals("Hash map isEmpty is incorrect", reference.isEmpty(), test.keySet().isEmpty());

        assertEquals("Hash map key sets did not match", reference.keySet(), test.keySet());

        for (K key : reference.keySet()) {
            // System.out.println("checking for key " + key);
            assertEquals("Hash maps differed at key " + key, reference.get(key), test.get(key));
        }

        QuadraticProbingMapEntry[] table = test.getTable();
        Set<K> keySet = reference.keySet();

        // Make sure each key appears only once
        for (K key : keySet) {
            boolean seen = false;
            for (int i = 0; i < table.length; i++) {
                if (table[i] == null || !table[i].getKey().equals(key)) continue;

                if (!table[i].isRemoved()) assertTrue("Incorrect key present in hashmap", keySet.contains(key));

                assertFalse("Key appeared twice (duplicate may be a removed entry that" +
                        " was not correctly reassigned)", seen);
                seen = true;
            }
        }

        verifyHashIndexes(test);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private <K, V> void verifyHashIndexes(QuadraticProbingHashMap<K, V> test) {
        QuadraticProbingMapEntry[] table = test.getTable();

        Set<K> keySet = test.keySet();
        Map<K, Integer> remainingKeySet = keySet.stream().collect(Collectors.toMap(e -> e, e -> -1));
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j * 2 <= table.length; j++) {
                int index = (int)((i + ((long)j)*j)%table.length);
                if (table[index] == null) break;

                if (table[index].isRemoved()) assertFalse("Entry marked removed in key set", keySet.contains(table[index].getKey()));
                else if (Math.abs(table[index].getKey().hashCode()%table.length) == i) {
                    assertTrue("Entry in table not in key set: " + table[index].getKey(), remainingKeySet.get(table[index].getKey()) == -1 || remainingKeySet.get(table[index].getKey()) == index);
                    remainingKeySet.put((K) table[index].getKey(), index);
                }
            }
        }

        for (Map.Entry<K, Integer> entry : remainingKeySet.entrySet()) {
            assertNotEquals("Hash map contained misplaced key " + entry, Integer.valueOf(-1), entry.getValue());

        }
    }

    private void assertThrows(Class<? extends Exception> exceptionClass, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (!exceptionClass.isInstance(e)) {
                throw new AssertionFailedError("Runnable did not throw correct exception");
            }
            return;
        }
        throw new AssertionFailedError("Runnable did not throw exception");
    }

    class Dummy {
        public final int x;
        public Dummy(int x) { this.x = x; }
        @Override public int hashCode() { return 42; }
        public boolean equals(Dummy o) { return o.x == this.x; }
        @Override public String toString() { return "{"+x+"}"; }
    }
}