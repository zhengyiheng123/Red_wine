package com.xyd.red_wine.payments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.xyd.red_wine.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/11
 * @time: 9:48
 * @description: 公益金
 */

public class PaymentsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, PaymentsContract.View {

    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.payments_rv)
    RecyclerView paymentsRv;
    @Bind(R.id.payments_srl)
    SwipeRefreshLayout paymentsSrl;
    @Bind(R.id.base_title_headline)
    ImageView mHaedLine;
    private PaymentsAdapter adapter;
    private List<PaymentsModel.WelfareListBean> list;
    private TextView money;
    private PaymentsPresenter presenter;
    private int page = 1, num = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payments;
    }

    @Override
    protected void initView() {
        mHaedLine.setVisibility(View.GONE);
        presenter = new PaymentsPresenter(this);
        baseTitleTitle.setText("公益金");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        paymentsSrl.setOnRefreshListener(this);
        paymentsSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        paymentsRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        addHeadView();
        presenter.getData(page, num);

    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new PaymentsAdapter(list, this);
        adapter.setOnLoadMoreListener(this, paymentsRv);
        paymentsRv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);


    }

    private void addHeadView() {
        View headView = LayoutInflater.from(this).inflate(R.layout.payments_head, (ViewGroup) paymentsRv.getParent(), false);
        money = (TextView) headView.findViewById(R.id.payments_tv_price);
        adapter.addHeaderView(headView);
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


    /**
     * SwipeRefreshLayout
     */
    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        page = 1;
        presenter.getData(page, num);


    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        presenter.getData(page, num);
    }

    @Override
    public void setPresenter(PaymentsContract.Presenter presenter) {

    }

    @Override
    public void refreshData(PaymentsModel model) {
        money.setText(model.getChest()+"元");
        adapter.setNewData(model.getWelfare_list());
        paymentsSrl.setRefreshing(false);
        adapter.setEnableLoadMore(true);
    }

    @Override
    public void loadMoreData(PaymentsModel model, int type) {
        if (type == 1) {
            adapter.addData(list);
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
        }

    }

    @Override
    public void error(String msg) {
        showToast(msg);
        paymentsSrl.setRefreshing(false);
        adapter.loadMoreComplete();

    }
}
