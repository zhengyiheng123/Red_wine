package com.xyd.red_wine.forgetpassword;

import android.text.TextUtils;
import android.util.Log;

import com.xyd.red_wine.api.LoginApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.RegexUtils;

import static android.icu.lang.UScript.getCode;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 17:14
 * @description:
 */

public class ForgetPasswordPresenter implements ForgetPasswordContract.Presenter {
    private ForgetPasswordContract.View forgetPasswordView;

    public ForgetPasswordPresenter(ForgetPasswordContract.View forgetPasswordView) {
        this.forgetPasswordView = forgetPasswordView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void code(String phone) {
        if (!RegexUtils.isMobilePhoneNumber(phone))
            forgetPasswordView.error("请输入正确的手机号");
        else
            getCode(phone);
    }

    @Override
    public void editPassword(String phone, String password, String code) {
        if (TextUtils.isEmpty(code))
            forgetPasswordView.error("请输入验证码");
        else
            editPassword_(phone,password,code);
        Log.e("code",code);
    }

    private void editPassword_(String phone, String password, String code) {
        forgetPasswordView.showDialog();
        BaseApi.getRetrofit()
                .create(LoginApi.class)
                .forget(code,password,password)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        forgetPasswordView.closeDialog();
                        forgetPasswordView.success();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        forgetPasswordView.closeDialog();
                        forgetPasswordView.error(msg);
                    }
                });

    }

    /**
     * @param phone
     */
    private void getCode(String phone) {
        BaseApi.getRetrofit()
                .create(LoginApi.class)
                .sendCode(phone, 2)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        forgetPasswordView.downTime();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        forgetPasswordView.error(msg);
                    }
                });
    }
}
