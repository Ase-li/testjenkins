package com.chd.chd56lc.ui.activity.investment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.mvp.presenter.MyInvestmentPresenter;
import com.chd.chd56lc.mvp.view.IMyInvestmentView;
import com.chd.chd56lc.ui.adapter.MyInvestFragmentAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.ui.fragment.InvestInFragment;
import com.chd.chd56lc.ui.fragment.InvestOverFragment;
import com.chd.chd56lc.ui.fragment.InvestTransferFragment;
import com.chd.chd56lc.widget.MyViewPager;
import com.chd.chd56lc.widget.dialog.WarnHintDialog;

import javax.inject.Inject;

import butterknife.BindView;

public class MyInvestmentActivity extends BaseActivity implements IMyInvestmentView {
    public static final String SHOWITEM = "show_item";

    private int showItem;

    @BindView(R.id.tb_content)
    TabLayout tbContent;
    @BindView(R.id.vp_content)
    MyViewPager vpContent;
    String[] tab_array = new String[]{"在持中", "转让中", "已完成"};
    Fragment[] fragments = new Fragment[]{new InvestInFragment(), new InvestTransferFragment(), new InvestOverFragment()};

    @Inject
    MyInvestmentPresenter debtSignPresenter;

    @Override
    public void initDagger() {
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }

    public void initView() {
        setTitle("我的投资");
        setRight("转让", R.mipmap.icon_wdtz_zr);
        showItem = getIntent().getIntExtra(SHOWITEM, 0);
        vpContent.setAdapter(new MyInvestFragmentAdapter(getSupportFragmentManager(), tab_array, fragments));
        tbContent.setupWithViewPager(vpContent);
        setTabs(tbContent);
        vpContent.setCurrentItem(showItem);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_my_investment;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.ll_right) {
            debtSignPresenter.getSignDebtStatus();
        }
    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout) {
        for (int i = 0; i < tab_array.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = getLayoutInflater().inflate(R.layout.main_tab_item, null);
            view.setTag(i);
            TextView tvTitle = view.findViewById(R.id.tv_tab);
            tvTitle.setText(tab_array[i]);
            tab.setCustomView(view);
        }
    }

    @Override
    public void debtSignStatus(int status) {
        switch (status) {
            case 0:
                toActivity(DebtSignActivity.class);
                break;
            case 1:
                toActivity(InvestTransferableActivity.class);
                break;
            case 2:
                new WarnHintDialog(this, "签约处理中").show();
                break;

        }
    }
}
