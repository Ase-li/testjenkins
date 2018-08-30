package com.chd.chd56lc.net.api;

import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.entity.BannerOrFloatWindow;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.PageListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OperateApi {
    /**
     * 首页banner
     *
     * @return
     */
    @GET("api/service-operation/app/activityManage/listBanner")
    Observable<BaseBean<BannerOrFloatWindow>> listBanner();

    /**
     * 活动
     *
     * @return
     */
    @GET("api/service-operation/app/activityManage/paginateActivity")
    Observable<BaseBean<PageListBean<BannerBean>>> paginateActivity(@Query("page") int page, @Query("count") int count);

}
