package com.xyd.red_wine.collect;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.CollectApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.newsdetail.DetailActivity;
import com.xyd.red_wine.utils.ToastUtils;
import com.xyd.red_wine.video.VideoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 10:08
 * @description: 收藏
 */

public class CollectActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener ,BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

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
    @Bind(R.id.base_title_headline)
    ImageView mHeadLine;
    private List<CollectModel.MyCollectBean> list;
    private CollectAdapterDelete adapter;
    private int page = 1, num = 10;
    private List<CollectModel.MyCollectBean> mCollectModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        mHeadLine.setVisibility(View.GONE);
        baseTitleBack.setText("");
        baseTitleTitle.setText("我的收藏");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleMenu.setImageResource(R.mipmap.sousuo1);
        newsSrl.setOnRefreshListener(this);
        newsSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        newsRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        getData();

    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(CollectApi.class)
                .collect(page, num)
                .compose(RxSchedulers.<BaseModel<CollectModel>>compose())
                .subscribe(new BaseObserver<CollectModel>() {
                    @Override
                    protected void onHandleSuccess(CollectModel collectModel, String msg, int code) {
                        adapter.loadMoreComplete();
                        mCollectModel = collectModel.getMy_collect();
                        newsSrl.setRefreshing(false);
                        if (page==1){
                            adapter.setNewData(collectModel.getMy_collect());
                        }else if (collectModel.getMy_collect().size()>0){
                            adapter.addData(collectModel.getMy_collect());
                        }else {
                            adapter.loadMoreEnd();
                        }

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
        adapter = new CollectAdapterDelete(list, this);
        adapter.setOnLoadMoreListener(this, newsRv);
        View view= LayoutInflater.from(this).inflate(R.layout.empty_view,null);
        adapter.setEmptyView(view);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        newsRv.setAdapter(adapter);


    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        baseTitleMenu.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
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
        page=1;
        getData();

    }

    /**
     * RecyclerView  加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
        Bundle b ;
            if (adapter.getData().get(position).getT_id() == 5){
                b= new Bundle();
                b.putInt(VideoActivity.VIDEO_ID, adapter.getData().get(position).getA_id());
                b.putInt(VideoActivity.COLLECT, 1);
                b.putString(VideoActivity.VIDEO_URL,adapter.getData().get(position).getA_content());
                startActivity(VideoActivity.class,b);
            }else {
                b= new Bundle();
                b.putInt(DetailActivity.NEWS_ID, adapter.getData().get(position).getA_id());
                b.putInt(DetailActivity.COLLECT, 1);
                b.putString(DetailActivity.NEWS_URL,adapter.getData().get(position).getA_content());
                startActivity(DetailActivity.class,b);
            }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        initDialog(position);
    }
    private void initDialog(final int data){
        AlertDialog.Builder builder = new AlertDialog.Builder(CollectActivity.this);
        builder.setTitle("是否删除");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                adapter.remove(data);
                delCollect(data);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void delCollect(final int data) {
        BaseApi.getRetrofit()
                .create(CollectApi.class)
                .delCollect(adapter.getData().get(data).getA_id()+"")
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        ToastUtils.show(msg);
                        adapter.remove(data);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });
    }
}
