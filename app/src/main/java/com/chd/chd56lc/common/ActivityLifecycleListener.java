package com.chd.chd56lc.common;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.chd.chd56lc.constant.PreferenceConstant;
import com.chd.chd56lc.entity.UserInfoBean;
import com.chd.chd56lc.manager.PreferenceManager;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.ui.activity.base.SplashActivity;
import com.chd.chd56lc.ui.activity.personCenter.GestureLoginActivity;
import com.chd.chd56lc.ui.activity.personCenter.VerifyTouchIdActivity;
import com.chd.chd56lc.utils.Logger;

public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {
    private String tag = "activity_life";
    private int activityCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.i(tag, "onActivityCreated" + activity.toString());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        activityCount++;
        Logger.i(tag, "onActivityStarted" + activity.toString() + "-----activityCount:" + activityCount + (System.currentTimeMillis() - PreferenceManager.getLong(PreferenceConstant.BACK_TIME, 0) > 60 * 1000));
        if (!UserManager.getInstance().isFirstOpenApp() && UserManager.getInstance().isLogin() && activityCount == 1 &&
                (System.currentTimeMillis() - PreferenceManager.getLong(PreferenceConstant.BACK_TIME, 0) > 300 * 1000)
                && UserManager.getInstance().getCurrentUserInfo() != null
                && UserManager.getInstance().getCurrentUserInfo().getSettings() != null) {
            UserInfoBean.SettingsBean currentUserInfo = UserManager.getInstance().getCurrentUserInfo().getSettings();
            if (currentUserInfo.isIfGesture())
                activity.startActivity(new Intent(activity, GestureLoginActivity.class));
            if (currentUserInfo.isIfTouchId())
                activity.startActivity(new Intent(activity, VerifyTouchIdActivity.class));
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Logger.i(tag, "onActivityResumed" + activity.toString());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Logger.i(tag, "onActivityPaused" + activity.toString());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Logger.i(tag, "onActivityStopped" + activity.toString());
        activityCount--;
        if (activityCount == 0) {
            PreferenceManager.saveValue(PreferenceConstant.BACK_TIME, System.currentTimeMillis());
        }

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Logger.i(tag, "onActivitySaveInstanceState" + activity.toString());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Logger.i(tag, "onActivityDestroyed" + activity.toString());
    }
}
