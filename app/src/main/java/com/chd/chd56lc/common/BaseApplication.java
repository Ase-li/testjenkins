package com.chd.chd56lc.common;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.chd.chd56lc.constant.AppIDConstant;
import com.chd.chd56lc.dagger.component.AppComponent;
import com.chd.chd56lc.dagger.component.DaggerAppComponent;
import com.chd.chd56lc.dagger.modules.AppModule;
import com.chd.chd56lc.entity.MyInvestment;
import com.chd.chd56lc.utils.AppUtil;
import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.widget.CustomToast;
import com.meituan.android.walle.WalleChannelReader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BaseApplication extends TinkerApplication {
    //发布版改为false
    public static boolean IS_DEBUG = true;
    private static BaseApplication instance;
    private static AppComponent appComponent;
    @Inject
    CustomToast customToast;

    public BaseApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.chd.chd56lc.common.SampleApplicationLike");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);
        String channel = WalleChannelReader.getChannel(this);
        // 调试时，将第三个参数改为true
        if (BaseApplication.IS_DEBUG) {
//            Bugly.init(getApplication(), AppIDConstant.TEST_BUGLY_KEY, true);
//            UMConfigure.init(this, AppIDConstant.TEST_UMENG_APPKEY, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
//            UMConfigure.setLogEnabled(true);
//            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
//            JPushInterface.setDebugMode(true);
//            JPushInterface.init(getApplication());
        } else {
//            UMConfigure.init(this, AppIDConstant.UMENG_APPKEY, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
//            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
        }
        Logger.d(AppUtil.getDeviceInfo(this));

        PlatformConfig.setWeixin("wx67689b603cd9e81e", "7b02df8d00aa48c0f5280745564c65c1");
        PlatformConfig.setQQZone("1106825722", "dpBRtGuOvQg8SbpA");
        if (IS_DEBUG) {
            LeakCanary.install(this);
        }
        //整体异常捕捉
        ExceptionCrashUnhandler.getInstance().init(this);
//        AppUtil.fixFocusedViewLeak(this);
        Logger.d("model", Build.MANUFACTURER);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static BaseApplication getInstance() {
        return instance;
    }


    public static List getList() {
        ArrayList<MyInvestment> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(new MyInvestment());
        }
        return objects;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();

            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return res;
    }
}
