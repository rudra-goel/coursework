import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

// A (probably completely overkill) set of tests for BST
public class VrajBSTCustomTest {

    TreeSet<Item> referenceSet;
    BST<Item> testSet;

    @Before
    public void setup() {
        referenceSet = new TreeSet<>();
        testSet = new BST<>();
    }

    @Test
    public void testEmptyInit() {
        assertNull(testSet.getRoot());
        assertEquals(0, testSet.size());
        assertEquals(-1, testSet.height());
    }

    @Test
    public void testSortedPreInit() {
        Item[] items = new Item[]{new Item(5), new Item(4), new Item(3), new Item(2)};
        testSet = new BST<>(List.of(items[0], items[1], items[2], items[3]));

        assertEquals(4, testSet.size());
        assertEquals(3, testSet.height());

        for (int i = 0; i < 4; i++) {
            assertSame(items[i], testSet.get(new Item(5-i)));
        }

        assertSame(items[0], testSet.getRoot().getData());
        BSTNode<Item> current = testSet.getRoot();

        for (int i = 0; i < 4; i++) {
            assertSame(items[i], current.getData());
            current = current.getLeft();
        }
    }

    @Test
    public void testReverseSortedPreInit() {
        Item[] items = new Item[]{new Item(0), new Item(1), new Item(2), new Item(3)};
        testSet = new BST<>(List.of(items[0], items[1], items[2], items[3]));

        assertEquals(4, testSet.size());
        assertEquals(3, testSet.height());

        for (int i = 0; i < 4; i++) {
            assertSame(items[i], testSet.get(new Item(i)));
        }

        assertSame(items[0], testSet.getRoot().getData());
        BSTNode<Item> current = testSet.getRoot();

        for (int i = 0; i < 4; i++) {
            assertSame(items[i], current.getData());
            current = current.getRight();
        }
    }

    @Test
    public void testRandomSortedPreInitWithDups() {
        Item[] items = new Item[]{new Item(0), new Item(5), new Item(7), new Item(3), new Item(2), new Item(4), new Item(2)};

        testSet = new BST<>(List.of(items[0], items[1], items[2], items[3], items[4], items[5], items[6]));
        referenceSet.addAll(Arrays.asList(items));

        assertValidBST();
    }

    @Test
    public void testAddDups() {
        addItem(5);
        addItem(7);
        addItem(3);
        addItem(5);
        addItem(5);
        addItem(7);

        assertValidBST();
    }

    @Test
    public void testAddSameRow() {
        addItem(0);
        addItem(1);
        addItem(2);
        addItem(2);
        addItem(0);
        addItem(3);
        addItem(4);


        assertValidBST();
    }

    @Test
    public void testAddReverseRow() {
        addItem(5);
        addItem(4);
        addItem(3);
        addItem(4);
        addItem(2);
        addItem(1);

        assertValidBST();
    }

    @Test
    public void testRemoveRoot() {
        Item x = addItem(5);

        removeItem(x);

        assertValidBST();
        assertNull(testSet.getRoot());
    }

    @Test
    public void testRemoveRootWithRightChild() {
        Item x = addItem(5);
        Item y = addItem(8);
        addItem(6);
        addItem(9);

        removeItem(x);

        assertValidBST();
        assertSame(y, testSet.getRoot().getData());
    }

    @Test
    public void testRemoveRootWithLeftChild() {
        Item x = addItem(5);
        Item y = addItem(2);
        addItem(1);
        addItem(3);

        removeItem(x);

        assertValidBST();
        assertSame(y, testSet.getRoot().getData());
    }

    @Test
    public void testRemoveRootWithTwoChildren() {
        Item x = addItem(5);

        addItem(3);
        addItem(2);
        addItem(4);

        addItem(10);
        addItem(9);
        Item y = addItem(7);
        addItem(8);
        addItem(11);

        removeItem(x);

        assertValidBST();
        assertSame(y, testSet.getRoot().getData());
    }

    @Test
    public void testRemoveArbitraryLeaf() {
        Item[] data = fillPreMadeTree();

        removeItem(data[3]); // 5
        assertValidBST();

        removeItem(data[13]); // 56
        assertValidBST();

        addItem(5);
        assertValidBST();

        addItem(56);
        assertValidBST();
    }

