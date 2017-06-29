package com.yelpapp.stevenwu.app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yelpapp.stevenwu.app.models.Business;
import com.yelpapp.stevenwu.app.models.Review;
import com.yelpapp.stevenwu.app.models.ReviewResponse;
import com.yelpapp.stevenwu.app.service.YelpFactory;
import com.yelpapp.stevenwu.app.service.YelpStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a {@link RestaurantMapActivity}
 * in two-pane mode (on tablets) or a {@link RestaurantDetailActivity}
 * on handsets.
 */
public class RestaurantDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ID = "id";


    private Activity activity;
    private Business mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ID)) {
            mItem = YelpStorage.getBussinesById(getArguments().getString(ARG_ID));
            activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity
                    .findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }

        }
    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {

        if (mItem == null) {
            return;
        }
        YelpFactory
                .reviews(mItem.id)
                .enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    Review[] reviews = YelpFactory.getReviews(response);
                    if (reviews != null) {
                        recyclerView.setAdapter(new ReviewItemRecyclerViewAdapter(reviews));
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            setUpDetail(rootView);
            View recyclerView = rootView.findViewById(R.id.restaurant_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);

        }

        return rootView;
    }
    private  void setUpDetail( View rootView) {
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.restaurant_detail))
                    .setText(mItem.location.getFormatAddress());
            ImageView imageView = ((ImageView) rootView.findViewById(R.id.restaurant_img));
            Picasso.with(getContext()).load(mItem.image_url).into(imageView);
            ((TextView) rootView.findViewById(R.id.rating))
                    .setText(String.format("%d Stars", (int) mItem.rating));
            ((TextView) rootView.findViewById(R.id.phone_number))
                    .setText(mItem.phone);
            ((TextView) rootView.findViewById(R.id.categories))
                    .setText(mItem.getCategoryDescription());
        }
    }
    public class ReviewItemRecyclerViewAdapter
            extends RecyclerView.Adapter<ReviewItemRecyclerViewAdapter.ViewHolder> {
        private final Review[] mValues;

        public ReviewItemRecyclerViewAdapter(Review[] items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.restaurant_list_content, parent, false);
            return new RestaurantDetailFragment.ReviewItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues[position];
            holder.mIdView.setText(mValues[position].user.name);
            holder.mContentView.setText(mValues[position].text);
        }

        @Override
        public int getItemCount() {
            return mValues.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Review mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
