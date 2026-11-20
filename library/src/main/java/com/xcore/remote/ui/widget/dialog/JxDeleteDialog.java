package com.xcore.remote.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcore.ui.widget.dialog.callback.JDialogCallback;
import com.xcore.remote.ui.R;

public class JxDeleteDialog extends Dialog {

    TextView dialog_title;
    ImageView dialog_close;
    TextView dialog_message;
    TextView dialog_cancel_btn;
    TextView dialog_confirm_btn;

    String content;

    JDialogCallback dialogCallback;

    public JxDeleteDialog(Context context, String content, JDialogCallback callback) {
        super(context, com.xcore.ui.R.style.MyDialog_Them);
        this.content = content;
        dialogCallback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dialog_jx_delete);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        dialog_title = findViewById(R.id.dialog_title);
        dialog_close = findViewById(R.id.dialog_close);
        dialog_message = findViewById(R.id.dialog_message);
        dialog_cancel_btn = findViewById(R.id.dialog_cancel_btn);
        dialog_confirm_btn = findViewById(R.id.dialog_confirm_btn);

        initData();
        initEvent();

    }

    private void initData(){
        if (!TextUtils.isEmpty(content)) {
            dialog_message.setText(content);
        }
    }

    private void initEvent(){
        dialog_close.setOnClickListener(v -> dismiss());
        dialog_cancel_btn.setOnClickListener(v -> {
            dialogCallback.cancel(v);
            dismiss();
        });
        dialog_confirm_btn.setOnClickListener(v -> {
            dialogCallback.confirm(v);
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