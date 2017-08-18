package com.xyd.red_wine.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.OrderApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.evaluate.EvaluateActivity;
import com.xyd.red_wine.winedetail.WineDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 15:34
 * @description: 订单  完成
 */

public class OrderFinishFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {
    @Bind(R.id.order_rv)
    RecyclerView orderRv;
    @Bind(R.id.order_srl)
    SwipeRefreshLayout orderSrl;

    private List<OrderModel.MyOrderBean> list;
    private OrderAdapter adapter;
    private int page = 1, num = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {

        orderSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        orderRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();
        getData();

    }

    /**
     * 数据获取
     */
    private void getData() {

        BaseApi.getRetrofit()
                .create(OrderApi.class)
                .orders("2", page, num)
                .compose(RxSchedulers.<BaseModel<OrderModel>>compose())
                .subscribe(new BaseObserver<OrderModel>() {
                    @Override
                    protected void onHandleSuccess(OrderModel orderModel, String msg, int code) {
                        adapter.loadMoreComplete();
                        orderSrl.setRefreshing(false);
                        if (page == 1) {
                            adapter.setNewData(orderModel.getMy_order());
                        } else if (orderModel.getMy_order().size() > 0) {
                            adapter.addData(orderModel.getMy_order());
                        } else {
                            adapter.loadMoreEnd();
                        }

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        orderSrl.setRefreshing(false);
                        adapter.loadMoreComplete();
                        showToast(msg);
                    }
                });
    }

    private void initAdapter() {

        list = new ArrayList<>();

        adapter = new OrderAdapter(list, getActivity());
        adapter.setOnLoadMoreListener(this, orderRv);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        orderRv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        adapter.setEnableLoadMore(true);
    }

    @Override
    protected void initEvent() {
        orderSrl.setOnRefreshListener(this);
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        getData();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();

    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
        switch (view.getId()) {
            case R.id.order_tv_left:
                Bundle bundle=new Bundle();
                bundle.putSerializable(EvaluateActivity.EVALUATE, adapter.getData().get(position));
                startActivity(EvaluateActivity.class,bundle);
                break;
            case R.id.order_tv_right:
                Bundle bundle1=new Bundle();
                bundle1.putString(WineDetailActivity.G_ID,adapter.getData().get(position).getG_id()+"");
                startActivity(WineDetailActivity.class,bundle1);
                break;
        }

    }
}
