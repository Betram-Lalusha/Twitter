package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.LinkedList;
import java.util.List;

import okhttp3.Headers;

public class TimeLineActivity extends AppCompatActivity {

    List<Tweet> tweets;
    TwitterClient twitterClient;
    TweetsAdapter tweetsAdapter;
    // Instance of the progress action-view
    MenuItem miActionProgressItem;
    RecyclerView tweetsRecyclerView;

    private final int REQUEST_CODE = 20;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final String TAG ="TimelineActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        //find swipe container view
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        //listener for swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(TimeLineActivity.this, "refreshing", Toast.LENGTH_SHORT).show();

                //code executed during refresh
                //1. Show Progress within ActionBar
                showProgressBar();
                //2. fetch new data
                fetchTimelineAsync(0);
                //remove progress bar
                hideProgressBar();
            }
        });

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

    //controls the action in progress display
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);

        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    //get new data
    private void fetchTimelineAsync(int page) {
        showProgressBar();
        twitterClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                //remove old data
                tweetsAdapter.clear();

                //add new data
                try {
                    tweetsAdapter.addAll(Tweet.getAllTweets(json.jsonArray));
                } catch (JSONException e) {
                    Log.d(TAG, "failed to get all tweets " + e.toString());
                    e.printStackTrace();
                }

                //end refreshing
                swipeRefreshLayout.setRefreshing(false);
                hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "Fetching failed on refresh " + response);
                hideProgressBar();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.compose) {
            Toast.makeText(this, "compose!",Toast.LENGTH_SHORT ).show();
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //GET DATA FROM INTENT
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            //UPDATE RV WITH TWEEK
            tweets.add(0, tweet);
            tweetsAdapter.notifyItemChanged(0);
            tweetsRecyclerView.smoothScrollToPosition(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                Log.i(TAG, "failure fetching timeline " + response, throwable);
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


    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }

}