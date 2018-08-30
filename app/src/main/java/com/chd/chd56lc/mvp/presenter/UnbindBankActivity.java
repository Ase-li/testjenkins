package com.chd.chd56lc.mvp.presenter;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerBankInfoComponent;
import com.chd.chd56lc.dagger.modules.BankInfoModule;
import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.view.IMyBankInfoView;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.widget.CustomToast;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UnbindBankActivity extends BaseActivity implements IMyBankInfoView {

    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Inject
    BankInfoPresenter bankInfoPresenter;
    @Inject
    CustomToast toast;
    @Inject
    CaptchaPresenter captchaPresenter;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_unbind_bank;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        setTitle("我的银行卡");
        DaggerBankInfoComponent.builder().appComponent(BaseApplication.getAppComponent())
                .bankInfoModule(new BankInfoModule(this))
                .build().inject(this);
        final String phone = UserManager.getInstance().getCurrentUserInfo().getPhone();
        captchaPresenter.getCaptcha(UserManager.getInstance().getCurrentUserInfo().getPhone(),
                etVerificationCode, CaptchaPresenter.CaptchaType.UNBIND_BANK.getType(), tvGetVerifyCode);
        RxView.clicks(tvConfirm).throttleFirst(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        bankInfoPresenter.unbindBankCard(phone, etVerificationCode.getText().toString());
                    }
                });
    }


    /**
     * 绑定卡
     *
     * @param depositLinkBean
     */
    @Override
    public void bindBankCard(DepositLinkBean depositLinkBean) {

    }

    /**
     * 解除绑定
     */
    @Override
    public void unbindBankCard() {
        toast.setText("解绑成功");
        toActivity(MainActivity.class);
    }

    /**
     * 获取银行卡信息
     *
     * @param bankInfoOrBalanceBean
     */
    @Override
    public void updateBankInfo(BankInfoOrBalanceBean bankInfoOrBalanceBean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (captchaPresenter != null)
            captchaPresenter.disDisposable();
    }
}
