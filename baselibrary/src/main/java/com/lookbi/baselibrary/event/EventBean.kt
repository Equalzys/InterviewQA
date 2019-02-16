package com.lookbi.baselibrary.event

import java.io.Serializable

open class EventBean(event: Int, obj: Any? = null) : Serializable {
    var event: Int? = event
    var data: Any? = obj
}