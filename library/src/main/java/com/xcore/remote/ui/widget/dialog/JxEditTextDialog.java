package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.JxDialogCallback;

/**
 * 输入对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxEditTextDialog extends Dialog {

    private Activity mContext;

    private Button btnConfirm;
    private Button btnCancel;

    private TextView contentTv;
    private EditText editContent;

    private String title;
    private String text;
    private JxDialogCallback callback;

    public JxEditTextDialog(Activity context, String title, String text, JxDialogCallback callback) {
        super(context, com.xcore.ui.R.style.MyDialog_Them);
        mContext = context;
        this.title = title;
        this.text = text;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_jx_edit);
        //按空白处不能取消动画
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();
        //初始化界面控件
        contentTv = findViewById(R.id.contentTv);
        editContent = findViewById(R.id.edit_content);
        btnConfirm = findViewById(R.id.okBtn);
        btnCancel = findViewById(R.id.cancelBtn);
        //初始化界面数据
        initData();
        initEvent();
    }

    private void initData() {
        if (!title.isEmpty()) contentTv.setText(title);
        contentTv.setTextColor(mContext.getResources().getColor(R.color.greyE0_color));
        editContent.setTextColor(mContext.getResources().getColor(R.color.txt_input));
        if (!text.isEmpty()) editContent.setText(text);
    }

    //初始化界面控件的事件
    private void initEvent() {
        btnConfirm.setOnClickListener(v -> {
            String editTxt = editContent.getText().toString();
            if (!editTxt.isEmpty()) {
                dismiss();
                callback.confirm(v, editTxt);
            }
        });

        btnCancel.setOnClickListener(v -> {
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
