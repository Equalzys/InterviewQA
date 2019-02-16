package com.zys.qa.ui.qa_info

import com.lookbi.baselibrary.base.IBasePresenter
import com.lookbi.baselibrary.base.IBaseView
import com.zys.qa.bean.QAList

class QAInfoContract{
     interface IView : IBaseView {

        fun getQAInfoSuccess(info: QAList.QA?)


    }

     interface IPresenter : IBasePresenter {
        fun getQAInfo(qaid: Int)
    }
}