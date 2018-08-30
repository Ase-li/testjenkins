package com.chd.chd56lc.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.jpush.android.api.JPushInterface;

public class JPushStartupReceiver extends BroadcastReceiver {
    static final String action_boot = "android.intent.action.BOOT_COMPLETED";
    private static final String TAG = "JPushStartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        JPushInterface.init(context);
        JPushInterface.resumePush(context);
        if (intent.getAction().equals(action_boot)) {
            JPushInterface.init(context);
            // Intent StartIntent=new Intent(context,MainActivity.class);
            // //接收到广播后，跳转到MainActivity
            // StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // context.startActivity(StartIntent);
        }

        JPushInterface.resumePush(context);

        if (JPushInterface.isPushStopped(context)) {
            JPushInterface.resumePush(context);
        } else {
            JPushInterface.resumePush(context);
        }

        JPushInterface.resumePush(context);
    }

}
