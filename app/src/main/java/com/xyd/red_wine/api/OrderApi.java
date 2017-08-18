package com.xyd.red_wine.api;

import com.xyd.red_wine.alipay.AliPay;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.commissionorder.CommissionOrderModel;
import com.xyd.red_wine.commitorder.AliModel;
import com.xyd.red_wine.commitorder.WxModel;
import com.xyd.red_wine.logistics.LogisticsModel;
import com.xyd.red_wine.order.OrderModel;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 10:47
 * @description:
 */

public interface OrderApi {

    /**
     * 查看物流信息
     *
     * @param order_num
     * @return
     */
    @POST("orders/check_logistics")
    Observable<BaseModel<LogisticsModel>> logistics(@Query("order_num") String order_num);

    /**
     * 立即购买
     *
     * @param a_id    收货地址ID
     * @param g_id    产品id
     * @param num     购买数量
     * @param price   付款金额
     * @param content 留言（可以为空）
     * @return
     */
    @POST("wechat_pay/wechat_pay")
    Observable<BaseModel<WxModel>> buy(@Query("a_id") String a_id,
                                       @Query("g_id") String g_id,
                                       @Query("num") String num,
                                       @Query("price") String price,
                                       @Query("content") String content);

    @POST("alipay/ali_pay")
    Observable<BaseModel<AliModel>> buyAliPay(@Query("a_id") String a_id,
                                              @Query("g_id") String g_id,
                                              @Query("num") String num,
                                              @Query("price") String price,
                                              @Query("content") String content);
    /**
     * 确认收货
     *
     * @param order_num
     * @return
     */
    @POST("orders/edit_status")
    Observable<BaseModel<EmptyModel>> edit_status(@Query("order_num") String order_num);

    /**
     * 我的订单
     *
     * @param state 0全部  1待收货  2全部
     * @return
     */
    @POST("orders/index")
    Observable<BaseModel<OrderModel>> orders(@Query("state") String state,
                                             @Query("page") int page,
                                             @Query("num") int num);

    /**
     * 提成订单
     *
     * @return
     */
    @POST("orders/deduct_order")
    Observable<BaseModel<CommissionOrderModel>> deduct_order(@Query("page") int page,
                                                             @Query("num") int num);
}
