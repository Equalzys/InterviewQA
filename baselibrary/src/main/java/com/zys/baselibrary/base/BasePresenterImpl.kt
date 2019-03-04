package com.zys.baselibrary.base

import android.app.Activity
import com.zys.baselibrary.utils.LogUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.Reference
import java.lang.ref.WeakReference

open class BasePresenterImpl<V : IBaseView?>(context: Activity) : IBasePresenter {
    var context: Activity? = context
    private var isShowLoading = true
    private var isRefershOrLoadmore = false


    private var mWeakRef: Reference<V>? = null

    fun attachView(view: V) {
        mWeakRef = WeakReference(view)
    }

    //请求完 先判断是否连接和View层连接  否则可能会空指针
    fun isViewAttached(): Boolean {
        return mWeakRef != null && mWeakRef!!.get() != null
    }

    fun detachView() {
        if (mWeakRef != null) {
            mWeakRef!!.clear()
            mWeakRef = null
        }
        unDisposable()
    }

    fun getView(): V? {
        return if (mWeakRef != null) mWeakRef!!.get() else null
    }


    //将此PresenterImpl中所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private var mCompositeDisposable: CompositeDisposable? = null

    override fun setShowLoading(isShowLoading: Boolean) {
        this.isShowLoading = isShowLoading
    }

    override fun isShowLoading(): Boolean {
        return isShowLoading
    }

    override fun setRefershOrLoadmore(isRefershOrLoadmore: Boolean) {
        this.isRefershOrLoadmore = isRefershOrLoadmore
    }
    /**
     * 是否是下拉刷新-上拉加载 状态
     * @return
     */
    override fun isRefershOrLoadmore(): Boolean {
        return isRefershOrLoadmore
    }


    /**
     * 添加Disposable
     *
     * @param subscription
     */
    override fun addDisposable(subscription: Disposable) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    override fun unDisposable() {
        mCompositeDisposable?.dispose()
        mCompositeDisposable = null
        LogUtil.e("unDisposable---")
    }


}