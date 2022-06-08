package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet {

    public User user;
    public String mediaUrl;
    public String timeStamp;
    public String tweetBody;
    public String createdAt;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private final String TAG = "Tweet.java";

    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.tweetBody = jsonObject.getString("text");
            tweet.createdAt = jsonObject.getString("created_at");
            //get timestamp
            tweet.timeStamp = tweet.getRelativeTimeAgo(jsonObject.getString("created_at"));
            //retrieve media url
            getMediaUrl(tweet, jsonObject);
            Log.i("TWEET.JAVA", "media? " + jsonObject);
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

    //get time stamp
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (ParseException e) {
            Log.i(TAG, "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }
}
