package com.hyip.app.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyip.app.R;
import com.hyip.app.api.handler.imp.OnSuccessListener;
import com.hyip.app.api.models.article.ArticleModel;
import com.hyip.app.api.models.article.ArticleResultModel;
import com.hyip.app.api.models.project.ProjectResultModel;
import com.hyip.app.components.Constants;
import com.hyip.app.components.KeyboardUtils;
import com.hyip.app.components.ProgressBarHandler;
import com.hyip.app.components.dl.App;
import com.hyip.app.components.prefs.SharedPrefs;
import com.hyip.app.components.textutils.CutUrlHelper;
import com.hyip.app.ui.article.ArticleDetailsFragment;
import com.hyip.app.ui.project.adapter.ProjectCommentsAdapter;
import com.hyip.app.ui.project.views.BottomMenuView;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.util.Date;

/**
 * Created by lfqdt on 15.12.2015.
 */
public class ProjectDetailsFragment extends BaseProjectFragment {

    private boolean showingFirst = true;
    private FlowLayout resourcesLayout;
    private FlowLayout videoReviewsLayout;
    private int type;

    public static final ProjectDetailsFragment newInstance(ProjectResultModel projectResultModel, int type){
        ProjectDetailsFragment f = new ProjectDetailsFragment();
        f.projectResultModel  = projectResultModel;
        f.type = type;
        return f;
    }

