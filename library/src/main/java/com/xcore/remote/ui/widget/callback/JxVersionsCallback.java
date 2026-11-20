package com.xcore.remote.ui.widget.callback;

import android.view.View;

import com.xcore.remote.ui.model.VersionBean;

/**
 * 回调
 * author: Created by 闹闹 on 2018/6/26
 * version: 1.0.0
 */
public interface JxVersionsCallback {

    /**
     * 取消对话框
     */
    void cancel(View view);

    /**
     * 确定对话框
     */
    void listItemClick(int position, VersionBean data);


}
