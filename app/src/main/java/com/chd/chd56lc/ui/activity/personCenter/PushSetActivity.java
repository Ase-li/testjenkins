package com.chd.chd56lc.ui.activity.personCenter;

import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.entity.PushSetBean;
import com.chd.chd56lc.mvp.presenter.PushSetPresenter;
import com.chd.chd56lc.mvp.view.IPushSetView;
import com.chd.chd56lc.ui.activity.personCenter.PushTypeSetActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.widget.CustomToast;
import com.chd.chd56lc.widget.NewTwoSideTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class PushSetActivity extends BaseActivity implements IPushSetView, CompoundButton.OnCheckedChangeListener {

    private static final int TRANS_TYPE_REQUEST_CODE = 0x00001;
    public static final String PUSH_TYPE = "trans_type";
    public static final String EMAIL = "email";
    @BindView(R.id.cb_recharge)
    CheckBox cbRecharge;
    @BindView(R.id.cb_withdraw_deposit)
    CheckBox cbWithdrawDeposit;
    @BindView(R.id.cb_investment)
    CheckBox cbInvestment;
    @BindView(R.id.cb_dividend_payout)
    CheckBox cbDividendPayout;
    @BindView(R.id.cb_returned_money)
    CheckBox cbReturnedMoney;
    @BindView(R.id.cb_assignment_debt)
    CheckBox cbAssignmentDebt;
    @BindView(R.id.ntv_trans_style)
    NewTwoSideTextView ntvTransStyle;

    private int transType;
    private String email;

    @Inject
    PushSetPresenter pushSetPresenter;
    @Inject
    CustomToast customToast;

    private PushSetBean pushSetBean;
    private boolean isCharge = false;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_trans_set;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pushSetPresenter != null) {
            pushSetPresenter.onUnsubscribe();
        }
    }

    @Override
    public void initView() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this)).build()
                .inject(this);
        setTitle("推送设置");
        pushSetPresenter.getPushSettings();
        cbRecharge.setOnCheckedChangeListener(this);
        cbWithdrawDeposit.setOnCheckedChangeListener(this);
        cbInvestment.setOnCheckedChangeListener(this);
        cbDividendPayout.setOnCheckedChangeListener(this);
        cbReturnedMoney.setOnCheckedChangeListener(this);
        cbAssignmentDebt.setOnCheckedChangeListener(this);
    }

    @OnClick(R.id.ntv_trans_style)
    public void onClick() {
        Intent intent = new Intent(activity, PushTypeSetActivity.class).putExtra(PUSH_TYPE, transType);
        if (transType == 3)
            intent.putExtra(EMAIL, email);
        startActivityForResult(intent, TRANS_TYPE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        transType = data.getIntExtra(PUSH_TYPE, 0);
        email = data.getStringExtra(EMAIL);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TRANS_TYPE_REQUEST_CODE:
                    isCharge = true;
                    setTransType(transType);
                    break;
            }
        }
    }

    @Override
    public void finish() {
        if (isCharge)
            pushSetPresenter.setPushSettings(cbRecharge.isChecked(), cbWithdrawDeposit.isChecked(), cbInvestment.isChecked(),
                    cbDividendPayout.isChecked(), cbReturnedMoney.isChecked(), cbAssignmentDebt.isChecked(), transType, email);
        else
            super.finish();
    }

    /**
     * 推送设置
     */
    @Override
    public void setPushSuccess() {
        customToast.setText("设置成功");
        super.finish();
    }

    /**
     * 获取推送
     */
    @Override
    public void updatePush(PushSetBean pushSetBean) {
        this.pushSetBean = pushSetBean;
        cbAssignmentDebt.setChecked(pushSetBean.isDebt());
        cbDividendPayout.setChecked(pushSetBean.isDividend());
        cbInvestment.setChecked(pushSetBean.isInvest());
        cbRecharge.setChecked(pushSetBean.isRecharge());
        cbReturnedMoney.setChecked(pushSetBean.isRemit());
        cbWithdrawDeposit.setChecked(pushSetBean.isWithdraw());
        transType = pushSetBean.getChannel();
        email = pushSetBean.getEmail();
        setTransType(pushSetBean.getChannel());
    }

    private void setTransType(int transType) {
        switch (transType) {
            case 0:
                ntvTransStyle.setValueText("短信");
                break;
            case 1:
                ntvTransStyle.setValueText("APP");
                break;
            case 2:
                ntvTransStyle.setValueText("微信");
                break;
            case 3:
                ntvTransStyle.setValueText("email");
                break;
        }
    }


    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isCharge = true;
    }
}
