package com.chd.chd56lc.glide;

/**
 * Created by li on 2017/9/11.
 */

public class MyImageModel {
    private final String imageUrl;

    public MyImageModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageId() {
        if (imageUrl != null && imageUrl.contains("?")) {
            return imageUrl.substring(0, imageUrl.lastIndexOf("?"));
        } else {
            return imageUrl;
        }
    }
}