    @Test
    public void testRemoveArbitraryNodeWithLeftChild() {
        Item[] data = fillPreMadeTree();

        removeItem(data[2]); // 10
        assertValidBST();

        removeItem(data[9]); // 60
        assertValidBST();

        addItem(60);
        assertValidBST();

        addItem(10);
        assertValidBST();
    }

    @Test
    public void testRemoveArbitraryNodeWithRightChild() {
        Item[] data = fillPreMadeTree();

        removeItem(data[8]); // 50
        assertValidBST();

        removeItem(data[5]); // 25
        assertValidBST();

        addItem(25);
        assertValidBST();

        addItem(50);
        assertValidBST();
    }

    @Test
    public void testRemoveArbitraryNodeWithTwoChildren() {
        Item[] data = fillPreMadeTree();

        removeItem(data[1]); // 20
        assertValidBST();

        removeItem(data[10]); // 55
        assertValidBST();

        addItem(55);
        assertValidBST();

        addItem(20);
        assertValidBST();
    }

    @Test
    public void testRemoveAll() {
        Item[] data = fillPreMadeTree();


        // Order intentionally maximizes the number of cases with 2 children
        int[] removalOrder = new int[]{
                4, 10, 13, 0, 1, 5, 6, 7, 11, 12, 9, 2, 8, 3
        };

        for (int i : removalOrder) {
            removeItem(data[i]);
            assertValidBST();
        }
    }

    @Test
    public void testCreateWithNulls() {
        List<Item> onlyNull = new ArrayList<>();
        onlyNull.add(null);
        assertThrows(IllegalArgumentException.class, () -> testSet = new BST<>(onlyNull));
        assertValidBST();


        List<Item> listWithNulls = new ArrayList<>();
        listWithNulls.add(new Item(0));
        listWithNulls.add(new Item(5));
        listWithNulls.add(new Item(0));
        listWithNulls.add(null);
        listWithNulls.add(new Item(6));

        assertThrows(IllegalArgumentException.class, () -> testSet = new BST<>(listWithNulls));
        assertValidBST();
    }

    @Test
    public void testAddNull() {
        assertThrows(IllegalArgumentException.class, () -> testSet.add(null));
        assertValidBST();

        assertThrows(IllegalArgumentException.class, () -> testSet.add(null));
        assertValidBST();

        fillPreMadeTree();

        assertThrows(IllegalArgumentException.class, () -> testSet.add(null));
        assertValidBST();

        assertThrows(IllegalArgumentException.class, () -> testSet.add(null));
        assertValidBST();
    }

    @Test
    public void testRemoveMissingAndNull() {
        assertThrows(IllegalArgumentException.class, () -> testSet.remove(null));
        assertValidBST();

        assertThrows(IllegalArgumentException.class, () -> testSet.remove(null));
        assertValidBST();

        assertThrows(NoSuchElementException.class, () -> testSet.remove(new Item(10)));
        assertValidBST();

        assertThrows(NoSuchElementException.class, () -> testSet.remove(new Item(5)));
        assertValidBST();

        assertThrows(NoSuchElementException.class, () -> testSet.remove(new Item(10)));
        assertValidBST();

        assertThrows(IllegalArgumentException.class, () -> testSet.remove(null));
        assertValidBST();

        fillPreMadeTree();

        assertThrows(IllegalArgumentException.class, () -> testSet.remove(null));
        assertValidBST();

        assertThrows(IllegalArgumentException.class, () -> testSet.remove(null));
        assertValidBST();

        assertThrows(NoSuchElementException.class, () -> testSet.remove(new Item(13234)));
        assertValidBST();

        assertThrows(NoSuchElementException.class, () -> testSet.remove(new Item(17844)));
        assertValidBST();

        assertThrows(NoSuchElementException.class, () -> testSet.remove(new Item(13234)));
        assertValidBST();

        assertThrows(IllegalArgumentException.class, () -> testSet.remove(null));
        assertValidBST();
    }

    @Test
    public void testOneNodeRemoveTwice() {
        Item x = addItem(5643);
        assertValidBST();

        removeItem(x);
        assertValidBST();

        assertThrows(NoSuchElementException.class, () -> testSet.remove(x));
        assertValidBST();
    }

