package com.xyd.red_wine.member;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.ToastUtils;

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
    @Bind(R.id.base_title_headline)
    ImageView mHeadLine;
    private List<EarningModel.DeductBean> list;
    private EarningAdapter adapter;
    private EarningPresenter presenter;
    private int page=1,num=6;
    private InputDialog.Builder dialogBuilder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_earnings;
    }

    @Override
    protected void initView() {
        mHeadLine.setVisibility(View.GONE);
        presenter = new EarningPresenter(this);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("我的会员");
        earningsSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        earningsRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
//        presenter.getData(page,num);
        getNetData(page,num);

    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new EarningAdapter(list, this);
        adapter.setOnLoadMoreListener(this, earningsRv);
        adapter.setOnItemClickListener(this);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
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
//        presenter.getData(page, num);
        getNetData(page,num);

    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
//        presenter.getData(page, num);
        getNetData(page,num);
    }

    @Override
    public void setPresenter(EarningContract.Presenter presenter) {

    }
    //无用
    @Override
    public void refreshData(EarningModel model) {

        adapter.setNewData(model.getDeduct());
//        earningsSrl.setRefreshing(false);
//        adapter.setEnableLoadMore(true);
//        adapter.setOnItemClickListener(this);
    }
    //无用
    @Override
    public void loadMoreData(EarningModel model, int type) {
        if (type == 1) {
            adapter.addData(list);
        } else {
            adapter.loadMoreEnd();
        }

    }
    //无用
    @Override
    public void error(String msg) {
        showToast(msg);
        earningsSrl.setRefreshing(false);
        adapter.loadMoreComplete();

    }
    //无用
    @Override
    public void loadmoreComplete() {
        adapter.loadMoreComplete();
        earningsSrl.setRefreshing(false);
    }
    //  请求网络数据
    private void getNetData(final int page, int num){
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .my_yield(page, num)
                .compose(RxSchedulers.<BaseModel<EarningModel>>compose())
                .subscribe(new BaseObserver<EarningModel>() {
                    @Override
                    protected void onHandleSuccess(EarningModel earningModel, String msg, int code) {
                        adapter.loadMoreComplete();
                        earningsSrl.setRefreshing(false);
                        if (page == 1) {
                            adapter.setNewData(earningModel.getDeduct());
                        } else if (earningModel.getDeduct().size() > 0) {
                            adapter.addData(earningModel.getDeduct());

                        } else {
                            adapter.loadMoreEnd();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        adapter.loadMoreComplete();
                        earningsSrl.setRefreshing(false);
                        showToast(msg);
                    }
                });
    }
    @Override
    public void onItemClick(final BaseQuickAdapter adapter1, View view, final int position) {
        dialogBuilder = new InputDialog.Builder(EarningActivity.this)
                .setTitle("留言")
                .setInputHint("请输入留言")
                .setPositiveButton("确定", new InputDialog.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        // TODO
//                        ToastUtils.show(inputText);
                        if (TextUtils.isEmpty(inputText.toString())){
                            ToastUtils.show("请输入内容");
                            return;
                        }
                        presenter.sentMessage(adapter.getData().get(position).getUserid(),inputText.toString());
                    }
                })
                .setNegativeButton("取消", new InputDialog.ButtonActionListener() {
                    @Override
                    public void onClick(CharSequence inputText) {
                        // TODO
                    }
                });
        dialogBuilder.show();
    }
}
