package com.xcore.remote.ui.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.xcore.remote.ui.data.PreferencesHelper;

import java.util.Locale;

/**
 * MultiLanguageUtil
 */
public class MultiLanguageUtil {

    public static Context attachBaseContext(Context context) {
        PreferencesHelper.setContext(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            setConfiguration(context);
            return context;
        }
    }

    public static void setLanguage(String language) {
        PreferencesHelper.instance().setLanguage(LocalUtil.LOCALE_LANGUAGE, language);
    }

    public static void setCountry(String country) {
        PreferencesHelper.instance().setLanguage(LocalUtil.LOCALE_COUNTRY, country);
    }

    public static String getLanguage() {
        return PreferencesHelper.instance().getLanguage(LocalUtil.LOCALE_LANGUAGE);
    }

    public static String getCountry() {
        return PreferencesHelper.instance().getLanguage(LocalUtil.LOCALE_COUNTRY);
    }

    /**
     * 设置语言
     */
    public static void setConfiguration(Context context) {
        Locale appLocale = getAppLocale(context);

        //如果本地有语言信息，以本地为主，如果本地没有使用默认Locale
        Locale locale = null;
        String spLanguage = getLanguage();
        String spCountry = getCountry();

        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            if (isSameLocal(appLocale, spLanguage, spCountry)) {
                locale = appLocale;
            } else {
                locale = new Locale(spLanguage, spCountry);
            }
        } else {
            locale = appLocale;
        }

        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale appLocale = getAppLocale(context);

        Log.i("language0", appLocale.getLanguage() + "--------" + appLocale.getCountry() + "--------" + appLocale.toLanguageTag());
        LocalUtil.SYSTEM_LANGUAGE = appLocale.getLanguage();
        LocalUtil.SYSTEM_COUNTRY = appLocale.getCountry();
        LocalUtil.SYSTEM_TOLANGUAGETAG = appLocale.toLanguageTag();

        //如果本地有语言信息，以本地为主，如果本地没有使用默认Locale
        Locale locale = null;
        String spLanguage = getLanguage();
        String spCountry = getCountry();
        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            if (isSameLocal(appLocale, spLanguage, spCountry)) {
                locale = appLocale;
            } else {
                locale = new Locale(spLanguage, spCountry);
            }
        } else {
            if (LocalUtil.SYSTEM_TOLANGUAGETAG.contains("zh-Hant")) {
                locale = new Locale(LocalUtil.SYSTEM_LANGUAGE, "TW");
            } else if (LocalUtil.SYSTEM_TOLANGUAGETAG.contains("zh-Hans") || LocalUtil.SYSTEM_TOLANGUAGETAG.equals("zh-CN")) {
                locale = new Locale(LocalUtil.SYSTEM_LANGUAGE, "CN");
            } else {
                locale = appLocale;
            }
        }

        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    /**
     * 更改应用语言
     *
     * @param
     * @param locale      语言地区
     * @param persistence 是否持久化
     */
    public static void changeAppLanguage(Context context, Locale locale, boolean persistence) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (locale.getLanguage().equals("en")) {
                configuration.setLocale(Locale.getDefault());
                configuration.setLocale(Locale.ENGLISH);
            } else {
                configuration.setLocale(locale);
                configuration.setLocales(new LocaleList(locale));
            }
            context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);

        if (persistence) {
            setLanguage(locale.getLanguage());
            setCountry(locale.getCountry());
        }
    }

    //获取本地应用的实际的多语言信息
    public static Locale getAppLocale(Context context) {
        //获取应用语言
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = configuration.getLocales().get(0);
        } else {
            locale = configuration.locale;
        }
        return locale;
    }

    //判断sp中和app中的多语言信息是否相同
    public static boolean isSameWithSetting(Context context) {
        Locale locale = getAppLocale(context);
        String language = locale.getLanguage();
        String country = locale.getCountry();

        String spLanguage = getLanguage();
        String spCountry = getCountry();
        if (language.equals(spLanguage) && country.equals(spCountry)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSameLocal(Locale appLocale, String sp_language, String sp_country) {
        String appLanguage = appLocale.getLanguage();
        String appCountry = appLocale.getCountry();
        if (appLanguage.equals(sp_language) && appCountry.equals(sp_country)) {
            return true;
        } else {
            return false;
        }
    }


    public static void initSwLanguageMethod(Context context) {
        String language = getLanguage();
        String area = getCountry();
        Locale newLocale = null;
        if (TextUtils.isEmpty(language)) {
            if (LocalUtil.SYSTEM_TOLANGUAGETAG.contains("zh-Hant")) {
                newLocale = new Locale(LocalUtil.SYSTEM_LANGUAGE, "TW");
            } else {
                newLocale = new Locale(LocalUtil.SYSTEM_LANGUAGE, LocalUtil.SYSTEM_COUNTRY);
            }
        } else {
            newLocale = new Locale(language, area);
        }
        MultiLanguageUtil.changeAppLanguage(context, newLocale, false);
    }
}