    @Test
    public void testRemoveTwice() {
        fillPreMadeTree();
        testOneNodeRemoveTwice();
    }

    @Test
    public void testGetContainsNull() {
        assertThrows(IllegalArgumentException.class, () -> testSet.get(null));
        assertThrows(IllegalArgumentException.class, () -> testSet.contains(null));
        assertValidBST();

        fillPreMadeTree();

        assertThrows(IllegalArgumentException.class, () -> testSet.get(null));
        assertThrows(IllegalArgumentException.class, () -> testSet.contains(null));
        assertValidBST();
    }

    @Test
    public void testGetMissingElement() {
        assertThrows(NoSuchElementException.class, () -> testSet.get(new Item(352)));
        assertValidBST();

        fillPreMadeTree();

        assertThrows(NoSuchElementException.class, () -> testSet.get(new Item(2451)));
        assertValidBST();

        assertThrows(NoSuchElementException.class, () -> testSet.get(new Item(2451)));
        assertValidBST();

        int[] itemsThroughRange = new int[]{2, 22, 51, 54, 70};
        for (int i : itemsThroughRange) {
            assertThrows(NoSuchElementException.class, () -> testSet.get(new Item(i)));
            assertValidBST();
        }
    }

    @Test
    public void testContains() {
        assertFalse(testSet.contains(new Item(0)));
        assertFalse(testSet.contains(new Item(5)));
        assertFalse(testSet.contains(new Item(0)));
        assertValidBST();

        fillPreMadeTree();
        assertValidBST(); // Checks all true values for contains

        assertFalse(testSet.contains(new Item(2)));
        assertFalse(testSet.contains(new Item(22)));
        assertFalse(testSet.contains(new Item(51)));
        assertFalse(testSet.contains(new Item(54)));
        assertFalse(testSet.contains(new Item(70)));
    }

    @Test
    public void testEmptyTraversals() {
        assertEquals(0, testSet.preorder().size());
        assertEquals(0, testSet.inorder().size());
        assertEquals(0, testSet.postorder().size());
        assertEquals(0, testSet.levelorder().size());

        assertEquals(0, testSet.preorder().size());
        assertEquals(0, testSet.inorder().size());
        assertEquals(0, testSet.postorder().size());
        assertEquals(0, testSet.levelorder().size());
        assertValidBST();
    }

    @Test
    public void testRootOnlyTraversals() {
        Item root = addItem(352);
        List<Item> desiredTraversal = List.of(root);

        assertIterableSame(desiredTraversal, testSet.preorder());
        assertIterableSame(desiredTraversal, testSet.postorder());
        assertIterableSame(desiredTraversal, testSet.inorder());
        assertIterableSame(desiredTraversal, testSet.levelorder());
    }

    @Test
    public void testPreOrderTraversals() {
        Item[] preorder = fillPreMadeTree();

        assertIterableSame(Arrays.asList(preorder), testSet.preorder());

        assertValidBST();
    }

    @Test
    public void testPostOrderTraversals() {
        Item[] preorder = fillPreMadeTree();

        // This mess just gets the actual items from their codes, im too lazy to do it by hand
        int[] postOrderRaw = new int[]{5, 10, 26, 25, 32, 30, 20, 52, 56, 57, 55, 60, 50, 40};
        List<Item> postOrder = codify(preorder, postOrderRaw);

        assertIterableSame(postOrder, testSet.postorder());

        assertValidBST();
    }

    @Test
    public void testInOrderTraversals() {
        // Finally an easy one to test!
        fillPreMadeTree();

        List<Item> inorder = testSet.inorder();

        assertEquals(referenceSet.size(), inorder.size());
        for (int i = 0; i < inorder.size() - 1; i++) {
            assertTrue(inorder.get(i).compareTo(inorder.get(i + 1)) < 0);
            assertSame(inorder.get(i), referenceSet.ceiling(inorder.get(i)));
        }

        assertSame(inorder.get(inorder.size() - 1), referenceSet.ceiling(inorder.get(inorder.size() - 1)));

        assertValidBST();
    }

