package com.xcore.remote.ui.widget.callback;

import android.view.View;

/**
 * 按钮回调
 * author: Created by 闹闹 on 2018/6/26
 * version: 1.0.0
 */
public interface JxListItemCallback {

    void cancel(View view);

    /**
     * 列表选定回调
     *
     * @param selectedData 选定的列表项数据
     * @param position     选定的列表位置
     */
    void listItemClick(String selectedData, int position);

}
