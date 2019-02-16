package com.lookbi.baselibrary.base

import io.reactivex.disposables.Disposable

interface IBasePresenter {
    fun setShowLoading(isShow: Boolean)

    fun isShowLoading(): Boolean

    fun setRefershOrLoadmore(isShow: Boolean)

    fun isRefershOrLoadmore(): Boolean

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    fun addDisposable(subscription: Disposable)

    //注销所有请求
    fun unDisposable()
}