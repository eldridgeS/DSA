import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

/**
 * This is a basic set of unit tests for PatternMatching.
 * <p>
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 * <p>
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class PatternMatchingStudentTestPlusStudent {

    private static final int TIMEOUT = 200;

    private String kmpPattern;
    private String kmpText;
    private String kmpNoMatch;
    private List<Integer> kmpAnswer;
    private List<Integer> kmpPatternEquivalencyAnswer;

    private String sellPattern;
    private String sellText;
    private String sellNoMatch;
    private List<Integer> sellAnswer;

    private String multiplePattern;
    private String multipleText;
    private List<Integer> multipleAnswer;

    private List<Integer> emptyList;

    private CharacterComparator comparator;


    @Before
    public void setUp() {
        kmpPattern = "ababa";
        kmpText = "ababaaababa";
        kmpNoMatch = "ababbaba";

        kmpAnswer = new ArrayList<>();
        kmpAnswer.add(0);
        kmpAnswer.add(6);

        kmpPatternEquivalencyAnswer = new ArrayList<>();
        kmpPatternEquivalencyAnswer.add(0);

        sellPattern = "sell";
        sellText = "She sells seashells by the seashore.";
        sellNoMatch = "sea lions trains cardinal boardwalk";

        sellAnswer = new ArrayList<>();
        sellAnswer.add(4);

        multiplePattern = "ab";
        multipleText = "abab";

        multipleAnswer = new ArrayList<>();
        multipleAnswer.add(0);
        multipleAnswer.add(2);

        emptyList = new ArrayList<>();

        comparator = new CharacterComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testBruteForceMatch() {
        /*
            pattern: sell
            text: She sells seashells by the seashore.
            indices: 4
            expected total comparisons: 44
         */
        assertEquals(sellAnswer, PatternMatching.bruteForce(sellPattern,
                sellText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 44.", 44, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBruteForceNoMatch() {
        /*
            pattern: sell
            text: sea lions trains cardinal boardwalk
            indices: -
            expected total comparisons: 36
         */
        assertEquals(emptyList, PatternMatching.bruteForce(sellPattern,
                sellNoMatch, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 36.", 36, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBruteForceMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
            expected total comparisons: 5
         */
        assertEquals(multipleAnswer,
                PatternMatching.bruteForce(multiplePattern, multipleText,
                        comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBruteForceLongerText() {
        /*
            pattern: sea lions trains cardinal boardwalk
            text: sell
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList, PatternMatching.bruteForce(sellNoMatch,
                sellPattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPMatch() {
        /*
            pattern: ababa
            text: ababaaababa
            indices: 0, 6
            expected total comparison: 18
         */
        /*
            failure table: [0, 0, 1, 2, 3]
            comparisons: 4
         */
        /*
        a | b | a | b | a | a | a | b | a | b | a
        --+---+---+---+---+---+---+---+---+---+---
        a | b | a | b | a |   |   |   |   |   |
        - | - | - | - | - |   |   |   |   |   |         comparisons: 5
          |   | a | b | a | b | a |   |   |   |
          |   |   |   |   | - |   |   |   |   |         comparisons: 1
          |   |   |   | a | b | a | b | a |   |
          |   |   |   |   | - |   |   |   |   |         comparisons: 1
          |   |   |   |   | a | b | a | b | a |
          |   |   |   |   | - | - |   |   |   |         comparisons: 2
          |   |   |   |   |   | a | b | a | b | a
          |   |   |   |   |   | - | - | - | - | -       comparisons: 5
        comparisons: 14
         */
        assertEquals(kmpAnswer, PatternMatching.kmp(kmpPattern, kmpText,
                comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 18.", 18, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPNoMatch() {
        /*
            pattern: ababa
            text: ababbaba
            indices: -
            expected total comparison: 10
         */
        /*
            failure table: [0, 0, 1, 2, 3]
            comparisons: 4
         */
        /*
        a | b | a | b | b | a | b | a
        --+---+---+---+---+---+---+---
        a | b | a | b | a |   |   |
        - | - | - | - | - |   |   |            comparisons: 5
          |   | a | b | a | b | a |
          |   |   |   | - |   |   |            comparisons: 1
        comparisons: 6
         */
        assertEquals(emptyList, PatternMatching.kmp(kmpPattern, kmpNoMatch,
                comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 10.", 10, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
            expected total comparison: 5
         */
        /*
            failure table: [0, 0]
            comparisons: 1
         */
        /*
        a | b | a | b
        --+---+---+---
        a | b |   |
        - | - |   |             comparisons: 2
          |   | a | b
          |   | - | -           comparisons: 2
        comparisons: 5
         */
        assertEquals(multipleAnswer, PatternMatching.kmp(multiplePattern,
                multipleText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPLongerText() {
        /*
            pattern: sea lions trains cardinal boardwalk
            text: sell
            indices: -
            expected total comparison: 0
         */
        assertEquals(emptyList, PatternMatching.kmp(sellNoMatch, sellPattern,
                comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)

    public void testKMPPatternEqualsText() {
        /*
            pattern: ababa
            text: ababa
            indices: -
            expected total comparison: 5 or 9 (if failure table is built)
         */
        assertEquals(kmpPatternEquivalencyAnswer,
                PatternMatching.kmp("ababa", "ababa", comparator));
        assertTrue("Comparison count is different than expected",
                comparator.getComparisonCount() == 5 || comparator
                        .getComparisonCount() == 9);
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRuleOne() {

        // Test case 1: Pattern and text are the same
        CharSequence pattern1 = "ababa";
        CharSequence text1 = "ababa";
        List<Integer> expectedIndices1 = Arrays.asList(0);
        assertEquals(expectedIndices1, PatternMatching.boyerMooreGalilRule(pattern1, text1, comparator));
        assertTrue("Comparison count for case 1 is different than expected",
                comparator.getComparisonCount() == 5 || comparator.getComparisonCount() == 9);

    }
    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRuletwo() {
        // Test case 2: Pattern occurs multiple times in the text
        CharSequence pattern2 = "aba";
        CharSequence text2 = "ababa";
        List<Integer> expectedIndices2 = Arrays.asList(0, 2);
        assertEquals(expectedIndices2, PatternMatching.boyerMooreGalilRule(pattern2, text2, comparator));
        assertTrue("Comparison count for case 2 is different than expected",
                comparator.getComparisonCount() == 7);
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRulethree() {
        CharSequence pattern3 = "abc";
        CharSequence text3 = "ababa";
        List<Integer> expectedIndices3 = new LinkedList<>();
        assertEquals(expectedIndices3, PatternMatching.boyerMooreGalilRule(pattern3, text3, comparator));
        assertTrue("Comparison count for case 3 is different than expected",
                comparator.getComparisonCount() == 4);

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRulefour () {


        CharSequence pattern4 = "aa";
        CharSequence text4 = "aaaaaa";
        List<Integer> expectedIndices4 = Arrays.asList(0, 1, 2, 3, 4);
        assertEquals(expectedIndices4, PatternMatching.boyerMooreGalilRule(pattern4, text4, comparator));
        assertTrue("Comparison count for case 4 is different than expected",
                comparator.getComparisonCount() == 7);

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRulefive () {
        CharSequence pattern5 = "a";
        CharSequence text5 = "";
        List<Integer> expectedIndices5 = new LinkedList<>();
        assertEquals(expectedIndices5, PatternMatching.boyerMooreGalilRule(pattern5, text5, comparator));
        assertTrue("Comparison count for case 5 is different than expected",
                comparator.getComparisonCount() == 0);

    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRulesix () {
        CharSequence pattern5 = "a";
        CharSequence text5 = "";
        try {
            PatternMatching.boyerMooreGalilRule("", text5, comparator);
            fail("Expected IllegalArgumentException for empty pattern");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRuleseven () {
        CharSequence pattern7 = "abcde";
        CharSequence text7 = "abc";
        List<Integer> expectedIndices7 = new LinkedList<>();
        assertEquals(expectedIndices7, PatternMatching.boyerMooreGalilRule(pattern7, text7, comparator));
        assertTrue("Comparison count for case 7 is different than expected",
                comparator.getComparisonCount() == 0);
    }



    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable() {
        /*
            pattern: ababa
            failure table: [0, 0, 1, 2, 3]
            comparisons: 4
         */
        int[] failureTable = PatternMatching.buildFailureTable(kmpPattern,
                comparator);
        int[] expected = {0, 0, 1, 2, 3};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 4.", 4, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTableTwo() {
        /*
            pattern: abacabyq1ab
            failure table: [0, 0, 1, 0, 1, 2, 0, 0, 0, 1, 2]
            comparisons: 12
         */
        int[] failureTable = PatternMatching.buildFailureTable("abacabyq1ab",
                comparator);
        int[] expected = {0, 0, 1, 0, 1, 2, 0, 0, 0, 1, 2};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 12.", 12, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTableThree() {
        /*
            pattern: abacabyq1ab
            failure table: [0, 0, 1, 1, 2, 3, 4, 5]
            comparisons: 12
         */
        int[] failureTable = PatternMatching.buildFailureTable("abaabaab",
                comparator);
        int[] expected = {0, 0, 1, 1, 2, 3, 4, 5};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 8.", 8, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTableFour() {
        /*
            pattern: a
            failure table: [0]
            comparisons: 0
         */
        int[] failureTable = PatternMatching.buildFailureTable("a",
                comparator);
        int[] expected = {0};
        assertArrayEquals(expected, failureTable);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 0.", 0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMatch() {
        /*
            pattern: sell
            text: She sells seashells by the seashore.
            indices: 4
            expected total comparisons: 20
         */
        assertEquals(sellAnswer, PatternMatching.boyerMoore(sellPattern,
                sellText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 20.", 20, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreNoMatch() {
        /*
            pattern: sell
            text: sea lions trains cardinal boardwalk
            indices: -
            expected total comparisons: 9
         */
        assertEquals(emptyList, PatternMatching.boyerMoore(sellPattern,
                sellNoMatch, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 9.", 9, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
            expected total comparisons: 5
         */
        assertEquals(multipleAnswer,
                PatternMatching.boyerMoore(multiplePattern, multipleText,
                        comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreLongerText() {
        /*
            pattern: sea lions trains cardinal boardwalk
            text: sell
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList, PatternMatching.boyerMoore(sellNoMatch,
                sellPattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable() {
        /*
            pattern: sell
            last table: {s : 0, e : 1, l : 3}
         */
        Map<Character, Integer> lastTable =
                PatternMatching.buildLastTable(sellPattern);
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('s', 0);
        expectedLastTable.put('e', 1);
        expectedLastTable.put('l', 3);
        assertEquals(expectedLastTable, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTableTwo() {
        Map<Character, Integer> lastTable =
                PatternMatching.buildLastTable(kmpPattern);
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 4);
        expectedLastTable.put('b', 3);
        assertEquals(expectedLastTable, lastTable);

        //kmpPattern = "ababa";
        //kmpText = "ababaaababa";
        //kmpNoMatch = "ababbaba";
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTableEdgeCases() {
        // Test case 2: Empty pattern
        String kmpPattern = "";
        Map<Character, Integer> lastTable = PatternMatching.buildLastTable(kmpPattern);

        Map<Character, Integer> expectedLastTable = new HashMap<>();
        assertEquals(expectedLastTable, lastTable);

        // Test case 3: Pattern with all unique characters
        kmpPattern = "abcde";
        lastTable = PatternMatching.buildLastTable(kmpPattern);

        expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 0);
        expectedLastTable.put('b', 1);
        expectedLastTable.put('c', 2);
        expectedLastTable.put('d', 3);
        expectedLastTable.put('e', 4);

        assertEquals(expectedLastTable, lastTable);

        // Test case 4: Pattern with repeated characters
        kmpPattern = "aaabb";
        lastTable = PatternMatching.buildLastTable(kmpPattern);

        expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 2);
        expectedLastTable.put('b', 4);

        assertEquals(expectedLastTable, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTableSpecialCharacters() {
        // Test case 5: Pattern with special characters
        String kmpPattern = "a!b@c#";
        Map<Character, Integer> lastTable = PatternMatching.buildLastTable(kmpPattern);

        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('a', 0);
        expectedLastTable.put('!', 1);
        expectedLastTable.put('b', 2);
        expectedLastTable.put('@', 3);
        expectedLastTable.put('c', 4);
        expectedLastTable.put('#', 5);

        assertEquals(expectedLastTable, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void TestWithSpecialChar() {
        String pattern = "a!b@c#";
        String textString = "xyza!b@c#defa!b@c#";

        Map<Character, Integer> lastTable = PatternMatching.buildLastTable(pattern);

        List<Integer> matches = PatternMatching.boyerMoore(pattern, textString, comparator);

        List<Integer> expectedMatches = List.of(3, 12);
        assertEquals(expectedMatches, matches);

        int expectedComparisons = 15;
        assertEquals(expectedComparisons, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testChallengingCase() {
        String pattern = "abc#123";
        String textString = "xyzabc#123defghiabc#123jklabc#123";
        CharacterComparator comparator = new CharacterComparator();

        Map<Character, Integer> lastTable = PatternMatching.buildLastTable(pattern);

        List<Integer> matches = PatternMatching.boyerMoore(pattern, textString, comparator);

        List<Integer> expectedMatches = Arrays.asList(3, 16, 26);
        assertEquals(expectedMatches, matches);

        int expectedComparisons = 26;
        assertEquals(expectedComparisons, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testChallengingCaseBMG() {
        String pattern = "abc#123";
        String textString = "xyzabc#123defghiabc#123jklabc#123";
        CharacterComparator comparator = new CharacterComparator();

        Map<Character, Integer> lastTable = PatternMatching.buildLastTable(pattern);

        List<Integer> matches = PatternMatching.boyerMooreGalilRule(pattern, textString, comparator);

        List<Integer> expectedMatches = Arrays.asList(3, 16, 26);
        assertEquals(expectedMatches, matches);

        int expectedComparisons = 30;
        assertEquals(expectedComparisons, comparator.getComparisonCount());
    }

}