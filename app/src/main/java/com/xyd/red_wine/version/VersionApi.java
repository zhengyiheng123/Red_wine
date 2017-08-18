package com.xyd.red_wine.version;

import com.xyd.red_wine.base.BaseModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/14
 * @time: 15:29
 * @description:
 */

public interface VersionApi {

    @POST("versions/index")
    Observable<BaseModel<VersionUpdateModel>>  versions(@Query("version") int version);

}
