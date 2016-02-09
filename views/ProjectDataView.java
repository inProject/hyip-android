package com.hyip.app.ui.project.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hyip.app.R;

import org.apmem.tools.layouts.FlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lfqdt on 16.12.2015.
 */
public class ProjectDataView extends LinearLayout {

    @Bind(R.id.urlText)
    TextView urlText;
    @Bind(R.id.refPlatformText)
    TextView refPlatformText;
    @Bind(R.id.paymentsText)
    TextView paymentsText;
    @Bind(R.id.videoReviewsLayout)
    FlowLayout videoReviewsLayout;
    @Bind(R.id.addingMoneyText)
    TextView addingMoneyText;
    @Bind(R.id.paysSystemsText)
    TextView paysSystemsText;
    @Bind(R.id.hostingText)
    TextView hostingText;
    @Bind(R.id.sslText)
    TextView sslText;
    @Bind(R.id.domenText)
    TextView domenText;
    @Bind(R.id.scriptText)
    TextView scriptText;
    @Bind(R.id.forumsLayout)
    FlowLayout forumsLayout;
    @Bind(R.id.moreInfoText)
    TextView moreInfoText;
    @Bind(R.id.contributionText)
    TextView contributionText;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.intreviewButton)
    Button intreviewButton;
    @Bind(R.id.projectNewsButton)
    Button projectNewsButton;
    @Bind(R.id.selectionLayout)
    LinearLayout selectionLayout;

    @Bind(R.id.dropdownLegendView)
    DropdownCategoryView dropdownLegendView;
    @Bind(R.id.legendTextView)
    TextView legendTextView;

    public ProjectDataView(Context context) {
        super(context);
        init(context);
    }

    public ProjectDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProjectDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_project_data, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);
    }

    public TextView getUrlText() {
        return urlText;
    }

    public TextView getRefPlatformText() {
        return refPlatformText;
    }

    public TextView getPaymentsText() {
        return paymentsText;
    }

    public FlowLayout getVideoReviewsLayout() {
        return videoReviewsLayout;
    }

    public TextView getAddingMoneyText() {
        return addingMoneyText;
    }

    public TextView getPaysSystemsText() {
        return paysSystemsText;
    }

    public TextView getHostingText() {
        return hostingText;
    }

    public TextView getSslText() {
        return sslText;
    }

    public TextView getDomenText() {
        return domenText;
    }

    public TextView getScriptText() {
        return scriptText;
    }

    public FlowLayout getForumsLayout() {
        return forumsLayout;
    }

    public TextView getMoreInfoText() {
        return moreInfoText;
    }

    public TextView getContributionText() {
        return contributionText;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public Button getIntreviewButton() {
        return intreviewButton;
    }

    public Button getProjectNewsButton() {
        return projectNewsButton;
    }

    public LinearLayout getSelectionLayout() {
        return selectionLayout;
    }

    public DropdownCategoryView getDropdownLegendView() {
        return dropdownLegendView;
    }

    public TextView getLegendTextView() {
        return legendTextView;
    }
}
