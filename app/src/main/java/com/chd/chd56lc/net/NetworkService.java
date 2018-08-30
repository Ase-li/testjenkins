package com.chd.chd56lc.net;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.net.cookie.CookieJarImpl;
import com.chd.chd56lc.net.cookie.store.SPCookieStore;
import com.chd.chd56lc.net.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.chd.chd56lc.net.GsonConverter.GsonConverterFactory;

/**
 * Created by lyl.
 */

public class NetworkService {

    private Retrofit retrofit;

    public NetworkService(String baseUrl) {
        OkHttpClient okHttpClient = buildClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    public OkHttpClient buildClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder build = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .cookieJar(new CookieJarImpl(new SPCookieStore(BaseApplication.getInstance())));
        return build.build();
    }
}
