package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.xcore.ui.widget.dialog.callback.JDialogCallback;
import com.xcore.remote.ui.R;

/**
 * 对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxTipsDialog extends Dialog {

    private Activity mContext;
    private JDialogCallback callback;
    private String tips;


    public JxTipsDialog(Activity context, String tips, JDialogCallback callback) {
        super(context, com.xcore.ui.R.style.MyDialog_Them);
        mContext = context;
        this.tips = tips;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_tips);
        //按空白处不能取消动画
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();
        //初始化界面控件
        TextView content = findViewById(R.id.tv_content);
        TextView cancel = findViewById(R.id.btn_cancel);
        TextView confirm = findViewById(R.id.btn_confirm);

        //初始化界面控件的事件
        cancel.setOnClickListener(v -> {
            dismiss();
            callback.cancel(v);
        });
        confirm.setOnClickListener(v -> {
            dismiss();
            callback.confirm(v);
        });
        if (!tips.isEmpty()) content.setText(tips);

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
