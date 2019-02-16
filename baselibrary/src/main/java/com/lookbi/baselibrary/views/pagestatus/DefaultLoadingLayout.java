package com.lookbi.baselibrary.views.pagestatus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lookbi.baselibrary.R;


/**
 * 默认的正在加载布局...
 */
public class DefaultLoadingLayout extends SmartLoadingLayout {
    private LayoutInflater mInflater;
    private Context mContext;
    private RelativeLayout rlAddedView;
    private boolean mAnyAdded;
    private RelativeLayout.LayoutParams mLayoutParams;

    private TextView tvLoadingDescription;
    private TextView tvEmptyDescription;
    private TextView tvErrorDescription;
    private TextView tv_empty_message;
    private TextView tv_error_message;
    private TextView tv_error;
    private TextView tv_empty_agreen;
    private DotsTextView dtvLoading;
    private LinearLayout mLoadingContent;

    public DefaultLoadingLayout(Context context, View contentView) {
        this.mContext = context;
        this.mContentView = contentView;
        this.mInflater = LayoutInflater.from(context);
        {
            mLoadingView = mInflater.inflate(R.layout.smartloadinglayout_view_on_loading, null);
            mEmptyView = mInflater.inflate(R.layout.smartloadinglayout_view_on_empty, null);
            tv_empty_message = mEmptyView.findViewById(R.id.tv_empty_message);
            tv_empty_agreen = mEmptyView.findViewById(R.id.tv_empty_agreen);
            mErrorView = mInflater.inflate(R.layout.smartloadinglayout_view_on_error, null);
            tv_error_message = mErrorView.findViewById(R.id.tv_error_message);
            tv_error = mErrorView.findViewById(R.id.tv_error);
            dtvLoading = (DotsTextView) mLoadingView.findViewById(R.id.dots);
            mLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                    .MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            mLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        }
    }

    public void setLoadingDescriptionColor(int color) {
        if (tvLoadingDescription == null) {
            tvLoadingDescription = (TextView) mLoadingView.findViewById(R.id.tv_loading_message);
        }

        tvLoadingDescription.setTextColor(color);
        dtvLoading.setTextColor(color);
    }

    public void setLoadingDescriptionTextSize(float size) {
        if (tvLoadingDescription == null) {
            tvLoadingDescription = (TextView) mLoadingView.findViewById(R.id.tv_loading_message);
        }

        tvLoadingDescription.setTextSize(size);
    }

    public void setLoadingDescription(String loadingDescription) {
        if (tvLoadingDescription == null) {
            tvLoadingDescription = (TextView) mLoadingView.findViewById(R.id.tv_loading_message);
        }

        tvLoadingDescription.setText(loadingDescription);
    }

    public void setLoadingDescription(int resID) {
        if (tvLoadingDescription == null) {
            tvLoadingDescription = (TextView) mLoadingView.findViewById(R.id.tv_loading_message);
        }

        tvLoadingDescription.setText(resID);
    }

    public void replaceLoadingProgress(View view) {
        if (mLoadingContent == null) {
            mLoadingContent = (LinearLayout) mLoadingView.findViewById(R.id.ll_loading);
        }

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams
                        .WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        ((RelativeLayout) mLoadingView).addView(view, lp);
        ((RelativeLayout) mLoadingView).removeView(mLoadingContent);
    }

    public void setEmptyDescriptionColor(int color) {
        if (tvEmptyDescription == null) {
            tvEmptyDescription = (TextView) mEmptyView.findViewById(R.id.tv_empty_message);
        }

        tvEmptyDescription.setTextColor(color);
    }

    public void setEmptyDescriptionTextSize(float size) {
        if (tvEmptyDescription == null) {
            tvEmptyDescription = (TextView) mEmptyView.findViewById(R.id.tv_empty_message);
        }

        tvEmptyDescription.setTextSize(size);
    }

    public void setEmptyDescription(String emptyDescription) {
        if (tvEmptyDescription == null) {
            tvEmptyDescription = (TextView) mEmptyView.findViewById(R.id.tv_empty_message);
        }

        tvEmptyDescription.setText(emptyDescription);
    }

    public void setEmptyDescription(int resID) {
        if (tvEmptyDescription == null) {
            tvEmptyDescription = (TextView) mEmptyView.findViewById(R.id.tv_empty_message);
        }

        tvEmptyDescription.setText(resID);
    }

