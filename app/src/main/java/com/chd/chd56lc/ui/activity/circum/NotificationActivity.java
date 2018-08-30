package com.chd.chd56lc.ui.activity.circum;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.ui.adapter.MyInvestFragmentAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.ui.fragment.MyMessageFragment;
import com.chd.chd56lc.ui.fragment.NotifyFragment;
import com.chd.chd56lc.widget.MyViewPager;

import butterknife.BindView;

@SuppressWarnings("ALL")
public class NotificationActivity extends BaseActivity {
    String[] tab_array = new String[]{"平台公告", "我的消息"};
    Fragment[] fragments = new Fragment[]{new NotifyFragment(), new MyMessageFragment()};
    @BindView(R.id.tb_content)
    TabLayout tbContent;
    @BindView(R.id.vp_content)
    MyViewPager vpContent;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_vp_tab;
    }

    public void initView() {
        setTitle("通知");
        vpContent.setAdapter(new MyInvestFragmentAdapter(getSupportFragmentManager(), tab_array, fragments));
        tbContent.setupWithViewPager(vpContent);
        setTabs(tbContent);
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
            tvTitle.setTextSize(18);
            tvTitle.setText(tab_array[i]);
            tab.setCustomView(view);
        }
    }
}