# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 应用层 ANR 规避需要按原字段名读取 SDK 子播放器及检查线程；SDK 升级后须重新核对。
-keepclassmembers class com.skydroid.fpvplayer.FPVWidget {
    *** ijkVideoView;
}
-keepclassmembers class com.skydroid.fpvplayer.ijk.IjkVideoView2 {
    *** checkConnectThread;
}
