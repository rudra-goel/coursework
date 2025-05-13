import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;

/**
 * Some tests for the last edge cases I encountered
 *
 * @author Rohan Godha
 * @version 0.1
 */
public class DoublyLinkedListRohanTest {
  private static final int TIMEOUT = 200;
  private DoublyLinkedList<String> list;

  @Before
  public void setUp() {
    list = new DoublyLinkedList<>();
  }

  @Test(timeout = TIMEOUT)
  public void message() {
    System.out.println("add me on linkedin :)\nhttps://www.linkedin.com/in/rohan-godha-370711279/");

  }

  @Test(timeout = TIMEOUT)
  public void testAddAtFirstHalfIndex() {
    // if this test fails, csvistool.com will be VERY useful for debugging
    list.addToBack("index0");
    list.addToBack("index1002");
    list.addToBack("index1003");
    assertEquals(3, list.size());

    for (int i = 1; i < 1002; i++) {
      list.addAtIndex(i, "index" + i);
    }

    assertEquals("index0", list.get(0));
    assertEquals("index1", list.get(1));
    assertEquals("index1002", list.get(1002));

    for (int i = 0; i != list.size(); i++) {
      assertEquals("index" + i, list.get(i));
    }
  }

  @Test(timeout = TIMEOUT)
  public void testAddAtSecondHalfIndex() {
    list.addToBack("index0");
    list.addToBack("index1");
    list.addToBack("index2");

    assertEquals(3, list.size());
    list.addToBack("index1003");

    for (int i = 3; i < 1003; i++) {
      list.addAtIndex(i, "index" + i);
    }

    for (int i = 0; i != list.size(); i++) {
      assertEquals("index" + i, list.get(i));
    }
  }

  @Test(timeout = TIMEOUT)
  public void testRemoveAtFirstHalfIndex() {
    // if this fails, make sure the addAtIndex tests work before debugging it
    list.addToBack("index0");
    list.addToBack("index1002");
    list.addToBack("index1003");
    assertEquals(3, list.size());

    for (int i = 1; i < 1002; i++) {
      list.addAtIndex(i, "index" + i);
    }

    assertEquals("index0", list.get(0));
    assertEquals("index1002", list.get(1002));

    for (int i = 0; i != 1003; i++) {
      assertEquals("index" + i, list.get(i));
    }

    for (int i = 1; i < 1000; i++) {
      assertEquals("index" + i, list.removeAtIndex(1));
    }

    assertEquals("index0", list.removeFromFront());
    assertEquals("index1000", list.removeFromFront());
    assertEquals("index1001", list.removeFromFront());
    assertEquals("index1002", list.removeFromFront());
    assertEquals("index1003", list.removeFromFront());
    assertEquals(0, list.size());
  }

  @Test(timeout = TIMEOUT)
  public void testRemoveAtSecondHalfIndex() {
    // if this fails, make sure the addAtIndex tests work before debugging it
    list.addToBack("index0");
    list.addToBack("index1");
    list.addToBack("index2");

    assertEquals(3, list.size());
    list.addToBack("index1003");

    for (int i = 3; i < 1003; i++) {
      list.addAtIndex(i, "index" + i);
    }

    for (int i = 0; i != 1003; i++) {
      assertEquals("index" + i, list.get(i));
    }

    for (int i = 1000; i >= 0; i--) {
      assertEquals("index" + i, list.removeAtIndex(i));
    }

    assertEquals("index1003", list.removeFromBack());
    assertEquals("index1002", list.removeFromBack());
    assertEquals("index1001", list.removeFromBack());
    assertEquals(0, list.size());
  }

  @Test(timeout = TIMEOUT)
  public void testRemoveFrontUpdatesTail() {
    list.addToBack("index0");
    list.addToBack("index1");
    list.addToBack("index2");
    list.addToBack("index3");

    assertEquals("index0", list.removeFromFront());
    assertEquals("index1", list.getHead().getData());

    assertEquals("index1", list.removeFromFront());
    assertEquals("index2", list.getHead().getData());

    assertEquals("index2", list.removeFromFront());
    assertEquals("index3", list.getHead().getData());
    assertEquals("index3", list.getTail().getData());

    assertEquals("index3", list.removeFromFront());
    assertNull(list.getHead());
    assertNull(list.getTail());
    assertEquals(0, list.size());
  }

  @Test(timeout = TIMEOUT)
  public void testRemoveBackUpdatesHead() {
    list.addToBack("index0");
    list.addToBack("index1");
    list.addToBack("index2");
    list.addToBack("index3");

    assertEquals("index3", list.removeFromBack());
    assertEquals("index2", list.getTail().getData());

    assertEquals("index2", list.removeFromBack());
    assertEquals("index1", list.getTail().getData());

    assertEquals("index1", list.removeFromBack());
    assertEquals("index0", list.getTail().getData());
    assertEquals("index0", list.getHead().getData());

    assertEquals("index0", list.removeFromBack());
    assertNull(list.getHead());
    assertNull(list.getTail());
    assertEquals(0, list.size());
  }

  @Test(timeout = TIMEOUT)
  public void testAddToBackUpdatesHead() {
    list.addToBack("index0");
    assertEquals("index0", list.getHead().getData());
    assertEquals("index0", list.getTail().getData());
    assertEquals(1, list.size());
    assertEquals("index0", list.get(0));
  }

  @Test(timeout = TIMEOUT)
  public void testAddToFrontUpdatesTail() {
    list.addToFront("index0");
    assertEquals("index0", list.getHead().getData());
    assertEquals("index0", list.getTail().getData());
    assertEquals(1, list.size());
    assertEquals("index0", list.get(0));
  }

  @Test(timeout = TIMEOUT)
  public void testClear() {
    list.addToBack("index0");
    list.addToBack("index1");
    list.addToBack("index2");
    list.addToBack("index3");

    list.clear();
    assertNull(list.getHead());
    assertNull(list.getTail());
    assertEquals(0, list.size());
  }
}