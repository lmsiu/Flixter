package adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity;
import com.example.flixter.R;
import com.example.flixter.movie;


import org.parceler.Parcels;

import java.util.List;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ViewHolder> {

    Context context;
    List<movie> movies;

    public movieAdapter(Context context, List<movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    //inflates layout from XML and returning the holder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //Populating data into item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //get movie passed at the position
        movie movie = movies.get(position);
        //bind movie data inot the viewholder
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle, tvOverview;
        ImageView ivPoster;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvtitle);
        tvOverview = itemView.findViewById(R.id.tvoverview);
        ivPoster = itemView.findViewById(R.id.ivposter);
        container = itemView.findViewById(R.id.container);
    }

        public void bind(movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);

            //1. register click listener on whole row
            //2. navigate to new activity on tap
            //making a click listener
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                    //make a new intent object to transfer between activities
                   Intent i = new Intent(context, DetailActivity.class);
                    //going to pass "movie" as something that is parcelable
                    i.putExtra("movie", Parcels.wrap(movie));
                    //start the activity with intent i
                    context.startActivity(i);
                }
                //leave
            });{

            }
        }
    }
}
