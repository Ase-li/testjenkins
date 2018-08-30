package com.chd.chd56lc.ui.activity.personCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerBankInfoComponent;
import com.chd.chd56lc.dagger.modules.BankInfoModule;
import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.BankInfoPresenter;
import com.chd.chd56lc.mvp.presenter.UnbindBankActivity;
import com.chd.chd56lc.mvp.view.IMyBankInfoView;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.activity.web.DepositWebActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBankInfoActivity extends BaseActivity implements IMyBankInfoView {

    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.iv_small_bank_icon)
    ImageView ivSmallBankIcon;
    @BindView(R.id.tv_bank_info)
    TextView tvBankInfo;
    @BindView(R.id.iv_big_bank_icon)
    LinearLayout ivBigBankIcon;      //绑定卡是显示
    @BindView(R.id.ll_unbind)
    LinearLayout llUnbind;          //没有绑定卡显示
    @BindView(R.id.tv_add_bank)
    ImageView tvAddBank;
    @BindView(R.id.tv_unbind_bank)
    TextView tvUnbindBank;
    @BindView(R.id.tv_contact_us)
    TextView tvContactUs;
    @BindView(R.id.tv_bank_hint)
    TextView tvBankHint;  //银行限制
    @BindView(R.id.vw_line)
    View vwLine;

    @Inject
    CustomToast customToast;
    @Inject
    BankInfoPresenter bankInfoPresenter;


    @Override
    public void initDagger() {
        DaggerBankInfoComponent.builder().appComponent(BaseApplication.getAppComponent())
                .bankInfoModule(new BankInfoModule(this)).build()
                .inject(this);
    }


    public void initView() {
        setTitle("我的银行卡");
        TextViewSpanUtils.setURLSpanSigle(tvContactUs, UIUtils.getString(R.string.contact_service),
                Url.USER_MYSERVICE, UIUtils.getColor(R.color.color_71b8ff));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(UserManager.getInstance().getCurrentUserInfo().getBankCard().getDepositBankName())) {
            tvAddBank.setEnabled(false);
            bankInfoPresenter.getBankLimitInfo();
        }
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_my_bank_info;
    }

    @OnClick({R.id.tv_add_bank, R.id.tv_unbind_bank})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_add_bank:
                tvAddBank.setEnabled(false);
                bankInfoPresenter.bindBankCard();
                break;
            case R.id.tv_unbind_bank:
                if ("查看限额".equals(tvUnbindBank.getText().toString())) {
                    toActivity(new Intent(activity, CommonWebActivity.class)
                            .putExtra(CommonWebActivity.TITLE, "查看限额")
                            .putExtra(CommonWebActivity.URL, Url.BANK_LIMIT_SHEET));
                } else {
                    toActivity(UnbindBankActivity.class);
                }
                break;
        }
    }

    /**
     * 绑定卡
     *
     * @param depositLinkBean
     */
    @Override
    public void bindBankCard(DepositLinkBean depositLinkBean) {
        tvAddBank.setEnabled(true);
        if (depositLinkBean == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(activity, DepositWebActivity.class)
                .putExtra(DepositWebActivity.DEPOSIT, depositLinkBean)
                .putExtra("TITLE", "绑定银行卡")
        ;
        startActivity(intent);
    }

    /**
     * 解除绑定
     */
    @Override
    public void unbindBankCard() {

    }

    /**
     * 获取银行卡信息
     *
     * @param bankInfoOrBalanceBean
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void updateBankInfo(BankInfoOrBalanceBean bankInfoOrBalanceBean) {
        try {
            if (bankInfoOrBalanceBean != null) {
                tvBankName.setText(bankInfoOrBalanceBean.getBankName());
                tvBankInfo.setText(StringUtils.desensitization(bankInfoOrBalanceBean.getBankCardNo(), 4, 4, " **** **** "));
                ivBigBankIcon.setVisibility(View.VISIBLE);
                llUnbind.setVisibility(View.GONE);
                tvUnbindBank.setText(R.string.asset_unbind_bank);
                vwLine.setBackgroundColor(UIUtils.getColor(R.color.color_ffc5ad));
                int resID = getResources().getIdentifier("img_bank_code_balance_" + bankInfoOrBalanceBean.getBankCode().toLowerCase(), "mipmap", getApplicationInfo().packageName);
                int resBGID = getResources().getIdentifier("img_bank_code_balance_bj_" + bankInfoOrBalanceBean.getBankCode().toLowerCase(), "mipmap", getApplicationInfo().packageName);
                if (resID != 0)
                    ivSmallBankIcon.setImageResource(resID);
                if (resBGID != 0)
                    ivBigBankIcon.setBackground(getResources().getDrawable(resBGID));
                tvBankHint.setText("银行限额：单笔" + (int)bankInfoOrBalanceBean.getSingleLimit() + "元 、单日" + (int)bankInfoOrBalanceBean.getDayLimit() + "元");
            } else {
                ivBigBankIcon.setVisibility(View.GONE);
                llUnbind.setVisibility(View.VISIBLE);
                tvUnbindBank.setText(R.string.asset_check_limit);
            }
            tvAddBank.setEnabled(true);
        } catch (Exception e) {
            Logger.tCatch(e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bankInfoPresenter != null)
            bankInfoPresenter.onUnsubscribe();
    }

}
