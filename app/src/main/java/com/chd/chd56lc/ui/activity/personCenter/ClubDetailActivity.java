package com.chd.chd56lc.ui.activity.personCenter;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.entity.CommissionInfo;
import com.chd.chd56lc.mvp.presenter.CommissionPresenter;
import com.chd.chd56lc.mvp.view.ICommissionInfoView;
import com.chd.chd56lc.ui.adapter.MyInvestFragmentAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.ui.fragment.CommissionDetailFragment;
import com.chd.chd56lc.ui.fragment.FriendListFragment;
import com.chd.chd56lc.widget.MyViewPager;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

/**
 * 尊享俱乐部
 */
public class ClubDetailActivity extends BaseActivity implements ICommissionInfoView {

    @BindView(R.id.tv_vip_introduce)
    TextView tvVipIntroduce;
    @BindView(R.id.tv_total_Commission)
    TextView tvTotalCommission;
    @BindView(R.id.tv_next_earn)
    TextView tvNextEarn;
    @BindView(R.id.tv_current_invest)
    TextView tvCurrentInvest;
    @BindView(R.id.tb_content)
    TabLayout tbContent;
    @BindView(R.id.vp_content)
    MyViewPager vpContent;

    private TextView tvFriend;

    String[] tab_array = {"佣金明细", "好友列表"};
    Fragment[] fragments = {new CommissionDetailFragment(), new FriendListFragment()};

    @Named("commissionInfo")
    @Inject
    CommissionPresenter commissionPresenter;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_club_detail;
    }

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this))
                .build().inject(this);
    }

    @Override
    public void initView() {
        setTitle("尊享俱乐部");
        vpContent.setAdapter(new MyInvestFragmentAdapter(getSupportFragmentManager(), tab_array, fragments));
        tbContent.setupWithViewPager(vpContent);
        setTabs();
        commissionPresenter.getCommissionAndHoldInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (commissionPresenter!=null)
            commissionPresenter.onUnsubscribe();
    }

    /**
     * @description: 设置添加Tab
     */
    @SuppressWarnings("ConstantConditions")
    private void setTabs() {
        for (int i = 0; i < tab_array.length; i++) {
            TabLayout.Tab tab = tbContent.getTabAt(i);
            View view = getLayoutInflater().inflate(R.layout.main_tab_item, null);
            TextView tvTitle = view.findViewById(R.id.tv_tab);
            tvTitle.setTextSize(14);
            tvTitle.setText(tab_array[i]);
            if (i == 1)
                tvFriend = tvTitle;
            tab.setCustomView(view);
        }
    }

    /**
     * 佣金详情
     *
     * @param commissionInfo
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void setCommissionInfo(CommissionInfo commissionInfo) {
        if (commissionInfo == null) return;
        tvCurrentInvest.setText(commissionInfo.getCurrentAvgDailyHold() + "");
        tvNextEarn.setText(commissionInfo.getNextRoundCommission() + "");
        tvTotalCommission.setText(commissionInfo.getAccumulateCommission() + "");
        tvFriend.setText("好友列表 (" + commissionInfo.getInviteesAmount() + ")");
    }
}
