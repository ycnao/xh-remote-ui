package com.xcore.remote.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.xcore.remote.ui.R;


/**
 * 消息的右上角 + 扩展功能
 */
public class JxFileMoreDialog extends Dialog {

    private Context mContext;

    public static int ADD = 1;
    public static int PATH = 2;
    public static int FRIEND = 3;
    public static int MEET = 4;
    private static DialogCallback callback;

    public interface DialogCallback {

        void confirm(int type);
    }

    public JxFileMoreDialog(Context context, DialogCallback onClickListener) {
        super(context, com.xcore.ui.R.style.FullScreenDialog);
        mContext = context;
        callback = onClickListener;
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

        setContentView(R.layout.dialog_jx_select_more);
        //按空白处不能取消动画
//        setCanceledOnTouchOutside(false);
//        setBackgroundTransparent();
        getWindow().setDimAmount(0f);
        //初始化界面控件
        initView();
        //初始化界面数据
        //初始化界面控件的事件
    }

    public void initView() {
        findViewById(R.id.root).setOnClickListener(v -> dismiss());
        findViewById(R.id.btn_add).setOnClickListener(v -> initEvent(ADD));
        findViewById(R.id.btn_path).setOnClickListener(v -> initEvent(PATH));
//        findViewById(R.id.btn_add_friend).setOnClickListener(v -> initEvent(FRIEND));
//        findViewById(R.id.btn_meeting).setOnClickListener(v -> initEvent(MEET));
    }

    public void initEvent(int type) {
        callback.confirm(type);
        dismiss();
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            //do something.
//            return true;
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
//    }
}
