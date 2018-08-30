package com.chd.chd56lc.common;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.chd.chd56lc.constant.AppIDConstant;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

public class SampleApplicationLike extends DefaultApplicationLike {

    public static final String TAG = "Tinker.SampleApplicationLike";

    public SampleApplicationLike(Application application, int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                 long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //配置渠道，方便统计
        String channel = WalleChannelReader.getChannel(getApplication());
        Bugly.setAppChannel(getApplication(), channel);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        if (BaseApplication.IS_DEBUG) {
            Bugly.init(getApplication(), AppIDConstant.TEST_BUGLY_KEY, true);
            UMConfigure.init(getApplication(), AppIDConstant.TEST_UMENG_APPKEY, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
            UMConfigure.setLogEnabled(true);
            MobclickAgent.setScenarioType(getApplication(), MobclickAgent.EScenarioType.E_DUM_NORMAL);
            JPushInterface.setDebugMode(true);
            JPushInterface.init(getApplication());
        } else {
            Bugly.init(getApplication(), AppIDConstant.BUGLY_KEY, false);
            UMConfigure.init(getApplication(), AppIDConstant.UMENG_APPKEY, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
            MobclickAgent.setScenarioType(getApplication(), MobclickAgent.EScenarioType.E_DUM_NORMAL);
            JPushInterface.setDebugMode(false);
            JPushInterface.init(getApplication());
        }
        PlatformConfig.setWeixin("wx67689b603cd9e81e", "7b02df8d00aa48c0f5280745564c65c1");
        PlatformConfig.setQQZone("1106825722", "dpBRtGuOvQg8SbpA");
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);


    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

}