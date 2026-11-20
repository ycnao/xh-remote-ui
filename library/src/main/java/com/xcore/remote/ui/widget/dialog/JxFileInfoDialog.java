package com.xcore.remote.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.xcore.libs.file.JFileUtils;
import com.xcore.libs.file.JVideoUtils;
import com.xcore.libs.model.MimeType;
import com.xcore.remote.ui.R;

import java.io.File;

/**
 * 单选 - 一个确认按钮
 */
public class JxFileInfoDialog extends Dialog {

    private TextView title;
    private TextView tvName;
    private TextView tvSize;
    private TextView tvRate;
    private TextView tvDate;
    private TextView tvPath;

    private static String mTitle;
    private static String fileName;
    private static String filePath;
    private static MimeType fileType;

    public JxFileInfoDialog(Context context, String title, MimeType type, String name, String path) {
        super(context, com.xcore.ui.R.style.JDialog_Them);
        mTitle = title;
        fileType = type;
        fileName = name;
        filePath = path;

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

        setContentView(R.layout.dialog_jx_file_info);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    public void initView() {
        title = findViewById(R.id.dialog_title);
        tvName = findViewById(R.id.tv_name);
        tvSize = findViewById(R.id.tv_size);
        tvRate = findViewById(R.id.tv_rate);
        tvDate = findViewById(R.id.tv_date);
        tvPath = findViewById(R.id.tv_path);
    }

    public void initData() {
        if (!TextUtils.isEmpty(mTitle)) {
            title.setText(mTitle);
        }

        if (!TextUtils.isEmpty(fileName)) {
            tvName.setText(fileName);
        }

        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                //图片日期为
                String fileModifiedDate = JFileUtils.getFileModifiedDate(file);
                tvDate.setText(fileModifiedDate);
            } catch (Exception ee) {
                Log.v("==", " 出了点问题 ");
            }
        }

        if (!TextUtils.isEmpty(filePath)) {
            long imageSize = getImageSize(filePath);
            String size = Formatter.formatFileSize(getContext(), imageSize);
            tvSize.setText(size);

            if (fileType == MimeType.IMAGE) {
                tvRate.setText(getRate(filePath));
            } else if (fileType == MimeType.VIDEO) {
                int[] resolution = JVideoUtils.getVideoResolution(filePath);
                int width = resolution[0];
                int height = resolution[1];
                tvRate.setText(width + "*" + height);
            }
            //存储路径
            tvPath.setText(filePath);
        }
    }

    public void initEvent() {
        findViewById(R.id.dialog_close).setOnClickListener(v -> {
            dismiss();
        });
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    private long getImageSize(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        return file.length();
    }

    private String getRate(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        return width + "*" + height;
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

