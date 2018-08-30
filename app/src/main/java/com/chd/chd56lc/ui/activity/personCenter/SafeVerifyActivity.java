package com.chd.chd56lc.ui.activity.personCenter;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.ModifyPhonePresenter;
import com.chd.chd56lc.mvp.view.ISafeVerifyView;
import com.chd.chd56lc.ui.base.KeyBoardBaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.CustomToast;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class SafeVerifyActivity extends KeyBoardBaseActivity implements ISafeVerifyView {

    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R.id.tv_password_submit)
    TextView tvPasswordSubmit;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;

    @Inject
    CaptchaPresenter captchaPresenter;
    @Inject
    CustomToast toast;
    @Named("resetPhoneStepOne")
    @Inject
    ModifyPhonePresenter modifyPhonePresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (captchaPresenter != null)
            captchaPresenter.disDisposable();
        if (modifyPhonePresenter != null)
            modifyPhonePresenter.onUnsubscribe();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_safe_verify;
    }

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this))
                .build().inject(this);
    }

    @Override
    public void initView() {
        setTitle("安全验证");
        tvPasswordSubmit.setEnabled(true);
        String phone = UserManager.getInstance().getCurrentUserInfo().getPhone();
        tvPhone.setText(StringUtils.desensitization(phone, 3, 4, "****"));
        captchaPresenter.getCaptcha(phone, etVerificationCode, CaptchaPresenter.CaptchaType.SAFE_CHECK.getType(), tvGetVerifyCode);
        super.initView();
    }

    @Override
    public int getBtnId() {
        return R.id.tv_password_submit;
    }

    @OnClick(R.id.tv_password_submit)
    public void onClick() {
        String code = etVerificationCode.getText().toString();
        if (StringUtils.isEmpty(code) || code.length() < 6) {
            toast.setText("请输入验证码");
            return;
        }
        tvPasswordSubmit.setEnabled(false);
        modifyPhonePresenter.resetPhoneStepOne(code);
    }

    @Override
    public void verifySuccess() {
        toActivity(new Intent(activity, SetPhoneActivity.class));
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        tvPasswordSubmit.setEnabled(true);
    }
}
