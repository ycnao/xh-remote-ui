package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.JxLoadCallback;

/**
 * 对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxLoadingDialog extends Dialog {

    private Activity mContext;
    private String text;
    private int second;
    private JxLoadCallback callback;

    public JxLoadingDialog(Activity context, String text, int second, JxLoadCallback callback) {
        super(context, R.style.MyDialog_Them);
        mContext = context;
        this.text = text;
        this.second = second;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_load);
        //按空白处不能取消动画
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();
        //初始化界面控件
        TextView content = findViewById(R.id.tv_content);

        if (!text.isEmpty()) content.setText(text);
        long millis;
        if (second != 0) {
            millis = (long) second * 1000;
        } else {
            millis = 2000;
        }
        callback.onStart();

        new CountDownTimer(millis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                callback.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                dismiss();
                callback.onFinish();
            }

        }.start();
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
