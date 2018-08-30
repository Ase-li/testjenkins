package com.chd.chd56lc.glide;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * Created by li on 2017/9/11.
 */

public class MyImageLoader extends BaseGlideUrlLoader<MyImageModel> {
    public MyImageLoader(Context context) {
        super(context);
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(final MyImageModel model, int width,
                                                       int height) {
        return new HttpUrlFetcher(new GlideUrl(model.getImageUrl())) {
            @Override
            public String getId() {
                return model.getImageId();
            }
        };
    }

    @Override
    protected String getUrl(MyImageModel model, int width, int height) {
        return null;
    }

    public static class Factory implements ModelLoaderFactory<MyImageModel, InputStream> {
        @Override
        public ModelLoader<MyImageModel, InputStream> build(Context context,
                                                            GenericLoaderFactory factories) {
            return new MyImageLoader(context);
        }

        @Override
        public void teardown() { /* no op */ }
    }
}
