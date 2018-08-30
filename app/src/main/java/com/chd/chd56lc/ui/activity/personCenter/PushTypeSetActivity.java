package com.chd.chd56lc.ui.activity.personCenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.widget.dialog.SetEmailDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class PushTypeSetActivity extends BaseActivity {

    @BindView(R.id.iv_sms_style)
    ImageView ivSmsStyle;
    @BindView(R.id.iv_app_style)
    ImageView ivAppStyle;
    @BindView(R.id.iv_wechat_style)
    ImageView ivWechatStyle;
    @BindView(R.id.iv_email_style)
    ImageView ivEmailStyle;
    @BindView(R.id.tv_email)
    TextView tvEmail;

    private int pushType;
    private String mEmail;

    public void initView() {
        setTitle("接收方式");
        ivSmsStyle.setVisibility(View.GONE);
        ivAppStyle.setVisibility(View.GONE);
        ivEmailStyle.setVisibility(View.GONE);
        ivWechatStyle.setVisibility(View.GONE);
        pushType = getIntent().getIntExtra(PushSetActivity.PUSH_TYPE, -1);
        mEmail = getIntent().getStringExtra(PushSetActivity.EMAIL);
        switch (pushType) {
            case 0:
                ivSmsStyle.setVisibility(View.VISIBLE);
                break;
            case 1:
                ivAppStyle.setVisibility(View.VISIBLE);
                break;
            case 2:
                ivWechatStyle.setVisibility(View.VISIBLE);
                break;
            case 3:
                ivEmailStyle.setVisibility(View.VISIBLE);
                tvEmail.setVisibility(View.VISIBLE);
                tvEmail.setText(mEmail);
                break;
        }
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_type_style_set;
    }

    @OnClick({R.id.rl_sms_style, R.id.rl_app_style, R.id.rl_wechat_style, R.id.rl_email_style})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rl_sms_style:
                ivSmsStyle.setVisibility(View.VISIBLE);
                ivAppStyle.setVisibility(View.GONE);
                ivEmailStyle.setVisibility(View.GONE);
                ivWechatStyle.setVisibility(View.GONE);
                pushType = 0;
                finish();
                break;
            case R.id.rl_app_style:
                ivSmsStyle.setVisibility(View.GONE);
                ivAppStyle.setVisibility(View.VISIBLE);
                ivEmailStyle.setVisibility(View.GONE);
                ivWechatStyle.setVisibility(View.GONE);
                pushType = 1;
                finish();
                break;
            case R.id.rl_wechat_style:
                ivSmsStyle.setVisibility(View.GONE);
                ivAppStyle.setVisibility(View.GONE);
                ivEmailStyle.setVisibility(View.GONE);
                ivWechatStyle.setVisibility(View.VISIBLE);
                pushType = 2;
                finish();
                break;
            case R.id.rl_email_style:
                new SetEmailDialog(activity, new SetEmailDialog.SetEmailCallBack() {
                    @Override
                    public void callBack(String email) {
                        ivSmsStyle.setVisibility(View.GONE);
                        ivAppStyle.setVisibility(View.GONE);
                        ivEmailStyle.setVisibility(View.VISIBLE);
                        ivWechatStyle.setVisibility(View.GONE);
                        tvEmail.setVisibility(View.VISIBLE);
                        pushType = 3;
                        mEmail = email;
                        finish();
                    }
                }).show();
                break;
        }
    }

    @Override
    public void finish() {
        setResult(RESULT_OK, new Intent().putExtra(PushSetActivity.PUSH_TYPE, pushType).putExtra(PushSetActivity.EMAIL, mEmail));
        super.finish();
    }
}
