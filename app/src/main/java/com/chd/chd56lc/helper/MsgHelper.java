package com.chd.chd56lc.helper;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.StringUtils;

import java.util.List;

/**
 * @author 作者   shulan
 * @date 创建时间：2016-5-8
 * @parameter
 * @return
 */
public class MsgHelper {
    public static final String TAG = "MsgHelper";
    private static TelephonyManager telMgr;
    public InviteClickCallBack inviteClickCallBack;
    private String SENT = "sms_sent";
    private BroadcastReceiver broadcastReceiver;

    /**
     * 有无sim卡
     */
    public static boolean isHasSimCard(Context context) {
        telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        if (telMgr.getSimState() == TelephonyManager.SIM_STATE_ABSENT ||
                telMgr.getSimSerialNumber() == null || telMgr.getPhoneType() == TelephonyManager
                .PHONE_TYPE_NONE) {
            return false;
        }
        return true;
    }


    /**
     * 验证手机格式
     */
    public boolean isMobileNO(String mobiles) {
        return StringUtils.isMobileNO(mobiles);
    }

    /**
     * 直接调用短信接口发短信，不含发送报告和接受报告
     *
     * @param phoneNumber
     * @param message
     */
    public void sendSMS(String phoneNumber, String message) {
        // 获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }


    public void registerReceiver(Context context) {
        Logger.d(TAG, "registerReceiver");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        inviteClickCallBack.doSomeThing(1, intent.getIntExtra("position", -1));
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Logger.d(TAG, intent.getIntExtra("position", -1) + "onReceive");
                        inviteClickCallBack.doSomeThing(-1, intent.getIntExtra("position", -1));
                        Logger.d(TAG, "send fail");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Logger.d(TAG, intent.getIntExtra("position", -1) + "onReceive");
                        inviteClickCallBack.doSomeThing(-1, intent.getIntExtra("position", -1));
                        Logger.d(TAG, "send fail");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Logger.d(TAG, intent.getIntExtra("position", -1) + "onReceive");
                        inviteClickCallBack.doSomeThing(-1, intent.getIntExtra("position", -1));
                        Logger.d(TAG, "send fail");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Logger.d(TAG, intent.getIntExtra("position", -1) + "onReceive");
                        inviteClickCallBack.doSomeThing(-1, intent.getIntExtra("position", -1));
                        Logger.d(TAG, "send fail");
                        break;
                }
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(SENT));//,new IntentFilter
        // (SENT)
    }

    public void unRegisterReceiver(Context context) {
        Logger.d(TAG, "unRegisterReceiver");
        context.unregisterReceiver(broadcastReceiver);
    }

    /**
     * 直接调用短信接口发短信，含发送报告
     *
     * @param phoneNumber
     * @param message
     */
    public void sendSMS(Context context, String phoneNumber, String message, int position) {

        Intent intent = new Intent(SENT);
        intent.putExtra("position", position);
        Logger.d(TAG, position + "sendsms");
        PendingIntent sentPI = PendingIntent.getBroadcast(context, position, intent, 0);
        /*String DELIVERED = "sms_delivered";
        PendingIntent deliveredPI = PendingIntent.getActivity(context, 0, new Intent(DELIVERED), 0);
        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent){
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:

                        break;
                    case Activity.RESULT_CANCELED:

                        break;
                }
            }
        }, new IntentFilter(DELIVERED));*/
        // 获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        // 拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, null);
        }

    }

    public interface InviteClickCallBack {
        public void doSomeThing(int code, int position);
    }
}
