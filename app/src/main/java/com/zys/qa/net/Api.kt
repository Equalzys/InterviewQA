package com.zys.qa.net

import com.zys.baselibrary.net.RetrofitFactory
import com.zys.qa.net.HttpUrl

class Api(baseUrl: String) : RetrofitFactory(baseUrl) {
    companion object {

        private val api = Api(HttpUrl.BASEURL!!)

        val service: ApiService
            get() = api.getRetrofit().create(ApiService::class.java)

        fun <T> getService(service: Class<T>): T {
            return api.getRetrofit().create(service)
        }
    }


}