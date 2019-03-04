package com.zys.qa.ui.main.mine

import com.zys.baselibrary.base.BaseFragment
import com.zys.baselibrary.utils.LogUtil
import com.zys.baselibrary.utils.UtilTools
import com.zys.qa.R
import com.zys.qa.ui.feedback.FeedBackActivity
import com.zys.qa.ui.h5.BaseH5Activity
import com.zys.qa.update.ApkUpdate
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.support.v4.startActivity

class MineFragment : BaseFragment() {

    internal var apkUpdate: ApkUpdate? = null

    override fun bindLayout(): Int {
        return R.layout.fragment_mine
    }

    override fun getFristTopViewId(): Int {
        return R.id.view_mine_top
    }


    override fun initView() {

        rl_version.setOnClickListener {
            if (apkUpdate == null) {
                apkUpdate = ApkUpdate(activity!!)
            }
            apkUpdate!!.getNewApkInfo(true)
        }
        rl_about_us.setOnClickListener {
            startActivity<BaseH5Activity>(
                "title" to "关于我"
            )
        }
        rl_feedback.setOnClickListener {
            startActivity<FeedBackActivity>()
        }
    }


    override fun initData() {
        tv_version.text = ("V" + UtilTools.getVersion(activity))
    }


}