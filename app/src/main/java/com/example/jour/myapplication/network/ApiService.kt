package com.example.jour.myapplication.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/user/action/send-verify-code")
    suspend fun sendVerifyCode(@Query("phone") phone: String): Call<ResponseBody>

    @POST("/user/action/send-verify-code")
    fun sendVerifyCode2(@Query("phone") phone: String): Call<ResponseBody>
}