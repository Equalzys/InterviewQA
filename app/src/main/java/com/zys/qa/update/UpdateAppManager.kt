package com.zys.qa.update

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build

class UpdateAppManager {
    companion object {
        private val TAG = "UpdateAppManager"
//        var id: Long = 0
        var manager: DownloadManager? = null


        /**
         * 下载Apk, 并设置Apk地址,
         * 默认位置: /storage/sdcard0/Download
         *
         * @param context    上下文
         * @param downLoadUrl 下载地址
         * @param infoName   通知名称
         * @param description  通知描述
         */
        fun downloadApk(
            context: Context,
            downLoadUrl: String,
            description: String,
            infoName: String
        ) {

            if (!isDownloadManagerAvailable()) {
                return
            }

            var appUrl: String? = downLoadUrl
            if (appUrl == null || appUrl.isEmpty()) {

                return
            }
            appUrl = appUrl.trim { it <= ' ' } // 去掉首尾空格
            if (!appUrl.startsWith("http")) {
                appUrl = "http://$appUrl" // 添加Http信息
            }


            val request: DownloadManager.Request
            try {
                request = DownloadManager.Request(Uri.parse(appUrl))
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }

            request.setTitle(infoName)
            request.setDescription(description)

            //在通知栏显示下载进度
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }

            //sdcard目录下的download文件夹
            request.setDestinationInExternalPublicDir(UpdateCommon.SAVE_APP_LOCATION, UpdateCommon.SAVE_APP_NAME)

            val appContext = context.applicationContext
            manager = appContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager?.enqueue(request)

        }

        // 最小版本号大于9
        private fun isDownloadManagerAvailable(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
        }

    }
}