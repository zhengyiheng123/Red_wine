package com.xyd.red_wine.api;

import com.xyd.red_wine.balance.BalanceModel;
import com.xyd.red_wine.balance.CashValueModel;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.commitorder.AliModel;
import com.xyd.red_wine.commitorder.WxModel;
import com.xyd.red_wine.main.mine.MineFragment;
import com.xyd.red_wine.member.EarningModel;
import com.xyd.red_wine.payments.PaymentsModel;
import com.xyd.red_wine.personinformation.InfromationModel;
import com.xyd.red_wine.rank.RankModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 9:50
 * @description: 个人中心
 */

public interface MineApi {

    /**
     * 个人信息
     *
     * @return
     */
    @POST("user/index")
    Observable<BaseModel<InfromationModel>> information();

    /**
     * 账户余额
     *
     * @return
     */
    @POST("user/account_balance")
    Observable<BaseModel<BalanceModel>> balance();

    /**
     * 公益金
     *
     * @return
     */
    @POST("user/chest_list")
    Observable<BaseModel<PaymentsModel>> chest(@Query("page") int page,
                                               @Query("num") int num);

    /**
     * 我的会员
     *
     * @return
     */
    @POST("user/my_yield")
    Observable<BaseModel<EarningModel>> my_yield(@Query("page") int page,
                                                 @Query("num") int num);

    /**
     * 排行榜
     *
     * @return
     */
    @POST("user/top")
    Observable<BaseModel<RankModel>> rank();

    /**
     * 修改个人信息
     *
     * @param body
     * @return
     */
    @POST("user/user_edit")
    Observable<BaseModel<EmptyModel>> user_edit(@Body RequestBody body);


    /**
     * 充值
     *
     * @param recharge_money 收货地址ID
     * @return
     */
    @POST("wechat_recharge/wechat_pay")
    Observable<BaseModel<WxModel>> wechat(@Query("recharge_money") String recharge_money
    );

    /**
     * @param recharge_money
     * @return
     */
    @POST("ali_recharge/ali_pay")
    Observable<BaseModel<AliModel>> aliPay(@Query("recharge_money") String recharge_money
    );

    /**
     * 提现
     * @param recharge_money
     * @return
     */
    @POST("ali_transfer/transfer")
    Observable<BaseModel<EmptyModel>> ali_transfer(@Query("recharge_money") String recharge_money,
                                                 @Query("pay_account") String pay_account,@Query("code")String code,@Query("payee_real_name")String realName
    );

    /**
     * 提现充值记录
     * @return
     */
    @POST("user/cash_value")
    Observable<BaseModel<CashValueModel>> cash_value(@Query("page") int page,
                                                     @Query("num")int num);

    /**
     * 发送站内信
     */
    @POST("remind/add")
    Observable<BaseModel> sendMessage(@Query("pid")int pid,@Query("r_con")String r_con);

    /**
     * 回复站内信
     */
    @POST("remind/reply")
    Observable<BaseModel> replyMessage(@Query("r_id")int r_id,@Query("r_con")String r_con);

    /**
     * 是否有未读消息
     */
    @POST("user/s_index")
    Observable<BaseModel<MineFragment.Is_Read>> s_index();

    /**
     * 绑定手机号
     */
    @POST("user/edit_phone")
    Observable<BaseModel> bind(@Query("phone")String phone,@Query("code")String code,@Query("password")String password,@Query("repassword")String repassword,@Query("userid")int userId);

    /**
     * 修改手机号
     */
    @POST("user/change_phone")
    Observable<BaseModel> edit_phone(@Query("phone")String phone,@Query("code")String code);
}
