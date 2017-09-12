package com.xyd.red_wine.login;

import android.app.Activity;
import android.content.Context;

import com.xyd.red_wine.base.BasePresenter;
import com.xyd.red_wine.base.BaseView;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 16:10
 * @description:
 */

public interface LoginContract {
    interface  View extends BaseView<Presenter>{
        void error(String msg);
        void success();
        void showDialog();
        void closeDialog();


    }


   interface Presenter extends BasePresenter{

    void login(String phone, String password);
       void loginQQ(Activity context);
       void loginWx(Activity context);
       void loginWb(Activity context);

   }
}
