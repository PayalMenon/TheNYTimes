package thenytimes.android.example.com.thenytimes.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import thenytimes.android.example.com.thenytimes.Application;
import thenytimes.android.example.com.thenytimes.R;
import thenytimes.android.example.com.thenytimes.fragments.FilterFragment;
import thenytimes.android.example.com.thenytimes.fragments.ListFragment;
import thenytimes.android.example.com.thenytimes.models.feed.FeedResponse;
import thenytimes.android.example.com.thenytimes.preference.NewsSettings;
import thenytimes.android.example.com.thenytimes.services.FeedService;
import thenytimes.android.example.com.thenytimes.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @Inject
    FeedService mService;
    @Inject
    NewsSettings mSettings;

    @BindView(R.id.toolbar)
    Toolbar mActionBar;
    @BindView(R.id.list_progress_Spinner)
    ProgressBar mSpinner;

    private ListFragmentListener mListFragmentListener;
    private boolean initalLaunch;
    public int mPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((Application) getApplication()).getNetworkComponent().inject(this);

        setSupportActionBar(mActionBar);

        addListFragment();

        if (savedInstanceState == null) {
            initalLaunch = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (initalLaunch) {
            mPageNumber = 0;
            getFeedList(Constants.DEFAULT_QUERY_BREAKING_NEWS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                MainActivity.this.mListFragmentListener.clearList();
                mPageNumber = 0;
                getFeedList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_filter) {

            FilterFragment fragment = new FilterFragment();
            fragment.show(getSupportFragmentManager(), Constants.FRAGMENT_FILTER);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {

            FragmentManager manager = getSupportFragmentManager();

            ListFragment listFragment = (ListFragment) manager.findFragmentByTag(Constants.FRAGMENT_LIST);
            manager.beginTransaction().remove(listFragment).commit();

            mSettings.clearAll();
        }
    }

    public void setListFragmentListener(ListFragmentListener listener) {

        this.mListFragmentListener = listener;
    }

    public void getFeedList(String query) {

        mSpinner.setVisibility(View.VISIBLE);

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(Constants.QUERY, query);
        queryMap.put(Constants.QUERY_KEY_PAGE_NUMBER, mPageNumber);

        String sortType = mSettings.getString(Constants.QUERY_KEY_SORT, null);
        if (sortType != null) {
            queryMap.put(Constants.QUERY_KEY_SORT, sortType);
        }
        String beginDate = mSettings.getString(Constants.QUERY_KEY_BEGIN_DATE, null);
        if (beginDate != null) {
            queryMap.put(Constants.QUERY_KEY_BEGIN_DATE, beginDate);
        }
        String newsDesk = mSettings.getString(Constants.QUERY_KEY_NEWS_DESK, null);
        if (newsDesk != null) {
            StringBuilder deskQuery = new StringBuilder();
            deskQuery.append(Constants.QUERY_KEY_NEWS_DESK + ":(");
            deskQuery.append(newsDesk + ")");
            queryMap.put("fq", deskQuery.toString());
        }

        Call<FeedResponse> call = mService.getNewsFeed(queryMap);
        this.mListFragmentListener.getNewsList(call, query);
    }

    public void onFilterSaved() {
        FragmentManager manager = getSupportFragmentManager();

        FilterFragment fragment = (FilterFragment) manager.findFragmentByTag(Constants.FRAGMENT_FILTER);
        manager.beginTransaction().remove(fragment).commit();
    }

    public void dismissSpinner() {
        mSpinner.setVisibility(View.GONE);
    }

    private void addListFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ListFragment fragment = (ListFragment) manager.findFragmentByTag(Constants.FRAGMENT_LIST);

        if (fragment == null) {

            fragment = new ListFragment();
            transaction.add(R.id.fragment_container, fragment, Constants.FRAGMENT_LIST);
            transaction.commit();
        }
    }

    public interface ListFragmentListener {

        void getNewsList(Call<FeedResponse> call, String queryString);

        void clearList();

        void onItemClicked(int position);
    }
}
