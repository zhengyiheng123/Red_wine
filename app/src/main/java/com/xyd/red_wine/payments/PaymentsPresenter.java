package com.xyd.red_wine.payments;

import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/20
 * @time: 15:45
 * @description:
 */

public class PaymentsPresenter implements PaymentsContract.Presenter {
    private PaymentsContract.View view;

    public PaymentsPresenter(PaymentsContract.View view) {
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
                .create(MineApi.class)
                .chest(page, num)
                .compose(RxSchedulers.<BaseModel<PaymentsModel>>compose())
                .subscribe(new BaseObserver<PaymentsModel>() {
                    @Override
                    protected void onHandleSuccess(PaymentsModel model, String msg, int code) {

                        if (page == 1)
                            view.refreshData(model);
                        else if (model.getWelfare_list().size()>0)
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
