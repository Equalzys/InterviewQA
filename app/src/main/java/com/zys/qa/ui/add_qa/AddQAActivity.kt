package com.zys.qa.ui.add_qa

import android.os.Bundle
import android.text.TextUtils
import com.zys.baselibrary.base.BaseMVPActivity
import com.zys.qa.R
import com.zys.qa.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_add_qa.*

class AddQAActivity : BaseMVPActivity<AddQAContract.IView, AddQAPresentImpl>(),
    AddQAContract.IView {


    override fun createPresenter(): AddQAPresentImpl {
        return AddQAPresentImpl(this)
    }

    override fun getFristTopViewId(): Int {
        return R.id.view_top
    }

    override fun bindLayout(): Int {
        return R.layout.activity_add_qa
    }

    override fun initView() {
        initWhiteToolbar(toolbar)
        tv_post.setOnClickListener {

            mPresenter?.postQA(
                et_title.text.toString(),
                et_info.text.toString()
            )
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun postQASuccess(isSuccess: Boolean?) {
        if (null == isSuccess) {
            ToastUtil.show("提交失败，请稍后再试")
        } else if (isSuccess) {
            ToastUtil.show("已提交审核")
            finish()
        } else {
            ToastUtil.show("提交失败，请稍后再试")
        }
    }


    override fun onHttpError(e: String) {
        ToastUtil.show(e)
    }

    override fun onNoData(code: Int) {
    }

    override fun onRequestEnd() {
    }

}
