package com.chd.chd56lc.ui.activity.personCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerRechargeOrWithDrawComponent;
import com.chd.chd56lc.dagger.modules.RechargeOrWithdrawModule;
import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.WithDrawInfo;
import com.chd.chd56lc.mvp.presenter.RechargeOrWithdrawPresenter;
import com.chd.chd56lc.mvp.view.IRechargeOrWithdrawView;
import com.chd.chd56lc.ui.activity.web.DepositWebActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.EditTextUtils;
import com.chd.chd56lc.utils.NetUtil;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.chd.chd56lc.widget.dialog.WithdrawDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 提现
 */
public class WithdrawDepositActivity extends BaseActivity implements IRechargeOrWithdrawView {

    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.iv_small_bank_icon)
    ImageView ivSmallBankIcon;
    @BindView(R.id.tv_bank_info)
    TextView tvBankInfo;
    @BindView(R.id.vw_line)
    View vwLine;
    @BindView(R.id.tv_bank_hint)
    TextView tvBankHint;
    @BindView(R.id.iv_big_bank_icon)
    LinearLayout ivBigBankIcon;
    @BindView(R.id.tv_big_recharge)
    TextView tvBigRecharge;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_balance_next)
    TextView tvBalanceNext;
    @BindView(R.id.tv_withdraw_rule)
    TextView tvWithdrawRule;
    @BindView(R.id.tv_handing)
    TextView tvHanding;
    @BindView(R.id.ll_look_detail)
    LinearLayout llLookDetail;

    @Inject
    RechargeOrWithdrawPresenter rechargeOrWithdrawPresenter;
    @Inject
    CustomToast customToast;

    private BankInfoOrBalanceBean bankInfoOrBalanceBean;
    private Disposable subscribe;


    @Override
    public void initDagger() {
        super.initDagger();
        DaggerRechargeOrWithDrawComponent.builder().appComponent(BaseApplication.getAppComponent())
                .rechargeOrWithdrawModule(new RechargeOrWithdrawModule(this)).build().inject(this);

    }

    public void initView() {
        setTitle("提现");
        setRight("", R.mipmap.btn_ktcgzh_kf);
        tvType.setText(R.string.asset_withdraw_money);
        subscribe = EditTextUtils.AfterCountTwo(etMoney);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_withdraw_deposit;
    }

    @OnClick({R.id.tv_big_recharge, R.id.tv_balance_next, R.id.ll_look_detail})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_big_recharge:
                break;
            case R.id.tv_balance_next:
                if (bankInfoOrBalanceBean == null) {
                    return;
                }
                if (StringUtils.isEmpty(etMoney.getText().toString().trim())) {
                    customToast.setText("请输入提现金额");
                    return;
                }

                if (etMoney.getText().toString().trim().equals(".") || etMoney
                        .getText().toString().trim().matches("\\.[0-9]+")) {

                    customToast.setText("请输入提现金额");
                    return;
                }

                if (Double.parseDouble(etMoney.getText().toString().trim() + "") < 20) {
                    customToast.setText("单笔提现金额20元起");
                    return;
                }
                if (Double.parseDouble(etMoney.getText().toString().trim() + "") > bankInfoOrBalanceBean.getBalance()) {
                    customToast.setText("提现金额大于账户余额");
                    return;
                }

                if (!NetUtil.isNetworkAvailable()) {
                    customToast.setText(UIUtils.getString(R.string.not_notwork));
                    return;
                }
                tvBalanceNext.setEnabled(false);
                rechargeOrWithdrawPresenter.queryPersonalWithdrawInfo(etMoney.getText().toString());
                break;
            case R.id.ll_look_detail:
                break;
        }
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        tvBalanceNext.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rechargeOrWithdrawPresenter.queryUserBankInfo(2);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setBankInfo(BankInfoOrBalanceBean bankInfoOrBalanceBean) {
        this.bankInfoOrBalanceBean = bankInfoOrBalanceBean;
        try {
            vwLine.setBackgroundColor(UIUtils.getColor(R.color.color_ffc5ad));
            tvWithdrawRule.setText(bankInfoOrBalanceBean.getWithdrawDesc());
            tvBankHint.setText(UIUtils.getString(R.string.asset_withdraw_to_bank));
            tvBankName.setText(bankInfoOrBalanceBean.getBankName());
            if (bankInfoOrBalanceBean.getProcessAmount() != 0) {
                tvHanding.setText("您上笔" + bankInfoOrBalanceBean.getProcessAmount() + "元提现正在处理中，请勿重复操作哦~");
                tvBalanceNext.setEnabled(false);
                llLookDetail.setVisibility(View.VISIBLE);
            } else {
                tvBalanceNext.setEnabled(true);
                llLookDetail.setVisibility(View.GONE);
                tvHanding.setVisibility(View.GONE);
            }
            etMoney.setHint("可提现金额" + NumberFormalUtils.numberFormat(bankInfoOrBalanceBean.getBalance(),2,2) + "元");
            String bankCardNo = bankInfoOrBalanceBean.getBankCardNo();
            tvBankInfo.setText(bankCardNo.substring(0, 4) + " **** **** " + bankCardNo.substring(bankCardNo.length() - 4));
            int resID = getResources().getIdentifier("img_bank_code_balance_" + bankInfoOrBalanceBean.getBankCode().toLowerCase(), "mipmap", getApplicationInfo().packageName);
            int resBGID = getResources().getIdentifier("img_bank_code_balance_bj_" + bankInfoOrBalanceBean.getBankCode().toLowerCase(), "mipmap", getApplicationInfo().packageName);
            if (resID != 0)
                ivSmallBankIcon.setImageResource(resID);
            if (resBGID != 0)
                ivBigBankIcon.setBackground(getResources().getDrawable(resBGID));
        } catch (Exception e) {

        }
    }

    @Override
    public void rechargeOrWithdraw(DepositLinkBean depositLinkBean) {
        Intent intent = new Intent();
        if (depositLinkBean != null && !StringUtils.isEmpty(depositLinkBean.getUrl())) {
            intent.setClass(activity, DepositWebActivity.class)
                    .putExtra(DepositWebActivity.DEPOSIT, depositLinkBean)
                    .putExtra("TITLE", "提现");
            startActivityForResult(intent, 1);
            finish();
        } else {
            BaseApplication.getAppComponent().customToast().setText("提现异常请咨询客服");
        }
    }

    @Override
    public void withdrawInfo(WithDrawInfo withDrawInfo) {
        new WithdrawDialog(activity, etMoney.getText().toString(), withDrawInfo, new WithdrawDialog.WithdrawCallBack() {

            @Override
            public void withdraw(double withdrawMoney, boolean couponStatus) {
                rechargeOrWithdrawPresenter.withdrawP(withdrawMoney, couponStatus);
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
        if (rechargeOrWithdrawPresenter != null)
            rechargeOrWithdrawPresenter.onUnsubscribe();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1)
                finish();
        }
    }
}
