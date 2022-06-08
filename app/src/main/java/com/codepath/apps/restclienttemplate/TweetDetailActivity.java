package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {
    Tweet tweet;
    public TextView userName;
    public TextView  tweetBody;
    public ImageView tweetImage;
    public ImageView userProfilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        tweetImage = findViewById(R.id.detailImage);
        userName = findViewById(R.id.detailUserName);
        tweetBody = findViewById(R.id.detailTweetBody);
        userProfilePicture = findViewById(R.id.detailUserImage);

        Intent intent = getIntent();
        tweet = (Tweet) Parcels.unwrap(intent.getParcelableExtra(Tweet.class.getSimpleName()));

        tweetBody.setText(tweet.tweetBody);
        userName.setText(tweet.user.userName);
        Glide.with(this).load(tweet.mediaUrl).into(tweetImage);
        Glide.with(this).load(tweet.user.userImageUrl).into(userProfilePicture);
    }
}