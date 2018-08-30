package com.chd.chd56lc.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by li on 2017/9/11.
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,"glide_cache",256*1024*1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(MyImageModel.class, InputStream.class, new MyImageLoader.Factory());
    }
}
