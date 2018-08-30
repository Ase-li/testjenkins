package com.chd.chd56lc.ui.activity.investment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.ui.adapter.MyInvestFragmentAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.ui.fragment.TotalAssetFragment;
import com.chd.chd56lc.ui.fragment.TotalEarnFragment;
import com.chd.chd56lc.widget.MyViewPager;

import butterknife.BindView;

@SuppressWarnings("ALL")
public class AssetAnalyzeActivity extends BaseActivity {
    public static final String SHOWITEM = "show_item";
    String[] tab_array = new String[]{"总资产", "收益"};
    Fragment[] fragments = new Fragment[]{new TotalAssetFragment(), new TotalEarnFragment()};
    @BindView(R.id.tb_content)
    TabLayout tbContent;
    @BindView(R.id.vp_content)
    MyViewPager vpContent;

    private int showItem;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_vp_tab;
    }

    public void initView() {
        setTitle("资产分析");
        showItem = getIntent().getIntExtra(SHOWITEM, 0);
        vpContent.setAdapter(new MyInvestFragmentAdapter(getSupportFragmentManager(), tab_array, fragments));
        tbContent.setupWithViewPager(vpContent);
        setTabs(tbContent);
        vpContent.setCurrentItem(showItem);
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
}