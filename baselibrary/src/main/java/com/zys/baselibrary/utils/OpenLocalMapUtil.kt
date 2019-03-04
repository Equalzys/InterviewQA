package com.zys.baselibrary.utils

import java.io.File

object OpenLocalMapUtil{

    /**
     * 地图应用是否安装
     * @return
     */
    fun isGdMapInstalled(): Boolean {
        return isInstallPackage("com.autonavi.minimap")
    }

    fun isBaiduMapInstalled(): Boolean {
        return isInstallPackage("com.baidu.BaiduMap")
    }

    private fun isInstallPackage(packageName: String): Boolean {
        return File("/data/data/$packageName").exists()
    }

    /**
     * 获取打开百度地图应用uri [http://lbsyun.baidu.com/index.php?title=uri/api/android]
     * @param originLat
     * @param originLon
     * @param desLat
     * @param desLon
     * @return
     */
    fun getBaiduMapUri(
        originLat: String,
        originLon: String,
        originName: String,
        desLat: String,
        desLon: String,
        destination: String,
        region: String,
        src: String
    ): String {
        val uri = "intent://map/direction?origin=latlng:%1\$s,%2\$s|name:%3\$s" +
                "&destination=latlng:%4\$s,%5\$s|name:%6\$s&mode=driving&region=%7\$s&src=%8\$s#Intent;" +
                "scheme=bdapp;package=com.baidu.BaiduMap;end"

        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, src)
    }

    /**
     * 获取打开高德地图应用uri
     */
    fun getGdMapUri(
        appName: String,
        slat: String,
        slon: String,
        sname: String,
        dlat: String,
        dlon: String,
        dname: String
    ): String {
        val uri =
            "androidamap://route?sourceApplication=%1\$s&slat=%2\$s&slon=%3\$s&sname=%4\$s&dlat=%5\$s&dlon=%6\$s&dname=%7\$s&dev=0&m=0&t=2"
        return String.format(uri, appName, slat, slon, sname, dlat, dlon, dname)
    }


    /**
     * 网页版百度地图 有经纬度
     * @param originLat
     * @param originLon
     * @param originName ->注：必填
     * @param desLat
     * @param desLon
     * @param destination
     * @param region : 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。-->注：必填，不填不会显示导航路线
     * @param appName
     * @return
     */
    fun getWebBaiduMapUri(
        originLat: String,
        originLon: String,
        originName: String,
        desLat: String,
        desLon: String,
        destination: String,
        region: String,
        appName: String
    ): String {
        val uri = "http://api.map.baidu.com/direction?origin=latlng:%1\$s,%2\$s|name:%3\$s" +
                "&destination=latlng:%4\$s,%5\$s|name:%6\$s&mode=driving&region=%7\$s&output=html" +
                "&src=%8\$s"
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, appName)
    }


    /**
     * 百度地图定位经纬度转高德经纬度
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    fun bdToGaoDe(bd_lat: Double, bd_lon: Double): DoubleArray {
        val gd_lat_lon = DoubleArray(2)
        val PI = 3.14159265358979324 * 3000.0 / 180.0
        val x = bd_lon - 0.0065
        val y = bd_lat - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI)
        gd_lat_lon[0] = z * Math.cos(theta)
        gd_lat_lon[1] = z * Math.sin(theta)
        return gd_lat_lon
    }

    /**
     * 高德地图定位经纬度转百度经纬度
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    fun aoDeToBaidu(gd_lon: Double, gd_lat: Double): DoubleArray {
        val bd_lat_lon = DoubleArray(2)
        val PI = 3.14159265358979324 * 3000.0 / 180.0
        val z = Math.sqrt(gd_lon * gd_lon + gd_lat * gd_lat) + 0.00002 * Math.sin(gd_lat * PI)
        val theta = Math.atan2(gd_lat, gd_lon) + 0.000003 * Math.cos(gd_lon * PI)
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006
        return bd_lat_lon
    }
}