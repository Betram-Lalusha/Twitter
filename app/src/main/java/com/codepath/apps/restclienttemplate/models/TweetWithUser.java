package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class TweetWithUser {

    @Embedded
    User user;

    @Embedded(prefix= "tweet_")
    Tweet tweet;

    public static List<Tweet> getTweetList(List<TweetWithUser> tweetWithUsers) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        for(int index = 0; index < tweetWithUsers.size(); index++){
            Tweet tweet = tweetWithUsers.get(index).tweet;
            tweet.user = tweetWithUsers.get(index).user;
            tweets.add(tweet);
        }

        return tweets;
    }
}
