package com.chd.chd56lc.ui.activity.investment;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.dagger.component.DaggerRegisterComponent;
import com.chd.chd56lc.dagger.modules.RegisterModule;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.RegisterPresenter;
import com.chd.chd56lc.mvp.view.IRegisterView;
import com.chd.chd56lc.ui.activity.personCenter.AccountSafeActivity;
import com.chd.chd56lc.ui.base.KeyBoardBaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterCheckVerifyActivity extends KeyBoardBaseActivity implements IRegisterView {
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_visible)
    ImageView ivVisible;
    @BindView(R.id.tv_error_hint)
    TextView tvErrorHint;
    @BindView(R.id.btn_complete_register)
    TextView btnCompleteRegister;

    @Inject
    CustomToast customToast;
    @Inject
    RegisterPresenter registerPresenter;
    @Inject
    CaptchaPresenter captchaPresenter;

    private String telePhone;
    //邀请码
    private String invitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.onEvent(this, UMConstants.LOGIN_REGISTER_CAPTCHA_EVENT);
    }

    public void initView() {
        DaggerRegisterComponent.builder().appComponent(BaseApplication.getAppComponent())
                .registerModule(new RegisterModule(this)).build().inject(this);
        setTitle("填写验证码");
        telePhone = getIntent().getStringExtra("telePhone");
        invitation = getIntent().getStringExtra("invitation");
        tvPhoneNumber.setText(telePhone.substring(0, 3) + "****" + telePhone.substring(7));
        captchaPresenter.getCaptcha(telePhone, etVerificationCode, CaptchaPresenter.CaptchaType.REGISTER.getType(), tvGetVerifyCode);
        if (StringUtils.isEmpty(telePhone) || telePhone.length() != 11) {
            finish();
            return;
        }
        super.initView();
    }

    @Override
    public int getBtnId() {
        return R.id.btn_complete_register;
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_register_check_verify;
    }

    /**
     * 注册成功
     */
    @Override
    public void registerSuccess() {
        MobclickAgent.onEvent(this, UMConstants.LOGIN_REGISTER_SUCCESS_EVENT);
        toActivity(AccountSafeActivity.class);
        finish();
    }

    @OnClick({R.id.iv_visible, R.id.btn_complete_register})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_visible:
                etPassword.clearFocus();
                if ("invisible".equals(etPassword.getTag())) {
                    etPassword.setTag("visible");
                    ivVisible.setImageResource(R.mipmap.btn_txyzm_yj_nor);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTag("invisible");
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivVisible.setImageResource(R.mipmap.btn_txyzm_yj_pre);
                }
                break;
            case R.id.btn_complete_register:
                String captcha = etVerificationCode.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (StringUtils.isEmpty(captcha)) {
                    customToast.setText("请输入验证码");
                    return;
                }

                if (!StringUtils.password(password)) {
                    customToast.setText(UIUtils.getString(R.string.check_hint_password_rule));
                    return;
                }
                registerPresenter.register(telePhone, invitation, password, captcha);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerPresenter != null) {
            registerPresenter.onUnsubscribe();
        }
        if (captchaPresenter != null && captchaPresenter.getCaptchaDisposable() != null && !captchaPresenter.getCaptchaDisposable().isDisposed()) {
            captchaPresenter.getCaptchaDisposable().dispose();
        }
    }
}
