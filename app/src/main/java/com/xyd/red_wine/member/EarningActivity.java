package com.xyd.red_wine.member;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 14:23
 * @description: 我的下级
 */

public class EarningActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener ,EarningContract.View{
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;

    @Bind(R.id.earnings_rv)
    RecyclerView earningsRv;
    @Bind(R.id.earnings_srl)
    SwipeRefreshLayout earningsSrl;
    private List<EarningModel.DeductBean> list;
    private EarningAdapter adapter;
    private EarningPresenter presenter;
    private int page=1,num=10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_earnings;
    }

    @Override
    protected void initView() {
        presenter = new EarningPresenter(this);
        baseTitleMenu.setVisibility(View.GONE);
        baseTitleTitle.setText("我的会员");
        earningsSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        earningsRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        presenter.getData(page,num);

    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new EarningAdapter(list, this);
        adapter.setOnLoadMoreListener(this, earningsRv);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        earningsRv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
    }

    @Override
    protected void initEvent() {
        earningsSrl.setOnRefreshListener(this);
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


    /**
     * SwipeRefreshLayout
     */
    @Override
    public void onRefresh() {
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
    public void setPresenter(EarningContract.Presenter presenter) {

    }

    @Override
    public void refreshData(EarningModel model) {

        adapter.setNewData(model.getDeduct());
        earningsSrl.setRefreshing(false);
        adapter.setEnableLoadMore(true);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void loadMoreData(EarningModel model, int type) {
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
        earningsSrl.setRefreshing(false);
        adapter.loadMoreComplete();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
        Bundle b=new Bundle();
        b.putInt(MemberActivity.ID,adapter.getData().get(position).getUserid());
        startActivity(MemberActivity.class,b);

    }
}
