package com.hyip.app.ui.project.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hyip.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lfqdt on 24.12.2015.
 */
public class AddCommentView extends LinearLayout {

    @Bind(R.id.addPhotoButton)
    Button addPhotoButton;
    @Bind(R.id.addCommentButton)
    Button addCommentButton;
    @Bind(R.id.nameText)
    EditText nameText;
    @Bind(R.id.commentText)
    EditText commentText;

    public AddCommentView(Context context) {
        super(context);
        init(context);
    }

    public AddCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_add_comment, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);
    }

    public EditText getNameText() {
        return nameText;
    }

    public EditText getCommentText() {
        return commentText;
    }

    public Button getAddPhotoButton() {
        return addPhotoButton;
    }

    public Button getAddCommentButton() {
        return addCommentButton;
    }
}
