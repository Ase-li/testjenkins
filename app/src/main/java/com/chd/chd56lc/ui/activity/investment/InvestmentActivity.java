package com.chd.chd56lc.ui.activity.investment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.ParcelableSpan;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerProjectComponent;
import com.chd.chd56lc.dagger.modules.ProjectModule;
import com.chd.chd56lc.entity.CouponBean;
import com.chd.chd56lc.entity.CouponNum;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.InvestDetail;
import com.chd.chd56lc.manager.CustomUrlSpan;
import com.chd.chd56lc.mvp.presenter.InvestmentPresenter;
import com.chd.chd56lc.mvp.view.IInvestmentView;
import com.chd.chd56lc.ui.activity.personCenter.RechargeActivity;
import com.chd.chd56lc.ui.activity.web.DepositWebActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class InvestmentActivity extends BaseActivity implements IInvestmentView {
    public static final String INVEST_MONEY = "invest_money";
    private static final int COUPON_FOR_RESULT = 0x0001;
    //友盟统计用于判断从哪里进入
    private static final int RECHARGE_FOR_RESULT = 0X002;
    private static final int INVESTMENT_FOR_RESULT = 0X003;

    @BindView(R.id.tv_remain_recoverable_amount)
    TextView tvRemainRecoverableAmount;      //剩余可投
    @BindView(R.id.tv_lock_time)
    TextView tvLockTime;                    //锁定期
    @BindView(R.id.et_invest_money)
    EditText etInvestMoney;                 //投资金额
    @BindView(R.id.tv_expected_earn)
    TextView tvExpectedEarn;                //预期回报
    @BindView(R.id.tv_earn1)
    TextView tvEarn1;                       //附加回报1
    @BindView(R.id.tv_earn2)
    TextView tvEarn2;                       //附加回报2
    @BindView(R.id.tv_bonuses_and_coupon_used)
    TextView tvBonusesAndCouponUsed;        //可用优惠券
    @BindView(R.id.tv_available_balance)
    TextView tvAvailableBalance;            //可用余额
    @BindView(R.id.iv_available_balance)
    ImageView ivAvailableBalance;            //刷新
    @BindView(R.id.tv_need_pay)
    TextView tvNeedPay;                     //需要充值
    @BindView(R.id.tv_bank_card)
    TextView tvBankCard;                    //银行卡号
    @BindView(R.id.tv_fast_payment)
    TextView tvFastPayment;                 //充值类型
    @BindView(R.id.tv_need_recharge)
    TextView tvNeedRecharge;                //需要充值
    @BindView(R.id.rb_frame_select)
    CheckBox rbFrameSelect;              //勾选协议
    @BindView(R.id.rl_need_pay)
    RelativeLayout rlNeedPay;                  //需要支付
    @BindView(R.id.rl_select_recharge_type)
    RelativeLayout rlSelectRechargeType;       //选择充值方式
    @BindView(R.id.rl_need_recharge)
    RelativeLayout rlNeedRecharge;             //需要充值
    @BindView(R.id.tv_loan_contract)
    TextView tvLoanContract;                //协议内容
    @BindView(R.id.tv_bonuses_and_coupon_name)
    TextView tvBonusesAndCouponName;                //优惠券类型
    @BindView(R.id.btn_invest)
    TextView btnInvest;                //充值
    @BindView(R.id.iv_plus)
    ImageView ivPlus;                //加
    @BindView(R.id.iv_subtract)
    ImageView ivSubtract;                //减
    @BindView(R.id.tv_earn_hint)
    TextView tvEarnHint;                //协议约定年化利率  季无忧为预期到期收益

    @Inject
    CustomToast customToast;
    @Inject
    InvestmentPresenter investmentPresenter;

    private int type;   // 2 债权转让，1 无忧标；
    private String id;   //标的id/债权id
    private int interestWay;   //派息方式
    private int investMoney;   //投资金额

    private InvestDetail investDetail;
    //优惠券实体类
    private CouponBean.Item couponBean;

    //umeng
    private String projectType;
    private boolean isAfterRecharge;
    private HashMap<String, String> umengMap;

    private CouponNum couponNum;

    private Animation rotate;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ivAvailableBalance != null)
            ivAvailableBalance.clearAnimation();
        if (investmentPresenter != null)
            investmentPresenter.onUnsubscribe();
    }

    @Override
    public void initDagger() {
        DaggerProjectComponent.builder().appComponent(BaseApplication.getAppComponent())
                .projectModule(new ProjectModule(this)).build().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (umengMap == null)
            umengMap = new HashMap<>();
        umengMap.clear();
        umengMap.put(UMConstants.CHANNEL, getIntent().getStringExtra(UMConstants.CHANNEL));
        umengMap.put(UMConstants.PROJECT_TYPE, projectType);
        MobclickAgent.onEvent(this, UMConstants.LOGIN_INVEST_EVENT, umengMap);
        rotate = AnimationUtils.loadAnimation(this, R.anim.refresh);
    }

    public void initView() {
        setTitle("56金服");
        type = getIntent().getIntExtra(InvestmentDetailActivity.TYPE, 0);
        id = getIntent().getStringExtra(InvestmentDetailActivity.ID);
        investMoney = getIntent().getIntExtra(INVEST_MONEY, 0);
        interestWay = getIntent().getIntExtra(InvestmentDetailActivity.INTEREST_WAY, 0);
        if (type == 0)
            return;
        tvEarnHint.setText("预期到期回报（元）");
        CustomUrlSpan customUrlSpan1 = null;
        switch (type) {
            case 2:
                projectType = "债权转让";
                tvLoanContract.setText(UIUtils.getString(R.string.transfer_letter_of_commitment));
                customUrlSpan1 = TextViewSpanUtils.setURLSpan(tvLoanContract, UIUtils.getString(R.string.assignment_of_claims), Url.ASSIGNMEN_OF_DEBT, UIUtils.getColor(R.color.color_ff4e03));
                break;
            case 1:
                if (interestWay == 1) {
                    projectType = "年无忧";
                } else {
                    projectType = "季无忧";
                }
                tvLoanContract.setText(UIUtils.getString(R.string.transfer_commitment));
                customUrlSpan1 = TextViewSpanUtils.setURLSpan(tvLoanContract, UIUtils.getString(R.string.loan_contract), Url.LOAN_CONTRACT, UIUtils.getColor(R.color.color_ff4e03));
                break;
            default:
                customToast.setText("异常");
                finish();
                break;
        }
        CustomUrlSpan customUrlSpan2 = TextViewSpanUtils.setURLSpan(tvLoanContract, UIUtils.getString(R.string.lending_network_hint), Url.LENDING_NETWORK_HINT, UIUtils.getColor(R.color.color_ff4e03));
        CustomUrlSpan customUrlSpan3 = TextViewSpanUtils.setURLSpan(tvLoanContract, UIUtils.getString(R.string.funds_legal_undertaking), Url.FUNDS_LEGAL_UNDERTAKING, UIUtils.getColor(R.color.color_ff4e03));
        CustomUrlSpan customUrlSpan4 = TextViewSpanUtils.setURLSpan(tvLoanContract, UIUtils.getString(R.string.signature_seal_attorney), Url.SIGNATURE_SEAL_ATTORNEY, UIUtils.getColor(R.color.color_ff4e03));
        if (type == 2)
            TextViewSpanUtils.baseMultiSpans(new ParcelableSpan[]{customUrlSpan1, customUrlSpan2, customUrlSpan3, customUrlSpan4}, tvLoanContract,
                    new String[]{UIUtils.getString(R.string.assignment_of_claims), UIUtils.getString(R.string.lending_network_hint)
                            , UIUtils.getString(R.string.funds_legal_undertaking), UIUtils.getString(R.string.signature_seal_attorney)});
        else
            TextViewSpanUtils.baseMultiSpans(new ParcelableSpan[]{customUrlSpan1, customUrlSpan2, customUrlSpan3, customUrlSpan4}, tvLoanContract,
                    new String[]{UIUtils.getString(R.string.loan_contract), UIUtils.getString(R.string.lending_network_hint)
                            , UIUtils.getString(R.string.funds_legal_undertaking), UIUtils.getString(R.string.signature_seal_attorney)});

        initData();
    }

    @SuppressLint("CheckResult")
    private void initData() {
        investmentPresenter.detailInvest(id, type);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_investment;
    }

    @Override
    public void updateInvestDetail(InvestDetail investDetail) {
        this.investDetail = investDetail;
        if (investDetail == null) return;
        updateData();
    }

    @Override
    public void investment(DepositLinkBean depositLinkBean) {
        Intent intent = new Intent();
        if (depositLinkBean != null && !StringUtils.isEmpty(depositLinkBean.getUrl())) {
            intent.setClass(activity, DepositWebActivity.class)
                    .putExtra(DepositWebActivity.DEPOSIT, depositLinkBean)
                    .putExtra("TITLE", "投资");
            startActivityForResult(intent, INVESTMENT_FOR_RESULT);
        } else {
            BaseApplication.getAppComponent().customToast().setText("投资异常请咨询客服");
        }
    }

    /**
     * 优惠券数量
     *
     * @param couponNum
     */
    @Override
    public void couponNum(CouponNum couponNum) {
        this.couponNum = couponNum;
        couponBean = null;
        if (couponNum.getCouponCount() == 0) {
            tvBonusesAndCouponUsed.setText(UIUtils.getString(R.string.invest_no_enable_coupon));
            tvBonusesAndCouponUsed.setTextColor(UIUtils.getColor(R.color.color_666666));
        } else
            tvBonusesAndCouponUsed.setText(couponNum.getCouponCount() + "张可用");
    }

    @SuppressLint("SetTextI18n")
    private void updateData() {
        tvBankCard.setText(investDetail.getBankCardName() + "(尾号" + investDetail.getBankCardNo() + ")");
        tvRemainRecoverableAmount.setText("剩余可投金额:" + NumberFormalUtils.numberFormat(investDetail.getCanInvestAmount(), 0, 2) + "元");
        tvLockTime.setText("锁定期：" + investDetail.getLockDay() + "天");
        tvAvailableBalance.setText(NumberFormalUtils.numberFormat(investDetail.getUserBalanceAmount(), 2, 2) + "");

        if (type == 2) {
            //刷新时不更新投资金额
            if (investMoney == 0)
                investMoney = (int) investDetail.getCanInvestAmount();//当为债权转让时，投资金额默认全部
        } else {
            //刷新时不更新投资金额
            if (investMoney == 0)
                investMoney = 10000;
        }
        //可投金额小于或等于0时，无法点击加减
        if (investDetail.getCanInvestAmount() <= 0) {
            etInvestMoney.setEnabled(false);
            ivPlus.setEnabled(false);
            ivSubtract.setEnabled(false);
            btnInvest.setText("当前标的可投金额为0");
            investMoney = 0;
            return;
        } else {
            etInvestMoney.setEnabled(true);
            ivPlus.setEnabled(true);
            ivSubtract.setEnabled(true);
            btnInvest.setEnabled(true);
            etInvestMoney.addTextChangedListener(new MyTextWatcher());
            if (investMoney > investDetail.getCanInvestAmount()) {
                etInvestMoney.setText((int) investDetail.getCanInvestAmount() + "");
                investMoney = (int) investDetail.getCanInvestAmount();
            } else
                etInvestMoney.setText(investMoney + "");
        }

        loadCouponNum();
    }

    //加载优惠券数量
    private void loadCouponNum() {
        investmentPresenter.getCouponCount(etInvestMoney, type, id);
    }

    //计算优惠券信息，是否需要充值等
    @SuppressLint("SetTextI18n")
    private void calculate() {
        if (investDetail == null) {
            return;
        }
        tvExpectedEarn.setText(NumberFormalUtils.numberFormat(investMoney * (investDetail.getAnnualRate() / 360 * investDetail.getTerm()), 2, 2) + "");
        try {
            if (investMoney != 0 && investDetail.getPlatformInterestRate() != 0) {
                tvEarn1.setText("+" + NumberFormalUtils.numberFormat(investMoney * investDetail.getPlatformInterestRate() / 360 * investDetail.getPlatformInterestDays(), 2, 2) + "(平台加息)");
            }
            calculateCoupon();
            calculateInvestOrRecharge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //计算红包与加息券
    @SuppressLint("SetTextI18n")
    private void calculateCoupon() {
        if (couponBean != null && Integer.parseInt(etInvestMoney.getText().toString()) >= couponBean.getLimitAmount()) {
            try {
                switch (couponBean.getCouponType()) {
                    case 4:
                        tvBonusesAndCouponUsed.setText("红包" + (int) couponBean.getDiscountAmount() + "元");
                        tvEarn2.setText("");
                        break;
                    case 2:
                        tvBonusesAndCouponUsed.setText("加息券" + NumberFormalUtils.percentFormat(couponBean.getAddInterestRate(), 0, 2));
                        if (investDetail.getPlatformInterestRate() != 0) {
                            if (couponBean.isIfFullInterest() || couponBean.getAddRateDays() > investDetail.getTerm()) {
                                tvEarn2.setText("+" + NumberFormalUtils.numberFormat(Double.parseDouble(etInvestMoney.getText().toString())
                                        * couponBean.getAddInterestRate() / 360 * investDetail.getTerm(), 0, 2) + "(加息券)");
                            } else {
                                tvEarn2.setText("+" + NumberFormalUtils.numberFormat(Double.parseDouble(etInvestMoney.getText().toString())
                                        * couponBean.getAddInterestRate() / 360 * couponBean.getAddRateDays(), 0, 2) + "(加息券)");
                            }
                        } else {
                            if (couponBean.isIfFullInterest() || couponBean.getAddRateDays() > investDetail.getTerm()) {
                                tvEarn1.setText("+" + NumberFormalUtils.numberFormat(Double.parseDouble(etInvestMoney.getText().toString())
                                        * couponBean.getAddInterestRate() / 360 * investDetail.getTerm(), 0, 2) + "(加息券)");
                            } else {
                                tvEarn1.setText("+" + NumberFormalUtils.numberFormat(Double.parseDouble(etInvestMoney.getText().toString())
                                        * couponBean.getAddInterestRate() / 360 * couponBean.getAddRateDays(), 0, 2) + "(加息券)");
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //计算充值或者投资需支付金额
    private void calculateInvestOrRecharge() {
        int investMoney = Integer.parseInt(etInvestMoney.getText().toString());
        //判断是否需要充值，投资金额小于余额与红包的总值表示不需要，反之表示需要
        if (investMoney <= (investDetail.getUserBalanceAmount() + getRedCoupon())) {
            rlNeedPay.setVisibility(View.VISIBLE);
            rlNeedRecharge.setVisibility(View.GONE);
            rlSelectRechargeType.setVisibility(View.GONE);
            if (investMoney != 0) {
                tvNeedPay.setText(NumberFormalUtils.numberFormat(investMoney - getRedCoupon(), 2, 2) + "元");
                btnInvest.setText("确认支付");
            } else {
                btnInvest.setText("投资");
                tvNeedPay.setText("");
            }
        } else {
            rlNeedPay.setVisibility(View.GONE);
            rlNeedRecharge.setVisibility(View.VISIBLE);
            if (investMoney != 0) {
                tvNeedRecharge.setText(NumberFormalUtils.numberFormat(investMoney - investDetail.getUserBalanceAmount() - getRedCoupon(), 2, 2) + "元");
                btnInvest.setText("需充值" + NumberFormalUtils.numberFormat(investMoney - investDetail.getUserBalanceAmount() - getRedCoupon(), 2, 2) + "元");
            } else {
                btnInvest.setText("投资");
                tvNeedRecharge.setText("");
            }
        }

        if (investMoney != 0 && investDetail.getCanInvestAmount() != 0 && investDetail.getCanInvestAmount() >= investMoney) {
            btnInvest.setEnabled(true);
        } else {
            btnInvest.setEnabled(false);
        }
    }

    //获取红包值
    private int getRedCoupon() {
        if (couponBean != null && couponBean.getCouponType() == 4)
            return (int) couponBean.getDiscountAmount();
        else return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case COUPON_FOR_RESULT:
                if (resultCode == RESULT_OK) {
                    couponBean = data.getParcelableExtra(InvestCouponActivity.COUPON);
                    investmentPresenter.setItem(couponBean);
                    if (couponBean == null) {
                        tvBonusesAndCouponUsed.setText(couponNum.getCouponCount() + "张可用");
                        tvEarn2.setText("");
                        if (tvEarn1.getText().toString().contains("加息券"))
                            tvEarn1.setText("");
                    }
                }
                calculateCoupon();
                calculateInvestOrRecharge();
                break;
            case INVESTMENT_FOR_RESULT:
                finish();
                break;
            case RECHARGE_FOR_RESULT:
                isAfterRecharge = true;
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tv_bonuses_and_coupon_used, R.id.rl_available_balance, R.id.btn_invest, R.id.iv_plus, R.id.iv_subtract})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_plus:
                int plus = Integer.parseInt(etInvestMoney.getText().toString()) + 1000;
                if (plus > 0)
                    etInvestMoney.setText(plus + "");
                break;
            case R.id.iv_subtract:
                int subtract = Integer.parseInt(etInvestMoney.getText().toString()) - 1000;
                if (subtract < 0)
                    etInvestMoney.setText(0 + "");
                else
                    etInvestMoney.setText(subtract + "");
                break;
            case R.id.tv_bonuses_and_coupon_used:
                if (investDetail == null || tvBonusesAndCouponUsed.getText().toString().contains(UIUtils.getString(R.string.invest_no_enable_coupon))) {
                    return;
                }
                Intent intent = new Intent(activity, InvestCouponActivity.class)
                        .putExtra(INVEST_MONEY, investMoney)
                        .putExtra(InvestCouponActivity.BID_ID, id)
                        .putExtra(InvestCouponActivity.SCENARIO, type)
                        .putExtra(InvestCouponActivity.SELECTED_COUPON_ID, couponBean != null ? couponBean.getId() : "");
                startActivityForResult(intent, COUPON_FOR_RESULT);
                break;
            case R.id.rl_available_balance:
                tvAvailableBalance.setEnabled(false);
                investmentPresenter.detailInvest(id, type);
                ivAvailableBalance.startAnimation(rotate);
                break;
            case R.id.btn_invest:

                if (!rbFrameSelect.isChecked()) {
                    customToast.setText("请阅读并同意相关协议");
                    return;
                }
                if (investDetail != null && !btnInvest.getText().toString().contains("充值")) {
                    if (investMoney != investDetail.getCanInvestAmount()) {
                        if (investMoney < 100) {
                            customToast.setText("投资金额必须不小于100");
                            return;
                        }
                        if (investMoney % 100 != 0) {
                            customToast.setText("请输入100的倍数");
                            return;
                        }
                    }
                    btnInvest.setEnabled(false);
                    if (umengMap == null)
                        umengMap = new HashMap<>();
                    umengMap.clear();
                    umengMap.put(UMConstants.CHANNEL, isAfterRecharge ? "充值后余额" : "余额");
                    MobclickAgent.onEventValue(this, UMConstants.LOGIN_INVEST_PAY_EVENT, umengMap, investMoney);
                    //投资
                    invest(investMoney);
                }
                //充值
                if (btnInvest.getText().toString().contains("充值")) {
                    startActivityForResult(new Intent(activity, RechargeActivity.class)
                            .putExtra("recharge_money", tvNeedRecharge.getText().toString().replace(",", ""))
                            .putExtra(UMConstants.CHANNEL, "投资页"), RECHARGE_FOR_RESULT);
                }
                break;
        }
    }

    private void invest(double investMoney) {
        if (type == 1)
            investmentPresenter.bidApplyP(id, couponBean != null ? couponBean.getId() : "", investMoney);
        else
            investmentPresenter.buyCreditP(id, couponBean != null ? couponBean.getId() : "", investMoney);
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (investDetail != null && investDetail.getCanInvestAmount() > 0)
            btnInvest.setEnabled(true);
        tvBonusesAndCouponUsed.setEnabled(true);
        tvAvailableBalance.setEnabled(true);
        ivAvailableBalance.clearAnimation();
    }

    class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable s) {
            if (StringUtils.isEmpty(s.toString())) {
                etInvestMoney.setText("0");
                return;
            }
            if (s.length() > 1 && s.toString().startsWith("0")) {
                etInvestMoney.setText(Integer.parseInt(s.toString()) + "");
                return;
            }
            investMoney = Integer.parseInt(s.toString());
            //当金额大于可投金额时，显示最大可投金额
            if (investDetail != null && investMoney > investDetail.getCanInvestAmount()) {
                etInvestMoney.setText((int) investDetail.getCanInvestAmount() + "");
                investMoney = (int) investDetail.getCanInvestAmount();
                return;
            }
            calculate();
        }

    }

}
