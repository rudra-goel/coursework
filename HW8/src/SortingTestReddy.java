import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ishaan Reddy
 * @version 1.0
 */

public class SortingTestReddy {
    private static final int TIMEOUT = 200;
    private IntegerWrapper[] integers;
    private IntegerWrapper[] sortedIntegers;
    private ComparatorPlus<IntegerWrapper> comp;

    @Before
    public void setUp() {
        integers = new IntegerWrapper[] {
            new IntegerWrapper(10), new IntegerWrapper(7), new IntegerWrapper(3),
            new IntegerWrapper(8), new IntegerWrapper(2), new IntegerWrapper(5),
            new IntegerWrapper(1), new IntegerWrapper(9)
        };
        sortedIntegers = new IntegerWrapper[] {
            new IntegerWrapper(1), new IntegerWrapper(2), new IntegerWrapper(3),
            new IntegerWrapper(5), new IntegerWrapper(7), new IntegerWrapper(8),
            new IntegerWrapper(9), new IntegerWrapper(10)
        };
        comp = IntegerWrapper.getComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(integers, comp);
        assertArrayEquals(sortedIntegers, integers);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 28 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Sorting.cocktailSort(integers, comp);
        assertArrayEquals(sortedIntegers, integers);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 28 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(integers, comp);
        assertArrayEquals(sortedIntegers, integers);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 20 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        int randomSeed = 5;
        Sorting.quickSort(integers, comp, new Random(randomSeed));
        assertArrayEquals(sortedIntegers, integers);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 20 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[]{45, 23, 89, 12, 78, 34, 56, 90};
        int[] sortedArray = new int[]{12, 23, 34, 45, 56, 78, 89, 90};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSort() {
        int[] unsortedArray = new int[] {45, 23, 89, 12, 78, 34, 56, 90};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {12, 23, 34, 45, 56, 78, 89, 90};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    // Additional tests with different values

    @Test(timeout = TIMEOUT)
    public void testInsertionSortDifferentValues() {
        IntegerWrapper[] differentIntegers = new IntegerWrapper[] {
            new IntegerWrapper(15), new IntegerWrapper(11), new IntegerWrapper(19),
            new IntegerWrapper(14), new IntegerWrapper(13), new IntegerWrapper(16)
        };
        IntegerWrapper[] sortedDifferentIntegers = new IntegerWrapper[] {
            new IntegerWrapper(11), new IntegerWrapper(13), new IntegerWrapper(14),
            new IntegerWrapper(15), new IntegerWrapper(16), new IntegerWrapper(19)
        };
        Sorting.insertionSort(differentIntegers, comp);
        assertArrayEquals(sortedDifferentIntegers, differentIntegers);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortDifferentValues() {
        IntegerWrapper[] differentIntegers = new IntegerWrapper[] {
            new IntegerWrapper(25), new IntegerWrapper(21), new IntegerWrapper(29),
            new IntegerWrapper(24), new IntegerWrapper(23), new IntegerWrapper(26)
        };
        IntegerWrapper[] sortedDifferentIntegers = new IntegerWrapper[] {
            new IntegerWrapper(21), new IntegerWrapper(23), new IntegerWrapper(24),
            new IntegerWrapper(25), new IntegerWrapper(26), new IntegerWrapper(29)
        };
        Sorting.cocktailSort(differentIntegers, comp);
        assertArrayEquals(sortedDifferentIntegers, differentIntegers);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortDifferentValues() {
        IntegerWrapper[] differentIntegers = new IntegerWrapper[] {
            new IntegerWrapper(35), new IntegerWrapper(31), new IntegerWrapper(39),
            new IntegerWrapper(34), new IntegerWrapper(33), new IntegerWrapper(36)
        };
        IntegerWrapper[] sortedDifferentIntegers = new IntegerWrapper[] {
            new IntegerWrapper(31), new IntegerWrapper(33), new IntegerWrapper(34),
            new IntegerWrapper(35), new IntegerWrapper(36), new IntegerWrapper(39)
        };
        Sorting.mergeSort(differentIntegers, comp);
        assertArrayEquals(sortedDifferentIntegers, differentIntegers);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortDifferentValues() {
        IntegerWrapper[] differentIntegers = new IntegerWrapper[] {
            new IntegerWrapper(45), new IntegerWrapper(41), new IntegerWrapper(49),
            new IntegerWrapper(44), new IntegerWrapper(43), new IntegerWrapper(46)
        };
        IntegerWrapper[] sortedDifferentIntegers = new IntegerWrapper[] {
            new IntegerWrapper(41), new IntegerWrapper(43), new IntegerWrapper(44),
            new IntegerWrapper(45), new IntegerWrapper(46), new IntegerWrapper(49)
        };
        Sorting.quickSort(differentIntegers, comp, new Random());
        assertArrayEquals(sortedDifferentIntegers, differentIntegers);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSortDifferentValues() {
        int[] unsortedArray = new int[]{65, 43, 99, 22, 88, 54, 76, 100};
        int[] sortedArray = new int[]{22, 43, 54, 65, 76, 88, 99, 100};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortDifferentValues() {
        int[] unsortedArray = new int[] {65, 43, 99, 22, 88, 54, 76, 100};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {22, 43, 54, 65, 76, 88, 99, 100};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    // Edge case tests

    @Test(timeout = TIMEOUT)
    public void testInsertionSortSingleElement() {
        IntegerWrapper[] singleElement = new IntegerWrapper[] {
            new IntegerWrapper(1)
        };
        IntegerWrapper[] sortedSingleElement = new IntegerWrapper[] {
            new IntegerWrapper(1)
        };
        Sorting.insertionSort(singleElement, comp);
        assertArrayEquals(sortedSingleElement, singleElement);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortSingleElement() {
        IntegerWrapper[] singleElement = new IntegerWrapper[] {
            new IntegerWrapper(1)
        };
        IntegerWrapper[] sortedSingleElement = new IntegerWrapper[] {
            new IntegerWrapper(1)
        };
        Sorting.cocktailSort(singleElement, comp);
        assertArrayEquals(sortedSingleElement, singleElement);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortSingleElement() {
        IntegerWrapper[] singleElement = new IntegerWrapper[] {
            new IntegerWrapper(1)
        };
        IntegerWrapper[] sortedSingleElement = new IntegerWrapper[] {
            new IntegerWrapper(1)
        };
        Sorting.mergeSort(singleElement, comp);
        assertArrayEquals(sortedSingleElement, singleElement);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSortSingleElement() {
        IntegerWrapper[] singleElement = new IntegerWrapper[] {
            new IntegerWrapper(1)
        };
        IntegerWrapper[] sortedSingleElement = new IntegerWrapper[] {
            new IntegerWrapper(1)
        };
        Sorting.quickSort(singleElement, comp, new Random());
        assertArrayEquals(sortedSingleElement, singleElement);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSortSingleElement() {
        int[] singleElement = new int[]{1};
        int[] sortedSingleElement = new int[]{1};
        Sorting.lsdRadixSort(singleElement);
        assertArrayEquals(sortedSingleElement, singleElement);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortSingleElement() {
        List<Integer> singleElementList = new ArrayList<>();
        singleElementList.add(1);
        int[] sortedSingleElement = new int[]{1};
        int[] actualArray = Sorting.heapSort(singleElementList);
        assertArrayEquals(sortedSingleElement, actualArray);
    }

    // Inner class IntegerWrapper
    public static class IntegerWrapper implements Comparable<IntegerWrapper> {
        private Integer value;

        public IntegerWrapper(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public int compareTo(IntegerWrapper other) {
            return this.value.compareTo(other.value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            IntegerWrapper that = (IntegerWrapper) obj;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return value.toString();
        }

        public static ComparatorPlus<IntegerWrapper> getComparator() {
            return new ComparatorPlus<IntegerWrapper>() {
                private int count = 0;

                @Override
                public int compare(IntegerWrapper o1, IntegerWrapper o2) {
                    incrementCount();
                    return o1.compareTo(o2);
                }

                @Override
                public int getCount() {
                    return count;
                }

                @Override
                public void resetCount() {
                    count = 0;
                }

                private void incrementCount() {
                    count++;
                }
            };
        }
    }

    // Inner class IntegerWrapperComparator
    public static class IntegerWrapperComparator implements ComparatorPlus<IntegerWrapper> {
        private int count;

        @Override
        public int compare(IntegerWrapper o1, IntegerWrapper o2) {
            count++;
            return o1.compareTo(o2);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public void resetCount() {
            count = 0;
        }
    }

    // Inner interface ComparatorPlus
    public interface ComparatorPlus<T> extends Comparator<T> {
        int getCount();
        void resetCount();
    }
}