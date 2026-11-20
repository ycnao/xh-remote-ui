package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xcore.libs.utils.Md5Utils;
import com.xcore.remote.ui.R;
import com.xcore.remote.ui.app.JxConstant;
import com.xcore.remote.ui.data.DataHelper;
import com.xcore.remote.ui.widget.callback.JxEditDialogCallback;

/**
 * 输入对话框
 * author：created by 闹闹 on 2025/11/18
 * version：v1.0.0
 */
public class JxPasswordDialog extends Dialog {

    private Activity mContext;

    private Button btnConfirm;
    private Button btnCancel;

    private TextView contentTv;
    private EditText editContent;

    private String title;
    private String snMd5;
    private String password;
    private JxEditDialogCallback callback;

    public JxPasswordDialog(Activity context, String title, String password, String md5, JxEditDialogCallback callback) {
        super(context, com.xcore.ui.R.style.MyDialog_Them);
        mContext = context;
        this.title = title;
        this.snMd5 = md5;
        this.password = password;
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
    }

    //初始化界面控件的事件
    private void initEvent() {
        btnConfirm.setOnClickListener(v -> {
            String editTxt = editContent.getText().toString();
            if (editTxt.contains("#")) {
                String[] split = DataHelper.strToArray(editTxt);
                if (split.length >= 3 && split[1].equals("touch")) {
                    String toMd5 = Md5Utils.toMD5(snMd5);
                    if (split[2].equals(toMd5)) {
                        callback.confirm(editTxt, 3, JxConstant.SP_TOUCH, "");
                        dismiss();
                    } else {
                        callback.error();
                    }
                } else if (split.length >= 3 && split[1].equals("all")) {
                    String toMd5 = Md5Utils.toMD5(snMd5);
                    if (split[2].equals(toMd5)) {
                        callback.confirm(editTxt, 3, JxConstant.SP_ALL, "");
                        dismiss();
                    } else {
                        callback.error();
                    }
                } else if (split.length >= 3 && split[1].equals("download")) {
                    if (split[2].equals("file")) {
                        callback.confirm(editTxt, 3, JxConstant.SP_DOWN, "");
                        dismiss();
                    } else {
                        callback.error();
                    }
                } else if (split.length >= 4 && split[1].equals("upgrade")) {
                    if (split[2].equals("remote")) {
                        callback.confirm(editTxt, 4, JxConstant.UP_REMOTE, split[3]);
                        dismiss();
                    } else if (split[2].equals("device")) {
                        callback.confirm(editTxt, 4, JxConstant.UP_DEVICE, split[3]);
                        dismiss();
                    } else if (split[2].equals("subdevice")) {
                        callback.confirm(editTxt, 4, JxConstant.UP_SUB_DEVICE, split[3]);
                        dismiss();
                    } else {
                        callback.error();
                    }
                } else {
                    callback.error();
                }
            } else if (password.equals(editTxt)) {
                callback.confirm(editTxt, 0, JxConstant.SP_NULL, "");
                dismiss();
            } else {
                callback.error();
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
