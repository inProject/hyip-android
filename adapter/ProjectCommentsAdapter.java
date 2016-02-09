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
import com.hyip.app.api.models.project.comments.CommentsResultModel;
import com.hyip.app.components.textutils.CutUrlHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lfqdt on 25.12.2015.
 */
public class ProjectCommentsAdapter extends BaseAdapter {

    public Context context;
    private ArrayList<CommentsResultModel> commentsResultModels = new ArrayList<>();

    public ProjectCommentsAdapter(Context context) {
        this.context = context;
    }

    public ProjectCommentsAdapter setItem(CommentsResultModel model) {
        commentsResultModels.add(model);
        notifyDataSetChanged();
        return this;
    }

    public ProjectCommentsAdapter setItemList(ArrayList<CommentsResultModel> model) {
        this.commentsResultModels = model;
        return this;
    }

    public ProjectCommentsAdapter clearAdapter() {
        commentsResultModels.clear();
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getCount() {
        return commentsResultModels.size();
    }

    @Override
    public CommentsResultModel getItem(int position) {
        return commentsResultModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_commets, parent, false);
        Holder holder = new Holder(convertView);

        CommentsResultModel model = getItem(position);
        holder.nameText.setText(CutUrlHelper.getHost(model.getName()));
        holder.dateText.setText(model.getCreatedAt());
        holder.commentText.setText(model.getText());
        Picasso.with(context).load(model.getImage()).fit().into(holder.image);
        return convertView;
    }

    class Holder{
        @Bind(R.id.nameText)
        TextView nameText;
        @Bind(R.id.dateText)
        TextView dateText;
        @Bind(R.id.commentText)
        TextView commentText;
        @Bind(R.id.avatarImage)
        ImageView image;

        public Holder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
