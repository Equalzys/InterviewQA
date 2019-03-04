package com.zys.qa.ui.search

import android.app.Activity
import com.zys.baselibrary.base.BasePresenterImpl
import com.zys.baselibrary.bean.BaseBoolean
import com.zys.baselibrary.net.RxSchedulers
import com.zys.qa.net.Api
import com.zys.qa.net.BaseObserver
import com.zys.qa.constant.NoCodeConstant
import com.zys.qa.bean.QAList
import java.util.HashMap

class SearchPresentImpl(activity: Activity) : BasePresenterImpl<SearchContract.IView>(activity),
    SearchContract.IPresenter {
    override fun search(keyword: String) {
        val mObserver = object : BaseObserver<QAList>(
            context, this
        ) {
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
                getView()?.searchSuccess(mData.list!!)

            }

        }
        val map = HashMap<String, String>()
        map["keyword"] = keyword
        mObserver.isShowLoading = true
        Api.service.search(map)
            .compose(RxSchedulers.compose(this))
            .subscribe(mObserver)
    }


}