    public static final ProjectDetailsFragment newInstance(ProjectResultModel projectResultModel){
        ProjectDetailsFragment f = new ProjectDetailsFragment();
        f.projectResultModel  = projectResultModel;
        return f;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getProjectLikes();
        resourcesLayout = projectDataView.getForumsLayout();
        videoReviewsLayout = projectDataView.getVideoReviewsLayout();
        projectCommentsAdapter = new ProjectCommentsAdapter(context);
        projectDataView.getUrlText().setText(CutUrlHelper.getHost(projectResultModel.getProjectUrl()));
        projectDataView.getUrlText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURL(projectResultModel.getProjectUrl().trim());
            }
        });
        projectDataView.getRefPlatformText().setText(projectResultModel.getReffer());
        projectDataView.getPaymentsText().setText(projectResultModel.getPayments());
        projectDataView.getAddingMoneyText().setText(projectResultModel.getAccruals());
        projectDataView.getPaysSystemsText().setText(projectResultModel.getSystems());
        projectDataView.getHostingText().setText(projectResultModel.getHosting());
        projectDataView.getSslText().setText(projectResultModel.getSsl());
        projectDataView.getDomenText().setText(projectResultModel.getDomain());
        projectDataView.getScriptText().setText(projectResultModel.getScript());
        projectDataView.getMoreInfoText().setText(projectResultModel.getAdditionalInformation());
        if(Integer.valueOf(projectResultModel.getPays()) == 0) {
            pays.setText(getString(R.string.project_details_pays2));
            pays.setTextColor(getResources().getColor(R.color.refback_canceled_color));
        } else {
            pays.setText(getString(R.string.project_details_pays));
            pays.setTextColor(getResources().getColor(R.color.refback_payed_color));
        }
        projectDataView.getDropdownLegendView().getDropdownText().setText(getString(R.string.project_details_show_legend));
        Picasso.with(context).load(projectResultModel.getImage()).into(image);
        if(Integer.valueOf(projectResultModel.getOurChoice()) == 1) {
            projectDataView.getSelectionLayout().setVisibility(View.VISIBLE);
            projectDataView.getRatingBar().setRating(Integer.valueOf(projectResultModel.getRating()));
            projectDataView.getContributionText().setText(projectResultModel.getContribution());
            projectDataView.getIntreviewButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(projectResultModel.getInterview()) != 0) {
                        getArticleById(projectResultModel.getInterview()).setOnSuccessRequestListener(new OnSuccessListener() {
                            @Override
                            public void success(Object result) {
                                ArticleResultModel model = ((ArticleModel) result).getResult().get(0);
                                getFragmentManager().beginTransaction().replace(R.id.container, new ArticleDetailsFragment().newInstance(model))
                                        .addToBackStack(Constants.FRAGMENT_TAG)
                                        .commit();
                            }
                        });
                    } else {
                        Toast.makeText(context, getString(R.string.project_link), Toast.LENGTH_LONG).show();
                    }
                }
            });
            projectDataView.getProjectNewsButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(projectResultModel.getNews()) != 0) {
                        getArticleById(projectResultModel.getNews()).setOnSuccessRequestListener(new OnSuccessListener() {
                            @Override
                            public void success(Object result) {
                                ArticleResultModel model = ((ArticleModel) result).getResult().get(0);
                                getFragmentManager().beginTransaction().replace(R.id.container, new ArticleDetailsFragment().newInstance(model))
                                        .addToBackStack(Constants.FRAGMENT_TAG)
                                        .commit();
                            }
                        });
                    } else {
                        Toast.makeText(context, getString(R.string.project_link), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else
            projectDataView.getSelectionLayout().setVisibility(View.GONE);
        name.setText(CutUrlHelper.getHost(projectResultModel.getTitle()));
        if(Integer.valueOf(projectResultModel.getScam()) == 1) {
            workedTime.setText("scam");
            likeFinger.setImageResource(R.mipmap.hand_icon_rotated);
            workedTime.setTextColor(getResources().getColor(R.color.refback_canceled_color));
        } else {
            workedTime.setText(getString(R.string.project_details_working)
                    + " " + daysBetween(convertStringToDate(projectResultModel.getStartDate()), new Date())
                    + " " + getString(R.string.project_details_days));
        }
        bottomMenuView.setOnClickListener(buttonMenuListener);
        addComentView.getAddPhotoButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_OK);
            }
        });
        projectDataView.getDropdownLegendView().getPopUpWindow().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showingFirst == true) {
                    projectDataView.getDropdownLegendView().getPopUpWindow().setImageResource(R.mipmap.dropdow_icon_selected);
                    projectDataView.getLegendTextView().setVisibility(View.VISIBLE);
                    showingFirst = false;
                } else {
                    projectDataView.getDropdownLegendView().getPopUpWindow().setImageResource(R.mipmap.dropdow_icon);
                    projectDataView.getLegendTextView().setVisibility(View.GONE);
                    showingFirst = true;
                }
                projectDataView.getLegendTextView().setText(TextUtils.isEmpty(projectResultModel.getLegend()) ? getString(R.string.project_details_show_legend_empty) : projectResultModel.getLegend());
            }
        });
        hidecCmmentViews();
        addComentView.getAddCommentButton().setOnClickListener(addCommentListener);

        String[] forums = projectResultModel.getForum().split(",");
        for(int i = 0; i < forums.length; i++) {
            createForumsViews(savedInstanceState, forums[i]);
        }
        //TODO:make here  getVideoReview()
        String[] videoReviews = projectResultModel.getForum().split(",");
        for(int i = 0; i < videoReviews.length; i++) {
            createVideoReviewViews(savedInstanceState, videoReviews[i]);
        }
    }

    private void createForumsViews(Bundle savedInstanceState, String forum) {
        final View view = getLayoutInflater(savedInstanceState).inflate(R.layout.item_forum_layout, null);
        final TextView text = (TextView) view.findViewById(R.id.forumText);
        text.setText(CutUrlHelper.getHost(forum));
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] forums = projectResultModel.getForum().split(",");
                for (int i = 0; i < forums.length; i++) {
                    if (text.getText().toString().indexOf(CutUrlHelper.getHost(forums[i])) >= 0){
                        openURL(forums[i].trim());
                    }
                }
            }
        });
        resourcesLayout.addView(view);
    }

    private void createVideoReviewViews(Bundle savedInstanceState, String reviews) {
        final View view = getLayoutInflater(savedInstanceState).inflate(R.layout.item_forum_layout, null);
        final TextView text = (TextView) view.findViewById(R.id.forumText);
        text.setText(CutUrlHelper.getHost(reviews));
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:make here  getVideoReview()
                String[] reviews = projectResultModel.getForum().split(",");
                for (int i = 0; i < reviews.length; i++) {
                    if (text.getText().toString().indexOf(CutUrlHelper.getHost(reviews[i])) >= 0){
                        openURL(reviews[i].trim());
                    }
                }
            }
        });
        videoReviewsLayout.addView(view);
    }

    View.OnClickListener addCommentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProgressBarHandler.showProgressDialog(context);
            if(!TextUtils.isEmpty(addComentView.getNameText().getText().toString())) {
                SharedPrefs.saveCommentName(context, addComentView.getNameText().getText().toString());
            }
            if(!TextUtils.isEmpty(pathToFile)) {
                SharedPrefs.saveLinkToImage(context, pathToFile);
            }
            addProjectComment(Integer.valueOf(projectResultModel.getId()),
                    addComentView.getCommentText().getText().toString(),
                    addComentView.getNameText().getText().toString(),
                    pathToFile).setOnSuccessRequestListener(new OnSuccessListener() {
                @Override
                public void success(Object result) {
                    ProgressBarHandler.dismissDialog();
                    getProjectComments();
                    KeyboardUtils.dismissKeyboard(context, addComentView.getNameText());
                    hidecCmmentViews();
                }
            });
        }
    };

    BottomMenuView.OnClickListener buttonMenuListener = new BottomMenuView.OnClickListener() {
        @Override
        public void clicked(int type) {
            switch (type) {
                case BottomMenuView.TYPE_SHARE:
                    shareIntent("", getString(R.string.project_share_text) + context.getPackageName());
                    App.getInstance().trackEvent("Project Details", "Click", "Share");
                    break;
                case BottomMenuView.TYPE_LIKE:
                    addProjectLike(Integer.valueOf(projectResultModel.getId())).setOnSuccessRequestListener(new OnSuccessListener() {
                        @Override
                        public void success(Object result) {
                            getProjectLikes();
                        }
                    });
                    App.getInstance().trackEvent("Project Details", "Click", "Like");
                    break;
                case BottomMenuView.TYPE_ADD_COMMENT:
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    if(TextUtils.isEmpty(SharedPrefs.getCommentName(context))) {
                        addComentView.getNameText().requestFocus();
                        KeyboardUtils.showKeyboard(context, addComentView.getNameText());
                    } else {
                        addComentView.getCommentText().requestFocus();
                        KeyboardUtils.showKeyboard(context, addComentView.getCommentText());
                    }
                    App.getInstance().trackEvent("Project Details", "Click", "Add comment");
                    break;
                case BottomMenuView.TYPE_FAVORITE:
                    addToFavorite(Integer.valueOf(projectResultModel.getId()));
                    App.getInstance().trackEvent("Project Details", "Click", "Add to favorite");
                    break;
            }
        }
    };

    public void hidecCmmentViews() {
        if(!TextUtils.isEmpty(SharedPrefs.getCommentName(context))) {
            addComentView.getNameText().setText(SharedPrefs.getCommentName(context));
            addComentView.getNameText().setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(pathToFile) || !TextUtils.isEmpty(SharedPrefs.getLinkToImage(context))) {
            addComentView.getAddPhotoButton().setVisibility(View.GONE);
            pathToFile = SharedPrefs.getLinkToImage(context);
        }
    }
}
