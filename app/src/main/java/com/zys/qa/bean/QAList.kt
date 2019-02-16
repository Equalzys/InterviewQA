package com.zys.qa.bean

import java.io.Serializable

class QAList : Serializable {
    var list: List<QA>? = null

    class QA : Serializable {
        var qaid: Int = 0
        var views: Int = 0
        var type: Int = 0
        var question: String? = null
        var create_time: String? = null
        var answer: String? = null
    }
}