package com.zys.baselibrary.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不能左右滑动的ViewPager
 * 
 * @author ZYS
 * 
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context) {
		this(context,null);
	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 重写OnTouch事件 让它什么都不用做,不让他滑动
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}

}
