package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {

    @ColumnInfo
    @PrimaryKey
    public long id;
    @ColumnInfo
    public int followers;
    @ColumnInfo
    public int following;
    @ColumnInfo
    public String userName;
    @ColumnInfo
    public String userHandle;
    @ColumnInfo
    public String userImageUrl;
    @ColumnInfo
    public String profileBackGroundImage;


    public User() {}
    public static User fromJson(JSONObject jsonObject) {

        User user = new User();
        try {
            user.id = jsonObject.getLong("id");
            user.userName = jsonObject.getString("name");
            user.following = jsonObject.getInt("friends_count");
            user.followers = jsonObject.getInt("followers_count");
            user.userHandle = jsonObject.getString("screen_name");
            user.userImageUrl = jsonObject.getString("profile_image_url_https");
            user.profileBackGroundImage = jsonObject.getString("profile_background_image_url_https");
            Log.i("USER", "USER " + user.profileBackGroundImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> fromTweetArray(List<Tweet> tweetsFromJson) {
        List<User> users = new ArrayList<>();
        for(Tweet tweet: tweetsFromJson) {
            users.add(tweet.user);
        }
        return users;
    }
}
