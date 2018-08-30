package com.chd.chd56lc.ui.activity.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.dagger.component.DaggerLoginComponent;
import com.chd.chd56lc.dagger.modules.LoginModule;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.LoginPresenter;
import com.chd.chd56lc.mvp.view.ILoginView;
import com.chd.chd56lc.ui.activity.investment.SecondLoginActivity;
import com.chd.chd56lc.ui.activity.investment.SecondLoginSetPassWordActivity;
import com.chd.chd56lc.ui.activity.personCenter.RegisterActivity;
import com.chd.chd56lc.ui.activity.personCenter.SetPasswordActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.SoftKeyBroadManager;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {

    public static final String MOBILE = "mobile";
    public static final String CHANNEL = "channel";
    @BindView(R.id.img_phone_clean)
    ImageView imgPhoneClean;
    @BindView(R.id.et_sms_phone)
    EditText etSmsPhone;                //验证码登录账号输入
    @BindView(R.id.et_input_code)
    EditText etInputCode;
    @BindView(R.id.tv_sms_submit)
    TextView tvSmsSubmit;
    @BindView(R.id.img_phone_clean_1)
    ImageView imgPhoneClean1;
    @BindView(R.id.et_password_phone)
    EditText etPasswordPhone;           //密码登录账号输入
    @BindView(R.id.et_input_password)
    EditText etInputPassword;
    @BindView(R.id.tv_password_submit)
    TextView tvPasswordSubmit;
    @BindView(R.id.lly_login_password)
    LinearLayout llyLoginPassword;
    @BindView(R.id.lly_sms_phone)
    RelativeLayout llySmsPhone;
    @BindView(R.id.tv_check_phone)
    TextView tvCheckPhone;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.lly_login_sms)
    LinearLayout llyLoginSms;
    @BindView(R.id.img_password_clean)
    ImageView imgPasswordClean;

    @Inject
    CustomToast customToast;
    @Inject
    LoginPresenter loginPresenter;
    @Inject
    CaptchaPresenter captchaPresenter;

    @BindView(R.id.iv_password_login)
    ImageView ivPasswordLogin;
    @BindView(R.id.iv_sms_login)
    ImageView ivSmsLogin;
    @BindView(R.id.base_view)
    LinearLayout baseView;


    //是否采用验证码登录
    private boolean isSms;
    private String phone;

    //用于 手势与指纹校验进入 为1
    private int channel;

    @Override
    public void initDagger() {
        DaggerLoginComponent.builder().appComponent(BaseApplication.getAppComponent())
                .loginModule(new LoginModule(this))
                .build().inject(this);
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        captchaPresenter.getCaptcha(etSmsPhone, etInputCode, CaptchaPresenter.CaptchaType.LOGIN.getType(), tvSendCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.onEvent(this, UMConstants.LOGIN_EVENT);
    }

    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        phone = getIntent().getStringExtra(MOBILE);
        etPasswordPhone.setText(phone);
        llyLoginPassword.setVisibility(View.VISIBLE);
        llyLoginSms.setVisibility(View.GONE);
        etPasswordPhone.addTextChangedListener(new EditWatcher(etPasswordPhone));
        etInputPassword.addTextChangedListener(new EditWatcher(etInputPassword));
        etSmsPhone.addTextChangedListener(new EditWatcher(etSmsPhone));
        etInputCode.addTextChangedListener(new EditWatcher(etInputCode));
        initListener();

        initKeyBroad();
    }

    private void initKeyBroad() {
        SoftKeyBroadManager softKeyBroadManager = new SoftKeyBroadManager(findViewById(R.id.base_view));
        softKeyBroadManager.addSoftKeyboardStateListener(new SoftKeyBroadManager.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                    baseView.scrollTo(0, 400);
            }

            @Override
            public void onSoftKeyboardClosed() {
                Logger.d("键盘", "消失");
                baseView.scrollTo(0, 0);
            }
        });
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.tv_send_code, R.id.tv_password_submit, R.id.tv_sms_submit, R.id.btn_password_login, R.id.btn_sms_login, R.id.img_password_clean, R.id.img_phone_clean, R.id.img_phone_clean_1, R.id.tv_register_golden, R.id.tv_forget_password})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_password_submit:
                loginByPassword();
                break;
            case R.id.tv_sms_submit:
                loginBySms();
                break;
            case R.id.img_phone_clean:
                etSmsPhone.setText("");
                etSmsPhone.setHint(UIUtils.getString(R.string.phone));
                imgPhoneClean.setVisibility(View.GONE);
                break;
            case R.id.img_phone_clean_1:
                etPasswordPhone.setText("");
                etPasswordPhone.setHint(UIUtils.getString(R.string.phone));
                imgPhoneClean1.setVisibility(View.GONE);
                break;
            case R.id.tv_register_golden:
                toActivity(RegisterActivity.class);
                break;
            case R.id.tv_forget_password:
                toActivity(SetPasswordActivity.class);
                break;
            case R.id.btn_password_login:
                if (llyLoginPassword.getVisibility() == View.VISIBLE) return;
                llyLoginPassword.setVisibility(View.VISIBLE);
                llyLoginSms.setVisibility(View.GONE);
                ivPasswordLogin.setVisibility(View.VISIBLE);
                ivSmsLogin.setVisibility(View.GONE);
                isSms = false;
                break;
            case R.id.btn_sms_login:
                if (llyLoginSms.getVisibility() == View.VISIBLE) return;
                llyLoginPassword.setVisibility(View.GONE);
                llyLoginSms.setVisibility(View.VISIBLE);
                ivPasswordLogin.setVisibility(View.GONE);
                ivSmsLogin.setVisibility(View.VISIBLE);
                isSms = true;
                break;
            case R.id.img_password_clean:
                etInputPassword.setText("");
                etInputPassword.setHint(UIUtils.getString(R.string.input_password));
                imgPasswordClean.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 双因子登录
     *
     * @param loginCode 登录状态
     */
    @Override
    public void login(int loginCode) {
        switch (loginCode) {
            case 0:
            case 1:
                toActivity(new Intent(activity, SecondLoginActivity.class).putExtra(MOBILE, phone).putExtra(SecondLoginActivity.LOGIN_TYPE, loginCode));
                break;
            case 2:
                UserManager.getInstance().saveLogin(true);
                if (getIntent().getIntExtra(CHANNEL, 0) == 1)
                    toActivity(MainActivity.class);
                //测试
//                toActivity(AccountSafeActivity.class);
                finish();
                break;
            case 3:
                startActivity(new Intent(activity, SecondLoginSetPassWordActivity.class).putExtra(MOBILE, phone));
                break;
        }

    }

    class EditWatcher implements TextWatcher {
        private TextView textView;

        public EditWatcher(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isSms) {
                if (etSmsPhone.getText().toString().length() > 10 && etInputCode.getText().toString().length() > 5) {
                    tvSmsSubmit.setEnabled(true);
                } else {
                    tvSmsSubmit.setEnabled(false);
                }
            } else {
                if (etPasswordPhone.getText().toString().length() > 10 && etInputPassword.getText().toString().length() > 5) {
                    tvPasswordSubmit.setEnabled(true);
                } else {
                    tvPasswordSubmit.setEnabled(false);
                }
            }
            if (StringUtils.isEmpty(s.toString())) {
                switch (textView.getId()) {
                    case R.id.et_sms_phone:
                        imgPhoneClean.setVisibility(View.GONE);
                        break;
                    case R.id.et_password_phone:
                        imgPhoneClean1.setVisibility(View.GONE);
                        break;
                    case R.id.et_input_password:
                        imgPasswordClean.setVisibility(View.GONE);
                        break;
                }
            } else {
                switch (textView.getId()) {
                    case R.id.et_sms_phone:
                        imgPhoneClean.setVisibility(View.VISIBLE);
                        break;
                    case R.id.et_password_phone:
                        imgPhoneClean1.setVisibility(View.VISIBLE);
                        break;
                    case R.id.et_input_password:
                        imgPasswordClean.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    /**
     * 密码登录
     */
    private void loginByPassword() {
        String phonePassword = etInputPassword.getText().toString().trim();
        phone = etPasswordPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            customToast.setText("请输入手机号码");
            return;
        }
        if (!StringUtils.isMobileNO(phone)) {
            customToast.setText("请输入正确的手机格式");
            return;
        }

        if (StringUtils.isEmpty(phonePassword)) {
            customToast.setText("请输入登录密码");
            return;
        }
        loginPresenter.login(phone, phonePassword);
    }

    /**
     * 验证码登录
     */
    private void loginBySms() {

        phone = etSmsPhone.getText().toString().trim();
        String captcha = etInputCode.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            customToast.setText("请输入手机号");
            return;
        }
        if (!StringUtils.isMobileNO(phone)) {
            customToast.setText("请输入正确的手机格式");
            return;
        }
        if (StringUtils.isEmpty(captcha)) {
            customToast.setText("请输入验证吗");
            return;
        }

        loginPresenter.loginBySms(phone, captcha);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginPresenter != null)
            loginPresenter.onUnsubscribe();
        if (captchaPresenter != null && captchaPresenter.getCaptchaDisposable() != null && !captchaPresenter.getCaptchaDisposable().isDisposed()) {
            captchaPresenter.getCaptchaDisposable().dispose();
        }
    }

}
