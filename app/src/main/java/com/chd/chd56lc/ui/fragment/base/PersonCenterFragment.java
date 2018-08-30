package com.chd.chd56lc.ui.fragment.base;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.entity.CouponMsgBean;
import com.chd.chd56lc.entity.UserInfoBean;
import com.chd.chd56lc.manager.CheckDeposeManager;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.PersonCenterPresenter;
import com.chd.chd56lc.mvp.view.IPersonCenterView;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.ui.activity.investment.AssetAnalyzeActivity;
import com.chd.chd56lc.ui.activity.investment.MyCouponActivity;
import com.chd.chd56lc.ui.activity.investment.MyInvestmentActivity;
import com.chd.chd56lc.ui.activity.investment.TransactionRecordListActivity;
import com.chd.chd56lc.ui.activity.personCenter.MyBankInfoActivity;
import com.chd.chd56lc.ui.activity.personCenter.PersonSetActivity;
import com.chd.chd56lc.ui.activity.personCenter.RechargeActivity;
import com.chd.chd56lc.ui.activity.personCenter.RegisterActivity;
import com.chd.chd56lc.ui.activity.personCenter.WithdrawDepositActivity;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.utils.AppUtil;
import com.chd.chd56lc.utils.DateToString;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.NewTwoSideTextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("SetTextI18n")
public class PersonCenterFragment extends BaseFragment implements IPersonCenterView {

