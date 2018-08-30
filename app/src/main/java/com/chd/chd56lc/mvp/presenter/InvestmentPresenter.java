package com.chd.chd56lc.mvp.presenter;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.CouponBean;
import com.chd.chd56lc.entity.CouponNum;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.InvestDetail;
import com.chd.chd56lc.mvp.model.ProjectModel;
import com.chd.chd56lc.mvp.view.IInvestmentView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class InvestmentPresenter extends NetPresenter {
    private IInvestmentView iInvestmentView;
    private ProjectModel projectModel;
    private CouponBean.Item item;

    public void setItem(CouponBean.Item item) {
        this.item = item;
    }

    public InvestmentPresenter(IInvestmentView iInvestmentView, ProjectModel projectModel) {
        this.iInvestmentView = iInvestmentView;
        this.projectModel = projectModel;
    }

    public void detailInvest(String id, int type) {
        iInvestmentView.showLoading();
        addSubscription(projectModel.detailInvest(id, type), new ApiCallback<InvestDetail>() {
            @Override
            public void onSuccess(InvestDetail model) {
                iInvestmentView.updateInvestDetail(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iInvestmentView.dismissLoading();
            }
        });
    }

    public void bidApplyP(String bidId, String couponId, double amount) {
        iInvestmentView.showLoading();
        addSubscription(projectModel.bidApplyP(bidId, couponId, amount), new ApiCallback<DepositLinkBean>() {
            @Override
            public void onSuccess(DepositLinkBean model) {
                iInvestmentView.investment(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iInvestmentView.dismissLoading();
            }
        });
    }

    public void buyCreditP(String bidId, String couponId, double amount) {
        iInvestmentView.showLoading();
        addSubscription(projectModel.buyCreditP(bidId, couponId, amount), new ApiCallback<DepositLinkBean>() {
            @Override
            public void onSuccess(DepositLinkBean model) {
                iInvestmentView.investment(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iInvestmentView.dismissLoading();
            }
        });
    }

    /**
     * 查看优惠券数量
     *
     * @param etInvestMoney
     */
    @SuppressLint("CheckResult")
    public void getCouponCount(EditText etInvestMoney, final int scenario, final String bidId) {
        addSubscription(RxTextView.textChanges(etInvestMoney)
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .map(new Function<CharSequence, Double>() {
                    @Override
                    public Double apply(CharSequence o) throws Exception {
                        if (StringUtils.isEmpty(o.toString()))
                            return 0.0;
                        else
                            return Double.parseDouble(o.toString());
                    }
                })
                .flatMap(new Function<Double, ObservableSource<BaseBean<CouponNum>>>() {
                    @Override
                    public ObservableSource<BaseBean<CouponNum>> apply(Double investMoney) throws Exception {
                        //当选择优惠券且投资金额<优惠券限制金额，获取可用优惠券数量
                        if (item == null || investMoney < item.getLimitAmount()) {
                            item = null;
                            return projectModel.getCouponCount(investMoney, scenario, bidId);
                        } else
                            return Observable.empty();
                    }
                }), new ApiCallback<CouponNum>() {
            @Override
            public void onSuccess(CouponNum model) {

                iInvestmentView.couponNum(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 查看优惠券数量
     *
     * @param etInvestMoney
     * @param tvBonusesAndCouponUsed
     */
    @SuppressLint("CheckResult")
    public void getCouponCount(EditText etInvestMoney, final TextView tvBonusesAndCouponUsed, final int scenario, final String bidId) {
        RxTextView.textChanges(etInvestMoney).subscribeOn(Schedulers.io())
                .throttleFirst(2, TimeUnit.SECONDS)
                .flatMap(new Function<CharSequence, ObservableSource<BaseBean<CouponNum>>>() {
                    @Override
                    public ObservableSource<BaseBean<CouponNum>> apply(CharSequence charSequence) throws Exception {
                        if (!StringUtils.isEmpty(charSequence.toString()))
                            return projectModel.getCouponCount(Double.parseDouble(charSequence.toString()), scenario, bidId);
                        else
                            return Observable.empty();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<BaseBean<CouponNum>>() {
                    @Override
                    public boolean test(BaseBean<CouponNum> couponNumBaseBean) throws Exception {
                        if (couponNumBaseBean.getStatus().getErrCode() == 200)
                            return true;
                        else {
                            if (tvBonusesAndCouponUsed != null)
                                tvBonusesAndCouponUsed.setText(UIUtils.getString(R.string.invest_no_enable_coupon));
                            BaseApplication.getAppComponent().customToast().setText(couponNumBaseBean.getStatus().getMessage());
                            return false;
                        }
                    }
                }).subscribe(new Consumer<BaseBean<CouponNum>>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void accept(BaseBean<CouponNum> doubleBaseBean) throws Exception {
                if (tvBonusesAndCouponUsed != null) {
                    if (doubleBaseBean.getData().getCouponCount() == 0)
                        tvBonusesAndCouponUsed.setText(UIUtils.getString(R.string.invest_no_enable_coupon));
                    else
                        tvBonusesAndCouponUsed.setText(doubleBaseBean.getData().getCouponCount() + "张可用");
                }
            }
        });
    }
}
