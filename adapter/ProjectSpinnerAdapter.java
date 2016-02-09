package com.hyip.app.ui.project.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hyip.app.R;
import com.hyip.app.api.models.categories.CategoriesResultModel;

import java.util.List;

/**
 * Created by lfqdt on 14.12.2015.
 */
public class ProjectSpinnerAdapter extends ArrayAdapter<CategoriesResultModel> {

    private LayoutInflater inflater = null;
    private List<CategoriesResultModel> objects;
    private Context context;

    public ProjectSpinnerAdapter(Context ctx, int txtViewResourceId, List<CategoriesResultModel> objects) {
        super(ctx, txtViewResourceId, objects);
        inflater = (LayoutInflater)ctx.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = objects;
        this.context = ctx;
    }

    @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) { return getCustomView(position, cnvtView, prnt); }

    @Override public View getView(int pos, View cnvtView, ViewGroup prnt) { return getCustomView(pos, cnvtView, prnt); }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View mySpinner = inflater.inflate(R.layout.item_categories, parent, false);
        TextView main_text = (TextView) mySpinner.findViewById(R.id.catName);
        String category = objects.get(position).getCategory();
        main_text.setText(category);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (category.contains("Набирающие популярность") ||
                    category.contains("Популярные") ||
                    category.contains("Новые") ||
                    category.contains("Gaining popularity") ||
                    category.contains("Popular") ||
                    category.contains("New")) {
                mySpinner.setBackground(context.getResources().getDrawable(R.drawable.popup_window_selector_2));
            } else {
                mySpinner.setBackground(context.getResources().getDrawable(R.drawable.popup_window_selector));
            }
        }
        return mySpinner;
    }
}