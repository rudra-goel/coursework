import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * 
 * @author Liam Weng
 * @version 1.0
 *
 * Collaborators: CS1332 TAs (Template used).
 *
 * Enjoy!
 *
 * Trees used:
 *
 * Straight tree:
 *               2
 *              /
 *             0
 *              \
 *               1
 *
 * Full Tree:
 *                    5
 *                 /     \
 *               1         9
 *             /   \     /  \
 *            0     4   8   10
 *
 * Tree with One Node:
 *             0
 *
 *
 */
public class BSTTestLW {

    private static final int TIMEOUT = 200;
    private BST<Integer> treeStraight;
    private BST<Integer> treeFull;
    private BST<Integer> treeOne;
    private BST<Integer> treeEmpty;
    

    @Before
    public void setup() {
        treeStraight = new BST<>();
        treeFull = new BST<>();
        treeOne = new BST<>();
        treeEmpty = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, treeStraight.size());
        assertNull(treeStraight.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        /*
              2
             /
            0
             \
              1
        */

        //Straight Tree
        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        assertEquals(3, treeStraight.size());

        assertEquals((Integer) 2, treeStraight.getRoot().getData());
        assertEquals((Integer) 0, treeStraight.getRoot().getLeft().getData());
        assertEquals((Integer) 1, treeStraight.getRoot().getLeft().getRight()
                .getData());


        //Full Tree
        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        assertEquals(7, treeFull.size());

        assertEquals((Integer) 5, treeFull.getRoot().getData());
        assertEquals((Integer) 1, treeFull.getRoot().getLeft().getData());
        assertEquals((Integer) 9, treeFull.getRoot().getRight().getData());
        assertEquals((Integer) 0, treeFull.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, treeFull.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 8, treeFull.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 10, treeFull.getRoot().getRight().getRight().getData());


        //Tree with one node
        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        assertEquals(1, treeOne.size());

        assertEquals((Integer) 0, treeOne.getRoot().getData());


        //Empty Tree
        treeEmpty = new BST<>();

        assertEquals(0, treeEmpty.size());

        assertNull(treeEmpty.getRoot());

    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {

    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {

        //doesn't test returning data FROM tree (see BSTStudentTest.java for example)
        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();


        //Straight tree
        treeStraight.remove(1);
        assertEquals(2, treeStraight.size());
        assertEquals((Integer) 2, treeStraight.getRoot().getData());
        assertEquals((Integer) 0, treeStraight.getRoot().getLeft().getData());

        treeStraight.add(1);
        treeStraight.remove(0);
        assertEquals(2, treeStraight.size());
        assertEquals((Integer) 2, treeStraight.getRoot().getData());
        assertEquals((Integer) 1, treeStraight.getRoot().getLeft().getData());


        data.clear();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);
        treeStraight.remove(2);
        assertEquals(2, treeStraight.size());
        assertEquals((Integer) 0, treeStraight.getRoot().getData());
        assertEquals((Integer) 1, treeStraight.getRoot().getRight().getData());


        //Full Tree
        treeFull.remove(0);

        assertEquals(6, treeFull.size());

        assertEquals((Integer) 5, treeFull.getRoot().getData());
        assertEquals((Integer) 1, treeFull.getRoot().getLeft().getData());
        assertEquals((Integer) 9, treeFull.getRoot().getRight().getData());
        assertNull(treeFull.getRoot().getLeft().getLeft());
        assertEquals((Integer) 4, treeFull.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 8, treeFull.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 10, treeFull.getRoot().getRight().getRight().getData());

        treeFull.add(0);
        treeFull.remove(10);

        assertEquals(6, treeFull.size());

        assertEquals((Integer) 5, treeFull.getRoot().getData());
        assertEquals((Integer) 1, treeFull.getRoot().getLeft().getData());
        assertEquals((Integer) 9, treeFull.getRoot().getRight().getData());
        assertEquals((Integer) 0, treeFull.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, treeFull.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 8, treeFull.getRoot().getRight().getLeft().getData());
        assertNull(treeFull.getRoot().getRight().getRight());


        treeFull.add(10);
        treeFull.remove(1);

        assertEquals(6, treeFull.size());

        assertEquals((Integer) 5, treeFull.getRoot().getData());
        assertEquals((Integer) 4, treeFull.getRoot().getLeft().getData());
        assertEquals((Integer) 9, treeFull.getRoot().getRight().getData());
        assertEquals((Integer) 0, treeFull.getRoot().getLeft().getLeft().getData());
        assertNull(treeFull.getRoot().getLeft().getRight());
        assertEquals((Integer) 8, treeFull.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 10, treeFull.getRoot().getRight().getRight().getData());

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);
        treeFull.remove(9);

        assertEquals(6, treeFull.size());

        assertEquals((Integer) 5, treeFull.getRoot().getData());
        assertEquals((Integer) 1, treeFull.getRoot().getLeft().getData());
        assertEquals((Integer) 10, treeFull.getRoot().getRight().getData());
        assertEquals((Integer) 0, treeFull.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, treeFull.getRoot().getLeft().getRight().getData());
        assertNull(treeFull.getRoot().getRight().getRight());
        assertEquals((Integer) 8, treeFull.getRoot().getRight().getLeft().getData());


        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);
        treeFull.remove(5);

