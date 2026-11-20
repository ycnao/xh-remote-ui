package com.xcore.remote.ui.model


/**
 *实体类
 * author: Created by 闹闹 on 2019/1/15
 * version: 1.0.0
 */

data class DataBean(val name: String)

data class MenuData(val name: String)

data class MenuBean(val name: String, val resId: Int)


data class ActionVo(val data: List<ActionData>)

data class ActionData(val name: String, val value: String)

data class ActionBean(val name: String, val icon: Int)

//
data class LogData(val product: String, val type: String, val info: String, val time: String)

//版本
data class VersionVo(val code: Int, val msg: String, val data: VersionBean?)
data class VersionData(val code: Int, val msg: String, val data: List<VersionBean>?)

data class VersionBean(
    val id: String, //15
    val firmwareName: String, //遥控器1
    val firmwareVersion: String, //2.6.8.4
    val fileName: String, //lingguang_b3_app_ota_2.5.0.1.bin
    val fileUrl: String, //https://human-cloud-oss-test.oss-cn-guangzhou.aliyuncs.com/upload/1755690096113_lingguang_b3_app_ota_2.5.0.1.bin
    val pushStatus: String, //1
    val firmwareType: String, //1
    val productId: String, //1958084987634151424
    val productName: String, //K4-V1遥控器
    val productType: String, //K4-V1
    val productClassification: String, //4
    val createTime: String, //2025-08-20 19:41:39
    val updateTime: String //2025-10-29 09:21:19
)



