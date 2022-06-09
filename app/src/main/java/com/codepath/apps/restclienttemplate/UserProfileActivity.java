package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcel;
import org.parceler.Parcels;

public class UserProfileActivity extends AppCompatActivity {
    User user;
    public TextView userName;
    public TextView  followers;
    public TextView  following;
    public ImageView userProfilePicture;
    public ImageView userProfilePictureBckg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        userName = findViewById(R.id.profileViewUserName);
        userProfilePicture = findViewById(R.id.pfViewUserImage2);
        userProfilePictureBckg = findViewById(R.id.pfViewUserImage);

        //get intent
        Intent intent = getIntent();
        user = (User) Parcels.unwrap(intent.getParcelableExtra("user"));

        //put info in views
        userName.setText(user.userName);
        followers.setText("followers " + String.valueOf(user.followers));
        following.setText("following " + String.valueOf(user.following));
        Glide.with(this).load(user.userImageUrl).into(userProfilePicture);
        System.out.println("background " + user.profileBackGroundImage);
        Glide.with(this).load(user.userImageUrl).into(userProfilePictureBckg);
    }
}