        assertEquals(6, treeFull.size());

        assertEquals((Integer) 8, treeFull.getRoot().getData());
        assertEquals((Integer) 1, treeFull.getRoot().getLeft().getData());
        assertEquals((Integer) 9, treeFull.getRoot().getRight().getData());
        assertEquals((Integer) 0, treeFull.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, treeFull.getRoot().getLeft().getRight().getData());
        assertNull(treeFull.getRoot().getRight().getLeft());
        assertEquals((Integer) 10, treeFull.getRoot().getRight().getRight().getData());


        //Tree with one node
        treeOne.remove(0);

        assertEquals(0, treeOne.size());
        assertNull(treeOne.getRoot());



    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        Integer temp200 = 200;
        Integer temp185 = 185;
        Integer temp190 = 190;
        Integer temp195 = 195;
        Integer temp215 = 215;
        Integer temp205 = 205;
        Integer temp210 = 210;



        //Straight tree
        List<Integer> data = new ArrayList<>();
        data.add(temp200);
        data.add(temp190);
        data.add(temp185);
        treeStraight = new BST<>(data);

        assertSame(temp200, treeStraight.get(200));
        assertSame(temp190, treeStraight.get(190));
        assertSame(temp185, treeStraight.get(185));
        assertEquals(3, treeStraight.size());

        //Full Tree
        data.clear();
        data.add(temp200);
        data.add(temp190);
        data.add(temp185);
        data.add(temp195);
        data.add(temp210);
        data.add(temp205);
        data.add(temp215);
        treeFull = new BST<>(data);

        assertSame(temp200, treeFull.get(200));
        assertSame(temp190, treeFull.get(190));
        assertSame(temp185, treeFull.get(185));
        assertSame(temp210, treeFull.get(210));
        assertSame(temp205, treeFull.get(205));
        assertSame(temp215, treeFull.get(215));
        assertEquals(7, treeFull.size());


        //Tree with one node
        data.clear();
        data.add(temp200);
        treeOne = new BST<>(data);

