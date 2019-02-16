package com.lookbi.baselibrary.views.pagestatus;

import android.app.Activity;
import android.view.View;

public class CustomLoadingLayout extends SmartLoadingLayout{
    private Activity mHostActivity;

    CustomLoadingLayout(Activity activity) {
        mHostActivity = activity;
    }

    public void setLoadingView(int viewID) {
        mLoadingView = mHostActivity.findViewById(viewID);
        mLoadingView.setVisibility(View.GONE);
    }

    public void setContentView(int viewID) {
        mContentView = mHostActivity.findViewById(viewID);
        mContentView.setVisibility(View.VISIBLE);
    }

    public void setEmptyView(int viewID) {
        mEmptyView = mHostActivity.findViewById(viewID);
        mEmptyView.setVisibility(View.GONE);
    }

    public void setErrorView(int viewID) {
        mErrorView = mHostActivity.findViewById(viewID);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void onLoading() {
        showViewWithStatus(LayoutStatus.Loading);
    }


    @Override
    public void onShowData() {
        showViewWithStatus(LayoutStatus.ShowData);
    }

    @Override
    public void onEmpty() {
        showViewWithStatus(LayoutStatus.Empty);
    }

    @Override
    public void onEmpty(boolean isShowAgreen) {
        showViewWithStatus(LayoutStatus.Empty);
    }

    @Override
    public void onEmpty(String s) {
        showViewWithStatus(LayoutStatus.Empty);
    }

    @Override
    public void onError() {
        showViewWithStatus(LayoutStatus.Error);
    }

    @Override
    public void onError(String e) {

    }

}
