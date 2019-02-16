package com.lookbi.baselibrary.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

interface IRetrofit{
    abstract fun getRetrofit(): Retrofit

    abstract fun setInterceptor(interceptor: Interceptor): OkHttpClient.Builder

    abstract fun setConverterFactory(factory: Converter.Factory): Retrofit.Builder
}