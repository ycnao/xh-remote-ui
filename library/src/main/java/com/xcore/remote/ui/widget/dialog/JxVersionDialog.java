package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.model.VersionBean;
import com.xcore.remote.ui.widget.callback.JxVersionCallback;


/**
 * 版本 对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxVersionDialog extends Dialog {

    private Activity mContext;
    private JxVersionCallback callback;
    private LinearLayout layoutInfo;
    private LinearLayout layoutDown;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private TextView btnCancel;
    private TextView btnConfirm;

    private static VersionBean mData;

    public JxVersionDialog(Activity context, VersionBean version, JxVersionCallback callback) {
        super(context, R.style.MyDialog_Them);
        mContext = context;
        mData = version;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_version);
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        TextView textView = findViewById(R.id.tv_version);
        TextView dateTv = findViewById(R.id.tv_date);
        layoutInfo = findViewById(R.id.layout_info);
        layoutDown = findViewById(R.id.layout_down);
        progressBar = findViewById(R.id.pb_download);
        tvProgress = findViewById(R.id.tv_progress);
        btnCancel = findViewById(R.id.btn_cancel);
        btnConfirm = findViewById(R.id.btn_confirm);

        String newNo = getContext().getString(R.string.update_version_new);
        String dateTx = getContext().getString(R.string.update_version_date);
        textView.setText("（" + mData.getFirmwareVersion() + "）");
        dateTv.setText(dateTx + mData.getCreateTime());

        initEvent();
    }


    private void initEvent() {
        //初始化界面控件的事件
        btnCancel.setOnClickListener(v -> {
            callback.cancel(v);
            dismiss();
        });
        btnConfirm.setOnClickListener(v -> {
            callback.confirm(mData);
            btnCancel.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
            setView();
        });
    }

    private void setView() {
        layoutInfo.setVisibility(View.GONE);
        layoutDown.setVisibility(View.VISIBLE);
    }

    public void setProgressBar(int progress) {
        progressBar.setProgress(progress);
        tvProgress.setText(progress >= 100 ? "下载完成" : "下载中 " + progress + "%");
        if (progress >= 100) dismiss();
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
