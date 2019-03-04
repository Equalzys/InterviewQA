package com.zys.qa.ui.main.home

import com.zys.baselibrary.base.IBasePresenter
import com.zys.baselibrary.base.IBaseView
import com.zys.qa.bean.QAList

class HomeContract{
     interface IView : IBaseView {

        fun getQAListSuccess(list: List<QAList.QA>)


    }

     interface IPresenter : IBasePresenter {
        fun getQAList(from: Int)
    }
}