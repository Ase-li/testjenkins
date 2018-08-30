package com.chd.chd56lc.ui.fragment.base;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerOperateComponent;
import com.chd.chd56lc.dagger.modules.OperateModule;
import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.FindPresenter;
import com.chd.chd56lc.mvp.view.IFindView;
import com.chd.chd56lc.ui.activity.circum.InvestClassRoomActivity;
import com.chd.chd56lc.ui.activity.personCenter.ClubActivity;
import com.chd.chd56lc.ui.activity.personCenter.ClubDetailActivity;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.adapter.ActivityAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.MultiItemTypeAdapter;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.widget.CustomToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment implements IFindView {

    @BindView(R.id.btn_investment_classroom)
    LinearLayout btnInvestmentClassroom;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_title_type)
    TextView tvTitleType;
    @BindView(R.id.btn_invite_reward)
    LinearLayout btnInviteReward;
    @BindView(R.id.btn_depository_guide)
    LinearLayout btnDepositoryGuide;
    @BindView(R.id.btn_information_disclosure)
    LinearLayout btnInformationDisclosure;
    Unbinder unbinder;
    Unbinder unbinder1;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    @Inject
    FindPresenter findPresenter;
    @Inject
    CustomToast customToast;
    List<BannerBean> bannerBeanList;

    private ActivityAdapter activityAdapter;
    private int page = 1;
    private int count = 10;

    public FindFragment() {
        // Required empty public constructor
        DaggerOperateComponent.builder().appComponent(BaseApplication.getAppComponent())
                .operateModule(new OperateModule(this)).build().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (findPresenter!=null)
            findPresenter.onUnsubscribe();
    }

    @Override
    protected void initView() {
        tvTitleType.setText(R.string.play_make_center);
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("暂无活动信息");
        if (bannerBeanList == null)
            bannerBeanList = new ArrayList<>();
        rcData.setLayoutManager(new LinearLayoutManager(context));
        activityAdapter = new ActivityAdapter(context, bannerBeanList);
        activityAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, bannerBeanList.get(position).getActivityUrl()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rcData.setAdapter(activityAdapter);
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                initData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                bannerBeanList.clear();
                page = 1;
                initData();
            }
        });
        smartRefresh.autoRefresh();
    }

    private void initData() {
        findPresenter.paginateActivity(page, count);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_find;
    }


    @OnClick({R.id.btn_invite_reward, R.id.btn_depository_guide, R.id.btn_information_disclosure, R.id.btn_investment_classroom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_invite_reward:
                if (UserManager.getInstance().isLogin())
                    if (UserManager.getInstance().getCurrentUserInfo().getType() == 2)
                        toActivity(ClubDetailActivity.class);
                    else
                        toActivity(ClubActivity.class);
                else
                    customToast.setText("登录后才能查看哦");
                break;
            case R.id.btn_depository_guide:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.DEPOSITORY_GUIDE)
                        .putExtra(CommonWebActivity.TITLE, "存管指南")
                );
                break;
            case R.id.btn_information_disclosure:
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, Url.INFORMATION_DISCLOSURE)
                        .putExtra(CommonWebActivity.TITLE, "信息披露")
                );
                break;
            case R.id.btn_investment_classroom:
                toActivity(InvestClassRoomActivity.class);
                break;
        }
    }

    @Override
    public void updateActive(PageListBean<BannerBean> Activities) {
        if (Activities != null && Activities.getArray() != null && Activities.getArray().size() > 0) {
            llNodata.setVisibility(View.GONE);
            rcData.setVisibility(View.VISIBLE);
            bannerBeanList.addAll(Activities.getArray());
            if (Activities.getPage() == Activities.getTotalPageNo()) {
                smartRefresh.setEnableLoadMore(false);
            }
        }
        if (bannerBeanList.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
        }
        activityAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smartRefresh.isLoading())
            smartRefresh.finishLoadMore();
        if (smartRefresh.isRefreshing())
            smartRefresh.finishRefresh();
    }
}
