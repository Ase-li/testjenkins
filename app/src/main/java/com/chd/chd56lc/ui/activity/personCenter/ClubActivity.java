package com.chd.chd56lc.ui.activity.personCenter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.mvp.presenter.ClubPresenter;
import com.chd.chd56lc.mvp.view.IClubView;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 尊享俱乐部 VIP申请
 */
public class ClubActivity extends BaseActivity implements IClubView {


    @BindView(R.id.iv_condition1)
    ImageView ivCondition1;
    @BindView(R.id.tv_condition1)
    TextView tvCondition1;
    @BindView(R.id.iv_condition2)
    ImageView ivCondition2;
    @BindView(R.id.tv_condition2)
    TextView tvCondition2;
    @BindView(R.id.iv_condition3)
    ImageView ivCondition3;
    @BindView(R.id.tv_condition3)
    TextView tvCondition3;
    @BindView(R.id.tv_vip_apply)
    TextView tvVipApply;

    @Inject
    ClubPresenter clubPresenter;
    @Inject
    CustomToast toast;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_club;
    }


    @Override
    public void initDagger() {
        super.initDagger();
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this))
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (clubPresenter!=null)
            clubPresenter.onUnsubscribe();
    }

    @Override
    public void initView() {
        setTitle("尊享俱乐部");
        clubPresenter.listUserFinancialPlannerStatus();
    }


    @OnClick(R.id.tv_vip_apply)
    public void onClick() {
        clubPresenter.applyForFinancialPlanner();
    }

    /**
     * 申请结果
     *
     * @param result
     */
    @Override
    public void applyResult(boolean result) {
        if (result) {
            toast.setText("理财师申请成功");
            finish();
        }
    }

    /**
     * 状态
     *
     * @param status
     */
    @Override
    public void userFinancialPlannerStatus(List<Integer> status) {
        if (status.contains(0)) {
            tvCondition1.setTextColor(UIUtils.getColor(R.color.color_666666));
            ivCondition1.setImageResource(R.mipmap.icon_zxjlb_01);
        }
        if (status.contains(1)) {
            tvCondition2.setTextColor(UIUtils.getColor(R.color.color_666666));
            ivCondition2.setImageResource(R.mipmap.icon_zxjlb_02);
        }
        if (status.contains(2)) {
            tvCondition3.setTextColor(UIUtils.getColor(R.color.color_666666));
            ivCondition3.setImageResource(R.mipmap.icon_zxjlb_03);
        }
        if (status.contains(1) && status.contains(2) && status.contains(0)) {
            tvVipApply.setEnabled(true);
        } else {
            tvVipApply.setEnabled(false);
        }
    }
}
