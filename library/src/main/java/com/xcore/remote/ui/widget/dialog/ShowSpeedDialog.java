package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.OnNumberSelectedListener;
import com.xcore.remote.ui.widget.wheelview.WheelView;
import com.xcore.remote.ui.widget.wheelview.contract.OnWheelChangedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 为速度改造的dialog
 */
public class ShowSpeedDialog extends RxDialog {

    private int maxValue = 1;
    private int postion;
    private int defValue;
    private String title;

    // 仅动作参数启用符号选择；原有速度等页面沿用非负数编辑方式。
    private final boolean allowNegative;

    private View dialog_view;
    private TextView dialog_sure_tv;
    private TextView dialog_cancel_tv;

    private WheelView dialog_number_wheel_1;
    private WheelView dialog_number_wheel_2;
    private WheelView dialog_number_wheel_3;
    private WheelView dialog_sign_wheel;

    private OnNumberSelectedListener onNumberSelectedListener;

    public ShowSpeedDialog(Activity activity, String title, int index, int value, int max, OnNumberSelectedListener numberSelectedListener) {
        this(activity, title, index, value, max, false, numberSelectedListener);
    }

    /**
     * 带独立符号列的数值选择弹窗。数字列编辑绝对值，确认时再组合符号。
     * allowNegative 为 true 时显示正负号，负数默认选中“-”，零默认选中“+”。
     */
    public ShowSpeedDialog(Activity activity, String title, int index, int value, int max,
                           boolean allowNegative, OnNumberSelectedListener numberSelectedListener) {
        super(activity, R.style.DialogStyle);
        this.allowNegative = allowNegative;
        this.title = title;
        this.postion = index;
        this.defValue = value;
        this.maxValue = max;
        this.onNumberSelectedListener = numberSelectedListener;
    }

    public void setDefValue(int defValue) {
        this.defValue = defValue;
    }

    @NonNull
    @Override
    protected View createContentView() {
        dialog_view = LayoutInflater.from(activity).inflate(R.layout.dialog_config_dev_2, null);
        TextView textView = dialog_view.findViewById(R.id.tv_title);
        if (!title.isEmpty()) textView.setText(title);

        dialog_number_wheel_1 = dialog_view.findViewById(R.id.dialog_number_wheel_1);
        dialog_number_wheel_2 = dialog_view.findViewById(R.id.dialog_number_wheel_2);
        dialog_number_wheel_3 = dialog_view.findViewById(R.id.dialog_number_wheel_3);
        dialog_sign_wheel = dialog_view.findViewById(R.id.dialog_sign_wheel);
        dialog_sign_wheel.setVisibility(allowNegative ? View.VISIBLE : View.GONE);
        if (allowNegative) {
            dialog_sign_wheel.setData(Arrays.asList("+", "-"));
            dialog_sign_wheel.setDefaultValue(defValue < 0 ? "-" : "+");
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= maxValue; i++) {
            list.add(i);
        }
        dialog_number_wheel_1.setData(list);
        dialog_number_wheel_2.setData(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        dialog_number_wheel_3.setData(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        // 符号与数字分开回显，不能直接用负数取模，否则数字列找不到对应项。
        int magnitude = allowNegative ? (int) Math.min(Math.abs((long) defValue), maxValue * 100L + 99) : defValue;
        dialog_number_wheel_1.setDefaultValue(magnitude / 100);
        dialog_number_wheel_2.setDefaultValue((magnitude / 10) % 10);
        dialog_number_wheel_3.setDefaultValue(magnitude % 10);
        dialog_number_wheel_1.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onWheelScrolled(WheelView view, int offset) {
            }

            @Override
            public void onWheelSelected(WheelView view, int position) {
                if (maxValue == 1) {
                    if (position == 1) {
                        dialog_number_wheel_2.setDefaultValue(0);
                        dialog_number_wheel_3.setDefaultValue(0);
                    }
                }
            }

            @Override
            public void onWheelScrollStateChanged(WheelView view, int state) {
            }

            @Override
            public void onWheelLoopFinished(WheelView view) {
            }
        });
        dialog_number_wheel_2.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onWheelScrolled(WheelView view, int offset) {
            }

            @Override
            public void onWheelSelected(WheelView view, int position) {
                if (maxValue == 1) {
                    Number currentItem1 = dialog_number_wheel_1.getCurrentItem();
                    int bai = currentItem1.intValue();
                    if (bai > 0) {
                        dialog_number_wheel_1.setDefaultValue(0);
                    }
                }
            }

            @Override
            public void onWheelScrollStateChanged(WheelView view, int state) {
            }

            @Override
            public void onWheelLoopFinished(WheelView view) {
            }
        });
        dialog_number_wheel_3.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onWheelScrolled(WheelView view, int offset) {
            }

            @Override
            public void onWheelSelected(WheelView view, int position) {
                if (maxValue == 1) {
                    Number currentItem1 = dialog_number_wheel_1.getCurrentItem();
                    int bai = currentItem1.intValue();
                    if (bai > 0) {
                        dialog_number_wheel_1.setDefaultValue(0);
                    }
                }
            }

            @Override
            public void onWheelScrollStateChanged(WheelView view, int state) {
            }

            @Override
            public void onWheelLoopFinished(WheelView view) {
            }
        });


        dialog_cancel_tv = dialog_view.findViewById(R.id.dialog_cancel_tv);
        dialog_cancel_tv.setOnClickListener(view -> dismiss());

        dialog_sure_tv = dialog_view.findViewById(R.id.dialog_sure_tv);
        dialog_sure_tv.setOnClickListener(view -> {
            Number currentItem1 = dialog_number_wheel_1.getCurrentItem();
            int bai = currentItem1.intValue();
            Number currentItem2 = dialog_number_wheel_2.getCurrentItem();
            int shi = currentItem2.intValue();
            Number currentItem3 = dialog_number_wheel_3.getCurrentItem();
            int ge = currentItem3.intValue();
            int distance = (bai * 100) + (shi * 10) + ge;
            // 选负号后返回有符号整数；“-000”自然归一为 0，不生成额外的负零状态。
            if (allowNegative && "-".equals(dialog_sign_wheel.getCurrentItem())) {
                distance = -distance;
            }
            onNumberSelectedListener.onNumberSelected(postion, distance);
            dismiss();
        });
        return dialog_view;
    }

    private List<Integer> setRange(int min, int max, int step) {
        int minValue = Math.min(min, max);
        int maxValue = Math.max(min, max);
        // 指定初始容量，避免OutOfMemory
        int capacity = (maxValue - minValue) / step;
        List<Integer> data = new ArrayList<>(capacity);
        for (int i = minValue; i <= maxValue; i = i + step) {
            data.add(i);
        }
        return data;
    }
}
