package thenytimes.android.example.com.thenytimes.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

import thenytimes.android.example.com.thenytimes.R;
import thenytimes.android.example.com.thenytimes.activities.MainActivity;
import thenytimes.android.example.com.thenytimes.models.feed.FeedDetails;
import thenytimes.android.example.com.thenytimes.models.feed.FeedMultimedia;
import thenytimes.android.example.com.thenytimes.utils.Constants;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<FeedDetails> mFeedList;
    private MainActivity.ListFragmentListener mListener;

    public ListAdapter(Context context, List<FeedDetails> feedList, MainActivity.ListFragmentListener listener) {
        this.mContext = context;
        this.mFeedList = feedList;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (Constants.LIST_WITH_IMAGES == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new ListHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_simple, parent, false);
        return new SimpleListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        FeedDetails details = mFeedList.get(position);

        if (Constants.LIST_WITH_IMAGES == viewHolder.getItemViewType()) {
            ListHolder holder = (ListHolder) viewHolder;

            String imageUrl = null;
            for (int i = 0; i < details.getMultimedia().size(); i++) {
                FeedMultimedia media = details.getMultimedia().get(i);
                if ("wide".equals(media.getSubType())) {
                    imageUrl = Constants.IMAGE_URL + media.getUrl();
                    Glide.with(mContext)
                            .load(imageUrl)
                            .placeholder(R.drawable.list_placeholder)
                            .into(holder.mItemImage);
                }
            }

            holder.mItemTitle.setText(details.getHeadline().getTitle());

            if (details.getDesk() != null) {
                holder.mItemRace.setVisibility(View.VISIBLE);
                holder.mItemRace.setText(details.getDesk().toUpperCase());
            } else {
                holder.mItemRace.setVisibility(View.GONE);
            }

            holder.mItemSnippet.setText(details.getSnippet());

            if (details.getReporter() == null || details.getReporter().getReportedBy() == null) {
                holder.mItemReportedBy.setVisibility(View.GONE);
            } else {
                holder.mItemReportedBy.setVisibility(View.VISIBLE);
                holder.mItemReportedBy.setText(details.getReporter().getReportedBy());
            }

            holder.mItemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(position);
                }
            });
        } else {
            SimpleListHolder holder = (SimpleListHolder) viewHolder;

            holder.mItemTitle.setText(details.getHeadline().getTitle());

            if (details.getDesk() != null) {
                holder.mItemRace.setVisibility(View.VISIBLE);
                holder.mItemRace.setText(details.getDesk().toUpperCase());
            } else {
                holder.mItemRace.setVisibility(View.GONE);
            }

            holder.mItemSnippet.setText(details.getSnippet());

            if (details.getReporter() == null || details.getReporter().getReportedBy() == null) {
                holder.mItemReportedBy.setVisibility(View.GONE);
            } else {
                holder.mItemReportedBy.setVisibility(View.VISIBLE);
                holder.mItemReportedBy.setText(details.getReporter().getReportedBy());
            }

            holder.mItemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    @Override
    public int getItemViewType(int position) {
        FeedDetails details = mFeedList.get(position);
        if (details.getMultimedia().size() > 0) {
            return Constants.LIST_WITH_IMAGES;
        }
        return Constants.SIMPLE_LIST;
    }

    public void updateFeedList(List<FeedDetails> list) {
        this.mFeedList = list;
    }

    public class ListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        ImageView mItemImage;
        @BindView(R.id.item_title)
        TextView mItemTitle;
        @BindView(R.id.item_reportedBy)
        TextView mItemReportedBy;
        @BindView(R.id.item_race)
        TextView mItemRace;
        @BindView(R.id.item_snippet)
        TextView mItemSnippet;
        @BindView(R.id.item_card_view)
        CardView mItemCardView;

        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SimpleListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        TextView mItemTitle;
        @BindView(R.id.item_reportedBy)
        TextView mItemReportedBy;
        @BindView(R.id.item_race)
        TextView mItemRace;
        @BindView(R.id.item_snippet)
        TextView mItemSnippet;
        @BindView(R.id.item_card_view)
        CardView mItemCardView;

        public SimpleListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
