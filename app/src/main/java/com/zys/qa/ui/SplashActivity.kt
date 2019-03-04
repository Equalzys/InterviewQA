package com.zys.qa.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.zys.baselibrary.base.BaseActivity
import com.zys.qa.R
import com.zys.qa.ui.main.MainActivity
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {


    private var handler: Handler? = null
    internal var isDestroy = false
    override fun bindLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {

    }


    override fun initData(savedInstanceState: Bundle?) {
//        checkPermission()
        next()
    }


    /**
     * 检查用户设备是否拥有读写SDCard权限
     */
    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                    0x1000
                )
            } else {
                next()
            }
        } else {
            next()
        }

    }

    private operator fun next() {
        handler = Handler()
        handler?.postDelayed(Runnable { toNextActivity() }, 1500)
    }


    private fun toNextActivity() {
        if (isDestroy) {
            return
        }
        isDestroy = true
        startActivity<MainActivity>()
        finish()
    }

    override fun onDestroy() {
        handler?.removeCallbacksAndMessages(null)
        super.onDestroy()
        handler = null
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 0x1000) {
            next()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
