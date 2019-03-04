package com.zys.baselibrary.event

import org.greenrobot.eventbus.EventBus

object EventBusUtil {


    fun post(event: Int, obj: Any? = null) {
        EventBus.getDefault().post(EventBean(event, obj))
    }

    fun post(event: Int) {
        EventBus.getDefault().post(EventBean(event, null))
    }

    fun register(obj: Any) {
        EventBus.getDefault().register(obj)
    }

    fun unRegister(obj: Any) {
        if (isRegistered(obj)) {
            EventBus.getDefault().unregister(obj)
        }
    }

    fun isRegistered(obj: Any): Boolean {
        return EventBus.getDefault().isRegistered(obj)
    }
}