package com.chd.chd56lc.ui.activity.personCenter;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.ModifyPasswordPresenter;
import com.chd.chd56lc.mvp.view.IModifyLoginPassword;
import com.chd.chd56lc.ui.base.KeyBoardBaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.CustomToast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPasswordActivity extends KeyBoardBaseActivity implements IModifyLoginPassword {
    public static final String CHARGE_PASSWORD_TYPE = "charge_password_type";
    @BindView(R.id.et_tel_number)
    EditText etTelNumber;
    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_visible)
    ImageView ivVisible;
    @BindView(R.id.et_re_password)
    EditText etRePassword;
    @BindView(R.id.iv_re_visible)
    ImageView ivReVisible;
    @BindView(R.id.tv_set_password_verify)
    TextView tvSetPasswordVerify;

    @Inject
    CaptchaPresenter captchaPresenter;
    @Inject
    ModifyPasswordPresenter modifyPasswordPresenter;
    @Inject
    CustomToast customToast;

    private int type;//0:忘记密码 1:修改登录密码；

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_set_password;
    }

    @Override
    public void initView() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this)).build()
                .inject(this);
        setTitle("设置登录密码");
        type = getIntent().getIntExtra(CHARGE_PASSWORD_TYPE, 0);
        //绑定获取验证码
        captchaPresenter.getCaptcha(etTelNumber, etVerificationCode, CaptchaPresenter.CaptchaType.MODIFY_LOGIN_PASSWORD.getType(), tvGetVerifyCode);
        super.initView();
    }

    @Override
    public int getBtnId() {
        return R.id.tv_set_password_verify;
    }

    @OnClick({R.id.iv_visible, R.id.iv_re_visible, R.id.tv_set_password_verify})
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
            case R.id.iv_re_visible:
                etRePassword.clearFocus();
                if ("invisible".equals(etRePassword.getTag())) {
                    etRePassword.setTag("visible");
                    etRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivReVisible.setImageResource(R.mipmap.btn_txyzm_yj_nor);
                } else {
                    etRePassword.setTag("invisible");
                    etRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivReVisible.setImageResource(R.mipmap.btn_txyzm_yj_pre);
                }
                break;
            case R.id.tv_set_password_verify:
                String phone = etTelNumber.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etRePassword.getText().toString();
                String code = etVerificationCode.getText().toString();
                if (StringUtils.isEmpty(phone) || phone.length() < 11) {
                    customToast.setText("请输入正确的手机号码");
                    return;
                }
                if (StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword)) {
                    customToast.setText("请输入密码");
                    return;
                }
                if (!StringUtils.password(password)) {
                    customToast.setText("请输入8-20位数字和字母组合密码");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    customToast.setText("两次输入的密码不一致");
                    return;
                }
                if (type == 1)
                    modifyPasswordPresenter.changePassword(phone, password, confirmPassword, code);
                else
                    modifyPasswordPresenter.forgotPassword(phone, password, confirmPassword, code);
                break;
            default:
                break;
        }
    }

    @Override
    public void modifySuccess() {
        customToast.setText("修改成功");
        finish();
    }

    @Override
    public void modifyFailure() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (modifyPasswordPresenter != null) {
            modifyPasswordPresenter.onUnsubscribe();
        }
        if (captchaPresenter != null && captchaPresenter.getCaptchaDisposable() != null && !captchaPresenter.getCaptchaDisposable().isDisposed()) {
            captchaPresenter.getCaptchaDisposable().dispose();
        }
    }
}
