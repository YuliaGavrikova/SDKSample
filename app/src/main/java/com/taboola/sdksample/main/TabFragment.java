package com.taboola.sdksample.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.android.api.TBPlacement;
import com.taboola.sdksample.R;
import com.taboola.sdksample.SampleApplication;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class TabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = TabFragment.class.getSimpleName();
    private static final String EXTRA_KEY_PLACEMENT_NAME = "PLACEMENT_NAME";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private FeedAdapter feedAdapter;
    private LinearLayoutManager layoutManager;

    private TBPlacement lastUsedPlacement;

    public static TabFragment newInstance(@NonNull String placementName) {
        Bundle data = new Bundle();
        data.putSerializable(EXTRA_KEY_PLACEMENT_NAME, placementName);

        TabFragment fragment = new TabFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.rv_main_category_tab);
        fab = view.findViewById(R.id.fab);

        recyclerView.setAdapter(feedAdapter);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setHasFixedSize(true);
        addOnScrollListener(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(this);
        fetchInitialContent();

        return view;
    }

    private void fetchInitialContent() {
        String publisher = ((SampleApplication) getActivity().getApplicationContext())
                .getAppConfig().getPublisher();
        int thumbnailHeight = (int) getContext().getResources()
                .getDimension(R.dimen.height_feed_article_short_thumbnail);
        int thumbnailWidth = (int) getContext().getResources()
                .getDimension(R.dimen.width_feed_article_short_thumbnail);
        ContentRepository.getFirstContentBatch(publisher, getPlacementName(), 10,
                thumbnailHeight, thumbnailWidth, new ContentRepository.ContentFetchCallback() {
                    @Override
                    public void onRecommendationsFetched(TBPlacement placement) {
                        lastUsedPlacement = placement;
                        // feedAdapter.addItems(placement.getItems());
                    }

                    @Override
                    public void onRecommendationsFailed(Throwable t) {

                    }
                });
        ContentRepository.getPlaceholderContent(3);
    }

    @Override
    public void onRefresh() {
        // todo
    }

    public void scrollToTop() {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Nullable
    private String getPlacementName() {
        Bundle data = getArguments();
        if (data != null) {
            return data.getString(EXTRA_KEY_PLACEMENT_NAME);
        } else {
            Log.e(TAG, "getPlacementName: placement name is not set");
            return null;
        }
    }

    private void addOnScrollListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                String publisher = ((SampleApplication) getActivity().getApplicationContext())
                        .getAppConfig().getPublisher();
                ContentRepository.getNextBatchForPlacement(publisher, lastUsedPlacement,
                        new ContentRepository.ContentFetchCallback() {
                            @Override
                            public void onRecommendationsFetched(TBPlacement placement) {
                                lastUsedPlacement = placement;
                            }

                            @Override
                            public void onRecommendationsFailed(Throwable t) {

                            }
                        });
                ContentRepository.getPlaceholderContent(3);

            }
        });
    }

}
