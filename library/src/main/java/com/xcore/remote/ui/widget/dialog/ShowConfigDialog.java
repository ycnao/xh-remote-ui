package com.xcore.remote.ui.widget.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.xcore.remote.ui.R;
import com.xcore.remote.ui.widget.callback.OnNumberSelectedListener;
import com.xcore.remote.ui.widget.wheelview.WheelView;
import com.xcore.remote.ui.widget.wheelview.contract.OnWheelChangedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowConfigDialog extends RxDialog {

    private WheelView dialog_number_wheel;

    private int postion;
    private int defValue;
    private TextView dialog_sure_tv;
    private View dialog_view;
    private TextView dialog_cancel_tv;
    private WheelView dialog_number_wheel_1, dialog_number_wheel_2, dialog_number_wheel_3;
    private OnNumberSelectedListener onNumberSelectedListener;
    private int tipErrStringId;
    private int devShowType;

    public ShowConfigDialog(Activity activity, int index, int value, int errTipId, int showType,
                            OnNumberSelectedListener numberSelectedListener) {
        super(activity, R.style.DialogStyle);
        this.postion = index;
        this.defValue = value;
        this.tipErrStringId = errTipId;
        this.onNumberSelectedListener = numberSelectedListener;
        this.devShowType = showType;
    }

    public void setDefValue(int defValue) {
        this.defValue = defValue;
    }

    @NonNull
    @Override
    protected View createContentView() {
        if (1 == devShowType || 3 == devShowType || 4 == devShowType || 5 == devShowType || 6 == devShowType) {
            dialog_view = LayoutInflater.from(activity).inflate(R.layout.dialog_config_dev_0, null);
            dialog_number_wheel = dialog_view.findViewById(R.id.dialog_number_wheel);
            switch (devShowType) {
                case 1:
                    dialog_number_wheel.setData(setRange(1, 100, 1));
                    break;
                case 3:
                    dialog_number_wheel.setData(Arrays.asList(0, 10, 20, 30, 40));
                    break;
                case 4:
                case 5:
                    dialog_number_wheel.setData(setRange(500, 2000, 100));
                    break;
                case 6:
                    dialog_number_wheel.setData(setRange(70, 130, 5));
                    break;
            }

            dialog_number_wheel.setDefaultValue(defValue);
        } else {
            dialog_view = LayoutInflater.from(activity).inflate(R.layout.dialog_config_dev_1, null);
            dialog_number_wheel_1 = dialog_view.findViewById(R.id.dialog_number_wheel_1);
            dialog_number_wheel_2 = dialog_view.findViewById(R.id.dialog_number_wheel_2);
            dialog_number_wheel_3 = dialog_view.findViewById(R.id.dialog_number_wheel_3);
            dialog_number_wheel_1.setData(Arrays.asList(0, 1, 2));
            dialog_number_wheel_2.setData(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
            dialog_number_wheel_3.setData(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
            dialog_number_wheel_1.setDefaultValue(defValue / 100);
            dialog_number_wheel_2.setDefaultValue((defValue / 10) % 10);
            dialog_number_wheel_3.setDefaultValue(defValue % 10);
            dialog_number_wheel_1.setOnWheelChangedListener(new OnWheelChangedListener() {
                @Override
                public void onWheelScrolled(WheelView view, int offset) {
                }

                @Override
                public void onWheelSelected(WheelView view, int position) {
                    if (2 == position) {
                        dialog_number_wheel_2.setDefaultValue(0);
                        dialog_number_wheel_3.setDefaultValue(0);
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
                    Number currentItem1 = dialog_number_wheel_1.getCurrentItem();
                    int bai = currentItem1.intValue();
                    if (bai > 1) {
                        dialog_number_wheel_1.setDefaultValue(1);
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
                    Number currentItem1 = dialog_number_wheel_1.getCurrentItem();
                    int bai = currentItem1.intValue();
                    if (bai > 1) {
                        dialog_number_wheel_1.setDefaultValue(1);
                    }
                }

                @Override
                public void onWheelScrollStateChanged(WheelView view, int state) {
                }

                @Override
                public void onWheelLoopFinished(WheelView view) {
                }
            });

        }

        dialog_cancel_tv = dialog_view.findViewById(R.id.dialog_cancel_tv);
        dialog_cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        dialog_sure_tv = dialog_view.findViewById(R.id.dialog_sure_tv);
        dialog_sure_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (1 == devShowType || 3 == devShowType || 4 == devShowType || 5 == devShowType || 6 == devShowType) {
                    Number currentItem = dialog_number_wheel.getCurrentItem();
                    int checkInt = currentItem.intValue();
                    if (defValue != checkInt) {
                        onNumberSelectedListener.onNumberSelected(postion, checkInt);
                        dismiss();
                    } else {
                        Toast.makeText(activity, tipErrStringId, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Number currentItem1 = dialog_number_wheel_1.getCurrentItem();
                    int bai = currentItem1.intValue();
                    Number currentItem2 = dialog_number_wheel_2.getCurrentItem();
                    int shi = currentItem2.intValue();
                    Number currentItem3 = dialog_number_wheel_3.getCurrentItem();
                    int ge = currentItem3.intValue();
                    int distance = (bai * 100) + (shi * 10) + ge;
                    if (defValue != distance) {
                        onNumberSelectedListener.onNumberSelected(-1, distance);
                        dismiss();
                    } else {
                        Toast.makeText(activity, tipErrStringId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
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
