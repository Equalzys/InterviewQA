package com.zys.qa.update

import java.io.File

class UpdateCommon{
    companion object {

        val UPDATE_APP_NAME = "面试题"
        val SAVE_APP_NAME = "iqa_k_app.apk"
        val SAVE_APPPATCH_NAME = "iqa_k_app_patch.apk"

        val SAVE_APP_LOCATION = "/download/iqak"
        val SAVE_APPPATCH_LOCATION = "/download/iqakkpatch"

        val APP_FILE_NAME = "/sdcard" + SAVE_APP_LOCATION + File.separator + SAVE_APP_NAME
        val APPPATCH_FILE_NAME = "/sdcard" + SAVE_APPPATCH_LOCATION + File.separator + SAVE_APPPATCH_NAME
        val DOWNLOAD_APK_ID_PREFS = "download_apk_id_prefs"
    }
}