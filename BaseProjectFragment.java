package com.hyip.app.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hyip.app.R;
import com.hyip.app.api.handler.imp.OnSuccessListener;
import com.hyip.app.api.models.project.ProjectResultModel;
import com.hyip.app.api.models.project.comments.CommentsModel;
import com.hyip.app.api.models.project.comments.CommentsResultModel;
import com.hyip.app.components.KeyboardUtils;
import com.hyip.app.components.dl.App;
import com.hyip.app.components.prefs.SharedPrefs;
import com.hyip.app.components.textutils.CutUrlHelper;
import com.hyip.app.components.textutils.JsonParser;
import com.hyip.app.ui.BaseFragment;
import com.hyip.app.ui.project.adapter.ProjectCommentsAdapter;
import com.hyip.app.ui.project.views.AddCommentView;
import com.hyip.app.ui.project.views.BottomMenuView;
import com.hyip.app.ui.project.views.NotScolledListView;
import com.hyip.app.ui.project.views.ProjectDataView;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;

/**
 * Created by lfqdt on 25.12.2015.
 */
public class BaseProjectFragment extends BaseFragment {

    public ProjectResultModel projectResultModel;
    public ArrayList<CommentsResultModel> commentsResultModels;
    public ProjectCommentsAdapter projectCommentsAdapter;

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.workedTime)
    TextView workedTime;
    @Bind(R.id.likeFinger)
    ImageView likeFinger;
    @Bind(R.id.pays)
    TextView pays;
    @Bind(R.id.numberLikes)
    TextView numberLikes;
    @Bind(R.id.commentsList)
    NotScolledListView commentsList;
    @Bind(R.id.scroll)
    ScrollView scrollView;
    @Bind(R.id.projectDataView)
    ProjectDataView projectDataView;
    @Bind(R.id.bottomButtonsView)
    BottomMenuView bottomMenuView;
    @Bind(R.id.addComentView)
    AddCommentView addComentView;

    public void getProjectLikes() {
        getProjectLikes(Integer.valueOf(projectResultModel.getId())).setOnSuccessRequestListener(new OnSuccessListener() {
            @Override
            public void success(Object result) {
                Response response = ((Response) result);
                try {
                    String count = JsonParser.getResult(CutUrlHelper.convertFromResponseToString(response)).getString("count_like");
                    numberLikes.setText(count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getProjectComments();
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    public void getProjectComments() {
        getProjectComments(Integer.valueOf(projectResultModel.getId())).setOnSuccessRequestListener(new OnSuccessListener() {
            @Override
            public void success(Object result) {
                commentsResultModels = ((CommentsModel) result).getResult();
                projectCommentsAdapter.setItemList(commentsResultModels);
                commentsList.setAdapter(projectCommentsAdapter);
                addComentView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_OK) {
            if(data != null) {
                onActivityResultConverter(data);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(TextUtils.isEmpty(SharedPrefs.getCommentName(context))) {
                            addComentView.getNameText().requestFocus();
                            KeyboardUtils.showKeyboard(context, addComentView.getNameText());
                        } else {
                            addComentView.getCommentText().requestFocus();
                            KeyboardUtils.showKeyboard(context, addComentView.getCommentText());
                        }
                    }
                }, 1000);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_project_details, container, false);
        ButterKnife.bind(this, view);
        App.getInstance().trackScreenView("Project Details Screen");
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if ((view.getRootView().getHeight() - view.getHeight()) > view.getRootView().getHeight() / 3) {
                    addComentView.setVisibility(View.VISIBLE);
                    bottomMenuView.setVisibility(View.GONE);
                } else {
                    bottomMenuView.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
}
