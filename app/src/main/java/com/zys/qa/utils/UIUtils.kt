package com.zys.qa.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import com.zys.qa.AppContext

class UIUtils{

    companion object {
        private val TAG = "UIUtils-TAG"

        var screenWidth: Int = 0
        var screenHeight: Int = 0
        var screenMin: Int = 0// 宽高中，小的一边
        var screenMax: Int = 0// 宽高中，较大的值
        var density: Float = 0.toFloat()
        var scaleDensity: Float = 0.toFloat()
        var xdpi: Float = 0.toFloat()
        var ydpi: Float = 0.toFloat()
        var densityDpi: Int = 0
        var statusbarheight: Int = 0
        var navbarheight: Int = 0
        /**
         * 得到上下文
         *
         * @return
         */
        fun getContext(): Context {
            return AppContext.instance.context
        }

        /**
         * 得到resources对象
         *
         * @return
         */
        fun getResource(): Resources {
            return getContext().resources
        }

        /**
         * 得到string.xml中的字符串
         *
         * @param resId
         * @return
         */
        fun getString(resId: Int): String {
            return getResource().getString(resId)
        }

        /**
         * 得到string.xml中的字符串，带点位符
         *
         * @return
         */
        fun getString(id: Int, vararg formatArgs: Any): String {
            return getResource().getString(id, *formatArgs)
        }

        /**
         * 得到string.xml中和字符串数组
         *
         * @param resId
         * @return
         */
        fun getStringArr(resId: Int): Array<String> {
            return getResource().getStringArray(resId)
        }

        /**
         * 得到colors.xml中的颜色
         *
         * @param colorId
         * @return
         */
        fun getColor(colorId: Int): Int {
            return getResource().getColor(colorId)
        }

        /**
         * 得到应用程序的包名
         *
         * @return
         */
        fun getPackageName(): String {
            return getContext().packageName
        }


        /**
         * dip-->px
         */
        fun dip2Px(dip: Int): Int {
            // px/dip = density;
            // density = dpi/160
            // 320*480 density = 1 1px = 1dp
            // 1280*720 density = 2 2px = 1dp

            val density = getResource().displayMetrics.density
            return (dip * density + 0.5f).toInt()
        }

        /**
         * px-->dip
         */
        fun px2dip(px: Int): Int {

            val density = getResource().displayMetrics.density
            return (px / density + 0.5f).toInt()
        }

        /**
         * sp-->px
         */
        fun sp2px(sp: Int): Int {
            return (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), getResource()
                    .displayMetrics
            ) + 0.5f).toInt()
        }


        fun getDisplayWidth(): Int {
            if (screenWidth == 0) {
                GetInfo(UIUtils.getContext())
            }
            return screenWidth
        }

        fun getDisplayHeight(): Int {
            if (screenHeight == 0) {
                GetInfo(UIUtils.getContext())
            }
            return screenHeight
        }

        fun GetInfo(context: Context?) {
            if (null == context) {
                return
            }
            val dm = context.applicationContext.resources.displayMetrics
            screenWidth = dm.widthPixels
            screenHeight = dm.heightPixels
            screenMin = if (screenWidth > screenHeight) screenHeight else screenWidth
            screenMax = if (screenWidth < screenHeight) screenHeight else screenWidth
            density = dm.density
            scaleDensity = dm.scaledDensity
            xdpi = dm.xdpi
            ydpi = dm.ydpi
            densityDpi = dm.densityDpi
            statusbarheight = getStatusBarHeight(context)
            navbarheight = getNavBarHeight(context)
            Log.d(
                TAG, "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + " density=" +
                        density
            )
        }

        fun getNavBarHeight(context: Context): Int {
            val resources = context.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceId > 0) {
                resources.getDimensionPixelSize(resourceId)
            } else 0
        }

        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            try {
                val resourceId = context.resources.getIdentifier(
                    "status_bar_height", "dimen",
                    "android"
                )
                if (resourceId > 0) {
                    result = context.resources.getDimensionPixelSize(resourceId)
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }

            return result
        }


        fun getScreenSize(context: Context, useDeviceSize: Boolean): IntArray {

            val size = IntArray(2)

            val w = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val d = w.defaultDisplay
            val metrics = DisplayMetrics()
            d.getMetrics(metrics)
            // since SDK_INT = 1;
            var widthPixels = metrics.widthPixels
            var heightPixels = metrics.heightPixels

            if (!useDeviceSize) {
                size[0] = widthPixels
                size[1] = heightPixels - getStatusBarHeight(context)

                return size
            }

            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
                try {
                    widthPixels = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
                    heightPixels = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
                } catch (ignored: Exception) {
                }

            }
            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= 17) {
                try {
                    val realSize = Point()
                    Display::class.java.getMethod("getRealSize", Point::class.java).invoke(d, realSize)
                    widthPixels = realSize.x
                    heightPixels = realSize.y
                } catch (ignored: Exception) {
                }

            }
            size[0] = widthPixels
            size[1] = heightPixels
            return size
        }


    }
}