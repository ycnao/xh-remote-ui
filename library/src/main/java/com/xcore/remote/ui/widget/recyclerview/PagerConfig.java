package com.xcore.remote.ui.widget.recyclerview;


public class PagerConfig {

    private static final String TAG = "PagerGrid";
    private static boolean sShowLog = false;
    private static int sFlingThreshold = 1000;          // Fling 阀值，滚动速度超过该阀值才会触发滚动
    private static float sMillisecondsPreInch = 60f;    // 每一个英寸滚动需要的微秒数，数值越大，速度越慢


    public static boolean isShowLog() {
        return sShowLog;
    }

    public static void setShowLog(boolean showLog) {
        sShowLog = showLog;
    }

    public static int getFlingThreshold() {
        return sFlingThreshold;
    }

    public static void setFlingThreshold(int flingThreshold) {
        sFlingThreshold = flingThreshold;
    }

    public static float getMillisecondsPreInch() {
        return sMillisecondsPreInch;
    }

    public static void setMillisecondsPreInch(float millisecondsPreInch) {
        sMillisecondsPreInch = millisecondsPreInch;
    }

    public static void LogI(String msg) {
        if (!PagerConfig.isShowLog()) {
            return;
        }
    }

    public static void LogE(String msg) {
        if (!PagerConfig.isShowLog()) {
            return;
        }
    }
}
