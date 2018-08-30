package com.chd.chd56lc.net;

import com.chd.chd56lc.utils.Logger;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NetPresenter {
    private CompositeDisposable disposable;

    public NetPresenter() {
        this.disposable = new CompositeDisposable();
        Logger.d("disposable", "已创建");
    }


    /**
     * RXjava取消注册，以避免内存泄露
     */
    public void onUnsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.clear();
        }
    }


    public void addSubscription(Observable observable, ApiCallback observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        disposable.add(observer.getDisposable());
    }

    public Map<String, String> toMap(String queryJson) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("json", queryJson);
        return queryMap;
    }


}
