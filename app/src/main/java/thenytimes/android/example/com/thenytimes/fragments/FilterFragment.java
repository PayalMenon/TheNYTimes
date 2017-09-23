package thenytimes.android.example.com.thenytimes.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import thenytimes.android.example.com.thenytimes.R;
import thenytimes.android.example.com.thenytimes.preference.NewsSettings;
import thenytimes.android.example.com.thenytimes.utils.Constants;

public class FilterFragment extends DialogFragment {

    @Inject
    NewsSettings mSettings;

    @BindView(R.id.filter_sort_type)
    Spinner mSortSpinner;
    @BindView(R.id.filter_save_button)
    FloatingActionButton mSaveButton;
    @BindView(R.id.news_desk_arts)
    CheckBox mDeskArts;
    @BindView(R.id.news_desk_fashion)
    CheckBox mDeskFashion;

    private String mSortType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.filter_sort_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortSpinner.setAdapter(adapter);
        mSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                mSortType = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSortType = null;
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSettings.setString(Constants.QUERY_KEY_SORT, mSortType);
                mSettings.setString(Constants.QUERY_KEY_NEWS_DESK, null);
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
