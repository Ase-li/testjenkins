package com.chd.chd56lc.ui.activity.personCenter;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.GesturePresenter;
import com.chd.chd56lc.mvp.view.ILoginGestureView;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.gestureView.LockPatternView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

import static com.chd.chd56lc.ui.activity.base.LoginActivity.MOBILE;

/**
 * Created by Sym on 2015/12/24.
 */
public class GestureLoginActivity extends BaseActivity implements ILoginGestureView {

    private static final String TAG = "LoginGestureActivity";

    @BindView(R.id.lockPatternView)
    LockPatternView lockPatternView;
    @BindView(R.id.tv_message_hint)
    TextView tvMessageHint;
    @BindView(R.id.tv_forget_gesture_password)
    TextView tvForgetGesturePassword;
    @BindView(R.id.rl_jump)
    RelativeLayout rvJump;

    private static final long DELAYTIME = 600l;

    @Named("loginGesture")
    @Inject
    GesturePresenter gesturePresenter;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_gesture;
    }

    @Override
    public void initDagger() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this))
                .build().inject(this);
    }

    @Override
    public void initView() {
        setTitle("验证手势密码");
        tvPhone.setText(StringUtils.desensitization(UserManager.getInstance().getCurrentUserInfo().getPhone(), 3, 4, "****"));
        lockPatternView.setOnPatternListener(patternListener);
        tvForgetGesturePassword.setVisibility(View.VISIBLE);
        rvJump.setVisibility(View.GONE);
        updateStatus(Status.DEFAULT);
    }

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if (pattern != null) {
                StringBuilder stringBuilder = new StringBuilder(20);
                for (LockPatternView.Cell cell :
                        pattern) {
                    stringBuilder.append(cell.getIndex() + "");
                }
                gesturePresenter.verifyGesture(stringBuilder.toString());
            }
        }
    };

    /**
     * 更新状态
     *
     * @param status
     */
    private void updateStatus(Status status) {
        tvMessageHint.setText(status.strId);
        tvMessageHint.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CORRECT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
        }
    }

    /**
     * 手势登录成功（去首页）
     */
    public void loginGestureResult(boolean result) {
        if (result) {
            updateStatus(Status.CORRECT);
            finish();
        } else {
            updateStatus(Status.ERROR);
        }
    }

    /**
     * 忘记手势密码（去账号登录界面）
     */
    @OnClick(R.id.tv_forget_gesture_password)
    void forgetGesturePasswrod() {
        Intent intent = new Intent(GestureLoginActivity.this, LoginActivity.class)
                .putExtra(MOBILE, UserManager.getInstance().getCurrentUserInfo().getPhone())
                .putExtra(LoginActivity.CHANNEL, 1);
        startActivity(intent);
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_default, R.color.color_666666),
        //密码输入错误
        ERROR(R.string.gesture_error, R.color.color_ff1f1f),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.color_666666);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_back) super.moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gesturePresenter != null)
            gesturePresenter.onUnsubscribe();
    }
}
