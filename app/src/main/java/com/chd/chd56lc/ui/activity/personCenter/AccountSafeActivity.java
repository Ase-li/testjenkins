package com.chd.chd56lc.ui.activity.personCenter;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chd.chd56lc.R;
import com.chd.chd56lc.touchidmodul.core.FingerprintCore;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.widget.dialog.TouchIdDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountSafeActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_fingerprint)
    ImageView tvFingerprint;

    private Handler handler = new Handler();
    private FingerprintCore mFingerprintCore;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_account_safe;
    }

    @Override
    public void initView() {
        setTitle("账户安全保护");
        llBack.setVisibility(View.GONE);
//        mFingerprintCore = new FingerprintCore(activity,false);
//        if (!mFingerprintCore.isHardwareDetected()) {
//            findViewById(R.id.tv_fingerprint).setVisibility(View.GONE);
//        }

    }

    @OnClick({R.id.tv_gesture, R.id.tv_fingerprint, R.id.tv_jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gesture:
                toActivity(CreateGestureActivity.class);
                break;
            case R.id.tv_fingerprint:
                tvFingerprint.setEnabled(false);
                new TouchIdDialog(this, new TouchIdDialog.CallBack() {
                    @Override
                    public void verifyResult(boolean result) {
                        if (result) {
                            tvFingerprint.setEnabled(true);
                            toActivity(MainActivity.class);
                            finish();
                        } else
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    tvFingerprint.setEnabled(true);
                                }
                            }, 30000);
                    }
                }).show();
                break;
            case R.id.tv_jump:
                toActivity(MainActivity.class);
                break;
        }
    }

    @Override
    protected void onPause() {
        if (mFingerprintCore != null) {
            mFingerprintCore.onDestroy();
            mFingerprintCore = null;
        }
        super.onPause();
    }
}
