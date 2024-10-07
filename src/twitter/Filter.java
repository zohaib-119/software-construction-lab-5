/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
	    Set<Tweet> resultSet = new HashSet<>(); // Use a HashSet to avoid duplicates
	    for (Tweet tweet : tweets) {
	        if (tweet.getAuthor().equalsIgnoreCase(username)) {
	            resultSet.add(tweet);
	        }
	    }
	    return new ArrayList<>(resultSet); // Convert back to ArrayList
	}
	
	// first legal implementation
	
//	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//	    List<Tweet> result = new LinkedList<>();
//	    for (Tweet tweet : tweets) {
//	        if (tweet.getAuthor().equalsIgnoreCase(username)) {
//	            result.add(tweet);
//	        }
//	    }
//	    return result;
//	}

	// second legal implementation
	
//	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//	    return tweets.stream()
//	                 .filter(tweet -> tweet.getAuthor().equalsIgnoreCase(username))
//	                 .collect(Collectors.toList());
//	}
	
	// third legal implementation
	
//	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//	    Set<Tweet> resultSet = new HashSet<>();
//	    for (Tweet tweet : tweets) {
//	        if (tweet.getAuthor().equalsIgnoreCase(username)) {
//	            resultSet.add(tweet);
//	        }
//	    }
//	    return new ArrayList<>(resultSet);
//	}
	
	
    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
	 public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
	        List<Tweet> result = new ArrayList<>();
	        for (Tweet tweet : tweets) {
	            if (!tweet.getTimestamp().isBefore(timespan.getStart()) && 
	                !tweet.getTimestamp().isAfter(timespan.getEnd())) {
	                result.add(tweet);
	            }
	        }
	        return result;
	    }

	 
    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
	 public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
	        List<Tweet> result = new ArrayList<>();
	        for (Tweet tweet : tweets) {
	            String tweetText = tweet.getText().toLowerCase();
	            for (String word : words) {
	                if (tweetText.contains(word.toLowerCase())) {
	                    result.add(tweet);
	                    break; // No need to check other words once we found a match
	                }
	            }
	        }
	        return result;
	    }

}

// buggy implementation of filter class

//public class Filter {
//
//    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//        List<Tweet> result = new ArrayList<>();
//        // Bug: Always adds tweets by "alyssa" regardless of the username
//        for (Tweet tweet : tweets) {
//            if (tweet.getAuthor().equalsIgnoreCase("alyssa")) {
//                result.add(tweet);
//            }
//        }
//        return result;
//    }
//
//    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
//        List<Tweet> result = new ArrayList<>();
//        // Bug: Always adds the first tweet, ignoring the timespan
//        if (!tweets.isEmpty()) {
//            result.add(tweets.get(0));
//        }
//        return result;
//    }
//
//    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
//        List<Tweet> result = new ArrayList<>();
//        // Bug: Incorrectly adds all tweets, even those that don't match the words
//        for (Tweet tweet : tweets) {
//            result.add(tweet); // This should check for the words instead
//        }
//        return result;
//    }
//}

