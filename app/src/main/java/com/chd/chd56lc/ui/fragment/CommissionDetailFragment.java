package com.chd.chd56lc.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.entity.CommissionBean;
import com.chd.chd56lc.mvp.presenter.CommissionPresenter;
import com.chd.chd56lc.mvp.view.ICommissionDetailView;
import com.chd.chd56lc.ui.adapter.CommissionListAdapter;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.widget.CustomToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

/**
 * 我的资产转让中
 */
public class CommissionDetailFragment extends BaseFragment implements ICommissionDetailView {


    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;

    @Named("CommissionDetail")
    @Inject
    CommissionPresenter commissionPresenter;
    @Inject
    CustomToast toast;


    private CommissionListAdapter CommissionListAdapter;
    private List<CommissionBean> commissionBeanList;

    public CommissionDetailFragment() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this)).build()
                .inject(this);
    }


    @Override
    protected void initView() {
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("当前好友列表");
        if (commissionBeanList == null)
            commissionBeanList = new ArrayList<>();
        commissionBeanList.clear();
        rcData.setLayoutManager(new LinearLayoutManager(context));
        CommissionListAdapter = new CommissionListAdapter(context, commissionBeanList);
        rcData.setAdapter(CommissionListAdapter);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                commissionBeanList.clear();
                loadData();
            }
        });
        smartRefresh.setEnableLoadMore(false);
        smartRefresh.autoRefresh();
    }

    private void loadData() {
        commissionPresenter.listCommissionDetail();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smartRefresh.isRefreshing()) {
            smartRefresh.finishRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (commissionPresenter != null)
            commissionPresenter.onUnsubscribe();
    }

    /**
     * 设置我的资产转让中
     *
     * @param commissionBeans
     */
    @Override
    public void setCommissionBeans(List<CommissionBean> commissionBeans) {
        if (commissionBeans != null && commissionBeans.size() > 0) {
            llNodata.setVisibility(View.GONE);
            rcData.setVisibility(View.VISIBLE);
            commissionBeanList.addAll(commissionBeans);
        }
        if (commissionBeans.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
        }
        CommissionListAdapter.notifyDataSetChanged();
    }
}
