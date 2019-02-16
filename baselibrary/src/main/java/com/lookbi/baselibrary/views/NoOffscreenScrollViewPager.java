package com.lookbi.baselibrary.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不能手动左右滑动,没有预加载的ViewPager
 *
 */
public class NoOffscreenScrollViewPager extends NoOffscreenViewPager {

	public NoOffscreenScrollViewPager(Context context) {
		this(context,null);
	}

	public NoOffscreenScrollViewPager(Context context, AttributeSet attrs) {
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
