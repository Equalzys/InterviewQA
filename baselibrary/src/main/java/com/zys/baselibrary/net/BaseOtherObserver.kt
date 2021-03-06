package com.zys.baselibrary.net

import android.app.Activity
import com.zys.baselibrary.base.BasePresenterImpl
import com.zys.baselibrary.base.IBaseView
import com.zys.baselibrary.utils.DialogUtils
import com.zys.baselibrary.utils.LogUtil
import com.zys.baselibrary.utils.SPUtil
import com.zys.baselibrary.utils.UtilTools
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseOtherObserver<T> : Observer<T> {
    private var mContext: Activity? = null
    var isShowLoading = false
    //是否可以手动取消网络请求（取消订阅）
    var isCanDispose = false
    val loadingMsg = "加载中"
    private var mDisposable: Disposable? = null
    var isRefershOrLoadmore = false
    var isShowErrorToast = true
    //缓存key为-1时，不使用缓存
    var cacheKey = -1
    private var presenter: BasePresenterImpl<*>? = null

    constructor(context: Activity?) {
        this.mContext = context
    }

    constructor(context: Activity?, presenter: BasePresenterImpl<*>?) {
        this.mContext = context
        this.presenter = presenter
    }

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
        if (mContext == null) {
            errorOver("context不能为空")
            onEnd()
            return
        }
        if (!UtilTools.isNetWorkConnected(mContext)) {
            try {
                if (cacheKey != -1) {
                    val t = SPUtil.getObject(mContext!!, cacheKey.toString() + "") as T
                    onSuccess(t)
                } else {
                    errorOver("请检查您的网络")
                }
            } catch (e: Exception) {
                errorOver("请检查您的网络")
            }
            onEnd()
        } else {
            onStart(d)
            if (!isRefershOrLoadmore) {
                if (isShowLoading) {
                    if (isCanDispose) {
                        DialogUtils.show(mContext!!, loadingMsg) {
                            //随着dialog消失，如果订阅了就取消订阅
                            if (d.isDisposed) {
                                d.dispose()
                                onEnd()
                                LogUtil.e("onDismiss-BaseObserver-dispose")
                            }
                        }
                    } else {
                        DialogUtils.show(mContext!!, loadingMsg)
                    }
                }
            }
        }
    }


    private fun errorOver(msg: String) {
        if (null != mDisposable && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
        onError(msg)
    }

    override fun onNext(value: T) {
        LogUtil.e("onNext-$value")
        if (isShowLoading) {
            DialogUtils.dismiss()
        }
        try {
            onEnd()
            onSuccess(value)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

        if (isShowLoading) {
            DialogUtils.dismiss()
        }
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        DialogUtils.dismiss()
        var err = ""
        if (!UtilTools.isNetWorkConnected(mContext)) {
            err = "请检查您的网络"
        } else {
            err = ExceptionHelper.handleException(e)
        }
        if (!err.isEmpty()) {
            try {
                onError(err)
            } catch (e1: Exception) {
                e1.printStackTrace()
                onError(e1.message)
            }

        }
        onEnd()
    }

    override fun onComplete() {
        if (null != mDisposable && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
    }


    protected fun onStart(disposable: Disposable?) {
        LogUtil.e("onStart")
    }

    protected abstract fun onSuccess(mData: T?)

    protected open fun onError(msg: String?) {
        DialogUtils.dismiss()
        if (presenter != null) {
            if (presenter!!.isViewAttached()) {
                (presenter!!.getView() as IBaseView).onHttpError(msg!!)
            }
        }
    }

    protected open fun onEnd() {
        if (presenter != null) {
            if (presenter!!.isViewAttached()) {
                (presenter!!.getView() as IBaseView).onRequestEnd()
            }
        }
    }
}