import java.util.Comparator;
import java.util.Random;


//used for LSDRadixSort
import java.util.List;
import java.util.LinkedList;

//Used for HeapSort
import java.util.PriorityQueue;
/**
 * Your implementation of various sorting algorithms.
 *
 * Your implementations must match what was taught in lecture and 
 * recitation to receive credit. Implementing a different sort or 
 * a different implementation for a sort will receive no credit even
 * if it passes comparison checks.
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
public class Sorting {

    /**
     * Implement insertion sort. You should start sorting 
     * from the beginning of the array.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(arr == null ? "array" : "comparator" + " cant be null");
        }
        //start from index 1 since the sub array of size
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            // System.out.println("j is " + j + " " + Arrays.toString(arr));
            //while J is strictly positive and arr[j-1] > arr[j]
            while (j > 0 && comparator.compare(arr[j - 1],  arr[j]) > 0) {
                //bubble arr[j] down since arr[j-1] is bigger than arr[j]
                //swap arr[j-1] with arr[j] and then decrement j
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;

                j--;
            }

        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(arr == null ? "array" : "comparator" + " cant be null");
        }

        int start = 0;
        int end = arr.length - 1;
        
        while (start < end) {
            int lastSwapped = start;

            for (int i = start; i < end; i++) {
                //compare current item to current item + 1
                //swap if their relation is not good
                //if the current item is bigger than the next, swap
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    lastSwapped = i;
                }
            }
                
            end = lastSwapped;
            
            for (int j = end; j > start; j--) {
                //compare current item to item behind it and swap is relation is not good
                //if arr[j] is less than the one behind it, swap
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    lastSwapped = j;
                }
            }
            start = lastSwapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(arr == null ? "array" : "comparator" + " cant be null");
        }
        //case of empty array so exit method
        if (arr.length == 0) {
            return;
        }
        mergeSortHelper(arr, comparator);
    }

    /**
     * helper to merge sort
     * @param <T> type
     * @param arr array to sort
     * @param comparator comparator object
     */
    @SuppressWarnings("unchecked")
    private static <T> void mergeSortHelper(T[] arr, Comparator<T> comparator) {
        if (arr.length == 1) {
            return;
        }

        //split array into two and call mergeSortHelper on left and right half 
        T[] leftArr;
        T[] rightArr;

        if (arr.length % 2 == 0) {
            leftArr = (T[]) new Object[arr.length / 2];
            rightArr = (T[]) new Object[arr.length - leftArr.length];

            for (int i = 0; i < arr.length / 2; i++) {
                leftArr[i] = arr[i];
            }

            for (int i = arr.length / 2; i < arr.length; i++) {
                rightArr[i - arr.length / 2] = arr[i];
            }
            
        } else {
            //if array size is uneven, then make left bigger
            leftArr = (T[]) new Object[arr.length / 2 + 1];
            rightArr = (T[]) new Object[arr.length - leftArr.length];

            for (int i = 0; i < arr.length / 2 + 1; i++) {
                leftArr[i] = arr[i];
            }

            for (int i = 0; i < rightArr.length; i++) {
                rightArr[i] = arr[i + arr.length / 2 + 1];
            }
        }

        
        mergeSortHelper(leftArr, comparator);
        mergeSortHelper(rightArr, comparator);

        // System.out.println("Main Array " + Arrays.toString(arr));
        // System.out.println("Left Array " + Arrays.toString(leftArr));
        // System.out.println("Right Array " + Arrays.toString(rightArr));
        // System.out.println();
        
        //left arr and right arrays are sorted
        //now sort them back into the original array in this function's context
        int leftCounter = 0;
        int rightCounter = 0;

        for (int i = 0; i < arr.length; i++) {
            
            if (leftCounter >= leftArr.length) {
                //end of right arr so dump remaining leftArr into arr
                arr[i] = rightArr[rightCounter];
                rightCounter++;
            } else if (rightCounter >= rightArr.length) {
                //end of left arr so dump remaining rightArr into arr
                arr[i] = leftArr[leftCounter];
                leftCounter++;
            } else {
                //elements still in leftArr and RightArr so compare and add accordingly
                if (comparator.compare(leftArr[leftCounter], rightArr[rightCounter]) <= 0) {
                    //meaning left at index leftCounter is smaller than or equal to right at index rightCounter
                    // --> put left item in arr
                    //equality makes it stable
                    arr[i] = leftArr[leftCounter];
                    leftCounter++;
                } else {
                    //meaning right at index rightCounter is smaller than left at index leftCounter
                    //--> put left item in arr
                    arr[i] = rightArr[rightCounter];
                    rightCounter++;
                }
            }
        }
        
        // System.out.println("Merged: " + Arrays.toString(arr));
        // System.out.println();
        
    }
    
    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class
     * (see Canvas Modules for reference).
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(arr == null ? "array" : "comparator" + " cant be null");
        } else if (rand == null) {
            throw new IllegalArgumentException(" random cant be null");
        }

        quickSortHelper(arr, comparator, 0, arr.length - 1, rand);
    }

    /**
     * Helper to quicksort method
     * @param <T>           type
     * @param arr           array to sort
     * @param comparator    comparator object
     * @param start         start index
     * @param end           end index
     * @param rand          random object for deciding pivot
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator, int start, int end, Random rand) {
        //base case where 
        if (end - start < 1) {
            return;
        }

        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIndex];

        //swap start and pivotIndex indices
        T temp = arr[start];
        arr[start] = arr[pivotIndex];
        arr[pivotIndex] = temp;

        //init pointer vars
        int i = start + 1;
        int j = end;

        //while i has NOT crossed j
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) < 0) {
                //while i has not crossed j and arr[i] is less than pivotVal, increment i
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) > 0) {
                //while i has not crossed j and arr[j] is greater than the pivot increment j
                j--;
            }
            if (i <= j) {
                //i and j are now either pointing to elements that need to be swapped
                //swap things at i and j
                T tempSwap = arr[i];
                arr[i] = arr[j];
                arr[j] = tempSwap;
                //move i and j 
                i++;
                j--;
            }
        }
        //if I and J cross, main loop is exited and we put the pivot value in position j
        T tempVal = arr[start];
        arr[start] = arr[j];
        arr[j] = tempVal;

        //recurse for left of pivot and then right of pivot
        quickSortHelper(arr, comparator, start, j - 1, rand);
        quickSortHelper(arr, comparator, j + 1, end, rand);

        // System.out.println(Arrays.toString(arr));
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class
     * (see Canvas Modules for reference).
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * Note: Be very careful about how Integer.MIN_VALUE is handled, especially
     * when using Math.abs().
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    @SuppressWarnings("unchecked")
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("array cant be null");
        }
        //first determine the number of passes we need for sorting arr
        int k = determineK(arr);

        List<Integer>[] buckets = new LinkedList[19];

        buckets[0] = new LinkedList<Integer>(); //-9
        buckets[1] = new LinkedList<Integer>(); //-8
        buckets[2] = new LinkedList<Integer>(); //-7
        buckets[3] = new LinkedList<Integer>(); //-6
        buckets[4] = new LinkedList<Integer>(); //-5
        buckets[5] = new LinkedList<Integer>(); //-4
        buckets[6] = new LinkedList<Integer>(); //-3
        buckets[7] = new LinkedList<Integer>(); //-2
        buckets[8] = new LinkedList<Integer>(); //-1

        buckets[9] = new LinkedList<Integer>(); //0

        buckets[10] = new LinkedList<Integer>(); //1
        buckets[11] = new LinkedList<Integer>(); //2
        buckets[12] = new LinkedList<Integer>(); //3
        buckets[13] = new LinkedList<Integer>(); //4
        buckets[14] = new LinkedList<Integer>(); //5
        buckets[15] = new LinkedList<Integer>(); //6
        buckets[16] = new LinkedList<Integer>(); //7
        buckets[17] = new LinkedList<Integer>(); //8
        buckets[18] = new LinkedList<Integer>(); //9

        for (int i = 1; i <= k; i++) {
            for (int j = 0; j < arr.length; j++) {
                int val = arr[j];
                //extract the kth place digit
                int digitK = extractKthDigit(val, i);
                //map the digitK to the correct index for the bucketsArr
                //add to the kth bucket
                buckets[digitK + 9].add(val); // ensure stability since it adds elements to the end of the bucket
            }
            //reorganize the array according to the buckets
            reorganizeArrayWithBuckets(arr, buckets);
            // System.out.println("Iteration: i Array: " + Arrays.toString(arr));
        }

        // System.out.println(Arrays.toString(arr));
    }
    /**
     * Helper to radix to reassign the bucket values to the array and then flush the bucket contents
     * @param arr       array
     * @param buckets   bucket array
     */
    private static void reorganizeArrayWithBuckets(int[] arr, List<Integer>[] buckets) {
        //counter to traverse the array
        int arrayCounter = 0;
        
        //traverse through the buckets
        for (int i = 0; i < buckets.length; i++) {
            //get the bucket
            List<Integer> bucket = buckets[i];

            //add each item from the bucket into the array
            while (bucket.size() != 0) {
                arr[arrayCounter] = bucket.get(0);
                bucket.remove(0);
                arrayCounter++;
            }

            //flush the contents of the bucket
            bucket.clear();
        }
    }

    /**
     * Returns a value 0-9 that is the kth digit of the argument value
     * @param val   value to obtain the kth digit from
     * @param k     the desired place
     * @return      the desired digit place from val
     */
    private static int extractKthDigit(int val, int k) {
        int digit;

        //right shift the val until the kth digit is in the LSD spot
        for (int i = 1; i < k; i++) {
            val = val / 10;
        }

        //mod by 10 to obtain the kth digit
        digit = val % 10;

        return digit;
    }

    /**
     * Helper method to determine the number passes needed for radixSort
     * 
     * O(n) method
     * @param arr   array in question
     * @return      the value K that is the number of passes needed for radix sort 
     */
    private static int determineK(int[] arr) {

        int max = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                max = arr[i];
                //since Integer.MIN_VALUE is 10 digits
                return 10;
            }

            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }

        int k = 0; 

        //to determine if a number is more, iteratively integer divide by 10 until the number is zero
        while (max > 0) {
            max /= 10;
            k++;
        }

        return k;
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(data);
        int[] ret = new int[data.size()];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = pq.remove();
        }


        return ret;
    }
}
