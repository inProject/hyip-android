package com.hyip.app.ui.project.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyip.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lfqdt on 16.12.2015.
 */
public class BottomMenuView extends LinearLayout implements View.OnClickListener, View.OnTouchListener {

    public static final int TYPE_SHARE = 0;
    public static final int TYPE_FAVORITE = 1;
    public static final int TYPE_LIKE = 2;
    public static final int TYPE_ADD_COMMENT = 3;

    private OnClickListener onClickListener;

    @Bind(R.id.shareButton)
    LinearLayout shareButton;
    @Bind(R.id.likeButton)
    LinearLayout likeButton;
    @Bind(R.id.favoriteButton)
    LinearLayout favoriteButton;
    @Bind(R.id.commentButton)
    LinearLayout commentButton;
    @Bind(R.id.shareText)
    TextView shareText;
    @Bind(R.id.likeText)
    TextView likeText;
    @Bind(R.id.commentText)
    TextView commentText;
    @Bind(R.id.favoriteText)
    TextView favoriteText;
    @Bind(R.id.shareBtn)
    ImageButton shareBtn;
    @Bind(R.id.likeBtn)
    ImageButton likeBtn;
    @Bind(R.id.commentBtn)
    ImageButton commentBtn;
    @Bind(R.id.favoriteBtn)
    ImageButton favoriteBtn;

    public BottomMenuView(Context context) {
        super(context);
        init(context);
    }

    public BottomMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_bottom_menu, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);
        setUpView();
    }

    private void setUpView() {
        shareButton.setOnClickListener(this);
        shareButton.setOnTouchListener(this);
        likeButton.setOnClickListener(this);
        likeButton.setOnTouchListener(this);
        favoriteButton.setOnClickListener(this);
        favoriteButton.setOnTouchListener(this);
        commentButton.setOnClickListener(this);
        commentButton.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.shareButton:
                onClickListener.clicked(TYPE_SHARE);
                break;
            case R.id.likeButton:
                onClickListener.clicked(TYPE_LIKE);
                break;
            case R.id.favoriteButton:
                onClickListener.clicked(TYPE_FAVORITE);
                break;
            case R.id.commentButton:
                onClickListener.clicked(TYPE_ADD_COMMENT);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                switch(v.getId()) {
                    case R.id.shareButton:
                        shareText.setTextColor(getResources().getColor(R.color.button_click_text_color));
                        shareBtn.setBackgroundResource(R.mipmap.share_button_clicked);
                        break;
                    case R.id.likeButton:
                        likeText.setTextColor(getResources().getColor(R.color.button_click_text_color));
                        likeBtn.setBackgroundResource(R.mipmap.like_button_clicked);
                        break;
                    case R.id.commentButton:
                        commentText.setTextColor(getResources().getColor(R.color.button_click_text_color));
                        commentBtn.setBackgroundResource(R.mipmap.add_comment_button_clicked);
                        break;
                    case R.id.favoriteButton:
                        favoriteText.setTextColor(getResources().getColor(R.color.button_click_text_color));
                        favoriteBtn.setBackgroundResource(R.mipmap.favorite_button_clicked);
                        break;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                switch(v.getId()) {
                    case R.id.shareButton:
                        shareText.setTextColor(getResources().getColor(R.color.white));
                        shareBtn.setBackgroundResource(R.mipmap.share_button);
                        break;
                    case R.id.likeButton:
                        likeText.setTextColor(getResources().getColor(R.color.white));
                        likeBtn.setBackgroundResource(R.mipmap.like_button);
                        break;
                    case R.id.commentButton:
                        commentText.setTextColor(getResources().getColor(R.color.white));
                        commentBtn.setBackgroundResource(R.mipmap.add_comment_button);
                        break;
                    case R.id.favoriteButton:
                        favoriteText.setTextColor(getResources().getColor(R.color.white));
                        favoriteBtn.setBackgroundResource(R.mipmap.favorite_button);
                        break;
                }
                break;
        }
        return false;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void clicked(int type);
    }
}
