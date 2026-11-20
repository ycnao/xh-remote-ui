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




