package com.lookbi.baselibrary.utils

object MapTranslateUtils{

    /**
     * 坐标转换，腾讯地图（火星坐标）转换成百度地图坐标
     *
     * @param lat 腾讯纬度
     * @param lon 腾讯经度
     * @return 返回结果：经度,纬度
     */
    fun map_hx2bd(lat: Double, lon: Double): DoubleArray {
        val bd_lat: Double
        val bd_lon: Double
        val x_pi = 3.14159265358979324 * 3000.0 / 180.0
        val z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * x_pi)
        val theta = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * x_pi)
        bd_lon = z * Math.cos(theta) + 0.0065
        bd_lat = z * Math.sin(theta) + 0.006
//        System.out.println("bd_lat:" + bd_lat);
        //        System.out.println("bd_lon:" + bd_lon);
        return doubleArrayOf(bd_lat, bd_lon)
    }


    /**
     * 坐标转换，百度地图坐标转换成腾讯地图坐标
     *
     * @param lat 百度坐标纬度
     * @param lon 百度坐标经度
     * @return 返回结果：纬度,经度
     */
    fun map_bd2hx(lat: Double, lon: Double): DoubleArray {
        val tx_lat: Double
        val tx_lon: Double
        val x_pi = 3.14159265358979324
        val x = lon - 0.0065
        val y = lat - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi)
        tx_lon = z * Math.cos(theta)
        tx_lat = z * Math.sin(theta)

        return doubleArrayOf(tx_lat, tx_lon)
    }

    fun map_bd2gd(lat: Double, lon: Double): DoubleArray {
        val x_pi = 3.14159265358979324 * 3000.0 / 180.0
        val x = lon - 0.0065
        val y = lat - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi)
        val tempLon = z * Math.cos(theta)
        val tempLat = z * Math.sin(theta)
        return doubleArrayOf(tempLat, tempLon)
    }

}