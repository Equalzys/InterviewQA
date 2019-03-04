package com.zys.baselibrary.net;

import android.net.ParseException;
import com.alibaba.fastjson.JSONException;
import com.zys.baselibrary.utils.LogUtil;
import com.zys.baselibrary.utils.LogUtil;
import retrofit2.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/*
 * 描述:     TODO 异常抛出帮助类
 */
public class ExceptionHelper {

    private static String TAG = "TAG-ExceptionHelper";

    public static String handleException(Throwable e) {
        e.printStackTrace();
        String error;
        if (e instanceof SocketTimeoutException) {//网络超时
            LogUtil.e(TAG, "网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof HttpException) {//网络超时
            LogUtil.e(TAG, "网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof ConnectException) { //均视为网络错误
            LogUtil.e(TAG, "网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof JSONException
                || e instanceof ParseException) {   //均视为解析错误
            LogUtil.e(TAG, "数据解析异常: " + e.getMessage());
            error = "数据解析异常";
        } else if (e instanceof RuntimeException) {//服务器返回的错误信息
            error = e.getCause().getMessage();
        } else if (e instanceof UnknownHostException) {
            LogUtil.e(TAG, "网络连接异常: " + e.getMessage());
            error = "网络连接异常";
        } else if (e instanceof IllegalArgumentException) {
            LogUtil.e(TAG, "参数类型不合法: " + e.getMessage());
            error = "参数类型不合法";
        } else {//未知错误
            try {
                LogUtil.e(TAG, "错误: " + e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
                LogUtil.e(TAG, "未知错误Debug调试 ");
            }
            error = "错误";
        }
        return error;
    }

}