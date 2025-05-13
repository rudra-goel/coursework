import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
}

class ValueComparator implements Comparator<Value> {
    @Override
    public int compare(Value o1, Value o2) {
        return Integer.valueOf(o1.value).compareTo(o2.value);
    }
}

class Value {
    int value;

    Value(int v) {
        value = v;
    }

    @Override
    public String toString() {
        return String.format("%s %d", super.toString(), value);
    }
}

public class SortingStudentTest2 {
    @Test(timeout = 200)
    public void testMergeSortOdd() {
        var unsorted = new Integer[]{8, 3, 9, 1, 0};
        var sorted = new Integer[]{8, 3, 9, 1, 0};
        Arrays.sort(sorted);
        Sorting.mergeSort(unsorted, new IntegerComparator());
        assertArrayEquals(sorted, unsorted);
    }

    @Test(timeout = 10000)
    public void testMergeSortFuzz() {
        int length = 1 * 1000 * 1000;
        Random r = new Random(1729471607);

        var unsorted = new ArrayList<Value>(length);
        var sorted = new ArrayList<Value>(length);

        for (int i = 0; i < length; i++) {
            int k = r.nextInt();
            Value v = new Value(k);
            unsorted.add(v);
            sorted.add(v);
        }

        sorted.sort(new ValueComparator());

        var arr = unsorted.toArray(new Value[length]);

        Sorting.mergeSort(arr, new ValueComparator());

//        System.out.print("my   ");
//        System.out.println(Arrays.toString(arr));
//        System.out.print("java ");
//        System.out.println(Arrays.toString(sorted.toArray()));

        assertArrayEquals(sorted.toArray(new Value[0]), arr);
    }

    @Test(timeout = 10000)
    public void testRadixSortFuzz() {
        int length = 1 * 1000 * 1000;
        Random r = new Random(1729471607);

        var unsorted = new ArrayList<Integer>(length);
        var sorted = new ArrayList<Integer>(length);

        
        for (int i = 0; i < length; i++) {
                int k = r.nextInt();
                unsorted.add(k);
                sorted.add(k);
        }
        sorted.sort(new IntegerComparator());
        var arr = unsorted.stream().mapToInt(Integer::intValue).toArray();
        
        Sorting.lsdRadixSort(arr);
//        System.out.print("my   ");
//        System.out.println(Arrays.toString(arr));
//        System.out.print("java ");
//        System.out.println(Arrays.toString(sorted.toArray()));
        var sortedArray = sorted.stream().mapToInt(Integer::intValue).toArray();
        assertArrayEquals(sortedArray, arr);
    }

    @Test(timeout = 10000)
    public void testRadixSortNegative() {
        var unsorted = new ArrayList<>(Arrays.asList(10, -1, 1, 0, -10));
        var sorted = new ArrayList<>(Arrays.asList(-10, -1, 0, 1, 10));
        sorted.sort(new IntegerComparator());
        var arr = unsorted.stream().mapToInt(Integer::intValue).toArray();
        Sorting.lsdRadixSort(arr);
        System.out.print("my   ");
        System.out.println(Arrays.toString(arr));
        System.out.print("java ");
        System.out.println(Arrays.toString(sorted.toArray()));
        var sortedArray = sorted.stream().mapToInt(Integer::intValue).toArray();
        assertArrayEquals(sortedArray, arr);
    }

    @Test(timeout = 100)
    public void testExceptions() {
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.insertionSort(new Integer[1], null));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.insertionSort(null, new IntegerComparator()));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.insertionSort(null, null));

        assertThrows(IllegalArgumentException.class, () ->
                Sorting.cocktailSort(new Integer[1], null));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.cocktailSort(null, new IntegerComparator()));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.cocktailSort(null, null));

        assertThrows(IllegalArgumentException.class, () ->
                Sorting.mergeSort(new Integer[1], null));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.mergeSort(null, new IntegerComparator()));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.mergeSort(null, null));

        assertThrows(IllegalArgumentException.class, () ->
                Sorting.quickSort(new Integer[1], null, new Random()));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.quickSort(null, new IntegerComparator(), new Random()));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.quickSort(null, null, new Random()));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.quickSort(new Integer[1], new IntegerComparator(), null));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.quickSort(new Integer[1], null, null));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.quickSort(null, new IntegerComparator(), null));
        assertThrows(IllegalArgumentException.class, () ->
                Sorting.quickSort(null, null, null));

        assertThrows(IllegalArgumentException.class, () ->
                Sorting.lsdRadixSort(null));

        assertThrows(IllegalArgumentException.class, () ->
                Sorting.heapSort(null));
    }

    private void assertThrows(Class<? extends Exception> exceptionClass, Runnable runnable) {
        // Method copied straight from Vraj. Thanks Vraj!
        try {
            runnable.run();
        } catch (Exception e) {
            if (!exceptionClass.isInstance(e)) {
                throw new AssertionFailedError("Runnable did not throw correct exception");
            }
            return;
        }
        throw new AssertionFailedError("Runnable did not throw exception");
    }
}