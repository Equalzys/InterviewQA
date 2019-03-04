package com.zys.qa.ui.add_qa

import com.zys.baselibrary.base.IBasePresenter
import com.zys.baselibrary.base.IBaseView
import com.zys.qa.bean.QAList

class AddQAContract {
    interface IView : IBaseView {
        fun postQASuccess(isSuccess: Boolean?)

    }

    interface IPresenter : IBasePresenter {
        fun postQA(title: String, content: String)
    }
}