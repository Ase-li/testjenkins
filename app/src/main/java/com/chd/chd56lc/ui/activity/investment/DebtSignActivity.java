package com.chd.chd56lc.ui.activity.investment;

import android.content.Intent;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerDepositComponent;
import com.chd.chd56lc.dagger.modules.DepositModule;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.mvp.presenter.DebtSignPresenter;
import com.chd.chd56lc.mvp.view.IDebtSignView;
import com.chd.chd56lc.ui.activity.web.DepositWebActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.StringUtils;

import javax.inject.Inject;

import butterknife.OnClick;

public class DebtSignActivity extends BaseActivity implements IDebtSignView {

    @Inject
    DebtSignPresenter debtSignPresenter;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_debt_sign;
    }

    @Override
    public void initView() {
        setTitle("手续费签约");
    }

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerDepositComponent.builder().appComponent(BaseApplication.getAppComponent())
                .depositModule(new DepositModule(this)).build().inject(this);
    }

    @OnClick(R.id.tv_debt_sign)
    public void onClick() {
        debtSignPresenter.signTransferP();
    }

    @Override
    public void debtSignStatus(DepositLinkBean depositLinkBean) {
        Intent intent = new Intent();
        if (depositLinkBean != null && !StringUtils.isEmpty(depositLinkBean.getUrl())) {
            intent.setClass(activity, DepositWebActivity.class)
                    .putExtra(DepositWebActivity.DEPOSIT, depositLinkBean)
                    .putExtra("TITLE", "投资");
            toActivity(intent);
            finish();
        } else {
            BaseApplication.getAppComponent().customToast().setText("投资异常请咨询客服");
        }
    }
}
