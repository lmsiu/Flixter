package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

//for this, would normally extend app activity, however for acessing YouTube, we need it to extend YouTubeBaseActivity
public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_KEY = "AIzaSyC5EuETYKnjP8FDhiW9EJVsoR4JCvtbGVo";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView titletv, overviewtv;
    RatingBar stars;
    YouTubePlayerView youtubepv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //shows which activity we're on
        setContentView(R.layout.activity_detail);

        titletv = findViewById(R.id.datvTitle);
        overviewtv = findViewById(R.id.overviewtv);
        stars = findViewById(R.id.ratingBar);
        youtubepv = findViewById(R.id.player);

        //recieving data from the other activity
        movie mov = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        titletv.setText(mov.getTitle());
        overviewtv.setText(mov.getOverview());
        //(float) downcasts the double to a float so we can use it to get the rating
        stars.setRating((float) mov.getRating());

        //getting the URL from YouTube matching to the video
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, mov.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0)
                        return;
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youtubeKey);
                    initializeYouTube(youtubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });
    }


    private void initializeYouTube(String youtubeKey) {
        //connects API to app
        youtubepv.initialize(YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "onSuccess");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "onFailure");
            }
        });
    }
    }
