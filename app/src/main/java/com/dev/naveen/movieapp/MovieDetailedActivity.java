package com.dev.naveen.movieapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.naveen.movieapp.dto.ResultsItem;
import com.dev.naveen.movieapp.util.NetworkHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by Naveen on 4/21/2018.
 */

public class MovieDetailedActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailedActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle bn = getIntent().getExtras();

        ResultsItem resultsItem = null;
        if (bn != null) {
            if (bn.containsKey(getString(R.string.movie_details))) {
                resultsItem = bn.getParcelable(getString(R.string.movie_details));
            }
        }

        ImageView ivMovie = findViewById(R.id.ivMovieImg);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvOverview = findViewById(R.id.tvOverview);
        TextView tvUserRating = findViewById(R.id.tvUserRating);
        TextView tvReleaseDate = findViewById(R.id.tvReleaseDate);


        if (resultsItem != null) {
            String title = resultsItem.getTitle();
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(getString(R.string.title_value,title));
            }

            String overview = resultsItem.getOverview();
            if (!TextUtils.isEmpty(overview)) {
                SpannableString spOverView = new SpannableString(getString(R.string.overview_value, overview));
                spOverView.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvOverview.setText(spOverView);
            }

            double voteAverage = resultsItem.getVote_average();
            if (voteAverage != -1) {
                SpannableString spUserRating = new SpannableString(getString(R.string.userrating_value,voteAverage + ""));
                spUserRating.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvUserRating.setText(spUserRating);
            }

            String releaseDate = resultsItem.getRelease_date();
            if (!TextUtils.isEmpty(releaseDate)) {
                SpannableString spReleaseDate = new SpannableString(getString(R.string.release_date_value,releaseDate));
                spReleaseDate.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvReleaseDate.setText(spReleaseDate);
            }

            String imagePath = resultsItem.getPoster_path();
            Log.d(TAG, imagePath +" 000");
            if (!TextUtils.isEmpty(imagePath)) {

                ivMovie.getLayoutParams().height = getHeight();

                String url = NetworkHelper.constructImageUrl(getString(R.string.image_url), imagePath);
                Log.d(TAG, url);
                Picasso.get().load(url).into(ivMovie);
            }

        }

    }

    private int getHeight() {
        float sW = 187f;
        float sH = 274f;

        int width = getResources().getDisplayMetrics().widthPixels - dpToPx(20);


        return (int) ((float) width / (sW / sH));
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
