package com.xyd.red_wine.store;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.payments.PaymentsContract;
import com.xyd.red_wine.payments.PaymentsModel;
import com.xyd.red_wine.winedetail.WineDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 14:23
 * @description:  商城
 */

public class StoreActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener ,BaseQuickAdapter.OnItemClickListener,StoreContract.View{
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.store_rv)
    RecyclerView storeRv;
    @Bind(R.id.store_srl)
    SwipeRefreshLayout storeSrl;
    private List<StoreModel.GoodsBean> list;
    private StoreAdapter adapter;
    private int page=1,num=10;
    private StorePresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    protected void initView() {
        presenter = new StorePresenter(this);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("商城");
        storeSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        storeRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        presenter.getData(page,num);

    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new StoreAdapter(list,this);
        adapter.setOnLoadMoreListener(this,storeRv);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        storeRv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setEnableLoadMore(true);
    }

    @Override
    protected void initEvent() {
        storeSrl.setOnRefreshListener(this);
        baseTitleBack.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.base_title_back:
                finish();
                break;
        }

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {

        Bundle bundle=new Bundle();
        bundle.putString(WineDetailActivity.G_ID,adapter.getData().get(position).getG_id()+"");
        Log.e("position",position+"");
        Log.e("g_id",adapter.getData().get(position).getG_id()+"");
        startActivity(WineDetailActivity.class,bundle);
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
    public void setPresenter(StoreContract.Presenter presenter) {

    }

    @Override
    public void refreshData(StoreModel model) {
        adapter.setNewData(model.getGoods());
        storeSrl.setRefreshing(false);
        adapter.setEnableLoadMore(true);
    }

    @Override
    public void loadMoreData(StoreModel model, int type) {
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
        storeSrl.setRefreshing(false);
        adapter.loadMoreComplete();

    }
}
