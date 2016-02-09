package com.hyip.app.ui.project.views;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyip.app.R;
import com.hyip.app.api.models.categories.CategoriesModel;
import com.hyip.app.ui.article.adapter.ArticleSpinnerAdapter;
import com.hyip.app.ui.project.adapter.ProjectSpinnerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lfqdt on 30.12.2015.
 */
public class DropdownCategoryView extends LinearLayout {

    private Context context;
    public PopupWindow popupWindow;
    public PopupWindow window;
    public ListView headerListView;

    @Bind(R.id.selection)
    TextView dropdownText;
    @Bind(R.id.popUpWindow)
    ImageButton popUpWindow;

    public DropdownCategoryView(Context context) {
        super(context);
        this.context = context;
        if(!isInEditMode())
            init(context);
    }

    public DropdownCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if(!isInEditMode())
            init(context);
    }

    public DropdownCategoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        if(!isInEditMode())
            init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_dropdown_category, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);
        setUpViews();
    }

    private void setUpViews() {
        popupWindow = new PopupWindow(context);
        headerListView = new ListView(context);
    }

    public void setItem(CategoriesModel result) {
        window = projectPopUpWondow(result);
        dropdownText.setText(result.getResult().get(0).getCategory());
    }

    private PopupWindow projectPopUpWondow(CategoriesModel result) {
        headerListView.setAdapter(new ProjectSpinnerAdapter(context,
                R.layout.item_categories,
                result.getResult()));
        customizePopUpWindow();
        return popupWindow;
    }

    public void setItem(String[] array) {
        window = articlePopUpWondow(array);
        dropdownText.setText(array[0]);
    }

    private PopupWindow articlePopUpWondow(String[] array) {
        headerListView.setAdapter(new ArticleSpinnerAdapter(context,
                R.layout.item_categories,
                array));
        customizePopUpWindow();
        return popupWindow;
    }

    public void setLegendItem(String result) {
        window = legendPpUpWindow(result);
    }

    private PopupWindow legendPpUpWindow(String result) {
        String[] value = { result };
        headerListView.setAdapter(new ArticleSpinnerAdapter(context,
                R.layout.item_categories,
                value));
        customizePopUpWindow();
        return popupWindow;
    }

    private void customizePopUpWindow() {
        ColorDrawable myColor = new ColorDrawable(
                this.getResources().getColor(android.R.color.white)
        );
        headerListView.setDivider(myColor);
        popupWindow.setWidth(500);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(headerListView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                popUpWindow.setImageResource(R.mipmap.dropdow_icon);
                popupWindow.dismiss();
            }
        });
    }

    public TextView getDropdownText() {
        return dropdownText;
    }

    public ImageButton getPopUpWindow() {
        return popUpWindow;
    }

    public PopupWindow getWindow() {
        return window;
    }

    public ListView getHeaderListView() {
        return headerListView;
    }
}
