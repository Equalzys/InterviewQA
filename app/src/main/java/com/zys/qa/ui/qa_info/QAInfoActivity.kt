package com.zys.qa.ui.qa_info

import android.os.Bundle
import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebSettings
import com.lookbi.baselibrary.base.BaseMVPActivity
import com.zys.qa.R
import com.zys.qa.bean.QAList
import com.zys.qa.net.HttpUrl
import com.zys.qa.utils.ToastUtil
import kotlinx.android.synthetic.main.ac_qa_info.*
import java.util.ArrayList

class QAInfoActivity : BaseMVPActivity<QAInfoContract.IView, QAInfoPresentImpl>(), QAInfoContract.IView {


    internal var mUrlHead = ("<html><head><meta name=\"viewport\" content=\"width=device-width, " +
            "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\"" +
            " />" +
            "<style>img{max-width:100% !important;height:auto !important;}</style>"
            + "<style>body{max-width:100% !important;}</style>" + "</head><body>"
            + "<base href=\"" + HttpUrl.BASEURL + "\"/>")
    internal var mList: MutableList<QAList.QA> = ArrayList<QAList.QA>()
    var position: Int = 0
    var isFromSearch: Boolean = false
    override fun createPresenter(): QAInfoPresentImpl {
        return QAInfoPresentImpl(this)
    }

    override fun bindLayout(): Int {
        return R.layout.ac_qa_info
    }

    override fun getFristTopViewId(): Int {
        return R.id.view_top
    }

    override fun initView() {
        initWhiteToolbar(toolbar)

        val webSettings = web_view.getSettings()
        webSettings.javaScriptEnabled = true//启用js功能
        webSettings.allowFileAccess = true
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.setAppCacheEnabled(true)
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        // 设置加载进来的页面自适应手机屏幕
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        tv_next.setOnClickListener {
            if (position < mList.size - 1) {
                position++
                getData(mList[position].qaid)
                if (position >= mList.size - 1) {
                    tv_next.visibility = GONE
                }
            }
        }
    }


    override fun initData(savedInstanceState: Bundle?) {
        isFromSearch = intent.getBooleanExtra("FromSearch", false)
        if (isFromSearch) {
            tv_next.visibility = GONE
            val qa = intent.getSerializableExtra("QA") as QAList.QA
            getData(qa.qaid)
        } else {
            mList = intent.getSerializableExtra("QAList") as ArrayList<QAList.QA>
            position = intent.getIntExtra("position", 0)
            if (position >= mList.size - 1) {
                tv_next.visibility = GONE
            } else {
                tv_next.visibility = VISIBLE
            }
            getData(mList[position].qaid)
        }


    }

    private fun getData(id: Int) {
        mPresenter?.getQAInfo(id)
    }


    override fun onHttpError(e: String) {
        ToastUtil.show(e)
    }

    override fun onNoData(code: Int) {
    }

    override fun onRequestEnd() {
    }

    override fun getQAInfoSuccess(info: QAList.QA?) {
        tv_q.text = info?.question
        tv_time.text = info?.create_time
        tv_views.text = ("浏览次数：" + info?.views.toString())
        if (!TextUtils.isEmpty(info?.answer)) {
            web_view.loadDataWithBaseURL(
                null, mUrlHead + info?.answer +
                        "</body></html>", "text/html", "utf-8", null
            )
        }
    }


}