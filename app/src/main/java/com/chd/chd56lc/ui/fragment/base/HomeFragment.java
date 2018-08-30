package com.chd.chd56lc.ui.fragment.base;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerOperateComponent;
import com.chd.chd56lc.dagger.modules.OperateModule;
import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.entity.BannerOrFloatWindow;
import com.chd.chd56lc.entity.FloatWindowBean;
import com.chd.chd56lc.entity.HomeDataBean;
import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.event.MainJumpEvent;
import com.chd.chd56lc.glide.GlideRoundTransform;
import com.chd.chd56lc.manager.CheckDeposeManager;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.HomePresenter;
import com.chd.chd56lc.mvp.view.IHomeView;
import com.chd.chd56lc.ui.activity.personCenter.RegisterActivity;
import com.chd.chd56lc.ui.activity.circum.NotificationActivity;
import com.chd.chd56lc.ui.activity.investment.InvestmentActivity;
import com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.FloatWindowImageView;
import com.chd.chd56lc.widget.convenientbanner.ConvenientBanner;
import com.chd.chd56lc.widget.convenientbanner.holder.CBViewHolderCreator;
import com.chd.chd56lc.widget.convenientbanner.holder.Holder;
import com.chd.chd56lc.widget.convenientbanner.listener.OnItemClickListener;
import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity.ID;
import static com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity.INTEREST_WAY;
import static com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity.PROJECT_ID;
import static com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity.TYPE;

