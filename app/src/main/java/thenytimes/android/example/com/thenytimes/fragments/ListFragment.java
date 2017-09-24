package thenytimes.android.example.com.thenytimes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import thenytimes.android.example.com.thenytimes.Application;
import thenytimes.android.example.com.thenytimes.R;
import thenytimes.android.example.com.thenytimes.activities.MainActivity;
import thenytimes.android.example.com.thenytimes.adapters.ListAdapter;
import thenytimes.android.example.com.thenytimes.models.feed.FeedDetails;
import thenytimes.android.example.com.thenytimes.models.feed.FeedList;
import thenytimes.android.example.com.thenytimes.models.feed.FeedResponse;
import thenytimes.android.example.com.thenytimes.services.FeedService;
import thenytimes.android.example.com.thenytimes.utils.Constants;

public class ListFragment extends Fragment implements MainActivity.ListFragmentListener {

    @Inject
    FeedService mService;

    @BindView(R.id.listView)
    RecyclerView mFeedListView;

    private ListAdapter mAdapter;
    private List<FeedDetails> mFeedList = new ArrayList<>();
    private StaggeredGridLayoutManager mLayoutManager;
    private String mQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((Application) getActivity().getApplication()).getNetworkComponent().inject(this);

        mAdapter = new ListAdapter(getActivity(), mFeedList, this);
        mFeedListView.setAdapter(mAdapter);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mFeedListView.setLayoutManager(mLayoutManager);

        ((MainActivity) getActivity()).getSupportActionBar().show();
        ((MainActivity) getActivity()).setListFragmentListener(this);

        mFeedListView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                int[] currentPosition = mLayoutManager
                        .findLastCompletelyVisibleItemPositions(null);
                if (currentPosition != null && currentPosition.length > 0) {
                    int lastVisibleItem = currentPosition[0];
                    if(lastVisibleItem == (mFeedList.size() - 1)) {
                        ((MainActivity) getActivity()).mPageNumber++;
                        ((MainActivity) getActivity()).getFeedList(mQuery);
                    }
                }
            }
        });
    }

    @Override
    public void getNewsList(Call<FeedResponse> call, String query) {
        mQuery = query;
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                ((MainActivity) getActivity()).dismissSpinner();
                if (response != null && response.isSuccessful()) {
                    FeedList list = response.body().getResponse();
                    mFeedList.addAll(list.getDocs());
                    System.out.println("list size = " + mFeedList.size());
                    mAdapter.updateFeedList(mFeedList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                ((MainActivity) getActivity()).dismissSpinner();
                Log.d("Failed", "Failed");
            }
        });
    }

    @Override
    public void clearList() {
        mFeedList.clear();
    }

    @Override
    public void onItemClicked(int position) {
        FeedDetails feedDetails = mFeedList.get(position);

        DetailsFragment fragment = DetailsFragment.getInstance(feedDetails.getWebUrl());
        getFragmentManager().beginTransaction()
                .addToBackStack(Constants.FRAGMENT_LIST)
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, fragment, Constants.FRAGMENT_DETAIL)
                .commit();

    }
}
