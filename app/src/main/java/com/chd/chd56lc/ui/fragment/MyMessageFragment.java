package com.chd.chd56lc.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerMessageOrClassRoomComponent;
import com.chd.chd56lc.dagger.modules.MessageOrClassRoomModule;
import com.chd.chd56lc.entity.MessageBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.mvp.presenter.MessagePresenter;
import com.chd.chd56lc.mvp.view.IMessageListView;
import com.chd.chd56lc.ui.activity.circum.NotifyDetailActivity;
import com.chd.chd56lc.ui.adapter.NotifyAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.MultiItemTypeAdapter;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.chd.chd56lc.ui.activity.circum.NotifyDetailActivity.ID;

public class MyMessageFragment extends BaseFragment implements IMessageListView {

    @BindView(R.id.iv_nodata)
    ImageView ivNodata;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private List<MessageBean> notifyBeanList;
    private NotifyAdapter notifyAdapter;

    public MyMessageFragment() {
        // Required empty public constructor
    }


    @Named("MessageList")
    @Inject
    MessagePresenter messagePresenter;

    private int page = 1;
    private int count = 10;

    @Override
    protected void initView() {
        DaggerMessageOrClassRoomComponent.builder().appComponent(BaseApplication.getAppComponent())
                .messageOrClassRoomModule(new MessageOrClassRoomModule(this))
                .build().inject(this);
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("暂无我的消息");
        if (notifyBeanList == null)
            notifyBeanList = new ArrayList<>();
        notifyAdapter = new NotifyAdapter(context, notifyBeanList);
        rcData.setLayoutManager(new LinearLayoutManager(context));
        rcData.setAdapter(notifyAdapter);
        notifyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                toActivity(new Intent(context, NotifyDetailActivity.class)
                        .putExtra(ID, notifyBeanList.get(position).getId()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                initDate();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                notifyBeanList.clear();
                initDate();
            }
        });
//        initDate();
        smartRefresh.autoRefresh();
    }

    private void initDate() {
        messagePresenter.paginageMsgList(page, count, 3);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_notify;
    }


    @Override
    public void updateMessageList(PageListBean<MessageBean> messageBeanPageListBean) {
        if (messageBeanPageListBean != null && messageBeanPageListBean.getArray() != null && messageBeanPageListBean.getArray().size() > 0) {
            llNodata.setVisibility(View.GONE);
            rcData.setVisibility(View.VISIBLE);
            notifyBeanList.addAll(messageBeanPageListBean.getArray());
            if (messageBeanPageListBean.getPage() == messageBeanPageListBean.getTotalPageNo()) {
                smartRefresh.setEnableLoadMore(false);
            }
        }
        if (notifyBeanList.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
        }
        notifyAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smartRefresh.isLoading())
            smartRefresh.finishLoadMore();
        if (smartRefresh.isRefreshing())
            smartRefresh.finishRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (messagePresenter != null)
            messagePresenter.onUnsubscribe();
    }
}
