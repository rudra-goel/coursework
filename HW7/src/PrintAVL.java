/**
 * A helper class for debugging the AVL cs1332 homework
 * Provides a method that prints out an AVL tree
 * usage: PrintAVL.printTree(tree);
 * @author Theo Halpern
 * @version 1.0
 */
public class PrintAVL {
    /**
     * prints a tree in an easy to debug format
     * I got the idea from https://leetcode.com/problems/print-binary-tree/description/
     * consider changing the itemWidth if you are dealing with items that take a lot of space to print
     * @param tree the Tree to print
     */
    public static void printTree(AVL tree) {
        System.out.println("AVL Tree Size: " + tree.size() + " Height: " + tree.height());
        int[] realSize = new int[1];
        int realHeight = rHeightAndSize(tree.getRoot(), realSize);
        System.out.println("Real size: " + realSize[0] + " Real height: " + realHeight);
        int numberOfRow = realHeight + 1;
        int numberOfCols = (int) Math.pow(2, realHeight + 1) - 1;
        Comparable[][] placedTree = new Comparable[numberOfRow][numberOfCols];
        int itemWidth = 3;
        if (tree.getRoot() != null) {
            placedTree[0][numberOfCols / 2] = tree.getRoot().getData();
            addChildrenToPlacedTree(tree.getRoot(), 0, numberOfCols / 2, realHeight, placedTree);
            for (int row = 0; row < placedTree.length; row++) {
                System.out.print(row + ": ");
                for (int col = 0; col < placedTree[0].length; col++) {
                    if (placedTree[row][col] != null) {
                        System.out.print(strPad(placedTree[row][col].toString(), itemWidth));
                    } else {
                        System.out.print(" ".repeat((itemWidth)));
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * adds the children of a node to the placedTree 2d array
     * @param parent the parent avl node
     * @param parentRow the row that parent was placed at
     * @param parentCol the col that the parent was placed at
     * @param treeHeight the height of the tree
     * @param placedTree the resulting 2d array which items are added to
     */
    public static void addChildrenToPlacedTree(
            AVLNode parent, int parentRow, int parentCol, int treeHeight, Comparable[][] placedTree) {
        if (parent.getLeft() != null) {
            int col = parentCol + (int) Math.pow(2, treeHeight - parentRow - 1);
            placedTree[parentRow + 1][col] = parent.getLeft().getData();
            addChildrenToPlacedTree(parent.getLeft(), parentRow + 1, col, treeHeight, placedTree);
        }
        if (parent.getRight() != null) {
            int col = parentCol - (int) Math.pow(2, treeHeight - parentRow - 1);
            placedTree[parentRow + 1][col] = parent.getRight().getData();
            addChildrenToPlacedTree(parent.getRight(), parentRow + 1, col, treeHeight, placedTree);
        }
    }

    /**
     * Adds padding to a string so it takes up at least the padding amount of characters
     * @param s the string to pad
     * @param padding the number of chars of minmum width
     * @return the padded string
     */
    private static String strPad(String s, int padding) {
        return " ".repeat((int) Math.ceil((padding - s.length()) / 2.0)) + s + " ".repeat((padding - s.length()) / 2);
    }

    /**
     * Returns the inOrder (heap) representation of the tree, could be useful for debugging
     * @param tree tree to generate a inOrder for
     * @return an array representation of the tree in the same format as cs1332 heaps
     */
    public static Comparable[] inOrder(AVL tree) {
        Comparable[] res = new Comparable[(int) Math.pow(2, tree.height() + 1)];
        rInOrder(tree.getRoot(), 1, res);
        return res;
    }

    /**
     * recursively generates a inOrder traversal
     * @param curr the current position in the tree
     * @param pos  the current position in the result array
     * @param res the result array
     */
    private static void rInOrder(AVLNode curr, int pos, Comparable[] res) {
        if (curr == null) {
            return;
        }
        res[pos] = curr.getData();
        rInOrder(curr.getLeft(), pos * 2, res);
        rInOrder(curr.getRight(), pos * 2 + 1, res);
    }

    /**
     * Gets the real height and size of a tree
     * @param curr the current position in the tree
     * @param size an array which first element is iterated for each node to help store the size
     * @return the height of the curr node
     */
    private static int rHeightAndSize(AVLNode curr, int[] size) {
        if (curr == null) {
            return -1;
        }
        size[0]++;
        return Math.max(rHeightAndSize(curr.getLeft(), size), rHeightAndSize(curr.getRight(), size)) + 1;
    }
}