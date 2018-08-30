package com.chd.chd56lc.ui.activity.personCenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.widget.dialog.TouchIdDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class VerifyTouchIdActivity extends BaseActivity {

    @BindView(R.id.iv_touch)
    ImageView ivTouch;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_verify_touch_id;
    }

    @Override
    public void initView() {
        setTitle("验证指纹");
    }

    @OnClick({R.id.iv_touch, R.id.tv_switch_account, R.id.tv_password_login, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                moveTaskToBack(true);
                break;
            case R.id.iv_touch:
                ivTouch.setEnabled(false);
                new TouchIdDialog(this, new TouchIdDialog.CallBack() {
                    @Override
                    public void verifyResult(boolean result) {
                        if (result) {
                            finish();
                        }
                        ivTouch.setEnabled(true);
                    }
                }).show();
                break;
            case R.id.tv_switch_account:
                toActivity(new Intent(this, LoginActivity.class)
                        .putExtra(LoginActivity.CHANNEL, 1));
                break;
            case R.id.tv_password_login:
                toActivity(new Intent(this, LoginActivity.class)
                        .putExtra(LoginActivity.MOBILE, UserManager.getInstance().getCurrentUserInfo().getPhone())
                        .putExtra(LoginActivity.CHANNEL, 1));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }
}
