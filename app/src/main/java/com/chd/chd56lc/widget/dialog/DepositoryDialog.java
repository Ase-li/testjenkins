package com.chd.chd56lc.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;

/**
 * Created by li on 2018/3/24.
 */

public class DepositoryDialog extends Dialog implements View.OnClickListener {
    /**
     * 设置密码
     */
    public static final int SET_PAYMENT_PASSWORD = 1;
    /**
     * 绑定银行卡
     */
    public static final int BID_CARD = 2;
    /**
     * 开通存管
     */
    public static final int OPEN_DEPOSITORY_ACCOUNT = 3;
    //初始值，点击确认退出当前页面
    private int investDialogType = -1;


    private LayoutInflater inflater;

    private ImageView btnClose;
    private TextView btnConfirm;
    private FrameLayout flDialogContent;
    private Context context;
    private InvestCallBack callBack;


    public DepositoryDialog(@NonNull Context context, int investDialogType, InvestCallBack callBack) {
        super(context, R.style.Dialog);
        this.investDialogType = investDialogType;
        inflater = LayoutInflater.from(context);
        this.callBack = callBack;
        this.context = context;
    }

    public DepositoryDialog(@NonNull Context context, int investDialogType) {
        super(context, R.style.Dialog);
        this.investDialogType = investDialogType;
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.transparency_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_base_multe);
        initView();

    }

    public void initView() {
        btnClose = (ImageView) findViewById(R.id.iv_dialog_close);
        btnConfirm = (TextView) findViewById(R.id.btn_dialog_confirm);
        btnConfirm = (TextView) findViewById(R.id.btn_dialog_confirm);
        flDialogContent = (FrameLayout) findViewById(R.id.fl_invest_content);
        btnConfirm.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        switch (investDialogType) {
            case SET_PAYMENT_PASSWORD:
                btnConfirm.setText("确定");
                View setPayWord = inflater.inflate(R.layout.dialog_invest_set_password, null);
                flDialogContent.addView(setPayWord);
                break;
            case BID_CARD:
                btnConfirm.setText("确定");
                View bidCard = inflater.inflate(R.layout.dialog_invest_bid_card, null);
                flDialogContent.addView(bidCard);
                break;
            case OPEN_DEPOSITORY_ACCOUNT:
                btnConfirm.setText("立即开通");
                View bidopenDepositCard = inflater.inflate(R.layout.dialog_invest_open_deposit, null);
                TextView tvHint = (TextView) bidopenDepositCard.findViewById(R.id.tv_hint);
                String hint = tvHint.getText().toString();
                SpannableString spannableString = new SpannableString(hint);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7E0E"))
                        , hint.indexOf("(暂不支持"), hint.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
                tvHint.setText(spannableString);
                flDialogContent.addView(bidopenDepositCard);
                break;
        }

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_confirm:
                if (callBack != null)
                    callBack.investConfirm(investDialogType, getContext());
                dismiss();
                break;
            case R.id.iv_dialog_close:
                dismiss();
                break;
        }
    }

    public interface InvestCallBack {
        void investConfirm(int investDialogType, Context context);
    }
}
