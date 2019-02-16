package com.zys.qa.ui.feedback

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import com.lookbi.baselibrary.base.BaseMVPActivity
import com.lookbi.baselibrary.lisenter.OnItemClickListener
import com.zys.qa.R
import com.zys.qa.adapter.SearchAdapter
import com.zys.qa.ui.qa_info.QAInfoActivity
import com.zys.qa.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.startActivity

class FeedBackActivity : BaseMVPActivity<FeedBackContract.IView, FeedBackPresentImpl>(),
    FeedBackContract.IView {

    override fun createPresenter(): FeedBackPresentImpl {
        return FeedBackPresentImpl(this)
    }

    override fun getFristTopViewId(): Int {
        return R.id.view_top
    }

    override fun bindLayout(): Int {
        return R.layout.activity_feedback
    }

    override fun initView() {
        initWhiteToolbar(toolbar)
        tv_post.setOnClickListener {
            if (TextUtils.isEmpty(et_title.text.toString())) {
                ToastUtil.show("请输入问题标题")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_phone.text.toString())) {
                ToastUtil.show("请输入您的手机型号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_info.text.toString())) {
                ToastUtil.show("请输入问题详细描述")
                return@setOnClickListener
            }
            mPresenter?.postFB(
                et_title.text.toString(),
                et_phone.text.toString(),
                et_info.text.toString(),
                et_contact.text.toString()
            )
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun postFBSuccess(isSuccess: Boolean?) {
        if (null == isSuccess) {
            ToastUtil.show("提交失败，请稍后再试")
        } else
            if (isSuccess) {
                ToastUtil.show("感谢您的提交反馈")
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
