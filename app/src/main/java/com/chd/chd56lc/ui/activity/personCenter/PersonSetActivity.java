package com.chd.chd56lc.ui.activity.personCenter;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.UserInfoBean;
import com.chd.chd56lc.helper.TagAliasOperatorHelper;
import com.chd.chd56lc.manager.CheckDeposeManager;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.PersonSetPresenter;
import com.chd.chd56lc.mvp.view.IPersonSetView;
import com.chd.chd56lc.touchidmodul.core.FingerprintCore;
import com.chd.chd56lc.ui.activity.base.ContactUsActivity;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.ui.activity.web.DepositWebActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.AppUtil;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.chd.chd56lc.widget.NewTwoSideTextView;
import com.chd.chd56lc.widget.dialog.TouchIdDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人设置
 */
public class PersonSetActivity extends BaseActivity implements IPersonSetView, CompoundButton.OnCheckedChangeListener {
    public static final String TRANS_SET_RESULT = "trans_set_result";
    private static final int REQUEST_SET_GESTURE = 0x001;
    @BindView(R.id.ntv_tel_number)
    NewTwoSideTextView ntvTelNumber;        //登录手机号
    @BindView(R.id.ntv_user_name)
    NewTwoSideTextView ntvUserName;         //用户名
    @BindView(R.id.ntv_user_id)
    NewTwoSideTextView ntvUserId;           //身份证
    @BindView(R.id.ntv_trans_password)
    NewTwoSideTextView ntvTransPassword;           //重置密码
    @BindView(R.id.cb_touch_id)
    CheckBox cbTouchId;                     //开启touchId
    @BindView(R.id.cb_gesture_password)
    CheckBox cbGesturePassword;             //设置手势密码
    @BindView(R.id.ntv_version_info)
    TextView ntvVersionInfo;      //版本信息

    @Inject
    PersonSetPresenter personSetPresenter;
    @Inject
    CustomToast toast;

