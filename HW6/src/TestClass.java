
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestClass {
    public static final int TIMEOUT = 200;

    private QuadraticProbingHashMap<Integer, String> map;

    @Before
    public void initializeMap() {
        map = new QuadraticProbingHashMap<Integer, String>();
    }

    @Test(timeout = TIMEOUT)
    public void testExceptions() {
        /*
         * Exceptions for put() method
         */
        assertThrows(IllegalArgumentException.class, () -> map.put(1, null));
        assertThrows(IllegalArgumentException.class, () -> map.put(null, "a"));
        assertThrows(IllegalArgumentException.class, () -> map.put(null, null));

        /*
         * Exceptions for remove method
         */
        assertThrows(IllegalArgumentException.class, () -> map.remove(null));
        assertThrows(NoSuchElementException.class, () -> map.remove(1));
        assertThrows(NoSuchElementException.class, () -> map.remove(0));

        /*
         * Exceptions for get() method 
         */
        assertThrows(IllegalArgumentException.class, () -> map.get(null));
        assertThrows(NoSuchElementException.class, () -> map.get(1));

        /*
         * Exceptions for contains() method
         */
        assertThrows(IllegalArgumentException.class, () -> map.containsKey(null));

        /**
         * Exceptions for resizeBackingTable
         */
        assertThrows(IllegalArgumentException.class, () -> map.resizeBackingTable(map.size() - 1));
    }

    @Test(timeout = TIMEOUT)
    public void testPutting() {

        map.put(12, "a");
        map.put(7, "b");
        map.put(18, "c");
        map.put(3, "d");
        map.put(20, "e"); // first collision with <7, b> & <20, e>

        map.printTable(); 
        
        map.put(1, "f");
        map.put(15, "g");
        map.put(9, "h");
        map.put(6, "i"); // force resize since load factor > MAX_LOAD_FACTOR
        
        map.printTable();
        
        map.put(14, "j");
        map.put(4, "k");
        map.put(16, "l");
        map.put(8, "m");
        map.put(19, "n");
        map.put(11, "o");
        map.put(2, "p");
        map.put(17, "q");
        map.put(5, "r");
        map.put(13, "s"); // force resize

        map.put(56, "z");
        map.printTable();

    }

    @Test(timeout = TIMEOUT)
    public void testQuadraticResidueResize() {

        /**
         * Initial size is 13
         * put keys where the resulting index to put is 1 and it probes 6 times
         * 
         */
        map.put(1, "a");
        map.put(14, "b"); // probed 1
        map.put(27, "c"); // probed 2
        map.put(40, "d"); // probed 3
        map.put(53, "e"); // probed 4
        map.put(66, "f"); // probed 5
        map.put(79, "g"); // probed 6
        map.printTable();
        map.put(92, "h"); // force resize bc of quadratic residue
        map.printTable();
    }

    @Test(timeout = TIMEOUT)
    public void testAddOnDEL() {

        /**
         * Initial size is 13
         * put keys where the resulting index to put is 1 and it probes 6 times
         * 
         */
        map.put(1, "a");
        map.put(14, "b"); // probed 1
        map.put(27, "c"); // probed 2
        
        assertEquals("b", map.remove(14)); // del marker at index 2

        map.printTable();
        
        map.put(40, "d"); // probed 3, put at index of first del
        
        map.printTable();

    }
}
