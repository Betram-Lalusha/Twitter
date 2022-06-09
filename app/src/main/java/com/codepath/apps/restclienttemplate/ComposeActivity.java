package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.databinding.ActivityComposeBinding;
import com.codepath.apps.restclienttemplate.databinding.ActivityLoginBinding;
import com.codepath.apps.restclienttemplate.databinding.ActivityUserProfileBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    Button submitButton;
    TextView tweetContent;
    TwitterClient twitterClient;
    private static final String TAG = "ComposeActivity";
    public ActivityComposeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComposeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tweetContent = binding.newTweet;
        submitButton = binding.submitTweet;
        twitterClient = TwitterApp.getRestClient(this);
        //add event listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = tweetContent.getText().toString();
                if(content.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "tweet cannot be empty", Toast.LENGTH_LONG).show();
                }

                if(content.length() > 140) {
                    Toast.makeText(ComposeActivity.this, "tweet too long", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(ComposeActivity.this, "tweet published!", Toast.LENGTH_SHORT).show();

                //post tweet
                twitterClient.postTweet(content, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {

                        Log.i(TAG, "success posting tweet");
                        Tweet tweet = Tweet.fromJson(json.jsonObject);

                        //PASS DATA BACK TO TIMELINE
                        Intent intent = new Intent();
                        intent.putExtra("tweet", Parcels.wrap(tweet));
                        setResult(RESULT_OK, intent);
                        //close activity and pass data back to parent
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.i(TAG, "failure posting tweet: "+  response, throwable);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}