    private UserInfoBean userInfo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (personSetPresenter != null)
            personSetPresenter.onUnsubscribe();
    }

    @Override
    protected void onPause() {
        if (fingerprintCore != null) {
            fingerprintCore.onDestroy();
            fingerprintCore = null;
        }
        super.onPause();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_person_set;
    }

    @Override
    protected int setStatusBarColor() {
        return UIUtils.getColor(R.color.color_333333);
    }

    @Override
    public void initDagger() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this)).build().inject(this);
    }

    private void initData() {
        userInfo = UserManager.getInstance().getCurrentUserInfo();
        if (userInfo != null) {
            if (!StringUtils.isEmpty(userInfo.getRealName())) {
                ntvUserName.setValueText(StringUtils.getDesensitizeName(userInfo.getRealName()));
            }
            if (!StringUtils.isEmpty(userInfo.getPhone()))
                ntvTelNumber.setValueText(StringUtils.desensitization(userInfo.getPhone(), 3, 4, "****"));
            if (!StringUtils.isEmpty(userInfo.getIdcardNo()))
                ntvUserId.setValueText(StringUtils.desensitization(userInfo.getIdcardNo(), 4, 4, "**********"));
            cbGesturePassword.setChecked(userInfo.getSettings().isIfGesture());
            cbTouchId.setChecked(userInfo.getSettings().isIfTouchId());

            cbTouchId.setOnCheckedChangeListener(this);
            cbGesturePassword.setOnCheckedChangeListener(this);
        }
    }

    FingerprintCore fingerprintCore;

    public void initView() {
        setTitle("设置");
        CheckDeposeManager.getInstance().checkDeposeStatus(RxView.clicks(ntvTransPassword).throttleFirst(2, TimeUnit.SECONDS), activity, new CheckDeposeManager.CheckResult() {
            @Override
            public void checkResult(boolean result) {
                if (result) personSetPresenter.resetPassword();
            }
        });
        ntvVersionInfo.setText(AppUtil.versionName() + "(" + AppUtil.versionCode() + ")");
        fingerprintCore = new FingerprintCore(activity, false);
        if (!fingerprintCore.isHardwareDetected()) {
            findViewById(R.id.rl_touch_id).setVisibility(View.GONE);
        }
        initData();
    }

    @OnClick({R.id.ntv_password, R.id.ntv_tel_number, R.id.ntv_trans_set, R.id.ntv_about_we, R.id.ntv_contact_us, R.id.ntv_out_login})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ntv_password:
                toActivity(new Intent(activity, SetPasswordActivity.class).putExtra(SetPasswordActivity.CHARGE_PASSWORD_TYPE, 1));
                break;
            case R.id.ntv_tel_number:
                toActivity(SafeVerifyActivity.class);
                break;
            case R.id.ntv_trans_set:
                startActivity(new Intent(activity, PushSetActivity.class));
                break;
            case R.id.ntv_about_we:
                break;
            case R.id.ntv_contact_us:
                toActivity(ContactUsActivity.class);
                break;
            case R.id.ntv_out_login:
                personSetPresenter.loginOut();
                TagAliasOperatorHelper.getInstance().deleteAlias();
                TagAliasOperatorHelper.getInstance().cleanTags();
                break;
        }
    }

    /**
     * 退出登录结果
     *
     * @param result
     */
    @Override
    public void loginOutResult(boolean result) {
        toActivity(LoginActivity.class);
        finish();
    }

    /**
     * 设置交易密码
     *
     * @param depositLinkBean
     */
    @Override
    public void resetPasswordResult(DepositLinkBean depositLinkBean) {
        Intent intent = new Intent();
        intent.setClass(activity, DepositWebActivity.class)
                .putExtra(DepositWebActivity.DEPOSIT, depositLinkBean)
                .putExtra("TITLE", "交易密码");
        startActivity(intent);
    }

    /**
     * 开启手势结果
     *
     * @param result
     */
    @Override
    public void openGestureResult(boolean result) {
        if (!result)
            cbGesturePassword.setChecked(!cbGesturePassword.isChecked());
        else {
            if (cbGesturePassword.isChecked())
                cbTouchId.setChecked(false);
            UserInfoBean userInfoBean = UserManager.getInstance().getCurrentUserInfo();
            userInfoBean.getSettings().setIfGesture(cbGesturePassword.isChecked());
            userInfoBean.getSettings().setIfTouchId(false);
            UserManager.getInstance().saveUserInfo(userInfoBean);
        }
    }

    /**
     * 开启指纹
     *
     * @param result
     */
    @Override
    public void openTouchIdResult(boolean result) {
        if (!result)
            cbTouchId.setChecked(!cbTouchId.isChecked());
        else {
            if (cbTouchId.isChecked())
                cbGesturePassword.setChecked(false);
            UserInfoBean userInfoBean = UserManager.getInstance().getCurrentUserInfo();
            userInfoBean.getSettings().setIfGesture(false);
            userInfoBean.getSettings().setIfTouchId(cbTouchId.isChecked());
            UserManager.getInstance().saveUserInfo(userInfoBean);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_gesture_password:
                if (isChecked) {
                    startActivityForResult(new Intent(this, CreateGestureActivity.class)
                            .putExtra(CreateGestureActivity.PATH, 1), REQUEST_SET_GESTURE);
                } else {
                    personSetPresenter.setIfGesture(false);
                }
                break;
            case R.id.cb_touch_id:
                if (isChecked)
                    new TouchIdDialog(this, new TouchIdDialog.CallBack() {
                        @Override
                        public void verifyResult(boolean result) {
                            if (result) {
                                personSetPresenter.setIfTouchID(true);
                            } else {
                                cbTouchId.setChecked(false);
                            }
                        }
                    }).show();
                else {
                    personSetPresenter.setIfTouchID(false);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SET_GESTURE) {
            if (resultCode == RESULT_OK) {
                personSetPresenter.setIfGesture(true);
            } else {
                cbGesturePassword.setChecked(false);
            }
        }
    }
}
