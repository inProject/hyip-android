package com.hyip.app.ui.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.hyip.app.R;
import com.hyip.app.api.handler.imp.OnSuccessListener;
import com.hyip.app.api.models.categories.CategoriesModel;
import com.hyip.app.api.models.categories.CategoriesResultModel;
import com.hyip.app.api.models.project.ProjectModel;
import com.hyip.app.api.models.project.ProjectResultModel;
import com.hyip.app.components.Constants;
import com.hyip.app.components.dl.App;
import com.hyip.app.ui.BaseFragment;
import com.hyip.app.ui.project.adapter.ProjectAdapter;
import com.hyip.app.ui.project.views.DropdownCategoryView;
import com.hyip.app.ui.project.views.SearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lfqdt on 14.12.2015.
 */
public class ProjectsFragment extends BaseFragment {

    private static final int POPULAR_PROJECTS = 0;

    public static final int TYPE_PROJECT = 0;
    public static final int TYPE_OUR_SELECTION = 1;
    public static final int TYPE_SCAM = 2;

    @Bind(R.id.listView)
    ListView listView;

    private int type;
    private ProjectAdapter adapter;
    public ArrayList<CategoriesResultModel> categoriesModels;
    public ArrayList<ProjectResultModel> projectResultModels;

    public static final ProjectsFragment newInstance(int type){
        ProjectsFragment f = new ProjectsFragment();
        f.type  = type;
        return f;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ProjectAdapter(context);
        switch(type) {
            case TYPE_SCAM:
                App.getInstance().trackScreenView("Scam List Screen");
                listView.addHeaderView(initScamViews());
                getProjects().setOnSuccessRequestListener(new OnSuccessListener() {
                    @Override
                    public void success(Object result) {
                        projectResultModels = ((ProjectModel) result).getResult();
                        addDataToAdapter(result);
                    }
                });
                break;
            case TYPE_OUR_SELECTION:
                App.getInstance().trackScreenView("Our Selection List Screen");
                listView.addHeaderView(initScamViews());
                getProjects().setOnSuccessRequestListener(new OnSuccessListener() {
                    @Override
                    public void success(Object result) {
                        projectResultModels = ((ProjectModel) result).getResult();
                        addDataToAdapter(result);
                    }
                });
                break;
            case TYPE_PROJECT:
                App.getInstance().trackScreenView("Project List Screen");
                getCategories().setOnSuccessRequestListener(new OnSuccessListener() {
                    @Override
                    public void success(Object result) {
                        categoriesModels = ((CategoriesModel) result).getResult();
                        listView.addHeaderView(initViews(((CategoriesModel) result)));
                        getProjects().setOnSuccessRequestListener(new OnSuccessListener() {
                            @Override
                            public void success(Object result) {
                                projectResultModels = ((ProjectModel) result).getResult();
                                getProjectsByCategory(Integer.valueOf(categoriesModels.get(POPULAR_PROJECTS).getId()))
                                        .setOnSuccessRequestListener(new OnSuccessListener() {
                                            @Override
                                            public void success(Object result) {
                                                progressBar.setVisibility(View.GONE);
                                                addDataToAdapter(result);
                                            }
                                        });
                            }
                        });
                    }
                });
                break;
        }
        adapter.setOnClickListener(new ProjectAdapter.OnClickListener() {
            @Override
            public void clicked(ProjectResultModel model) {
                App.getInstance().trackEvent("Project List", "Click", "Go to project details");
                getFragmentManager().beginTransaction().replace(R.id.container, new ProjectDetailsFragment().newInstance(model, type))
                        .addToBackStack(Constants.FRAGMENT_TAG)
                        .commit();
            }
        });
    }

    public View initViews(CategoriesModel result) {
        View view = View.inflate(context, R.layout.header_project, null);
        final DropdownCategoryView categoryView = (DropdownCategoryView) view.findViewById(R.id.customDropdown);

        bannerView(view);
        searchView(view);

        categoryView.setItem(result);
        categoryView.getPopUpWindow().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryView.getPopUpWindow().setImageResource(R.mipmap.dropdow_icon_selected);
                categoryView.getWindow().showAsDropDown(v);
            }
        });
        categoryView.getHeaderListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = categoriesModels.get(position).getCategory();
                categoryView.getDropdownText().setText(selectedItemText);
                categoryView.getPopUpWindow().setImageResource(R.mipmap.dropdow_icon);
                getProjectsByCategory(Integer.valueOf(categoriesModels.get(position).getId())).setOnSuccessRequestListener(new OnSuccessListener() {
                    @Override
                    public void success(Object result) {
                        addDataToAdapter(result);
                    }
                });
                categoryView.getWindow().dismiss();
            }
        });
        return view;
    }

    public View initScamViews() {
        View view = View.inflate(context, R.layout.header_project_scam, null);
        bannerView(view);
        searchView(view);
        return view;
    }

    private void searchView (View view) {
        final SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.getSearchEditText().setHint(getString(R.string.project_spinner_text));
        searchView.getSearchButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(searchView.getSearchEditText().getText().toString())) {
                    searchProjects(searchView.getSearchEditText().getText().toString()).setOnSuccessRequestListener(new OnSuccessListener() {
                        @Override
                        public void success(Object result) {
                            addDataToAdapter(result);
                        }
                    });
                }
            }
        });
    }

    private void bannerView (View view) {
        ImageView bannerView = (ImageView) view.findViewById(R.id.banner);
        if(TextUtils.isEmpty(projectBanner))
            bannerView.setVisibility(View.GONE);
        switch (type) {
            case TYPE_SCAM:
                Picasso.with(context).load(projectScamBanner).into(bannerView);
                bannerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openURL(projectScamBannerLink);
                        App.getInstance().trackEvent("Scam List", "Click", "Open Banner");
                    }
                });
                break;
            case TYPE_OUR_SELECTION:
                Picasso.with(context).load(projectSelectionBanner).into(bannerView);
                bannerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openURL(projectSelectionBannerLink);
                        App.getInstance().trackEvent("Our Selection List", "Click", "Open Banner");
                    }
                });
                break;
            case TYPE_PROJECT:
                Picasso.with(context).load(projectBanner).into(bannerView);
                bannerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openURL(projectBannerLink);
                        App.getInstance().trackEvent("Projects List", "Click", "Open Banner");
                    }
                });
                break;
        }
    }

    private void addDataToAdapter(Object result) {
        ArrayList<ProjectResultModel> projectResultModels = ((ProjectModel) result).getResult();
        progressBar.setVisibility(View.GONE);
        adapter.clearAdapter();
        for (ProjectResultModel res : projectResultModels) {
            switch (type) {
                case TYPE_SCAM:
                    if (Integer.valueOf(res.getScam()) == 1)
                        adapter.setItem(res);
                    break;
                case TYPE_OUR_SELECTION:
                    if (Integer.valueOf(res.getOurChoice()) == 1)
                        adapter.setItem(res);
                    break;
                case TYPE_PROJECT:
                    if (Integer.valueOf(res.getScam()) == 0)
                        adapter.setItem(res);
                    break;
            }
        }
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
