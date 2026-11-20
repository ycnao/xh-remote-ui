package com.xcore.remote.ui.http.service

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * 网络请求
 * author: Created by 闹闹 on 2018-09-13
 * version: 1.0.0
 */
interface IRequestService {

    @GET
    fun getCall(@Url url: String, @QueryMap map: Map<String, String>): Call<ResponseBody>

    @POST
    fun postCall(@Url url: String, @QueryMap map: Map<String, String>): Call<ResponseBody>

    @POST
    fun postCall(@Url url: String, @Body requestBody: RequestBody): Call<ResponseBody>

    @POST
    fun postCall(
        @Url url: String,
        @Header("authorization") token: String,
        @Body requestBody: RequestBody
    ): Call<ResponseBody>

}