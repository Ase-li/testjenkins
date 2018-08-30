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
import com.chd.chd56lc.entity.FriendBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.mvp.presenter.CommissionPresenter;
import com.chd.chd56lc.mvp.view.IFriendView;
import com.chd.chd56lc.ui.adapter.FriendListAdapter;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.widget.CustomToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

/**
 * 我的资产转让中
 */
public class FriendListFragment extends BaseFragment implements IFriendView {


    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;

    @Named("FriendView")
    @Inject
    CommissionPresenter commissionPresenter;
    @Inject
    CustomToast toast;

    private int page = 1;
    private int count = 10;

    private FriendListAdapter friendListAdapter;
    private List<FriendBean> friendBeanList;

    public FriendListFragment() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this))
                .build().inject(this);
    }


    @Override
    protected void initView() {
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("当前无好友列表");
        if (friendBeanList == null)
            friendBeanList = new ArrayList<>();
        friendBeanList.clear();
        rcData.setLayoutManager(new LinearLayoutManager(context));
        friendListAdapter = new FriendListAdapter(context, friendBeanList);
        rcData.setAdapter(friendListAdapter);
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                loadData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                friendBeanList.clear();
                page = 1;
                loadData();
            }
        });
        smartRefresh.autoRefresh();
    }

    private void loadData() {
        commissionPresenter.paginateInvitee(page, count);
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
     * 好友列表
     *
     * @param friendBeans
     */
    @Override
    public void setFriendBeans(PageListBean<FriendBean> friendBeans) {
        if (friendBeans != null && friendBeans.getArray() != null && friendBeans.getArray().size() > 0) {
            llNodata.setVisibility(View.GONE);
            rcData.setVisibility(View.VISIBLE);
            friendBeanList.addAll(friendBeans.getArray());
            if (friendBeans.getPage() == friendBeans.getTotalPageNo()) {
                smartRefresh.setEnableLoadMore(false);
            } else {
                smartRefresh.setEnableLoadMore(true);
            }
        }
        if (friendBeanList.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
            smartRefresh.setEnableLoadMore(false);
        } else {
            smartRefresh.setEnableLoadMore(true);
        }
        friendListAdapter.notifyDataSetChanged();
    }
}
