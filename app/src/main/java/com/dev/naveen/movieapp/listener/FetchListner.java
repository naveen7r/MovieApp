package com.dev.naveen.movieapp.listener;

import com.dev.naveen.movieapp.dto.reviews.ResultsItem;

import java.util.List;

/**
 * Created by Naveen on 6/2/2018.
 */

public interface FetchListner {
    public void onSuccess(List<ResultsItem> reviews, List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> trailers);
}
