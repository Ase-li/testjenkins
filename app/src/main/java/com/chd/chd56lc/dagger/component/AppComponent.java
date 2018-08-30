package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.modules.AppModule;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.widget.CustomToast;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(BaseApplication application);

    void inject(MainActivity mainActivity);

    CustomToast customToast();

    Retrofit retrofit();
}
