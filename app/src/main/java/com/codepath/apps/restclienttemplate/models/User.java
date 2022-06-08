package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String userName;
    public String userHandle;
    public String userImageUrl;
    public int followers;
    public int following;
    public String profileBackGroundImage;

    public User() {}
    public static User fromJson(JSONObject jsonObject) {

        User user = new User();
        try {
            user.userName = jsonObject.getString("name");
            user.following = jsonObject.getInt("friends_count");
            user.followers = jsonObject.getInt("followers_count");
            user.userHandle = jsonObject.getString("screen_name");
            user.userImageUrl = jsonObject.getString("profile_image_url_https");
            user.profileBackGroundImage = jsonObject.getString("profile_background_image_url_https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}
