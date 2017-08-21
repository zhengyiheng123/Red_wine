package com.xyd.red_wine.rank;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 11:56
 * @description: 公益排行榜
 */

public class BenefitFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.base_rv)
    RecyclerView baseRv;
    @Bind(R.id.base_srl)
    SwipeRefreshLayout baseSrl;
    private List<RankModel.ChestBean> list;
    private BenefitAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.base_srl_rv;
    }

    @Override
    protected void initView() {

        baseSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        baseRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();
        getData();

    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .rank()
                .compose(RxSchedulers.<BaseModel<RankModel>>compose())
                .subscribe(new BaseObserver<RankModel>() {
                    @Override
                    protected void onHandleSuccess(RankModel rankModel, String msg, int code) {
                        adapter.setNewData(rankModel.getChest());
                        baseSrl.setRefreshing(false);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        baseSrl.setRefreshing(false);
                        showToast(msg);
                    }
                });
    }

    private void initAdapter() {

        list = new ArrayList<>();
        adapter = new BenefitAdapter(list, getActivity());
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        baseRv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
    }

    @Override
    protected void initEvent() {
        baseSrl.setOnRefreshListener(this);
    }

    @Override
    public void widgetClick(View v) {

    }

    /**
     * SwipeRefreshLayout
     */
    @Override
    public void onRefresh() {
        getData();

    }


}
