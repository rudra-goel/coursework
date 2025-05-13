import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) { 
            throw new IllegalArgumentException("Cannot construct BST with null collection reference");
        }
        for (T element : data) {
            add(element);
        }

    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) { 
            throw new IllegalArgumentException("Cannot add null data to BST");
        }
        root = add(data, root);
    }

    /**
     * helper method of add
     * @param data to add
     * @param node the current node we are comparing data against
     * @return the node that was added to the tree
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        //base case: we have hit a place in the BST where the current nodes parent is a leaf
        if (node == null) {
            size++;
            return new BSTNode<T>(data);
            
        }
        //this means that the data they want to add is greater than the current node
        //we have to add the data to the right hand side of the current node
        if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        } else if (data.compareTo(node.getData()) < 0) {
            //this means that the data they want to add is less than the current node
            //we must use pointer reinforcement to add the data to the left side of the tree
            node.setLeft(add(data, node.getLeft()));
        }
        
        return node;

    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Cannot remove data that is null");
        }

        BSTNode<T> target = new BSTNode<T>(null);

        root = removeHelper(root, data, target);

        size--;

        return target.getData();

    }

    /**
     * Helper method for remove
     * @param node the current node
     * @param data data to remove
     * @param target the node containing the data intended to be removed
     * @return the data the was removed and or the node to remain via pointer reinforcement
     */
    private BSTNode<T> removeHelper(BSTNode<T> node, T data, BSTNode<T> target) {
        if (node == null) {
            throw new NoSuchElementException("Cannot remove data that was not found in the tree");
        }

        // System.out.println("Data: " + data + "\nNode: "+node.getData() + "\n");

        //data found - now we have to remove data 
        if (node.getData().equals(data)) {
            
            //set target reference to the node's data we found
            target.setData(node.getData());

            //if node has two children, return successor
            if (node.getLeft() != null && node.getRight() != null) {
                //must step right first. We are certain that the node we need to find the successor of
                //has a right child since this method is only called when the node in question has
                //two children.
                
                BSTNode<T> deadTarget = new BSTNode<T>(null);

                //we have to find the successor, then remove it, and 
                node.setRight(removeSuccessorNode(node.getRight(), deadTarget));

                node.setData(deadTarget.getData());
            } else if (node.getLeft() != null) {
                //node has one left child
                // System.out.println("Node has left child ONLY\nTarget: " + target);
                return node.getLeft();
            } else if (node.getRight() != null) {
                //node has one right child
                // System.out.println("Node has right child ONLY\nTarget: " + target);
                return node.getRight();
            } else {
                //node is a leaf --> we return null
                //this removes node from tree via pointer reinforcement because 
                //next on the call stack sets either left or right parent node to null
                return null;
            }
        } else if (data.compareTo(node.getData()) < 0) {
            //traverse left subtree since data is smaller than current node's data
            // System.out.println("Compared " + data + " with " + node.getData() + ". Traversed left");
            node.setLeft(removeHelper(node.getLeft(), data, target));
        } else if (data.compareTo(node.getData()) > 0) {
            //traverse right side
            // System.out.println("Compared " + data + " with " + node.getData() + ". Traversed Right");
            node.setRight(removeHelper(node.getRight(), data, target));
        }

        return node;
    }

    /**
     * find and remove the successor node
    * @param node current node
     * @param target target node to remove
     * @return the successor
     */
    private BSTNode<T> removeSuccessorNode(BSTNode<T> node, BSTNode<T> target) {
        if (node.getLeft() == null) {
            //successor found
            target.setData(node.getData());

            return node.getRight();
        } else {
            node.setLeft(removeSuccessorNode(node.getLeft(), target));
            return node;
        }

    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot operate on data that is null");
        }
        return get(data, root);

    }

    /**
     * helper of get
     * @param data the data to retrieve
     * @param node the current node
     * @return the data found
     */
    private T get(T data, BSTNode<T> node) {

        if (node == null) {
            throw new NoSuchElementException("Data " + data + " was not found in the BST");
        }

        //recurse on the left since data is less than current node
        if (data.compareTo(node.getData()) < 0) {
            return get(data, node.getLeft());
        }  else if (data.compareTo(node.getData()) > 0) {
            //recurse on the right since data is more than current node
            return get(data, node.getRight());
        } else {
            //data is found
            return node.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        try {
            get(data);
            return true;
        } catch (NoSuchElementException nse) {
            //data was not in tree --> thats why exception thrown
            return false;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preOrderList = new LinkedList<T>();

        preOrderHelper(root, preOrderList);

        return preOrderList;
    }

    /**
     * Helper method for preorder
     * @param node the current node
     * @param preOrderList the list on where to add the data into in pre order
     */
    private void preOrderHelper(BSTNode<T> node, List<T> preOrderList) {
        /**
         * First add all items to the left of the node
         * then add all items to the right of the current node
         */
        if (node != null) {
            preOrderList.add(node.getData());
            preOrderHelper(node.getLeft(), preOrderList);
            preOrderHelper(node.getRight(), preOrderList);
        }
    }


    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inOrderList = new LinkedList<T>();

        inOrderHelper(root, inOrderList);

        return inOrderList;

    }

    /**
     * Helper method to in order traversal
     * @param node current node 
     * @param inOrderList list to add data to
     */
    private void inOrderHelper(BSTNode<T> node, List<T> inOrderList) {
        if (node != null) {
            inOrderHelper(node.getLeft(), inOrderList);
            inOrderList.add(node.getData());
            inOrderHelper(node.getRight(), inOrderList);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postOrderList = new LinkedList<T>();

        postOrderHelper(root, postOrderList);

        return postOrderList;
        
    }

    /**
     * helper method for post order
     * @param node current node 
     * @param postOrderList list to add data to
     */
    private void postOrderHelper(BSTNode<T> node, List<T> postOrderList) {
        if (node != null) {
            postOrderHelper(node.getLeft(), postOrderList);
            postOrderHelper(node.getRight(), postOrderList);
            postOrderList.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        //we are using the LinkedList as a queue
        //meaning we only need to add to the back (TAIL) and dequeue from the front (HEAD)
        List<BSTNode<T>> levelOrderListForNodes = new LinkedList<BSTNode<T>>();

        //this linked list is set to track the actual data in the level order list. 
        //this list will be returned at the end of the function
        List<T> levelOrderList = new LinkedList<T>();

        if (size == 0) {
            return levelOrderList;
        }

        //add the root to the queue of nodes
        levelOrderListForNodes.add(root);

        BSTNode<T> currentNode; 

        while (levelOrderListForNodes.size() > 0) {
            //then by setting current node to be the first item in the list
            currentNode = levelOrderListForNodes.get(0);

            //first need to enqueue children of current node to linkedList containing nodes

            //current node has a left child so add it to queue
            if (currentNode.getLeft() != null) {
                levelOrderListForNodes.add(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                levelOrderListForNodes.add(currentNode.getRight());
            }

            //add the data of the current node to the list to be returned at the end of the routine
            levelOrderList.add(currentNode.getData());
            // System.out.println(levelOrderList);

            //move the currentNode pointer up by one.
            //do this by removing the first element (HEAD)
            levelOrderListForNodes.remove(0);
            
        }

        return levelOrderList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * helper method for finding the height of the root
     * @param node  the current node
     * @return the height of a given node
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } 

        int leftSubTreeHeight = height(node.getLeft());
        int rightSubTreeHeight = height(node.getRight());

        return leftSubTreeHeight > rightSubTreeHeight ? leftSubTreeHeight + 1 : rightSubTreeHeight + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("cannot find path between data that is null");
        }
        if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("Either Data 1 or Data 2 is not in the tree");
        }

        BSTNode<T> dca = findDCA(data1, data2, root);

        List<T> path = new LinkedList<T>();

        path.add(dca.getData());

        //if data1 is smaller than DCA data, then find the path on the left subtree of the DCA
        if (data1.compareTo(dca.getData()) < 0) {
            //add path for data1 --> DCA
            traversePathAndAdd(dca.getLeft(), data1, path, true);
        } else if (data1.compareTo(dca.getData()) > 0) {
            //add path for data1 --> DCA
            traversePathAndAdd(dca.getRight(), data1, path, true);
        }
        
        if (data2.compareTo(dca.getData()) < 0) {
            //add path for DCA --> data2
            traversePathAndAdd(dca.getLeft(), data2, path, false);
        } else if (data2.compareTo(dca.getData()) > 0) {
            //add path for DCA --> data2
            traversePathAndAdd(dca.getRight(), data2, path, false);
        }
        

        return path;
    }

    /**
     * DCA Conditions are such that data1 < DCA < data2
     * @param data1 data that is SMALLER than data2
     * @param data2 data that is BIGGER than data1
     * @param node the current node we are looking at
     * @return the DCA of the two provided data
     */
    private BSTNode<T> findDCA(T data1, T data2, BSTNode<T> node) {

        
        // System.out.println("Finding DCA for " + data1 + " and " + data2 + ". Current Node is " + node.getData()); 


        //if both data are bigger than current node, then we need to traverse right subtree
        if (data1.compareTo(node.getData()) > 0 && data2.compareTo(node.getData()) > 0) {
            return findDCA(data1, data2, node.getRight());
        } 
        //meaning that data2 is smaller than the current node, so we have to find the DCA on the left subtree
        if (data1.compareTo(node.getData()) < 0 && data2.compareTo(node.getData()) < 0) {
            return findDCA(data1, data2, node.getLeft());
        }
        
        //found the DCA
        // System.out.println("DCA Found. it is " + node.getData());
        return node;

    }

    /**
     * Helper method for finding the path between two nodes. adds path from target to node
     * @param node the current node
     * @param target the target data we are searching for
     * @param pathList the list we are adding the path to
     * @param addToFront boolean to determine fi we add to the front of the path list or to the back
     */
    private void traversePathAndAdd(BSTNode<T> node, T target, List<T> pathList, boolean addToFront) {
        if (node == null) {
            return;
        }
        if (addToFront) {
            //add to the front of then path list for data1
            if (target.compareTo(node.getData()) > 0) {
                //add to front then recurse on right subtree
                pathList.add(0, node.getData());
                traversePathAndAdd(node.getRight(), target, pathList, true);
            } else if (target.compareTo(node.getData()) < 0) {
                //if data is smaller than current node, add to front of list for smaller data
                pathList.add(0, node.getData());
                traversePathAndAdd(node.getLeft(), target, pathList, true);
            } else {
                //data is found
                pathList.add(0, node.getData());
            }
        } else {
            //add to the back of then path list for data2
            if (target.compareTo(node.getData()) > 0) {
                //add to front then recurse on right subtree
                pathList.add(node.getData());
                traversePathAndAdd(node.getRight(), target, pathList, false);
            } else if (target.compareTo(node.getData()) < 0) {
                //if data is smaller than current node, add to front of list for smaller data
                pathList.add(node.getData());
                traversePathAndAdd(node.getLeft(), target, pathList, false);
            } else {
                //data is found
                pathList.add(node.getData());
            }
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
    public BSTNode<T> getRoot() {
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
