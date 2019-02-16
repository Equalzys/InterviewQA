package com.lookbi.baselibrary.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.lookbi.baselibrary.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by zhangyisheng on 2018/3/23.
 */

public class RefershLoadingHeader extends RelativeLayout implements RefreshHeader {
    GifImageView gifImageView;
    ImageView imageView;

    public RefershLoadingHeader(Context context) {
        super(context);
        initView(context);
    }


    public RefershLoadingHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public RefershLoadingHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        setBackgroundColor(context.getResources().getColor(R.color.white));

        gifImageView = new GifImageView(context);
        gifImageView.setImageResource(R.drawable.loading);
        gifImageView.setVisibility(INVISIBLE);
//        imageView = new ImageView(context);
//        imageView.setImageResource(R.drawable.ic_loading_01);
        addView(gifImageView, DensityUtil.dp2px(80), DensityUtil.dp2px(80));
//        addView(imageView, DensityUtil.dp2px(80), DensityUtil.dp2px(80));

        setMinimumHeight(DensityUtil.dp2px(60));
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {
//        LogUtil.e("onPulling-offset="+offset);
    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {
//        LogUtil.e("onReleasing-offset="+offset);
    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
//        LogUtil.e("onReleased-");

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int
            extendHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 500;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState
            newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                imageView.setVisibility(VISIBLE);
                gifImageView.setVisibility(GONE);
//                LogUtil.e("PullDownToRefresh-");

//                mHeaderText.setText("下拉开始刷新");
//                mArrowView.setVisibility(VISIBLE);//显示下拉箭头
//                mProgressView.setVisibility(GONE);//隐藏动画
//                mArrowView.animate().rotation(0);//还原箭头方向
                break;
            case Refreshing:
                imageView.setVisibility(GONE);
                gifImageView.setVisibility(VISIBLE);
//                LogUtil.e("Refreshing-");

//                mHeaderText.setText("正在刷新");
//                mProgressView.setVisibility(VISIBLE);//显示加载动画
//                mArrowView.setVisibility(GONE);//隐藏箭头
                break;
            case ReleaseToRefresh:
//                imageView.setVisibility(VISIBLE);
//                gifImageView.setVisibility(GONE);
//                LogUtil.e("ReleaseToRefresh-");

//                mHeaderText.setText("释放立即刷新");
//                mArrowView.animate().rotation(180);//显示箭头改为朝上
                break;
        }
    }
}
