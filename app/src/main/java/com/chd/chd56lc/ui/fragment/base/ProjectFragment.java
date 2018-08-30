package com.chd.chd56lc.ui.fragment.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerProjectComponent;
import com.chd.chd56lc.dagger.modules.ProjectModule;
import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.entity.ProjectListItem;
import com.chd.chd56lc.mvp.presenter.ProjectFragmentPresenter;
import com.chd.chd56lc.mvp.view.IProjectFragmentView;
import com.chd.chd56lc.ui.adapter.ProjectListAdapter;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.utils.UIUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ProjectFragment extends BaseFragment implements IProjectFragmentView {
    /**
     * 无忧标
     */
    private static final int PROJECT_WYB = 1;
    /**
     * 债权转让
     */
    private static final int PROJECT_ZQZR = 2;

    private static final String PROJECT_TYPE = "projectType";

    @BindView(R.id.tv_content)
    TextView tvContent;  //债转优选金额 仅在债转时显示
    @BindView(R.id.header)
    ClassicsHeader header;
    @BindView(R.id.rcv_data)
    ListView rcvData;
    @BindView(R.id.foot)
    ClassicsFooter foot;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLoadLayout;
    @BindView(R.id.tv_invest_wy)
    TextView tvInvestWy;
    @BindView(R.id.vw_invest_wy)
    View vwInvestWy;
    @BindView(R.id.tv_invest_zqzr)
    TextView tvInvestZqzr;
    @BindView(R.id.vw_invest_zqzr)
    View vwInvestZqzr;
    @BindView(R.id.ll_screen)
    LinearLayout llScreen; //债转优选显示

    @Inject
    ProjectFragmentPresenter projectFragmentPresenter;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private int projectType = PROJECT_WYB;
    private int page;
    private static final int COUNT = 10;
    private List<ProjectDetailBean> projectDetailBeans;
    private ProjectListAdapter projectListAdapter;

    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView() {
        DaggerProjectComponent.builder().appComponent(BaseApplication.getAppComponent())
                .projectModule(new ProjectModule(this)).build()
                .inject(this);
        llNodata.setVisibility(View.VISIBLE);
        rcvData.setVisibility(View.GONE);
        if (projectType == 2)
            tvNoData.setText("暂无债转份额");
        else
            tvNoData.setText("暂无产品");
//        rcvData.addFooterView(LayoutInflater.from(context).inflate(R.layout.item_textview, null));
        initData();
    }

    private void initData() {
        if (projectDetailBeans == null) {
            projectDetailBeans = new ArrayList<>();
        }
        projectListAdapter = new ProjectListAdapter(context, projectDetailBeans);
        rcvData.setAdapter(projectListAdapter);
//        llScreen.setVisibility(View.GONE);
        refreshLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                projectDetailBeans.clear();
                loadData();
            }
        });
        refreshLoadLayout.setEnableLoadMore(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLoadLayout.autoRefresh();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_prodect;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick({R.id.btn_invest_wy, R.id.btn_invest_zqzr})
    public void onClick(View view) {
        switch (view.getId()) {
            //无忧标
            case R.id.btn_invest_wy:
                showLoading();
                select(1);
                break;
            //债权转让
            case R.id.btn_invest_zqzr:
                showLoading();
                select(2);
                break;
        }
    }

    //选择类型
    private void select(int type) {
        if (type == 1) {
            if (vwInvestWy.getVisibility() == View.VISIBLE) return;
            vwInvestWy.setVisibility(View.VISIBLE);
            tvInvestWy.setTextColor(UIUtils.getColor(R.color.color_ff4e03));
            vwInvestZqzr.setVisibility(View.GONE);
//            llScreen.setVisibility(View.GONE);
            tvInvestZqzr.setTextColor(UIUtils.getColor(R.color.color_333333));
            projectType = PROJECT_WYB;
            tvNoData.setText("暂无无忧标");
            page = 1;
            projectDetailBeans.clear();
            loadData();
        } else {
            if (vwInvestZqzr.getVisibility() == View.VISIBLE) return;
            vwInvestZqzr.setVisibility(View.VISIBLE);
            tvInvestZqzr.setTextColor(UIUtils.getColor(R.color.color_ff4e03));
//            llScreen.setVisibility(View.VISIBLE);
            vwInvestWy.setVisibility(View.GONE);
            tvInvestWy.setTextColor(UIUtils.getColor(R.color.color_333333));
            projectType = PROJECT_ZQZR;
            tvNoData.setText("暂无债转份额");
            page = 1;
            projectDetailBeans.clear();
            loadData();
        }
    }

    private void loadData() {
        projectFragmentPresenter.projectList(page, COUNT, projectType);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateProjectList(ProjectListItem projectListItemBean) {
        if (projectListItemBean != null && projectListItemBean.getArray() != null) {
            rcvData.setVisibility(View.VISIBLE);
            llNodata.setVisibility(View.GONE);
//            if (projectType == PROJECT_ZQZR)
//                tvContent.setText(projectListItemBean.getQueueAmount() + "");
            projectDetailBeans.addAll(projectListItemBean.getArray());
            if (projectListItemBean.getPage() == projectListItemBean.getTotalPageNo()) {
                refreshLoadLayout.setEnableLoadMore(false);
            } else {
                refreshLoadLayout.setEnableLoadMore(true);
            }
        }
        if (projectDetailBeans.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcvData.setVisibility(View.GONE);
        }
        projectListAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (refreshLoadLayout.isRefreshing()) {
            refreshLoadLayout.finishRefresh();
        }
        if (refreshLoadLayout.isLoading())
            refreshLoadLayout.finishLoadMore();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(PROJECT_TYPE, projectType);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (projectFragmentPresenter != null)
            projectFragmentPresenter.onUnsubscribe();
    }
}
