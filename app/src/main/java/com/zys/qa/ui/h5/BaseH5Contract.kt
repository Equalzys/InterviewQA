package com.zys.qa.ui.h5

import com.lookbi.baselibrary.base.IBasePresenter
import com.lookbi.baselibrary.base.IBaseView
import com.zys.qa.bean.BaseH5

class BaseH5Contract{
    interface IView : IBaseView {

        fun getAboutUsSuccess(mData: BaseH5?)

    }

    interface IPresenter : IBasePresenter {

        fun getAboutUs()
    }
}