    public void replaceEmptyIcon(Drawable newIcon) {
        if (tvEmptyDescription == null) {
            tvEmptyDescription = (TextView) mEmptyView.findViewById(R.id.tv_empty_message);
        }

        newIcon.setBounds(0, 0, newIcon.getMinimumWidth(), newIcon.getMinimumHeight());
        tvEmptyDescription.setCompoundDrawables(null, newIcon, null, null);
    }

    public void replaceEmptyBg(int color) {
        if (mEmptyView == null) {
            mEmptyView.setBackgroundColor(color);
        }
    }

    public void replaceEmptyIcon(int resId) {
        if (tvEmptyDescription == null) {
            tvEmptyDescription = (TextView) mEmptyView.findViewById(R.id.tv_empty_message);
        }

        Drawable newIcon = mContext.getResources().getDrawable(resId);
        newIcon.setBounds(0, 0, newIcon.getMinimumWidth(), newIcon.getMinimumHeight());
        tvEmptyDescription.setCompoundDrawables(null, newIcon, null, null);
    }

    public void setErrorDescriptionColor(int color) {
        if (tvErrorDescription == null) {
            tvErrorDescription = (TextView) mErrorView.findViewById(R.id.tv_error_message);
        }

        tvErrorDescription.setTextColor(color);
    }

    public void setErrorDescriptionTextSize(float size) {
        if (tvErrorDescription == null) {
            tvErrorDescription = (TextView) mErrorView.findViewById(R.id.tv_error_message);
        }

        tvErrorDescription.setTextSize(size);
    }

    public void setErrorDescription(String errorDescription) {
        if (tvErrorDescription == null) {
            tvErrorDescription = (TextView) mErrorView.findViewById(R.id.tv_error_message);
        }

        tvErrorDescription.setText(errorDescription);
    }

    public void setErrorDescription(int resID) {
        if (tvErrorDescription == null) {
            tvErrorDescription = (TextView) mErrorView.findViewById(R.id.tv_error_message);
        }

        tvErrorDescription.setText(resID);
    }

    public void setErrorButtonListener(View.OnClickListener listener) {
        if (tv_error == null) {
            tv_error = (TextView) mErrorView.findViewById(R.id.tv_error);
        }

        tv_error.setOnClickListener(listener);
    }

    public void setEmptyButtonListener(View.OnClickListener listener) {
        if (tv_empty_agreen == null) {
            tv_empty_agreen = (TextView) mEmptyView.findViewById(R.id.tv_empty_agreen);
        }
        tv_empty_agreen.setOnClickListener(listener);
    }


    public void setErrorButtonBackground(Drawable background) {
        if (tv_error == null) {
            tv_error = (TextView) mErrorView.findViewById(R.id.tv_error);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tv_error.setBackground(background);
        } else {
            //noinspection deprecation
            tv_error.setBackgroundDrawable(background);
        }
    }

    public void setErrorButtonBackground(int resID) {
        if (tv_error == null) {
            tv_error = (TextView) mErrorView.findViewById(R.id.tv_error);
        }

        tv_error.setBackgroundResource(resID);

    }

    public void setErrorButtonTextColor(int color) {
        if (tv_error == null) {
            tv_error = (TextView) mErrorView.findViewById(R.id.tv_error);
        }

        tv_error.setTextColor(color);
    }

    public void setErrorButtonText(String text) {
        if (tv_error == null) {
            tv_error = (TextView) mErrorView.findViewById(R.id.tv_error);
        }

        tv_error.setText(text);
    }

    public void setErrorButtonText(int resID) {
        if (tv_error == null) {
            tv_error = (TextView) mErrorView.findViewById(R.id.tv_error);
        }

        tv_error.setText(resID);
    }

    public void hideErrorButton() {
        if (tv_error == null) {
            tv_error = (TextView) mErrorView.findViewById(R.id.tv_error);
        }

        tv_error.setVisibility(View.GONE);
    }

