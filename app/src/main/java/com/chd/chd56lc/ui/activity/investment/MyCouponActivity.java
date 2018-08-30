package com.chd.chd56lc.ui.activity.investment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.ui.adapter.MyInvestFragmentAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.ui.fragment.CashRedEnvelopeFragment;
import com.chd.chd56lc.ui.fragment.OtherCouponFragment;
import com.chd.chd56lc.ui.fragment.RateCouponFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCouponActivity extends BaseActivity {
    public static final String SHOW_ITEM = "show_item";
    private int itemPosition;

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    String[] tab_array = new String[]{"红包", "加息券", "其他"};
    Fragment[] fragments = new Fragment[]{new CashRedEnvelopeFragment(), new RateCouponFragment(), new OtherCouponFragment()};

    @Override
    public void initDagger() {

    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_my_coupon;
    }

    public void initView() {
        setTitle("优惠券");
        itemPosition = getIntent().getIntExtra(SHOW_ITEM, 0);
        viewpager.setAdapter(new MyInvestFragmentAdapter(getSupportFragmentManager(), tab_array, fragments));
        tablayout.setupWithViewPager(viewpager);
        setTabs(tablayout);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                setTitle(tab_array[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager.setCurrentItem(itemPosition);
    }

    @OnClick({R.id.tv_use_record, R.id.tv_is_out_time})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_use_record:
                startActivity(new Intent(activity, NoEnableCouponActivity.class)
                        .putExtra(NoEnableCouponActivity.TYPE, 1));
                break;
            case R.id.tv_is_out_time:
                startActivity(new Intent(activity, NoEnableCouponActivity.class)
                        .putExtra(NoEnableCouponActivity.TYPE, 2));
                break;
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
}
