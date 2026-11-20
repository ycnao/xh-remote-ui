package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.JxCancelCallback;


/**
 * 对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxTipDialog extends Dialog {

    private Activity mContext;
    private JxCancelCallback callback;
    private String tips;
    private String title;


    public JxTipDialog(Activity context, String title, String tips, JxCancelCallback callback) {
        super(context, com.xcore.ui.R.style.MyDialog_Them);
        mContext = context;
        this.tips = tips;
        this.title = title;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_tip);
        //按空白处不能取消动画
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();
        //初始化界面控件
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView content = findViewById(R.id.tv_content);
        TextView cancel = findViewById(R.id.btn_cancel);

        //初始化界面控件的事件
        cancel.setOnClickListener(v -> {
            dismiss();
            callback.onCancel();
        });

        if (!tips.isEmpty()) content.setText(tips);
        if (!title.isEmpty()) tvTitle.setText(title);
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
