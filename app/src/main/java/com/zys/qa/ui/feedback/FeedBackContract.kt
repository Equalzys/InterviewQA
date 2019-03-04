package com.zys.qa.ui.feedback

import com.zys.baselibrary.base.IBasePresenter
import com.zys.baselibrary.base.IBaseView
import com.zys.qa.bean.QAList

class FeedBackContract {
    interface IView : IBaseView {
        fun postFBSuccess(isSuccess: Boolean?)

    }

    interface IPresenter : IBasePresenter {
        fun postFB(title: String, phone_type: String, info: String, contact: String)
    }
}