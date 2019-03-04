package com.zys.qa.ui.qa_info

import android.app.Activity
import com.zys.baselibrary.base.BasePresenterImpl
import com.zys.baselibrary.bean.BaseBoolean
import com.zys.baselibrary.net.RxSchedulers
import com.zys.qa.net.Api
import com.zys.qa.net.BaseObserver
import com.zys.qa.constant.NoCodeConstant
import com.zys.qa.bean.QAList
import java.util.HashMap

class QAInfoPresentImpl(activity: Activity) : BasePresenterImpl<QAInfoContract.IView>(activity), QAInfoContract.IPresenter {
    override fun getQAInfo(qaid: Int) {
        val mObserver = object : BaseObserver<QAList.QA>(
            context, this) {
            override fun onSuccess(mData: QAList.QA?) {
                if (!isViewAttached()) {
                    return
                }

                getView()?.getQAInfoSuccess(mData)

            }

        }
        val map = HashMap<String, String>()
        map["qaid"] = qaid.toString()
        mObserver.isShowLoading=true
        Api.service.getQAInfo(map)
            .compose(RxSchedulers.compose(this))
            .subscribe(mObserver)
    }


}