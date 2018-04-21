package com.dev.naveen.movieapp.listener;

import com.dev.naveen.movieapp.dto.MovieResponse;

import java.util.List;

/**
 * Created by Naveen on 4/21/2018.
 */

public interface MoviesListener {
    void onSuccessMoviesResponse(MovieResponse movieResponse);
}
