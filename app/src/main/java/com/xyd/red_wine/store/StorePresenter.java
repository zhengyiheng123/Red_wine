package com.xyd.red_wine.store;

import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.api.StoreApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.payments.PaymentsModel;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 15:45
 * @description:
 */

public class StorePresenter implements StoreContract.Presenter {
    private StoreContract.View view;

    public StorePresenter(StoreContract.View view) {
        this.view = view;

    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getData(final int page, int num) {
        BaseApi.getRetrofit()
                .create(StoreApi.class)
                .goodsList(page, num)
                .compose(RxSchedulers.<BaseModel<StoreModel>>compose())
                .subscribe(new BaseObserver<StoreModel>() {
                    @Override
                    protected void onHandleSuccess(StoreModel model, String msg, int code) {
                        if (page == 1)
                            view.refreshData(model);
                        else if (model.getGoods().size()>0)
                            view.loadMoreData(model,1);
                        else
                            view.loadMoreData(model,2);


                    }

                    @Override
                    protected void onHandleError(String msg) {
                        view.error(msg);
                    }
                });


    }
}