public class HomeFragment extends BaseFragment implements IHomeView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.vp_civilized_class)
    ConvenientBanner vpCivilizedClass;
    @BindView(R.id.tv_second_name)
    TextView tvSecondName;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.tv_first_name)
    TextView tvFirstName;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_third_name)
    TextView tvThirdName;
    @BindView(R.id.tv_third)
    TextView tvThird;
    @BindView(R.id.iv_full)
    ImageView ivFull;
    @BindView(R.id.iv_account_pic)
    ImageView ivAccountPic;
    @BindView(R.id.tv_debt_name)
    TextView tvDebtName;
    @BindView(R.id.tv_year_rate)
    TextView tvYearRate;
    @BindView(R.id.tv_year_rate_hint)
    TextView tvYearRateHint;
    @BindView(R.id.tv_limit_time)
    TextView tvLimitTime;
    @BindView(R.id.btn_invest)
    TextView btnInvest;
    @BindView(R.id.tv_remain_money)
    TextView tvRemainMoney;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.rl_account_status)
    RelativeLayout rlAccountStatus;
    @BindView(R.id.smr_layout)
    SmartRefreshLayout smrLayout;
    @BindView(R.id.vw1)
    TextView vw1;
    @BindView(R.id.vw2)
    TextView vw2;
    @BindView(R.id.tv_hint1)
    TextView tvHint1;
    @BindView(R.id.tv_hint2)
    TextView tvHint2;
    @BindView(R.id.tv_info_count)
    TextView tvInfoCount;
    @BindView(R.id.fiv)
    FloatWindowImageView fiv;

    @Inject
    HomePresenter homePresenter;

    private List<BannerBean> paths;
    private ProjectDetailBean projectDetailBean;
    private HomeDataBean homeDateBean;
    private FloatWindowBean activityManageFloatWinResp;

    public HomeFragment() {
        // Required empty public constructor
        DaggerOperateComponent.builder().appComponent(BaseApplication.getAppComponent()).operateModule(new OperateModule(this))
                .build().inject(this);
    }

    @Override
    protected void initView() {
        CheckDeposeManager.getInstance().checkDeposeStatus(RxView.clicks(tvSecondName).throttleFirst(2, TimeUnit.SECONDS), context, new CheckDeposeManager.CheckResult() {
            @Override
            public void checkResult(boolean result) {
            }
        });
        if (paths == null)
            paths = new ArrayList<>();
        vpCivilizedClass.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new Holder() {
                    private ImageView imageView;

                    @Override
                    public View createView(Context context) {
                        View inflate = LayoutInflater.from(context).inflate(R.layout.item_civilization_banner, null);
                        imageView = (ImageView) inflate.findViewById(R.id.iv_civilization);
                        return inflate;
                    }

                    @Override
                    public void UpdateUI(Context context, int position, Object data) {
                        Glide.clear(imageView);
                        Glide.with(context).load(paths.get(position).getImgUrl()).placeholder(R.mipmap.img_sy_banner)
                                .error(R.mipmap.img_sy_banner).transform(new GlideRoundTransform(context)).into(imageView);
                    }

                };
            }
        }, paths).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, paths.get(position).getActivityUrl())
                );
            }
        });
        vpCivilizedClass.setPageIndicator(new int[]{R.mipmap.indicator_unselect, R.mipmap.indicator_select});
        smrLayout.setEnableLoadMore(false);
        smrLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        smrLayout.autoRefresh();
        fiv.setOnSingleClick(new FloatWindowImageView.OnSingleClick() {
            @Override
            public void onSingleClick() {
                if (activityManageFloatWinResp != null)
                    toActivity(new Intent(context, CommonWebActivity.class).putExtra(CommonWebActivity.URL, activityManageFloatWinResp.getActivityUrl()));
            }
        });
    }

    private void initData() {
//        homePresenter.listBanner();
//        homePresenter.homeRecommendBid();
        homePresenter.home();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (homePresenter != null)
            homePresenter.onUnsubscribe();
    }

    /**
     * 新手指引默认
     */
    private void setNewGuidelinesDefault() {
        rlAccountStatus.setVisibility(View.VISIBLE);
        ivAccountPic.setVisibility(View.GONE);
        tvFirst.setTextColor(UIUtils.getColor(R.color.color_ff4e03));
        tvSecond.setTextColor(UIUtils.getColor(R.color.color_666666));
        tvThird.setTextColor(UIUtils.getColor(R.color.color_666666));
        tvFirstName.setEnabled(true);
        tvFirstName.setBackgroundResource(R.drawable.main_home_progress_backgound);
        tvFirstName.setTextColor(UIUtils.getColor(R.color.gray_white_txtcolor));
        tvSecondName.setEnabled(false);
        tvThirdName.setEnabled(false);
        vw1.setEnabled(false);
        vw2.setEnabled(false);
        tvFirst.setTextSize(14);
        tvSecond.setTextSize(12);
        tvThird.setTextSize(12);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        vpCivilizedClass.stopTurning();
    }

    @Override
    public void onResume() {
        super.onResume();
        vpCivilizedClass.startTurning(3000);
        if (UserManager.getInstance().isLogin())
            homePresenter.getHomePageData();
        else {
            setNewGuidelinesDefault();
            tvInfoCount.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.btn_risk_control, R.id.btn_information_disclosure, R.id.iv_account_pic, R.id.ll_detail, R.id.btn_invite_reward, R.id.btn_my_message, R.id.tv_first_name, R.id.tv_second_name, R.id.tv_third_name, R.id.btn_invest, R.id.btn_customer_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_account_pic:
                if (homeDateBean == null) return;
                switch (homeDateBean.getBeginnerTask()) {
                    case 1:
                        EventBus.getDefault().post(new MainJumpEvent(1, true));
                        break;
                    case 2:
                        if (homeDateBean.getPromotionBo() != null) {
                            toActivity(new Intent(context, CommonWebActivity.class)
                                    .putExtra(CommonWebActivity.URL, homeDateBean.getPromotionBo().getActivityUrl()));
                        }
                        break;
                }
                break;

            case R.id.btn_risk_control:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.RISK_CONTROL)
                        .putExtra(CommonWebActivity.TITLE, "风险管理")
                );
                break;
            case R.id.btn_information_disclosure:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.INFORMATION_DISCLOSURE)
                        .putExtra(CommonWebActivity.TITLE, "信息披露")
                );
                break;
            case R.id.btn_invite_reward:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.REWARD_INVITING)
                        .putExtra(CommonWebActivity.TITLE, "邀请有礼")
                );
                break;
            case R.id.btn_my_message:
                toActivity(NotificationActivity.class);
                break;
            case R.id.tv_first_name:
                toActivity(RegisterActivity.class);
                break;
            case R.id.ll_detail:
                if (projectDetailBean == null) return;
                toActivity(new Intent(new Intent(context, InvestmentDetailActivity.class)
                        .putExtra(ID, projectDetailBean.getId())
                        .putExtra(INTEREST_WAY, projectDetailBean.getInterestWay())
                        .putExtra(TYPE, projectDetailBean.getType())));
                break;
            case R.id.tv_third_name:
                break;
            case R.id.btn_invest:
                if (projectDetailBean == null) return;
                final Intent intent = new Intent(context, InvestmentActivity.class)
                        .putExtra(TYPE, projectDetailBean.getType())
                        .putExtra(ID, projectDetailBean.getId())
                        .putExtra(INTEREST_WAY, projectDetailBean.getInterestWay())
                        .putExtra(PROJECT_ID, projectDetailBean.getProjectId())
                        .putExtra(UMConstants.CHANNEL, "首页推荐");
                btnInvest.setEnabled(false);
                CheckDeposeManager.getInstance().checkDeposeStatus(Observable.just(new Object()), context, new CheckDeposeManager.CheckResult() {
                    @Override
                    public void checkResult(boolean result) {
                        btnInvest.setEnabled(true);
                        if (result)
                            startActivity(intent);
                    }
                });
                break;
            case R.id.btn_customer_service:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.USER_MYSERVICE)
                        .putExtra(CommonWebActivity.TITLE, "客服中心"));
                break;
        }
    }

    @Override
    public void setBannerOrFloat(BannerOrFloatWindow bannerOrFloatWindow) {
        if (bannerOrFloatWindow == null)
            return;
        activityManageFloatWinResp = bannerOrFloatWindow.getActivityManageFloatWinResp();
        if (bannerOrFloatWindow.getAppActivityBannerResps() != null && bannerOrFloatWindow.getAppActivityBannerResps().size() > 0) {
            paths.clear();
            paths.addAll(bannerOrFloatWindow.getAppActivityBannerResps());
            vpCivilizedClass.notifyDataSetChanged();
        }
        if (activityManageFloatWinResp != null && !StringUtils.isEmpty(activityManageFloatWinResp.getFloatIcon())) {
            Glide.with(context).load(bannerOrFloatWindow.getActivityManageFloatWinResp().getFloatIcon()).fitCenter()
                    .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(fiv);
        } else {
            fiv.setVisibility(View.GONE);
        }
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smrLayout.isRefreshing())
            smrLayout.finishRefresh();
    }

    @Override
    public void homeRecommendBid(ProjectDetailBean projectDetailBean) {
        this.projectDetailBean = projectDetailBean;

        if (projectDetailBean == null) return;
        tvDebtName.setText(projectDetailBean.getName());
        String yearRate = NumberFormalUtils.percentFormat(projectDetailBean.getAnnualRate(), 0, 2);
        tvYearRate.setText(yearRate);
        TextViewSpanUtils.setAbsoluteSizeSpan(tvYearRate, "%", 18);
        if (projectDetailBean.getType() == 1) {
            tvLimitTime.setText(projectDetailBean.getExpiryDay() + " 天");
            tvLimitTime.setTextColor(UIUtils.getColor(R.color.color_333333));
            TextViewSpanUtils.setAbsoluteSizeSpan(tvLimitTime, projectDetailBean.getExpiryDay() + "", 18);
        } else {
            tvLimitTime.setText(projectDetailBean.getExpiryTime() + "");
            tvLimitTime.setTextColor(UIUtils.getColor(R.color.color_999999));
            tvLimitTime.setTextSize(12);
        }
        tvHint1.setText(projectDetailBean.getInterestWayString());
        tvHint2.setText(projectDetailBean.getCanTransferString());

        pbProgress.setProgress((int) (100 * projectDetailBean.getRaisedPercent()));
        tvRemainMoney.setText(NumberFormalUtils.numberFormat(projectDetailBean.getCanInvestAmount(), 0, 2));

    }

    /**
     * 首页信息数，新手指引
     *
     * @param homeDataBean
     */
    @Override
    public void homePageDate(HomeDataBean homeDataBean) {
        this.homeDateBean = homeDataBean;
        if (homeDataBean != null) {
            if (homeDataBean.getCountBo() != null && homeDataBean.getCountBo().getCount() != 0) {
                tvInfoCount.setVisibility(View.VISIBLE);
                if (homeDataBean.getCountBo().getCount() < 100)
                    tvInfoCount.setText(homeDataBean.getCountBo().getCount() + "");
                else tvInfoCount.setText("99");
            } else
                tvInfoCount.setVisibility(View.GONE);
            switch (homeDataBean.getBeginnerTask()) {
                case 0:
                    rlAccountStatus.setVisibility(View.VISIBLE);
                    ivAccountPic.setVisibility(View.GONE);
                    tvFirst.setTextColor(UIUtils.getColor(R.color.color_ff4e03));
                    tvSecond.setTextColor(UIUtils.getColor(R.color.color_ff4e03));
                    tvThird.setTextColor(UIUtils.getColor(R.color.color_666666));
                    tvFirstName.setEnabled(false);
                    tvFirstName.setBackgroundResource(R.drawable.bg_home_progress_unclick);
                    tvFirstName.setTextColor(UIUtils.getColor(R.color.color_ffffff));
                    tvSecondName.setEnabled(true);
                    tvThirdName.setEnabled(false);
                    vw1.setEnabled(true);
                    vw2.setEnabled(false);
                    tvFirst.setTextSize(14);
                    tvSecond.setTextSize(14);
                    tvThird.setTextSize(12);
                    break;
                case 1:
                case 2:
                    rlAccountStatus.setVisibility(View.GONE);
                    ivAccountPic.setVisibility(View.VISIBLE);
                    Glide.with(context).load(homeDataBean.getPromotionBo().getImgUrl()).into(ivAccountPic);
                    break;
                default:
                    setNewGuidelinesDefault();
                    break;
            }
        } else {
            setNewGuidelinesDefault();
        }
    }
}
