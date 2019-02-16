package com.lookbi.baselibrary.base

interface IBaseView {
    fun onHttpError(e: String)

    fun onNoData(code: Int)

    fun onRequestEnd()
}