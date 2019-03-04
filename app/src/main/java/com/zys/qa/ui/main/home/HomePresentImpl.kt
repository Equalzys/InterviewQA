package com.zys.qa.ui.main.home

import android.app.Activity
import com.zys.baselibrary.base.BasePresenterImpl
import com.zys.baselibrary.net.RxSchedulers
import com.zys.qa.net.Api
import com.zys.qa.net.BaseObserver
import com.zys.qa.constant.NoCodeConstant
import com.zys.qa.bean.QAList
import java.util.HashMap

class HomePresentImpl(activity: Activity) : BasePresenterImpl<HomeContract.IView>(activity), HomeContract.IPresenter {
    override fun getQAList(from: Int) {
        val mObserver = object : BaseObserver<QAList>(
            context, this) {
            override fun onSuccess(mData: QAList?) {
                if (!isViewAttached()) {
                    return
                }
                if (mData == null) {
                    getView()?.onNoData(NoCodeConstant.NOQALIST)
                    return
                }
                if (mData.list == null) {
                    getView()?.onNoData(NoCodeConstant.NOQALIST)

                    return
                }
                if (mData.list!!.isEmpty()) {
                    getView()?.onNoData(NoCodeConstant.NOQALIST)

                    return
                }
                getView()?.getQAListSuccess(mData.list!!)

            }

        }
        val map = HashMap<String, String>()
        map["from"] = from.toString()
        map["limit"] = "20"
        mObserver.isRefershOrLoadmore = isRefershOrLoadmore()
        mObserver.isShowLoading=true
        Api.service.getQAList(map)
            .compose(RxSchedulers.compose(this))
            .subscribe(mObserver)
    }

}