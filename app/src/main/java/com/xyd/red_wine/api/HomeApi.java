package com.xyd.red_wine.api;

import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.main.home.HomeModel;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/27
 * @time: 13:44
 * @description:
 */

public interface HomeApi {

    @POST("index/index")
    Observable<BaseModel<HomeModel>>  home();
}
