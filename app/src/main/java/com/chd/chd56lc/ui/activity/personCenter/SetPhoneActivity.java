package com.chd.chd56lc.ui.activity.personCenter;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.ModifyPhonePresenter;
import com.chd.chd56lc.mvp.view.ISetPhoneView;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.ui.base.KeyBoardBaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class SetPhoneActivity extends KeyBoardBaseActivity implements ISetPhoneView {
    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_password_submit)
    TextView tvConfirm;
    @BindView(R.id.et_phone)
    EditText etPhone;

    @Named("resetPhoneStepTwo")
    @Inject
    ModifyPhonePresenter modifyPhonePresenter;
    @Inject
    CustomToast toast;
    @Inject
    CaptchaPresenter captchaPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (modifyPhonePresenter != null)
            modifyPhonePresenter.onUnsubscribe();
        if (captchaPresenter!=null)
            captchaPresenter.disDisposable();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_set_phone;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this))
                .build().inject(this);
        setTitle("设置新手机号码");
        tvConfirm.setEnabled(true);
        captchaPresenter.getCaptcha(etPhone, etVerificationCode, CaptchaPresenter.CaptchaType.MODIFY_TEL.getType(), tvGetVerifyCode);
        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS)
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        String phone = etPhone.getText().toString();
                        if (StringUtils.isEmpty(phone) || !StringUtils.isMobileNO(phone)) {
                            toast.setText("请输入正确的电话号码");
                            return false;
                        }
                        String code = etVerificationCode.getText().toString();
                        if (StringUtils.isEmpty(code) || code.length() < 6) {
                            toast.setText("请输入验证码");
                            return false;
                        }
                        return true;
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        modifyPhonePresenter.resetPhoneStepTwo(etPhone.getText().toString(), etVerificationCode.getText().toString());
                    }
                });
        super.initView();
    }

    @Override
    public int getBtnId() {
        return R.id.tv_password_submit;
    }


    @Override
    public void setPhoneResult(boolean result) {
        if (result) {
            toast.setText("更新手机号成功");
            toActivity(MainActivity.class);
            finish();
        }

    }
}
