package com.chd.chd56lc.glide;

/**
 * Created by li on 2017/9/11.
 */

public class GlideUtils {
    /**
     * url判空
     *
     * @param url
     * @return
     */
    public static MyImageModel getUrl(String url) {
        if (url != null && url.length()>0) {
            return new MyImageModel(url);
        }
        return null;
    }
}
