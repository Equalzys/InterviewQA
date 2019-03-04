package com.zys.qa.ui.qa_info

import com.zys.baselibrary.base.IBasePresenter
import com.zys.baselibrary.base.IBaseView
import com.zys.qa.bean.QAList

class QAInfoContract{
     interface IView : IBaseView {

        fun getQAInfoSuccess(info: QAList.QA?)


    }

     interface IPresenter : IBasePresenter {
        fun getQAInfo(qaid: Int)
    }
}