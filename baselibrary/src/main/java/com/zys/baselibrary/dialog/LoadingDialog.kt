package com.zys.baselibrary.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import com.zys.baselibrary.R

class LoadingDialog(context: Context, msg: String) : Dialog(context, R.style.custom_dialog) {
    var msg: String? = msg;
    var tv_msg: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading_view)
        setCanceledOnTouchOutside(false)
        initView()
    }

    private fun initView() {
        tv_msg = findViewById(R.id.tv_msg)
        if (!TextUtils.isEmpty(msg)) {
            tv_msg?.text = msg;
        }
    }

    fun dismissLoading() {
        dismiss()
    }
}