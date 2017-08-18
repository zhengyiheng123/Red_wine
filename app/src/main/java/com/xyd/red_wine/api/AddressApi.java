package com.xyd.red_wine.api;

import com.xyd.red_wine.address.AddressModel;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.EmptyModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 11:13
 * @description:
 */

public interface AddressApi {
    /**
     * 查看地址
     *  is_default  1  查看默认
     * @return
     */
    @POST("address/index")
    Observable<BaseModel<AddressModel>> address(@Query("is_default")String is_default);


    /**
     * 添加收货地址
     *
     * @param a_name     收货人姓名
     * @param a_area     收货地址
     * @param a_address  详细地址
     * @param link_phone 手机号
     * @return
     */
    @POST("address/add")
    Observable<BaseModel<EmptyModel>> addAddress(@Query("a_name") String a_name,
                                                 @Query("a_area") String a_area,
                                                 @Query("a_address") String a_address,
                                                 @Query("link_phone") String link_phone);

    /**
     * 修改收货地址
     * @param a_id
     * @param a_name
     * @param a_area
     * @param a_address
     * @param link_phone
     * @return
     */
    @POST("address/edit")
    Observable<BaseModel<EmptyModel>> editAddress(@Query("a_id") String a_id,
                                                  @Query("a_name") String a_name,
                                                  @Query("a_area") String a_area,
                                                  @Query("a_address") String a_address,
                                                  @Query("link_phone") String link_phone);

    /**
     * 删除收货地址
     * @param a_id
     * @return
     */
    @POST("address/del")
    Observable<BaseModel<EmptyModel>> delAddress(@Query("a_id")String a_id);
    /**
     * 设置收货地址
     * @param a_id
     * @return
     */
    @POST("address/set_default")
    Observable<BaseModel<EmptyModel>> setDefaultAddress(@Query("a_id")String a_id);
}