        assertSame(temp200, treeOne.get(200));
        assertSame(treeOne.getRoot().getData(), treeOne.get(200));
        assertEquals(1, treeOne.size());

    }

    @Test(timeout = TIMEOUT)
    public void testContains() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();

        assertEquals(3, treeStraight.size());

        //Straight tree
        assertTrue(treeStraight.contains(0));
        assertTrue(treeStraight.contains(1));
        assertTrue(treeStraight.contains(2));
        assertFalse(treeStraight.contains(10));

        //Full tree
        assertTrue(treeFull.contains(0));
        assertTrue(treeFull.contains(1));
        assertTrue(treeFull.contains(4));
        assertTrue(treeFull.contains(5));
        assertTrue(treeFull.contains(8));
        assertTrue(treeFull.contains(9));
        assertTrue(treeFull.contains(10));
        assertFalse(treeStraight.contains(3));

        //Tree with one node
        assertTrue(treeOne.contains(0));
        assertFalse(treeOne.contains(1));

        //Empty Tree
        assertFalse(treeEmpty.contains(0));

    }

    @Test(timeout = TIMEOUT)
    public void testPreorder() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();


        //Straight tree
        List<Integer> preorder = new ArrayList<>();
        preorder.add(2);
        preorder.add(0);
        preorder.add(1);

        // Should be [2,0,1]
        assertEquals(preorder, treeStraight.preorder());


        //Full tree
        preorder = new ArrayList<>();
        preorder.add(5);
        preorder.add(1);
        preorder.add(0);
        preorder.add(4);
        preorder.add(9);
        preorder.add(8);
        preorder.add(10);

        //Should be [5,1,0,4,9,8,10]
        assertEquals(preorder, treeFull.preorder());


        //Tree with one node
        preorder = new ArrayList<>();
        preorder.add(0);

        //[0]
        assertEquals(preorder, treeOne.preorder());

        //Empty tree
        preorder = new ArrayList<>();
        assertEquals(preorder, treeEmpty.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void testInorder() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();


        //Straight tree
        List<Integer> inorder = new ArrayList<>();
        inorder.add(0);
        inorder.add(1);
        inorder.add(2);

        // Should be [0,1,2]
        assertEquals(inorder, treeStraight.inorder());


        //Full tree
        inorder = new ArrayList<>();
        inorder.add(0);
        inorder.add(1);
        inorder.add(4);
        inorder.add(5);
        inorder.add(8);
        inorder.add(9);
        inorder.add(10);

        //Should be [0,1,4,5,8,9,10]
        assertEquals(inorder, treeFull.inorder());


        //Tree with one node
        inorder = new ArrayList<>();
        inorder.add(0);

        //[0]
        assertEquals(inorder, treeOne.inorder());

        //Empty tree
        inorder = new ArrayList<>();
        assertEquals(inorder, treeEmpty.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void testPostorder() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();

        //Straight tree
        List<Integer> postorder = new ArrayList<>();
        postorder.add(1);
        postorder.add(0);
        postorder.add(2);

        // Should be [1,0,2]
        assertEquals(postorder, treeStraight.postorder());


        //Full tree
        postorder = new ArrayList<>();
        postorder.add(0);
        postorder.add(4);
        postorder.add(1);
        postorder.add(8);
        postorder.add(10);
        postorder.add(9);
        postorder.add(5);

        //Should be [0,4,1,8,10,9,5]
        assertEquals(postorder, treeFull.postorder());


        //Tree with one node
        postorder = new ArrayList<>();
        postorder.add(0);

        //[0]
        assertEquals(postorder, treeOne.postorder());

        //Empty tree
        postorder = new ArrayList<>();
        assertEquals(postorder, treeEmpty.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();

        //Straight tree
        List<Integer> levelorder = new ArrayList<>();
        levelorder.add(2);
        levelorder.add(0);
        levelorder.add(1);

        // Should be [2,0,1]
        assertEquals(levelorder, treeStraight.levelorder());


        //Full tree
        levelorder = new ArrayList<>();
        levelorder.add(5);
        levelorder.add(1);
        levelorder.add(9);
        levelorder.add(0);
        levelorder.add(4);
        levelorder.add(8);
        levelorder.add(10);

        //Should be [5,1,9,0,4,8,10]
        assertEquals(levelorder, treeFull.levelorder());


        //Tree with one node
        levelorder = new ArrayList<>();
        levelorder.add(0);

        //[0]
        assertEquals(levelorder, treeOne.levelorder());

        //Empty tree
        levelorder = new ArrayList<>();
        assertEquals(levelorder, treeEmpty.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();

        //Straight tree
        assertEquals(3, treeStraight.size());
        assertEquals(2, treeStraight.height());

        //Full tree
        assertEquals(7, treeFull.size());
        assertEquals(2, treeFull.height());

        //Tree with one node
        assertEquals(1, treeOne.size());
        assertEquals(0, treeOne.height());

        //Empty tree
        assertEquals(0, treeEmpty.size());
        assertEquals(-1, treeEmpty.height());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();

        //Straight tree
        treeStraight.clear();
        assertEquals(0, treeStraight.size());
        assertNull(treeStraight.getRoot());

        //Full tree
        treeFull.clear();
        assertEquals(0, treeFull.size());
        assertNull(treeFull.getRoot());

        //Tree with one node
        treeOne.clear();
        assertEquals(0, treeOne.size());
        assertNull(treeOne.getRoot());

        //Empty tree
        treeEmpty.clear();
        assertEquals(0, treeEmpty.size());
        assertNull(treeEmpty.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testFindPathBetween() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();

        //Straight tree
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(0);
        expected.add(1);
        assertEquals(expected, treeStraight.findPathBetween(2, 1));

        expected.clear();
        expected.add(2);
        expected.add(0);
        assertEquals(expected, treeStraight.findPathBetween(2, 0));

        expected.clear();
        expected.add(0);
        expected.add(1);
        assertEquals(expected, treeStraight.findPathBetween(0, 1));

        expected.clear();
        expected.add(1);
        expected.add(0);
        assertEquals(expected, treeStraight.findPathBetween(1, 0));

        expected.clear();
        expected.add(1);
        expected.add(0);
        expected.add(2);
        assertEquals(expected, treeStraight.findPathBetween(1, 2));

        expected.clear();
        expected.add(0);
        expected.add(2);
        assertEquals(expected, treeStraight.findPathBetween(0, 2));


        //Full tree
        expected.clear();
        expected.add(0);
        expected.add(1);
        expected.add(4);
        assertEquals(expected, treeFull.findPathBetween(0, 4));

        expected.clear();
        expected.add(8);
        expected.add(9);
        expected.add(10);
        assertEquals(expected, treeFull.findPathBetween(8, 10));

        expected.clear();
        expected.add(0);
        expected.add(1);
        expected.add(5);
        assertEquals(expected, treeFull.findPathBetween(0, 5));

        expected.clear();
        expected.add(8);
        expected.add(9);
        expected.add(5);
        assertEquals(expected, treeFull.findPathBetween(8, 5));

        expected.clear();
        expected.add(0);
        expected.add(1);
        expected.add(5);
        expected.add(9);
        expected.add(10);
        assertEquals(expected, treeFull.findPathBetween(0, 10));

        expected.clear();
        expected.add(10);
        expected.add(9);
        expected.add(5);
        expected.add(1);
        expected.add(0);
        assertEquals(expected, treeFull.findPathBetween(10, 0));

        expected.clear();
        expected.add(0);
        expected.add(1);
        expected.add(5);
        expected.add(9);
        assertEquals(expected, treeFull.findPathBetween(0, 9));

        expected.clear();
        expected.add(9);
        expected.add(5);
        expected.add(1);
        assertEquals(expected, treeFull.findPathBetween(9, 1));


        //Tree with one node
        expected.clear();
        expected.add(0);
        assertEquals(expected, treeOne.findPathBetween(0, 0));


        //Empty tree
    }

    @Test(timeout = TIMEOUT)
    public void errors() {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        treeStraight = new BST<>(data);

        data.clear();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(0);
        data.add(4);
        data.add(8);
        data.add(10);
        treeFull = new BST<>(data);

        data.clear();
        data.add(0);
        treeOne = new BST<>(data);

        treeEmpty = new BST<>();

        data.clear();
        data.add(null);

        //Constructor
        assertThrows(IllegalArgumentException.class, () ->
                treeStraight = new BST<>(null));
        assertThrows(IllegalArgumentException.class, () ->
                treeStraight = new BST<>(data));

        //Add
        assertThrows(IllegalArgumentException.class, () ->
                treeStraight.add(null));
        assertThrows(IllegalArgumentException.class, () ->
                treeFull.add(null));
        assertThrows(IllegalArgumentException.class, () ->
                treeOne.add(null));
        assertThrows(IllegalArgumentException.class, () ->
                treeEmpty.add(null));

        //Removal
        assertThrows(IllegalArgumentException.class, () ->
                treeStraight.remove(null));
        assertThrows(IllegalArgumentException.class, () ->
                treeFull.remove(null));
        assertThrows(IllegalArgumentException.class, () ->
                treeOne.remove(null));
        assertThrows(IllegalArgumentException.class, () ->
                treeEmpty.remove(null));

        assertThrows(NoSuchElementException.class, () ->
                treeStraight.remove(5));
        assertThrows(NoSuchElementException.class, () ->
                treeFull.remove(3));
        assertThrows(NoSuchElementException.class, () ->
                treeOne.remove(5));
        assertThrows(NoSuchElementException.class, () ->
                treeEmpty.remove(5));

        //Get
        assertThrows(IllegalArgumentException.class, () ->
                treeStraight.get(null));

        assertThrows(NoSuchElementException.class, () ->
                treeStraight.get(5));
        assertThrows(NoSuchElementException.class, () ->
                treeFull.get(3));
        assertThrows(NoSuchElementException.class, () ->
                treeOne.get(5));
        assertThrows(NoSuchElementException.class, () ->
                treeEmpty.get(5));

        //Contains
        assertThrows(IllegalArgumentException.class, () ->
                treeStraight.contains(null));

        //findPathBetween
        assertThrows(IllegalArgumentException.class, () ->
                treeStraight.findPathBetween(null, 2));
        assertThrows(IllegalArgumentException.class, () ->
                treeFull.findPathBetween(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                treeOne.findPathBetween(2, null));
        assertThrows(IllegalArgumentException.class, () ->
                treeEmpty.findPathBetween(5, null));


        assertThrows(NoSuchElementException.class, () ->
                treeStraight.findPathBetween(5, 2));

        assertThrows(NoSuchElementException.class, () ->
                treeFull.findPathBetween(11, 2));
        assertThrows(NoSuchElementException.class, () ->
                treeFull.findPathBetween(11, 2));
        assertThrows(NoSuchElementException.class, () ->
                treeFull.findPathBetween(5, 11));
        assertThrows(NoSuchElementException.class, () ->
                treeFull.findPathBetween(0, 11));

        assertThrows(NoSuchElementException.class, () ->
                treeOne.findPathBetween(6, 2));
        assertThrows(NoSuchElementException.class, () ->
                treeOne.findPathBetween(6, 0));
        assertThrows(NoSuchElementException.class, () ->
                treeOne.findPathBetween(0, 2));

        assertThrows(NoSuchElementException.class, () ->
                treeEmpty.findPathBetween(5, 2));

    }
}
