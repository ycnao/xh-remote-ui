package com.xcore.remote.ui.widget.callback;

/**
 * 按钮回调
 * author: Created by 闹闹 on 2018/6/26
 * version: 1.0.0
 */
public interface JxLoadCallback {

    void onStart();

    void onTick(long millisUntilFinished);

    void onFinish();


}
