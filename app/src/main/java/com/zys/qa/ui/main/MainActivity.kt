package com.zys.qa.ui.main

import android.os.Bundle
import android.os.Process
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.KeyEvent
import android.view.View
import com.lookbi.baselibrary.base.BaseActivity
import com.lookbi.baselibrary.utils.ActivityManager
import com.zys.qa.utils.ToastUtil
import com.zys.qa.utils.UIUtils
import com.zys.qa.R
import com.zys.qa.ui.main.home.HomeFragment
import com.zys.qa.ui.main.mine.MineFragment
import com.zys.qa.update.ApkUpdate
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class MainActivity  : BaseActivity(), View.OnClickListener {


    private var mFragmentList: MutableList<Fragment>? = null
    private var mFragmentAdapter: FragmentPagerAdapter? = null
    internal var lastPosition = 0
    private var exitTime: Long = 0
    internal var apkUpdate: ApkUpdate?=null

    override fun bindLayout(): Int {
        return R.layout.activity_main;
    }

    override fun initView() {
        mImmersionBar?.init()
        initFragment()
        initViewPage()
        ll_tab1.setOnClickListener(this)
        ll_tab2.setOnClickListener(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        apkUpdate = ApkUpdate(this)
        apkUpdate!!.getNewApkInfo(false)
    }


    fun initFragment() {
        mFragmentList = ArrayList()
        mFragmentList!!.add(HomeFragment())
        mFragmentList!!.add(MineFragment())
    }


    private fun initViewPage() {
        val fm = supportFragmentManager
        mFragmentAdapter = object : FragmentPagerAdapter(fm) {
            override fun getCount(): Int {
                return if (mFragmentList == null) 0 else mFragmentList!!.size
            }

            override fun getItem(position: Int): Fragment {
                return mFragmentList!![position]
            }
        }
        vp_main.adapter = mFragmentAdapter

    }

    /**
     * 返回键的监听
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtil.show("再按一次退出程序")
                //                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis()
            } else {
                ActivityManager.instance?.finishAllActivity()
                finish()
                Process.killProcess(Process.myPid())
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_tab1 -> if (lastPosition != 0) {
                setCheckPosition(0)
            }
            R.id.ll_tab2 -> if (lastPosition != 1) {
                setCheckPosition(1)
            }
        }
    }

    private fun setCheckPosition(i: Int) {
        lastPosition = i

        when (i) {
            0 -> {
                iv_icon1.setImageResource(R.mipmap.ic_home_1_s)
                iv_icon2.setImageResource(R.mipmap.ic_home_2_n)
                tv_text1.setTextColor(UIUtils.getColor(R.color.main_blue))
                tv_text2.setTextColor(UIUtils.getColor(R.color.color_999))
            }
            1 -> {
                iv_icon2.setImageResource(R.mipmap.ic_home_2_s)
                iv_icon1.setImageResource(R.mipmap.ic_home_1_n)
                tv_text2.setTextColor(UIUtils.getColor(R.color.main_blue))
                tv_text1.setTextColor(UIUtils.getColor(R.color.color_999))
            }

            else -> {
            }
        }
        vp_main.setCurrentItem(i, false)
    }



}
