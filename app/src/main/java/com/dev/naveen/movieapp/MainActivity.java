package com.dev.naveen.movieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.naveen.movieapp.adapter.MoviesAdapter;
import com.dev.naveen.movieapp.dto.MovieResponse;
import com.dev.naveen.movieapp.dto.ResultsItem;
import com.dev.naveen.movieapp.listener.AdapterItemClickListener;
import com.dev.naveen.movieapp.listener.MoviesListener;
import com.dev.naveen.movieapp.room.MovieViewModel;
import com.dev.naveen.movieapp.util.NetworkHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesListener, AdapterItemClickListener {

    private MovieResponse myMovieResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btTryAgain = findViewById(R.id.btTryAgain);
        btTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenu(menuType);
            }
        });

    }

    private void loadData(String url){
        RecyclerView rvMovies = findViewById(R.id.rvMoviesList);
        Button btTryAgain = findViewById(R.id.btTryAgain);
        TextView tvNoDataFound = findViewById(R.id.tvNoDataFound);
        if(NetworkHelper.isNetworkAvailable(this)) {
            new GetMovieList(this,this).execute(url);
            rvMovies.setVisibility(View.VISIBLE);
            btTryAgain.setVisibility(View.GONE);
            tvNoDataFound.setVisibility(View.GONE);
        } else {
            showOrHideTryAgain();
            Toast.makeText(this, getString(R.string.no_network_available), Toast.LENGTH_LONG).show();
        }
    }


    private void showOrHideTryAgain(){
        RecyclerView rvMovies = findViewById(R.id.rvMoviesList);
        Button btTryAgain = findViewById(R.id.btTryAgain);
        TextView tvNoDataFound = findViewById(R.id.tvNoDataFound);
        if(myMovieResponse == null) {
            rvMovies.setVisibility(View.GONE);
            btTryAgain.setVisibility(View.VISIBLE);
            tvNoDataFound.setVisibility(View.VISIBLE);
        } else {
            rvMovies.setVisibility(View.VISIBLE);
            btTryAgain.setVisibility(View.GONE);
            tvNoDataFound.setVisibility(View.GONE);
        }
    }

    private void showOrHideTryAgain(List<ResultsItem> movies){
        RecyclerView rvMovies = findViewById(R.id.rvMoviesList);
        Button btTryAgain = findViewById(R.id.btTryAgain);
        TextView tvNoDataFound = findViewById(R.id.tvNoDataFound);
        if(movies != null && movies.size() > 0) {
            rvMovies.setVisibility(View.VISIBLE);
            btTryAgain.setVisibility(View.GONE);
            tvNoDataFound.setVisibility(View.GONE);
        } else {
            rvMovies.setVisibility(View.GONE);
//            btTryAgain.setVisibility(View.VISIBLE);
            tvNoDataFound.setVisibility(View.VISIBLE);

        }
    }

    private void loadFavouriteMovies(List<ResultsItem> movies){
        if(movies != null && movies.size() > 0 ) {
            showOrHideTryAgain(movies);
            int spancount;
            RecyclerView rvMovies = findViewById(R.id.rvMoviesList);
            rvMovies.setAdapter(null);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                spancount = 5;
                rvMovies.setLayoutManager(new GridLayoutManager(this, spancount, LinearLayoutManager.VERTICAL, false));
            } else {
                spancount = 3;
                rvMovies.setLayoutManager(new GridLayoutManager(this, spancount, LinearLayoutManager.VERTICAL, false));
            }
            MoviesAdapter moviesAdapter = new MoviesAdapter(this, movies, getHeight(spancount));
            rvMovies.setAdapter(moviesAdapter);
        } else {
          showOrHideTryAgain(movies);
        }
    }


    @Override
    public void onSuccessMoviesResponse(MovieResponse movieResponse) {
        if(movieResponse != null && movieResponse.getResults() != null) {
            int spancount;
            myMovieResponse = movieResponse;

            RecyclerView rvMovies = findViewById(R.id.rvMoviesList);
            rvMovies.setAdapter(null);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                spancount = 5;
                rvMovies.setLayoutManager(new GridLayoutManager(this, spancount, LinearLayoutManager.VERTICAL, false));
            } else {
                spancount = 3;
                rvMovies.setLayoutManager(new GridLayoutManager(this, spancount, LinearLayoutManager.VERTICAL, false));
            }
            MoviesAdapter moviesAdapter = new MoviesAdapter(this, movieResponse.getResults(), getHeight(spancount));
            rvMovies.setAdapter(moviesAdapter);



        }


    }

    private int getHeight(int spanCount){
        float sW= 187f;
        float sH = 274f;

        int width = getResources().getDisplayMetrics().widthPixels / spanCount;


        return (int)((float)width / (sW / sH));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private int menuType = -1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menuType = item.getItemId();
        return selectMenu(menuType);
    }

    private boolean selectMenu(int menuType){
        switch (menuType) {
            case R.id.mMostPopular:
                loadData(getString(R.string.discover_movies_popular));
                return true;
            case R.id.mMostRated:
                loadData(getString(R.string.discover_movies_most_rated));
                return true;
            case R.id.mFavourite:
                fetchFavourite();
                return true;
            default:
                return true;
        }
    }


    private MovieViewModel movieViewModel;

    private void fetchFavourite(){

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movieViewModel.getMovies().observe(this, new Observer<List<ResultsItem>>() {
            @Override
            public void onChanged(@Nullable List<ResultsItem> resultsItems) {
                if(menuType == R.id.mFavourite) {
                    loadFavouriteMovies(resultsItems);
                }
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.saveinstance), myMovieResponse);
        outState.putInt(getString(R.string.menutype), menuType);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(getString(R.string.saveinstance))){
                myMovieResponse = savedInstanceState.getParcelable(getString(R.string.saveinstance));
                onSuccessMoviesResponse(myMovieResponse);
                showOrHideTryAgain();
            }
            if(savedInstanceState.containsKey(getString(R.string.menutype))){
                menuType = savedInstanceState.getInt(getString(R.string.menutype));
            }
        }
    }

    @Override
    protected void onDestroy() {
        myMovieResponse = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(menuType == -1) {
            loadData(getString(R.string.discover_movies_popular));
            menuType = R.id.mMostPopular;
        }

        /*if(menuType == R.id.mFavourite){
            fetchFavourite();
        }*/
    }

    @Override
    public void onItemClicked(ResultsItem resultsItem) {
        if(resultsItem != null) {
            Intent intent = new Intent(this, MovieDetailedActivity.class);
            intent.putExtra(getString(R.string.movie_details), resultsItem);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.unable_to_load_content), Toast.LENGTH_LONG).show();
        }
    }
}
