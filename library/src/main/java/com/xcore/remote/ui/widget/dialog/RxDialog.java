package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.xcore.remote.ui.R;

import java.util.List;


public abstract class RxDialog extends Dialog implements DialogInterface.OnDismissListener {

    protected View contentView;
    protected LayoutParams mLayoutParams;
    protected Activity activity;
    private Window window;
    public LayoutParams getLayoutParams() {
        return mLayoutParams;
    }
    public RxDialog(Activity context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }
    public RxDialog(Activity activity ) {
        super(activity);
        initView(activity);
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (contentView == null) {
            readyView();
            setCanceledOnTouchOutside(true);
        }
    }


    private void readyView() {
        contentView = createContentView();
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        setContentView(contentView);
    }
    @NonNull
    protected abstract View createContentView();

    private void initView(Activity activity) {
        this.activity = activity;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window = this.getWindow();

        window.setBackgroundDrawableResource(R.mipmap.transparent_bg);
        LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }
    }

    @Deprecated
    @CallSuper
    protected void onInit(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }
    @CallSuper
    protected void onInit(@Nullable Bundle savedInstanceState) {
        //noinspection deprecation
        onInit(activity, savedInstanceState);
    }

    public void setOnWhole() {
        getWindow().setType(LayoutParams.TYPE_SYSTEM_ALERT);
    }


    @Override
    public void show() {
        focusNotAle(window);
        super.show();
        hideNavigationBar(window);
        clearFocusNotAle(window);
    }

    private void clearFocusNotAle(Window window) {
        window.clearFlags(LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    private void focusNotAle(Window window) {
        window.setFlags(LayoutParams.FLAG_NOT_FOCUSABLE,
                        LayoutParams.FLAG_NOT_FOCUSABLE);
    }
    @Override
    public void dismiss() {
        super.dismiss();
        hideNavigationBar(window);
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    public void hideNavigationBar(final Window window) {
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
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
            }
        });
    }
}
