package com.dev.naveen.movieapp;

import android.app.Activity;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.dev.naveen.movieapp.dto.MovieResponse;
import com.dev.naveen.movieapp.listener.MoviesListener;
import com.dev.naveen.movieapp.util.NetworkHelper;
import com.dev.naveen.movieapp.util.ProgressBarHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URL;

/**
 * Created by Naveen on 4/21/2018.
 */

public class GetMovieList extends AsyncTask<String, Void, String> {


    private static final String TAG = GetMovieList.class.getCanonicalName();
    private MoviesListener moviesListener;
    private ProgressBarHandler progressBarHandler;

    public GetMovieList(Activity activity,MoviesListener moviesListener) {
        this.moviesListener = new WeakReference<MoviesListener>(moviesListener).get();
        Activity activity1 = new WeakReference<Activity>(activity).get();
        this.progressBarHandler = new ProgressBarHandler(activity1);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressBarHandler != null){
            progressBarHandler.show();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings.length > 0) {
            String urlString = strings[0];

            URL url = NetworkHelper.constructURL(urlString);
            try {

                return NetworkHelper.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(progressBarHandler != null){
            progressBarHandler.hide();
        }

        Gson gson = new Gson();
        MovieResponse movieResponse = gson.fromJson(s, MovieResponse.class);

        if (moviesListener != null) {
            moviesListener.onSuccessMoviesResponse(movieResponse);
        }
    }
}
