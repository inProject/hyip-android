package com.hyip.app.ui.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyip.app.R;
import com.hyip.app.components.textutils.CutUrlHelper;
import com.hyip.app.api.models.project.ProjectResultModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lfqdt on 14.12.2015.
 */
public class ProjectAdapter extends BaseAdapter {

    public Context context;
    private ArrayList<ProjectResultModel> projectResultModelArrayList = new ArrayList<>();
    private OnClickListener listener;

    public ProjectAdapter(Context context) {
        this.context = context;
    }

    public ProjectAdapter setItem(ProjectResultModel projectResultModel) {
        projectResultModelArrayList.add(projectResultModel);
        notifyDataSetChanged();
        return this;
    }

    public ProjectAdapter clearAdapter() {
        projectResultModelArrayList.clear();
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getCount() {
        return projectResultModelArrayList.size();
    }

    @Override
    public ProjectResultModel getItem(int position) {
        return projectResultModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.items_project, parent, false);
        Holder holder = new Holder(convertView);

        final ProjectResultModel model = getItem(position);

        holder.name.setText(model.getProjectUrl().startsWith("http://") || model.getProjectUrl().startsWith("https://") ?
                CutUrlHelper.getHost(model.getProjectUrl()) + " - " + model.getTitle() :
                model.getProjectUrl() + " - " + model.getTitle());
        holder.percent.setText(model.getPersent() + "%");
        Picasso.with(context).load(model.getImage()).into(holder.image);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clicked(model);
            }
        });
        return convertView;
    }

    class Holder{
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.percent)
        TextView percent;
        @Bind(R.id.image)
        ImageView image;

        public Holder(View view){
            ButterKnife.bind(this, view);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void clicked(ProjectResultModel model);
    }
}
