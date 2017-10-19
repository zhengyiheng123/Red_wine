package com.xyd.red_wine.commissionorder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.OrderApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 15:03
 * @description: 提成订单
 */

public class CommissionOrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.commission_rv)
    RecyclerView commissionRv;
    @Bind(R.id.commission_srl)
    SwipeRefreshLayout commissionSrl;
    @Bind(R.id.base_title_headline)
    ImageView mHeadLlne;
    private CommissionOrderAdapter adapter;
    private List<CommissionOrderModel.DeductBean> list;
    private TextView money;
    private int page = 1, num = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commission_order;
    }

    @Override
    protected void initView() {
        mHeadLlne.setVisibility(View.GONE);
        baseTitleTitle.setText("我的提成订单");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        commissionSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        commissionSrl.setOnRefreshListener(this);
        commissionRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        addHeadView();
        getData();


    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(OrderApi.class)
                .deduct_order(page, num)
                .compose(RxSchedulers.<BaseModel<CommissionOrderModel>>compose())
                .subscribe(new BaseObserver<CommissionOrderModel>() {
                    @Override
                    protected void onHandleSuccess(CommissionOrderModel commissionOrderModel, String msg, int code) {
                        commissionSrl.setRefreshing(false);
                        adapter.loadMoreComplete();
                        money.setText(commissionOrderModel.getTotal_revenue()+"元");
                        if (page == 1) {
                            adapter.setNewData(commissionOrderModel.getDeduct());
                        }else if (commissionOrderModel.getDeduct().size()>0){
                            adapter.addData(commissionOrderModel.getDeduct());
                        }else {
                            adapter.loadMoreEnd();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        commissionSrl.setRefreshing(false);
                        adapter.loadMoreComplete();
                        showToast(msg);
                    }
                });

    }

    private void addHeadView() {
        View headView = LayoutInflater.from(this).inflate(R.layout.payments_head, (ViewGroup) commissionRv.getParent(), false);
        money = (TextView) headView.findViewById(R.id.payments_tv_price);
        adapter.addHeaderView(headView);
    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new CommissionOrderAdapter(list, this);
        adapter.setOnLoadMoreListener(this, commissionRv);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        commissionRv.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
        }

    }

    @Override
    public void onRefresh() {
        page=1;
        getData();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();

    }


}
