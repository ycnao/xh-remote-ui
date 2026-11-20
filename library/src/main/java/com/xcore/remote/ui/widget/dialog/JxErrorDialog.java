package com.xcore.remote.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.JxCancelCallback;


/**
 * 异常-对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxErrorDialog extends Dialog {

    private JxCancelCallback callback;
    private String tips;

    public JxErrorDialog(Context context, String tips, JxCancelCallback callback) {
        super(context, R.style.MyDialog_Them);
        this.tips = tips;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_error);
        //按空白处不能取消动画
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();

        //初始化界面控件
        TextView content = findViewById(R.id.tv_content);
        if (!tips.isEmpty()) content.setText(tips);

        //初始化界面控件的事件
        findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            dismiss();
            callback.onCancel();
        });
        findViewById(R.id.iv_cancel).setOnClickListener(v -> {
            dismiss();
            callback.onCancel();
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
