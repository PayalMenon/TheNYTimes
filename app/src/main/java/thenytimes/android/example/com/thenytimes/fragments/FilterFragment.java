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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import thenytimes.android.example.com.thenytimes.Application;
import thenytimes.android.example.com.thenytimes.R;
import thenytimes.android.example.com.thenytimes.activities.MainActivity;
import thenytimes.android.example.com.thenytimes.preference.NewsSettings;
import thenytimes.android.example.com.thenytimes.utils.Constants;

public class FilterFragment extends DialogFragment {

    @Inject
    NewsSettings mSettings;

    @BindView(R.id.filter_sort_type)
    EditText mSortView;
    @BindView(R.id.filter_save_button)
    FloatingActionButton mSaveButton;
    @BindView(R.id.news_desk_arts)
    CheckBox mDeskArts;
    @BindView(R.id.news_desk_fashion)
    CheckBox mDeskFashion;
    @BindView(R.id.news_desk_sports)
    CheckBox mDeskSports;
    @BindView(R.id.news_desk_technology)
    CheckBox mDeskTechnology;
    @BindView(R.id.filter_date)
    EditText mBeginDateView;

    private StringBuilder mDeskTypes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((Application) getActivity().getApplication()).getNetworkComponent().inject(this);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sortType = mSortView.getText().toString();
                if (sortType != null && !sortType.isEmpty()) {
                    mSettings.setString(Constants.QUERY_KEY_SORT, sortType);
                }
                if (!mDeskTypes.toString().isEmpty()) {
                    mSettings.setString(Constants.QUERY_KEY_NEWS_DESK, mDeskTypes.toString());
                }
                String beginDate = mBeginDateView.getText().toString();
                if (beginDate != null && !beginDate.isEmpty()) {
                    mSettings.setString(Constants.QUERY_KEY_BEGIN_DATE, beginDate);
                }
                ((MainActivity) getActivity()).onFilterSaved();
            }
        });

        mDeskTypes = new StringBuilder();

        mDeskArts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mDeskTypes.append("\"" + Constants.DESK_TYPE_ARTS + "\"");
                }
            }
        });

        mDeskFashion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mDeskTypes.append("\"" + Constants.DESK_TYPE_FASHION + "\"");
            }
        });

        mDeskSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mDeskTypes.append("\"" + Constants.DESK_TYPE_SPORTS + "\"");
            }
        });

        mDeskTechnology.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mDeskTypes.append("\"" + Constants.DESK_TYPE_TECHNOLOGY + "\"");
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
