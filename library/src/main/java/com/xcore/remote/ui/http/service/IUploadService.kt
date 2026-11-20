package com.xcore.remote.ui.http.service

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.Multipart


/**
 * 网络请求
 * author: Created by 闹闹 on 2018-09-13
 * version: 1.0.0
 */
interface IUploadService {

    @Multipart
    @POST("/core/file/uploadAttachment.json")
    fun uploadFile(@Part file: MultipartBody.Part): Call<ResponseBody>

}
