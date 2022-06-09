package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityComposeBinding;
import com.codepath.apps.restclienttemplate.databinding.ActivityTweetDetailBinding;
import com.codepath.apps.restclienttemplate.databinding.ActivityUserProfileBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {
    Tweet tweet;
    public TextView userName;
    public TextView  tweetBody;
    public ImageView tweetImage;
    public ImageView userProfilePicture;
    public ActivityTweetDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTweetDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tweetImage = binding.detailImage;
        userName = binding.detailUserName;
        tweetBody = binding.detailTweetBody;
        userProfilePicture = binding.detailUserImage;

        Intent intent = getIntent();
        tweet = (Tweet) Parcels.unwrap(intent.getParcelableExtra(Tweet.class.getSimpleName()));

        tweetBody.setText(tweet.tweetBody);
        userName.setText(tweet.user.userName + "@"+ tweet.user.userHandle + ". " + tweet.timeStamp);
        Glide.with(this).load(tweet.mediaUrl).into(tweetImage);
        Glide.with(this).load(tweet.user.userImageUrl).into(userProfilePicture);
    }

    public void goTodUserProfile(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("user", Parcels.wrap(tweet.user));
        //start activity
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}