package com.example.flixter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class movie {

    String posterPath, title, overview;

    public movie(JSONObject jsonObject) throws JSONException {
        //names are the keys from the JSON
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    public static List<movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<movie> movies = new ArrayList<>();
        for(int i = 0; i<movieJsonArray.length(); i++){
            movies.add(new movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;

    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