    public void replaceErrorButton(Button newButton) {
        if (tv_error == null) {
            tv_error = (TextView) mErrorView.findViewById(R.id.tv_error);
        }

        ((RelativeLayout) mErrorView).removeView(tv_error);
        tv_error = newButton;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams
                        .WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.BELOW, R.id.tv_error_message);
        lp.topMargin = dp2px(mContext, 12);
        ((RelativeLayout) mErrorView).addView(tv_error, lp);
    }

    public void replaceErrorIcon(Drawable newIcon) {
        if (tvErrorDescription == null) {
            tvErrorDescription = (TextView) mErrorView.findViewById(R.id.tv_error_message);
        }

        newIcon.setBounds(0, 0, newIcon.getMinimumWidth(), newIcon.getMinimumHeight());
        tvErrorDescription.setCompoundDrawables(null, newIcon, null, null);
    }


    public void replaceErrorIcon(int resId) {
        if (tvErrorDescription == null) {
            tvErrorDescription = (TextView) mErrorView.findViewById(R.id.tv_error_message);
        }

        Drawable newIcon = mContext.getResources().getDrawable(resId);
        newIcon.setBounds(0, 0, newIcon.getMinimumWidth(), newIcon.getMinimumHeight());
        tvErrorDescription.setCompoundDrawables(null, newIcon, null, null);
    }

    public void setLayoutBackgroundColor(int color) {
        initAddedLayout();
        rlAddedView.setBackgroundColor(color);
    }

    public void setLayoutBackground(int resID) {
        initAddedLayout();
        rlAddedView.setBackgroundResource(resID);
    }

    @Override
    public void onLoading() {
        checkContentView();
        if (!mLoadingAdded) {
            initAddedLayout();

            if (mLoadingView != null) {
                rlAddedView.addView(mLoadingView, mLayoutParams);
                mLoadingAdded = true;
            }
        }
        dtvLoading.showAndPlay();
        showViewWithStatus(LayoutStatus.Loading);
    }

    @Override
    public void onShowData() {
        checkContentView();
        dtvLoading.hideAndStop();
        showViewWithStatus(LayoutStatus.ShowData);
    }

    @Override
    public void onEmpty() {
        checkContentView();
        if (!mEmptyAdded) {
            initAddedLayout();

            if (mEmptyView != null) {
                rlAddedView.addView(mEmptyView, mLayoutParams);
                mEmptyAdded = true;
            }
        }
        dtvLoading.hideAndStop();
        showViewWithStatus(LayoutStatus.Empty);
    }

    @Override
    public void onEmpty(boolean isShowAgreen) {
        checkContentView();
        if (!mEmptyAdded) {
            initAddedLayout();

            if (mEmptyView != null) {
                tv_empty_agreen.setVisibility(isShowAgreen ? View.VISIBLE : View.GONE);
                rlAddedView.addView(mEmptyView, mLayoutParams);
                mEmptyAdded = true;
            }
        }
        dtvLoading.hideAndStop();
        showViewWithStatus(LayoutStatus.Empty);
    }

    @Override
    public void onEmpty(String s) {
        checkContentView();
        if (!mEmptyAdded) {
            initAddedLayout();

            if (mEmptyView != null) {
                tv_empty_message.setText(s);
                rlAddedView.addView(mEmptyView, mLayoutParams);
                mEmptyAdded = true;
            }
        }
        dtvLoading.hideAndStop();
        showViewWithStatus(LayoutStatus.Empty);
    }

    public void showEmptyAgreen(boolean b) {
        if (tv_empty_agreen != null) {
            tv_empty_agreen.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onError() {
        checkContentView();
        if (!mErrorAdded) {
            initAddedLayout();

            if (mErrorView != null) {
                rlAddedView.addView(mErrorView, mLayoutParams);
                mErrorAdded = true;
            }
        }
        dtvLoading.hideAndStop();
        showViewWithStatus(LayoutStatus.Error);
    }

    @Override
    public void onError(String e) {
        checkContentView();
        if (!mErrorAdded) {
            initAddedLayout();

            if (mErrorView != null) {
                tv_error_message.setText(e);
                rlAddedView.addView(mErrorView, mLayoutParams);
                mErrorAdded = true;
            }
        }
        dtvLoading.hideAndStop();
        showViewWithStatus(LayoutStatus.Error);
    }


    private void checkContentView() {
        if (mContentView == null) {
            throw new NullPointerException("The content view not set..");
        }
    }

    private void initAddedLayout() {
        if (!mAnyAdded) {
            rlAddedView = new RelativeLayout(mContext);
            rlAddedView.setLayoutParams(mLayoutParams);
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            parent.addView(rlAddedView);
            mAnyAdded = true;
        }
    }

    /**
     * dp转px
     */
    public int dp2px(Context context, float dp) {
        // 拿到屏幕密度
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);// 四舍五入
        return px;
    }
}
