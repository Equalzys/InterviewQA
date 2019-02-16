package com.zys.qa.ui.main.home

import com.lookbi.baselibrary.base.IBasePresenter
import com.lookbi.baselibrary.base.IBaseView
import com.zys.qa.bean.QAList

class HomeContract{
     interface IView : IBaseView {

        fun getQAListSuccess(list: List<QAList.QA>)


    }

     interface IPresenter : IBasePresenter {
        fun getQAList(from: Int)
    }
}