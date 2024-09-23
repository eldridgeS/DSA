import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * Boyer Moore and KMP Tests Modified for Galil Rule
 *
 * @author Joseph Yoo
 * @version 1.0
 */
public class GalilRuleStudentTest {

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
    public void testKMPGalilRuleMatch() {
        /*
            pattern: ababa
            text: ababaaababa
            indices: 0, 6
            expected total comparison: 18
         */
        /*
            failure table: [0, 0, 1, 2, 3]
            comparisons: 4
            last table: {
                'a' : 4,
                'b' : 3
            }
            k = 5 - 3 = 2
         */
        /*
        a | b | a | b | a | a | a | b | a | b | a
        --+---+---+---+---+---+---+---+---+---+---
        a | b | a | b | a |   |   |   |   |   |
        - | - | - | - | - |   |   |   |   |   |         comparisons: 5
          |   | a | b | a | b | a |   |   |   |
          |   |   |   |   | - | - |   |   |   |         comparisons: 2
          |   |   | a | b | a | b | a |   |   |
          |   |   |   |   |   |   | - |   |   |         comparisons: 1
          |   |   |   | a | b | a | b | a |   |
          |   |   |   |   | - | - | - | - |   |         comparisons: 4
          |   |   |   |   | a | b | a | b | a |
          |   |   |   |   |   |   |   |   | - |         comparisons: 1
          |   |   |   |   |   | a | b | a | b | a |
          |   |   |   |   |   | - | - | - | - | - |     comparisons: 5
        comparisons: 18
         */
        assertEquals(kmpAnswer, PatternMatching.boyerMooreGalilRule(kmpPattern, kmpText,
                comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 22.", 22, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPGalilRuleNoMatch() {
        /*
            pattern: ababa
            text: ababbaba
            indices: -
            expected total comparison: 10
         */
        /*
            failure table: [0, 0, 1, 2, 3]
            comparisons: 4
            last table: {
                'a' : 4,
                'b' : 3
            }
         */
        /*
        a | b | a | b | b | a | b | a
        --+---+---+---+---+---+---+---
        a | b | a | b | a |   |   |
          |   |   |   | - |   |   |            comparisons: 1
          | a | b | a | b | a |   |
          |   |   | - | - | - |   |            comparisons: 3
          |   | a | b | a | b | a |
          |   |   |   |   |   | - |            comparisons: 1
          |   |   | a | b | a | b | a |
          |   |   | - | - | - | - | - |        comparisons: 5
        comparisons: 10
         */
        assertEquals(emptyList, PatternMatching.boyerMooreGalilRule(kmpPattern, kmpNoMatch,
                comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 14.", 14, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPGalilRuleMultipleMatches() {
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
        assertEquals(multipleAnswer, PatternMatching.boyerMooreGalilRule(multiplePattern,
                multipleText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPGalilRuleLongerText() {
        /*
            pattern: sea lions trains cardinal boardwalk
            text: sell
            indices: -
            expected total comparison: 0
         */
        assertEquals(emptyList, PatternMatching.boyerMooreGalilRule(sellNoMatch, sellPattern,
                comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPGalilRulePatternEqualsText() {
        /*
            pattern: ababa
            text: ababa
            indices: -
            expected total comparison: 5 or 9 (if failure table is built)
         */
        assertEquals(kmpPatternEquivalencyAnswer,
                PatternMatching.boyerMooreGalilRule(kmpPattern, kmpPattern, comparator));
        assertTrue("Comparison count is different than expected",
                comparator.getComparisonCount() == 5 || comparator
                        .getComparisonCount() == 9);
    }


    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRuleMatch() {
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
    public void testBoyerMooreGalilRuleNoMatch() {
        /*
            pattern: sell
            text: sea lions trains cardinal boardwalk
                  sell   n                                1
                      sell   r                            1
                          sell   s                        1
                              sell  a                     2
                                 sell   n                 1
                                     sell   b             1
                                         sell   d         1
                                             sell   k     1
                                                 sell     1
            indices: -
            expected total comparisons: 3 + 9 = 12
         */
        assertEquals(emptyList, PatternMatching.boyerMooreGalilRule(sellPattern,
                sellNoMatch, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 12.", 12, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRuleMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
            expected total comparisons: 5
         */
        assertEquals(multipleAnswer,
                PatternMatching.boyerMooreGalilRule(multiplePattern, multipleText,
                        comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testboyerMooreGalilRuleLongerText() {
        /*
            pattern: sea lions trains cardinal boardwalk
            text: sell
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList, PatternMatching.boyerMooreGalilRule(sellNoMatch,
                sellPattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }
}