package com.dev.naveen.movieapp.listener;

import com.dev.naveen.movieapp.dto.trailers.Trailers;

import java.util.List;

/**
 * Created by Naveen on 6/2/2018.
 */

public interface TrailerListener {
    public void onSuccess(List<Object> trailersAndReviews);
}
