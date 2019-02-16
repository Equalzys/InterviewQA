package com.lookbi.baselibrary.bean

import java.io.Serializable

class NewApkInfo : Serializable {
     var newApkUrl: String? = null
     var patchUrl: String? = null
     var apkVersion: String? = null
     var patchVersion: String? = null
     var updateMessage: String? = null
    override fun toString(): String {
        return "NewApkInfo(newApkUrl=$newApkUrl, patchUrl=$patchUrl, apkVersion=$apkVersion, patchVersion=$patchVersion, updateMessage=$updateMessage)"
    }


}