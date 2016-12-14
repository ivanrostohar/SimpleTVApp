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
import com.example.ivan.simpletvapp.models.SeriesModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ivan on 12.12.2016..
 */

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder>{
    private Context context;
    private List<SeriesModel> seriesModel;

    public SeriesAdapter(Context context, List<SeriesModel> seriesModel) {
        this.context = context;
        this.seriesModel = seriesModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_card_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String series_poster_path = "https://image.tmdb.org/t/p/w500" + seriesModel.get(position).getPosterPath();

        holder.txt_series_title.setText(seriesModel.get(position).getName());
        holder.txt_series_date.setText(seriesModel.get(position).getAirDate());
        holder.txt_series_average.setText(String.valueOf(seriesModel.get(position).getVoteAverage()));
        Picasso.with(context).load(series_poster_path).into(holder.img_series_poster, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap drawable = ((BitmapDrawable)holder.img_series_poster.getDrawable()).getBitmap();
                Palette.Builder builder = new Palette.Builder(drawable);
                builder.generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant = palette.getLightVibrantSwatch();
                        if (vibrant != null) {
                            holder.cl_series.setBackgroundColor(vibrant.getRgb());
                            holder.txt_series_title.setTextColor(vibrant.getTitleTextColor());
                            holder.txt_series_date.setTextColor(vibrant.getTitleTextColor());
                            holder.txt_series_average.setTextColor(vibrant.getTitleTextColor());
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
        return seriesModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout cl_series;
        public ImageView img_series_poster;
        public TextView txt_series_title, txt_series_date, txt_series_average;

        public ViewHolder(View itemView) {
            super(itemView);
            cl_series = (ConstraintLayout)itemView.findViewById(R.id.cl_series);
            img_series_poster = (ImageView)itemView.findViewById(R.id.img_series_poster);
            txt_series_title = (TextView)itemView.findViewById(R.id.txt_series_name);
            txt_series_average = (TextView)itemView.findViewById(R.id.txt_series_average);
            txt_series_date = (TextView)itemView.findViewById(R.id.txt_series_date);
        }
    }
}
