package com.zys.qa.net

import com.zys.baselibrary.bean.BaseBean
import com.zys.baselibrary.bean.BaseBoolean
import com.zys.baselibrary.bean.NewApkInfo
import com.zys.qa.bean.BaseH5
import com.zys.qa.bean.QAList
import io.reactivex.Observable
import retrofit2.http.*
import java.util.HashMap

interface ApiService {
    //新版本检测更新
    @GET(HttpUrl.APKINFOJSONURL)
    abstract fun getNewApkInfo(): Observable<BaseBean<NewApkInfo>>

    //列表
    @GET(HttpUrl.QA_LIST)
    abstract fun getQAList(@QueryMap hashMap: HashMap<String, String>): Observable<BaseBean<QAList>>

    //搜索列表
    @FormUrlEncoded
    @POST(HttpUrl.QA_SEARCH)
    abstract fun search(@FieldMap hashMap: HashMap<String, String>): Observable<BaseBean<QAList>>

    //机器详情
    @GET(HttpUrl.QA_INFO)
    abstract fun getQAInfo(@QueryMap hashMap: HashMap<String, String>): Observable<BaseBean<QAList.QA>>

    //网站详情
    @GET(HttpUrl.ABOUT_US)
    abstract fun getAboutus(@Query("h5id") h5id: Int): Observable<BaseBean<BaseH5>>

    //问题反馈
    @FormUrlEncoded
    @POST(HttpUrl.V_ADD_FEEDBACK)
    abstract fun addFeedBack(@FieldMap hashMap: HashMap<String, String>): Observable<BaseBean<BaseBoolean>>

    //添加面试题
    @FormUrlEncoded
    @POST(HttpUrl.QA_ADD)
    abstract fun addQA(@FieldMap hashMap: HashMap<String, String>): Observable<BaseBean<BaseBoolean>>


}