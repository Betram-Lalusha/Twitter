package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

import okhttp3.Headers;

public class TimeLineActivity extends AppCompatActivity {

    List<Tweet> tweets;
    TwitterClient twitterClient;
    TweetsAdapter tweetsAdapter;
    RecyclerView tweetsRecyclerView;
    private final String TAG ="TimelineActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        twitterClient = TwitterApp.getRestClient(this);
        //find recycler view
        tweetsRecyclerView = findViewById(R.id.rvTweets);
        //initialize tweets and adapter
        tweets = new LinkedList<Tweet>();
        tweetsAdapter = new TweetsAdapter(this, tweets);
        ///recycler view setup:
        //setup layout manager
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set adapter
        tweetsRecyclerView.setAdapter(tweetsAdapter);
        populateHomeTimeLine();
    }

    private void populateHomeTimeLine() {
        twitterClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "success fetching timeline");
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.getAllTweets(jsonArray));
                    tweetsAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.i(TAG, "failed to load tweets ", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "failure fetching timeline", throwable);
            }
        });
    }

    public void onLogOut(View view) {
        System.out.println("here");
        //forget user who logged in
        TwitterApp.getRestClient(this).clearAccessToken();

        //navigate to loginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        //ensure back button is disabled
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        System.out.println("here");
        startActivity(intent);
    }
}