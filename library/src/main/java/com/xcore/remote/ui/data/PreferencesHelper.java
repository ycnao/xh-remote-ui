package com.xcore.remote.ui.data;

import android.content.Context;

import com.xcore.remote.ui.app.JxConstant;
import com.xcore.libs.data.PreferencesUtil;

/**
 * author：created by 闹闹 on 2025/10/30
 * version：v1.0.0
 */
public class PreferencesHelper {

    public static Context mContext;
    public static PreferencesHelper helper;


    public static void setContext(Context mContext) {
        PreferencesHelper.mContext = mContext;
    }

    /**
     * 获取单例
     */
    public static PreferencesHelper instance() {
        if (helper == null) {
            synchronized (PreferencesHelper.class) {
                helper = new PreferencesHelper();
            }
        }
        return helper;
    }

    //状态
    public void setBoolean(String stateKey, boolean data) {
        PreferencesUtil.putBoolean(mContext, stateKey, data);
    }

    public boolean getBoolean(String stateKey) {
        return PreferencesUtil.getBoolean(mContext, stateKey, false);
    }

    public void setString(String stateKey, String data) {
        PreferencesUtil.putString(mContext, stateKey, data);
    }

    public String getString(String stateKey) {
        return PreferencesUtil.getString(mContext, stateKey, "");
    }

    public String getString(String stateKey, String defaultValue) {
        return PreferencesUtil.getString(mContext, stateKey, defaultValue);
    }

    public String getStringArray(String stateKey, int arrayId) {
        String[] array = mContext.getResources().getStringArray(arrayId);
        return PreferencesUtil.getString(mContext, stateKey, array[0]);
    }

    //状态
    public void setState(String stateKey, boolean data) {
        PreferencesUtil.putBoolean(mContext, stateKey, data);
    }

    public boolean getState(String stateKey) {
        return PreferencesUtil.getBoolean(mContext, stateKey, false);
    }

    //语言
    public void setLanguage(String key, String value) {
        PreferencesUtil.putString(mContext, key, value);
    }

    public String getLanguage(String key) {
        return PreferencesUtil.getString(mContext, key, "");
    }


    public void setConfigType(String data) {
        PreferencesUtil.putString(mContext, JxConstant.remoteKey.CONFIG_TYPE_KEY, data);
    }

    public String getConfigType() {
        return PreferencesUtil.getString(mContext, JxConstant.remoteKey.CONFIG_TYPE_KEY, "");
    }


}
