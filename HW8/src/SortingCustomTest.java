import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.Long.min;
import static java.lang.Integer.valueOf;
import static java.lang.Long.max;
import static org.junit.Assert.*;


public class SortingCustomTest {


    @Test
    public void testSortEmpty() {
        Item[] arr = new Item[0];
        TestComparator comparator = new TestComparator();

        Sorting.cocktailSort(arr, comparator);
        assertEquals(0, comparator.count);

        Sorting.insertionSort(arr, comparator);
        assertEquals(0, comparator.count);

        Sorting.quickSort(arr, comparator, new Random(678));
        assertEquals(0, comparator.count);

        Sorting.mergeSort(arr, comparator);
        assertEquals(0, comparator.count);

        Sorting.heapSort(List.of());
        Sorting.lsdRadixSort(new int[0]);
    }

    @Test
    public void testSortOne() {
        Item o = new Item(5);
        Item[] arr = new Item[]{o};
        TestComparator comparator = new TestComparator();

        Sorting.cocktailSort(arr, comparator);
        assertEquals(0, comparator.count);
        assertSame(o, arr[0]);

        Sorting.insertionSort(arr, comparator);
        assertEquals(0, comparator.count);
        assertSame(o, arr[0]);

        Sorting.quickSort(arr, comparator, new Random(678));
        assertEquals(0, comparator.count);
        assertSame(o, arr[0]);

        Sorting.mergeSort(arr, comparator);
        assertEquals(0, comparator.count);
        assertSame(o, arr[0]);

        List<Integer> a = List.of(5);
        Sorting.heapSort(a);
        assertEquals(valueOf(5), a.get(0));
        assertEquals(1, a.size());

        int[] b = new int[]{5};
        Sorting.lsdRadixSort(b);
        assertEquals(5, b[0]);
        assertEquals(1, b.length);
    }

    @Test
    public void testSortTwoInOrder() {
        Item o1 = new Item(5);
        Item o2 = new Item(6);
        Item[] arr = new Item[]{o1, o2};
        TestComparator comparator = new TestComparator();

        Sorting.cocktailSort(arr, comparator);
        assertEquals(1, comparator.count);
        assertSame(o1, arr[0]);
        assertSame(o2, arr[1]);

        Sorting.insertionSort(arr, comparator);
        assertEquals(2, comparator.count);
        assertSame(o1, arr[0]);
        assertSame(o2, arr[1]);

        Sorting.quickSort(arr, comparator, new Random(678));
        assertEquals(3, comparator.count);
        assertSame(o1, arr[0]);
        assertSame(o2, arr[1]);

        Sorting.mergeSort(arr, comparator);
        assertEquals(4, comparator.count);
        assertSame(o1, arr[0]);
        assertSame(o2, arr[1]);

        List<Integer> a = List.of(5, 6);
        Sorting.heapSort(a);
        assertEquals(valueOf(5), a.get(0));
        assertEquals(valueOf(6), a.get(1));
        assertEquals(2, a.size());

        int[] b = new int[]{5, 6};
        Sorting.lsdRadixSort(b);
        assertEquals(5, b[0]);
        assertEquals(6, b[1]);
        assertEquals(2, b.length);
    }

    @Test
    public void testCocktailShakerSortHeuristic() {
        int[] a = new int[]{1,2,3,5,13,10,11,4,10,9,4,120,123,142};
        Item[] arr = new Item[a.length];
        for (int i = 0; i < a.length; i++) arr[i] = new Item(a[i]);

        TestComparator comparator = new TestComparator();

        Sorting.cocktailSort(arr, comparator);

        for (int i = 1; i < a.length; i++) {
            assertTrue(arr[i-1].inStableOrder(arr[i]));
        }
        assertTrue(comparator.count <= 35); // This number came from the vis tool
    }

    @Test
    public void testSortTwoOutOrder() {
        Item o1 = new Item(6);
        Item o2 = new Item(5);
        Item[] arr = new Item[]{o1, o2};
        TestComparator comparator = new TestComparator();


        Sorting.cocktailSort(arr, comparator);
        assertEquals(1, comparator.count);
        assertSame(o1, arr[1]);
        assertSame(o2, arr[0]);

        arr = new Item[]{o1, o2};
        Sorting.insertionSort(arr, comparator);
        assertEquals(2, comparator.count);
        assertSame(o1, arr[1]);
        assertSame(o2, arr[0]);

        arr = new Item[]{o1, o2};
        Sorting.quickSort(arr, comparator, new Random(678));
        assertEquals(4, comparator.count);
        assertSame(o1, arr[1]);
        assertSame(o2, arr[0]);

        arr = new Item[]{o1, o2};
        Sorting.mergeSort(arr, comparator);
        assertEquals(5, comparator.count);
        assertSame(o1, arr[1]);
        assertSame(o2, arr[0]);

        List<Integer> a = List.of(6, 5);
        int[] a2 = Sorting.heapSort(a);
        assertEquals(5, a2[0]);
        assertEquals(6, a2[1]);
        assertEquals(2, a.size());

        int[] b = new int[]{6, 5};
        Sorting.lsdRadixSort(b);
        assertEquals(5, b[0]);
        assertEquals(6, b[1]);
        assertEquals(2, b.length);
    }

