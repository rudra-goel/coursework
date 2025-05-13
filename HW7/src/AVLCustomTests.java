import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class AVLCustomTests {

    private AVL<TestItem> test;
    private TreeSet<TestItem> reference;

    private static final String[] complements = {"You got this!", "You can do it!",
            "This is the last data structure, you are almost there!"};
    private static final String[] happy = {"Yayayayayayay!", "Woohoo!", "Yipee!"};
    @Rule
    public TestWatcher watch = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            System.out.println("Oh no! You failed the " + description + " test cast! Here is the current shape" +
                    " of the tree to help you debug.");
            printTree(test);
            System.out.println(complements[(int)(3*Math.random())] + " I believe in you!\n\t- Vraj Parikh");
        }

        @Override
        protected void succeeded(Description description) {
            System.out.println(happy[(int)(3*Math.random())] + " You passed the " + description + " test case!");
        }
    };

    @Before
    public void setup() {
        test = new AVL<>();
        reference = new TreeSet<>();
    }

    @After
    public void teardown() {

        List<TestItem> items = reference.stream().toList();
        for (TestItem item : items) {
            assertTrue(test.contains(item));
            assertSame(item, test.get(item));
            remove(item.value);
        }
    }

    @Test
    public void testSimpleAdd() {
        for (int i : new int[]{3, 43, 1, 5, 4, 12, 9}) add(i);
    }

    @Test
    public void testAddDuplicates() {
        for (int i : new int[]{3, 43, 3, 3, 4, 5, 9, 43, 3, 55, 5}) {
            add(i);
        }
    }

    @Test
    public void testSimpleAddRightRotation() {
        //     10
        //     /
        //    9        => add 7
        //   /
        //  8
        for (int i : new int[]{10, 9, 8, 7}) add(i);
    }

    @Test
    public void testSimpleAddLeftRotation() {
        // Reverse of previous
        for (int i : new int[]{10, 11, 12, 13}) add(i);
    }

    @Test
    public void testSimpleAddRightLeftRotation() {
        //     10
        //      \
        //       11        => add 12
        //        \
        //         13
        for (int i : new int[]{10, 11, 13, 12}) add(i);
    }

    @Test
    public void testSimpleAddLeftRightRotation() {
        // Reverse of previous
        for (int i : new int[]{10, 9, 7, 8}) add(i);
    }

    @Test
    public void testComplexAddRightRotation() {
        //            10
        //           / \
        //          5   17
        //         / \   \         => add 1
        //        3   7   20
        //       / \
        //      2   4

        for (int i : new int[]{10, 5, 17, 3, 7, 20, 2, 4, 1}) add(i);
    }

    @Test
    public void testComplexAddLeftRotation() {
        // Same structure as previous, but mirrored across root
        for (int i : new int[]{10, 5, 17, 3, 7, 20, 2, 4, 1}) add(30 - i);
    }

    @Test
    public void testComplexAddLeftRightRotation() {
        //            10
        //           / \
        //          5   17
        //         / \   \         => add 4
        //        2   7   20
        //       / \
        //      1   3

        for (int i : new int[]{10, 5, 17, 2, 7, 20, 1, 3, 4}) add(i);
    }

    @Test
    public void testComplexAddRightLeftRotation() {
        // Same as previous, but mirrored across root

        for (int i : new int[]{10, 5, 17, 2, 7, 20, 1, 3, 4}) add(30 - i);
    }

    @Test
    public void testAddNull() {
        assertThrows(IllegalArgumentException.class, () -> test.add(null));
        assertThrows(IllegalArgumentException.class, () -> test.add(null));

        add(5);
        add(10);

        assertThrows(IllegalArgumentException.class, () -> test.add(null));
        assertThrows(IllegalArgumentException.class, () -> test.add(null));

        verifyTree();
    }

    @Test
    public void testConstructorEmpty() {
        test = new AVL<TestItem>(List.of());

        verifyTree();
    }

    @Test
    public void testNullConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new AVL<TestItem>(null));

        List<TestItem> listWithNulls = new ArrayList<>();
        listWithNulls.add(new TestItem(5));
        listWithNulls.add(new TestItem(7));
        listWithNulls.add(null);
        listWithNulls.add(new TestItem(9));

        assertThrows(IllegalArgumentException.class, () -> new AVL<TestItem>(listWithNulls));
    }

    @Test
    public void testConstructorWithRotations() {
        // These are all the same inputs from previous tests
        // While they technically should be in different tests, I figured that if you passed the tests
        // above, then this one should pass unless you are doing something really wrong.

        for (Integer[] i: new Integer[][]{
                {3, 43, 1, 5, 4, 12, 9},
                {3, 43, 3, 3, 4, 5, 9, 43, 3, 55, 5},
                {10, 9, 8, 7},
                {10, 11, 12, 13},
                {10, 9, 7, 8},
                {10, 11, 13, 12},
                {10, 5, 17, 3, 7, 20, 2, 4, 1},
                {10, 5, 17, 2, 7, 20, 1, 3, 4},
                Arrays.stream(new Integer[]{10, 5, 17, 2, 7, 20, 1, 3, 4}).map(i -> 30 - i).toArray(Integer[]::new),
                Arrays.stream(new Integer[]{10, 5, 17, 2, 7, 20, 1, 3, 4}).map(i -> 30 - i).toArray(Integer[]::new),
        }){
            List<TestItem> l = Arrays.stream(i).map(TestItem::new).toList();
            reference = new TreeSet<>(l);
            test = new AVL<>(l);
            verifyTree();
        }
    }

    @Test
    public void testRemoveRightRotation() {
        //       10
        //      /  \
        //     5    15
        //    /
        //   2
        for (int i : new int[]{10, 5, 15, 2}) add(i);

        remove(15);
    }

    @Test
    public void testRemoveLeftRotation() {
        // Reverse of previous
        for (int i : new int[]{10, 5, 15, 2}) add(30 - i);

        remove(15);
    }

    @Test
    public void testRemoveLeftRightRotation() {
        //       10
        //      /  \
        //     5    15
        //      \
        //       7
        for (int i : new int[]{10, 5, 15, 7}) add(i);

        remove(15);
    }

    @Test
    public void testRemoveRightLeftRotation() {
        // Reverse of previous
        for (int i : new int[]{10, 5, 15, 7}) add(30 - i);

        remove(15);
    }

    @Test
    public void testRemoveComplexRightRotation() {
        // Yeah, I'm not drawing this tree in the comments, it took long enough to
        // just figure out the order of these numbers
        for (int i : new int[]{20, 10, 40, 5, 15, 35, 45, 2, 7, 12, 17, 30, 37, 43, 50, 1, 3, 6, 13, 55, 0}) add(i);

        remove(15);
    }

    @Test
    public void testRemoveComplexLeftRotation() {
        // Reverse of previous
        for (int i : new int[]{20, 10, 40, 5, 15, 35, 45, 2, 7, 12, 17, 30, 37, 43, 50, 1, 3, 6, 13, 55, 0}) add(70 - i);

        remove(70 - 15);
    }

    @Test
    public void testRemoveComplexLeftRightRotation() {
        // See prior comments
        for (int i : new int[]{20, 10, 40, 5, 15, 35, 45, 2, 7, 12, 17, 30, 37, 43, 50, 1, 3, 6, 8, 13, 55, 9}) add(i);

        remove(15);
    }

    @Test
    public void testRemoveComplexRightLeftRotation() {
        // See prior comments
        for (int i : new int[]{20, 10, 40, 5, 15, 35, 45, 2, 7, 12, 17, 30, 37, 43, 50, 1, 3, 6, 13, 55, 9}) add(70 - i);

        remove(70 - 15);
    }

    @Test
    public void testAdd200Increasing() {
        int[] l = IntStream.rangeClosed(1, 200).toArray();
        for (int i : l) add(i);
    }

    @Test
    public void testAdd200Decreasing() {
        int[] l = IntStream.rangeClosed(1, 200).toArray();
        for (int i = 199; i >= 0; i--) add(l[i]);
    }

    @Test
    public void testAdd1000Random() {
        ArrayList<Integer> insertionOrder = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(insertionOrder);

        for (int i : insertionOrder) add(i);

        Collections.shuffle(insertionOrder);

        for (int i : insertionOrder) remove(i);
    }

    @Test
    public void testPredecessorOfOneItem() {
        add(5);
        assertNull(test.predecessor(new TestItem(5)));
        assertNull(test.predecessor(new TestItem(5)));
    }

    @Test
    public void testPredecessorOfTwoIncreasingItems() {
        add(5);
        add(6);
        assertNull(test.predecessor(new TestItem(5)));
        assertEquals(new TestItem(5), test.predecessor(new TestItem(6)));
    }

    @Test
    public void testMissingAndNullPredecessor(){
        assertThrows(IllegalArgumentException.class, () -> test.predecessor(null));
        assertThrows(NoSuchElementException.class, () -> test.predecessor(new TestItem(5)));
        for (int i = 0; i < 10; i++) add(i);

        assertThrows(NoSuchElementException.class, () -> test.predecessor(new TestItem(11)));
        assertThrows(NoSuchElementException.class, () -> test.predecessor(new TestItem(-1)));

    }

    @Test
    public void deepestFromEmptyTree() {
        assertNull(test.maxDeepestNode());
    }

    @Test
    public void deepestJustRoot() {
        TestItem a = add(5);
        assertSame(a, test.maxDeepestNode());
    }

    @Test
    public void testDeepestTieAtRight() {
        add(5);
        add(4);
        add(7);
        add(6);
        TestItem a = add(8);
        assertSame(a, test.maxDeepestNode());
    }

    @Test
    public void testDeepestNotRightmost() {
        add(5);
        add(3);
        add(7);
        TestItem a = add(4);
        add(2);

        assertSame(a, test.maxDeepestNode());
    }

    @Test
    public void testPredecessorIsAncestor() {
        add(5);
        TestItem a = add(3);
        add(4);

        assertSame(a, test.predecessor(new TestItem(4)));
        assertNull(test.predecessor(new TestItem(3)));
    }


    public TestItem add(int i) {
        TestItem item = new TestItem(i);
        reference.add(item);
        test.add(item);

        PrintAVL.printTree(test);

        verifyTree();

        return item;
    }

    public void remove(int i) {
        TestItem item = reference.ceiling(new TestItem(i));
        if (item == null || item.value != i) {
            throw new RuntimeException("This shouldn't happen! There was a mistake with the test case! " +
                    "Please comment on Piazza and I'll try to fix it.");
        }

        TestItem altItem = test.remove(new TestItem(i));

        assertEquals("Remove returned the wrong item!", item, altItem);
        assertSame("Remove returned a duplicate of the item instead of the original reference", item, altItem);
        reference.remove(item);

        verifyTree();
    }

    public void verifyTree() {
        assertEquals("Expected size of AVL did not match actual size", reference.size(), test.size());

        recursiveVerifyTreeHelper(test.getRoot(), TestItem.MIN_VALUE, TestItem.MAX_VALUE);
    }

    private void recursiveVerifyTreeHelper(AVLNode<TestItem> root, TestItem mn, TestItem mx) {
        if (root == null) return;

        assertTrue("Data in node " + root + " is greater than a parent to the right", root.getData().compareTo(mx) < 0);
        assertTrue("Data in node " + root + " is less than a parent to the left ("+mn+")", root.getData().compareTo(mn) > 0);
        assertEquals("Balance factor of node " + root + " is incorrect with respect to its children",
                ((root.getLeft()==null)?-1:root.getLeft().getHeight()) - ((root.getRight()==null)?-1:root.getRight().getHeight()), root.getBalanceFactor());
        assertEquals("Height of node " + root + " is incorrect with respect to its children",
                1 + Math.max(((root.getLeft()==null)?-1:root.getLeft().getHeight()), ((root.getRight()==null)?-1:root.getRight().getHeight())), root.getHeight());

        assertTrue("Tree contained node " + root + ", even though it shouldn't have", reference.contains(root.getData()));

        assertTrue("Node " + root + " is not balanced", Math.abs(root.getBalanceFactor()) <= 1);

        recursiveVerifyTreeHelper(root.getLeft(), mn, root.getData());
        recursiveVerifyTreeHelper(root.getRight(), root.getData(), mx);
    }

    private void printNTimes(String print, int n) {
        for (int i = 0; i < n; i++) System.out.print(print);
    }

    public <T extends Comparable<T>> void printTree(AVL<T> tree) {

        //        |-------^-------|
        //       aaaaa               aaaaa
        //    |----^----|         |----^----|
        //
        List<AVLNode<T>> nodesInLevel = new ArrayList<>();
        nodesInLevel.add(tree.getRoot());
        int[] gaps = new int[tree.getRoot().getHeight() + 1];
        gaps[tree.getRoot().getHeight()] = 7;
        for (int i = tree.getRoot().getHeight() - 1; i >= 0; i--) gaps[i] = 2*gaps[i+1] + 1;

        for (int gap : gaps) {
            // Print line with numbers
            for (AVLNode<T> node : nodesInLevel) {
                int l = node==null?1:node.getData().toString().length();
                printNTimes(" ", gap - (l-1)/2);
                System.out.print(node==null?" ":node.getData());
                printNTimes(" ", gap - l/2 + 1);
            } System.out.println();

            // Print line with connectors
            for (AVLNode<T> node : nodesInLevel) {
                if (node == null) {
                    printNTimes(" ", 2*gap + 2);
                } else {
                    printNTimes(" ", gap/2);
                    System.out.print("|");
                    printNTimes("-", gap / 2);
                    System.out.print("^");
                    printNTimes("-", gap / 2);
                    System.out.print("|");
                    printNTimes(" ", gap/2 + 1);
                }
            } System.out.println();

            // Prepare for next level
            List<AVLNode<T>> nextLevel = new ArrayList<>();
            for (AVLNode<T> node : nodesInLevel) {
                if (node == null) {
                    nextLevel.add(null);
                    nextLevel.add(null);
                } else {
                    nextLevel.add(node.getLeft());
                    nextLevel.add(node.getRight());
                }
            }
            nodesInLevel = nextLevel;
        }
    }

    public static class TestItem implements Comparable<TestItem> {
        private final int value;
        public static final TestItem MAX_VALUE = new TestItem(Integer.MAX_VALUE);
        public static final TestItem MIN_VALUE = new TestItem(Integer.MIN_VALUE);


        public TestItem(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(TestItem o) {
            long r = (long)this.value - o.value;
            if (r > 2000000) return 2000000;
            else if (r < -2000000) return -2000000;
            else return (int) r;
        }

        public TestItem makeCopy() {
            return new TestItem(value);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            else if (o instanceof TestItem) {
                return this.value == ((TestItem)o).value;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return (int)(4000*Math.random());
            // You really shouldn't use this method, so here is a fun
            // implementation to mess up your code if you do
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    private <T extends Exception> void assertThrows(Class<T> exceptionClass, Runnable executable) {
        try {
            executable.run();
        } catch (Exception e) {
            if (!exceptionClass.isInstance(e)) {
                fail(e.getClass().getSimpleName() + " was thrown, expected " + exceptionClass.getSimpleName());
            } else {
                return;
            }
        }

        fail("Expected " + exceptionClass.getSimpleName() + ", but no exception was thrown");
    }
}