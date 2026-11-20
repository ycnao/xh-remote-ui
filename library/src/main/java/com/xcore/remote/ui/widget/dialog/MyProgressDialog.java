package com.xcore.remote.ui.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MyProgressDialog extends ProgressDialog {

    private Window window;

    public MyProgressDialog(Context context) {
        super(context);
        this.window = getWindow();
    }

    private void focusNotAle(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    private void clearFocusNotAle(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public void hideNavigationBar(final Window window) {
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    //布局位于状态栏下方
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    //全屏
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    //隐藏导航栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            } else {
                uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            window.getDecorView().setSystemUiVisibility(uiOptions);
        });
    }

    @Override
    public void show() {
        focusNotAle(window);
        super.show();
        hideNavigationBar(window);
        clearFocusNotAle(window);
    }

    public void setTextSize(View v, int size) {
        if (v instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) v;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt(i);
                setTextSize(child, size);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTextSize(size);
        }
    }
}