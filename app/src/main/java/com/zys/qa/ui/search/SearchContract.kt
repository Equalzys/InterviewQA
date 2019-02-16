package com.zys.qa.ui.search

import com.lookbi.baselibrary.base.IBasePresenter
import com.lookbi.baselibrary.base.IBaseView
import com.zys.qa.bean.QAList

class SearchContract {
    interface IView : IBaseView {
        fun searchSuccess(list: List<QAList.QA>)

    }

    interface IPresenter : IBasePresenter {
        fun search(keyword: String)
    }
}