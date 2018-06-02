package com.dev.naveen.movieapp.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dev.naveen.movieapp.dto.ResultsItem;

import java.util.List;

/**
 * Created by Naveen on 6/2/2018.
 */

@Dao
public interface DaoAccess {


    @Insert
    void insertMovieDetails(ResultsItem movies);

    @Insert
    void insertMultipleMoviesDetails(List<ResultsItem> moviesList);

    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    ResultsItem fetchOneMoviesbyMovieId(Long movieId);

    @Query("SELECT * FROM movie")
    LiveData<List<ResultsItem>> fetchMovies();

    @Update
    void updateMovie(ResultsItem movies);

    @Delete
    void deleteMovie(ResultsItem movies);


    @Insert
    void insertTrailer(com.dev.naveen.movieapp.dto.trailers.ResultsItem movies);

    @Insert
    void insertMultipleTrailers(List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> moviesList);

    @Query("SELECT * FROM trailer WHERE trailer_id = :trailerID")
    List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> fetchTrailersById(String trailerID);

    @Update
    void updateTrailers(com.dev.naveen.movieapp.dto.trailers.ResultsItem movies);

    @Delete
    void deleteTrailers(List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> movies);






    @Insert
    void insertReviews(com.dev.naveen.movieapp.dto.reviews.ResultsItem movies);

    @Insert
    void insertMultipleReviews(List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> moviesList);

    @Query("SELECT * FROM reviews WHERE review_id = :reviewId")
    List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> fetchReviewsById(String reviewId);

    @Update
    void updateReviews(com.dev.naveen.movieapp.dto.reviews.ResultsItem movies);

    @Delete
    void deleteReviews(List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> movies);


}