    @Test
    public void testMergeSort5() {
        Item o1 = new Item(5);
        Item o2 = new Item(6);
        Item o3 = new Item(3);
        Item o4 = new Item(4);
        Item o5 = new Item(8);
        Item[] arr = new Item[]{o1, o2, o3, o4, o5};
        TestComparator comparator = new TestComparator();

        Sorting.mergeSort(arr, comparator);

        for(int i = 1; i < arr.length; i++) {
            assertTrue(arr[i-1].inStableOrder(arr[i]));
        }
    }

    @Test
    public void cocktailSort5000() {
        Random rand = new Random();

        Item[] arr = new Item[5000];
        for (int i = 0; i < 5000; i++) {
            arr[i] = new Item(rand.nextInt());
        }

        Sorting.cocktailSort(arr, new TestComparator());

        for (int i = 1; i < 5000; i++) {
            assertTrue(arr[i-1].inStableOrder(arr[i]));
        }
    }

    @Test
    public void insertionSort7500() {
        Random rand = new Random();

        Item[] arr = new Item[7500];
        for (int i = 0; i < 7500; i++) {
            arr[i] = new Item(rand.nextInt(23));
        }

        Sorting.insertionSort(arr, new TestComparator());

        for (int i = 1; i < 7500; i++) {
            assertTrue(arr[i-1].inStableOrder(arr[i]));
        }
    }

    @Test
    public void mergeSort100000() {
        Random rand = new Random();

        Item[] arr = new Item[100000];
        for (int i = 0; i < 100000; i++) {
            arr[i] = new Item(rand.nextInt());
        }

        Sorting.mergeSort(arr, new TestComparator());

        for (int i = 1; i < 100000; i++) {
            assertTrue(arr[i-1].inStableOrder(arr[i]));
        }
    }

    @Test
    public void quickSort100000() {
        Random rand = new Random();

        Item[] arr = new Item[100000];
        for (int i = 0; i < 100000; i++) {
            arr[i] = new Item(rand.nextInt());
        }

        Sorting.quickSort(arr, new TestComparator(), rand);

        for (int i = 1; i < 100000; i++) {
            assertTrue(arr[i-1].inOrder(arr[i]));
        }
    }

    @Test
    public void heapSort100000() {
        Random rand = new Random();

        Integer[] arr = new Integer[100000];
        for (int i = 0; i < 100000; i++) {
            arr[i] = rand.nextInt();
        }

        int[] ans = Sorting.heapSort(List.of(arr));

        for (int i = 1; i < 100000; i++) {
            assertTrue(ans[i-1] <= ans[i]);
        }
    }

    @Test
    public void radixSort100000() {
        Random rand = new Random();

        int[] arr = new int[100000];
        for (int i = 0; i < 100000; i++) {
            arr[i] = rand.nextInt();
        }

        Sorting.lsdRadixSort(arr);

        for (int i = 1; i < 100000; i++) {
            assertTrue(arr[i-1] <= arr[i]);
        }
    }

    @Test
    public void radixSortLimits() {
        int[] arr1 = new int[]{5, 2, -123, 124, Integer.MIN_VALUE, 159, -16, 591, Integer.MAX_VALUE, Integer.MIN_VALUE, 18247, -3};
        int[] arr2 = arr1.clone();
        Arrays.sort(arr2);

        Sorting.lsdRadixSort(arr1);

        assertArrayEquals(arr2, arr1);
    }

    @Test
    public void radixSortOpposingNegatives() {
        int[] arr1 = new int[]{15, -3, 5, -5, 3, -15};
        int[] arr2 = arr1.clone();
        Arrays.sort(arr2);

        Sorting.lsdRadixSort(arr1);

        assertArrayEquals(arr2, arr1);
    }

    @Test
    public void radixSortMaxPower10() {
        int[] arr1 = new int[]{-100, 12, 59094, 100000, 1947, 102, -342, -59278};
        int[] arr2 = arr1.clone();
        Arrays.sort(arr2);

        Sorting.lsdRadixSort(arr1);

        assertArrayEquals(arr2, arr1);
    }

    @Test
    public void radixSortMinPower10() {
        int[] arr1 = new int[]{-100, 12, 59094, -100000, 1947, 102, -342, -59278};
        int[] arr2 = arr1.clone();
        Arrays.sort(arr2);

        Sorting.lsdRadixSort(arr1);

        assertArrayEquals(arr2, arr1);
    }

    @Test
    public void testMinValueOverflow() {
        int[] arr1 = new int[]{10, 5, Integer.MIN_VALUE};
        int[] arr2 = arr1.clone();
        Arrays.sort(arr2);

        Sorting.lsdRadixSort(arr1);

        assertArrayEquals(arr2, arr1);
    }

    private static class TestComparator implements Comparator<Item> {
        int count = 0;

        @Override
        public int compare(Item o1, Item o2) {
            count++;
            return clamp((long)o1.value - (long)o2.value);
        }

        private int clamp(long a) {
            return (int) min(max(a, -1000L), 1000L);
        }
    }

    private static class Item implements Comparable<Item> {
        private final int value;
        private final long rIDSort;

        private static long counter = 0;

        public Item(int value) {
            this.value = value;
            this.rIDSort = counter++;
        }

        public boolean inOrder(Item next) {
            return next.value >= this.value;
        }

        public boolean inStableOrder(Item next) {
            if (next.value == this.value) {
                return next.rIDSort > this.rIDSort;
            } else {
                return inOrder(next);
            }
        }

        @Override
        public int compareTo(Item o) {
            fail("Tried to use compareTo instead of comparator!");
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            fail("Tried to use equals instead of comparator!");
            return false;
        }
    }
}