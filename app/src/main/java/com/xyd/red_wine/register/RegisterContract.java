package com.xyd.red_wine.register;

import com.xyd.red_wine.base.BasePresenter;
import com.xyd.red_wine.base.BaseView;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/18
 * @time: 17:01
 * @description:
 */

public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void showError(String msg);
        void success();
        void showDialog();
        void closeDialog();
        void downTime();
    }

    interface Presenter extends BasePresenter {
        void getCode(String phone);
        void register(String phone, String password, String commit, String recommendCode, String code, boolean agreement);

    }
}
