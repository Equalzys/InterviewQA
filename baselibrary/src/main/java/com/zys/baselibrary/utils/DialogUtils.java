package com.zys.baselibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.zys.baselibrary.R;
import com.zys.baselibrary.dialog.LoadingDialog;
import com.zys.baselibrary.dialog.LoadingDialog;


/**
 * Created by ZYS on 2017/5/9.
 */

public class DialogUtils {
    static LoadingDialog mLoadingDialog;
    static Activity activity;

    public static void show(Activity mActivity, String msg) {
        if (mActivity.isFinishing()) {
            return;
        }
        if (mLoadingDialog != null) {
            dismiss();
        }
        mLoadingDialog = new LoadingDialog(mActivity, msg);
        mLoadingDialog.show();
        LogUtil.e("DialogUtils---show1----");
    }

    public static void show(Activity mActivity, String msg, DialogInterface.OnDismissListener
            listener) {
        if (mActivity.isFinishing()) {
            return;
        }
        if (activity != null) {
            if (activity == mActivity) {
                if (null != mLoadingDialog) {
                    if (mLoadingDialog.isShowing()) {
                        return;
                    }
                }
            }
        }
        activity = mActivity;
        if (null != mLoadingDialog) {
            dismiss();
        }
        mLoadingDialog = null;
        mLoadingDialog = new LoadingDialog(mActivity, msg);
        mLoadingDialog.show();
        if (listener != null) {
            mLoadingDialog.setOnDismissListener(listener);
        }
        LogUtil.e("DialogUtils---show2----");

    }

    public static void show(Activity mActivity, DialogInterface.OnDismissListener listener) {
        if (mActivity.isFinishing()) {
            return;
        }
        if (activity != null) {
            if (activity == mActivity) {
                if (null != mLoadingDialog) {
                    if (mLoadingDialog.isShowing()) {
                        return;
                    }
                }
            }
        }
        activity = mActivity;
        if (null != mLoadingDialog) {
            dismiss();
        }
        mLoadingDialog = new LoadingDialog(mActivity, "");
        mLoadingDialog.show();
        if (listener != null) {
            mLoadingDialog.setOnDismissListener(listener);
        }
        LogUtil.e("DialogUtils---show3----");

    }

    public static void dismiss() {
        if (null == mLoadingDialog) {
            return;
        }
        if (mLoadingDialog.isShowing()) {
            try {
                mLoadingDialog.dismissLoading();
                mLoadingDialog = null;
                activity = null;
            } catch (Exception e) {
                mLoadingDialog = null;
                activity = null;
            }
        }
        LogUtil.e("DialogUtils---dismiss---");

    }


    /**
     * 发现新版本提示框
     *
     * @param activity
     */
    public static Dialog showUpdateApkDialog(Activity activity, String version, String msg, final
    OnUpdateApkListener listener) {
        Dialog dialog = null;
        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.dialog_update_app, null);// 得到加载view
        final TextView tv_msg = (TextView) v.findViewById(R.id.tv_msg);
        TextView btn_cancle = (TextView) v.findViewById(R.id.btn_cancle);
        TextView btn_down = (TextView) v.findViewById(R.id.btn_down);
        TextView tv_code = (TextView) v.findViewById(R.id.tv_code);
        if (!TextUtils.isEmpty(version)) {
            tv_code.setText("版本更新 v" + version);
        }
        if (!TextUtils.isEmpty(msg)) {
            tv_msg.setText(msg);// 设置加载信息
        }
        dialog = new Dialog(activity, R.style.custom_dialog);// 创建自定义样式dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(v);
        final Dialog finalDialog = dialog;
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalDialog.isShowing()) {
                    if (listener != null) {
                        listener.onCancle();
                    }
                    finalDialog.dismiss();
                }
            }
        });
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalDialog.isShowing()) {
                    if (listener != null) {
                        listener.onDownload();
                    }
                    finalDialog.dismiss();
                }
            }
        });
        return finalDialog;
    }

    public static abstract class OnUpdateApkListener {
        /**
         * 取消
         */
        public abstract void onCancle();

        /**
         * 下载
         */
        public abstract void onDownload();

    }
}
