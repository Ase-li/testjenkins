package com.chd.chd56lc.ui.activity.investment;

import android.widget.EditText;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerLoginComponent;
import com.chd.chd56lc.dagger.modules.LoginModule;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.LoginPresenter;
import com.chd.chd56lc.mvp.view.ILoginView;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.ui.base.KeyBoardBaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.CustomToast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SecondLoginSetPassWordActivity extends KeyBoardBaseActivity implements ILoginView {
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repassword)
    EditText etRepassword;

    @Inject
    LoginPresenter loginPresenter;
    @Inject
    CustomToast customToast;

    private String phone;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_second_login_set_pass_word;
    }

    @Override
    public void initView() {
        DaggerLoginComponent.builder().appComponent(BaseApplication.getAppComponent())
                .loginModule(new LoginModule(this)).build().inject(this);
        setTitle("设置登录密码");
        phone = getIntent().getStringExtra("mobile");
        super.initView();
    }

    @Override
    public int getBtnId() {
        return R.id.tv_password_submit;
    }


    @OnClick(R.id.tv_password_submit)
    public void onClick() {
        String password = etPassword.getText().toString();
        String rePassword = etRepassword.getText().toString();
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(rePassword)) {
            customToast.setText("请设置登录密码");
            return;
        }
        if (!password.equals(rePassword)) {
            customToast.setText("两次输入的密码不一致");
            return;
        }
        if (!StringUtils.password(password)) {
            customToast.setText("请输入8位以上数字字母组合");
            return;
        }
        loginPresenter.loginSetPassword(phone, password, rePassword);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginPresenter != null)
            loginPresenter.onUnsubscribe();
    }

    /**
     * 双因子登录
     * 都输入登录索性放在一起
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
