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
import com.chd.chd56lc.entity.InvestmentClassroomBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.mvp.presenter.ClassRoomPresenter;
import com.chd.chd56lc.mvp.view.IClassRoomListView;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.adapter.InvestmentClassroomFirstDelegate;
import com.chd.chd56lc.ui.adapter.InvestmentClassroomSecondDelegate;
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

public class KnowledgeFragment extends BaseFragment implements IClassRoomListView {
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

    private List<InvestmentClassroomBean> classroomBeans;
    private MultiItemTypeAdapter<InvestmentClassroomBean> classroomAdapter;
    @Named("ClassRoomList")
    @Inject
    ClassRoomPresenter classRoomPresenter;

    private int page = 1;
    private int count = 10;

    public KnowledgeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView() {
        DaggerMessageOrClassRoomComponent.builder().appComponent(BaseApplication.getAppComponent())
                .messageOrClassRoomModule(new MessageOrClassRoomModule(this))
                .build().inject(this);
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("暂无知识普及信息");
        if (classroomBeans == null)
            classroomBeans = new ArrayList<>();
        classroomAdapter = new MultiItemTypeAdapter<>(context, classroomBeans);
        classroomAdapter.addItemViewDelegate(new InvestmentClassroomFirstDelegate(context));
        classroomAdapter.addItemViewDelegate(new InvestmentClassroomSecondDelegate(context));
        rcData.setLayoutManager(new LinearLayoutManager(context));
        rcData.setAdapter(classroomAdapter);
        classroomAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                toActivity(new Intent(context, CommonWebActivity.class)
                        .putExtra(CommonWebActivity.URL, classroomBeans.get(position).getUrl())
                );
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
                classroomBeans.clear();
                initDate();
            }
        });
        smartRefresh.autoRefresh();
    }

    private void initDate() {
        classRoomPresenter.paginateByReq(page, count, 1);
    }


    @Override
    public void updateClassRoomList(PageListBean<InvestmentClassroomBean> classRoomBeanPageListBean) {
        if (classRoomBeanPageListBean != null && classRoomBeanPageListBean.getArray() != null && classRoomBeanPageListBean.getArray().size() > 0) {
            llNodata.setVisibility(View.GONE);
            rcData.setVisibility(View.VISIBLE);
            classroomBeans.addAll(classRoomBeanPageListBean.getArray());
            if (classRoomBeanPageListBean.getPage() == classRoomBeanPageListBean.getTotalPageNo()) {
                smartRefresh.setEnableLoadMore(false);
            }
        }
        if (classroomBeans.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
        }
        classroomAdapter.notifyDataSetChanged();
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
    public int loadLayoutResID() {
        return R.layout.fragment_risk_education;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (classRoomPresenter != null)
            classRoomPresenter.onUnsubscribe();
    }
}
