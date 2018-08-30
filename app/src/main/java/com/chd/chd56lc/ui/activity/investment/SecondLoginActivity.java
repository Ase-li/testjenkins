package com.chd.chd56lc.ui.activity.investment;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerLoginComponent;
import com.chd.chd56lc.dagger.modules.LoginModule;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.LoginPresenter;
import com.chd.chd56lc.mvp.view.ILoginView;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.ui.base.KeyBoardBaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SecondLoginActivity extends KeyBoardBaseActivity implements ILoginView {
    /**
     * 登录类型 0-需验证手机验证码 1-需验证登录密码
     */
    public static final String LOGIN_TYPE = "login_type";
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_change_phone)
    TextView btnChangePhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.ll_password_login)
    LinearLayout llPasswordLogin;
    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifCode;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_password_submit)
    TextView tvPasswordSubmit;
    @BindView(R.id.rl_verification_code)
    RelativeLayout rlVerificationCode;

    private int login_type;
    private String mobile;

    @Inject
    CaptchaPresenter captchaPresenter;
    @Inject
    LoginPresenter loginPresenter;
    @Inject
    CustomToast customToast;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_second_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (captchaPresenter != null) {
            captchaPresenter.disDisposable();
        }
        if (loginPresenter != null)
            loginPresenter.onUnsubscribe();
    }

    @Override
    public void initView() {
        DaggerLoginComponent.builder().appComponent(BaseApplication.getAppComponent())
                .loginModule(new LoginModule(this))
                .build().inject(this);
        initData();
        super.initView();
    }

    @Override
    public int getBtnId() {
        return R.id.tv_password_submit;
    }

    /**
     * 初始化数据，并显示到界面上
     */
    public void initData() {
        login_type = getIntent().getIntExtra(LOGIN_TYPE, 0);
        mobile = getIntent().getStringExtra("mobile");
        tvPhone.setText(mobile.substring(0, 3) + "****" + mobile.substring(7));
        captchaPresenter.getCaptcha(mobile, etVerificationCode, CaptchaPresenter.CaptchaType.LOGIN.getType(), tvGetVerifCode);
        //login_type==1 表示进行了密码登录，还需验证码登录。！=1，则相反
        if (login_type == 0) {
            rlVerificationCode.setVisibility(View.VISIBLE);
            llPasswordLogin.setVisibility(View.GONE);
        } else {
            rlVerificationCode.setVisibility(View.GONE);
            llPasswordLogin.setVisibility(View.VISIBLE);
        }
        tvPasswordSubmit.setEnabled(true);

    }

    @OnClick({R.id.btn_close, R.id.btn_change_phone, R.id.tv_password_submit, R.id.tv_get_verify_code})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_change_phone:
                finish();
                break;
            case R.id.tv_password_submit:
                if (login_type == 0) {
                    if (StringUtils.isEmpty(etVerificationCode.getText().toString())) {
                        customToast.setText(UIUtils.getString(R.string.input_verification_code));
                        return;
                    }
                    loginPresenter.loginBySms(mobile, etVerificationCode.getText().toString());
                } else {
                    if (StringUtils.isEmpty(etPassword.getText().toString())) {
                        customToast.setText("请输入登录密码");
                        return;
                    }
                    loginPresenter.login(mobile, etPassword.getText().toString());
                }
                break;
        }
    }

    /**
     * 双因子登录
     *
     * @param loginStatus 登录状态
     */
    @Override
    public void login(int loginStatus) {
        switch (loginStatus) {
            case 2:
                UserManager.getInstance().saveLogin(true);
                toActivity(MainActivity.class);
                finish();
                break;
        }
    }
}
