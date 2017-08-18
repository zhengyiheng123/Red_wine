package com.xyd.red_wine.message;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.MessageApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.main.home.WebViewActivity;
import com.xyd.red_wine.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 9:27
 * @description:
 */

public class YiduFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener ,BaseQuickAdapter.OnItemClickListener{
    @Bind(R.id.base_rv)
    RecyclerView baseRv;
    @Bind(R.id.base_srl)
    SwipeRefreshLayout baseSrl;
    private int page = 1;
    private int num = 10;
    private List<MessageModel.RemindBean> list;
    private MessageAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.base_srl_rv;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        baseSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        baseRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();
        getData();

    }

    private void initAdapter() {

        list = new ArrayList<>();
        adapter = new MessageAdapter(list);
        adapter.setOnLoadMoreListener(this, baseRv);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        baseRv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
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
        page = 1;
        getData();

    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();
    }

    public void getData() {
        BaseApi.getRetrofit()
                .create(MessageApi.class)
                .remind(1, page, num)
                .compose(RxSchedulers.<BaseModel<MessageModel>>compose())
                .subscribe(new BaseObserver<MessageModel>() {
                    @Override
                    protected void onHandleSuccess(MessageModel model, String msg, int code) {
                        adapter.loadMoreComplete();
                        baseSrl.setRefreshing(false);
                        if (page == 1) {
                            adapter.setNewData(model.getRemind());
                        } else if (model.getRemind().size() > 0) {
                            adapter.addData(model.getRemind());

                        } else {
                            adapter.loadMoreEnd();
                        }

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        adapter.loadMoreComplete();
                        baseSrl.setRefreshing(false);
                        showToast(msg);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
        Bundle b = new Bundle();
        b.putString(MessageDetailActivity.TIME, TimeUtils.millis2String( adapter.getData().get(position).getCreate_time()*1000,"yyyy-MM-dd HH:mm"));
        b.putString(MessageDetailActivity.CONTENT,  adapter.getData().get(position).getMessage());
        b.putInt(MessageDetailActivity.ID,  -1);
        startActivity(MessageDetailActivity.class, b);

    }

    @Subscribe
    public  void onEvent(MessageEvent  event){
        page=1;
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}