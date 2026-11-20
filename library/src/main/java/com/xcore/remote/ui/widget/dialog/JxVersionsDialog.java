package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xcore.libs.adapter.NArrayListAdapter;
import com.xcore.remote.ui.R;
import com.xcore.remote.ui.model.VersionBean;
import com.xcore.remote.ui.widget.callback.JxVersionsCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 版本 对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxVersionsDialog extends Dialog {

    private Activity mContext;
    private boolean isUpdate;
    private ListView listView;
    private JxVersionsCallback callback;
    private static String typeStr;
    private static ArrayList<VersionBean> mListData = new ArrayList<VersionBean>();

    public JxVersionsDialog(Activity context, boolean update, List<VersionBean> listData, JxVersionsCallback callback) {
        super(context, R.style.MyDialog_Them);
        mContext = context;
        isUpdate = update;
        mListData.clear();
        mListData.addAll(listData);
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_versions);
        //初始化界面控件
        TextView titleTv = findViewById(R.id.tv_title);
        titleTv.setText(getContext().getString(R.string.update_select));
        TextView textView = findViewById(R.id.tv_not_data);

        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(mContext, mListData, R.layout.item_dialog_version);
        listView.setAdapter(adapter);

        if (mListData.size() > 0) {
            textView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        initEvent();
    }

    private void initEvent() {
        //初始化界面控件的事件
        findViewById(R.id.iv_cancel).setOnClickListener(v -> {
            callback.cancel(v);
            dismiss();
        });
        listView.setOnItemClickListener((arg0, view, position, arg3) -> {
            callback.listItemClick(position, mListData.get(position));
            dismiss();
        });
    }

    public class MyAdapter extends NArrayListAdapter<VersionBean> {

        public MyAdapter(Context context, ArrayList<VersionBean> data, int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public BaseViewHolder getViewHolder() {
            return new ViewHolder();
        }

        @Override
        public void initView(BaseViewHolder viewHolder, View convertView, int position) {
            ViewHolder mViewHolder = (ViewHolder) viewHolder;
            mViewHolder.title = convertView.findViewById(R.id.tv_name);
            mViewHolder.version = convertView.findViewById(R.id.tv_number);
            mViewHolder.date = convertView.findViewById(R.id.tv_dateTime);
            mViewHolder.update = convertView.findViewById(R.id.btn_update);
        }

        @Override
        public void setData(BaseViewHolder viewHolder, View convertView, int position) {
            if (mData == null || mData.isEmpty()) {
                return;
            }
            ViewHolder mViewHolder = (ViewHolder) viewHolder;
            VersionBean listBean = mData.get(position);

            mViewHolder.title.setText(listBean.getFileName() + "");
            mViewHolder.version.setText("版本号：" + listBean.getFirmwareVersion());
            mViewHolder.date.setText("发布时间：" + listBean.getUpdateTime());
            if (isUpdate) {
                mViewHolder.update.setVisibility(View.VISIBLE);
            } else {
                mViewHolder.update.setVisibility(View.GONE);
            }
        }

        class ViewHolder extends BaseViewHolder {
            private TextView title;
            private TextView version;
            private TextView date;
            private TextView update;
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
