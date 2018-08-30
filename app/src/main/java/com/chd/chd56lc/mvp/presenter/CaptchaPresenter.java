package com.chd.chd56lc.mvp.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.mvp.view.LoadingView;
import com.chd.chd56lc.net.api.LoginInfoApi;
import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.StringUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CaptchaPresenter {

    private Retrofit retrofit;
    private LoadingView loadingView;
    private Observable<Long> observable;
    private Consumer<Long> consumer;
    private Consumer<Throwable> throwableConsumer;
    private Disposable captchaDisposable;
    private Action action;
    private final long MAX_COUNT_TIME = 60;

    public enum CaptchaType {
        REGISTER("0"), LOGIN("1"), TWICE_CHECK("2"), MODIFY_LOGIN_PASSWORD("3"), MODIFY_TEL("4"), UNBIND_BANK("5"), SAFE_CHECK("6");
        private String type;

        CaptchaType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @Inject
    public CaptchaPresenter(LoadingView loadingView, Retrofit retrofit) {
        this.loadingView = loadingView;
        this.retrofit = retrofit;
    }

    public Disposable getCaptchaDisposable() {
        return captchaDisposable;
    }

    public void disDisposable() {
        if (captchaDisposable != null && !captchaDisposable.isDisposed()) {
            captchaDisposable.dispose();
        }
    }

    /**
     * 获取验证码
     *
     * @param etSmsPhone
     * @param captchaType 验证码类型 0-注册 1-登录 2-二次认证 3-更改登录密码 4-二次认证设置密码
     * @param tvSendCode
     */
    @SuppressLint("CheckResult")
    public void getCaptcha(final EditText etSmsPhone, final EditText etCaptchaCode, final String captchaType, final TextView tvSendCode) {
        observable = RxView.clicks(tvSendCode)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        if (!TextUtils.isEmpty(etSmsPhone.getText().toString()) && StringUtils.isMobileNO(etSmsPhone.getText().toString())) {
                            loadingView.showLoading();
                            etCaptchaCode.setText("");
                            etCaptchaCode.setHint(R.string.input_verification_code);
                            tvSendCode.setEnabled(false);
                            return true;
                        } else {
                            BaseApplication.getAppComponent().customToast().setText("请输入电话号码");
                            return false;
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Object, Observable<BaseBean>>() {
                    @Override
                    public Observable<BaseBean> apply(Object o) throws Exception {
                        return retrofit.create(LoginInfoApi.class).getCaptcha(captchaType, etSmsPhone.getText().toString());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean userInfoBaseBean) throws Exception {
                        if (userInfoBaseBean.getStatus().getErrCode() == 200)
                            return true;
                        else {
                            tvSendCode.setEnabled(true);
                            BaseApplication.getAppComponent().customToast().setText(userInfoBaseBean.getStatus().getMessage());
                            loadingView.dismissLoading();
                            return false;
                        }
                    }
                })
                .flatMap(new Function<BaseBean, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(BaseBean userInfoBaseBean) throws Exception {
                        loadingView.dismissLoading();
                        RxTextView.text(tvSendCode).accept(MAX_COUNT_TIME + "s");
                        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                                .take(MAX_COUNT_TIME).map(new Function<Long, Long>() {
                                    @Override
                                    public Long apply(Long aLong) throws Exception {
                                        return MAX_COUNT_TIME - (aLong + 1);
                                    }
                                });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        consumer = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (aLong == 0) {
                    tvSendCode.setEnabled(true);
                    tvSendCode.setText("发送验证码");
                    if (captchaDisposable != null && !captchaDisposable.isDisposed()) {
                        captchaDisposable.dispose();
                        captchaDisposable = observable.subscribe(consumer, throwableConsumer, action);
                    }
                } else {
                    tvSendCode.setText(aLong + "s");
                }
            }
        };
        throwableConsumer = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Logger.d("异常", throwable.toString());
                loadingView.dismissLoading();
                if (captchaDisposable != null && !captchaDisposable.isDisposed()) {
                    captchaDisposable.dispose();
                }
                captchaDisposable = observable.subscribe(consumer, throwableConsumer, action);
                tvSendCode.setEnabled(true);
            }
        };
        action = new Action() {
            /**
             * Runs the action and optionally throws a checked exception.
             *
             * @throws Exception if the implementation wishes to throw a checked exception
             */
            @Override
            public void run() throws Exception {
                Logger.d("完成");
            }
        };
        captchaDisposable = observable.subscribe(consumer, throwableConsumer, action);
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param captchaType 验证码类型 0-注册 1-登录 2-二次认证 3-更改登录密码 4-二次认证设置密码
     * @param tvSendCode
     */
    @SuppressLint("CheckResult")
    public void getCaptcha(final String phone, final EditText etCaptchaCode, final String captchaType, final TextView tvSendCode) {

        observable = RxView.clicks(tvSendCode)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Object>() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        if (!TextUtils.isEmpty(phone) && StringUtils.isMobileNO(phone)) {
                            loadingView.showLoading();
                            etCaptchaCode.setText("");
                            etCaptchaCode.setHint(R.string.input_verification_code);
                            tvSendCode.setEnabled(false);
                            return true;
                        } else {
                            BaseApplication.getAppComponent().customToast().setText("请输入电话号码");
                            return false;
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Object, Observable<BaseBean>>() {
                    @Override
                    public Observable<BaseBean> apply(Object o) throws Exception {
                        return retrofit.create(LoginInfoApi.class).getCaptcha(captchaType, phone);
                    }
                })
                .filter(new Predicate<BaseBean>() {
                    @Override
                    public boolean test(BaseBean userInfoBaseBean) throws Exception {
                        if (userInfoBaseBean.getStatus().getErrCode() == 200)
                            return true;
                        else {
                            loadingView.dismissLoading();
                            BaseApplication.getAppComponent().customToast().setText(userInfoBaseBean.getStatus().getMessage());
                            tvSendCode.setEnabled(true);
                            return false;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<BaseBean, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(BaseBean userInfoBaseBean) throws Exception {
                        loadingView.dismissLoading();
                        RxTextView.text(tvSendCode).accept(MAX_COUNT_TIME + "s");
                        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                                .take(MAX_COUNT_TIME).map(new Function<Long, Long>() {
                                    @Override
                                    public Long apply(Long aLong) throws Exception {
                                        return MAX_COUNT_TIME - (aLong + 1);
                                    }
                                });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        consumer = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (aLong == 0) {
                    tvSendCode.setEnabled(true);
                    tvSendCode.setText("发送验证码");
                    if (captchaDisposable != null && !captchaDisposable.isDisposed()) {
                        captchaDisposable.dispose();
                        captchaDisposable = observable.subscribe(consumer, throwableConsumer, action);
                    }
                } else {
                    tvSendCode.setText(aLong + "s");
                }
            }
        };
        throwableConsumer = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Logger.d("异常", throwable.toString());
                loadingView.dismissLoading();
                if (captchaDisposable != null && !captchaDisposable.isDisposed()) {
                    captchaDisposable.dispose();
                }
                captchaDisposable = observable.subscribe(consumer, throwableConsumer, action);
                tvSendCode.setEnabled(true);
            }
        };
        action = new Action() {
            /**
             * Runs the action and optionally throws a checked exception.
             *
             * @throws Exception if the implementation wishes to throw a checked exception
             */
            @Override
            public void run() throws Exception {
                Logger.d("完成");
            }
        };
        captchaDisposable = observable.subscribe(consumer, throwableConsumer, action);
    }

}
