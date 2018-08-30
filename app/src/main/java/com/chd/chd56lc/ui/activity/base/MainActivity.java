package com.chd.chd56lc.ui.activity.base;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.event.MainJumpEvent;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.ui.fragment.base.FindFragment;
import com.chd.chd56lc.ui.fragment.base.HomeFragment;
import com.chd.chd56lc.ui.fragment.base.PersonCenterFragment;
import com.chd.chd56lc.ui.fragment.base.ProjectFragment;
import com.chd.chd56lc.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    public static final String SELECT_TAB = "select_tab";
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]{new HomeFragment(), new ProjectFragment(), new FindFragment(), new PersonCenterFragment()};
    final String[] tabTag = new String[]{"home_fragment", "project_fragment", "find_fragment", "my_fragment"};
    @BindView(R.id.rg_bottom)
    RadioGroup rgBottom;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;
    @BindView(R.id.rb_tab1)
    RadioButton rbTab1;
    @BindView(R.id.rb_tab2)
    RadioButton rbTab2;
    @BindView(R.id.rb_tab3)
    RadioButton rbTab3;
    @BindView(R.id.rb_tab4)
    RadioButton rbTab4;
    private MainJumpEvent mainJumpEvent;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_main1;
    }

    @Override
    public void initDagger() {
        BaseApplication.getAppComponent().inject(this);
    }

    @Override
    public int setStatusBarColor() {
        return UIUtils.getColor(R.color.color_ffffff);
    }


    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_tab1:
                        showHome();
                        break;
                    case R.id.rb_tab2:
                        showProject();
                        break;
                    case R.id.rb_tab3:
                        showFind();
                        break;
                    case R.id.rb_tab4:
                        showMy();
                        break;

                }
            }
        });
        rbTab1.setChecked(true);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void JumpEvent(MainJumpEvent mainJumpEvent) {
        this.mainJumpEvent = mainJumpEvent;
        //防止当onSaveInstanceState调用后，FragmentTransaction调用commit异常
        if (mainJumpEvent != null && mainJumpEvent.isMain()) {
            switch (mainJumpEvent.getJumpTab()) {
                case 0:
                    rbTab1.setChecked(true);
                    break;
                case 1:
                    rbTab2.setChecked(true);
                    break;
                case 2:
                    rbTab3.setChecked(true);
                    break;
                case 3:
                    rbTab4.setChecked(true);
                    break;
            }
            //用完则置空，防止影响页面的其他切换
            this.mainJumpEvent = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mainJumpEvent == null) return;
        switch (mainJumpEvent.getJumpTab()) {
            case 0:
                rbTab1.setChecked(true);
                break;
            case 1:
                rbTab2.setChecked(true);
                break;
            case 2:
                rbTab3.setChecked(true);
                break;
            case 3:
                rbTab4.setChecked(true);
                break;
        }
        mainJumpEvent = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void showHome() {
        if (TAB_FRAGMENTS[0].isAdded()) {
            show(TAB_FRAGMENTS[0]);
            hide(TAB_FRAGMENTS[1]);
            hide(TAB_FRAGMENTS[2]);
            hide(TAB_FRAGMENTS[3]);
        } else {
            add(R.id.fl_fragment, TAB_FRAGMENTS[0], tabTag[0]);
            show(TAB_FRAGMENTS[0]);
            hide(TAB_FRAGMENTS[1]);
            hide(TAB_FRAGMENTS[2]);
            hide(TAB_FRAGMENTS[3]);
        }
    }

    private void showProject() {
        if (TAB_FRAGMENTS[1].isAdded()) {
            show(TAB_FRAGMENTS[1]);
            hide(TAB_FRAGMENTS[0]);
            hide(TAB_FRAGMENTS[2]);
            hide(TAB_FRAGMENTS[3]);
        } else {
            add(R.id.fl_fragment, TAB_FRAGMENTS[1], tabTag[1]);
            show(TAB_FRAGMENTS[1]);
            hide(TAB_FRAGMENTS[0]);
            hide(TAB_FRAGMENTS[2]);
            hide(TAB_FRAGMENTS[3]);
        }
    }

    private void showFind() {
        if (TAB_FRAGMENTS[2].isAdded()) {
            show(TAB_FRAGMENTS[2]);
            hide(TAB_FRAGMENTS[0]);
            hide(TAB_FRAGMENTS[1]);
            hide(TAB_FRAGMENTS[3]);
        } else {
            add(R.id.fl_fragment, TAB_FRAGMENTS[2], tabTag[2]);
            show(TAB_FRAGMENTS[2]);
            hide(TAB_FRAGMENTS[0]);
            hide(TAB_FRAGMENTS[1]);
            hide(TAB_FRAGMENTS[3]);
        }
    }

    private void showMy() {
        if (TAB_FRAGMENTS[3].isAdded()) {
            show(TAB_FRAGMENTS[3]);
            hide(TAB_FRAGMENTS[0]);
            hide(TAB_FRAGMENTS[1]);
            hide(TAB_FRAGMENTS[2]);
        } else {
            add(R.id.fl_fragment, TAB_FRAGMENTS[3], tabTag[3]);
            show(TAB_FRAGMENTS[3]);
            hide(TAB_FRAGMENTS[0]);
            hide(TAB_FRAGMENTS[1]);
            hide(TAB_FRAGMENTS[2]);
        }
    }

    /**
     * 增加片段
     */
    private void add(int layoutId, Fragment f, String tag) {
        // id int
        // tag String
        // 获取管理者
        FragmentManager fm = getSupportFragmentManager();
        // 打开事务
        FragmentTransaction ft = fm.beginTransaction();
        if (f.isAdded()) {
            //移除之前的
            ft.remove(f);
        }
        // 替换
        ft.add(layoutId, f, tag);
        //贴上
        ft.attach(f);
        // 关闭
        ft.commit();
//        ft.commitAllowingStateLoss();
    }

    /**
     * 显示片段
     */
    protected void show(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        //打开事务
        FragmentTransaction ft = fm.beginTransaction();
        //显示
        ft.show(f);
        //关闭
        ft.commit();
//        ft.commitAllowingStateLoss();
    }


    /**
     * 隐藏片段
     */
    protected void hide(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        // 打开事务
        FragmentTransaction ft = fm.beginTransaction();
        // 替换
        ft.hide(f);
        // 关闭
        ft.commit();
//        ft.commitAllowingStateLoss();
    }

    /**
     * 找到片段
     */
    protected Fragment find(String tag) {
        FragmentManager fm = getSupportFragmentManager();

        Fragment f = fm.findFragmentByTag(tag);

        return f;
    }


}
