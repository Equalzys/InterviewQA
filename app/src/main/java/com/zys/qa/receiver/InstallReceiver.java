package com.zys.qa.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;
import com.zys.qa.update.ApkUpdate;
import com.zys.qa.update.UpdateCommon;


import java.io.File;


/**
 * 安装下载接收器
 */

public class InstallReceiver extends BroadcastReceiver {

    private static final String TAG = "InstallReceiver";


    // 安装下载接收器
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            installApk(context);
        }
    }

    // 安装Apk
    private void installApk(Context context) {

        try {
            String filePath = UpdateCommon.APP_FILE_NAME;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() +
                        ".fileprovider", new File(filePath));
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android" +
                        ".package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            ApkUpdate.isDownLoad = false;
        } catch (Exception e) {
            Log.e(TAG, "安装失败");
            e.printStackTrace();
        }

    }
}