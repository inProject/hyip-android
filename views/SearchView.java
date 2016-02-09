package com.hyip.app.ui.project.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hyip.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lfqdt on 30.12.2015.
 */
public class SearchView extends LinearLayout {

    @Bind(R.id.searchText)
    EditText searchEditText;
    @Bind(R.id.searchButton)
    ImageButton searchButton;

    public SearchView(Context context) {
        super(context);
        init(context);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_search, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);
    }

    public EditText getSearchEditText() {
        return searchEditText;
    }

    public ImageButton getSearchButton() {
        return searchButton;
    }
}
