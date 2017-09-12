package com.xyd.red_wine.api;

import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.login.ChangePasswodModel;
import com.xyd.red_wine.login.LoginModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 14:51
 * @description:
 */

public interface LoginApi {
    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @POST("login/index")
    Observable<BaseModel<LoginModel>> login(@Query("phone") String phone,
                                 @Query("password") String password);

    /**
     * 三方登录
     * @param name
     * @param gender
     * @param uid
     * @return
     */
    @POST("tripartite_login/index")
    Observable<BaseModel<LoginModel>>  tripartite_login(@Query("name")String name,
                                                        @Query("gender")String gender,
                                                        @Query("uid")String uid);
    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param repassword
     * @param referral_code 推荐码（可为空）
     * @param code
     * @return
     */
    @POST("login/reg")
    Observable<BaseModel<EmptyModel>> register(@Query("phone") String phone,
                                    @Query("password") String password,
                                    @Query("repassword") String repassword,
                                    @Query("referral_code") String referral_code,
                                    @Query("code") String code);

    /**
     * @param phone
     * @param scene 发动场景1注册2忘记密码
     * @return
     */
    @POST("login/send_code")
    Observable<BaseModel<EmptyModel>> sendCode(@Query("phone") String phone,
                                    @Query("scene") int scene
    );

    /**
     * @param code
     * @param password
     * @param repassword   确认密码
     * @return
     */
    @POST("login/edit")
    Observable<BaseModel<EmptyModel>> forget(@Query("code") String code,
                                            @Query("password") String password,
                                            @Query("repassword") String repassword

    );
    /**
     * 乔治金瀚注册协议
     */
    @GET("user_agreement/index")
    Observable<BaseModel> xieyi();

    /*
    * 修改密码
    * */
    @POST("user/update_key")
    Observable<BaseModel<ChangePasswodModel>> getModification(@Query("old_password") String old_password, @Query("password") String password, @Query("repassword") String repassword);
}
