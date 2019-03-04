package com.zys.qa.ui.feedback

import android.app.Activity
import android.text.TextUtils
import com.zys.baselibrary.base.BasePresenterImpl
import com.zys.baselibrary.bean.BaseBoolean
import com.zys.baselibrary.net.RxSchedulers
import com.zys.qa.net.Api
import com.zys.qa.net.BaseObserver
import com.zys.qa.constant.NoCodeConstant
import com.zys.qa.bean.QAList
import java.util.HashMap

class FeedBackPresentImpl(activity: Activity) : BasePresenterImpl<FeedBackContract.IView>(activity),
    FeedBackContract.IPresenter {
    override fun postFB(title: String, phone_type: String, info: String, contact: String) {
        val mObserver = object : BaseObserver<BaseBoolean>(
            context, this
        ) {
            override fun onSuccess(mData: BaseBoolean?) {
                if (!isViewAttached()) {
                    return
                }

                getView()?.postFBSuccess(mData?.is_success)

            }

        }
        val map = HashMap<String, String>()
        map["title"] = title
        map["phone_type"] = phone_type
        map["info"] = info
        if (!TextUtils.isEmpty(contact)) {
            map["contact"] = contact
        }
        mObserver.isShowLoading = true
        Api.service.addFeedBack(map)
            .compose(RxSchedulers.compose(this))
            .subscribe(mObserver)
    }


}