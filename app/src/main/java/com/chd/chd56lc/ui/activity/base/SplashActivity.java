package com.chd.chd56lc.ui.activity.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.chd.chd56lc.R;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {

    private Handler handler;
    private Disposable subscribe;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_splash;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        UserManager.getInstance().openApp();
        handler = new Handler();
        subscribe = new RxPermissions(this).request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.USE_FINGERPRINT)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            handler.postDelayed(new MyRunnable(SplashActivity.this)
                                    , 1500);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
            subscribe = null;
        }
        super.onDestroy();
    }

    static class MyRunnable implements Runnable {
        private WeakReference<Activity> weakReference;

        public MyRunnable(Activity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            if (weakReference.get() != null) {
                weakReference.get().startActivity(new Intent(weakReference.get(), MainActivity.class));
                weakReference.get().finish();
            }
        }
    }
}
