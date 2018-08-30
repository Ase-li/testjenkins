package com.chd.chd56lc.ui.activity.personCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.ParcelableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerRechargeOrWithDrawComponent;
import com.chd.chd56lc.dagger.modules.RechargeOrWithdrawModule;
import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.WithDrawInfo;
import com.chd.chd56lc.manager.CustomUrlSpan;
import com.chd.chd56lc.mvp.presenter.RechargeOrWithdrawPresenter;
import com.chd.chd56lc.mvp.view.IRechargeOrWithdrawView;
import com.chd.chd56lc.ui.activity.investment.TransactionRecordListActivity;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.activity.web.DepositWebActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.EditTextUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * 充值
 */
public class RechargeActivity extends BaseActivity implements IRechargeOrWithdrawView {
    private static final int REQUESTCODE_RECHAREGE = 1;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.iv_small_bank_icon)
    ImageView ivSmallBankIcon;
    @BindView(R.id.tv_bank_info)
    TextView tvBankInfo;
    @BindView(R.id.tv_bank_hint)
    TextView tvBankHint;
    @BindView(R.id.tv_big_recharge)
    TextView tvBigRecharge;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.cb_select)
    CheckBox cbSelect;
    @BindView(R.id.tv_loan_contract)
    TextView tvLoanContract;
    @BindView(R.id.tv_recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.vw_line)
    View vwLine;
    @BindView(R.id.iv_big_bank_icon)
    LinearLayout ivBigBankIcon;
    @BindView(R.id.ll_look_detail)
    LinearLayout llLookDetail;
    @BindView(R.id.tv_handing)
    TextView tvHanding;

    @Inject
    RechargeOrWithdrawPresenter rechargeOrWithdrawPresenter;
    @Inject
    CustomToast customToast;

    Disposable subscribe;

    private String channel;
    private HashMap<String, String> umengMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        channel = getIntent().getStringExtra(UMConstants.CHANNEL);
        if (umengMap == null)
            umengMap = new HashMap<>();
        umengMap.clear();
        if (!StringUtils.isEmpty(channel)) {
            umengMap.put(UMConstants.CHANNEL, channel);
            MobclickAgent.onEvent(this, UMConstants.LOGIN_RECHARGE_EVENT, umengMap);
        }
    }

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerRechargeOrWithDrawComponent.builder().appComponent(BaseApplication.getAppComponent())
                .rechargeOrWithdrawModule(new RechargeOrWithdrawModule(this)).build().inject(this);
    }

    @SuppressLint("CheckResult")
    public void initView() {
        setTitle("快捷充值");
        setRight(null, R.mipmap.btn_ktcgzh_kf);
        tvType.setText(R.string.asset_recharge_money);
        CustomUrlSpan customUrlSpan = TextViewSpanUtils.setURLSpan(tvLoanContract, UIUtils.getString(R.string.recharge_user_quick_recharge_protocol),
                Url.USER_QUICK_RECHARGE_PROTOCOL, UIUtils.getColor(R.color.color_ffbf66));
        CustomUrlSpan customUrlSpan1 = TextViewSpanUtils.setURLSpan(tvLoanContract, UIUtils.getString(R.string.recharge_prepaid_withdrawal_rules),
                Url.PREPAID_WITHDRAWAL_RULES, UIUtils.getColor(R.color.color_ffbf66));
        TextViewSpanUtils.baseMultiSpans(new ParcelableSpan[]{customUrlSpan, customUrlSpan1},
                tvLoanContract, new String[]{UIUtils.getString(R.string.recharge_user_quick_recharge_protocol), UIUtils.getString(R.string.recharge_prepaid_withdrawal_rules)});
        etMoney.setHint(UIUtils.getString(R.string.asset_input_recharge_money));
        subscribe = EditTextUtils.AfterCountTwo(etMoney);
        etMoney.setText(getIntent().getStringExtra("recharge_money"));
    }


    @Override
    public int loadLayoutResID() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void onResume() {
        super.onResume();
        rechargeOrWithdrawPresenter.queryUserBankInfo(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null && !subscribe.isDisposed())
            subscribe.dispose();
        if (rechargeOrWithdrawPresenter != null)
            rechargeOrWithdrawPresenter.onUnsubscribe();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setBankInfo(BankInfoOrBalanceBean bankInfoOrBalanceBean) {
        if (bankInfoOrBalanceBean != null)
            try {
                vwLine.setBackgroundColor(UIUtils.getColor(R.color.color_ffc5ad));
                tvBankHint.setText("银行限额：单笔" + (int)bankInfoOrBalanceBean.getSingleLimit() + "元      " + "单日" + (int)bankInfoOrBalanceBean.getDayLimit() + "元");
                tvBankName.setText(bankInfoOrBalanceBean.getBankName());
                if (bankInfoOrBalanceBean.getProcessAmount() != 0) {
                    tvHanding.setText("您上笔" + bankInfoOrBalanceBean.getProcessAmount() + "元充值正在处理中，请勿重复操作哦~");
                    llLookDetail.setVisibility(View.VISIBLE);
                    tvRechargeMoney.setEnabled(false);
                } else {
                    tvHanding.setVisibility(View.GONE);
                    llLookDetail.setVisibility(View.GONE);
                    tvRechargeMoney.setEnabled(true);
                }
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
        startActivityForResult(new Intent(activity, DepositWebActivity.class)
                .putExtra(DepositWebActivity.TITLE, "充值")
                .putExtra(DepositWebActivity.DEPOSIT, depositLinkBean), REQUESTCODE_RECHAREGE);
    }

    @Override
    public void withdrawInfo(WithDrawInfo withDrawInfo) {

    }

    @OnClick({R.id.tv_recharge_money, R.id.ll_look_detail, R.id.ll_right})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_recharge_money:
                if (StringUtils.isEmpty(etMoney.getText().toString().trim())) {
                    customToast.setText("请输入充值的金额");
                    return;
                }

                if (etMoney.getText().toString().trim().equals("0.") ||
                        etMoney.getText().toString().trim().matches("\\.[0-9]+")) {
                    customToast.setText("请输入充值的金额");
                    return;
                }
                if (cbSelect.isChecked()) {
                    tvRechargeMoney.setEnabled(false);
                    rechargeToAccount();
                } else {
                    customToast.setText("请阅读并同意相关协议");
                }
                break;
            case R.id.ll_look_detail:
                toActivity(TransactionRecordListActivity.class);
                break;
            case R.id.ll_right:
                toActivity(CommonWebActivity.class);
                break;
        }
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        tvRechargeMoney.setEnabled(true);
    }

    private void rechargeToAccount() {
        String rechargeMoneyString = etMoney.getText().toString();
        double rechargeMoney = StringUtils.isEmpty(rechargeMoneyString) ? 0 : Double.parseDouble(rechargeMoneyString);
        MobclickAgent.onEventValue(this, UMConstants.LOGIN_RECHARGE_PAY_EVENT, null, (int) rechargeMoney);
        rechargeOrWithdrawPresenter.rechargeP(rechargeMoney);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_RECHAREGE) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
