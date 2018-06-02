package com.dev.naveen.movieapp.listener;

import com.dev.naveen.movieapp.dto.ResultsItem;
import com.dev.naveen.movieapp.dto.reviews.Reviews;
import com.dev.naveen.movieapp.dto.trailers.Trailers;

import java.util.List;

/**
 * Created by Naveen on 6/2/2018.
 */

public interface DAOTransListener {

    public void addFavouriteMovie(ResultsItem movie, List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> reviews, List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> trailers);

    public void removeFavouriteMovie(ResultsItem movie, List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> reviews, List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> trailers);
}
