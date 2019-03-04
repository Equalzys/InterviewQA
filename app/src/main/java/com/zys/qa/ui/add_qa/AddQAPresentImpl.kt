package com.zys.qa.ui.add_qa

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

class AddQAPresentImpl(activity: Activity) : BasePresenterImpl<AddQAContract.IView>(activity),
    AddQAContract.IPresenter {

    override fun postQA(title: String, content: String) {


        if (TextUtils.isEmpty(title)) {
            getView()?.onHttpError("请输入标题");
            return
        }
        if (TextUtils.isEmpty(content)) {
            getView()?.onHttpError("请输入答案");
            return
        }
        val map = HashMap<String, String>()
        map["question"] = title
        map["answer"] = content

        val mObserver = object : BaseObserver<BaseBoolean>(
            context, this
        ) {
            override fun onSuccess(mData: BaseBoolean?) {
                if (!isViewAttached()) {
                    return
                }

                getView()?.postQASuccess(mData?.is_success)

            }

        }

        mObserver.isShowLoading = true
        Api.service.addQA(map)
            .compose(RxSchedulers.compose(this))
            .subscribe(mObserver)
    }


}