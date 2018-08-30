package com.chd.chd56lc.ui.activity.personCenter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerUserServiceComponent;
import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.presenter.GesturePresenter;
import com.chd.chd56lc.mvp.view.ICreateGestureView;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.chd.chd56lc.widget.gestureView.LockPatternIndicator;
import com.chd.chd56lc.widget.gestureView.LockPatternView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * create gesture activity
 * Created by Sym on 2015/12/23.
 */
public class CreateGestureActivity extends BaseActivity implements ICreateGestureView {
    public static final String PATH = "path";
    @BindView(R.id.lockPatterIndicator)
    LockPatternIndicator lockPatternIndicator;
    @BindView(R.id.lockPatternView)
    LockPatternView lockPatternView;
    @BindView(R.id.tv_message_hint)
    TextView tvMessageHint;
    @BindView(R.id.rl_jump)
    RelativeLayout rlJump;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private List<LockPatternView.Cell> mChosenPattern = null;
    private static final long DELAYTIME = 600L;

    @Named("createGesture")
    @Inject
    GesturePresenter gesturePresenter;
    @Inject
    CustomToast toast;

    private int path;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_gesture;
    }

    @Override
    public void initDagger() {
        DaggerUserServiceComponent.builder().appComponent(BaseApplication.getAppComponent())
                .userServiceModule(new UserServiceModule(this)).build().inject(this);
    }

    @Override
    public void initView() {
        setTitle("设置手势密码");
        path = getIntent().getIntExtra(PATH, 0);
        if (path == 1) {
            rlJump.setVisibility(View.GONE);
        } else {
            llBack.setVisibility(View.GONE);
        }
        lockPatternView.setOnPatternListener(patternListener);
        tvPhone.setText(StringUtils.desensitization(UserManager.getInstance().getCurrentUserInfo().getPhone(), 3, 4, "****"));
    }


    /**
     * 手势监听
     */
    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
            //updateStatus(Status.DEFAULT, null);
            lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            //Log.e(TAG, "--onPatternDetected--");
            if (mChosenPattern == null && pattern.size() >= 4) {
                mChosenPattern = new ArrayList<LockPatternView.Cell>(pattern);
                updateStatus(Status.CORRECT, pattern);
            } else if (mChosenPattern == null && pattern.size() < 4) {
                updateStatus(Status.LESSERROR, pattern);
            } else if (mChosenPattern != null) {
                if (mChosenPattern.equals(pattern)) {
                    updateStatus(Status.CONFIRMCORRECT, pattern);
                } else {
                    updateStatus(Status.CONFIRMERROR, pattern);
                }
            }
        }
    };

    /**
     * 更新状态
     *
     * @param status
     * @param pattern
     */
    private void updateStatus(Status status, List<LockPatternView.Cell> pattern) {
        tvMessageHint.setTextColor(getResources().getColor(status.colorId));
        tvMessageHint.setText(status.strId);
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case CORRECT:
                updateLockPatternIndicator();
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case LESSERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case CONFIRMERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                mChosenPattern=null;
                updateLockPatternIndicator();
                break;
            case CONFIRMCORRECT:
                saveChosenPattern(pattern);
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
        }
    }

    /**
     * 更新 Indicator
     */
    private void updateLockPatternIndicator() {
//        if (mChosenPattern == null)
//            return;
        lockPatternIndicator.setIndicator(mChosenPattern);
    }

    /**
     * 成功设置了手势密码(跳到首页)
     */
    public void setLockPatternResult(boolean result) {
        if (result) {
            toast.setText("手势设置成功");
            if (path == 1) {
                setResult(RESULT_OK);
                finish();
            } else {
                toActivity(MainActivity.class);
                finish();
            }
        } else {
            toast.setText("手势设置失败");
            updateStatus(Status.DEFAULT, null);
        }
    }

    /**
     * 发起请求
     */
    private void saveChosenPattern(List<LockPatternView.Cell> cells) {
        StringBuilder stringBuilder = new StringBuilder(20);
        for (LockPatternView.Cell cell :
                cells) {
            stringBuilder.append(cell.getIndex() + "");
        }
        gesturePresenter.setGesture(stringBuilder.toString(), stringBuilder.toString());
    }


    @OnClick(R.id.tv_jump)
    public void onClick() {
        toActivity(MainActivity.class);
        finish();
    }

    private enum Status {
        //默认的状态，刚开始的时候（初始化状态）
        DEFAULT(R.string.create_gesture_default, R.color.color_666666),
        //第一次记录成功
        CORRECT(R.string.create_gesture_correct, R.color.color_666666),
        //连接的点数小于4（二次确认的时候就不再提示连接的点数小于4，而是提示确认错误）
        LESSERROR(R.string.create_gesture_less_error, R.color.color_666666),
        //二次确认错误
        CONFIRMERROR(R.string.create_gesture_confirm_error, R.color.color_ff1f1f),
        //二次确认正确
        CONFIRMCORRECT(R.string.create_gesture_confirm_correct, R.color.color_666666);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }

        private int strId;
        private int colorId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gesturePresenter != null)
            gesturePresenter.onUnsubscribe();
    }
}
