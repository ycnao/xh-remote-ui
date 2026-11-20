package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.JxCancelCallback;

/**
 * 对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxContactDialog extends Dialog {

    private Activity mContext;
    private String tips;

    private JxCancelCallback callback;

    public JxContactDialog(Activity context, JxCancelCallback callback) {
        super(context, com.xcore.ui.R.style.MyDialog_Them);
        mContext = context;
        this.tips = tips;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_contact);
        //按空白处不能取消动画
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();
        //初始化界面控件
        findViewById(R.id.iv_cancel).setOnClickListener(v -> {
            callback.onCancel();
            dismiss();
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
