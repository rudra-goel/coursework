import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class QuadraticProbingHashMapLLamaAITest {

    private QuadraticProbingHashMap<String, Integer> map;

    @Before
    public void setup() {
        map = new QuadraticProbingHashMap<>();
    }

    // Tests for put()

    @Test
    public void testPut_NewKey() {
        assertNull(map.put("apple", 5));
        assertEquals(1, map.size());
    }

    @Test
    public void testPut_ExistingKey() {
        map.put("apple", 5);
        assertEquals((Integer) 5, map.put("apple", 10));
        assertEquals(1, map.size());
    }

    @Test
    public void testPut_NullKey() {
        try {
            map.put(null, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(0, map.size());
        }
    }

    @Test
    public void testPut_NullValue() {
        try {
            map.put("apple", null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(0, map.size());
        }
    }

    @Test
    public void testPut_Resizing() {
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, i);
        }
        assertEquals(10, map.size());
    }

    // Tests for remove()

    @Test
    public void testRemove_ExistingKey() {
        map.put("apple", 5);
        assertEquals((Integer) 5, map.remove("apple"));
        assertEquals(0, map.size());
    }

    @Test
    public void testRemove_NonExistingKey() {
        try {
            map.remove("apple");
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertEquals(0, map.size());
        }
    }

    @Test
    public void testRemove_NullKey() {
        try {
            map.remove(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(0, map.size());
        }
    }

    // Tests for get()

    @Test
    public void testGet_ExistingKey() {
        map.put("apple", 5);
        assertEquals((Integer) 5, map.get("apple"));
    }

    @Test
    public void testGet_NonExistingKey() {
        try {
            map.get("apple");
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertEquals(0, map.size());
        }
    }

    @Test
    public void testGet_NullKey() {
        try {
            map.get(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(0, map.size());
        }
    }

    // Tests for containsKey()

    @Test
    public void testContainsKey_ExistingKey() {
        map.put("apple", 5);
        assertTrue(map.containsKey("apple"));
    }

    @Test
    public void testContainsKey_NonExistingKey() {
        assertFalse(map.containsKey("apple"));
    }

    @Test
    public void testContainsKey_NullKey() {
        try {
            map.containsKey(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(0, map.size());
        }
    }

    // Tests for keySet()

    @Test
    public void testKeySet() {
        map.put("apple", 5);
        map.put("banana", 10);
        Set<String> keys = map.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("apple"));
        assertTrue(keys.contains("banana"));
    }

    // Tests for values()

    @Test
    public void testValues() {
        map.put("apple", 5);
        map.put("banana", 10);
        List<Integer> values = map.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(5));
        assertTrue(values.contains(10));
    }

    // Tests for resizeBackingTable()

    @Test
    public void testResizeBackingTable() {
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, i);
        }
        assertEquals(10, map.size());
        map.resizeBackingTable(20);
        assertEquals(10, map.size());
    }

    // Tests for clear()

    @Test
    public void testClear() {
        map.put("apple", 5);
        map.put("banana", 10);
        map.clear();
        assertEquals(0, map.size());
    }@Test
    public void testGet_EmptyMap() {
        try {
            map.get("apple");
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertEquals(0, map.size());
        }
    }

    @Test
    public void testGet_SingleEntry() {
        map.put("apple", 5);
        assertEquals((Integer) 5, map.get("apple"));
        assertEquals(1, map.size());
    }

    @Test
    public void testGet_MultipleEntries() {
        map.put("apple", 5);
        map.put("banana", 10);
        assertEquals((Integer) 5, map.get("apple"));
        assertEquals((Integer) 10, map.get("banana"));
        assertEquals(2, map.size());
    }


    @Test
    public void testGet_KeyAfterResize() {
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, i);
        }
        assertEquals((Integer) 5, map.get("key5"));
        assertEquals(10, map.size());
    }
    @Test
    public void testContainsKey_EmptyMap() {
        assertFalse(map.containsKey("apple"));
    }

    @Test
    public void testContainsKey_SingleEntry() {
        map.put("apple", 5);
        assertTrue(map.containsKey("apple"));
    }

    @Test
    public void testContainsKey_MultipleEntries() {
        map.put("apple", 5);
        map.put("banana", 10);
        assertTrue(map.containsKey("apple"));
        assertTrue(map.containsKey("banana"));
    }



    @Test
    public void testContainsKey_KeyAfterResize() {
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, i);
        }
        assertTrue(map.containsKey("key5"));
    }

    @Test
    public void testContainsKey_RemovedKey() {
        map.put("apple", 5);
        map.remove("apple");
        assertFalse(map.containsKey("apple"));
    }
    @Test
    public void testKeySet_EmptyMap() {
        assertTrue(map.keySet().isEmpty());
    }

    @Test
    public void testKeySet_SingleEntry() {
        map.put("apple", 5);
        Set<String> keys = map.keySet();
        assertEquals(1, keys.size());
        assertTrue(keys.contains("apple"));
    }

    @Test
    public void testKeySet_MultipleEntries() {
        map.put("apple", 5);
        map.put("banana", 10);
        Set<String> keys = map.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("apple"));
        assertTrue(keys.contains("banana"));
    }

    @Test
    public void testKeySet_KeyAfterResize() {
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, i);
        }
        Set<String> keys = map.keySet();
        assertEquals(10, keys.size());
        assertTrue(keys.contains("key5"));
    }

    @Test
    public void testKeySet_Modification() {
        map.put("apple", 5);
        Set<String> keys = map.keySet();
        map.remove("apple");
        assertEquals(1, keys.size());
        assertTrue(keys.contains("apple"));
    }
    @Test
    public void testValues_EmptyMap() {
        assertTrue(map.values().isEmpty());
    }

    @Test
    public void testValues_SingleEntry() {
        map.put("apple", 5);
        List<Integer> values = map.values();
        assertEquals(1, values.size());
        assertTrue(values.contains(5));
    }

    @Test
    public void testValues_MultipleEntries() {
        map.put("apple", 5);
        map.put("banana", 10);
        List<Integer> values = map.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(5));
        assertTrue(values.contains(10));
    }

    @Test
    public void testValues_KeyAfterResize() {
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, i);
        }
        List<Integer> values = map.values();
        assertEquals(10, values.size());
        assertTrue(values.contains(5));
    }

    @Test
    public void testValues_Modification() {
        map.put("apple", 5);
        List<Integer> values = map.values();
        map.remove("apple");
        assertEquals(1, values.size());
        assertTrue(values.contains(5));
    }


    @Test
    public void testResizeBackingTable_SmallerSize() {
        try {
            for (int i = 0; i < 6; i++) {
                map.put("key" + i, i);
            }
            map.resizeBackingTable(5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals(6, map.size());
        }
    }

    @Test
    public void testResizeBackingTable_LargerSize() {
        map.put("apple", 5);
        map.resizeBackingTable(20);
        assertEquals(1, map.size());
        assertTrue(map.containsKey("apple"));
    }

    @Test
    public void testResizeBackingTable_SameSize() {
        map.put("apple", 5);
        map.resizeBackingTable(13);
        assertEquals(1, map.size());
        assertTrue(map.containsKey("apple"));
    }

    @Test
    public void testResizeBackingTable_MultipleResizes() {
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, i);
        }
        map.resizeBackingTable(20);
        map.resizeBackingTable(30);
        assertEquals(10, map.size());
        assertTrue(map.containsKey("key5"));
    }
    @Test
    public void testClear_EmptyMap() {
        map.clear();
        assertTrue(map.size() == 0);
    }

    @Test
    public void testClear_NonEmptyMap() {
        map.put("apple", 5);
        map.clear();
        assertTrue(map.size() == 0);
    }

    @Test
    public void testClear_MultipleClears() {
        map.put("apple", 5);
        map.clear();
        map.clear();
        assertTrue(map.size() == 0);
    }

    @Test
    public void testClear_AfterResize() {
        for (int i = 0; i < 10; i++) {
            map.put("key" + i, i);
        }
        map.resizeBackingTable(20);
        map.clear();
        assertTrue(map.size() == 0);
    }

}