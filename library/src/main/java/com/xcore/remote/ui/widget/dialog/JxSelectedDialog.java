package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xcore.libs.adapter.NArrayAdapter;
import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.JxListItemCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxSelectedDialog extends Dialog {

    private Activity mContext;
    private JxListItemCallback callback;

    private static String titleStr;
    private static ArrayList<String> mListData = new ArrayList<String>();

    private ListView listView;

    public JxSelectedDialog(Activity context, String title, List<String> listData, JxListItemCallback callback) {
        super(context, R.style.MyDialog_Them);
        mContext = context;
        titleStr = title;
        mListData.clear();
        mListData.addAll(listData);
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_select);
        //初始化界面控件
        TextView titleTv = findViewById(R.id.tv_title);
        listView = findViewById(R.id.listView);

        if (!titleStr.isEmpty()) titleTv.setText(titleStr);
        MyAdapter adapter = new MyAdapter(mContext, mListData);
        listView.setAdapter(adapter);

        initEvent();
    }

    private void initEvent() {
        //初始化界面控件的事件
        findViewById(R.id.iv_cancel).setOnClickListener(v -> {
            callback.cancel(v);
            dismiss();
        });
        listView.setOnItemClickListener((arg0, view, position, arg3) -> {
            callback.listItemClick(mListData.get(position), position);
            dismiss();
        });
    }

    public class MyAdapter extends NArrayAdapter {

        /**
         * 构造函数
         *
         * @param context
         * @param data
         */
        public MyAdapter(Context context, ArrayList<String> data) {
            super(context, data);
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * com.linan.logistics.widget.mudialog.adapter.AbstractMuDialogAdapter
         * #getListItemView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getListItemView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_selected, null, false);
                viewHolder = new ViewHolder();

                // 初始化空间
                viewHolder.itemTv = convertView.findViewById(R.id.tv_item_data);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // 设置数据
            if (mData != null && !mData.isEmpty()) {
                viewHolder.itemTv.setText(mData.get(position).toString());
            }

            return convertView;
        }

        class ViewHolder {
            //            CheckBox itemIv;
            ImageView itemIv;
            TextView itemTv;
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}
