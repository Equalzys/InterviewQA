package com.zys.baselibrary.utils;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * isDebug上线要改为flase,防止抓到日志
 */

public class LogUtil {
    private static boolean isDebug = true;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void e(String tag, String msg) {
        if (isDebug) {
            showLargeLog("TAG--LogUtil", 3 * 1024, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            showLargeLog("TAG--LogUtil", 3 * 1024, msg);
        }
    }

    public static void e(String tag, int msg) {
        if (isDebug) {
            showLargeLog(tag, 3 * 1024, msg + "");
        }
    }

    public static void e(int msg) {
        if (isDebug) {
            Log.e("TAG--LogUtil", msg + "");
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d("TAG--LogUtil", msg);
        }
    }

    public static void e(Object object, String msg) {
        if (isDebug) {
            Log.e(object.getClass().getSimpleName(), msg);
        }
    }

    private static void printLine(String tag, boolean isTop) {
        if (isDebug) {
            if (isTop) {
                e(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
            } else {
                e(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
            }
        }

    }

    public static void printJson(String tag, String msg, String headString) {
        String message;
        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//返回格式化的json字符串，数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }
        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            {
                e(tag, "║ " + line);
            }
        }
        printLine(tag, false);
    }

    private static void showLargeLog(String tag, int showLength,String logContent) {
        if (logContent.length() > showLength) {
            String show = logContent.substring(0, showLength);
            Log.e(tag, show);
            /*剩余的字符串如果大于规定显示的长度，截取剩余字符串进行递归，否则打印结果*/
            if ((logContent.length() - showLength) > showLength) {
                String partLog = logContent.substring(showLength, logContent.length());
                showLargeLog(partLog, showLength, tag);
            } else {
                String printLog = logContent.substring(showLength, logContent.length());
                Log.e(tag, printLog);
            }

        } else {
            Log.e(tag, logContent);
        }
    }
}
