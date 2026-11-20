package com.xcore.remote.ui.widget.wheelview.contract;


import com.xcore.remote.ui.widget.wheelview.WheelView;
import com.xcore.remote.ui.widget.wheelview.annotation.ScrollState;


public interface OnWheelChangedListener {


    void onWheelScrolled(WheelView view, int offset);


    void onWheelSelected(WheelView view, int position);

    void onWheelScrollStateChanged(WheelView view, @ScrollState int state);


    void onWheelLoopFinished(WheelView view);

}
