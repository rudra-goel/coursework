import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {
    private static final int TIMEOUT = 200;
    private CharacterComparator comparator;
    private List<Integer> emptyList;

    @Before
    public void setUp() {
        comparator = new CharacterComparator();
        emptyList = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTableRepeatedPattern() {
        // Tests pattern with repeated elements: "aaaa"
        String pattern = "aaaa";
        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        int[] expected = {0, 1, 2, 3};
        assertArrayEquals(expected, failureTable);
        assertEquals(3, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTableAlternatingPattern() {
        String pattern = "ababab";
        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        int[] expected = {0, 0, 1, 2, 3, 4};
        assertArrayEquals(expected, failureTable);
        assertEquals(5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTableSingleChar() {
        String pattern = "a";
        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        int[] expected = {0};
        assertArrayEquals(expected, failureTable);
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTableTwoChar() {
        String pattern = "ab";
        int[] failureTable = PatternMatching.buildFailureTable(pattern, comparator);
        int[] expected = {0, 0};
        assertArrayEquals(expected, failureTable);
        assertEquals(1, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPOverlappingMatches() {
        // Tests overlapping matches: "aaa" in "aaaa"
        String pattern = "aaa";
        String text = "aaaa";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        assertEquals(expected, PatternMatching.kmp(pattern, text, comparator));
        // 2 comparisons for failure table + 4 comparisons for matching
        assertEquals(6, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPRepeatedPattern() {
        // Tests text with many potential matches but only some are valid
        String pattern = "aa";
        String text = "aaaabaaa";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(5);
        expected.add(6);
        assertEquals(expected, PatternMatching.kmp(pattern, text, comparator));
        assertEquals(10, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPSingleCharPattern() {
        String pattern = "a";
        String text = "banana";
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(3);
        expected.add(5);
        assertEquals(expected, PatternMatching.kmp(pattern, text, comparator));
        assertEquals(6, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPPatternAtEnd() {
        String pattern = "end";
        String text = "This is the end";
        List<Integer> expected = new ArrayList<>();
        expected.add(12);
        assertEquals(expected, PatternMatching.kmp(pattern, text, comparator));
        assertEquals(18, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPEmptyText() {
        String pattern = "abc";
        String text = "";
        assertEquals(emptyList, PatternMatching.kmp(pattern, text, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKMPNullPattern() {
        PatternMatching.kmp(null, "text", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKMPNullText() {
        PatternMatching.kmp("pattern", null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKMPNullComparator() {
        PatternMatching.kmp("pattern", "text", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKMPEmptyPattern() {
        PatternMatching.kmp("", "text", comparator);
    }

    @Test
    public void testKMPLongPrefix() {
        // Tests pattern with long matching prefix/suffix
        String pattern = "aaaabaaaa";
        String text = "aaaabaaaabaaaa";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(5);
        assertEquals(expected, PatternMatching.kmp(pattern, text, comparator));
        assertEquals(25, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTableRepeatedChars() {
        // Test pattern with repeated characters to ensure last occurrence is used
        String pattern = "mississippi";
        Map<Character, Integer> lastTable = PatternMatching.buildLastTable(pattern);

        Map<Character, Integer> expected = new HashMap<>();
        expected.put('m', 0);
        expected.put('i', 10);
        expected.put('s', 6);
        expected.put('p', 9);

        assertEquals(expected, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTableSingleChar() {
        String pattern = "a";
        Map<Character, Integer> lastTable = PatternMatching.buildLastTable(pattern);

        Map<Character, Integer> expected = new HashMap<>();
        expected.put('a', 0);

        assertEquals(expected, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTableAllSameChar() {
        // Test pattern with all same characters
        String pattern = "aaaa";
        Map<Character, Integer> lastTable = PatternMatching.buildLastTable(pattern);

        Map<Character, Integer> expected = new HashMap<>();
        expected.put('a', 3);  // Should be last position

        assertEquals(expected, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoorePatternLargerThanText() {
        // Test where pattern is longer than text
        String pattern = "abcdef";
        String text = "abc";

        assertEquals(emptyList, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreOverlappingPattern() {
        String pattern = "aaa";
        String text = "aaaa";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);  // Should find overlapping matches
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(6, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreSkipCharacter() {
        // Test case where character in text doesn't exist in pattern
        // This tests the skipping behavior of Boyer-Moore
        String pattern = "abc";
        String text = "abxabc";  // 'x' isn't in pattern
        List<Integer> expected = new ArrayList<>();
        expected.add(3);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(4,comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoorePatternAtEnd() {
        // Test finding pattern at end of text
        String pattern = "end";
        String text = "This is the end";
        List<Integer> expected = new ArrayList<>();
        expected.add(12);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(7, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreWithManySkips() {
        // Test case that should trigger many skips due to mismatches
        String pattern = "xyz";
        String text = "abcabcabcxyz";
        List<Integer> expected = new ArrayList<>();
        expected.add(9);

        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(6,comparator.getComparisonCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoyerMooreNullPattern() {
        PatternMatching.boyerMoore(null, "text", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoyerMooreEmptyPattern() {
        PatternMatching.boyerMoore("", "text", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildLastTableNull() {
        PatternMatching.buildLastTable(null);
    }

    @Test
    public void testBuildLastTableEmpty() {
        Map<Character, Integer> result = PatternMatching.buildLastTable("");
        assertTrue(result.isEmpty());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullPattern() {
        PatternMatching.rabinKarp(null, "text", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testEmptyPattern() {
        PatternMatching.rabinKarp("", "text", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullText() {
        PatternMatching.rabinKarp("pattern", null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullComparator() {
        PatternMatching.rabinKarp("pattern", "text", null);
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpPatternLongerThanText() {
        String pattern = "abcdef";
        String text = "abc";
        assertEquals(emptyList, PatternMatching.rabinKarp(pattern, text, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpSingleCharacterPattern() {
        String pattern = "a";
        String text = "banana";
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(3);
        expected.add(5);
        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, comparator));
        assertEquals(3, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpAllRepeating() {
        String pattern = "aaa";
        String text = "aaaaa";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, comparator));
        assertEquals(9, comparator.getComparisonCount());  // 3 comparisons per match
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpPatternEqualsText() {
        String pattern = "abcde";
        String text = "abcde";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, comparator));
        assertEquals(5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpOverlappingMatches() {
        String pattern = "aa";
        String text = "aaaa";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, comparator));
        assertEquals(6, comparator.getComparisonCount());  // 2 comparisons per match
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpHashCollisionNoMatch() {
        // Both strings hash to 113
        String pattern = "\u0001\u0000";  // (ASCII 1,0)
        String text = "\u0000\u0071";  // (ASCII 0,113)
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, comparator));
        assertEquals(1, comparator.getComparisonCount());  // Compares the two characters once a hash match is found
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpPatternAtTextBoundaries() {
        String pattern = "test";
        String text = "testmiddletest";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(10);
        assertEquals(expected, PatternMatching.rabinKarp(pattern, text, comparator));
        assertEquals(8, comparator.getComparisonCount());  // 4 comparisons per match
    }

    // @Test
    // public void testGalilComparisons() {
    //     String pattern = "aaba";
    //     String text = "aabaaba";
    //     List<Integer> expected = new ArrayList<>();
    //     expected.add(0);
    //     expected.add(3);
    //     assertEquals(expected, PatternMatching.boyerMooreGalilRule(pattern, text, comparator));
    //     assertEquals(11, comparator.getComparisonCount()); // 4 to build failure table + 7
    // }

    // @Test(timeout = TIMEOUT)
    // public void testGalilPatternLargerThanText() {
    //     // Test where pattern is longer than text
    //     String pattern = "abcdef";
    //     String text = "abc";
    //     assertEquals(emptyList, PatternMatching.boyerMooreGalilRule(pattern, text, comparator));
    //     assertEquals(0, comparator.getComparisonCount());
    // }

    // @Test(timeout = TIMEOUT)
    // public void testGalilOverlappingPattern() {
    //     // Test overlapping pattern matches
    //     String pattern = "aaa";
    //     String text = "aaaa";
    //     List<Integer> expected = new ArrayList<>();
    //     expected.add(0);
    //     expected.add(1);
    //     assertEquals(expected, PatternMatching.boyerMooreGalilRule(pattern, text, comparator));
    //     assertEquals(6, comparator.getComparisonCount());
    // }

    // @Test(timeout = TIMEOUT)
    // public void testGalilSkipCharacter() {
    //     // Test case where character in text doesn't exist in pattern
    //     // This tests the skipping behavior of Boyer-Moore
    //     String pattern = "abab";
    //     String text = "axababab";
    //     List<Integer> expected = new ArrayList<>();
    //     expected.add(2);
    //     expected.add(4);
    //     assertEquals(expected, PatternMatching.boyerMooreGalilRule(pattern, text, comparator));
    //     assertEquals(12, comparator.getComparisonCount());
    // }

    // @Test(timeout = TIMEOUT)
    // public void testGalilAtEnd() {
    //     // Test finding pattern at end of text
    //     String pattern = "end";
    //     String text = "This is the end";
    //     List<Integer> expected = new ArrayList<>();
    //     expected.add(12);
    //     assertEquals(expected, PatternMatching.boyerMooreGalilRule(pattern, text, comparator));
    //     assertEquals(9, comparator.getComparisonCount());
    // }

    // @Test(timeout = TIMEOUT)
    // public void testGalilWithManySkips() {
    //     // Test case that should trigger many skips due to mismatches
    //     String pattern = "xyz";
    //     String text = "abcabcabcxyz";
    //     List<Integer> expected = new ArrayList<>();
    //     expected.add(9);
    //     assertEquals(expected, PatternMatching.boyerMooreGalilRule(pattern, text, comparator));
    //     assertEquals(8, comparator.getComparisonCount());
    // }

    // @Test
    // public void testGalilWithMultiple() {
    //     String pattern = "abc";
    //     String text = "xxabcabc";
    //     List<Integer> expected = new ArrayList<>();
    //     expected.add(2);
    //     expected.add(5);
    //     assertEquals(expected, PatternMatching.boyerMooreGalilRule(pattern, text, comparator));
    //     assertEquals(9, comparator.getComparisonCount());
    // }

}