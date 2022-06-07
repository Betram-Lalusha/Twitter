package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String userName;
    public String userHandle;
    public String userImageUrl;

    public User() {}
    public static User fromJson(JSONObject jsonObject) {

        User user = new User();
        try {
            user.userName = jsonObject.getString("name");
            user.userHandle = jsonObject.getString("screen_name");
            user.userImageUrl = jsonObject.getString("profile_image_url_https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
