package com.dev.naveen.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.naveen.movieapp.R;
import com.dev.naveen.movieapp.dto.ResultsItem;
import com.dev.naveen.movieapp.dto.reviews.Reviews;

import com.dev.naveen.movieapp.dto.trailers.Trailers;
import com.dev.naveen.movieapp.listener.DAOTransListener;
import com.dev.naveen.movieapp.util.NetworkHelper;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Naveen on 6/2/2018.
 */

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = DetailsAdapter.class.getCanonicalName();
    private List<Object> detailsList;
    private Context context;
    private DAOTransListener daoTransListener;

    private boolean isFav = false;

    private int DETAILS = 1;
    private int TRAILERS = 2;
    private int REVIEWS = 3;

    private int detailsCount = 0;
    private int trailerCount = 0;
    private int reviewCount = 0;

    public DetailsAdapter(List<Object> detailsList, Context context, DAOTransListener daoTransListener, boolean isFav) {
        this.detailsList = new WeakReference<List<Object>>(detailsList).get();
        this.context = new WeakReference<Context>(context).get();
        this.daoTransListener = new WeakReference<DAOTransListener>(daoTransListener).get();
        this.isFav = isFav;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == DETAILS) {
            return new DetailsHolder(View.inflate(parent.getContext(), R.layout.details_item, null));

        } else if (viewType == TRAILERS) {
            return new TrailerHolder(View.inflate(parent.getContext(), R.layout.trailer_item, null));
        } else if (viewType == REVIEWS) {
            return new ReviewsHolder(View.inflate(parent.getContext(), R.layout.reviews_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == DETAILS) {
            final DetailsHolder dH = (DetailsHolder) holder;

            populateDetails(dH, (ResultsItem) detailsList.get(position));

            dH.ivFavourite.setSelected(isFav);

            dH.ivFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFav = !isFav;
                    dH.ivFavourite.setSelected(isFav);
                    if(isFav){

                        daoTransListener.addFavouriteMovie((ResultsItem) detailsList.get(0),
                                ((com.dev.naveen.movieapp.dto.reviews.Reviews)detailsList.get(2)).getResults(),
                                ((com.dev.naveen.movieapp.dto.trailers.Trailers)detailsList.get(1)).getResults());
                    } else {
                        daoTransListener.removeFavouriteMovie((ResultsItem) detailsList.get(0),
                                ((com.dev.naveen.movieapp.dto.reviews.Reviews)detailsList.get(2)).getResults(),
                                ((com.dev.naveen.movieapp.dto.trailers.Trailers)detailsList.get(1)).getResults());
                    }
                }
            });
        } else if (viewType == TRAILERS) {
            TrailerHolder dH = (TrailerHolder) holder;

            int pos = detailsCount == 0 ? 0 : 1;
            if (pos < detailsList.size()) {
                Trailers trailers = (Trailers) detailsList.get(pos);
                List<com.dev.naveen.movieapp.dto.trailers.ResultsItem> resultsItems = trailers.getResults();

                int trailerPos = position - detailsCount;

                if (resultsItems != null && trailerPos < resultsItems.size()) {
                    final com.dev.naveen.movieapp.dto.trailers.ResultsItem resultsItem = resultsItems.get(trailerPos);


                    dH.tvTitle.setVisibility(trailerPos == 0 ? View.VISIBLE : View.GONE);
                    dH.tvTrailer.setText(resultsItem.getName());

                    dH.ivPlayArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String key = resultsItem.getKey();
                            String ytuUrl = "http://www.youtube.com/watch?v=" + key;

                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ytuUrl)));
                        }
                    });
                }
            }

        } else if (viewType == REVIEWS) {
            ReviewsHolder dH = (ReviewsHolder) holder;
            int pos = (detailsCount == 0 ? 0 : 1) + (trailerCount == 0 ? 0 : 1) ;
            if (pos < detailsList.size()) {
                Reviews reviews = (Reviews) detailsList.get(pos);
                List<com.dev.naveen.movieapp.dto.reviews.ResultsItem> resultsItems = reviews.getResults();

                int reviewsPos = position - detailsCount - trailerCount;
                dH.tvTitle.setVisibility(reviewsPos == 0 ? View.VISIBLE : View.GONE);
                if (resultsItems != null && reviewsPos < resultsItems.size()) {
                    com.dev.naveen.movieapp.dto.reviews.ResultsItem resultsItem = resultsItems.get(reviewsPos);

                    dH.tvAuthor.setText(context.getString(R.string.name) + " " + resultsItem.getAuthor());
                    dH.tvReview.setText(resultsItem.getContent());
                }
            }
        }

    }


    private void populateDetails(DetailsHolder holder, ResultsItem resultsItem) {
        if (resultsItem != null) {
            String title = resultsItem.getTitle();
            if (!TextUtils.isEmpty(title)) {
                holder.tvTitle.setText(context.getString(R.string.title_value, title));
            }

            String overview = resultsItem.getOverview();
            if (!TextUtils.isEmpty(overview)) {
                SpannableString spOverView = new SpannableString(context.getString(R.string.overview_value, overview));
                spOverView.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvOverview.setText(spOverView);
            }

            double voteAverage = resultsItem.getVote_average();
            if (voteAverage != -1) {
                SpannableString spUserRating = new SpannableString(context.getString(R.string.userrating_value, voteAverage + ""));
                spUserRating.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvUserRating.setText(spUserRating);
            }

            String releaseDate = resultsItem.getRelease_date();
            if (!TextUtils.isEmpty(releaseDate)) {
                SpannableString spReleaseDate = new SpannableString(context.getString(R.string.release_date_value, releaseDate));
                spReleaseDate.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvReleaseDate.setText(spReleaseDate);
            }

            String imagePath = resultsItem.getPoster_path();
            Log.d(TAG, imagePath + " 000");
            if (!TextUtils.isEmpty(imagePath)) {

                holder.ivMovieImg.getLayoutParams().height = getHeight();

                String url = NetworkHelper.constructImageUrl(context.getString(R.string.image_url), imagePath);
                Log.d(TAG, url);
                Picasso.get().load(url).into(holder.ivMovieImg);
            }

        }
    }

    private int getHeight() {
        float sW = 187f;
        float sH = 274f;

        int width = dpToPx(150);//getResources().getDisplayMetrics().widthPixels - dpToPx(20);


        return (int) ((float) width / (sW / sH));
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    @Override
    public int getItemViewType(int position) {
        if (position < detailsCount) {
            return DETAILS;
        } else if (position >= detailsCount && position < detailsCount + trailerCount) {
            return TRAILERS;
        } else if (position >= (detailsCount + trailerCount) && position < (detailsCount + trailerCount + reviewCount)) {
            return REVIEWS;
        }
        return -1;
    }


    @Override
    public int getItemCount() {

        int itemCount = 0;

        for (Object obj : detailsList) {
            if (obj != null) {
                if (obj instanceof ResultsItem) {
                    detailsCount = 1;
                    itemCount += detailsCount;
                } else if (obj instanceof Trailers) {
                    trailerCount = ((Trailers) obj).getResults().size();
                    itemCount += trailerCount;
                } else if (obj instanceof Reviews) {
                    reviewCount = ((Reviews) obj).getResults().size();
                    itemCount += reviewCount;
                }

            }
        }

        return itemCount;
    }

    class DetailsHolder extends RecyclerView.ViewHolder {

        ImageView ivMovieImg;
        ImageView ivFavourite;
        TextView tvTitle;
        TextView tvUserRating;
        TextView tvReleaseDate;
        TextView tvOverview;


        public DetailsHolder(View itemView) {
            super(itemView);
            ivMovieImg = itemView.findViewById(R.id.ivMovieImg);
            ivFavourite = itemView.findViewById(R.id.ivFavourite);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvUserRating = itemView.findViewById(R.id.tvUserRating);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            tvOverview = itemView.findViewById(R.id.tvOverview);


        }
    }

    class TrailerHolder extends RecyclerView.ViewHolder {

        ImageView ivPlayArrow;
        TextView tvTitle;
        TextView tvTrailer;

        public TrailerHolder(View itemView) {
            super(itemView);
            ivPlayArrow = itemView.findViewById(R.id.ivPlayArrow);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTrailer = itemView.findViewById(R.id.tvTrailer);
        }
    }

    class ReviewsHolder extends RecyclerView.ViewHolder {

        TextView tvAuthor;
        TextView tvReview;
        TextView tvTitle;

        public ReviewsHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvReview = itemView.findViewById(R.id.tvReview);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
