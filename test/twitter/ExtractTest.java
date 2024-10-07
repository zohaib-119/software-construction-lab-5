/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T09:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "john", "Hello @alice and @Bob!", d3);
    
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        assertEquals("expected start and end to be the same", d1, timespan.getStart());
        assertEquals("expected start and end to be the same", d1, timespan.getEnd());
    }

    @Test
    public void testGetTimespanNoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        // Depending on your implementation choice for empty input, adjust expected values accordingly
        assertEquals("expected default start", Instant.now(), timespan.getStart());
        assertEquals("expected default end", Instant.now(), timespan.getEnd());
    }

    @Test
    public void testGetTimespanMixedOrderTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet3, tweet1));
        assertEquals("expected start", d3, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersSingleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        Set<String> expectedUsers = new HashSet<>(Arrays.asList("alice", "bob"));
        assertEquals("expected mentioned users", expectedUsers, mentionedUsers);
    }


    @Test
    public void testGetMentionedUsersMultipleTweets() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet3));
        Set<String> expectedUsers = new HashSet<>(Arrays.asList("alice", "bob"));
        assertEquals("expected mentioned users", expectedUsers, mentionedUsers);
    }


    @Test
    public void testGetMentionedUsersCaseInsensitivity() {
        Tweet tweetWithCaps = new Tweet(4, "user", "Check this out @Alice and @bob!", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithCaps));
        Set<String> expectedUsers = new HashSet<>(Arrays.asList("alice", "bob"));
        assertEquals("expected mentioned users to be case insensitive", expectedUsers, mentionedUsers);
    }


    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
