package com.zys.qa.ui.h5

import com.zys.baselibrary.base.IBasePresenter
import com.zys.baselibrary.base.IBaseView
import com.zys.qa.bean.BaseH5

class BaseH5Contract{
    interface IView : IBaseView {

        fun getAboutUsSuccess(mData: BaseH5?)

    }

    interface IPresenter : IBasePresenter {

        fun getAboutUs()
    }
}
