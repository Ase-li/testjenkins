package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.net.GsonConverter.GsonConverterFactory;
import com.chd.chd56lc.net.cookie.CookieJarImpl;
import com.chd.chd56lc.net.cookie.store.SPCookieStore;
import com.chd.chd56lc.net.interceptor.HttpLoggingInterceptor;
import com.chd.chd56lc.widget.CustomToast;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class AppModule {

    private BaseApplication baseApplication;

    public AppModule(BaseApplication baseApplication) {
        this.baseApplication = baseApplication;
    }

    @Provides
    @Singleton
    public OkHttpClient providesBuildClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder build = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .cookieJar(new CookieJarImpl(new SPCookieStore(BaseApplication.getInstance())));
        return build.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Url.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public CustomToast providesCustonToast() {
        CustomToast toast = new CustomToast(baseApplication);
        toast.init(0, true);
        return toast;
    }
}
