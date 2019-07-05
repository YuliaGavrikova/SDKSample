package com.taboola.sdksample.main;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taboola.sdksample.R;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private final int TYPE_SHORT_ITEM = 0;

    private List<Object> data;

    private OnAttributionClick mAttributionClickCallback;

    public FeedAdapter(List<Object> data, OnAttributionClick attributionClickCallback) {
        this.data = data;
        mAttributionClickCallback = attributionClickCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_SHORT_ITEM: {
                ConstraintLayout shortItemLayout = (ConstraintLayout) inflater
                        .inflate(R.layout.item_article_short, parent, false);
                return new ShortItemViewHolder(shortItemLayout);
            }
            default: {
                throw new IllegalStateException("Unknown view type: " + viewType);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