    @BindView(R.id.iv_person_icon)
    ImageView ivPersonIcon;                 //个人头像
    @BindView(R.id.iv_person_info)
    ImageView ivPersonInfo;                 //是否实名
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;                  //用户名
    @BindView(R.id.tv_time_hour)
    TextView tvTimeHour;                  //时刻
    @BindView(R.id.iv_visible)
    ImageView ivVisible;                    //总资金明灭按钮
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;                  //总资金
    @BindView(R.id.tv_total_earn)
    TextView tvTotalEarn;                   //总收益
    @BindView(R.id.tv_history_earn)
    TextView tvHistoryEarn;                 //当前累计收益
    @BindView(R.id.tv_usable_balance)
    TextView tvUsableBalance;               //可用余额
    @BindView(R.id.tv_notify)
    TextView tvNotify;                      //消息
    @BindView(R.id.tv_my_invest)
    TextView tvMyInvest;                    //我的投资
    @BindView(R.id.ll_asset_analysis)
    LinearLayout llAssetAnalysis;                    //资产分析
    @BindView(R.id.rl_total_earn_fix)
    RelativeLayout rlTotalEarnFix;                    //总资产
    @BindView(R.id.rl_total_money_fix)
    RelativeLayout rlTotalMoneyFix;                    //总收益
    @BindView(R.id.tv_my_transfer)
    TextView tvMyTransfer;                  //我的转让
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;                  //充值
    @BindView(R.id.tv_withdraw_deposit)
    TextView tvWithdrawDeposit;                  //提现
    @BindView(R.id.tv_available_envelopes)
    TextView tvAvailableEnvelopes;          //可用红包
    @BindView(R.id.tv_interest_rate_coupon)
    TextView tvInterestRateCoupon;          //可用加息券
    @BindView(R.id.ntv_bind_bank)
    NewTwoSideTextView ntvBindBank;         //银行卡
    @BindView(R.id.ntv_risk_evaluation)
    NewTwoSideTextView ntvRiskEvaluation;   //风险测评
    @BindView(R.id.ntv_my_customer_service)
    NewTwoSideTextView ntvMyCustomerService;//我的客服
    @BindView(R.id.srf_refresh_load)
    SmartRefreshLayout srfRefreshLoad;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_no_login)
    RelativeLayout rlNoLogin;
    @BindView(R.id.rl_notify)
    RelativeLayout rlNotify;

    private boolean isVisible = true;
    //优惠券界面显示的位置
    private int couponUIItem;
    private UserInfoBean userInfo;
    @Inject
    PersonCenterPresenter personCenterPresenter;

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this))
                .build().inject(this);
        CheckDeposeManager.getInstance().checkOpenDeposet(RxView.clicks(ntvBindBank).throttleFirst(1, TimeUnit.SECONDS), context, new CheckDeposeManager.CheckDepositResult() {
            @Override
            public void checkDepositoryResult(boolean result) {
                if (result) {
                    toActivity(MyBankInfoActivity.class);
                }
            }
        });
        CheckDeposeManager.getInstance().checkDeposeStatus(RxView.clicks(tvRecharge).throttleFirst(1, TimeUnit.SECONDS), context, new CheckDeposeManager.CheckResult() {
            @Override
            public void checkResult(boolean result) {
                if (result) {
                    toActivity(new Intent(context, RechargeActivity.class)
                            .putExtra(UMConstants.CHANNEL, "个人中心"));
                }
            }
        });
        CheckDeposeManager.getInstance().checkDeposeStatus(RxView.clicks(tvWithdrawDeposit).throttleFirst(1, TimeUnit.SECONDS), context, new CheckDeposeManager.CheckResult() {
            @Override
            public void checkResult(boolean result) {
                if (result) {
                    toActivity(WithdrawDepositActivity.class);
                }
            }
        });
        CheckDeposeManager.getInstance().checkDeposeStatus(RxView.clicks(llAssetAnalysis).throttleFirst(2, TimeUnit.SECONDS), context, new CheckDeposeManager.CheckResult() {
            @Override
            public void checkResult(boolean result) {
                if (result) {
                    toActivity(AssetAnalyzeActivity.class);
                }
            }
        });
        CheckDeposeManager.getInstance().checkDeposeStatus(RxView.clicks(rlTotalEarnFix).throttleFirst(2, TimeUnit.SECONDS), context, new CheckDeposeManager.CheckResult() {
            @Override
            public void checkResult(boolean result) {
                if (result) {
                    toActivity(AssetAnalyzeActivity.class);
                }
            }
        });
        CheckDeposeManager.getInstance().checkDeposeStatus(RxView.clicks(rlTotalMoneyFix).throttleFirst(2, TimeUnit.SECONDS), context, new CheckDeposeManager.CheckResult() {
            @Override
            public void checkResult(boolean result) {
                if (result) {
                    startActivity(new Intent(context, AssetAnalyzeActivity.class)
                            .putExtra(AssetAnalyzeActivity.SHOWITEM, 1));
                }
            }
        });
        tvVersion.setText(AppUtil.versionName() + "(" + AppUtil.versionCode() + ")");
        switch (DateToString.getHourMoment()) {
            case 0:
                tvTimeHour.setText("早上好！");
                break;
            case 1:
                tvTimeHour.setText("上午好！");
                break;
            case 2:
                tvTimeHour.setText("中午好！");
                break;
            case 3:
                tvTimeHour.setText("下午好！");
                break;
            case 4:
                tvTimeHour.setText("晚上好！");
                break;
        }
        srfRefreshLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData();
            }
        });
        srfRefreshLoad.setEnableLoadMore(false);
    }


    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_person_center;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


    @OnClick({R.id.iv_person_set, R.id.iv_person_level, R.id.rl_notify, R.id.iv_visible, R.id.ll_my_invest, R.id.ll_my_transfer, R.id.ll_trading_record, R.id.ll_available_envelopes, R.id.ll_interest_rate_coupon,
            R.id.rl_no_login, R.id.rl_history_earn_fix, R.id.ntv_risk_evaluation, R.id.ntv_my_customer_service, R.id.ll_to_register, R.id.tv_to_login, R.id.tv_customer_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_no_login:
                break;
            case R.id.iv_person_set:
                toActivity(PersonSetActivity.class);
                break;
            case R.id.iv_person_level:
                break;
//            case R.id.ntv_bind_bank:
//                toActivity(MyBankInfoActivity.class);
//                break;
            case R.id.iv_visible:
                if (isVisible) {
                    ivVisible.setImageResource(R.mipmap.btn_grzx_yj_b);
                    tvTotalMoney.setText("******");
                    tvTotalEarn.setText("******");
                    tvHistoryEarn.setText("******");
                    tvUsableBalance.setText("******");
                    tvMyInvest.setText("******");
                } else {
                    tvTotalMoney.setText(NumberFormalUtils.numberFormat(userInfo.getTotalAssets(), 2, 2) + "");
                    tvTotalEarn.setText(NumberFormalUtils.numberFormat(userInfo.getAccumlatedProfit(), 2, 2) + "");
                    tvHistoryEarn.setText(NumberFormalUtils.numberFormat(userInfo.getHistoryProfit(), 2, 2) + "");
                    tvUsableBalance.setText(NumberFormalUtils.numberFormat(userInfo.getBalanceAvailable(), 2, 2) + "");
                    tvMyInvest.setText(NumberFormalUtils.numberFormat(userInfo.getMyInvestment(), 2, 2) + "元");
                    ivVisible.setImageResource(R.mipmap.btn_grzx_yj_c);
                }
                isVisible = !isVisible;
                break;
            case R.id.ll_my_invest:
                startActivity(new Intent(context, MyInvestmentActivity.class)
                        .putExtra(MyInvestmentActivity.SHOWITEM, 0));
                break;
            case R.id.ll_my_transfer:
                startActivity(new Intent(context, MyInvestmentActivity.class)
                        .putExtra(MyInvestmentActivity.SHOWITEM, 1));
                break;
            case R.id.ll_trading_record:
            case R.id.rl_history_earn_fix:
                toActivity(TransactionRecordListActivity.class);
                break;
            case R.id.ll_available_envelopes:
                startActivity(new Intent(context, MyCouponActivity.class)
                        .putExtra(MyCouponActivity.SHOW_ITEM, 0));
                break;
            case R.id.ll_interest_rate_coupon:
                startActivity(new Intent(context, MyCouponActivity.class)
                        .putExtra(MyCouponActivity.SHOW_ITEM, 1));
                break;
            case R.id.ntv_risk_evaluation:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.RISK_ASSESS)
                        .putExtra(CommonWebActivity.TITLE, "风险测评")
                );
                break;
            case R.id.ntv_my_customer_service:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.USER_MYSERVICE)
                        .putExtra(CommonWebActivity.TITLE, "客服中心"));
                break;
            case R.id.rl_notify:
                Intent intent = new Intent(context, MyCouponActivity.class);
                intent.putExtra(MyCouponActivity.SHOW_ITEM, couponUIItem);
                toActivity(intent);
                break;
            case R.id.ll_to_register:
                toActivity(RegisterActivity.class);
                break;
            case R.id.tv_to_login:
                toActivity(LoginActivity.class);
                break;
            case R.id.tv_customer_service:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.USER_MYSERVICE)
                        .putExtra(CommonWebActivity.TITLE, "客服中心"));
                break;
        }
    }

    private void initData() {
        if (UserManager.getInstance().isLogin()) {
            rlNoLogin.setVisibility(View.GONE);
            loadData();
        } else {
            rlNoLogin.setVisibility(View.VISIBLE);
            updateUserInfo(new UserInfoBean());
            tvTotalMoney.setText("******");
            tvTotalEarn.setText("******");
            tvHistoryEarn.setText("******");
            tvUsableBalance.setText("******");
        }
    }

    private void loadData() {
        personCenterPresenter.getPersonCenterInfo();
    }

    /**
     * 更新个人信息
     *
     * @param userInfo 用户信息
     */
    @Override
    public void updateUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
        tvTotalMoney.setText(NumberFormalUtils.numberFormat(userInfo.getTotalAssets(), 2, 2) + "");
        tvTotalEarn.setText(NumberFormalUtils.numberFormat(userInfo.getAccumlatedProfit(), 2, 2) + "");
        tvHistoryEarn.setText(NumberFormalUtils.numberFormat(userInfo.getHistoryProfit(), 2, 2) + "");
        tvUsableBalance.setText(NumberFormalUtils.numberFormat(userInfo.getBalanceAvailable(), 2, 2) + "");
        if (!StringUtils.isEmpty(userInfo.getRealName())) {
            if (!StringUtils.isEmpty(userInfo.getIdcardNo()) && userInfo.getRealName().length() < 4) {
                if (Integer.parseInt(userInfo.getIdcardNo().substring(16, 17)) % 2 == 0) {
                    tvPersonName.setText(userInfo.getRealName().substring(0, 1) + "女士");
                    ivPersonIcon.setImageResource(R.mipmap.head_woman);
                } else {
                    tvPersonName.setText(userInfo.getRealName().substring(0, 1) + "先生");
                    ivPersonIcon.setImageResource(R.mipmap.head_men);
                }
            } else
                tvPersonName.setText("");
            ivPersonInfo.setImageResource(R.mipmap.icon_grzx_sfrz_l);
        } else {
            tvPersonName.setText("");
            ivPersonIcon.setImageResource(R.mipmap.head_normal);
            ivPersonInfo.setImageResource(R.mipmap.icon_grzx_sfrz_c);
        }
        if (userInfo.getMyInvestment() == 0) {
            tvMyInvest.setVisibility(View.GONE);
            tvMyInvest.setText("");
        } else {
            tvMyInvest.setVisibility(View.VISIBLE);
            tvMyInvest.setText(NumberFormalUtils.numberFormat(userInfo.getMyInvestment(), 2, 2) + "元");
        }
        if (userInfo.getDebtNum() == 0) {
            tvMyTransfer.setVisibility(View.GONE);
            tvMyTransfer.setText("");
        } else {
            tvMyTransfer.setVisibility(View.VISIBLE);
            tvMyTransfer.setText(userInfo.getDebtNum() + "笔转让中");
        }
        if (userInfo.getRedPackNum() == 0) {
            tvAvailableEnvelopes.setVisibility(View.GONE);
            tvAvailableEnvelopes.setText("");
        } else {
            tvAvailableEnvelopes.setVisibility(View.VISIBLE);
            tvAvailableEnvelopes.setText((int) userInfo.getRedPackNum() + "元");
        }
        if (userInfo.getCouponNum() == 0) {
            tvInterestRateCoupon.setVisibility(View.GONE);
            tvInterestRateCoupon.setText("");
        } else {
            tvInterestRateCoupon.setVisibility(View.VISIBLE);
            tvInterestRateCoupon.setText(userInfo.getCouponNum() + "张");
        }
        if (userInfo.getBankCard() == null || StringUtils.isEmpty(userInfo.getBankCard().getDepositBankCardNo())) {
            ntvBindBank.setValueText("立即绑定");
        } else {
            ntvBindBank.setValueText(userInfo.getBankCard().getDepositBankName() + "("
                    + userInfo.getBankCard().getDepositBankCardNo()
                    .substring(userInfo.getBankCard().getDepositBankCardNo().length() - 4) + ")");
        }

        switch (userInfo.getRaLevel()) {
            case 0:
                ntvRiskEvaluation.setValueText("保守型");
                break;
            case 2:
                ntvRiskEvaluation.setValueText("积极型");
                break;
            case 1:
                ntvRiskEvaluation.setValueText("稳健型");
                break;
            case 3:
                ntvRiskEvaluation.setValueText("激进型");
                break;
            default:
                ntvRiskEvaluation.setValueText("");
                break;
        }
    }

    /**
     * 更新优惠券信息
     *
     * @param couponMsgBean
     */
    @Override
    public void updateCouponMsg(CouponMsgBean couponMsgBean) {
        if (couponMsgBean != null) {
            if (couponMsgBean.getType() == 2) couponUIItem = 1;
            else couponUIItem = 0;
            rlNotify.setVisibility(View.VISIBLE);
            tvNotify.setText(couponMsgBean.getDesc());
        }
    }


    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (srfRefreshLoad.isRefreshing())
            srfRefreshLoad.finishRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (personCenterPresenter != null)
            personCenterPresenter.onUnsubscribe();
    }

}
