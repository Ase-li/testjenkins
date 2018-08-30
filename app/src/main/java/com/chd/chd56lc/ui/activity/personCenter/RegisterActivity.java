package com.chd.chd56lc.ui.activity.personCenter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.ParcelableSpan;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerRegisterComponent;
import com.chd.chd56lc.dagger.modules.RegisterModule;
import com.chd.chd56lc.manager.CustomUrlSpan;
import com.chd.chd56lc.mvp.presenter.RegisterPresenter;
import com.chd.chd56lc.mvp.view.IRegisterView;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.ui.activity.investment.RegisterCheckVerifyActivity;
import com.chd.chd56lc.ui.base.KeyBoardBaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends KeyBoardBaseActivity implements IRegisterView {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_use_invitation)
    TextView btnUseInvitation;
    @BindView(R.id.et_invitation)
    EditText etInvitation;
    @BindView(R.id.btn_register_protocol)
    TextView btnRegisterProtocol;
    @BindView(R.id.btn_to_login)
    TextView btnToLogin;
    @BindView(R.id.rl_invitation)
    RelativeLayout rlInvitation;
    @BindView(R.id.btn_register)
    TextView btnRegister;

    @Inject
    RegisterPresenter registerPresenter;

    private String phone;
    private String invitationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.onEvent(this, UMConstants.LOGIN_REGISTER_EVENT);
    }

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerRegisterComponent.builder().appComponent(BaseApplication.getAppComponent())
                .registerModule(new RegisterModule(this)).build().inject(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerPresenter != null)
            registerPresenter.onUnsubscribe();
    }

    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setTitle("注册");
        setTitleColor(UIUtils.getColor(R.color.color_666666));
        setTitleBgColor(UIUtils.getColor(R.color.color_ffffff));
        setLeft("", R.mipmap.icon_zc_jt1);
        TextViewSpanUtils.setForegroundColor(btnToLogin, "立即登录", UIUtils.getColor(R.color.color_ffbf66));
        CustomUrlSpan privacyAgreement = TextViewSpanUtils.setURLSpan(btnRegisterProtocol, UIUtils.getString(R.string.privacy_agreement), Url.PRIVACY_AGREEMENT, UIUtils.getColor(R.color.color_ff4e03));
        CustomUrlSpan registrationProtocol = TextViewSpanUtils.setURLSpan(btnRegisterProtocol, UIUtils.getString(R.string.registration_protocol), Url.REGISTER_AGREEMENT, UIUtils.getColor(R.color.color_ff4e03));
        TextViewSpanUtils.baseMultiSpans(new ParcelableSpan[]{privacyAgreement, registrationProtocol}, btnRegisterProtocol, new String[]{UIUtils.getString(R.string.privacy_agreement), UIUtils.getString(R.string.registration_protocol), Url.REGISTER_AGREEMENT});
        etPhone.addTextChangedListener(new PhoneTextWatcher());
        super.initView();
    }

    @Override
    public int getBtnId() {
        return R.id.btn_register;
    }


    @Override
    protected int setStatusBarColor() {
        return UIUtils.getColor(R.color.color_ffffff);
    }

    /**
     * 返回一个用于设置界面的布局id
     */
    @Override
    public int loadLayoutResID() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.btn_use_invitation, R.id.btn_unuse_invitation, R.id.btn_register, R.id.btn_to_login})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_use_invitation:
                rlInvitation.setVisibility(View.VISIBLE);
                btnUseInvitation.setVisibility(View.GONE);
                break;
            case R.id.btn_unuse_invitation:
                rlInvitation.setVisibility(View.GONE);
                btnUseInvitation.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_register:
                phone = etPhone.getText().toString();
                invitationCode = etInvitation.getText().toString();
                if (StringUtils.isEmpty(phone) || phone.length() != 11) {
                    BaseApplication.getAppComponent().customToast().setText("请输入11位数字");
                    return;
                }
                if (!StringUtils.isMobileNO(phone)) {
                    BaseApplication.getAppComponent().customToast().setText("请输入正确的手机格式");
                    return;
                }
                btnRegister.setEnabled(false);
                registerPresenter.verifyPhoneRegister(phone);
                break;
            case R.id.btn_to_login:
                toActivity(LoginActivity.class);
                finish();
                break;
        }
    }

    /**
     * 验证成功
     */
    @Override
    public void registerSuccess() {
        startActivity(new Intent(this, RegisterCheckVerifyActivity.class).putExtra("telePhone", phone).putExtra("invitation", invitationCode));
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        btnRegister.setEnabled(true);
    }

    class PhoneTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 10) {
                btnRegister.setEnabled(true);
            } else {
                btnRegister.setEnabled(false);
            }
        }
    }
}
