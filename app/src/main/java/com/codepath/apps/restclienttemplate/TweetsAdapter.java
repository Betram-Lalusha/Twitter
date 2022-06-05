package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;

    //pass in context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.tweets = tweets;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //get position
        Tweet tweet = tweets.get(position);
        //bind
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //inflate each row with tweet data

    //bind views based on position of element

    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView  tweetBody;
        public ImageView userProfilePicture;

        public ViewHolder(@NonNull View viewItem) {
            super(viewItem);

            userName = viewItem.findViewById(R.id.userName);
            tweetBody = viewItem.findViewById(R.id.tweetBody);
            userProfilePicture = viewItem.findViewById(R.id.userProfilePicture);
        }

        //insert data into tweet row
        public void bind(Tweet tweet) {
            tweetBody.setText(tweet.tweetBody);
            userName.setText(tweet.user.userName);
            Glide.with(context).load(tweet.user.userImageUrl).into(userProfilePicture);
        }
    }
}
