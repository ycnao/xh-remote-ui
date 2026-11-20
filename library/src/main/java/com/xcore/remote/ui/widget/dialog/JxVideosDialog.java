package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.JxVideosCallback;

/**
 * 输入对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxVideosDialog extends Dialog {

    private Activity mContext;

    private EditText editName;
    private EditText editContent;

    private String name;
    private String rtsp;
    private JxVideosCallback callback;

    public JxVideosDialog(Activity context, String name, String rtsp, JxVideosCallback callback) {
        super(context, R.style.MyDialog_Them);
        mContext = context;
        this.name = name;
        this.rtsp = rtsp;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_videos);
        //按空白处不能取消动画
//        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
//        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();
        //初始化界面控件
        editName = findViewById(R.id.edit_name);
        editContent = findViewById(R.id.edit_content);
        //初始化界面数据
        initData();
        initEvent();
    }

    private void initData() {
        if (!name.isEmpty()) editName.setText(name);
        if (!rtsp.isEmpty()) editContent.setText(rtsp);
    }

    //初始化界面控件的事件
    private void initEvent() {
        findViewById(R.id.okBtn).setOnClickListener(v -> {
            String edit = editName.getText().toString();
            String editTxt = editContent.getText().toString();
            if (!edit.isEmpty()) {
                if (!editTxt.isEmpty()) {
                    callback.confirm(edit, editTxt);
                    dismiss();
                } else {
                    Toast.makeText(mContext, R.string.please_input_name, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, R.string.please_input_rtsp, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.cancelBtn).setOnClickListener(v -> {
            dismiss();
            callback.cancel(v);
        });
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
