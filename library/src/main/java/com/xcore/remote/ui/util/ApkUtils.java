package com.xcore.remote.ui.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.pgyersdk.update.PgyUpdateManager;
import com.xcore.ui.utils.ApkUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApkUtils {


    /**
     * pgy安装
     *
     * @param context
     * @param assetsApkName
     */
    public static void installApkFromAssets(Context context, String assetsApkName) {
        // 2. 复制 Assets 中的 APK 到本地
        File apkFile = ApkUtil.copyAssetsApkToLocal(context, assetsApkName);
        if (apkFile == null || !apkFile.exists()) {
            // 复制失败，提示用户
            return;
        }
        // 3. 调用安装器安装 APK
        PgyUpdateManager.installApk(apkFile);
    }
}
