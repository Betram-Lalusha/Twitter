package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.LinkedList;
import java.util.List;

@Parcel
public class Tweet {

    public User user;
    public String mediaUrl;
    public String tweetBody;
    public String createdAt;

    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.tweetBody = jsonObject.getString("text");
            tweet.createdAt = jsonObject.getString("created_at");
            getMediaUrl(tweet, jsonObject);
            Log.i("TWEET.JAVA", "media? " + jsonObject.getJSONObject("entities").getJSONArray("media"));
            //tweet.media = jsonObject.getJSONObject("entities").getJSONArray("media");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    //returns the url of the media
    private static void getMediaUrl(Tweet tweet, JSONObject jsonObject) {
        try {
            JSONArray media = jsonObject.getJSONObject("entities").getJSONArray("media");
            String mediaUri = media.getJSONObject(0).getString("media_url_https");
            tweet.mediaUrl = mediaUri;
            Log.i("MEDIA", "here is array " + tweet.mediaUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            //if exception occurs, no media was found
            tweet.mediaUrl = "";
        }
    }

    public static List<Tweet> getAllTweets(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new LinkedList<Tweet>();
        for(int index = 0; index <jsonArray.length(); index++) {
            tweets.add(fromJson(jsonArray.getJSONObject(index)));
        }
        return tweets;
    }
}
