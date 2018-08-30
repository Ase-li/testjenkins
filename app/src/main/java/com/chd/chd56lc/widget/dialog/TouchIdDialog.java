package com.chd.chd56lc.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.touchidmodul.core.FingerprintCore;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;

/**
 * 用于某种动态情况的提醒，如更新停服
 * Created by li on 2018/3/24.
 */

public class TouchIdDialog extends Dialog implements View.OnClickListener {

    private LayoutInflater inflater;

    private TextView btnConfirm;

    private TextView tvHint1;
    private TextView tvHint2;

    private FrameLayout flDialogContent;
    private CallBack callBack;
    private CustomToast toast;

    private FingerprintCore mFingerprintCore;

    public TouchIdDialog(@NonNull Context context, CallBack callBack) {
        super(context, R.style.Dialog);
        inflater = LayoutInflater.from(context);
        this.callBack = callBack;
        toast = BaseApplication.getAppComponent().customToast();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.transparency_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_base_multe);
        setCanceledOnTouchOutside(false);
        initView();
    }

    public void initView() {
        btnConfirm = findViewById(R.id.btn_dialog_confirm);
        flDialogContent = findViewById(R.id.fl_invest_content);
        findViewById(R.id.iv_dialog_close).setVisibility(View.GONE);
        btnConfirm.setOnClickListener(this);
        btnConfirm.setText("取消");
        View view = inflater.inflate(R.layout.dialog_touch_id, null);
        tvHint1 = view.findViewById(R.id.tv_touch_hint1);
        tvHint2 = view.findViewById(R.id.tv_touch_hint2);
        flDialogContent.addView(view);
        initFingerprintCore();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startFingerprintRecognition();
            }
        }, 200);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_confirm:
                callBack.verifyResult(false);
                dismiss();
                break;
        }
    }

    public interface CallBack {
        void verifyResult(boolean result);
    }

    private void initFingerprintCore() {
        mFingerprintCore = new FingerprintCore(getContext(), false);
        mFingerprintCore.setFingerprintManager(mResultListener);
    }

    /**
     * 开始指纹识别
     */
    private void startFingerprintRecognition() {
        if (mFingerprintCore.isSupport()) {
            if (!mFingerprintCore.isHasEnrolledFingerprints()) {
                toast.setText(UIUtils.getString(R.string.fingerprint_recognition_not_enrolled));
//                FingerprintUtil.openFingerPrintSettingPage(getContext());
                callBack.verifyResult(false);
                dismiss();
                return;
            }
            if (!mFingerprintCore.isAuthenticating()) {
                mFingerprintCore.startAuthenticate();
            }
        } else {
            toast.setText(UIUtils.getString(R.string.fingerprint_recognition_not_support));
            callBack.verifyResult(false);
            dismiss();
        }
    }

    private void cancelFingerprintRecognition() {
        if (mFingerprintCore.isAuthenticating()) {
            mFingerprintCore.cancelAuthenticate();
            resetGuideViewState();
        }
    }

    private FingerprintCore.IFingerprintResultListener mResultListener = new FingerprintCore.IFingerprintResultListener() {
        @Override
        public void onAuthenticateSuccess() {
            toast.setText(UIUtils.getString(R.string.fingerprint_recognition_success));
            callBack.verifyResult(true);
            dismiss();
        }

        @Override
        public void onAuthenticateFailed(int helpId) {
            tvHint1.setText(UIUtils.getString(R.string.fingerprint_recognition_failed));
        }

        @Override
        public void onAuthenticateError(int errMsgId) {
            resetGuideViewState();
            toast.setText(UIUtils.getString(R.string.fingerprint_recognition_error));
            callBack.verifyResult(false);
            dismiss();
        }

        @Override
        public void onStartAuthenticateResult(boolean isSuccess) {

        }
    };

    private void resetGuideViewState() {
        tvHint1.setText(UIUtils.getString(R.string.safe_56_touch_id));
        tvHint2.setText(UIUtils.getString(R.string.safe_touch_id_default));
    }

    @Override
    public void dismiss() {
        if (mFingerprintCore != null) {
            mFingerprintCore.onDestroy();
            mFingerprintCore = null;
        }
        mResultListener = null;
        super.dismiss();

    }
}
