import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Rudra Goel
 * @version 1.0
 * @userid rgoel68
 * @GTID 903897740
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot make an AVL with null Data");
        }
        
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (contains(data)) {
            return;
        }

        root = addHelper(root, data);
        size++;
    }

    /**
     * Helper for add
     * @param current curr
     * @param data data
     * @return  node
     */
    private AVLNode<T> addHelper(AVLNode<T> current, T data) {

        if (current == null) {
            AVLNode<T> newNode = new AVLNode<T>(data);
            newNode.setHeight(0); // leafs have height 0
            newNode.setBalanceFactor(0); // leafs have BF of 0

            return newNode;
        } else if (current.getData().compareTo(data) > 0) {
            //current is greater than data
            //so we add data to the left subtree
            current.setLeft(addHelper(current.getLeft(), data));

        } else if (current.getData().compareTo(data) < 0) {
            //current is less than data
            current.setRight(addHelper(current.getRight(), data));
        }

        //balance the current node if needed
        current = balanceNode(current);

        //return the new, balanced node
        return current;
    }

    /**
     * Method to balance a node. Calls a series of functions based on the necessary rotations
     * @param current the node needing to be checked to be rebalanced
     * @return node
     */
    private AVLNode<T> balanceNode(AVLNode<T> current) {

        //first update the node
        updateNode(current);

        int bf = current.getBalanceFactor();

        if (bf == -2) {
            //two cases if the (sub)tree is right leaning
            //1. right child has BF of -1 || 0 --> left rotation
            //2. right child has BF of 1 --> right-left rotation
            if (current.getRight().getBalanceFactor() == -1 || current.getRight().getBalanceFactor() == 0) {
                return leftRotation(current);
            } else {
                return rightLeftRotation(current);
            }
        } else if (bf == 2) {
            //two cases if the (sub)tree is left leaning
            //1. left child has BF 1 || 0 --> right rotation
            //2. left child has BF -1 --> left-right rotation

            if (current.getLeft().getBalanceFactor() == 1 || current.getLeft().getBalanceFactor() == 0) {
                return rightRotation(current);
            } else {
                return leftRightRotation(current);
            }
        } else {
            //BF of current node is [-1, 1]
            //meaning node is balanced so we exit
            return current; 
        }
    }


    /**
     *  right rotate
     * @param a node
     * @return node
     */
    private AVLNode<T> rightRotation(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft();

        a.setLeft(b.getRight());
        b.setRight(a);

        updateNode(a);
        updateNode(b);

        return b;
    }

    /**
     *  left rotate
     * @param a node
     * @return node
     */
    private AVLNode<T> leftRotation(AVLNode<T> a) {
        //get b and c references
        AVLNode<T> b = a.getRight();

        //left rotation
        a.setRight(b.getLeft());
        b.setLeft(a);

        updateNode(a);
        updateNode(b);

        return b;
    }

    /**
     * left right rotate
     * @param a node
     * @return node
     */
    private AVLNode<T> leftRightRotation(AVLNode<T> a) {
        //do a left rotation on a's right child
        a.setLeft(leftRotation(a.getLeft()));

        //then do a right rotation on a
        return rightRotation(a);

    }

    /**
     *  right left rotate
     * @param a node
     * @return node
     */
    private AVLNode<T> rightLeftRotation(AVLNode<T> a) {
        //do a right rotation on a's right child
        a.setRight(rightRotation(a.getRight()));

        //then do a left rotation on a
        return leftRotation(a);
    }

    /**
     * update height and bf
     * @param node node
     */
    private void updateNode(AVLNode<T> node) {
        //first update its height
        int leftChildHeight = node.getLeft() == null ? -1 : node.getLeft().getHeight();
        int rightChildHeight = node.getRight() == null ? -1 : node.getRight().getHeight();

        int nodeHeight = leftChildHeight > rightChildHeight ? leftChildHeight + 1 : rightChildHeight + 1;

        node.setHeight(nodeHeight);

        //update balance factor
        node.setBalanceFactor(leftChildHeight - rightChildHeight);
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null");
        }

        AVLNode<T> dummy = new AVLNode<T>(null);

        root = removeHelper(root, data, dummy);
        
        size--;

        return dummy.getData();
        
    }

    /**
     * Helper of remove
     * @param current node
     * @param data data
     * @param dummy put
     * @return node pointer reinforcement
     */
    private AVLNode<T> removeHelper(AVLNode<T> current, T data, AVLNode<T> dummy) {
        if (current == null) {
            throw new NoSuchElementException("Data not in tree");
        }

        if (current.getData().compareTo(data) > 0) {
            current.setLeft(removeHelper(current.getLeft(), data, dummy));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(removeHelper(current.getRight(), data, dummy));
        } else {
            //data is found
            dummy.setData(current.getData());

            //check if current is leaf, has 1 child, has 2 children
            if (current.getLeft() != null && current.getRight() != null) {
                //has two children
                //put the predecessor in the current spot

                AVLNode<T> predecessor = new AVLNode<T>(null);
                //guaranteed that current.getLeft is not null by if statement above
                current.setLeft(getAndRemoveRightMostNode(current.getLeft(), predecessor));

                //keep the current node but set it's data to be the predecessor's data
                current.setData(predecessor.getData());

            } else if (current.getLeft() != null) {
                return current.getLeft();

            } else if (current.getRight() != null) {
                return current.getRight();
            } else {
                //current is leaf
                return null;
            }
        }

        //after removing the node from this subtree, balance the current node when going back up for each node
        current = balanceNode(current);

        return current;
    }

    /**
     * rem right most node
     * @param current node
     * @param dummy node
     * @return node
     */
    private AVLNode<T> getAndRemoveRightMostNode(AVLNode<T> current, AVLNode<T> dummy) {
        if (current.getRight() == null) {
            //predecessor found
            dummy.setData(current.getData());
            return null;
        }

        current.setRight(getAndRemoveRightMostNode(current.getRight(), dummy));

        //balance the current node as something has been removed
        current = balanceNode(current);

        return current;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot determine if null is in tree");
        }

        return getHelper(root, data);
    }

    /**
     * helper for get
     * @param current node
     * @param data data 
     * @return data
     */
    private T getHelper(AVLNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException("");
        }
        if (current.getData().compareTo(data) > 0) {
            return getHelper(current.getLeft(), data);
        } else if (current.getData().compareTo(data) < 0) {
            return getHelper(current.getRight(), data);
        } else {
            return current.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot determine if null is in tree");
        }
        return containsHelper(root, data);
    }

    /**
     * helper for contains 
     * @param current node
     * @param data data
     * @return true if contains false otherwise
     */
    private boolean containsHelper(AVLNode<T> current, T data) {
        if (current == null) {
            return false;
        }

        if (current.getData().compareTo(data) > 0) {
            return containsHelper(current.getLeft(), data);
        } else if (current.getData().compareTo(data) < 0) {
            return containsHelper(current.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return root != null ? root.getHeight() : -1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the deepest
     * ancestor of the node holding data that contains a value less than data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be in AVL");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("Data not in AVL");
        }

        //first have to find the node
        AVLNode<T> nodeWrappingData = getNode(root, data);

        //then find the predecessor of that node

        if (nodeWrappingData.getLeft() != null) {
            return getRightMostNodeData(nodeWrappingData.getLeft());
        } else {
            //case two the left subtree is empty
            AVLNode<T> nodeJustSmallerThanTarget = new AVLNode<T>(null);

            getDeepestAncestorJustSmallerThanData(root, nodeJustSmallerThanTarget, nodeWrappingData.getData());

            return nodeJustSmallerThanTarget.getData() == null ? null : nodeJustSmallerThanTarget.getData();

        }
    }

    /**
     * get 
     * @param current node
     * @param tracker tracker
     * @param targetData data
     */
    private void getDeepestAncestorJustSmallerThanData(AVLNode<T> current, AVLNode<T> tracker, T targetData) {
        // if (current == null) {
        //     return null;
        // }

        if (current.getData().compareTo(targetData) < 0) {
            //current node is smaller than target
            //for first pass
            if (tracker.getData() == null) {
                tracker.setData(current.getData());
            }

            //current node is also bigger than previous tracker
            if (current.getData().compareTo(tracker.getData()) > 0) {
                tracker = current;
            }

        }


        if (current.getData().compareTo(targetData) < 0) {
            //current smaller than target so recurse on right
            getDeepestAncestorJustSmallerThanData(current.getRight(), tracker, targetData);
        } else if (current.getData().compareTo(targetData) > 0) {
            //current bigger than target so recurse on left
            getDeepestAncestorJustSmallerThanData(current.getLeft(), tracker, targetData);
        } else {
            //current has hit the target data, so no more ancestors
            //end function
            return;
        }
    }

    /**
     * get data of right most node
     * @param current node
     * @return data
     */
    private T getRightMostNodeData(AVLNode<T> current) {
        if (current.getRight() == null) {
            //predecessor found
            return current.getData();
        }

        return getRightMostNodeData(current.getRight());

    }

    /**
     * finds and returns the node of the data passed in
     * @param current   the current node to look and compare against
     * @param data  that is guaranteed to be in the tree
     * @return  the AVL node that the data wraps
     */
    private AVLNode<T> getNode(AVLNode<T> current, T data) {
        if (current.getData().compareTo(data) > 0) {
            //recurse on left subtree
            return getNode(current.getLeft(), data);
        } else if (current.getData().compareTo(data) < 0) {
            //recurse on right subtree
            return getNode(current.getRight(), data);
        } else {
            //data is found
            return current;
        }
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }

        return maxDeepestHelper(root);
    }

    /**
     * get deepest helper 
     * @param current node
     * @return data
     */
    private T maxDeepestHelper(AVLNode<T> current) {

        if (current.getLeft() == null && current.getRight() == null) {
            return current.getData();
        } else if (current.getLeft() == null) {
            //recurse on right subtree
            return maxDeepestHelper(current.getRight());
        } else if (current.getRight() == null) {
            //recurse on left subtree
            return maxDeepestHelper(current.getLeft());
        } else {
            //current has two children
            //chose to recurse on the subtree that has a larger height
            AVLNode<T> nodeToRecurseOn = current.getLeft().getHeight() > current.getRight().getHeight() 
                                                                        ? current.getLeft() : current.getRight();

            return maxDeepestHelper(nodeToRecurseOn);
        }



    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

}
