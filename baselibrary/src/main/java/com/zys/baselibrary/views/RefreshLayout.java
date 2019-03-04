package com.zys.baselibrary.views;

import android.content.Context;
import android.util.AttributeSet;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by zhangyisheng on 2017/7/27.
 */

public class RefreshLayout extends SmartRefreshLayout {
    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//    //禁止自动加载更多
//    @Override
//    public SmartRefreshLayout setEnableAutoLoadmore(boolean enable) {
//        return super.setEnableAutoLoadmore(false);
//    }
//    //禁止回弹
//    @Override
//    public SmartRefreshLayout setEnableOverScrollDrag(boolean enable) {
//        return super.setEnableOverScrollDrag(false);
//    }
}
