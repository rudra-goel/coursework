import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PatternMatchingCustomTests {

    CharacterComparator comp;

    @Before
    public void setup() {
        comp = new CharacterComparator();
    }

    @Test
    public void testKMPNullAndLengthZero() {
        String a = "aasdasd";

        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.kmp(null, a, comp); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.kmp(a, null, comp); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.kmp(a, a, null); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.kmp("", a, comp); });
    }

    @Test
    public void testKMPMissingPattern() {
        String pattern = "adfkljaklf";
        String text = "adjakfjjkvnkfjjkanaksvhasfsadfalifkjaslfjioearjlkv";

        assertEquals(
                0,
                PatternMatching.kmp(pattern, text, comp).size()
        );

        assertEquals(59, comp.getComparisonCount());
    }

    @Test
    public void testKMPPatternIsText() {
        String pattern = "adfkljaklf";
        String text = pattern;

        assertListEquals(
                List.of(0),
                PatternMatching.kmp(pattern, text, comp)
        );

        assertEquals(20, comp.getComparisonCount());
    }

    @Test
    public void testPatternOccurOverlapKMP() {
        String pattern = "aabaaba";
            String text = "aabaabaabaababaabaaba";

        assertListEquals(
                List.of(0, 3, 6, 14),
                PatternMatching.kmp(pattern, text, comp)
        );

        assertEquals(30, comp.getComparisonCount());
    }

    @Test
    public void testPatternLargerThenTextKMP() {
        String pattern = "abcdefg";
        String text = "abcd";

        assertListEquals(
                List.of(),
                PatternMatching.kmp(pattern, text, comp)
        );

        assertEquals(0, comp.getComparisonCount());
    }

    @Test
    public void testBoyerMooreNullAndLengthZero() {
        String a = "aasdasd";

        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.boyerMoore(null, a, comp); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.boyerMoore(a, null, comp); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.boyerMoore(a, a, null); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.boyerMoore("", a, comp); });
    }

    @Test
    public void testBMMissingPattern() {
        String pattern = "adfkljaklf";
        String text = "adjakfjjkvnkfjjkanaksvhasfsadfalifkjaslfjioearjlkv";

        assertEquals(
                0,
                PatternMatching.boyerMoore(pattern, text, comp).size()
        );

        assertEquals(6, comp.getComparisonCount());
    }

    @Test
    public void testBMPatternIsText() {
        String pattern = "adfkljaklf";
        String text = pattern;

        assertListEquals(
                List.of(0),
                PatternMatching.boyerMoore(pattern, text, comp)
        );

        assertEquals(10, comp.getComparisonCount());
    }

    @Test
    public void testBoyerMooreNoCommonLetters() {
        String pattern = "abcdabcdbca";
        String text = "rtjlehklhjktrhmrklgnjrognjrklnjrklnkjrklnjrgrknlrgn";

        assertListEquals(
                List.of(),
                PatternMatching.boyerMoore(pattern, text, comp)
        );

        assertEquals(4, comp.getComparisonCount());
    }

    @Test
    public void testPatternOccurOverlapBM() {
        String pattern = "aabaaba";
        String text = "aabaabaabaababaabaaba";

        assertListEquals(
                List.of(0, 3, 6, 14),
                PatternMatching.boyerMoore(pattern, text, comp)
        );

        assertEquals(52, comp.getComparisonCount());
    }

    @Test
    public void testPatternLargerThenTextBM() {
        String pattern = "abcdefg";
        String text = "abcd";

        assertListEquals(
                List.of(),
                PatternMatching.boyerMoore(pattern, text, comp)
        );

        assertEquals(0, comp.getComparisonCount());
    }

    @Test
    public void testRabinKarpNullAndLengthZero() {
        String a = "aasdasd";

        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.rabinKarp(null, a, comp); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.rabinKarp(a, null, comp); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.rabinKarp(a, a, null); });
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.rabinKarp("", a, comp); });
    }

    @Test
    public void testRKMissingPattern() {
        String pattern = "adfkljaklf";
        String text = "adjakfjjkvnkfjjkanaksvhasfsadfalifkjaslfjioearjlkv";

        assertEquals(
                0,
                PatternMatching.rabinKarp(pattern, text, comp).size()
        );

        assertEquals(0, comp.getComparisonCount());
    }

    @Test
    public void testRKPatternIsText() {
        String pattern = "adfkljaklf";
        String text = pattern;

        assertListEquals(
                List.of(0),
                PatternMatching.rabinKarp(pattern, text, comp)
        );

        assertEquals(10, comp.getComparisonCount());
    }

    @Test
    public void testPatternOccurOverlapRK() {
        String pattern = "aabaaba";
        String text = "aabaabaabaababaabaaba";

        assertListEquals(
                List.of(0, 3, 6, 14),
                PatternMatching.rabinKarp(pattern, text, comp)
        );

        assertEquals(28, comp.getComparisonCount());
    }

    @Test
    public void testPatternLargerThenTextRK() {
        String pattern = "abcdefg";
        String text = "abcd";

        assertListEquals(
                List.of(),
                PatternMatching.rabinKarp(pattern, text, comp)
        );

        assertEquals(0, comp.getComparisonCount());
    }

    @Test
    public void testPatternHashCollision() {
        // Take advantage of the fact that #㇡ has the same has as qཱི in base 113 (16724)
        String a = "#㇡";
        String b = "qཱི";

        assertEquals(
                ((int) a.charAt(0)) * 113 + ((int) a.charAt(1)),
                ((int) b.charAt(0)) * 113 + ((int) b.charAt(1))
        ); // This should always be true (maybe not depending on your OS)?
        // If this is false, then you can disregard this test

        String pattern = a + b + a + b;
        String text = a + b + a + b + a + b + a + b + a + b + a + b + "cde" + b + a + b + a;


        assertListEquals(
                List.of(0, 4, 8, 12, 16),
                PatternMatching.rabinKarp(pattern, text, comp)
        );

        // 5*8 for the correct ones + 5 for the incorrect ones
        assertEquals(5 * 8 + 5, comp.getComparisonCount());
    }

    @Test 
    public void testRoll() {
        String text = "rudracodyjoerudra";
        String pattern = "rudra";

        assertEquals(List.of(0, 12), PatternMatching.rabinKarp(pattern, text, comp));

    }

    @Test
    public void testRKAlmostOverflow() {
        // Adding just one more 'z' would cause this to overflow!
        // If you fail this test but pass the other Rabin Karp ones, it probably means that
        // you are not calculating your rolling hash correctly
        String pattern = "zzzzzz";
        String text = "zzzzzzzzzzzzzzzzzzzzzzzzz";

        assertListEquals(
                List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                            11, 12, 13, 14, 15, 16, 17, 18, 19
                        ),
                PatternMatching.rabinKarp(pattern, text, comp)
        );

        assertEquals(20*6, comp.getComparisonCount());
    }

    // @Test
    // public void testGalilRuleNullAndLengthZero() {
    //     String a = "aasdasd";

    //     assertThrows(IllegalArgumentException.class, () -> {PatternMatching.boyerMooreGalilRule(null, a, comp); });
    //     assertThrows(IllegalArgumentException.class, () -> {PatternMatching.boyerMooreGalilRule(a, null, comp); });
    //     assertThrows(IllegalArgumentException.class, () -> {PatternMatching.boyerMooreGalilRule(a, a, null); });
    //     assertThrows(IllegalArgumentException.class, () -> {PatternMatching.boyerMooreGalilRule("", a, comp); });
    // }

    // @Test
    // public void testGRMissingPattern() {
    //     String pattern = "adfkljaklf";
    //     String text = "adjakfjjkvnkfjjkanaksvhasfsadfalifkjaslfjioearjlkv";

    //     assertEquals(
    //             0,
    //             PatternMatching.boyerMooreGalilRule(pattern, text, comp).size()
    //     );

    //     assertEquals(16, comp.getComparisonCount());
    // }

    // @Test
    // public void testGRPatternIsText() {
    //     String pattern = "adfkljaklf";
    //     String text = pattern;

    //     assertListEquals(
    //             List.of(0),
    //             PatternMatching.boyerMooreGalilRule(pattern, text, comp)
    //     );

    //     assertEquals(20, comp.getComparisonCount());
    // }

    // @Test
    // public void testPatternOccurOverlapGR() {
    //     String pattern = "aabaaba";
    //     String text = "aabaabaabaababaabaaba";

    //     assertListEquals(
    //             List.of(0, 3, 6, 14),
    //             PatternMatching.boyerMooreGalilRule(pattern, text, comp)
    //     );

    //     assertEquals(40, comp.getComparisonCount());
    // }

    // @Test
    // public void testPatternLargerThenTextGR() {
    //     String pattern = "abcdefg";
    //     String text = "abcd";

    //     assertListEquals(
    //             List.of(),
    //             PatternMatching.boyerMooreGalilRule(pattern, text, comp)
    //     );

    //     assertEquals(0, comp.getComparisonCount());
    // }

    @Test
    public void testBuildFailureTableWithNulls() {
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.buildFailureTable(null, comp);});
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.buildFailureTable("", null);});
    }

    @Test
    public void testBuildFailureTableOfEmpty() {
        assertArrayEquals(
                new int[0],
                PatternMatching.buildFailureTable("", comp)
        );
    }

    @Test
    public void testBuildFailureTableUnique() {
        assertArrayEquals(
                new int[]{0, 0, 0, 0, 0, 0, 0},
                PatternMatching.buildFailureTable("abcdefg", comp)
        );

        assertEquals(6, comp.getComparisonCount());
    }

    @Test
    public void testBuildFailureAllSame() {
        assertArrayEquals(
                new int[]{0, 1, 2, 3, 4, 5, 6},
                PatternMatching.buildFailureTable("aaaaaaa", comp)
        );

        assertEquals(6, comp.getComparisonCount());
    }

    @Test
    public void testBuildFailureTableDropsToZero() {
        assertArrayEquals(
                new int[]{0, 0, 0, 1, 2, 0, 1, 2, 3, 4},
                PatternMatching.buildFailureTable("abcabdabca", comp)
        );

        assertEquals(10, comp.getComparisonCount());
    }

    @Test
    public void testBuildFailureTablePartialDrops() {
        assertArrayEquals(
                new int[]{0, 0, 0, 1, 2, 3, 4, 1, 2, 3, 0, 1, 1},
                PatternMatching.buildFailureTable("acbacbaacbcaa", comp)
        );

        assertEquals(16, comp.getComparisonCount());
    }

    @Test
    public void testBuildLOTWithNulls() {
        assertThrows(IllegalArgumentException.class, () -> {PatternMatching.buildLastTable(null);});
    }

    @Test
    public void testBuildLOTWithEmpty() {
        Map<Character, Integer> a = PatternMatching.buildLastTable("");

        assertEquals(0, a.size());
    }

    @Test
    public void testBuildLOTWithDistinct() {
        Map<Character, Integer> a = PatternMatching.buildLastTable("abcd");

        assertEquals(4, a.size());
        assertEquals(0, a.get('a').intValue());
        assertEquals(1, a.get('b').intValue());
        assertEquals(2, a.get('c').intValue());
        assertEquals(3, a.get('d').intValue());
    }

    @Test
    public void testBuildLOTWithDuplicates() {
        Map<Character, Integer> a = PatternMatching.buildLastTable("abcabcdddc");

        assertEquals(4, a.size());
        assertEquals(3, a.get('a').intValue());
        assertEquals(4, a.get('b').intValue());
        assertEquals(9, a.get('c').intValue());
        assertEquals(8, a.get('d').intValue());
    }

    private <T> void assertListEquals(List<T> expected, List<T> actual) {
        if (expected.size() != actual.size()) {
            fail("\n\tExpected list:" + expected + "\n\tActual list:" + actual);
        }

        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals(actual.get(i))) {
                fail("Mismatch at index " + i + ":\n\tExpected: " + expected.get(i) + "\n\tActual: " + actual.get(i));
            }
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