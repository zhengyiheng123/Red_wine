package com.xyd.red_wine.message;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.activity.BenefitAdapter;
import com.xyd.red_wine.activity.BenefitModel;
import com.xyd.red_wine.api.ActivityApi;
import com.xyd.red_wine.api.MessageApi;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.main.home.WebViewActivity;
import com.xyd.red_wine.utils.TimeUtils;
import com.xyd.red_wine.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 9:27
 * @description:
 */

public class WeiduFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
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
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
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
                .remind(0, page, num)
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
        b.putString(MessageDetailActivity.TIME, TimeUtils.stampToDateSdemand( adapter.getData().get(position).getCreate_time()+"","yyyy.MM.dd HH:mm"));
        b.putString(MessageDetailActivity.CONTENT,  adapter.getData().get(position).getMessage());
        b.putInt(MessageDetailActivity.ID,  adapter.getData().get(position).getR_id());
        b.putInt(MessageDetailActivity.USER_ID,adapter.getData().get(position).getU_id());
        b.putInt(MessageDetailActivity.R_TYPE,adapter.getData().get(position).getR_type());
        b.putInt(MessageDetailActivity.IS_REPLY,adapter.getData().get(position).getIs_reply());
        b.putString(MessageDetailActivity.FROM,adapter.getData().get(position).getNickname());
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
}
