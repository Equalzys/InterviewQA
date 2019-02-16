package com.zys.qa.ui.feedback

import com.lookbi.baselibrary.base.IBasePresenter
import com.lookbi.baselibrary.base.IBaseView
import com.zys.qa.bean.QAList

class FeedBackContract {
    interface IView : IBaseView {
        fun postFBSuccess(isSuccess: Boolean?)

    }

    interface IPresenter : IBasePresenter {
        fun postFB(title: String, phone_type: String, info: String, contact: String)
    }
}