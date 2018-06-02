package com.dev.naveen.movieapp.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.dev.naveen.movieapp.dto.ResultsItem;
import com.dev.naveen.movieapp.listener.FavListener;
import com.dev.naveen.movieapp.listener.FetchListner;

import java.util.List;

/**
 * Created by Naveen on 6/2/2018.
 */

public class MovieRepository {


    private DaoAccess daoAccess;
    private LiveData<List<ResultsItem>> movies;

    public MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        daoAccess = db.daoAccess();
        movies = daoAccess.fetchMovies();
    }


    public DaoAccess getDaoAccess() {
        return daoAccess;
    }

    public LiveData<List<ResultsItem>> getMovies() {
        return movies;
    }

    public void removeMovie(final ResultsItem resultsItem){

        new Thread(new Runnable() {
            @Override
            public void run() {
                daoAccess.deleteMovie(resultsItem);
            }
        }).start();
    }

    public void addMovie(final ResultsItem resultsItem){
        new Thread(new Runnable() {
            @Override
            public void run() {
                daoAccess.insertMovieDetails(resultsItem);
            }
        }).start();
    }

    public void removeTrailers(final List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> resultsItem){

        new Thread(new Runnable() {
            @Override
            public void run() {
                daoAccess.deleteTrailers(resultsItem);
            }
        }).start();
    }

    public void addTrailers(final List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> resultsItem){
        new Thread(new Runnable() {
            @Override
            public void run() {
                daoAccess.insertMultipleTrailers(resultsItem);
            }
        }).start();
    }

    public void removeReviews(final List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> resultsItem){

        new Thread(new Runnable() {
            @Override
            public void run() {
                daoAccess.deleteReviews(resultsItem);
            }
        }).start();
    }

    public void addReviews(final List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> resultsItem){
        new Thread(new Runnable() {
            @Override
            public void run() {
                daoAccess.insertMultipleReviews(resultsItem);
            }
        }).start();
    }

    public void isFavourite(final Long id, final FavListener favListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
               if(favListener != null){
                   ResultsItem resultsItem = daoAccess.fetchOneMoviesbyMovieId(id);
                   favListener.onSuccess(resultsItem != null);
               }
            }
        }).start();
    }

    public void fetchReviewTrailersById(final String id, final FetchListner fetchListner){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(fetchListner != null){
                    fetchListner.onSuccess(daoAccess.fetchReviewsById(id),daoAccess.fetchTrailersById(id));
                }
            }
        }).start();
    }
}
