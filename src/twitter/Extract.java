/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.time.Instant;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.time.Instant;
import java.util.Optional;
import java.util.Comparator;


/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
	 public static Timespan getTimespan(List<Tweet> tweets) {
	        if (tweets.isEmpty()) {
	            return new Timespan(Instant.now(), Instant.now()); // or however you want to handle empty lists
	        }
	        
	        Instant start = tweets.get(0).getTimestamp();
	        Instant end = start;

	        for (Tweet tweet : tweets) {
	            Instant tweetTime = tweet.getTimestamp();
	            if (tweetTime.isBefore(start)) {
	                start = tweetTime;
	            }
	            if (tweetTime.isAfter(end)) {
	                end = tweetTime;
	            }
	        }
	        
	        return new Timespan(start, end);
	    }
	 
	 // first legal implementation
	 
//		public static Timespan getTimespan(List<Tweet> tweets) {
//		    if (tweets.isEmpty()) {
//		        return new Timespan(Instant.now(), Instant.now());
//		    }
//
//		    Instant start = tweets.get(0).getTimestamp();
//		    Instant end = start;
//
//		    // This implementation retains the original loop structure to find min and max timestamps.
//		    for (Tweet tweet : tweets) {
//		        Instant tweetTime = tweet.getTimestamp();
//		        if (tweetTime.isBefore(start)) {
//		            start = tweetTime;
//		        }
//		        if (tweetTime.isAfter(end)) {
//		            end = tweetTime;
//		        }
//		    }
//
//		    return new Timespan(start, end);
//		}
	 
	 // second legal implementation
	 
//	 public static Timespan getTimespan(List<Tweet> tweets) {
//		    if (tweets.isEmpty()) {
//		        return new Timespan(Instant.now(), Instant.now());
//		    }
//
//		    // This implementation uses Java Streams for a more declarative approach to find min and max timestamps.
//		    Optional<Instant> minTime = tweets.stream()
//		                                       .map(Tweet::getTimestamp)
//		                                       .min(Instant::compareTo);
//
//		    Optional<Instant> maxTime = tweets.stream()
//		                                       .map(Tweet::getTimestamp)
//		                                       .max(Instant::compareTo);
//
//		    return new Timespan(minTime.get(), maxTime.get());
//		}

	 // third legal implementation
	 
//	 public static Timespan getTimespan(List<Tweet> tweets) {
//		    if (tweets.isEmpty()) {
//		        return new Timespan(Instant.now(), Instant.now());
//		    }
//
//		    // This implementation uses Comparator with Streams to find min and max timestamps in a functional style.
//		    Instant start = tweets.stream()
//		                          .map(Tweet::getTimestamp)
//		                          .min(Comparator.naturalOrder())
//		                          .orElse(Instant.now());
//
//		    Instant end = tweets.stream()
//		                        .map(Tweet::getTimestamp)
//		                        .max(Comparator.naturalOrder())
//		                        .orElse(Instant.now());
//
//		    return new Timespan(start, end);
//		}
    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
	 public static Set<String> getMentionedUsers(List<Tweet> tweets) {
		    Set<String> mentionedUsers = new HashSet<>();
		    
		    for (Tweet tweet : tweets) {
		        String text = tweet.getText();
		        // Regular expression to find mentions
		        Matcher matcher = Pattern.compile("(?<!\\w)@(\\w{1,15})(?!\\w)").matcher(text);
		        while (matcher.find()) {
		            String user = matcher.group(1); // Get the captured username without '@'
		            mentionedUsers.add(user.toLowerCase()); // Normalize to lowercase
		        }
		    }
		    
		    return mentionedUsers;
		}



}


// buggy implementation of this class

//public class Extract {
//
//    public static Timespan getTimespan(List<Tweet> tweets) {
//        if (tweets.isEmpty()) {
//            // Incorrectly handling empty lists by returning the current time twice
//            return new Timespan(Instant.now(), Instant.now().plusSeconds(1)); // Should be same Instant
//        }
//
//        Instant start = tweets.get(0).getTimestamp();
//        Instant end = start;
//
//        // Incorrectly assumes that timestamps are always in order
//        for (Tweet tweet : tweets) {
//            Instant tweetTime = tweet.getTimestamp();
//            // Faulty condition that may skip valid updates
//            if (tweetTime.isBefore(start) && tweetTime.isAfter(end)) {
//                start = tweetTime; // Incorrectly updates start
//            }
//            // Fails to correctly update end
//            if (tweetTime.isAfter(end)) {
//                end = tweetTime; // Only updating end correctly
//            }
//        }
//
//        // Returns Timespan which may not cover all tweets
//        return new Timespan(start, end);
//    }
//
//    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//        Set<String> mentionedUsers = new HashSet<>();
//
//        for (Tweet tweet : tweets) {
//            String text = tweet.getText();
//            // Faulty regex that does not correctly match all valid mentions
//            Matcher matcher = Pattern.compile("@(\\w{1,15})").matcher(text); // Fails to use lookbehind/lookahead
//            while (matcher.find()) {
//                String user = matcher.group(1);
//                mentionedUsers.add(user.toLowerCase()); // Normalizing but misses invalid captures
//            }
//        }
//
//        return mentionedUsers; // May include incorrect usernames due to regex issue
//    }
//}