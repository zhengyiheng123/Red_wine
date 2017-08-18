package com.xyd.red_wine.store;

import com.xyd.red_wine.base.BasePresenter;
import com.xyd.red_wine.base.BaseView;
import com.xyd.red_wine.payments.PaymentsModel;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 15:39
 * @description:
 */

public interface StoreContract {

    interface View extends BaseView<Presenter>{
        void refreshData(StoreModel model);

        /**
         *
         * @param model
         * @param type  1 有数据 2无更多数据
         */
        void loadMoreData(StoreModel model, int type);
        void error(String msg);


    }


    interface Presenter extends BasePresenter{
        void getData(int page, int num);
    }



}
