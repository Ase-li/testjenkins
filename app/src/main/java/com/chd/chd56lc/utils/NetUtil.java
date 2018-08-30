package com.chd.chd56lc.utils;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.chd.chd56lc.common.BaseApplication;

@SuppressLint("DefaultLocale")
public class NetUtil {
    private static final String TAG = "NetUtil";

    public enum NetType {
        NONE, UNKNOW, WIFI, MOBILE_GPRS, MOBILE_EDGE, MOBILE_3G;
    }

    /**
     * 检测当前网络是否连接可用
     *
     * @return true|false
     */
    public static boolean isNetworkAvailable() {

        if (BaseApplication.getInstance() == null) {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 获取当前手机连接网络
     *
     * @param context
     * @return
     */
    private static NetworkInfo getCurrentActiveNetworkInfo(Context context) {
        NetworkInfo networkInfo = null;
        // 获取手机所有连接管理对象（包括对wi-fi,net,gsm,cdma等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService
                (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            Log.d(TAG, "Current Active Network : " + networkInfo);

        }
        return networkInfo;
    }

    public static NetType getCurrentNetType(Context context) {
        NetType type = NetType.NONE;

        // 获取当前活动的网络
        NetworkInfo info = getCurrentActiveNetworkInfo(BaseApplication.getInstance());
        if (info == null) {
            return type;
        }

        // 判断当前网络是否已经连接
        if (info.getState() == NetworkInfo.State.CONNECTED) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                type = NetType.WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                String subTypeName = info.getSubtypeName().toUpperCase();

                if (subTypeName.indexOf("GPRS") > -1) {
                    type = NetType.MOBILE_GPRS;
                } else if (subTypeName.indexOf("EDGE") > -1) {
                    type = NetType.MOBILE_EDGE;
                } else {
                    type = NetType.MOBILE_3G;
                }
            } else {
                type = NetType.UNKNOW;
            }
        } else if (info.getState() == NetworkInfo.State.CONNECTING) {
            type = NetType.UNKNOW;
            System.out.println("connecting " + info.getType());
        }

        return type;
    }

    /*
     * 打开设置网络界面
     * */
    public static void setNetworkMethod(final Context context) {
        //提示对话框
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new
                DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        //判断手机系统的版本  即API大于10 就是3.0或以上版本
                        intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        context.startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
