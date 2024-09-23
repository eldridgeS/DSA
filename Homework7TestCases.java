import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class Homework7TestCases {
    private String tableText1;
    private String tableText2;
    private String tableText3;
    private String tableText4;
    private String tableText5;
    private String tableText6;
    private String tableText7;
    private String tableText8;

    private CharacterComparator comparator;



    @Before
    public void setUp() {
        tableText1 = "aaaab";
        tableText2 = "abcde";
        tableText3 = "abcababbabcab";
        tableText4 = "abaaab";
        tableText5 = "bbbabb";
        tableText6 = "aaaaa";
        tableText7 = "abcaba";
        tableText8 = "abcdabacdcca";

        comparator = new CharacterComparator();
    }

    //////// Build failure table tests
    @Test
    public void testBuildFailureTable1() {
        /*
            pattern: aaaab
            failure table: [0, 1, 2, 3, 0]
         */
        int[] expected = {0, 1, 2, 3, 0};
        int[] failureTable = PatternMatching.buildFailureTable(tableText1, comparator);
        assertArrayEquals(expected, failureTable);
    }

    @Test
    public void testBuildFailureTable2() {
        /*
            pattern: abcde
            failure table: [0, 0, 0, 0, 0]
         */
        int[] expected = {0, 0, 0, 0, 0};
        int[] failureTable = PatternMatching.buildFailureTable(tableText2, comparator);
        assertArrayEquals(expected, failureTable);
    }

    @Test
    public void testBuildFailureTable3() {
        /*
            pattern: abcababbabcab
            failure table: [0, 0, 0, 1, 2, 1, 2, 0, 1, 2, 3, 4, 5]
            comparisons: 12
         */
        int[] expected = {0, 0, 0, 1, 2, 1, 2, 0, 1, 2, 3, 4, 5};
        int[] failureTable = PatternMatching.buildFailureTable(tableText3, comparator);
        assertArrayEquals(expected, failureTable);
    }

    @Test
    public void testBuildFailureTable4() {
        /*
            pattern: abaaab
            failure table: [0, 0, 1, 1, 1, 2]
         */
        int[] expected = {0, 0, 1, 1, 1, 2};
        int[] failureTable = PatternMatching.buildFailureTable(tableText4, comparator);
        assertArrayEquals(expected, failureTable);
    }

    @Test
    public void testBuildFailureTable5() {
        /*
            pattern: bbbabb
            failure table: [0, 1, 2, 0, 1, 2]
         */
        int[] expected = {0, 1, 2, 0, 1, 2};
        int[] failureTable = PatternMatching.buildFailureTable(tableText5, comparator);
        assertArrayEquals(expected, failureTable);
    }

    //////////// Build last table tests
    @Test
    public void testBuildLastTable1() {
        /*
            pattern: abcde
            last table: {a=0, b=1, c=2, d=3, e=4}
        */
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 0);
        expectedLastTable.put('b', 1);
        expectedLastTable.put('c', 2);
        expectedLastTable.put('d', 3);
        expectedLastTable.put('e', 4);

        assertEquals(expectedLastTable, PatternMatching.buildLastTable(tableText2));
    }

    @Test
    public void testBuildLastTable2() {
        /*
            pattern: aaaaa
            last table: {a=4}
        */
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 4);

        assertEquals(expectedLastTable, PatternMatching.buildLastTable(tableText6));
    }

    @Test
    public void testBuildLastTable3() {
        /*
            pattern: aaaab
            last table: {a=3, b=4}
        */
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 3);
        expectedLastTable.put('b', 4);

        assertEquals(expectedLastTable, PatternMatching.buildLastTable(tableText1));
    }

    @Test
    public void testBuildLastTable4() {
        /*
            pattern: abcaba
            last table: {a=5, b=4, c=2}
        */
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 5);
        expectedLastTable.put('b', 4);
        expectedLastTable.put('c', 2);

        assertEquals(expectedLastTable, PatternMatching.buildLastTable(tableText7));
    }

    @Test
    public void testBuildLastTable5() {
        /*
            pattern: abcdabacdcca
            last table: {a=11, b=5, c=10, d=8}
        */
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 11);
        expectedLastTable.put('b', 5);
        expectedLastTable.put('c', 10);
        expectedLastTable.put('d', 8);

        assertEquals(expectedLastTable, PatternMatching.buildLastTable(tableText8));
    }

    ////////// Brute force tests
    @Test(expected = IllegalArgumentException.class)
    public void testBruteForcePatternNull() {
        String pattern = null;
        String text = "abc";
        PatternMatching.bruteForce(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBruteForcePatternEmpty() {
        String pattern = "";
        String text = "abc";
        PatternMatching.bruteForce(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBruteForceTextNull() {
        String pattern = "abc";
        String text = null;
        PatternMatching.bruteForce(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBruteForceComparatorNull() {
        String pattern = "abc";
        String text = "abc";
        PatternMatching.bruteForce(pattern, text, null);
    }

    @Test
    public void testBruteForceMatch() {
        /*
            pattern: sell
            text: She sells seashells by the seashore.
            indices: 4
         */
        String pattern = "sell";
        String text = "She sells seashells by the seashore.";
        List<Integer> expected = new ArrayList<>();
        expected.add(4);
        assertEquals(expected, PatternMatching.bruteForce(pattern, text, comparator));
        assertEquals(44, comparator.getComparisonCount());
    }

    @Test
    public void testBruteForceNoMatch() {
        /*
            pattern: sell
            text: sea lions trains cardinal boardwalk
            indices: empty list
         */
        String pattern = "sell";
        String text = "sea lions trains cardinal boardwalk";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.bruteForce(pattern, text, comparator));
        assertEquals(36, comparator.getComparisonCount());
    }

    @Test
    public void testBruteForceMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
         */
        String pattern = "ab";
        String text = "abab";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        assertEquals(expected, PatternMatching.bruteForce(pattern, text, comparator));
        assertEquals(5, comparator.getComparisonCount());
    }

    ///////////// KMP tests
    @Test(expected = IllegalArgumentException.class)
    public void testKMPPatternNull() {
        String pattern = null;
        String text = "abc";
        PatternMatching.kmp(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKMPPattern0Length() {
        String pattern = "";
        String text = "abc";
        PatternMatching.kmp(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKMPTextNull() {
        String pattern = "abc";
        String text = null;
        PatternMatching.kmp(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKMPComparatorNull() {
        String pattern = "abc";
        String text = "abc";
        PatternMatching.kmp(pattern, text, null);
    }

    @Test
    public void testKMPMatch() {
        /*
            pattern: ababa
            text: ababaaababa
            indices: 0, 6
         */
        String pattern = "ababa";
        String text = "ababaaababa";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(6);
        assertEquals(expected, PatternMatching.kmp(pattern, text, comparator));
        assertEquals(18, comparator.getComparisonCount());
    }

    @Test
    public void testKMPNoMatch() {
        /*
            pattern: ababa
            text: ababbbaba
            indices: empty list
         */
        String pattern = "ababa";
        String text = "ababbbaba";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.kmp(pattern, text, comparator));
        assertEquals(11, comparator.getComparisonCount());
    }

    @Test
    public void testKMPMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
         */
        String pattern = "ab";
        String text = "abab";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        assertEquals(expected, PatternMatching.kmp(pattern, text, comparator));
        assertEquals(5, comparator.getComparisonCount());
    }

    ///////// Boyer Moore tests
    @Test(expected = IllegalArgumentException.class)
    public void testBoyerMoorePatternNull() {
        String pattern = null;
        String text = "abc";
        PatternMatching.boyerMoore(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoyerMooreTextNull() {
        String pattern = "abc";
        String text = null;
        PatternMatching.boyerMoore(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBoyerMooreComparatorNull() {
        String pattern = "abc";
        String text = "abc";
        PatternMatching.boyerMoore(pattern, text, null);
    }

    @Test
    public void testBoyerMooreMatch() {
        /*
            pattern: aaba
            text: aabaacaadaabaaba
            indices: 0, 9, 12
         */
        String pattern = "aaba";
        String text = "aabaacaadaabaaba";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(9);
        expected.add(12);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(20, comparator.getComparisonCount());
    }

    @Test
    public void testBoyerMooreNoMatch() {
        /*
            pattern: dsa
            text: datastructuresandalgorithms
            indices: empty list
         */
        String pattern = "dsa";
        String text = "datastructuresandalgorithms";
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(11, comparator.getComparisonCount());
    }

    @Test
    public void testBoyerMooreMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
         */
        String pattern = "ab";
        String text = "abab";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(5, comparator.getComparisonCount());
    }

    @Test
    public void testBoyerMoorePatternEqualsText() {
        /*
            pattern: abab
            text: abab
            indices: 0
         */
        String pattern = "abab";
        String text = "abab";
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        assertEquals(expected, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(4, comparator.getComparisonCount());
    }
}