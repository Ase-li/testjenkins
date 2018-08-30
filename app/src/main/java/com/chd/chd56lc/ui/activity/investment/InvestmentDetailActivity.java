package com.chd.chd56lc.ui.activity.investment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerProjectComponent;
import com.chd.chd56lc.dagger.modules.ProjectModule;
import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.manager.CheckDeposeManager;
import com.chd.chd56lc.mvp.presenter.InvestmentDetailPresenter;
import com.chd.chd56lc.mvp.view.IInvestmentDetailView;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.adapter.InvestIntroduceAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.DateToString;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.widget.MyProgressBar;
import com.chd.chd56lc.widget.MyViewPager1;
import com.chd.chd56lc.widget.SpringScrollView;
import com.chd.chd56lc.widget.dialog.CounterDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class InvestmentDetailActivity extends BaseActivity implements IInvestmentDetailView {
    public static final String TYPE = "type";
    public static final String ID = "id";
    public static final String PROJECT_ID = "project_id";
    public static final String INTEREST_WAY = "interest_way";

    @BindView(R.id.layoutAll)
    public RelativeLayout layoutAll;
    @BindView(R.id.iv_title)
    TextView ivTitle;                          //项目类型
    @BindView(R.id.tv_product_rate)
    TextView tvProductRate;                     //协议约定年化利率
    @BindView(R.id.tv_surplus_fund)
    TextView tvSurplusFund;                     //剩余金额
    @BindView(R.id.mp_progress)
    MyProgressBar mpProgress;                   //进度跳
    @BindView(R.id.tv_lockup_period_data)
    TextView tvLockupPeriodData;                //锁定期
    @BindView(R.id.tv_lockup_period)
    TextView tvLockupPeriod;                    //锁定期
    @BindView(R.id.tv_total_finance_data)
    TextView tvTotalFinanceData;                //融资总额
    @BindView(R.id.tv_total_finance)
    TextView tvTotalFinance;                //融资总额title
    @BindView(R.id.tv_investment_amount_data)
    TextView tvInvestmentAmountData;            //起投金额
    @BindView(R.id.tv_left)
    TextView tvLeft;                            //左  取名恐惧症，后者看UI
    @BindView(R.id.tv_center)
    TextView tvCenter;                          //中 同上
    @BindView(R.id.tv_right)
    TextView tvRight;                           //右 同上
    @BindView(R.id.tv_value_progress_1)
    TextView tvValueProgress1;                      //投资流程1
    @BindView(R.id.ll_progress_2)
    LinearLayout llProgress2;                        //投资流程2
    @BindView(R.id.tv_key_progress_2)
    TextView tvKeyProgress2;                        //投资流程3
    @BindView(R.id.tv_value_progress_2)
    TextView tvValueProgress2;                        //投资流程3
    @BindView(R.id.ll_progress_3)
    LinearLayout llProgress3;                        //投资流程2
    @BindView(R.id.tv_key_progress_3)
    TextView tvKeyProgress3;                        //投资流程3
    @BindView(R.id.tv_value_progress_3)
    TextView tvValueProgress3;                        //投资流程3

    @BindView(R.id.ll_progress_4)
    LinearLayout llProgress4;                //投资流程4  定期付息 季无忧不显示
    @BindView(R.id.tv_value_progress_5)
    TextView tvValueProgress5;               //投资流程5
    @BindView(R.id.tv_monthly_payments)
    TextView tvMonthlyPayments;               //投资流程5
    @BindView(R.id.tv_key_progress_5)
    TextView tvKeyProgress5;               //投资流程5
    @BindView(R.id.btn_object_detail)
    TextView btnObjectDetail;                       //标的详情
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;                       //起息时间
    @BindView(R.id.ss_content1)
    SpringScrollView ssContent1;                //第一页
    @BindView(R.id.tb_content)
    TabLayout tbContent;                        //标题栏
    @BindView(R.id.vp_content)
    MyViewPager1 vpContent;                        //装载第二页的内容
    @BindView(R.id.ss_content2)
    SpringScrollView ssContent2;                 //第二页
    @BindView(R.id.btn_invest)
    TextView btnInvest;                         //投资


    private int type;   // 2 债权转让，1 无忧标；
    private String id;   //itemID

    private ProjectDetailBean detail;
    private InvestIntroduceAdapter investIntroduceAdapter;
    @Inject
    InvestmentDetailPresenter investmentPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (investmentPresenter != null)
            investmentPresenter.onUnsubscribe();
    }

    @Override
    public void initDagger() {
        DaggerProjectComponent.builder().appComponent(BaseApplication.getAppComponent())
                .projectModule(new ProjectModule(this)).build()
                .inject(this);
    }

    private void initData() {
        investmentPresenter.projectDetail(id, type);
    }

    @SuppressLint("SetTextI18n")
    public void updateData() {
        try {
            ivTitle.setText(detail.getName());
            tvProductRate.setText(NumberFormalUtils.percentFormat(detail.getAnnualRate(), 0, 2));
            tvSurplusFund.setText("剩余金额" + NumberFormalUtils.numberFormat(detail.getCanInvestAmount(), 0, 2) + "元，" +
                    "已募资" + NumberFormalUtils.percentFormat(detail.getRaisedPercent(), 0, 2));
            tvTotalFinanceData.setText(NumberFormalUtils.numberFormat(detail.getLoanAmount(), 0, 2));
            tvInvestmentAmountData.setText((int) detail.getInvestmentAmount() + "");
            mpProgress.setTopPaintWidth((float) detail.getRaisedPercent());

            TextViewSpanUtils.setAbsoluteSizeSpan(tvProductRate, "%", 18);
            tvLeft.setText(detail.getLabelFirst());
            tvCenter.setText(detail.getLabelSecond());
            tvRight.setText(detail.getLabelThird());
            if (detail.getType() == 2) {
                //债转
                llProgress4.setVisibility(View.GONE);
                tvTotalFinance.setText("转让总额 (元)");
                tvLockupPeriod.setText("投资期（天）");
                tvLockupPeriodData.setText("" + detail.getLockDay());
                tvValueProgress1.setText(DateToString.getDay(0));
                tvKeyProgress2.setText("锁定期");
                tvValueProgress2.setText("+" + detail.getLockDay() + "天");
                tvKeyProgress3.setText("定期付息");
                tvValueProgress3.setText("定期付息" + detail.getInterestCycle() + "天");
                llProgress4.setVisibility(View.GONE);
                tvKeyProgress5.setText("到期还本");
                tvValueProgress5.setText(DateToString.getDay(detail.getTerm()));
                tvBeginTime.setText("投资日");
                if (detail.getCanInvestAmount() > 0) {
                    btnInvest.setEnabled(true);
                } else {
                    btnInvest.setEnabled(false);
                }
            } else if (detail.getInterestWay() == 2) {
                //季无忧
                tvLockupPeriodData.setText("不可转让");
                tvValueProgress1.setText(DateToString.getDay(0));
                llProgress2.setVisibility(View.VISIBLE);
                llProgress3.setVisibility(View.GONE);
                llProgress4.setVisibility(View.GONE);
                tvValueProgress5.setText("放款后" + detail.getTerm() + "天");
                tvBeginTime.setText("放款日(放款前按年化" + NumberFormalUtils.percentFormat(detail.getRaiseRate(), 0, 2) + "补贴)");
                if (detail.getCanInvestAmount() > 0) {
                    btnInvest.setEnabled(true);
                } else {
                    btnInvest.setEnabled(false);
                }

            } else {
                //年无忧
                llProgress4.setVisibility(View.VISIBLE);
                tvLockupPeriodData.setText("" + detail.getLockDay());
                tvValueProgress1.setText(DateToString.getDay(0));
                tvValueProgress3.setText("+" + detail.getLockDay() + "天");
                tvMonthlyPayments.setText("定期付息" + detail.getInterestCycle() + "天");
                tvKeyProgress5.setText("到期还本");
                tvValueProgress5.setText("放款后" + detail.getTerm() + "天");
                tvBeginTime.setText("放款日(放款前按年化" + NumberFormalUtils.percentFormat(detail.getRaiseRate(), 0, 2) + "补贴)");
                if (detail.getCanInvestAmount() != 0) {
                    btnInvest.setEnabled(true);
                } else {
                    btnInvest.setEnabled(false);
                }
            }
            investIntroduceAdapter = new InvestIntroduceAdapter(this, type, detail.getInterestWay());
            vpContent.setAdapter(investIntroduceAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initView() {
        setTitle("项目详情");
        type = getIntent().getIntExtra(TYPE, 0);
        id = getIntent().getStringExtra(ID);
        ssContent1.setCallBack(new ScrollCallBack());
        ssContent2.setCallBack(new ScrollCallBack());
        tbContent.setupWithViewPager(vpContent);
        setRight(null, R.mipmap.btn_ktcgzh_kf);
        initData();
    }


    @Override
    public int loadLayoutResID() {
        return R.layout.activity_investment_detail;
    }

    @Override
    public void updateProjectDetail(ProjectDetailBean projectDetailBean) {
        this.detail = projectDetailBean;
        updateData();
    }

    @OnClick({R.id.btn_counter, R.id.btn_invest, R.id.ll_right, R.id.btn_object_detail})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_object_detail:
                Intent productIntent = new Intent(activity, CommonWebActivity.class);
                productIntent.putExtra(CommonWebActivity.URL,
                        new StringBuilder(Url.BIDS_DETAIL)
                                .append("?bidId=").append(detail.getId())
                                .toString());
                startActivity(productIntent);
                break;
            case R.id.btn_counter:
                new CounterDialog(activity, detail, new CounterDialog.Callback() {
                    @Override
                    public void confirm(String investMoney) {
                        invest(investMoney);
                    }
                }).show();
                break;
            case R.id.btn_invest:
                invest(null);
                break;
            case R.id.ll_right:
                break;
        }
    }

    /**
     * 投资
     *
     * @param investMoney
     */
    private void invest(String investMoney) {
        if (detail == null)
            return;
        final Intent intent = new Intent(this, InvestmentActivity.class)
                .putExtra(TYPE, detail.getType())
                .putExtra(ID, detail.getId())
                .putExtra(INTEREST_WAY, detail.getInterestWay())
                .putExtra(PROJECT_ID, detail.getProjectId())
                .putExtra(UMConstants.CHANNEL, "产品详情");
        if (!StringUtils.isEmpty(investMoney)) {
            intent.putExtra(InvestmentActivity.INVEST_MONEY, Integer.parseInt(investMoney));
        }
        btnInvest.setEnabled(false);
        CheckDeposeManager.getInstance().checkDeposeStatus(Observable.just(new Object()), activity, new CheckDeposeManager.CheckResult() {
            @Override
            public void checkResult(boolean result) {
                btnInvest.setEnabled(true);
                if (result)
                    startActivity(intent);
            }
        });
    }


    class ScrollCallBack implements SpringScrollView.CallBack {
        /**
         * 上拉显示第二张
         */
        @Override
        public void pullUp() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(ssContent2, "translationY", -ssContent2.getHeight());
            animator.setDuration(500);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.start();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ssContent1, "translationY", -ssContent1.getHeight());
            animator1.setDuration(500);
            animator1.setInterpolator(new AccelerateInterpolator());
            animator1.start();
            ssContent2.setCanPullUp(false);
            ssContent2.setCanPullDown(true);
        }

        /**
         * 下拉显示第一张
         */
        @Override
        public void pullDown() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(ssContent2, "translationY", 0);
            animator.setDuration(500);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.start();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(ssContent1, "translationY", 0);
            animator1.setDuration(500);
            animator1.setInterpolator(new AccelerateInterpolator());
            animator1.start();
            ssContent1.setCanPullUp(true);
            ssContent1.setCanPullDown(false);
        }
    }
}
