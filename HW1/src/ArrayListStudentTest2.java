import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

// import java.util.Arrays;
import java.util.NoSuchElementException;


/**
 * Quickly written set of tests for ArrayList that ensures the proper errors are thrown and the growth mechanism works
 *
 * @author Theo Halpern
 * @version 0.1
 */
public class ArrayListStudentTest2 {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;
    private ArrayList<String> list2;

    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.addToBack("Item 0");
        list.addToBack("Item 1");
        list.addToBack("Item 2");
        list.addToBack("Item 3");




        list2 = new ArrayList<>();
    }


    @Test(timeout = TIMEOUT)
    public void testInitializationWithInteger() {
        ArrayList<Integer> integerList = new ArrayList<>();
        assertEquals(0, integerList.size());
        assertArrayEquals(new Integer[ArrayList.INITIAL_CAPACITY],
                integerList.getBackingArray());
    }

    // @Test(timeout = TIMEOUT)
    // public void testInitializationWithExampleUserClass() {
    //     ArrayList<ExampleUserClass> userList = new ArrayList<>();
    //     assertEquals(0, userList.size());
    //     assertArrayEquals(new ExampleUserClass[ArrayList.INITIAL_CAPACITY],
    //             userList.getBackingArray());
    // }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtIndexThrowsOutOfBoundsError1() {
        list.addAtIndex(-1, "tooFarForward");
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtIndexThrowsOutOfBoundsError2() {
        list.addAtIndex(-5, "wayTooFarForward");

    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtIndexThrowsOutOfBoundsError3() {
        list.addAtIndex(5, "tooFarBack");

    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtIndexThrowsOutOfBoundsError4() {
        list.addAtIndex(6, "wayTooFarBack");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addAtIndexThrowsIllegalArgumentException() {
        list.addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addToFrontThrowsIllegalArgumentException() {
        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addToBackThrowsIllegalArgumentException() {
        list.addToFront(null);

    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexThrowsIllegalArgumentException1() {
        list.removeAtIndex(-1);
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexThrowsIllegalArgumentException2() {
        list.removeAtIndex(-5);
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexThrowsIllegalArgumentException3() {
        list.removeAtIndex(5);
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexThrowsIllegalArgumentException4() {
        list.removeAtIndex(6);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFromFrontThrowsIllegalArgumentException() {
        for (int i = 0; i < 4; i++) {
            list.removeFromFront();
        }
        list.removeFromFront();

    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFromBackThrowsIllegalArgumentException() {
        for (int i = 0; i < 4; i++) {
            list.removeFromBack();
            assertEquals(3 - i, list.size());
        }

        list.removeFromBack();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getThrowsIllegalArgumentException1() {
        list.get(-1);
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getThrowsIllegalArgumentException2() {
        list.get(-5);
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getThrowsIllegalArgumentException3() {
        list.get(5);
    }
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getThrowsIllegalArgumentException4() {
        list.get(6);
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
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexWith1000ItemsPosition0() {
        for (int i = 0; i < 1000; i++) {
            list.addAtIndex(0, "this is the " + (999 - i) + "th item");
        }
        assertEquals(1004, list.size());

        for (int i = 0; i < 999; i++) {
            assertEquals("this is the " + (i) + "th item", list.get(i));
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
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

    // private static class ExampleUserClass {
    //     private int age;
    //     private String name;
    //     private UUID id;
    //     private ArrayList<ExampleUserClass> friends;

    //     /**
    //      * Creates a new user with friends
    //      *
    //      * @param age     the user's age
    //      * @param name    the user's name
    //      * @param friends an ArrayList of the user's friends
    //      */
    //     public ExampleUserClass(int age, String name, ArrayList<ExampleUserClass> friends) {
    //         this.age = age;
    //         this.name = name;
    //         this.id = UUID.randomUUID();
    //         this.friends = friends;
    //         for (int i = 0; i < friends.size(); i++) {
    //             friends.get(i).friends.addToBack(this);
    //         }
    //     }
    // }

    // Every test case from this point on is one I created myself. (Not included in original download)

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexWith1000Items() {
        String removedData = list.removeAtIndex(2);
        assertEquals("Item 2", removedData);
        System.out.println(removedData);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String removedData = list.removeFromFront();
        assertEquals("Item 0", removedData);
        System.out.println(removedData);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String removedData = list.removeFromBack();
        assertEquals("Item 3", removedData);
        System.out.println(removedData);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveFromIndexThrowsException() {
        list.removeAtIndex(6);
    }

    @Test(timeout = TIMEOUT)
    public void testGetIndex() {
        String retrievedData = list.get(2);
        assertEquals("Item 2", retrievedData);
        System.out.print(retrievedData);
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
    }
}