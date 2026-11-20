package com.xcore.remote.ui.widget;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcore.remote.ui.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 可以监听滚动状态的HorizontalScrollView
 */
public class JxHorizontalTextView extends RelativeLayout {

    private TextView leftView;
    private TextView centerView;
    private TextView rightView;

    private List<String> listData = new ArrayList<>();

    public JxHorizontalTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JxHorizontalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_horizontal_text, this);

        leftView = findViewById(R.id.tv_left);
        centerView = findViewById(R.id.tv_center);
        rightView = findViewById(R.id.tv_right);

    }

    public void setListData(String[] array) {
        this.listData.clear();
        if (array.length > 0) {
            this.listData.addAll(Arrays.asList(array));
        }
//        setSelectView(0);
    }

    public String setSelectView(int position) {
        if (!listData.isEmpty() && position < listData.size()) {
            if (position - 1 < 0) {
                leftView.setText(listData.get(listData.size() - 1));
            } else {
                leftView.setText(listData.get(position - 1));
            }
            centerView.setText(listData.get(position));
            setVibrate(100);
            //动画效果
            Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
            centerView.startAnimation(shake);

            if (position == listData.size() - 1) {
                rightView.setText(listData.get(0));
            } else {
                rightView.setText(listData.get(position + 1));
            }
            return listData.get(position);
        }
        return "";
    }

    /**
     * 简短震动
     *
     * @param milliseconds
     */
    private void setVibrate(long milliseconds) {
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(milliseconds);
        }
    }
}


