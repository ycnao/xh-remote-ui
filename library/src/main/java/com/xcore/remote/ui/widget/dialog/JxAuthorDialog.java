package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.JxDialogCallback;
import com.xcore.ui.widget.view.VerificationCodeView;

/**
 * 输入对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxAuthorDialog extends Dialog {

    private final Activity mContext;

    private TextView btnConfirm;
    private TextView btnCancel;

    private TextView contentTv;
    private VerificationCodeView editContent;

    private final String title;
    private final JxDialogCallback callback;

    public JxAuthorDialog(Activity context, String title, JxDialogCallback callback) {
        super(context, com.xcore.ui.R.style.MyDialog_Them);
        mContext = context;
        this.title = title;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_author);
        //按空白处不能取消动画
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();
        //初始化界面控件
        contentTv = findViewById(R.id.contentTv);
        editContent = findViewById(R.id.vc_author);
        btnConfirm = findViewById(R.id.okBtn);
        btnCancel = findViewById(R.id.cancelBtn);
        //初始化界面数据
        initData();
        initEvent();
    }

    private void initData() {
        if (!title.isEmpty()) contentTv.setText(title);
    }

    //初始化界面控件的事件
    private void initEvent() {
        btnConfirm.setOnClickListener(v -> {
            String editTxt = editContent.getText();
            if (editTxt.length() == 6) {
                callback.confirm(v, editTxt);
                dismiss();
            } else {
                Toast.makeText(mContext, "请输入完整密码", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            callback.cancel(v);
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
