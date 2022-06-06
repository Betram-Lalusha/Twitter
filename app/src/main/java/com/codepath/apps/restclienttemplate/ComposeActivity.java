package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    Button submitButton;
    TextView tweetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        tweetContent = findViewById(R.id.newTweet);
        submitButton = findViewById(R.id.submitTweet);

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
            }
        });
    }
}