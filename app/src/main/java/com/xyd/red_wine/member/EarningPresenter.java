package com.xyd.red_wine.member;

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

public class EarningPresenter implements EarningContract.Presenter {
    private EarningContract.View view;

    public EarningPresenter(EarningContract.View view) {
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
                .my_yield(page, num)
                .compose(RxSchedulers.<BaseModel<EarningModel>>compose())
                .subscribe(new BaseObserver<EarningModel>() {
                    @Override
                    protected void onHandleSuccess(EarningModel model, String msg, int code) {
                        if (page == 1)
                            view.refreshData(model);
                        else if (model.getDeduct().size()>0)
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
