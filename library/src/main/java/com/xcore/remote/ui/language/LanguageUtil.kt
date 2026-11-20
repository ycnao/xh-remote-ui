package com.xcore.remote.ui.language

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale


object LanguageUtil {

    /**
     * 返回语言
     *
     * @param language 语言
     */
    @JvmStatic
    fun getLanguageVal(language: String?): String {
        return when (language) {
            LocalUtil.LANGUAGE_TAIWAN -> LocalUtil.LOCAL_TAIWAN_LANGUAGE
            LocalUtil.LANGUAGE_ENGLISH -> LocalUtil.LOCAL_ENGLISH_LANGUAGE
            LocalUtil.LANGUAGE_SPAIN -> LocalUtil.LOCAL_SPAIN_LANGUAGE
            LocalUtil.LANGUAGE_RUSSIAN -> LocalUtil.LOCAL_RUSSIAN_LANGUAGE
            LocalUtil.LANGUAGE_JAPANESE -> LocalUtil.LOCAL_JAPANESE_LANGUAGE
            LocalUtil.LANGUAGE_KOREAN -> LocalUtil.LOCAL_KOREAN_LANGUAGE
            else -> LocalUtil.LOCAL_CHINESE_LANGUAGE
        }
    }

    /**
     * 返回语言
     *
     * @param language 语言
     */
    @JvmStatic
    fun getLanguageStr(language: String?): String {
        return when (language) {
            LocalUtil.LOCAL_TAIWAN_LANGUAGE -> LocalUtil.LANGUAGE_TAIWAN
            LocalUtil.LOCAL_ENGLISH_LANGUAGE -> LocalUtil.LANGUAGE_ENGLISH
            LocalUtil.LOCAL_SPAIN_LANGUAGE -> LocalUtil.LANGUAGE_SPAIN
            LocalUtil.LOCAL_RUSSIAN_LANGUAGE -> LocalUtil.LANGUAGE_RUSSIAN
            LocalUtil.LOCAL_JAPANESE_LANGUAGE -> LocalUtil.LANGUAGE_JAPANESE
            LocalUtil.LOCAL_KOREAN_LANGUAGE -> LocalUtil.LANGUAGE_KOREAN
            else -> LocalUtil.LANGUAGE_CHINESE
        }
    }

    @JvmStatic
    fun wrap(context: Context, newLocale: Locale): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createConfigurationResources(context, newLocale)
        } else {
            applyLanguage(context, newLocale)
            context
        }
    }

    private fun applyLanguage(context: Context, newLocale: Locale) {
        val resources = context.resources
        val configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // apply locale
            configuration.setLocale(newLocale)
        } else {
            // updateConfiguration
            configuration.locale = newLocale
            val dm = resources.displayMetrics
            resources.updateConfiguration(configuration, dm)
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun createConfigurationResources(context: Context, newLocale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(newLocale)
        val localeList = LocaleList(newLocale)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
        return context.createConfigurationContext(configuration)
    }
}