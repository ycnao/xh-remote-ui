package com.xcore.remote.ui.http

import android.util.Log
import com.google.gson.Gson
import com.xcore.ui.http.RetrofitUtil
import com.xcore.ui.http.callback.HttpCallback
import com.xcore.ui.http.model.ResponseJsonBean
import com.xcore.ui.http.util.LogUtils
import com.xcore.remote.ui.http.service.IRequestService
import com.xcore.remote.ui.http.service.IUploadService

import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.io.IOException


/**
 * 网络请求封装
 * author：created by 闹闹 on 2025/10/29
 * version：v1.0.0
 */
abstract class RequestApiAbstract(val apiHost: String) {

    private val contentType = MediaType.parse("application/json; charset=utf-8")

    /**
     * 网络请求
     *
     * @param url      请求方法名称
     * @param map     请求参数封装
     * @param callback 返回值处理
     */
    fun requestServerPostByMap(url: String, map: Map<String, String>, callback: HttpCallback) {
        //httpService
        val httpService = RetrofitUtil().createApi(apiHost, IRequestService::class.java)
        Log.d("请求服务端URL", "- post - ${apiHost}${url}?$map")
        callback.onStart()
        httpService.postCall(url, map).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("请求服务端/成功 URL", "${apiHost}${url}")
                try {
                    Log.d("请求服务端成功返回response", response.toString())
                    if (response.body() != null) {
                        val jsonData = response.body()!!.string()
                        val respJsonBean = Gson().fromJson(jsonData, ResponseJsonBean::class.java)
                        if (respJsonBean.code == 200) {
                            LogUtils.success(if (response.body() != null) jsonData else "")
                            callback.onSuccess(jsonData)
                        } else {
                            LogUtils.error(if (response.body() != null) jsonData else "")
                            callback.onFailure(respJsonBean.message ?: "服务器异常")
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                callback.onFinish()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable?) {
                Log.d("请求服务端/失败 URL", url)
                if (t != null) t.message?.let { Log.d("请求服务端失败返回Throwable：", it) }
                if (t!!.message == "timeout") {
                    callback.onFailure("请求超时")
                } else {
                    callback.onFailure("网络异常")
                }
            }
        })
    }

    /**
     * 网络请求
     *
     * @param url      请求方法名称
     * @param map     请求参数封装
     * @param callback 返回值处理
     */
    fun requestServerGetByMap(url: String, map: Map<String, String>, callback: HttpCallback) {
        //request
        val httpService = RetrofitUtil().createApi(apiHost, IRequestService::class.java)
        Log.d("请求服务端URL", "- get - ${apiHost}${url}?$map")
        callback.onStart()
        httpService.getCall(url, map).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("请求服务端/成功 URL", "${apiHost}${url}")
                try {
                    Log.d("请求服务端成功返回response", response.toString())
                    if (response.body() != null) {
                        val jsonData = response.body()!!.string()
                        val respJsonBean = Gson().fromJson(jsonData, ResponseJsonBean::class.java)
                        if (respJsonBean.code == 200) {
                            LogUtils.success(if (jsonData != null) jsonData else "")
                            callback.onSuccess(jsonData)
                        } else {
                            LogUtils.error(if (jsonData != null) jsonData else "")
                            callback.onFailure(respJsonBean.message ?: "服务器异常")
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                callback.onFinish()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable?) {
                Log.d("请求服务端/失败 URL", url)
                if (t != null) t.message?.let { Log.d("请求服务端失败返回Throwable：", it) }
                if (t!!.message == "timeout") {
                    callback.onFailure("请求超时")
                } else {
                    callback.onFailure("网络异常")
                }
            }
        })
    }

    /**
     * 网络请求
     *
     * @param url      请求方法名称
     * @param json     请求参数封装
     * @param callback 返回值处理
     */
    fun requestServerByJsonObject(url: String, json: JSONObject, callback: HttpCallback) {
        //request
        Log.d("请求服务端URL", "- post - ${apiHost}${url}?$json")
        val body = RequestBody.create(contentType, json.toString())
        val httpService = RetrofitUtil().createApi(apiHost, IRequestService::class.java)
        callback.onStart()
        httpService.postCall(url, body).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("请求服务端/成功 URL", "${apiHost}${url}")
                try {
                    Log.d("请求服务端成功返回response", response.toString())
                    if (response.body() != null) {
                        val jsonData = response.body()!!.string()
                        val respJsonBean = Gson().fromJson(jsonData, ResponseJsonBean::class.java)
                        if (respJsonBean.code == 200) {
                            LogUtils.success(if (jsonData != null) jsonData else "")
                            callback.onSuccess(jsonData)
                        } else {
                            LogUtils.error(if (response.body() != null) jsonData else "")
                            callback.onFailure(respJsonBean.message ?: "服务器异常")
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                callback.onFinish()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable?) {
                Log.d("请求服务端/失败 URL", url)
                if (t != null) t.message?.let { Log.d("请求服务端失败返回Throwable：", it) }
                if (t!!.message == "timeout") {
                    callback.onFailure("请求超时")
                } else {
                    callback.onFailure("网络异常")
                }
            }
        })
    }


    /**
     * 上传
     *
     * @param host      请求方法名称
     * @param filePath     文件名
     * @param callback 返回值处理
     */
    fun uploadByJsonObject(host: String, filePath: String, callback: HttpCallback) {
        val url = "url=$host/core/file/uploadAttachment.json?file=$filePath"
        Log.d("请求服务端", url)
        val file = File(filePath)
        val retrofit = Retrofit.Builder().baseUrl(host).build()

        val service = retrofit.create<IUploadService>(IUploadService::class.java)

        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val response = service.uploadFile(part)

        response.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("请求服务端/成功 URL", url)
                try {
                    Log.d("请求服务端成功返回response", response.toString())
                    if (response.body() != null) {
                        val jsonData = response.body()!!.string()
                        val respJsonBean = Gson().fromJson(jsonData, ResponseJsonBean::class.java)
                        if (respJsonBean != null) {
                            if (respJsonBean.code == 200) {
                                LogUtils.success(if (jsonData != null) "$jsonData" else "null")
                                callback.onSuccess(jsonData)
                            } else {
                                LogUtils.error(if (jsonData != null) "$jsonData" else "null")
                                callback.onFailure(respJsonBean.message)
                            }
                        } else {
                            callback.onFailure("文件上传失败，请重新上传！")
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("请求服务端/失败 URL", url)
                t.message?.let { Log.d("请求服务端失败返回Throwable：", it) }
                callback.onFailure("网络异常")
            }
        })
    }

    private fun toRequestBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }
}
