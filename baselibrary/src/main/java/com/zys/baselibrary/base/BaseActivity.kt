package com.zys.baselibrary.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.zys.baselibrary.R
import com.zys.baselibrary.event.EventBusUtil
import com.zys.baselibrary.utils.ActivityManager
import com.zys.baselibrary.utils.DialogUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {
    protected var mImmersionBar: ImmersionBar? = null
    protected val ACCESS_FINE_LOCATION_REQUEST_CODE = 0x11
    protected val READ_PHONE_STATE_REQUEST_CODE = 0x12
    protected val CAMERA_REQUEST_CODE = 0x13
    protected val CALL_PHONE_REQUEST_CODE = 0x14
    protected val READ_EXTERNAL_STORAGE_REQUEST_CODE = 0x15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //将当前activity添加进入管理栈
        ActivityManager.instance?.addActivity(this)
        if (bindLayout() != 0) {
            setContentView(bindLayout())
        }
        mImmersionBar = ImmersionBar.with(this).keyboardEnable(true)
        setStatus()
        initView()
        initData(savedInstanceState)
    }

    private fun setStatus() {
        if (getFristTopViewId() != 0) {
            mImmersionBar!!.statusBarDarkFont(true, 0.2f).titleBar(getFristTopViewId())
                .init()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unDisposable()
        ActivityManager.instance?.removeActivity(this)
        mImmersionBar?.destroy()
        mImmersionBar = null
        if (EventBusUtil.isRegistered(this)) {
            EventBusUtil.unRegister(this)
        }
    }


    //白色背景 黑色返回键
    protected fun initBlackToolbar(index_toolbar: Toolbar) {
        index_toolbar.title = ""
        index_toolbar.setNavigationIcon(R.mipmap.ic_black_back)
        setSupportActionBar(index_toolbar)
        index_toolbar.setNavigationOnClickListener { finish() }
    }

    //深色背景 白色返回键
    protected fun initWhiteToolbar(index_toolbar: Toolbar) {
        index_toolbar.title = ""
        index_toolbar.setNavigationIcon(R.mipmap.ic_white_back)
        setSupportActionBar(index_toolbar)
        index_toolbar.setNavigationOnClickListener { finish() }
    }


    //得到当前界面的布局文件id(由子类实现)
    protected abstract fun bindLayout(): Int

    //沉浸式用到的布局id
    open protected fun getFristTopViewId(): Int {
        return 0;
    }

    protected abstract fun initView()


    protected abstract fun initData(savedInstanceState: Bundle?);

    //将此PresenterImpl中所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private var mCompositeDisposable: CompositeDisposable? = null

    /**
     * 添加Disposable
     *
     * @param subscription
     */
    fun addDisposable(subscription: Disposable) {
        //csb 如果解绑了的话添加 sp 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    fun unDisposable() {
        mCompositeDisposable?.dispose()
        mCompositeDisposable = null
    }


    fun startActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    fun startActivity(activity: Class<*>, bundle: Bundle) {
        val intent = Intent(this, activity)
        intent.putExtra("data", bundle)
        startActivity(intent)
    }

    /**
     * Activity跳转
     *
     * @param activity
     * @param action
     * @return
     */
    fun setIntents(activity: Class<*>, vararg action: String): Intent {
        val intent = Intent(this, activity)
        intent.putExtra("action", action)
        startActivity(intent)
        return intent
    }

    fun getArrayAction(): Array<String> {
        return intent.getStringArrayExtra("action")
    }

    fun startActivityAndClearTask(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun startActivityAndClearTop(activity: Class<*>) {
        val intent = Intent(this, activity)
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
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    ACCESS_FINE_LOCATION_REQUEST_CODE
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
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_REQUEST_CODE
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
            if (this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    READ_PHONE_STATE_REQUEST_CODE
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
            if (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    this,
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
            if (this.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                ActivityCompat.requestPermissions(
                    this,
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


    protected fun showLoading(msg: String = "") {
        DialogUtils.show(this, msg)
    }

    protected fun dismissLoading() {
        DialogUtils.dismiss()
    }

}