    @Test
    public void testLevelOrderTraversals() {
        Item[] preorder = fillPreMadeTree();

        // This mess just gets the actual items from their codes, im too lazy to do it by hand
        int[] levelOrderRaw = new int[]{40, 20, 50, 10, 30, 60, 5, 25, 32, 55, 26, 52, 57, 56};
        List<Item> levelOrder = codify(preorder, levelOrderRaw);

        assertIterableSame(levelOrder, testSet.levelorder());
        assertValidBST();
    }

    @Test
    public void testHeightOfEmpty() {
        assertEquals(-1, testSet.height());
        assertValidBST();
    }

    @Test
    public void testHeightOfRoot() {
        addItem(1);
        assertEquals(0, testSet.height());
        assertValidBST();
    }

    @Test
    public void testHeight() {
        fillPreMadeTree();
        assertEquals(5, testSet.height());
    }

    @Test
    public void testClearEmpty() {
        testSet.clear();
        referenceSet.clear();
        assertValidBST();

        assertNull(testSet.getRoot());
        assertEquals(0, testSet.size());
        assertEquals(-1, testSet.height());
    }

    @Test
    public void testClear() {
        fillPreMadeTree();
        testSet.clear();
        referenceSet.clear();
        assertValidBST();

        assertNull(testSet.getRoot());
        assertEquals(0, testSet.size());
        assertEquals(-1, testSet.height());
    }

    @Test
    public void testPathRootToRoot() {
        Item x = addItem(1);
        assertIterableSame(List.of(x), testSet.findPathBetween(new Item(1), new Item(1)));
    }

    // Check all 196 possible paths on the sample tree (this one is a doozy)
    // Also, this only works if the preorder traversal works
    @Test
    public void testAllPairwiseRoots() {
        Item[] items = fillPreMadeTree();

        List<Item> preorder = testSet.preorder();
        Map<Item, Integer> traversalIndexes = IntStream.range(0, preorder.size())
                .boxed().collect(Collectors.toMap(preorder::get, i -> i));

        for (Item item : items) {
            for (Item value : items) {
                List<Item> path = testSet.findPathBetween(item.copy(), value.copy());

                // Ensure that path starts and ends correctly
                assertSame(item, path.get(0));
                assertSame(value, path.get(path.size() - 1));

                // Ensure that there are no duplicates
                assertTrue(path.stream().allMatch(new HashSet<>()::add));

                // Ensure that all elements in path are in the set as well
                for (Item itemOnPath : path) {
                    assertSame(referenceSet.ceiling(itemOnPath), itemOnPath);
                }

                // Ensure that the path is valid
                // First convert to index on preorder traveral
                List<Integer> indexes = path.stream().map(traversalIndexes::get).collect(Collectors.toList());
                boolean dec = true;
                for (int k = 1; k < path.size(); k++) {
                    if (indexes.get(k) < indexes.get(k - 1)) {
                        assertTrue(dec);
                    } else {
                        dec = false;
                    }
                }
            }
        }
    }

    @Test
    public void testNullTraversal() {
        Item[] items = fillPreMadeTree();

        for (Item item : items) {
            assertThrows(IllegalArgumentException.class, () -> testSet.findPathBetween(item.copy(), null));
            assertThrows(IllegalArgumentException.class, () -> testSet.findPathBetween(null, item.copy()));
        }

        assertThrows(IllegalArgumentException.class, () -> testSet.findPathBetween(null, null));

        assertValidBST();
    }

    @Test
    public void testMissingValueTraversal() {
        int[] missing = new int[]{2, 22, 51, 54, 70};
        Item[] items = fillPreMadeTree();

        for (Item item : items) {
            for (int m : missing) {
                assertThrows(NoSuchElementException.class, () -> testSet.findPathBetween(item.copy(), new Item(m)));
                assertThrows(NoSuchElementException.class, () -> testSet.findPathBetween(new Item(m), item.copy()));
            }
        }

        for (int i : missing) {
            for (int j : missing) {
                assertThrows(NoSuchElementException.class, () -> testSet.findPathBetween(new Item(i), new Item(j)));
            }
        }

        assertValidBST();
    }

