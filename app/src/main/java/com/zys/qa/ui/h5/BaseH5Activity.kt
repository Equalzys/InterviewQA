package com.zys.qa.ui.h5
import android.os.Bundle
import android.text.TextUtils
import android.webkit.WebSettings
import com.lookbi.baselibrary.base.BaseMVPActivity
import com.zys.qa.R
import com.zys.qa.bean.BaseH5
import com.zys.qa.net.HttpUrl
import com.zys.qa.utils.ToastUtil
import kotlinx.android.synthetic.main.ac_h5.*

class BaseH5Activity : BaseMVPActivity<BaseH5Contract.IView, BaseH5PresentImpl>(), BaseH5Contract.IView {
    internal var mUrlHead = ("<html><head><meta name=\"viewport\" content=\"width=device-width, " +
            "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\"" +
            " />" +
            "<style>img{max-width:100% !important;height:auto !important;}</style>"
            + "<style>body{max-width:100% !important;}</style>" + "</head><body>"
            + "<base href=\"" + HttpUrl.BASEURL + "\"/>")

    override fun createPresenter(): BaseH5PresentImpl {
        return BaseH5PresentImpl(this)
    }

    override fun bindLayout(): Int {
        return R.layout.ac_h5
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

    }

    override fun initData(savedInstanceState: Bundle?) {
        val title = intent.getStringExtra("title")
        tv_title.text = title
        if (TextUtils.equals(title, "关于我")) {
            mPresenter?.getAboutUs()
        }
    }


    override fun getAboutUsSuccess(mData: BaseH5?) {
        if (!TextUtils.isEmpty(mData?.info)) {
            web_view.loadDataWithBaseURL(
                null, mUrlHead + mData?.info +
                        "</body></html>", "text/html", "utf-8", null
            )
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