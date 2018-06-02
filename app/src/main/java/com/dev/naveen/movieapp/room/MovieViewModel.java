package com.dev.naveen.movieapp.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.graphics.Movie;
import android.support.annotation.NonNull;

import com.dev.naveen.movieapp.dto.ResultsItem;
import com.dev.naveen.movieapp.listener.FavListener;
import com.dev.naveen.movieapp.listener.FetchListner;

import java.util.List;

/**
 * Created by Naveen on 6/2/2018.
 */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<List<ResultsItem>> movies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        movies = movieRepository.getMovies();
    }

    public LiveData<List<ResultsItem>> getMovies() {
        return movies;
    }

    public void removeMovie(ResultsItem resultsItem) {
        movieRepository.removeMovie(resultsItem);
    }

    public void addMovie(final ResultsItem resultsItem){
        movieRepository.addMovie(resultsItem);
    }

    public void removeTrailers(final List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> resultsItem){
        movieRepository.removeTrailers(resultsItem);

    }

    public void addTrailers(final List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> resultsItem){
        movieRepository.addTrailers(resultsItem);
    }

    public void removeReviews(final List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> resultsItem){
        movieRepository.removeReviews(resultsItem);

    }

    public void addReviews(final List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> resultsItem){
        movieRepository.addReviews(resultsItem);
    }

    public void isFavourite(Long id, FavListener favListener){
        movieRepository.isFavourite(id, favListener);
    }

    public void fetchReviewTrailersById(final String id, final FetchListner fetchListner){
        movieRepository.fetchReviewTrailersById(id, fetchListner);
    }
}
