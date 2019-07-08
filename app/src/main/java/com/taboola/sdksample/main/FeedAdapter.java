package com.taboola.sdksample.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.taboola.android.api.TBImageView;
import com.taboola.android.api.TBRecommendationItem;
import com.taboola.android.api.TBTextView;
import com.taboola.sdksample.R;
import com.taboola.sdksample.utils.DateTimeUtil;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    @NonNull
    protected Context context;
    private final int TYPE_SHORT_ITEM = 0;
    private final int TYPE_FAKE_ITEM = 1;


    private List<Object> items;

    private OnAttributionClick mAttributionClickCallback;

    public FeedAdapter(List<Object> items, OnAttributionClick attributionClickCallback) {
        this.items = items;
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
            case TYPE_FAKE_ITEM: {
                ConstraintLayout fakeItemLayout = (ConstraintLayout) inflater
                        .inflate(R.layout.item_fake, parent, false);
                return new FakeItemViewHolder(fakeItemLayout);
            }
            default: {
                throw new IllegalStateException("Unknown view type: " + viewType);
            }
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShortItemViewHolder) {
            ((ShortItemViewHolder) viewHolder).bind((TBRecommendationItem) items.get(position));
        } else if (viewHolder instanceof FakeItemViewHolder) {
            ((FakeItemViewHolder) viewHolder).bind((FakeItemModel) items.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof TBRecommendationItem) {
            return TYPE_SHORT_ITEM;
        } else if (items.get(position) instanceof FakeItemModel) {
            return TYPE_FAKE_ITEM;
        } else {
            throw new RuntimeException("Unknown item type");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItems(List<Object> allItems) {
        items.addAll(allItems);
        notifyDataSetChanged();
    }

    class ShortItemViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout itemTitle;
        public FrameLayout itemImage;
        public TextView itemTime;
        public FrameLayout itemBranding;


        public ShortItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.container_title);
            itemImage = itemView.findViewById(R.id.container_image);
            itemTime = itemView.findViewById(R.id.tv_time);
            itemBranding = itemView.findViewById(R.id.container_branding);
        }

        void bind(TBRecommendationItem item) {
            itemTitle.removeAllViews();
            itemImage.removeAllViews();
            itemBranding.removeAllViews();

            int thumbnailHeight = (int) context.getResources().getDimension(R.dimen.height_feed_article_short_thumbnail);
            int thumbnailWidth = (int) context.getResources().getDimension(R.dimen.width_feed_article_short_thumbnail);

            final TBImageView thumbnail = item.getThumbnailView(context, thumbnailHeight, thumbnailWidth);

            thumbnail.setId(R.id.item_image);
            thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TBTextView title = item.getTitleView(context);
            title.setId(R.id.item_title);
            title.setMaxLines(3);
            title.setEllipsize(TextUtils.TruncateAt.END);
            title.setTextColor(context.getResources().getColor(R.color.black));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                title.setTextAppearance(R.style.TitleText_Normal);
            }

            TBTextView brandingLabel = item.getBrandingView(context);
            brandingLabel.setId(R.id.item_branding);
            brandingLabel.setMaxLines(2);
            brandingLabel.setLayoutParams(new FrameLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            brandingLabel.setEllipsize(TextUtils.TruncateAt.END);
            brandingLabel.setTextColor(context.getResources().getColor(R.color.grey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                brandingLabel.setTextAppearance(R.style.SecondaryText);
            }
            brandingLabel.setGravity(Gravity.CENTER);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemImage.getClipToOutline();
            }


            if (thumbnail.getParent() != null) {
                ((ViewGroup) thumbnail.getParent()).removeView(thumbnail);
            }
            itemImage.addView(thumbnail);

            if (title.getParent() != null) {
                ((ViewGroup) title.getParent()).removeView(title);
            }
            itemTitle.addView(title);

            String timestamp = DateTimeUtil.getTimeBetween(item.getExtraDataMap().get("created"));
            itemTime.setText(timestamp);

            if (brandingLabel.getParent() != null) {
                ((ViewGroup) brandingLabel.getParent()).removeView(brandingLabel);
            }
            itemBranding.addView(brandingLabel);
        }
    }

    class FakeItemViewHolder extends RecyclerView.ViewHolder {

        public TextView itemDescription;
        public ImageView itemImage;


        public FakeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemDescription = itemView.findViewById(R.id.fake_description);
            itemImage = itemView.findViewById(R.id.container_image);
        }

        void bind(FakeItemModel item) {
            itemDescription.setText(item.title);
            itemImage.setBackgroundColor(Color.BLUE);
        }
    }


}
