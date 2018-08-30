package com.chd.chd56lc.ui.activity.personCenter;

import android.content.Intent;
import android.view.View;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.mvp.presenter.RiskAssessmentPresenter;
import com.chd.chd56lc.mvp.view.IRiskAssessmentView;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;

import javax.inject.Inject;

import butterknife.OnClick;

public class RiskAssessmentActivity extends BaseActivity implements IRiskAssessmentView {

    @Inject
    RiskAssessmentPresenter riskAssessmentPresenter;
    @Inject
    CustomToast toast;

    /**
     * 返回一个用于设置界面的布局id
     */
    @Override
    public int loadLayoutResID() {
        return R.layout.activity_risk_assessment;
    }

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this)).build().inject(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (riskAssessmentPresenter!=null)
            riskAssessmentPresenter.onUnsubscribe();
    }
    /**
     * 初始化数据，并显示到界面上
     */
    public void initView() {
        setTitle("完成开户-风险测评");
        setTitleBgColor(UIUtils.getColor(R.color.app_main_black));
        setRight(null, R.mipmap.btn_ktcgzh_kf);
    }

    @OnClick({R.id.btn_begin_check, R.id.btn_jump_check})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_begin_check:
                Intent intent = new Intent(activity, CommonWebActivity.class);
                intent.putExtra(CommonWebActivity.URL, Url.RISK_ASSESS);
                intent.putExtra(CommonWebActivity.TITLE, "风险评估");
                startActivity(intent);
                break;
            case R.id.btn_jump_check:
                riskAssessmentPresenter.setRaLevel("0");
                break;
        }
    }

    @Override
    public void riskAssessmentResult(boolean result) {
        if (result) {
            toast.setText("设置成功");
            finish();
        }
    }
}
