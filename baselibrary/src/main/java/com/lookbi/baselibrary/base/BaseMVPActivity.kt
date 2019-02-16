package com.lookbi.baselibrary.base

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
import com.lookbi.baselibrary.R
import com.lookbi.baselibrary.utils.ActivityManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

abstract class BaseMVPActivity<V : IBaseView, P : BasePresenterImpl<V>> : BaseActivity() {
    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        super.onCreate(savedInstanceState)

    }
    override fun onDestroy() {
        mPresenter?.detachView()
        mPresenter = null
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.setShowLoading(false)
    }
    protected abstract fun createPresenter(): P


}