Build starting...
Build image: default
Start: Fri Nov 20 08:49:59 UTC 2026 be601cfee65e
Git:
1.0.1-0-g62b02ba
commit 62b02babceaecb51cdaae6a31d8e9184e89ce6fb
Author: 1050104186@qq.com
Date:   Fri Nov 20 16:48:43 2026 +0800

    新增网络相关逻辑


Init SDKMan
Using JDK from jitpack.yml
Running before_install command:
chmod +x gradlew
Running install command:
./gradlew :library:install -Pgroup=com.github.ycnao -Pversion=$VERSION --console=plain
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8 -Dhttps.protocols=TLSv1.2
Downloading https://services.gradle.org/distributions/gradle-6.5-bin.zip
.........10%..........20%..........30%..........40%.........50%..........60%..........70%..........80%.........90%..........100%

Welcome to Gradle 6.5!

Here are the highlights of this release:
 - Experimental file-system watching
 - Improved version ordering
 - New samples

For more details see https://docs.gradle.org/6.5/release-notes.html

Starting a Gradle Daemon (subsequent builds will be faster)

> Configure project :library
WARNING: Configuration 'compile' is obsolete and has been replaced with 'implementation' and 'api'.
It will be removed in version 5.0 of the Android Gradle plugin.
For more information, see http://d.android.com/r/tools/update-dependency-configurations.html.
File /opt/android-sdk-linux/.android/repositories.cfg could not be loaded.
Checking the license for package Android SDK Build-Tools 29.0.2 in /opt/android-sdk-linux/licenses
License for package Android SDK Build-Tools 29.0.2 accepted.
Preparing "Install Android SDK Build-Tools 29.0.2 (revision: 29.0.2)".
"Install Android SDK Build-Tools 29.0.2 (revision: 29.0.2)" ready.
Installing Android SDK Build-Tools 29.0.2 in /opt/android-sdk-linux/build-tools/29.0.2
"Install Android SDK Build-Tools 29.0.2 (revision: 29.0.2)" complete.
"Install Android SDK Build-Tools 29.0.2 (revision: 29.0.2)" finished.
Checking the license for package Android SDK Platform 32 in /opt/android-sdk-linux/licenses
License for package Android SDK Platform 32 accepted.
Preparing "Install Android SDK Platform 32 (revision: 1)".
"Install Android SDK Platform 32 (revision: 1)" ready.
Installing Android SDK Platform 32 in /opt/android-sdk-linux/platforms/android-32
"Install Android SDK Platform 32 (revision: 1)" complete.
"Install Android SDK Platform 32 (revision: 1)" finished.

> Task :library:preBuild UP-TO-DATE
> Task :library:preReleaseBuild UP-TO-DATE
> Task :library:compileReleaseAidl NO-SOURCE
> Task :library:mergeReleaseJniLibFolders
> Task :library:mergeReleaseNativeLibs NO-SOURCE
> Task :library:stripReleaseDebugSymbols NO-SOURCE
> Task :library:copyReleaseJniLibsProjectAndLocalJars
> Task :library:compileReleaseRenderscript NO-SOURCE
> Task :library:generateReleaseBuildConfig
> Task :library:generateReleaseResValues
> Task :library:generateReleaseResources
> Task :library:packageReleaseResources
> Task :library:javaPreCompileRelease
> Task :library:mergeReleaseShaders
> Task :library:compileReleaseShaders NO-SOURCE
> Task :library:generateReleaseAssets UP-TO-DATE
> Task :library:packageReleaseAssets
> Task :library:packageReleaseRenderscript NO-SOURCE
> Task :library:prepareLintJarForPublish
> Task :library:processReleaseJavaRes NO-SOURCE
> Task :library:writeReleaseAarMetadata
> Task :library:processReleaseManifest
> Task :library:parseReleaseLocalResources

> Task :library:generateReleaseRFile
WARNING: [XmlResourcesTransformer] No mapping for: android/support/FILE_PROVIDER_PATHS
WARNING: [XmlResourcesTransformer] No mapping for: android/support/FILE_PROVIDER_PATHS

> Task :library:compileReleaseKotlin FAILED
e: Incompatible classes were found in dependencies. Remove them from the classpath or use '-Xskip-metadata-version-check' to suppress errors
e: /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/META-INF/library_release.kotlin_module: Module was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (5, 26): Class 'com.xcore.ui.http.RetrofitUtil' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/RetrofitUtil.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (6, 35): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (38, 81): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (40, 27): Class 'com.xcore.ui.http.RetrofitUtil' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/RetrofitUtil.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (42, 9): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (54, 29): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (57, 29): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (63, 17): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (70, 21): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (72, 21): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (85, 80): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (87, 27): Class 'com.xcore.ui.http.RetrofitUtil' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/RetrofitUtil.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (89, 9): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (101, 29): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (104, 29): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (110, 17): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (117, 21): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (119, 21): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (132, 76): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (136, 27): Class 'com.xcore.ui.http.RetrofitUtil' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/RetrofitUtil.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (137, 9): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (149, 29): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (152, 29): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (158, 17): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (165, 21): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (167, 21): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (181, 70): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (206, 33): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (209, 33): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (212, 29): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class
e: /home/jitpack/build/library/src/main/java/com/xcore/remote/ui/http/RequestApiAbstract.kt: (223, 17): Class 'com.xcore.ui.http.callback.HttpCallback' was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.16.
The class is loaded from /home/jitpack/.gradle/caches/transforms-2/files-2.1/507b0ffc4b99dae957b6bfd7148b99cd/jetified-xh-core-ui-1.0.9-api.jar!/com/xcore/ui/http/callback/HttpCallback.class

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':library:compileReleaseKotlin'.
> Compilation error. See log for more details

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 39s
14 actionable tasks: 14 executed
Build tool exit code: 1
⚠️ Build failed. See errors above.
2026-11-20T08:50:39.985135225Z
Exit code: 1