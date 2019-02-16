package com.zys.qa.utils

import android.content.Context
import android.widget.Toast
import com.hjq.toast.ToastUtils

class ToastUtil {
    companion object {
        internal var mToast: Toast? = null

        fun show(mContext: Context, msg: String) {
            if (mToast == null) {
                mToast = ToastUtils.getToast()
            }
            mToast!!.setText(msg)
            mToast!!.show()
        }

        fun show(msg: String) {
            if (mToast == null) {
                mToast = ToastUtils.getToast()
            }
            mToast!!.setText(msg)
            mToast!!.show()
        }

        fun showNoDev() {
            if (mToast == null) {
                mToast = ToastUtils.getToast()
            }
            mToast!!.setText("该模块开发中")
            mToast!!.show()
        }
    }
}