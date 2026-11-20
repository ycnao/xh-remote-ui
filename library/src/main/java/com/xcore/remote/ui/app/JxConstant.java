package com.xcore.remote.ui.app;

import com.xcore.ui.app.JConstants;

/**
 * 常量配置
 */
public class JxConstant extends JConstants {

    //rtsp
    public static final String RTSP_URL = "rtsp://admin:admin@192.168.144.165:554/h265/ch1/main/av_stream";

    public final static int SP_NULL = 0;
    public final static int SP_TOUCH = 1;
    public final static int SP_ALL = 2;
    public final static int SP_DOWN = 3;
    public final static int UP_REMOTE = 4;
    public final static int UP_DEVICE = 5;
    public final static int UP_SUB_DEVICE = 6;

    //固件类型 0-4G通讯版 1-主控制板(设备) 2-Android 3-子控制板(从设备) 4-视觉
    public final static String FIRMWARE_4G = "0"; //4通讯板
    public final static String FIRMWARE_ZKB = "1"; //主控制板
    public final static String FIRMWARE_SKB = "2"; //子控制板
    public final static String FIRMWARE_VIS = "3"; //视觉控制板
    public final static String FIRMWARE_FKB = "4"; //副控制板
    public final static String FIRMWARE_APK = "5"; //APK


    /**
     * 遥控器相关
     */
    public static final class remoteKey {
        // 开发者
        public static final String DEVELOPER_KEY = "developer_key";
        //摄像头检测
        public static final String CAMERA_TEST_KEY = "camera_test_key";
        //UI主题
        public static final String UI_THEME_KEY = "ui_theme";
        //协议类型
        public static final String PROTOCOL_KEY = "protocol_key";
        public static final String CONFIG_TYPE_KEY = "config_type";
        //版本类型
        public static final String VERSION_TYPE_KEY = "version_type";

    }

    /**
     * 设备相关
     */
    public static final class deviceKey {
        // 开发者
        public static final String DEV_DEVELOPER_KEY = "dev_developer_key";
        //电机强制使能
        public static final String SERVO_FORCE_KEY = "servo_force_key";
        //测试模式
        public static final String TEST_MODE_KEY = "test_mode_key";
        //图传图像切换
        public static final String LIVE_KEY = "live_key";
    }

    /**
     * 视频相关
     */
    public static final class videoKey {

        // 视频流地址
        public static final String RTSP_URL_KEY = "rtsp_url";
        // 硬件解码
        public static final String HARDWARE_STATE_KEY = "hardware_state";
        // 辅助线
        public static final String GUIDE_LINE_KEY = "guidelines_state";
        // 瞄准镜
        public static final String CROSS_LINE_KEY = "activate_scope_state";
        // 画面
        public static final String SET_PLAYER_KEY = "player_set";
        // 截图
        public static final String SET_IMAGES_KEY = "images_set";
        // 录屏
        public static final String SET_VIDEOS_KEY = "videos_set";

    }

    public static final class preferencesKey {

        public static final String IS_FIRST_KEY = "is_first";
        public static final String IS_WELCOME_KEY = "is_welcome";

        public static final String CITY_KEY = "city";
        public static final String ADDRESS_KEY = "address";
        public static final String LATITUDE_KEY = "latitude";
        public static final String LONGITUDE_KEY = "longitude";

    }

    /**
     * 加网络列表数据方式
     * EMPTY - 空加载
     * MORE - 列表滚动到底部加载更多
     * REFRESH -列表下拉刷新
     */
    public static enum LoadNetListDataType {
        EMPTY, MORE, REFRESH
    }


}
