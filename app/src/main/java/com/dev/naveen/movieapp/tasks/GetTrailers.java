package com.dev.naveen.movieapp.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.dev.naveen.movieapp.R;
import com.dev.naveen.movieapp.dto.reviews.Reviews;
import com.dev.naveen.movieapp.dto.trailers.ResultsItem;
import com.dev.naveen.movieapp.dto.trailers.Trailers;
import com.dev.naveen.movieapp.listener.TrailerListener;
import com.dev.naveen.movieapp.util.NetworkHelper;
import com.dev.naveen.movieapp.util.ProgressBarHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naveen on 6/2/2018.
 */

public class GetTrailers extends AsyncTask<String, Void, List<Object>> {

    private static final String TAG = GetTrailers.class.getCanonicalName();
    private TrailerListener trailerListener;
    private ProgressBarHandler progressBarHandler;

    private String trailerUrl = "";
    private String reviewUrl = "";

    public GetTrailers(Activity activity, TrailerListener trailerListener) {
        this.trailerListener = new WeakReference<TrailerListener>(trailerListener).get();
        Activity activity1 = new WeakReference<Activity>(activity).get();
        this.progressBarHandler = new ProgressBarHandler(activity1);

        trailerUrl = activity.getString(R.string.discover_trailers);
        reviewUrl = activity.getString(R.string.discover_reviews);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressBarHandler != null){
            progressBarHandler.show();
        }
    }

    @Override
    protected List<Object> doInBackground(String... strings) {
        if (strings.length > 0) {
            String movieId = strings[0];


            try {
                trailerUrl = trailerUrl.replace("KEY_ID", movieId);
                reviewUrl = reviewUrl.replace("KEY_ID", movieId);

                URL url = NetworkHelper.constructURL(trailerUrl);
                String trailerStr =  NetworkHelper.getResponseFromHttpUrl(url);

                Gson gson = new Gson();
                Trailers trailers = gson.fromJson(trailerStr, Trailers.class);
                String tid = trailers.getId() + "";
                for (ResultsItem re: trailers.getResults()) {
                    re.setT_id(tid);
                }

                //Reviews
                URL reviewURL = NetworkHelper.constructURL(reviewUrl);
                String reviewStr =  NetworkHelper.getResponseFromHttpUrl(reviewURL);


                Reviews reviews = gson.fromJson(reviewStr, Reviews.class);
                String rid = reviews.getId() + "";
                for (com.dev.naveen.movieapp.dto.reviews.ResultsItem re: reviews.getResults()) {
                    re.setR_id(rid);
                }

                List<Object> list = new ArrayList<>();
                list.add(trailers);
                list.add(reviews);

                return list;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Object> s) {
        super.onPostExecute(s);

        if(progressBarHandler != null){
            progressBarHandler.hide();
        }


        if (trailerListener != null) {
            trailerListener.onSuccess(s);
        }
    }
}
