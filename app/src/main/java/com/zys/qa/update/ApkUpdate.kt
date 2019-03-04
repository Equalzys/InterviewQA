package com.zys.qa.update

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.zys.baselibrary.bean.NewApkInfo
import com.zys.baselibrary.net.RxSchedulers
import com.zys.baselibrary.utils.DialogUtils
import com.zys.baselibrary.utils.LogUtil
import com.zys.baselibrary.utils.UtilTools
import com.zys.qa.net.Api
import com.zys.qa.net.BaseObserver
import com.zys.qa.utils.ToastUtil
import java.io.File

class ApkUpdate(mContext: Activity) {
    private var context: Activity = mContext

    private var apkurl: String? = null

    companion object {
        var isDownLoad = false
    }

    private var onUpdateLisenter: OnUpdateLisenter? = null
    private var isShowPoup = true

    fun getApkurl(): String? {
        return apkurl
    }

    interface OnUpdateLisenter {
        fun update(newVersion: String)
    }

    fun setOnUpdateLisenter(lisenter: OnUpdateLisenter) {
        onUpdateLisenter = lisenter
    }

    fun getNewApkInfo(isShowToast: Boolean, isShowPoupu: Boolean) {
        isShowPoup = isShowPoupu
        getNewApkInfo(isShowToast)
    }

    fun getNewApkInfo(isShowToast: Boolean) {// 检测更新
        if (isShowToast) {
            ToastUtil.show(context, "版本检测中....")
        }
        val mObserver = object : BaseObserver<NewApkInfo>(context, null) {
            override fun onSuccess(info: NewApkInfo?) {
                if (null != info) {
                    val newver = Integer.parseInt(
                        info!!.apkVersion?.trim()?.replace("\\.", "")
                    )
                    val ver = Integer.parseInt(
                        UtilTools.getVersion(context).trim().replace("\\.", "")
                    )
                    LogUtil.e("apk", "newver=$newver,ver=$ver")
                    if (newver > ver) {
                        LogUtil.e("下载apk")
                        if (isShowPoup) {
                            showDialog(
                                info!!.apkVersion, info!!.newApkUrl, info!!.updateMessage
                            )
                        }
                        if (onUpdateLisenter != null) {
                            onUpdateLisenter!!.update(info!!.apkVersion!!)
                        }
                        return
                    }
                    //                    if (newver == ver) {
                    //                        if (info.getPatchVersion() != null) {
                    //                            if (!TextUtils.isEmpty(info.getPatchVersion())) {
                    //                                if (TextUtils.isEmpty(SPUtil.getString(context,
                    //                                        "PatchVersion", ""))) {
                    //                                    LogUtil.e("下载补丁1");
                    //                                    //下载补丁文件
                    //                                    downLoadPatch(info.getPatchUrl(), info.getPatchVersion());
                    //                                    return;
                    //                                }
                    //                                int newPatchVersion = Integer.parseInt(info.getPatchVersion()
                    //                                        .trim().replaceAll(
                    //                                                "\\.", ""));
                    //                                int oldVersion = Integer.parseInt(SPUtil.getString(context,
                    //                                        "PatchVersion", "2.0.0.0").trim().replaceAll(
                    //                                        "\\.", ""));
                    //                                if (newPatchVersion > oldVersion) {
                    //                                    LogUtil.e("下载补丁2");
                    //                                    //下载补丁文件
                    //                                    downLoadPatch(info.getPatchUrl(), info.getPatchVersion());
                    //                                } else {
                    //                                    LogUtil.e("版本已最新1");
                    //                                    if (isShowToast) {
                    //                                        ToastUtil.show(context, "版本已最新");
                    //                                    }
                    //                                }
                    //                            } else {
                    //                                LogUtil.e("版本已最新2");
                    //
                    //                                if (isShowToast) {
                    //                                    ToastUtil.show(context, "版本已最新");
                    //                                }
                    //                            }
                    //                        } else {
                    //                            LogUtil.e("版本已最新3");
                    //
                    //                            if (isShowToast) {
                    //                                ToastUtil.show(context, "版本已最新");
                    //                            }
                    //                        }
                    //                        return;
                    //                    }
                    if (isShowToast) {
                        ToastUtil.show(context, "版本已最新")
                    }
                    LogUtil.e("版本已最新4")
                } else {
                    LogUtil.e("检测版本更新失败,为空")
                    if (isShowToast) {
                        ToastUtil.show(context, "版本已最新")
                    }
                    LogUtil.e("版本已最新5")
                }
            }

            override fun onError(e: String?) {
                LogUtil.e("e=$e")
                if (isShowToast) {
                    ToastUtil.show(context, "版本已最新")
                }
                LogUtil.e("版本已最新6")

            }

            override fun onEnd() {

            }
        }
        mObserver.isShowLoading = false
        Api.service.getNewApkInfo()
            .compose(RxSchedulers.compose(context))
            .subscribe(mObserver)
    }

