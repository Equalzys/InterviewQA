package com.lookbi.baselibrary.bean

import java.io.Serializable


class BaseBean<T> : Serializable {
    var err: String? = null
    var status: Int = 0
    var data: T? = null
    override fun toString(): String {
        return "BaseBean(err=$err, status=$status, data=$data)"
    }


}