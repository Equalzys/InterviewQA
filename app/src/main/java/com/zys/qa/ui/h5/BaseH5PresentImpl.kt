package com.zys.qa.ui.h5

import android.app.Activity
import com.lookbi.baselibrary.base.BasePresenterImpl
import com.lookbi.baselibrary.net.RxSchedulers
import com.zys.qa.net.Api
import com.zys.qa.net.BaseObserver
import com.zys.qa.bean.BaseH5

class BaseH5PresentImpl(context: Activity) : BasePresenterImpl<BaseH5Contract.IView>(context),
    BaseH5Contract.IPresenter {
    override fun getAboutUs() {
        val mObserver = object : BaseObserver<BaseH5>(
            context, this
        ) {

            override fun onSuccess(mData: BaseH5?) {
                if (!isViewAttached()) {
                    return
                }
                getView()?.getAboutUsSuccess(mData)
            }
        }
        Api.service.getAboutus(1)
            .compose(RxSchedulers.compose(this))
            .subscribe(mObserver)
    }

}