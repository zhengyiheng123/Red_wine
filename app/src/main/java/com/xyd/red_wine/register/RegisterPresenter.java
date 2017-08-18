package com.xyd.red_wine.register;

import android.text.TextUtils;

import com.xyd.red_wine.api.LoginApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.RegexUtils;

import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 14:22
 * @description:
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View registerView;

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public RegisterPresenter(RegisterContract.View registerView) {
        this.registerView = registerView;

    }

    @Override
    public void getCode(String phone) {
        if (!RegexUtils.isMobilePhoneNumber(phone))
            registerView.showError("请输入正确的手机号");
        else
            code(phone);


    }

    @Override
    public void register(String phone, String password, String commit, String recommendCode, String code, boolean agreement) {
        if (!RegexUtils.isMobilePhoneNumber(phone))
            registerView.showError("请输入正确的手机号");
        else if (!RegexUtils.isPWD(password))
            registerView.showError("密码必须是6~12位且同时包含数字与字母");
        else if (!password.equals(commit))
            registerView.showError("请确认密码");
        else if (TextUtils.isEmpty(code))
            registerView.showError("请输入手机验证码");
        else if (!agreement)
            registerView.showError("请确认已经阅读并同意相关协议");
        else
            commit(phone, password, commit, recommendCode, code);

    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param commit
     * @param recommendCode 邀请码
     * @param code
     */
    private void commit(String phone, String password, String commit, String recommendCode, String code) {
        registerView.showDialog();
        PublicStaticData.logString.add("2.注册接口调用");
        try {
            BaseApi.getRetrofit()
                    .create(LoginApi.class)
                    .register(phone, password, commit, recommendCode, code)
                    .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                    .subscribe(new BaseObserver<EmptyModel>() {
                        @Override
                        protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                            registerView.closeDialog();
                            registerView.showError(msg);
                            registerView.success();
                            PublicStaticData.logString.add("3.成功信息"+msg);

                        }

                        @Override
                        protected void onHandleError(String msg) {
                            PublicStaticData.logString.add("4.失败信息"+msg);
                            registerView.closeDialog();
                            registerView.showError(msg);
                        }
                    });
        } catch (Throwable t) {
            registerView.showError(t.toString());
            PublicStaticData.logString.add(".注册异常"+t.toString());
        }



    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    private void code(String phone) {
        BaseApi.getRetrofit()
                .create(LoginApi.class)
                .sendCode(phone, 1)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        registerView.downTime();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        registerView.showError(msg);
                    }
                });

    }

}
