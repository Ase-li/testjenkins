package com.chd.chd56lc.net.api;

import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.InvestmentClassroomBean;
import com.chd.chd56lc.entity.MessageBean;
import com.chd.chd56lc.entity.PageListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AmbitusApi {
    /**
     * 查看消息列表
     *
     * @param page
     * @param count
     * @param type
     * @return PageListBean<MessageBean>
     */
    @GET("api/service-message-push/app/messageCenter/paginageMsgList")
    Observable<BaseBean<PageListBean<MessageBean>>> paginageMsgList(@Query("page") int page, @Query("count") int count, @Query("type") int type);

    /**
     * 根据id查询消息内容
     *
     * @param id
     * @return
     */
    @GET("api/service-message-push/app/messageCenter/getMessageById")
    Observable<BaseBean<MessageBean>> getMessageById(@Query("id") String id);

    /**
     * 投资课堂分页
     *
     * @param page
     * @param count
     * @param type  类型 0-风险教育、1-知识普及
     * @return
     */
    @GET("/api/service-operation/app/investGuide/paginateByReq")
    Observable<BaseBean<PageListBean<InvestmentClassroomBean>>> paginateByReq(@Query("page") int page, @Query("count") int count, @Query("type") int type);


}
