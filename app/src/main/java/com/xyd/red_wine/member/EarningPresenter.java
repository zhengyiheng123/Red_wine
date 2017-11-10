package com.xyd.red_wine.member;

import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.ToastUtils;

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

    //发送站内信
    public void sentMessage(int pid,String r_con){
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .sendMessage(pid,r_con)
                .compose(RxSchedulers.<BaseModel>compose())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void onHandleSuccess(Object o, String msg, int code) {
                        if(code == 1){
                            ToastUtils.show("发送成功");
                        }else {
                            ToastUtils.show(msg);
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.show(msg);
                    }
                });
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
                        view.loadmoreComplete();
                        if (page == 1){
                            view.refreshData(model);
                        }
                        else if (model.getDeduct().size()>0){
                            view.loadMoreData(model,1);
                        }

                        else{
                            view.loadMoreData(model,2);
                        }


//                        adapter.loadMoreComplete();
//                        recordSrl.setRefreshing(false);
//                        if (page == 1) {
//                            adapter.setNewData(cashValueModel.getCash_value());
//                        } else if (cashValueModel.getCash_value().size() > 0) {
//                            adapter.addData(cashValueModel.getCash_value());
//
//                        } else {
//                            adapter.loadMoreEnd();
//                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        view.error(msg);
                    }
                });


    }
}
