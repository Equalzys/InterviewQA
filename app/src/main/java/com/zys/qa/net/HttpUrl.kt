package com.zys.qa.net

import com.zys.qa.AppContext

class HttpUrl {

    companion object {
        internal val BASEURL: String? = AppContext.instance.baseUrl
        //apk更新信息
        const val APKINFOJSONURL: String = "AppUpdateInfo.txt"
        //列表
        const val QA_LIST = "qa/list"
        //详情
        const val QA_INFO = "qa/info"
        //模糊搜索
        const val QA_SEARCH = "qa/search"
        //问题反馈
        const val V_ADD_FEEDBACK = "fb/add"
        //关于我们
        const val ABOUT_US = "h5/info"
        //添加面试题
        const val QA_ADD = "qa/add"

    }
}