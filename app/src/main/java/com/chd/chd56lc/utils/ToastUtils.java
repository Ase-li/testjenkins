package com.chd.chd56lc.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.widget.CustomToast;


/**
 * Created by li on 2017/9/23.
 */

public class ToastUtils {
    private static CustomToast toast;

    public static void showToast(String s) {
        if (toast == null) {
            toast = new CustomToast(BaseApplication.getInstance());
        }
        toast.init(0, true).setText(s);
    }

    public static class ToastHandler extends Handler {
        /**
         * Use the provided {@link Looper} instead of the default one.
         */
        public ToastHandler(Looper looper) {

            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    showToast("图片上传失败");
                    break;
                case 2:
                    showToast("发送语音失败");
                    break;
            }
        }
    }
}
