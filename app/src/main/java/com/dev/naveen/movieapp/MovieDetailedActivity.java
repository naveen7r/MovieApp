package com.dev.naveen.movieapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dev.naveen.movieapp.adapter.DetailsAdapter;
import com.dev.naveen.movieapp.dto.ResultsItem;
import com.dev.naveen.movieapp.dto.reviews.Reviews;
import com.dev.naveen.movieapp.dto.trailers.Trailers;
import com.dev.naveen.movieapp.listener.DAOTransListener;
import com.dev.naveen.movieapp.listener.FavListener;
import com.dev.naveen.movieapp.listener.FetchListner;
import com.dev.naveen.movieapp.listener.TrailerListener;
import com.dev.naveen.movieapp.room.MovieViewModel;
import com.dev.naveen.movieapp.tasks.GetTrailers;
import com.dev.naveen.movieapp.util.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naveen on 4/21/2018.
 */

public class MovieDetailedActivity extends AppCompatActivity implements DAOTransListener {

    private static final String TAG = MovieDetailedActivity.class.getCanonicalName();

    private boolean isFav = false;

    private MovieViewModel movieViewModel;
    private Trailers trailers;
    private Reviews reviews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle bn = getIntent().getExtras();

        ResultsItem resultsItem = null;
        if (bn != null) {
            if (bn.containsKey(getString(R.string.movie_details))) {
                resultsItem = bn.getParcelable(getString(R.string.movie_details));
            }
        }

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        final ResultsItem finalResultsItem = resultsItem;

        movieViewModel.isFavourite(resultsItem.getId(), new FavListener() {
            @Override
            public void onSuccess(boolean isFavourite) {
                isFav = isFavourite;
                MovieDetailedActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        init(finalResultsItem);
                    }
                });
            }
        });

    }

    private void populate(List<Object> res) {
        for (Object obj : res) {
            if (obj instanceof Trailers) {
                this.trailers = ((Trailers) obj);
            }

            if (obj instanceof Reviews) {
                this.reviews = ((Reviews) obj);
            }
        }
    }

    private void init(final ResultsItem finalResultsItem) {
        if (NetworkHelper.isNetworkAvailable(this) && !isFav) {
            new GetTrailers(this, new TrailerListener() {

                @Override
                public void onSuccess(List<Object> trailersAndReviews) {
                    if (trailersAndReviews != null) {
                        trailersAndReviews.add(0, finalResultsItem);
                        init(trailersAndReviews);
                    } else {
                        List<Object> list = new ArrayList<>();
                        list.add(finalResultsItem);
                        init(list);
                    }
                }
            }).execute(finalResultsItem.getId() + "");
        } else if (isFav) {

            movieViewModel.fetchReviewTrailersById(finalResultsItem.getId() + "", new FetchListner() {
                @Override
                public void onSuccess(List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> reviews, List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> trailers) {
                    final List<Object> list = new ArrayList<>();
                    list.add(finalResultsItem);
                    Trailers trailers1 = new Trailers();
                    trailers1.setResults(trailers);
                    list.add(trailers1);
                    Reviews reviews1 = new Reviews();
                    reviews1.setResults(reviews);
                    list.add(reviews1);

                    MovieDetailedActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init(list);
                        }
                    });
                }
            });
        } else {
            List<Object> list = new ArrayList<>();
            list.add(finalResultsItem);
            if (trailers != null)
                list.add(trailers);
            if (reviews != null)
                list.add(reviews);
            init(list);
        }
    }

    private void init(List<Object> trailersAndReviews) {
        if (trailersAndReviews != null) {
            populate(trailersAndReviews);
            RecyclerView rvDetails = findViewById(R.id.rvDetails);
            DetailsAdapter detailsAdapter = new DetailsAdapter(trailersAndReviews, MovieDetailedActivity.this, MovieDetailedActivity.this, isFav);
            rvDetails.setLayoutManager(new LinearLayoutManager(MovieDetailedActivity.this, LinearLayoutManager.VERTICAL, false));
            rvDetails.addItemDecoration(new DividerItemDecoration(MovieDetailedActivity.this, DividerItemDecoration.VERTICAL));
            rvDetails.setAdapter(detailsAdapter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.trailer), trailers);
        outState.putParcelable(getString(R.string.reviews), reviews);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        trailers = savedInstanceState.getParcelable(getString(R.string.trailer));
        reviews = savedInstanceState.getParcelable(getString(R.string.reviews));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void addFavouriteMovie(final ResultsItem movie, final List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> reviews, final List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> trailers) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieViewModel.addMovie(movie);
                movieViewModel.addReviews(reviews);
                movieViewModel.addTrailers(trailers);
            }
        }).start();

    }

    @Override
    public void removeFavouriteMovie(final ResultsItem movie, final List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> reviews, final List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> trailers) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieViewModel.removeMovie(movie);
                movieViewModel.removeReviews(reviews);
                movieViewModel.removeTrailers(trailers);
            }
        }).start();

    }
}
