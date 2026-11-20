package com.xcore.remote.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.xcore.remote.ui.R;
import com.xcore.remote.ui.model.SelectFileData;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选 - 一个确认按钮
 */
public class JxSelectFileDialog extends Dialog {

    private TextView title;
    private ListView listView;

    private String mTitle;

    private Context context;
    private MyListAdapter mListAdapter;
    private List<SelectFileData> mListData = new ArrayList<>();
    private static DialogCallback callback;

    public interface DialogCallback {

        void confirm(SelectFileData storage);
    }

    public JxSelectFileDialog(Context context, String title, List<SelectFileData> listData, DialogCallback onClickListener) {
        super(context, com.xcore.ui.R.style.MyDialog_Them);
        this.context = context;
        mTitle = title;
        mListData = listData;
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

        setContentView(R.layout.dialog_jx_select_file);
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
        listView = findViewById(R.id.listView);

        mListAdapter = new MyListAdapter(context, mListData);
        listView.setAdapter(mListAdapter);
//        mListAdapter.notifyDataSetChanged();
    }

    public void initData() {
        if (!TextUtils.isEmpty(mTitle)) {
            title.setText(mTitle);
        }

//        mListData.clear();
//        List<JFileVo> storage = SDCardUtil.getStorage(context);
//        //2、最后选择内部存储
//        String localPath = FileUtil.insideStorage(context).getAbsolutePath();
//        storage.add(new JFileVo(localPath, "本机"));
//
//        for (JFileVo item : storage) {
//            if (item.getType().contains("U")) {
//                name = Constants.USB_NAME;
//            } else if (item.getType().contains("SD")) {
//                name = Constants.TF_NAME;
//            } else {
//                name = Constants.INSIDE_NAME;
//            }
//
//            StatFs sf = new StatFs(item.getPath());
//            long blockSize = sf.getBlockSize();
//            long blockCount = sf.getBlockCount();
//            long availCount = sf.getAvailableBlocks();
//            long total = blockSize * blockCount;
//            long avail = availCount * blockSize;
//            Log.d("Util", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + blockSize * blockCount / 1024 / 1024 / 1024 + "GB");
//            Log.d("Util", "可用的block数目：:" + availCount + ",剩余空间:" + availCount * blockSize / 1024 / 1024 / 1024 + "GB");
//            String path = FileUtil.toRootPath(item.getPath());
//            SelectFileData data = new SelectFileData(name, path, item.getType(), total, avail);
//            mListData.add(data);
//        }
    }

    public void initEvent() {
        findViewById(R.id.dialog_close).setOnClickListener(v -> dismiss());
        listView.setOnItemClickListener((parent, view, position, id) -> {
            SelectFileData storageData = mListData.get(position);
            callback.confirm(storageData);

            dismiss();
        });
    }

    public static class MyListAdapter extends BaseAdapter {

        private Context context;
        private List<SelectFileData> data;
        private LayoutInflater inflater;

        public MyListAdapter(Context context, List<SelectFileData> data) {
            this.context = context;
            this.data = data;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<SelectFileData> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        public void addData(List<SelectFileData> data) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        public List<SelectFileData> getData() {
            return this.data;
        }

        public void clearData() {
            this.data.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_select_file, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SelectFileData item = data.get(position);

            holder.tv_name.setText(item.getName());
            long totalSize = item.getTotalSize();
            long availSize = item.getAvailable();
            String total = "空间容量:" + totalSize / 1024 / 1024 / 1024 + "GB";
            String avail = "可用:" + availSize / 1024 / 1024 / 1024 + "GB";

            holder.tv_text.setText(total + " / " + avail);

            if (position == data.size() - 1) {
                holder.tv_line.setVisibility(View.GONE);
            } else {
                holder.tv_line.setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        class ViewHolder {

            private TextView tv_name;
            private TextView tv_text;
            private TextView tv_line;

            ViewHolder(View convertView) {
                tv_name = convertView.findViewById(R.id.tv_name);
                tv_text = convertView.findViewById(R.id.tv_text);
                tv_line = convertView.findViewById(R.id.tv_line);
            }
        }

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

