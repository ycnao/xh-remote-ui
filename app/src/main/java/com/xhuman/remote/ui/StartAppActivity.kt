package com.xhuman.remote.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

/**
 * 应用启动入口。
 *
 * author：created by 闹闹 on 2025/10/27
 * version：v1.0.0
 */
class StartAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

    }

    /**
     * 页面重新可见时隐藏系统导航栏，保持遥控应用的全屏显示方式。
     */
    override fun onResume() {
        super.onResume()
    }
}
