package com.lookbi.baselibrary.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyf.barlibrary.ImmersionBar
import com.gyf.barlibrary.ImmersionFragment
import com.gyf.barlibrary.SimpleImmersionFragment
import com.lookbi.baselibrary.event.EventBusUtil
import com.lookbi.baselibrary.utils.DialogUtils
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment: SimpleImmersionFragment() {
    protected var mContext: Context? = null
    protected var mActivity: Activity? = null
    protected var mRootView: View? = null
    protected var isVisibled: Boolean = false
    private var isPrepared: Boolean = false
    private var isFirst = true
    protected val ACCESS_FINE_LOCATION_REQUEST_CODE = 1
    protected val READ_PHONE_STATE_REQUEST_CODE = 2
    protected val CAMERA_REQUEST_CODE = 3
    protected val CALL_PHONE_REQUEST_CODE = 4
    protected val READ_EXTERNAL_STORAGE_REQUEST_CODE = 5

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisibled = true
            lazyLoad()
        } else {
            isVisibled = false
            onInvisibled()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = context
        mActivity = activity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(bindLayout(), container, false)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mRootView != null) initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
        lazyLoad()
    }

    protected fun lazyLoad() {
        if (!isPrepared || !isVisibled || !isFirst) {
            return
        }
        initData()
        isFirst = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (mRootView?.getParent() as ViewGroup).removeView(mRootView)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBusUtil.isRegistered(this)) {
            EventBusUtil.unRegister(this)
        }
    }
    //得到当前界面的布局文件id(由子类实现)
    protected abstract fun bindLayout(): Int

    protected open fun getFristTopViewId(): Int{
        return 0
    }

    abstract fun initView()

    abstract fun initData()

    protected fun onInvisibled() {

    }

    fun startActivity(activity: Class<*>) {
        val intent = Intent(mActivity, activity)
        startActivity(intent)
    }

    fun startActivity(activity: Class<*>, bundle: Bundle) {
        val intent = Intent(mActivity, activity)
        intent.putExtra("data", bundle)
        startActivity(intent)
    }


    fun startActivityAndClearTask(activity: Class<*>) {
        val intent = Intent(mActivity, activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        mActivity?.finish()
    }

    fun startActivityAndClearTop(activity: Class<*>) {
        val intent = Intent(mActivity, activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    /**
     * 检验 读取定位  权限
     *
     * @return
     */
    fun checkLocationPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION_REQUEST_CODE
                )

                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    /**
     * 检验 读取SD卡  权限
     *
     * @return
     */
    fun checkStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE_REQUEST_CODE
                )
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    /**
     * 检验 读取手机信息  权限
     *
     * @return
     */
    fun checkPhoneStatePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.READ_PHONE_STATE), READ_PHONE_STATE_REQUEST_CODE
                )
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    /**
     * 检验 请求相机 权限
     *
     * @return
     */
    fun checkCameraPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE
                )
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    /**
     * 检验 请求打电话 权限
     *
     * @return
     */
    fun checkCallPhonePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_REQUEST_CODE
                )
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }


    override fun initImmersionBar() {
        if (getFristTopViewId() != 0) {
            ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .titleBar(getFristTopViewId())
                .keyboardEnable(true)
                .init()
        }
    }

    protected fun showLoading(msg: String = "") {
        DialogUtils.show(activity, msg)
    }

    protected fun dismissLoading() {
        DialogUtils.dismiss()
    }
}