    private List<Item> codify(Item[] actualItems, int[] itemCodes) {
        List<Item> coded = new ArrayList<>();
        for (int i = 0; i < itemCodes.length; i++) {
            for (int j = 0; j < itemCodes.length; j++) {
                if (actualItems[j].compareTo(new Item(itemCodes[i])) == 0) {
                    coded.add(actualItems[j]);
                }
            }
        }
        return coded;
    }



    /** Return set sorted in pre-order (total 14 nodes) */
    private Item[] fillPreMadeTree() {
        //              40
        //            /     \
        //           20     50
        //          /  \      \
        //         10  30     60
        //         /   /\     /
        //        5  25  32  55
        //            \      / \
        //             26   52  57
        //                      /
        //                     56

        Item[] items = new Item[]{
        addItem(40), // 0
        addItem(20), // 1
        addItem(10), // 2
        addItem(5),  // 3
        addItem(30), // 4
        addItem(25), // 5
        addItem(26), // 6
        addItem(32), // 7
        addItem(50), // 8
        addItem(60), // 9
        addItem(55), // 10
        addItem(52), // 11
        addItem(57), // 12
        addItem(56)  // 13
        };

        assertValidBST();
        return items;
    }

    private Item addItem(int item) {
        Item object = new Item(item);
        referenceSet.add(object);
        testSet.add(object);

        assertEquals("Size incorrect after add", referenceSet.size(), testSet.size());

        return object;
    }

    private void removeItem(Item item) {
        referenceSet.remove(item);
        assertSame("Removed item was incorrect", item, testSet.remove(item));
        assertEquals("Size incorrect after remove", referenceSet.size(), testSet.size());
    }

    private void assertValidBST() {
        assertEquals("BST had wrong size", referenceSet.size(), testSet.size());

        if (referenceSet.isEmpty()) {
            assertNull(testSet.getRoot());
            assertEquals(-1, testSet.height());
            return;
        }

        for (Item i : referenceSet) {
            assertSame("Item missing from BST", i, testSet.get(i));
            assertTrue("Item not found by contains", testSet.contains(i));
        }

        ArrayList<BSTNode<Item>> dfsStack = new ArrayList<>();

        dfsStack.add(testSet.getRoot());

        int counter = 0;

        while (!dfsStack.isEmpty()) {
            counter++;
            BSTNode<Item> node = dfsStack.remove(dfsStack.size() - 1);
            assertTrue("Incorrect entry in tree", referenceSet.contains(node.getData()));
            assertSame("Original item not stored", referenceSet.ceiling(node.getData()), node.getData());

            if (node.getRight() != null) {
                assertTrue("Right child not greater than parent", node.getData().compareTo(node.getRight().getData()) < 0);
                dfsStack.add(node.getRight());
            }

            if (node.getLeft() != null) {
                assertTrue("Left child not less than parent", node.getData().compareTo(node.getLeft().getData()) > 0);
                dfsStack.add(node.getLeft());
            }
        }

        assertEquals("Given size not consistent with actual size", referenceSet.size(), counter);
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

    private <T extends Iterable<Item>, R extends Iterable<Item>> void assertIterableSame(T a, R b) {
        Iterator<Item> aIt = a.iterator();
        Iterator<Item> bIt = b.iterator();

        while (aIt.hasNext()) {
            assertTrue("Lists have different sizes", bIt.hasNext());
            assertSame("Lists did not match", aIt.next(), bIt.next());
        }

        assertFalse("Lists have different sizes", bIt.hasNext());
    }

    static class Item implements Comparable<Item> {
        private final int code;

        public Item(int code) {
            this.code = code;
        }

        public Item copy() {
            return new Item(code);
        }

        public Integer getValue() {
            return code;
        }

        @Override
        public int compareTo(Item o) { // Note that this isn't just -1 and 1!
            return this.code - o.code;
        }

        @Override
        public boolean equals(Object o) { // Equals doesn't work! -> don't use it!
            // return false;
            return this.compareTo((Item) o) == 0;

        }

        @Override
        public int hashCode() { // Hash code doesn't work! -> don't use it!
            return 0;
        }

        @Override
        public String toString() {
            return "Item{" + code + '}';
        }
    }
}