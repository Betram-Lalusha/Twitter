package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityUserProfileBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcel;
import org.parceler.Parcels;

public class UserProfileActivity extends AppCompatActivity {
    User user;
    public TextView userName;
    public TextView  followers;
    public TextView  following;
    public TextView  description;
    public ImageView userProfilePicture;
    public ImageView userProfilePictureBckg;
    public ActivityUserProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        followers = binding.followers;
        following = binding.following;
        description = binding.description;
        userName = binding.profileViewUserName;
        userProfilePicture = binding.pfViewUserImage2;
        userProfilePictureBckg = binding.pfViewUserImage;

        //get intent
        Intent intent = getIntent();
        user = (User) Parcels.unwrap(intent.getParcelableExtra("user"));

        //put info in views
        description.setText("Bio: "+ user.description);
        userName.setText(user.userName + "@"+ user.userHandle);
        followers.setText("followers " + String.valueOf(user.followers));
        following.setText("following " + String.valueOf(user.following));
        Glide.with(this).load(user.userImageUrl).into(userProfilePicture);
        System.out.println("background " + user.profileBackGroundImage);
        Glide.with(this).load(user.userImageUrl).into(userProfilePictureBckg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}