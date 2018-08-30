package com.chd.chd56lc.ui.activity.circum;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerDepositComponent;
import com.chd.chd56lc.dagger.modules.DepositModule;
import com.chd.chd56lc.mvp.presenter.ResultDepositPresenter;
import com.chd.chd56lc.mvp.view.IResultDepositView;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.ui.activity.investment.InvestTransferableActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultDepositActivity extends BaseActivity implements IResultDepositView {

    @BindView(R.id.iv_fail)
    ImageView ivFail;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_back1)
    TextView tvBack1;

    @Inject
    CustomToast toast;
    @Inject
    ResultDepositPresenter resultDepositPresenter;

    private String title;
    private String orderId;
    private Handler handler = new Handler();
    private long lastRequestTime;
    private Animation animation;

    /**
     * 返回一个用于设置界面的布局id
     */
    @Override
    public int loadLayoutResID() {
        return R.layout.activity_result_deposit;
    }

    @Override
    public void initView() {
        DaggerDepositComponent.builder().appComponent(BaseApplication.getAppComponent())
                .depositModule(new DepositModule(this)).build()
                .inject(this);
        orderId = getIntent().getStringExtra("order_id");
        animation = AnimationUtils.loadAnimation(this, R.anim.refresh);
        initData();
    }

    /**
     * 初始化数据，并显示到界面上
     */
    public void initData() {
        title = getIntent().getStringExtra("title");
        setRight(null, R.mipmap.btn_ktcgzh_kf);
        setTitleBgColor(UIUtils.getColor(R.color.app_main_black));
        if (title.contains("失败")) {
            ivFail.setImageResource(R.mipmap.icon_ktcg_mybdyhk);
            tvBack1.setText("返回");
        } else {
            tvPhone.setVisibility(View.GONE);
            ivFail.setImageResource(R.mipmap.icon_czy_czcg);
            tvBack1.setText("完成");
        }
        setTitle(title);
        switch (title) {
            case "开户失败":
                tvContent.setText("银行存管开户失败，请稍后再试或联系客服！");
                break;
            case "投资失败":
                tvContent.setText("投资失败，请稍后再试或联系客服！");
                break;
            case "充值失败":
                tvContent.setText("充值失败，请稍后再试或联系客服！");
                break;
            case "密码设置失败":
                tvContent.setText("密码设置失败，请稍后再试或联系客服！");
                break;
            case "提现失败":
                tvContent.setText("提现失败，请稍后再试或联系客服！");
                break;
            case "转让失败":
                tvContent.setText("转让失败，请稍后再试或联系客服！");
                break;
            case "开户成功":
                tvContent.setText("开户成功");
                break;
            case "密码设置成功":
                tvContent.setText("密码设置成功");
                break;
            case "转让成功":
                tvContent.setText("转让申请已提交");
                lastRequestTime = System.currentTimeMillis();
                resultDepositPresenter.buyCreditPSuccessPage(orderId);
                break;
            case "充值成功":
                tvContent.setText("充值申请已提交");
                ivFail.setImageResource(R.mipmap.loading);
                ivFail.setAnimation(animation);
                animation.start();
                lastRequestTime = System.currentTimeMillis();
                resultDepositPresenter.updateOrderStatus(orderId);
                break;
            case "提现成功":
                tvContent.setText("提现申请已提交");
                ivFail.setImageResource(R.mipmap.loading);
                ivFail.setAnimation(animation);
                animation.start();
                lastRequestTime = System.currentTimeMillis();
                resultDepositPresenter.preDeductLocalAmount(orderId);
                break;
            case "投资成功":
                tvContent.setText("投标申请已提交");
                lastRequestTime = System.currentTimeMillis();
                resultDepositPresenter.bidApplyPSuccessPage(orderId);
                break;
            case "银行卡绑定失败":
                tvContent.setText("银行卡绑定失败");
                break;
            case "银行卡绑定成功":
                tvContent.setText("银行卡绑定成功");
                break;
            case "手续费签约成功":
                tvContent.setText("签约处理中，请稍候...");
                setTitle("手续费签约");
                ivFail.setImageResource(R.mipmap.loading);
                ivFail.setAnimation(animation);
                animation.start();
                resultDepositPresenter.updateSignDebtStatus();
                break;
            case "手续费签约失败":
                setTitle("手续费签约");
                tvContent.setText("手续费签约失败");
                break;
        }
    }

    @OnClick({R.id.tv_back1})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_back1:
                //暂时这么放着，之后采用EVENBUS告知跳转页面
                if (title.contains("银行卡绑定成功")) {
                    toActivity(MainActivity.class);
                } else if (title.contains("手续费签约成功")) {
                    toActivity(InvestTransferableActivity.class);
                }
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resultDepositPresenter != null) {
            resultDepositPresenter.onUnsubscribe();
            resultDepositPresenter = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (animation != null && ivFail != null) {
            animation.cancel();
            ivFail.clearAnimation();
        }
    }

    @Override
    public void rechargeResult(int statusCode) {
        switch (statusCode) {
            case 1:
                if (animation != null && ivFail != null) {
                    animation.cancel();
                    ivFail.clearAnimation();
                }
                ivFail.setImageResource(R.mipmap.icon_czy_czcg);
                tvContent.setText("充值已成功");
                break;
            case 2:
                if (animation != null && ivFail != null) {
                    animation.cancel();
                    ivFail.clearAnimation();
                }
                ivFail.setImageResource(R.mipmap.icon_ktcg_mybdyhk);
                tvContent.setText("充值已失败");
                break;
            case 4:
                if (System.currentTimeMillis() - lastRequestTime < 10 * 1000) {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (resultDepositPresenter != null)
                                resultDepositPresenter.updateOrderStatus(orderId);
                        }
                    }, 3000);
                }
                break;
        }
    }

    @Override
    public void withDrawResult(int statusCode) {
        switch (statusCode) {
            case 1:
                if (animation != null && ivFail != null) {
                    animation.cancel();
                    ivFail.clearAnimation();
                }
                ivFail.setImageResource(R.mipmap.icon_czy_czcg);
                tvContent.setText("提现已成功");
                break;
            case 2:
                if (animation != null && ivFail != null) {
                    animation.cancel();
                    ivFail.clearAnimation();
                }
                ivFail.setImageResource(R.mipmap.icon_ktcg_mybdyhk);
                tvContent.setText("提现已失败");
                break;
            case 4:
                if (System.currentTimeMillis() - lastRequestTime < 10 * 1000) {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (resultDepositPresenter != null)
                                resultDepositPresenter.preDeductLocalAmount(orderId);
                        }
                    }, 3000);
                }
                break;
        }
    }

    @Override
    public void investmentResult(int statusCode) {

    }

    @Override
    public void transferResult(int statusCode) {

    }

    /**
     * 债转签约回调
     */
    @Override
    public void updateDebtStatus() {
        lastRequestTime = System.currentTimeMillis();
        resultDepositPresenter.getSignDebtStatus();
    }

    /**
     * 签约状态,0 未签约,1 已签约,2 处理中
     */
    @Override
    public void debtStatus(int statusCode) {
        switch (statusCode) {
            case 0:
                break;
            case 1:
                if (animation != null) {
                    animation.cancel();
                    ivFail.clearAnimation();
                }
                ivFail.setImageResource(R.mipmap.icon_czy_czcg);
                tvContent.setText("手续费签约成功");
                tvBack1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvBack1.setVisibility(View.GONE);
                if (System.currentTimeMillis() - lastRequestTime < 10 * 1000) {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (resultDepositPresenter != null)
                                resultDepositPresenter.getSignDebtStatus();
                        }
                    }, 3000);
                }
                break;
        }
    }

}