    private fun showDialog(newVersion: String?, url: String?, msg: String?) {
        apkurl = url
        if (isDownLoad) {
            ToastUtil.show(context, "正在下载最新版本")
            return
        }
        DialogUtils.showUpdateApkDialog(context, newVersion, msg, object : DialogUtils.OnUpdateApkListener() {

            override fun onCancle() {

            }

            override fun onDownload() {
                checkSDCardPermission()
            }
        }).show()
    }

    /**
     * 检查用户设备是否拥有读写SDCard该权限
     */
    private fun checkSDCardPermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0x110
            )
        } else {
            downLoadApk()
        }
    }

    fun downLoadApk() {
        val midrname = "/sdcard" + UpdateCommon.SAVE_APP_LOCATION + File.separator
        UtilTools.isFoldersExists(midrname)

        val file = File(midrname)
        val files = file.listFiles()
        for (j in files!!.indices) {
            UtilTools.deleteFile(files[j].absolutePath)
        }
        if (!TextUtils.isEmpty(apkurl)) {
            UpdateAppManager.downloadApk(
                context, apkurl!!, "版本更新",
                UpdateCommon.UPDATE_APP_NAME)
            isDownLoad = true
        }

    }

//    private void downLoadPatch(String patchUrl, final String patchVersion) {
//        if (patchUrl == null) {
//            LogUtil.e("补丁为空");
//            return;
//        }
//        if (TextUtils.isEmpty(patchUrl)) {
//            LogUtil.e("补丁路径为空");
//            return;
//        }
//        Observer mObserver = new DownLoadObserver(context) {
//
//            @Override
//            protected void onSuccess(ResponseBody info) {
//                LogUtil.e("onSuccess");
//                if (info != null) {
//                    if (writeResponseBodyToDisk(info, patchVersion)) {
//
//                    } else {
//                        LogUtil.e("下载补丁失败");
//                    }
//                } else {
//                    LogUtil.e("info == null");
//                }
//
//            }
//
//            @Override
//            protected void onError(String e) {
//                LogUtil.e("e=" + e);
//
//            }
//
//            @Override
//            protected void onEnd() {
//                LogUtil.e("onEnd");
//            }
//        };
//        Api.getService().downloadFile(patchUrl)
//                .compose(RxSchedulers.compose(context))
//                .subscribe(mObserver);
//    }
//
//    private static boolean writeResponseBodyToDisk(ResponseBody body, String patchVersion) {
//
//        String midrname = "/sdcard" + UpdateCommon.SAVE_APPPATCH_LOCATION + File.separator;
//        UtilTools.isFoldersExists(midrname);
//        File file = new File(midrname);
//        File[] files = file.listFiles();
//        for (int j = 0; j < files.length; j++) {
//            UtilTools.deleteFile(files[j].getAbsolutePath());
//        }
//        try {
//            File futureStudioIconFile = new File(UpdateCommon.APPPATCH_FILE_NAME);
//
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//
//            try {
//                byte[] fileReader = new byte[1024];
//
//                long fileSize = body.contentLength();
//                long fileSizeDownloaded = 0;
//
//                inputStream = body.byteStream();
//                outputStream = new FileOutputStream(futureStudioIconFile);
//
//                while (true) {
//                    int read = inputStream.read(fileReader);
//
//                    if (read == -1) {
//                        break;
//                    }
//
//                    outputStream.write(fileReader, 0, read);
//
//                    fileSizeDownloaded += read;
//
//                    LogUtil.e("writeResponseBodyToDisk--", "file download: " + fileSizeDownloaded
//                            + "" +
//                            " of " + fileSize);
//                }
//                outputStream.flush();
//                TinkerInstaller.onReceiveUpgradePatch(context, UpdateCommon.APPPATCH_FILE_NAME);
//                SPUtil.setString(context, "PatchVersion", patchVersion);
//                return true;
//            } catch (IOException e) {
//                return false;
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            }
//        } catch (IOException e) {
//            return false;
//        }
//    }
}