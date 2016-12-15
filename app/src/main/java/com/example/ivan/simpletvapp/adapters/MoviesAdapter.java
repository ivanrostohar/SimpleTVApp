package com.example.ivan.simpletvapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.simpletvapp.R;
import com.example.ivan.simpletvapp.models.MoviesModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ivan on 5.12.2016..
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{
    private Context context;
    private List<MoviesModel> moviesList;


    public MoviesAdapter(Context context, List<MoviesModel> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_series_row_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String poster_path = "https://image.tmdb.org/t/p/w500" + moviesList.get(position).getPosterPath();


        holder.txt_movie_name.setText(moviesList.get(position).getOriginalTitle().toString());
        holder.txt_release_date.setText(String.valueOf(moviesList.get(position).getReleaseDate()));
        holder.txt_average.setText(String.valueOf(moviesList.get(position).getVoteAverage()));
      //  Glide.with(context).load(poster_path).into(holder.img_poster);
        Picasso.with(context).load(poster_path).into(holder.img_poster, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap drawable = ((BitmapDrawable) holder.img_poster.getDrawable()).getBitmap();
                Palette.Builder builder = new Palette.Builder(drawable);
                builder.generate(new Palette.PaletteAsyncListener() {
                                     @Override
                                     public void onGenerated(Palette palette) {
                                         Palette.Swatch vibrant = palette.getLightVibrantSwatch();
                                         if (vibrant != null) {
                                             holder.constraintLayout.setBackgroundColor(vibrant.getRgb());
                                             holder.txt_movie_name.setTextColor(vibrant.getTitleTextColor());
                                             holder.txt_release_date.setTextColor(vibrant.getTitleTextColor());
                                             holder.txt_average.setTextColor(vibrant.getTitleTextColor());
                                         }
                                     }
                                 });
           }
            @Override
            public void onError() {

            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout constraintLayout;
        public TextView txt_movie_name, txt_release_date, txt_average;
        public ImageView img_poster;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_movie_name = (TextView)itemView.findViewById(R.id.txt_sm_name);
            txt_release_date = (TextView)itemView.findViewById(R.id.txt_sm_date);
            txt_average = (TextView)itemView.findViewById(R.id.txt_sm_average);
            img_poster = (ImageView)itemView.findViewById(R.id.img_sm_poster);
            constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.cl_sm);
        }
    }
}
