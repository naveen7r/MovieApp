package com.dev.naveen.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.naveen.movieapp.MovieDetailedActivity;
import com.dev.naveen.movieapp.R;
import com.dev.naveen.movieapp.dto.ResultsItem;
import com.dev.naveen.movieapp.listener.AdapterItemClickListener;
import com.dev.naveen.movieapp.util.NetworkHelper;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Naveen on 4/21/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {
    private Context context;
    private List<ResultsItem> resultsItemList;
    private int height;

    public MoviesAdapter(Context context, List<ResultsItem> resultsItemList, int height) {
        this.resultsItemList = new WeakReference<List<ResultsItem>>(resultsItemList).get();
        this.context = new WeakReference<Context>(context).get();
        this.height = height;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.movie_item, null);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        if (holder != null && resultsItemList != null && position < resultsItemList.size() && resultsItemList.get(position) != null) {
            ResultsItem resultsItem = resultsItemList.get(position);
            holder.ivMovie.getLayoutParams().height = height;
            String imageUrl = NetworkHelper.constructImageUrl(context.getString(R.string.image_url), resultsItem.getPoster_path().toString());
            Picasso.get().load(imageUrl).into(holder.ivMovie);

            holder.ivMovie.setTag(position + "");
            holder.ivMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = Integer.parseInt(v.getTag().toString());
                    ResultsItem resultsItem1 = resultsItemList.get(pos);
                   if(context instanceof AdapterItemClickListener){
                       ((AdapterItemClickListener) context).onItemClicked(resultsItem1);
                   }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (resultsItemList != null && resultsItemList.size() > 0)
            return resultsItemList.size();
        return 0;
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        ImageView ivMovie;

        public MovieHolder(View itemView) {
            super(itemView);
            ivMovie = itemView.findViewById(R.id.ivMovie);
        }
    }
}
