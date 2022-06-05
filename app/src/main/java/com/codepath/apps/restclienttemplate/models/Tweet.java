package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Tweet {

    public User user;
    public String tweetBody;
    public String createdAt;

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.tweetBody = jsonObject.getString("text");
            tweet.createdAt = jsonObject.getString("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static List<Tweet> getAllTweets(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new LinkedList<Tweet>();
        for(int index = 0; index <jsonArray.length(); index++) {
            tweets.add(fromJson(jsonArray.getJSONObject(index)));
        }
        return tweets;
    }
}
