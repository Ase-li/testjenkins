package com.chd.chd56lc.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.chd.chd56lc.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    Unbinder unbinder;
    protected Context context;
    private static String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupporHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            judgeState(isSupporHidden);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());//fragment内存异常回收时，保存其显示状态
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(loadLayoutResID(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected abstract void initView();

    public abstract int loadLayoutResID();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 判断fragment被意外回收后。也就是内存重启后，是否需要重新显示
     *
     * @param isSupporHidden true-内存重启前改Fragment是hide状态，反之是显示状态
     */
    private void judgeState(boolean isSupporHidden) {
        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
        if (isSupporHidden) {
            mFragmentTransaction.hide(this);
        } else {
            mFragmentTransaction.show(this);
        }
        mFragmentTransaction.commit();
    }


    private ProgressDialog dialog;

    public void showLoading() {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(context, R.style.progress_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
        dialog.show();
    }

    public void showMsgLoading(String msg) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(context, R.style.progress_dialog);
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

    public void toActivity(Class activityClass) {
        startActivity(new Intent(context, activityClass));
    }

    public void toActivity(Intent intent) {
        startActivity(intent);
    }
}
