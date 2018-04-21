package com.dev.naveen.movieapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Naveen on 4/21/2018.
 */

public class NetworkHelper {

    // Todo: Replace your api key here
    private static final String API_KEY = "REPLACE_API_KEY";

    private static final String DOMAIN = "api.themoviedb.org";

    private static final String BASE_URL = "https://"+DOMAIN+"/3/";
    private static final String KEY_BASE_URL  = "BASE_URL";
    private static final String KEY_API  = "KEY_API";
    private static final String KEY_DENSITY  = "KEY_DENSITY";
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String w185 = "w185";


    private static final String TAG = NetworkHelper.class.getCanonicalName();


    public static String constructImageUrl(String urlString, String params){
        if(!TextUtils.isEmpty(urlString)){
            if(urlString.contains(KEY_BASE_URL)){
                urlString = urlString.replace(KEY_BASE_URL, BASE_IMAGE_URL);
                urlString = urlString.replace(KEY_DENSITY, w185);
                urlString += params;
            }
        }
        Log.d(TAG, urlString);
        return urlString;
    }

    public static URL constructURL(String urlString){
        if(!TextUtils.isEmpty(urlString)){
            if(urlString.contains(KEY_BASE_URL)){
                urlString = urlString.replace(KEY_BASE_URL, BASE_URL);
                urlString = urlString.replace(KEY_API, API_KEY);
            }
        }
        Log.d(TAG, urlString);
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            if(urlConnection.getResponseCode() == 200) {

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } else {
                String location = urlConnection.getHeaderField("Location");
                return getResponseFromHttpUrl(new URL(location));
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }
}
