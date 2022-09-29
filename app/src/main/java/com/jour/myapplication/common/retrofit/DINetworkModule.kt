package com.jour.myapplication.common.retrofit

import com.jour.myapplication.base.constant.VersionStatus
import com.jour.myapplication.common.constant.NetBaseUrlConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.converter.gson.GsonConverterFactory
import com.jour.myapplication.BuildConfig
import com.jour.myapplication.common.retrofit.HttpLogger
import javax.inject.Singleton

/**
 * 全局作用域的网络层的依赖注入模块c
 *
 * @author Qu Yunshuo
 * @since 6/4/21 8:58 AM
 */
@Module
@InstallIn(SingletonComponent::class)
class DINetworkModule {

    /**
     * [OkHttpClient]依赖提供方法
     *
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        // 日志拦截器部分
        val logInterceptor = HttpLoggingInterceptor(HttpLogger()).setLevel(BODY)
        return OkHttpClient.Builder()
            .connectTimeout(15L * 1000L, TimeUnit.MILLISECONDS)
            .readTimeout(20L * 1000L, TimeUnit.MILLISECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor {
                val original = it.request()
                val req =
                    original.newBuilder()
                        .addHeader("Content-Type", "application/json")//添加头部
                        .addHeader("Accept", "application/json")
                        .method(original.method, original.body)
                        .build()
                it.proceed(req)
            }
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * 项目主要服务器地址的[Retrofit]依赖提供方法
     *
     * @param okHttpClient OkHttpClient OkHttp客户端
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun provideMainRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetBaseUrlConstant.MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}