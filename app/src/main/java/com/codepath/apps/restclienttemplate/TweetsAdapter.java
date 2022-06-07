package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.compose.ui.geometry.RoundRect;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    //inflate each row with tweet data

    //bind views based on position of element

    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView  tweetBody;
        public ImageView tweetImage;
        public ImageView userProfilePicture;

        public ViewHolder(@NonNull View viewItem) {
            super(viewItem);

            userName = viewItem.findViewById(R.id.userName);
            tweetBody = viewItem.findViewById(R.id.tweetBody);
            tweetImage = viewItem.findViewById(R.id.tweetImage);
            userProfilePicture = viewItem.findViewById(R.id.userProfilePicture);
        }

        //insert data into tweet row
        public void bind(Tweet tweet) {
            tweetBody.setText(tweet.tweetBody);
            userName.setText(tweet.user.userName);
            //load image attached to tweet
            if(!tweet.mediaUrl.isEmpty()) {
                Glide.with(context)
                        .load(tweet.mediaUrl)
                        .into(tweetImage);
            }
            Glide.with(context).load(tweet.user.userImageUrl).into(userProfilePicture);
        }
    }
}
