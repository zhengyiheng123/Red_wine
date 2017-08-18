package com.xyd.red_wine.api;

import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.collect.CollectModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 11:05
 * @description:
 */

public interface CollectApi {
    /**
     * 我的收藏
     * @return
     */
    @POST("collect/index")
    Observable<BaseModel<CollectModel>> collect(@Query("page")int page,
                                                @Query("num")int num);

    /**
     * 添加收藏
     * @param a_id
     * @return
     */
    @POST("collect/add")
    Observable<BaseModel<EmptyModel>> addCollect(@Query("a_id") String a_id);

    /**
     * 取消收藏
     * @param a_id
     * @return
     */
    @POST("collect/del")
    Observable<BaseModel<EmptyModel>> delCollect(@Query("a_id") String a_id);
}
