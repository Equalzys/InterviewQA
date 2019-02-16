package com.zys.qa

import android.app.Activity
import android.content.Context
import android.support.multidex.MultiDexApplication
import android.text.TextUtils
import com.hjq.toast.ToastUtils
import com.hjq.toast.style.ToastQQStyle
import com.lookbi.baselibrary.utils.LogUtil
import com.lookbi.baselibrary.utils.SPUtil
import com.lookbi.baselibrary.utils.TimeUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import java.util.*
import kotlin.properties.Delegates

open class AppContext : MultiDexApplication() {
    companion object {
        @JvmStatic
        var instance: AppContext by Delegates.notNull()

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
                ClassicsHeader(context).setSpinnerStyle(
                    SpinnerStyle.Translate
                )
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                ClassicsFooter(context).setSpinnerStyle(
                    SpinnerStyle.Translate
                )
            }
        }

    }

    var context: Context by Delegates.notNull()

    var baseUrl: String? = ""

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        baseUrl = context.getResources().getString(R.string.baseUrl)

        ToastUtils.init(this)
        ToastUtils.initStyle(ToastQQStyle())
    }


}