package com.chd.chd56lc.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.ui.activity.web.DepositWebActivity;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.dialog.DepositoryDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CheckDeposeManager {
    private static CheckDeposeManager checkDeposeManager;
    private UserServerModel userServerModel;

    private CheckDeposeManager() {
    }

    public static CheckDeposeManager getInstance() {
        if (checkDeposeManager == null) {
            synchronized (CheckDeposeManager.class) {
                if (checkDeposeManager == null) {
                    checkDeposeManager = new CheckDeposeManager();
                }
            }
        }
        return checkDeposeManager;
    }

    /**
     * 检查开户状态
     *
     * @param observable
     * @param context
     * @param checkResult 回调结果
     */
    @SuppressLint("CheckResult")
    public void checkDeposeStatus(Observable<Object> observable, final Context context, final CheckResult checkResult) {
        observable.observeOn(Schedulers.io())
                .flatMap(new Function<Object, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Object o) throws Exception {
                        if (UserManager.getInstance().isLogin()) {
                            return Observable.just(true);
                        } else {
                            return Observable.just(false).delay(200, TimeUnit.MILLISECONDS);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    if (UserManager.getInstance().getDepositoryStatus() == 3) {
                        checkResult.checkResult(true);
                        return Observable.empty();
                    } else {
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).showLoading();
                            checkResult.checkResult(false);
                        }
                        return Observable.just(true);
                    }
                } else {
                    BaseApplication.getAppComponent().customToast().setText(UIUtils.getString(R.string.personal_not_login));
                    context.startActivity(new Intent(context, LoginActivity.class));
                    checkResult.checkResult(false);
                    return Observable.empty();
                }
            }
        }).observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<BaseBean<DepositLinkBean>>>() {
                    @Override
                    public ObservableSource<BaseBean<DepositLinkBean>> apply(Boolean aBoolean) throws Exception {
                        return new UserServerModel().getDepositInfo();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<DepositLinkBean>() {
                    @Override
                    public void onSuccess(DepositLinkBean depositLinkBean) {
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).dismissLoading();
                        }
                        MyInvestCallBack myInvestCallBack = new MyInvestCallBack(depositLinkBean);
                        switch (depositLinkBean.getDepositStatus()) {
                            case 0:
                                new DepositoryDialog(context, DepositoryDialog.OPEN_DEPOSITORY_ACCOUNT, myInvestCallBack).show();
                                checkResult.checkResult(false);
                                break;
                            case 1:
                                new DepositoryDialog(context, DepositoryDialog.BID_CARD, myInvestCallBack).show();
                                checkResult.checkResult(false);
                                break;
                            case 2:
                                new DepositoryDialog(context, DepositoryDialog.SET_PAYMENT_PASSWORD, myInvestCallBack).show();
                                checkResult.checkResult(false);
                                break;
                            case 3:
                                checkResult.checkResult(true);
                                break;
                            case 4:
                                BaseApplication.getAppComponent().customToast().setText("存管开户中，请稍后");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).dismissLoading();
                            checkResult.checkResult(false);
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    /**
     * 检查开户，未绑定银行卡也进入下一个，仅用于我的银行卡
     *
     * @param observable
     * @param context
     * @param checkResult 回调结果
     */
    @SuppressLint("CheckResult")
    public void checkOpenDeposet(Observable<Object> observable, final Context context, final CheckDepositResult checkResult) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Object, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Object o) throws Exception {
                        if (UserManager.getInstance().isLogin()) {
                            return Observable.just(true);
                        } else {
                            return Observable.just(false).delay(100000, TimeUnit.MILLISECONDS);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    if (UserManager.getInstance().getDepositoryStatus() == 3) {
                        checkResult.checkDepositoryResult(true);
                        return Observable.empty();
                    } else {
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).showLoading();
                        }
                        return Observable.just(true);
                    }
                } else {
                    BaseApplication.getAppComponent().customToast().setText(UIUtils.getString(R.string.personal_not_login));
                    context.startActivity(new Intent(context, LoginActivity.class));
                    checkResult.checkDepositoryResult(false);
                    return Observable.empty();
                }
            }
        }).observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<BaseBean<DepositLinkBean>>>() {
                    @Override
                    public ObservableSource<BaseBean<DepositLinkBean>> apply(Boolean aBoolean) throws Exception {
                        return new UserServerModel().getDepositInfo();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<DepositLinkBean>() {
                    @Override
                    public void onSuccess(DepositLinkBean depositLinkBean) {
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).dismissLoading();
                        }
                        MyInvestCallBack myInvestCallBack = new MyInvestCallBack(depositLinkBean);
                        switch (depositLinkBean.getDepositStatus()) {
                            case 0:
                                new DepositoryDialog(context, DepositoryDialog.OPEN_DEPOSITORY_ACCOUNT, myInvestCallBack).show();
                                break;
                            default:
                                checkResult.checkDepositoryResult(true);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).dismissLoading();
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    static class MyInvestCallBack implements DepositoryDialog.InvestCallBack {
        private DepositLinkBean depositLinkBean;

        public MyInvestCallBack(DepositLinkBean depositLinkBean) {
            this.depositLinkBean = depositLinkBean;
        }

        @Override
        public void investConfirm(int investDialogType, Context context) {
            Intent intent = new Intent();
            switch (investDialogType) {
                case DepositoryDialog.BID_CARD:
                    if (depositLinkBean != null && !StringUtils.isEmpty(depositLinkBean.getUrl())) {
                        intent.setClass(context, DepositWebActivity.class)
                                .putExtra(DepositWebActivity.DEPOSIT, depositLinkBean)
                                .putExtra("TITLE", "绑定银行卡")
                        ;
                        context.startActivity(intent);
                    } else {
                        BaseApplication.getAppComponent().customToast().setText("银行卡绑定异常请咨询客服");
                    }
                    break;
                case DepositoryDialog.OPEN_DEPOSITORY_ACCOUNT:
                    if (depositLinkBean != null && !StringUtils.isEmpty(depositLinkBean.getUrl())) {
                        intent.setClass(context, DepositWebActivity.class).
                                putExtra(DepositWebActivity.DEPOSIT, depositLinkBean)
                                .putExtra("TITLE", "开通银行存管");
                        context.startActivity(intent);
                    } else {
                        BaseApplication.getAppComponent().customToast().setText("存管开通异常请咨询客服");
                    }
                    break;
                case DepositoryDialog.SET_PAYMENT_PASSWORD:
                    if (depositLinkBean != null && !StringUtils.isEmpty(depositLinkBean.getUrl())) {
                        intent.setClass(context, DepositWebActivity.class)
                                .putExtra(DepositWebActivity.DEPOSIT, depositLinkBean)
                                .putExtra("TITLE", "设置交易密码");
                        context.startActivity(intent);
                    } else {
                        BaseApplication.getAppComponent().customToast().setText("设置密码异常请咨询客服");
                    }
                    break;
            }

        }
    }

    //存管检查回调
    public interface CheckResult {
        /**
         * 存管检查结果回调
         *
         * @param result true 为开通，false为未登录或者未开通
         */
        void checkResult(boolean result);
    }

    //是否开通存管
    public interface CheckDepositResult {
        /**
         * 是否开通存管
         *
         * @param result true 为开通，false为未登录或者未开通
         */
        void checkDepositoryResult(boolean result);
    }
}
