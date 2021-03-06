package thenytimes.android.example.com.thenytimes.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import thenytimes.android.example.com.thenytimes.R;
import thenytimes.android.example.com.thenytimes.activities.MainActivity;
import thenytimes.android.example.com.thenytimes.utils.Constants;

public class DetailsFragment extends Fragment {

    @BindView(R.id.details_webView)
    WebView mDetailView;
    @BindView(R.id.details_progress_Spinner)
    ProgressBar mSpinner;

    public static DetailsFragment getInstance(String url) {

        DetailsFragment fragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putString(Constants.DETAIL_FRAGMENT_URL, url);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeWebView();
    }

    private void initializeWebView() {
        mDetailView.setVerticalScrollBarEnabled(false);
        mDetailView.setHorizontalScrollBarEnabled(false);
        mDetailView.getSettings().setJavaScriptEnabled(true);
        mDetailView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    mSpinner.setVisibility(View.GONE);

                } else {
                    mSpinner.setVisibility(View.VISIBLE);

                }
            }
        });
        mDetailView.setWebViewClient(new FeedDetailWebViewClient());
        mDetailView.loadUrl(getArguments().getString(Constants.DETAIL_FRAGMENT_URL));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        if(searchItem != null) {
            searchItem.setVisible(false);
        }

        MenuItem filterItem = menu.findItem(R.id.action_filter);
        if(filterItem != null) {
            filterItem.setVisible(false);
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent .putExtra(Intent.EXTRA_TEXT, mDetailView.getUrl());

        provider.setShareIntent(shareIntent);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private class FeedDetailWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }
}
