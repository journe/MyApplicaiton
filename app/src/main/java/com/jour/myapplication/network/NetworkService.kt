package com.jour.myapplication.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
//    private var cookieJar: PersistentCookieJar =
//        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApplication.instance))

//    private val retrofit = Retrofit.Builder()
//        .client(OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS).build())
//        .baseUrl(Constants.BASE_URL_DEV)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//    val apiService = retrofit.create<ApiService>()

    private val apiService: ApiService
    private val client: OkHttpClient = OkHttpClient.Builder()
        .apply {
            followRedirects(false)
        }
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            val response = chain.proceed(request)
            response
        }
        .build()


    init {

        apiService = Retrofit.Builder()
            .baseUrl("https://dev-api.echoing.tech")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

//    private fun buildCache(): Cache? {
//        val cacheDir = File(MyApplication.instance.cacheDir, "webCache")
//        val cacheSize = 16 * 1024 * 1024
//        return Cache(cacheDir, cacheSize.toLong())
//    }
//
//    fun cleanCookies() {
//        cookieJar.clear()
//    }
}