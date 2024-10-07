/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;


import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T09:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T12:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "another tweet", d3);
    private static final Tweet tweet4 = new Tweet(4, "user", "This is a test tweet.", d4);
    
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled
    }
    
    @Test
    public void testWrittenByNoMatches() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "nonexistentuser");
        
        assertTrue("expected empty list", writtenBy.isEmpty());
    }

    @Test
    public void testWrittenByAllMatches() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");
        
        assertEquals("expected list size", 2, writtenBy.size());
        assertTrue("expected list to contain tweet1", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet3", writtenBy.contains(tweet3));
    }

    @Test
    public void testInTimespanMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T11:30:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweet1", inTimespan.contains(tweet1));
        assertTrue("expected list to contain tweet2", inTimespan.contains(tweet2));
        assertTrue("expected list to contain tweet3", inTimespan.contains(tweet3));
        assertFalse("expected list not to contain tweet4", inTimespan.contains(tweet4));
    }

    @Test
    public void testInTimespanExactBoundary() {
        Instant testStart = Instant.parse("2016-02-17T10:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T10:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), new Timespan(testStart, testEnd));
        
        assertEquals("expected list size", 1, inTimespan.size());
        assertTrue("expected list to contain tweet1", inTimespan.contains(tweet1));
    }

    @Test
    public void testContainingMultipleWords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk", "rivest"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweet1", containing.contains(tweet1));
        assertTrue("expected list to contain tweet2", containing.contains(tweet2));
    }

    @Test
    public void testContainingCaseInsensitive() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("TALK"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweet1", containing.contains(tweet1));
        assertTrue("expected list to contain tweet2", containing.contains(tweet2));
    }

    @Test
    public void testContainingWithPunctuation() {
        Tweet tweetWithPunctuation = new Tweet(5, "user", "Hey! Check this out: rivest.", d1);
        List<Tweet> containing = Filter.containing(Arrays.asList(tweetWithPunctuation), Arrays.asList("rivest"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweetWithPunctuation", containing.contains(tweetWithPunctuation));
    }

    @Test
    public void testContainingEmptyWordsList() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList());
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
