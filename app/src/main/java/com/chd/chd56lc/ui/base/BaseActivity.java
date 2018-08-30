package com.chd.chd56lc.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.manager.AppManager;
import com.chd.chd56lc.manager.SystemBarTintManager;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.utils.CleanLeakUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    Unbinder bind;
    protected View rootView = null;
    protected BaseActivity activity;
    private InputMethodManager manager;
    private int rightIconId;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        hideKeyboard();
    }


    @Override
    protected void onDestroy() {
        bind.unbind();
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
        CleanLeakUtils.fixInputMethodManagerLeak(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBarTint();
        setContentView(loadLayoutResID());
        bind = ButterKnife.bind(this);
        activity = this;
        AppManager.getAppManager().addActivity(this);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initDagger();
        initView();
    }

    //必须重写
    public abstract int loadLayoutResID();

    //必须重写
    public void initDagger() {
    }

    ;

    //必须重写
    public abstract void initView();


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        Unbinder bind = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        Unbinder bind = ButterKnife.bind(this);
    }


    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取深主题色
     */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    private ProgressDialog dialog;

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this, R.style.progress_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
        dialog.show();
    }

    public void showMsgLoading(String msg) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(this, R.style.progress_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        rootView = getLayoutInflater().inflate(layoutResID, null);
        if (rootView.findViewById(R.id.ll_back) != null) {
            rootView.findViewById(R.id.ll_back).setOnClickListener(this);
        }
        super.setContentView(rootView);
    }

    public void setTitle(String title) {
        if (rootView.findViewById(R.id.tv_title) != null) {
            if (rootView.findViewById(R.id.tv_title) instanceof TextView) {
                ((TextView) rootView.findViewById(R.id.tv_title)).setText(title);
            }
        }
    }

    public void setTitleColor(int color) {
        if (rootView.findViewById(R.id.tv_title) != null) {
            if (rootView.findViewById(R.id.tv_title) instanceof TextView) {
                ((TextView) rootView.findViewById(R.id.tv_title)).setTextColor(color);
            }
        }
    }

    public void setTitleBgColor(int color) {
        if (rootView.findViewById(R.id.layoutAll) != null) {
            if (rootView.findViewById(R.id.layoutAll) instanceof RelativeLayout) {
                ((RelativeLayout) rootView.findViewById(R.id.layoutAll)).setBackgroundColor(color);
            }
        }
    }


    public void setTitle(int stringResID) {
        if (rootView.findViewById(R.id.tv_title) != null) {
            if (rootView.findViewById(R.id.tv_title) instanceof TextView) {
                ((TextView) rootView.findViewById(R.id.tv_title)).setText(stringResID);
            }
        }
    }


    /**
     * 设置标题
     */
    public void setTitle(String title, int resouceId) {
        if (rootView.findViewById(resouceId) != null) {
            if (rootView.findViewById(resouceId) instanceof TextView) {
                ((TextView) rootView.findViewById(resouceId)).setText(title);
            }
        }
    }

    /**
     * 设置标题右边的文字
     */
    public void setRight(String rightText, int rightIconId) {
        if (rootView.findViewById(R.id.ll_right) != null) {
            if (rootView.findViewById(R.id.ll_right) instanceof LinearLayout) {
                rootView.findViewById(R.id.ll_right).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.ll_right).setOnClickListener(this);
            }
        }
        if (!StringUtils.isEmpty(rightText) && rootView.findViewById(R.id.tv_right) != null) {
            if (rootView.findViewById(R.id.tv_right) instanceof TextView) {
                ((TextView) rootView.findViewById(R.id.tv_right)).setVisibility(View.VISIBLE);
                ((TextView) rootView.findViewById(R.id.tv_right)).setText(rightText);
            }
        }
        if (rightIconId != 0 && rootView.findViewById(R.id.iv_right) != null) {
            if (rootView.findViewById(R.id.iv_right) instanceof ImageView) {
                ((ImageView) rootView.findViewById(R.id.iv_right)).setVisibility(View.VISIBLE);
                ((ImageView) rootView.findViewById(R.id.iv_right)).setImageResource(rightIconId);
                this.rightIconId = rightIconId;
            }
        }

    }

    /**
     * 设置标题左边的文字
     */
    public void setLeft(String leftText, int leftIconId) {
        if (rootView.findViewById(R.id.ll_back) != null) {
            if (rootView.findViewById(R.id.ll_back) instanceof LinearLayout) {
                rootView.findViewById(R.id.ll_back).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.ll_back).setOnClickListener(this);
            }
        }
        if (!StringUtils.isEmpty(leftText) && rootView.findViewById(R.id.tv_left) != null) {
            if (rootView.findViewById(R.id.tv_left) instanceof TextView) {
                ((TextView) rootView.findViewById(R.id.tv_left)).setVisibility(View.VISIBLE);
                ((TextView) rootView.findViewById(R.id.tv_left)).setText(leftText);
            }
        }
        if (leftIconId != 0 && rootView.findViewById(R.id.iv_back) != null) {
            if (rootView.findViewById(R.id.iv_back) instanceof ImageView) {
                ((ImageView) rootView.findViewById(R.id.iv_back)).setVisibility(View.VISIBLE);
                ((ImageView) rootView.findViewById(R.id.iv_back)).setImageResource(leftIconId);
            }
        }

    }

    /**
     * 获取标题右边的文字
     */
    public TextView getRightTxt(int resouceId) {
        if (rootView.findViewById(resouceId) != null) {
            if (rootView.findViewById(resouceId) instanceof TextView) {
                return ((TextView) rootView.findViewById(resouceId));
            }
        }
        return null;
    }

    /**
     * 获取标题文字
     */
    public CharSequence getTitleTxt() {
        if (rootView.findViewById(R.id.tv_title) != null) {
            if (rootView.findViewById(R.id.tv_title) instanceof TextView) {
                return ((TextView) rootView.findViewById(R.id.tv_title)).getText();
            }
        }
        return null;
    }

    public void toActivity(Class activityClass) {
        startActivity(new Intent(activity, activityClass));
    }

    public void toActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();    // 共同操作
                break;
            case R.id.ll_right:
                if (rightIconId == R.mipmap.btn_ktcgzh_kf)
                    toActivity(new Intent(activity, CommonWebActivity.class)
                            .putExtra(CommonWebActivity.URL, Url.USER_MYSERVICE)
                            .putExtra(CommonWebActivity.TITLE, "客服中心"));
                break;
        }
    }


    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {

        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭某个view的软键盘
     */
    public void hideKeyboard(View view) {
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
