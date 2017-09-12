package com.xyd.red_wine.primeur;

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
import com.xyd.red_wine.api.SuggestApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.newsdetail.DetailActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 14:16
 * @description: 期酒
 */

public class PrimeurActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener{
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.news_rv)
    RecyclerView newsRv;
    @Bind(R.id.news_srl)
    SwipeRefreshLayout newsSrl;

    private PrimeurAdapter adapter;
    private int page = 1, num = 10;
    private List<PrimeurModel.ArticlesBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        baseTitleBack.setText("");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("期酒");
        newsSrl.setOnRefreshListener(this);
        newsSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        newsRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        data();

    }
    private void data() {
        BaseApi.getRetrofit()
                .create(SuggestApi.class)
                .wins_msg(page, num)
                .compose(RxSchedulers.<BaseModel<PrimeurModel>>compose())
                .subscribe(new BaseObserver<PrimeurModel>() {
                    @Override
                    protected void onHandleSuccess(PrimeurModel model, String msg, int code) {
                        adapter.loadMoreComplete();
                        newsSrl.setRefreshing(false);
                        if (page == 1)
                          adapter.setNewData(model.getArticles());
                        else if (model.getArticles().size() > 0)
                            adapter.addData(model.getArticles());
                        else
                            adapter.loadMoreEnd();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        adapter.loadMoreComplete();
                        newsSrl.setRefreshing(false);
                        showToast(msg);
                    }
                });

    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new PrimeurAdapter(list, this);
        adapter.setOnLoadMoreListener(this, newsRv);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        newsRv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);

    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        baseTitleMenu.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.base_title_menu:
                showTestToast("menu");
                break;
        }

    }

    /**
     * SwipeRefreshLayout
     */
    @Override
    public void onRefresh() {
        page = 1;
        data();

    }

    /**
     * RecyclerView  加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        data();

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        adapter.remove(position);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
        Bundle b = new Bundle();
        b.putInt(DetailActivity.NEWS_ID, adapter.getData().get(position).getA_id());
        b.putInt(DetailActivity.COLLECT, adapter.getData().get(position).getCollect());
        b.putString(DetailActivity.NEWS_URL,adapter.getData().get(position).getA_content());
        startActivity(DetailActivity.class,b);

    }
}
