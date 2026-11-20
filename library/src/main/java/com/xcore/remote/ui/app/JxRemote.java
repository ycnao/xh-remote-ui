package com.xcore.remote.ui.app;

import android.app.Application;
import android.content.Context;

import com.pgyersdk.Pgyer;
import com.pgyersdk.PgyerActivityManager;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.crash.PgyerCrashObservable;
import com.tencent.bugly.crashreport.CrashReport;
import com.xcore.ui.crash.CrashCatcher;
import com.xcore.ui.crash.CrashMailSender;

public class JxRemote {

    private static Context mContext;
    private static boolean mRelease;
    private static Application mApplication;


    /**
     * 初始化设置产品版本
     * BuildConfig.APP_RELEASE true-正式版本，false-测试版本
     */
    public static void init(Application application, boolean release) {
        mApplication = application;
        mRelease = release;
        mContext = application.getApplicationContext();
    }

    /**
     * 初始化Pgyer
     */
    public static void initPgyer(String appId) {
        //蒲公英
        Pgyer.setAppId(appId);
        PgyCrashManager.register();
        PgyerActivityManager.set(mApplication);
        PgyerCrashObservable.get().attach((thread, throwable) -> {
        });
    }

    /**
     * 初始化Bugly
     */
    public static void initBugly(String appKey, boolean logger) {
        //bugly
        CrashReport.initCrashReport(mContext, appKey, logger);
    }

    /**
     * 初始化Crash
     */
    public static void initCrashReport(String appName, String authCode) {
        // 1. 安装崩溃捕获（必须在最前面）
        CrashCatcher.install(mContext, appName);
        // 2. 补发上次崩溃的日志
        CrashMailSender.initReport(mContext, authCode);
    }


    public static Context getContext() {
        return mContext;
    }

}
