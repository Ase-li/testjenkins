package com.chd.chd56lc.widget.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.WithDrawInfo;

/**
 * Created by li on 2018/3/24.
 */

public class WithdrawDialog extends AlertDialog implements View.OnClickListener {


    private Context context;
    private WithdrawCallBack callBack;
    private String withdrawMoney;
    private WithDrawInfo withDrawInfo;
    private CheckBox cbSelect;
    private TextView tvWithdrawalAmount;//显示实际提现金额
    private TextView tvTixianFee;
    private TextView tvTixianFeePlan;

    public WithdrawDialog(@NonNull Context context, String withdrawMoney, WithDrawInfo withDrawInfo, WithdrawCallBack callBack) {
        super(context, R.style.Dialog);
        this.callBack = callBack;
        this.context = context;
        this.withdrawMoney = withdrawMoney;
        this.withDrawInfo = withDrawInfo;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.mypopwindow_anim_style); // 添加动画效果
        setContentView(R.layout.dailog_withdraw_tips);
        initView();
    }

    @SuppressLint("SetTextI18n")
    public void initView() {
        if (withDrawInfo == null) dismiss();
        tvWithdrawalAmount = findViewById(R.id.tv_withdrawal_amount);
        tvTixianFee = findViewById(R.id.tv_tixian_fee);
        tvTixianFeePlan = findViewById(R.id.tv_tixian_fee_plan);
        tvTixianFeePlan.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ((TextView) findViewById(R.id.tv_rule)).setText(withDrawInfo.getWithdrawDesc());
        cbSelect = findViewById(R.id.cb_tanchuang_select);
        ((TextView) findViewById(R.id.tv_coupon_count)).setText(withDrawInfo.getBondCount() + "张");
        findViewById(R.id.tv_withdrawals).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_free_withdrawals)).setText(withDrawInfo.getFreeWithdrawTimes() + "次");
        if (withDrawInfo.getFreeWithdrawTimes() != 0) {
            tvWithdrawalAmount.setText(withdrawMoney + "元");
            tvTixianFee.setText("0元");
            tvTixianFeePlan.setText(withDrawInfo.getWithdrawFee() + "元");
            tvTixianFeePlan.setVisibility(View.VISIBLE);//当有免费提现次数时，不显示提现券选择行
            findViewById(R.id.rl_withdraw_arch).setVisibility(View.GONE);
        } else {
            tvWithdrawalAmount.setText(Double.parseDouble(withdrawMoney) - withDrawInfo.getWithdrawFee() + "元");
        }
        cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && withDrawInfo.getBondCount() > 0) {
                    tvWithdrawalAmount.setText(withdrawMoney + "元");
                    tvTixianFee.setText("0元");
                    tvTixianFeePlan.setText(withDrawInfo.getWithdrawFee() + "元");
                } else {
                    tvTixianFeePlan.setVisibility(View.GONE);
                    tvTixianFee.setText(withDrawInfo.getWithdrawFee() + "元");
                    tvWithdrawalAmount.setText(Double.parseDouble(withdrawMoney) - withDrawInfo.getWithdrawFee() + "元");
                }
            }
        });
        if (withDrawInfo.getBondCount() == 0) {
            ((TextView) findViewById(R.id.tv_coupon_count)).setEnabled(false);
        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (cbSelect.isChecked())
            callBack.withdraw(Double.parseDouble(withdrawMoney), true);
        else
            callBack.withdraw(Double.parseDouble(withdrawMoney), false);
        dismiss();
    }

    public interface WithdrawCallBack {
        void withdraw(double withdrawMoney, boolean couponStatus);
    }
}
