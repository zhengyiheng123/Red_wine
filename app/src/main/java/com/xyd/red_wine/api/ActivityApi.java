package com.xyd.red_wine.api;

import com.xyd.red_wine.activity.BenefitModel;
import com.xyd.red_wine.base.BaseModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/21
 * @time: 16:11
 * @description:
 */

public interface ActivityApi {
    /**
     *
     * @param ac_type 活动类型1商业0公益
     * @param page
     * @param num
     * @return
     */
    @POST("active/index")
    Observable<BaseModel<BenefitModel>>  active(@Query("ac_type") int ac_type,
                                                @Query("page") int page,
                                                @Query("num") int num);
}
